package ru.konverdev.parallax.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.model.classes.Station;
import ru.konverdev.parallax.utils.tools.TimeConverter;
import ru.konverdev.parallax.utils.tools.Tools;

public class AdapterStation extends ArrayAdapter<Station> {

    private static final String BEHIND = "Пройдено";
    private static final String START_STATION = "Станция отправления";
    private static final String END_STATION = "Станция прибытия";
    private static final String POSTFIX_MINUTE = " мин";

    private ArrayList<ViewHolder> lstHolders;
    private Handler handler = new Handler();

    private LayoutInflater lf;

    public AdapterStation(Context ctx) {
        super(ctx, 0, Station.GetStations());
        lstHolders = new ArrayList<>();
        lf = LayoutInflater.from(ctx);
        startUpdateTimer();
        notifyDataSetChanged();
    }

    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (ViewHolder holder : lstHolders) {
                    try {
                        holder.updateTimeRemaining();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateRemainingTimeRunnable);
            }
        }, 0, 1000);
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            holder = new ViewHolder();
            LayoutInflater inflater;
            v = lf.inflate(R.layout.item_station, parent, false);
            holder.number = (TextView) v.findViewById(R.id.ItemStationNumber);
            holder.station = (TextView) v.findViewById(R.id.ItemStationStation);
            holder.time = (TextView) v.findViewById(R.id.ItemStationTime);
            holder.arrival = (TextView) v.findViewById(R.id.ItemStationArrival);
            holder.stay = (TextView) v.findViewById(R.id.ItemStationStay);
            holder.departure = (TextView) v.findViewById(R.id.ItemStationDeparture);
            v.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.setData(getItem(position));
        return v;
    }

    private class ViewHolder {
        public TextView number;
        public TextView station;
        public TextView time;
        public TextView arrival;
        public TextView stay;
        public TextView departure;

        Station value;

        private void setData(Station item) {
            value = item;
            number.setText(String.valueOf(item.getNumber()));
            station.setText(item.getTitle());
            arrival.setText(item.getArrival() == null ? START_STATION : TimeConverter.getStringDate(item.getArrival(), TimeConverter.DATE_DAY_FMONTH_YEAR_TIME));
            stay.setText(item.getStop_time() / 60 + POSTFIX_MINUTE);
            departure.setText(item.getDeparture() == null ? END_STATION : TimeConverter.getStringDate(item.getDeparture(), TimeConverter.DATE_DAY_FMONTH_YEAR_TIME));
        }

        private void updateTimeRemaining() throws ParseException {
            Station.RefreshStationsRatio();
            if (value == null) {
                return;
            }
            switch (value.getPosition()) {
                case Station.BEFORE_NOW:
                    time.setTextColor(Color.GRAY);
                    time.setText(BEHIND);
                    break;
                case Station.STAY:
                    time.setTextColor(Color.RED);
                    time.setText(TimeConverter.getDifference(Tools.getNowMSK(), value.getDeparture()));
                    break;
                case Station.AFTER_NOW:
                    time.setTextColor(Color.GREEN);
                    time.setText(TimeConverter.getDifference(Tools.getNowMSK(), value.getArrival()));
                    break;
                default:
                    time.setText("Неизвестно");
                    break;
            }
        }
    }
}