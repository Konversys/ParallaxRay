package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterDirection;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.Route;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Station;
import ru.konverdev.parallax.model.classes.Wagon;
import ru.konverdev.parallax.model.yandex_api.Segment;
import ru.konverdev.parallax.model.yandex_api.Stations;
import ru.konverdev.parallax.model.yandex_api.Thread;
import ru.konverdev.parallax.utils.tools.EnvHandler;
import ru.konverdev.parallax.utils.tools.FragmentHandler;
import ru.konverdev.parallax.utils.tools.TimeConverter;
import ru.konverdev.parallax.utils.web.ApiConnector;

public class DirectionActivity extends AppCompatActivity {
    private static final String NO_NUMBER = "Введите номер вагона";
    private static final String NO_FACTORY = "Введите заводской номер вагона";
    private static final String NO_ALL = "Введите порядковый и заводской номера вагона";

    private TextView selectedDirection;
    private TextView selectedDate;

    private static AppCompatEditText numberEt;
    private static AppCompatEditText factoryEt;

    private static FragmentManager fragmentManager;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        route = new Route();
        EnvHandler.Init(this, "Начать рейс");
        initComponents();
        initSearch();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initComponents() {
        selectedDirection = (TextView) findViewById(R.id.AcDirectionSelectedTrain);
        selectedDate = (TextView) findViewById(R.id.AcDirectionSelectedDate);
        numberEt = (AppCompatEditText) findViewById(R.id.AcDirectionNumber);
        factoryEt = (AppCompatEditText) findViewById(R.id.AcDirectionFactory);
        fragmentManager = getSupportFragmentManager();
    }

    private void initSearch() {
        AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.AcDirectionSearch);
        search.setAdapter(new AdapterDirection(this));
        search.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Direction) {
                route.setDirection((Direction) item);
                ((TextView) findViewById(R.id.AcDirectionSelectedTrain)).setText(((Direction) item).getName());
                RefreshView();
            }
        });
    }

    public void clickDatepicker(View view) {
        Calendar min_calendar = Calendar.getInstance();
        min_calendar.set(Calendar.YEAR, 2019);
        min_calendar.set(Calendar.MONTH, 1);
        min_calendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        StringBuilder date = new StringBuilder(TimeConverter.getStringDate(date_ship_millis, TimeConverter.DATE_DAY_FMONTH_YEAR));
                        date.append(" г.");
                        route.setDate(TimeConverter.getDate(date_ship_millis));
                        ((TextView) findViewById(R.id.AcDirectionSelectedDate)).setText(date);
                        RefreshView();
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.primaryColor));
        datePicker.setMinDate(min_calendar);
        datePicker.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    public void clickStartLoad(View view) {
        String number_str = numberEt.getText().toString();
        String factory_str = factoryEt.getText().toString();
        if (number_str.equals("") && factory_str.equals("")) {
            Toast.makeText(getApplicationContext(), NO_ALL, Toast.LENGTH_SHORT).show();
            return;
        } else if (number_str.equals("")) {
            Toast.makeText(getApplicationContext(), NO_NUMBER, Toast.LENGTH_SHORT).show();
            return;
        } else if (factory_str.equals("")) {
            Toast.makeText(getApplicationContext(), NO_FACTORY, Toast.LENGTH_SHORT).show();
            return;
        }
        if (route.getDate() != null && route.getDirection() != null) {
            FragmentHandler.Download(fragmentManager);
            GetYandexDir(this, route);
        } else if (route.getDirection() == null && route.getDate() == null) {
            Toast.makeText(getApplicationContext(), Route.NO_ALL, Toast.LENGTH_SHORT).show();
        } else if (route.getDate() == null) {
            Toast.makeText(getApplicationContext(), Route.NO_DATE, Toast.LENGTH_SHORT).show();
        } else if (route.getDirection() == null) {
            Toast.makeText(getApplicationContext(), Route.NO_DIRECTION, Toast.LENGTH_SHORT).show();
        }
    }

    private void RefreshView() {
        if (route.getDirection() == null) {
            selectedDirection.setText(getResources().getString(R.string.no_select));
        }
        if (route.getDate() == null) {
            selectedDate.setText(getResources().getString(R.string.no_select));
        }
    }


    private static void GetYandexDir(AppCompatActivity activity, Route route) {
        ApiConnector.getYandexApi()
                .getDirectionsMsk(
                        route.getDirection().getFrom(),
                        route.getDirection().getTo(),
                        TimeConverter.getStringDate(route.getDate(), TimeConverter.DATE_LINE_YEAR_SMONTH_DAY))
                .enqueue(
                        new Callback<ru.konverdev.parallax.model.yandex_api.Direction>() {
                            @Override
                            public void onResponse(Call<ru.konverdev.parallax.model.yandex_api.Direction> call,
                                                   Response<ru.konverdev.parallax.model.yandex_api.Direction> response) {
                                ArrayList<Segment> segmentsYandex = response.body().getSegments().stream().filter(
                                        x -> x.getThread().getNumber().equals(route.getDirection().getValue())
                                ).collect(Collectors.toCollection(ArrayList::new));
                                if (segmentsYandex.size() <= 0) {
                                    FragmentHandler.Empty(fragmentManager);
                                    CustomToast.SnackBarIconError(activity, Route.ERROR_YANDEX);
                                } else {
                                    GetYandexStations(activity, route, segmentsYandex.get(0).getThread());
                                }

                            }

                            @Override
                            public void onFailure(Call<ru.konverdev.parallax.model.yandex_api.Direction> call, Throwable t) {
                                FragmentHandler.Empty(fragmentManager);
                                CustomToast.SnackBarIconError(activity, Route.ERROR_YANDEX);
                            }
                        }
                );
    }

    private static void GetYandexStations(AppCompatActivity activity, Route route, Thread thread) {
        ApiConnector.getYandexApi()
                .getStations(thread.getUid(), TimeConverter.getStringDate(route.getDate(), TimeConverter.DATE_LINE_YEAR_SMONTH_DAY))
                .enqueue(
                        new Callback<Stations>() {
                            @Override
                            public void onResponse(Call<Stations> call, Response<Stations> response) {
                                if (response.body() != null) {
                                    Station.SaveStations(response.body().GetStations());
                                    Direction.SetSelectedDirection(route.getDirection());
                                    Wagon.SetWagon(Wagon.COUPE,
                                            Integer.valueOf(numberEt.getText().toString()),
                                            Integer.valueOf(factoryEt.getText().toString()));
                                    FragmentHandler.Empty(fragmentManager);
                                    Intent intent = new Intent(activity, ScheduleActivity.class);
                                    activity.startActivity(intent);
                                } else {
                                    CustomToast.SnackBarIconError(activity, Route.ERROR_NO_STATIONS);
                                }
                            }

                            @Override
                            public void onFailure(Call<Stations> call, Throwable t) {
                                FragmentHandler.Empty(fragmentManager);
                                CustomToast.SnackBarIconError(activity, Route.ERROR_YANDEX);
                            }
                        }
                );
    }
}
