package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterProduct;
import ru.konverdev.parallax.utils.tools.EnvHandler;

public class ProductsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        EnvHandler.Init(this, "Товары");
        initComponent();
    }

    private void initComponent() {
        RecyclerView productsView = (RecyclerView) findViewById(R.id.AcProductsItems);
        productsView.setLayoutManager(new LinearLayoutManager(this));
        productsView.setHasFixedSize(true);

        //set data and list adapter
        AdapterProduct mAdapter = new AdapterProduct(this);
        productsView.setAdapter(mAdapter);
    }
}
