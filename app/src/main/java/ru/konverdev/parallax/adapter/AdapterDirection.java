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
import ru.konverdev.parallax.model.classes.Direction;

public class AdapterDirection extends ArrayAdapter<Direction> {

    private static final int START_SEARCH_SUMBOLS = 1;
    private ArrayList<Direction> directions;

    public AdapterDirection(Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return directionsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_direction
                    , parent, false);
        }
        TextView item = convertView.findViewById(R.id.ItemDirectionValue);
        Direction direction = getItem(position);
        if (direction != null){
            item.setText(direction.getName());
        }
        return convertView;
    }

    private Filter directionsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() < START_SEARCH_SUMBOLS)
            {
                results.values = new ArrayList<Direction>();
                results.count = 0;
            }
            else {
                directions = Direction.GetDirectionsByName(constraint.toString().trim());
                results.values = directions;
                results.count = directions.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Direction)resultValue).getName();
        }
    };
}