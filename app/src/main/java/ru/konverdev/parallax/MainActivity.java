package ru.konverdev.parallax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.konverdev.parallax.activity.DirectionActivity;
import ru.konverdev.parallax.activity.ScheduleActivity;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.Route;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.utils.tools.FragmentHandler;
import ru.konverdev.parallax.utils.tools.Tools;
import ru.konverdev.parallax.utils.web.ApiConnector;

public class MainActivity extends AppCompatActivity {
    private static final String ERROR_PARALLAX_API = "Не удалось получить список направлений";

    private static FragmentManager fragmentManager;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        activity = this;
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (Direction.GetDirections(0).isEmpty()) {
            FragmentHandler.Download(fragmentManager);
            ApiConnector.getParallaxLinkApi().getValidDirections().enqueue(new Callback<List<Direction>>() {
                @Override
                public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                    Direction.SaveDirections(response.body());
                    FragmentHandler.Empty(fragmentManager);
                    startActivity();
                }

                @Override
                public void onFailure(Call<List<Direction>> call, Throwable t) {
                    CustomToast.SnackBarIconError(activity, ERROR_PARALLAX_API);
                }
            });
        } else {
            startActivity();
        }
    }

    private void startActivity() {
        if (Tools.IsFlight()) {
            Intent intent = new Intent(activity, ScheduleActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(activity, DirectionActivity.class);
            startActivity(intent);
        }
    }
}
