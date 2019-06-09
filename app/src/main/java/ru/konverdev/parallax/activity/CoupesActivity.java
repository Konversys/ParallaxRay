package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterCoupe;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Wagon;
import ru.konverdev.parallax.utils.tools.EnvHandler;

public class CoupesActivity extends AppCompatActivity {
    private static final String NO_DIRECTION = "Направление не выбрано";
    private static final String NO_WAGON = "Вагон не выбран";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupes);
        EnvHandler.Init(this, "Вагон");
        initComponent();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initComponent(){
        RecyclerView adapterRecycler = (RecyclerView) findViewById(R.id.AcCoupesItems);
        adapterRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapterRecycler.setHasFixedSize(true);

        if (Direction.GetSelectedDirection() == null){
            CustomToast.SnackBarIconError(this, NO_DIRECTION);
            return;
        }
        if (Wagon.GetWagon() == null){
            CustomToast.SnackBarIconError(this, NO_WAGON);
            return;
        }
        Wagon wagon = Wagon.GetWagon();
        AdapterCoupe adapterWagonCoupe = new AdapterCoupe(this);
        adapterRecycler.setAdapter(adapterWagonCoupe);
    }
}
