package ru.konverdev.parallax.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.dialog.PlaceDialog;
import ru.konverdev.parallax.model.classes.Coupe;
import ru.konverdev.parallax.model.classes.Station;
import ru.konverdev.parallax.model.classes.Wagon;

public class AdapterCoupe extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Coupe> items = new ArrayList<>();

    public void ResetItems() {
        Wagon wagon = Wagon.GetWagon();
        if (wagon != null && wagon.getCoupes() != null || wagon.getCoupes().size() != 0) {
            this.items = new ArrayList<>(Wagon.GetWagon().getCoupes());
        }
    }

    private Context ctx;
    private AdapterCoupe.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Coupe obj, int position);
    }

    public void setOnItemClickListener(final AdapterCoupe.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterCoupe(Context context) {
        ResetItems();
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView coupe;
        public Button lt;
        public Button rt;
        public Button lb;
        public Button rb;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            coupe = (TextView) v.findViewById(R.id.ItemCoupeKypeNumber);
            lt = (Button) v.findViewById(R.id.ItemCoupeKypeLT);
            rt = (Button) v.findViewById(R.id.ItemCoupeKypeRT);
            lb = (Button) v.findViewById(R.id.ItemCoupeKypeLB);
            rb = (Button) v.findViewById(R.id.ItemCoupeKypeRB);
            lyt_parent = (View) v.findViewById(R.id.ItemCoupeKypeLyt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupe, parent, false);
        vh = new AdapterCoupe.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterCoupe.OriginalViewHolder) {
            AdapterCoupe.OriginalViewHolder view = (AdapterCoupe.OriginalViewHolder) holder;
            PlaceDialog placeDialog = new PlaceDialog(ctx, this);
            if (Station.GetCurrent() == null) {
                return;
            }
            Station station = Station.GetStation(Station.GetCurrent().getNumber());
            int curStationId = station.getNumber();
            boolean isEnd = false;
            if (station == null) {
                isEnd = true;
            }
            Coupe item = items.get(position);
            view.coupe.setText(String.valueOf(item.getNumber()));
            if (item.getPlaceLB().isTaken()) {
                int placeStationId = item.getPlaceLB().getTo().getNumber();
                if (placeStationId == curStationId) {
                    view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else if (placeStationId < curStationId) {
                    view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                }
                view.lb.setText(item.getPlaceLB().getNumber() + " " + item.getPlaceLB().getTo().getTitle());
            } else {
                view.lb.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                view.lb.setText(String.valueOf(item.getPlaceLB().getNumber()));
            }

            if (item.getPlaceRB().isTaken()) {
                int placeStationId = item.getPlaceRB().getTo().getNumber();
                if (placeStationId == curStationId) {
                    view.rb.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else if (placeStationId < curStationId) {
                    view.rb.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    view.rb.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                }
                view.rb.setText(item.getPlaceRB().getNumber() + " " + item.getPlaceRB().getTo().getTitle());
            } else {
                view.rb.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                view.rb.setText(String.valueOf(item.getPlaceRB().getNumber()));
            }

            if (item.getPlaceLT().isTaken()) {
                int placeStationId = item.getPlaceLT().getTo().getNumber();
                if (placeStationId == curStationId) {
                    view.lt.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else if (placeStationId < curStationId) {
                    view.lt.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    view.lt.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                }
                view.lt.setText(item.getPlaceLT().getNumber() + " " + item.getPlaceLT().getTo().getTitle());
            } else {
                view.lt.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                view.lt.setText(String.valueOf(item.getPlaceLT().getNumber()));
            }

            if (item.getPlaceRT().isTaken()) {
                int placeStationId = item.getPlaceRT().getTo().getNumber();
                if (placeStationId == curStationId) {
                    view.rt.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else if (placeStationId < curStationId) {
                    view.rt.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    view.rt.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                }
                view.rt.setText(item.getPlaceRT().getNumber() + " " + item.getPlaceRT().getTo().getTitle());
            } else {
                view.rt.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                view.rt.setText(String.valueOf(item.getPlaceRT().getNumber()));
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.lb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceLB());
                }
            });
            view.lt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceLT());
                }
            });
            view.rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceRB());
                }
            });
            view.rt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeDialog.showDialog(item.getPlaceRT());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
