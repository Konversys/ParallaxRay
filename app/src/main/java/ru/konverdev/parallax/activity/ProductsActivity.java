package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.utils.tools.EnvHandler;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        EnvHandler.Init(this, "Товары");
    }
}
