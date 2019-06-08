package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterStation;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Station;
import ru.konverdev.parallax.utils.tools.EnvHandler;

public class ScheduleActivity extends AppCompatActivity {

    private static final String NO_STATIONS = "Станции не найдены";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        EnvHandler.Init(this, "Расписание");
        initComponent();
    }

    private void initComponent() {
        ArrayList<Station> stations = Station.GetStations();
        if (stations == null || stations.isEmpty() || Direction.GetSelectedDirection() == null) {
            CustomToast.SnackBarIconError(this, NO_STATIONS);
        } else {
            ((TextView) findViewById(R.id.AcScheduleCurrentTrain)).setText(Direction.GetSelectedDirection().getName());
            ListView listView = (ListView) findViewById(R.id.AcScheduleItems);
            AdapterStation mAdapter = new AdapterStation(this);
            listView.setAdapter(mAdapter);
        }
    }
}
