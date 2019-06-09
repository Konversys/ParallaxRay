package ru.konverdev.parallax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.model.classes.Station;

public class AdapterSimpleStation extends ArrayAdapter<Station> {

    private ArrayList<Station> stations;
    private int atom;

    public AdapterSimpleStation(Context context, int atom) {
        super(context, 0);
        this.atom = atom;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return stationsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple_station
                    , parent, false);
        }
        TextView item = convertView.findViewById(R.id.ItemSimpleStationItem);
        Station station = getItem(position);
        if (station != null) {
            item.setText(station.getTitle());
        }
        return convertView;
    }

    private Filter stationsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null) {
                if (atom == Station.ATOM) {
                    results.values = Station.GetStations();
                } else {
                    results.values = Station.FindStationsAfter(atom);
                }
            } else {
                if (atom == Station.ATOM) {
                    results.values = Station.FindStations(constraint.toString());
                } else {
                    results.values = Station.FindStationsAfter(constraint.toString(), atom);
                }
            }
            results.count = ((ArrayList) results.values).size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Station) resultValue).getTitle();
        }
    };
}