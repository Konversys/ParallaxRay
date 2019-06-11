package ru.konverdev.parallax.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterCoupe;
import ru.konverdev.parallax.adapter.AdapterSimpleStation;
import ru.konverdev.parallax.model.classes.Place;
import ru.konverdev.parallax.model.classes.Station;

public class PlaceDialog {
    private static final String NO_ALL = "Выберите станции отправления и прибытия";
    private static final String NO_ARRIVAL = "Выберите станцию отправления";
    private static final String NO_DEPARTURE = "Выберите станцию прибытия";


    private Context context;
    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private Place place;
    private Station from;
    private Station to;
    private AdapterCoupe adapterWagonCoupe;

    private int selected_station_from;

    public PlaceDialog(Context activity, AdapterCoupe adapterWagonCoupe) {
        this.context = activity;
        this.adapterWagonCoupe = adapterWagonCoupe;
        selected_station_from = -1;
        initComponent();
        initDialog();
    }

    private void initComponent() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_set_place);
        dialog.setCancelable(true);

        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void initDialog() {
        final AutoCompleteTextView fromACTV = (AutoCompleteTextView) dialog.findViewById(R.id.DialogSetPlaceFrom);
        final AutoCompleteTextView toACTV = (AutoCompleteTextView) dialog.findViewById(R.id.DialogSetPlaceTo);
        final CheckBox animal = (CheckBox) dialog.findViewById(R.id.DialogSetPlaceAnimal);
        final CheckBox children = (CheckBox) dialog.findViewById(R.id.DialogSetPlaceChildren);
        final CheckBox linen = (CheckBox) dialog.findViewById(R.id.DialogSetPlaceLinen);
        fromACTV.setAdapter(new AdapterSimpleStation(dialog.getContext(), Station.ATOM));
        toACTV.setAdapter(new AdapterSimpleStation(dialog.getContext(), Station.ATOM));

        fromACTV.setOnItemClickListener((parent, view, position, id) -> {
                    Object item = parent.getItemAtPosition(position);
                    if (item instanceof Station) {
                        this.from = (Station) item;
                        selected_station_from = from.getNumber();
                        toACTV.setAdapter(new AdapterSimpleStation(dialog.getContext(), from.getNumber()));
                    }
                }
        );

        toACTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_station_from == Station.ATOM || fromACTV.getText().toString().equals("")) {
                    toACTV.setAdapter(new AdapterSimpleStation(dialog.getContext(), Station.ATOM));
                }
            }

            @Override
            protected void finalize() throws Throwable {
                if (selected_station_from != Station.ATOM || fromACTV.getText().toString().equals("")) {
                    super.finalize();
                }
            }
        });

        toACTV.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Station) {
                this.to = (Station) item;
            }
        });


        ((ImageButton) dialog.findViewById(R.id.DialogSetPlaceCancel)).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog();
                    }
                });
        ((Button) dialog.findViewById(R.id.DialogSetPlaceRemove)).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        place.clean();
                        closeDialog();
                        adapterWagonCoupe.notifyDataSetChanged();
                    }
                });
        ((Button) dialog.findViewById(R.id.DialogSetPlaceSubmit)).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (from == null && to == null) {
                            Toast.makeText(context.getApplicationContext(), NO_ALL, Toast.LENGTH_SHORT).show();
                        } else if (from == null) {
                            Toast.makeText(context.getApplicationContext(), NO_ARRIVAL, Toast.LENGTH_SHORT).show();
                        } else if (to == null) {
                            Toast.makeText(context.getApplicationContext(), NO_DEPARTURE, Toast.LENGTH_SHORT).show();
                        } else {
                            place.setFrom(from);
                            place.setTo(to);
                            place.setWithAnimal(animal.isChecked());
                            place.setWithLinen(linen.isChecked());
                            place.setWithChildren(children.isChecked());
                            Place.SetPlace(place);
                        }
                        adapterWagonCoupe.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
    }

    public void showDialog(Place place) {
        this.place = place;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void closeDialog() {
        dialog.dismiss();
    }
}