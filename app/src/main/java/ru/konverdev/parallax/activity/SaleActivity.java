package ru.konverdev.parallax.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.adapter.AdapterProduct;
import ru.konverdev.parallax.adapter.AdapterSimpleProduct;
import ru.konverdev.parallax.adapter.AdapterSimpleStation;
import ru.konverdev.parallax.adapter.AdapterSoldProduct;
import ru.konverdev.parallax.model.classes.Product;
import ru.konverdev.parallax.utils.tools.EnvHandler;

public class SaleActivity extends AppCompatActivity {
    Button sub;
    Button add;
    Dialog dialogSell;
    NumberPicker sellPicker;

    Dialog dialogAdd;
    TextView totalSum;
    TextView soldSum;
    RecyclerView sellProductsView;
    AdapterSoldProduct mAdapterSoldProducts;

    RecyclerView productsView;
    AdapterProduct mAdapterDiProducts;
    NumberPicker numberPicker;

    WindowManager.LayoutParams lpAdd;
    WindowManager.LayoutParams lpSell;

    Product currentChangingProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        EnvHandler.Init(this, "Продажа");
        initProductList();
        initAddProductDialog();
        initSellProductDialog();
        totalSum = (TextView) findViewById(R.id.AcSoldTotal);
        soldSum = (TextView) findViewById(R.id.AcSoldTotalSelled);

        totalSum.setText(String.valueOf(Product.SumTotalProducts()));
        soldSum.setText(String.valueOf(Product.SumSoldProducts()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AcSoldFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
                dialogAdd.getWindow().setAttributes(lpAdd);
            }
        });
    }

    public void initSellProductDialog() {
        dialogSell = new Dialog(this);

        lpSell = new WindowManager.LayoutParams();
        lpSell.copyFrom(dialogSell.getWindow().getAttributes());
        lpSell.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpSell.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialogSell.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogSell.setContentView(R.layout.dialog_sale_product);
        dialogSell.setCancelable(true);
        sellPicker = (NumberPicker) dialogSell.findViewById(R.id.DialogSaleProductNumber);
        sellPicker.setMinValue(0);
        sellPicker.setMaxValue(500);
        TextView product = (TextView) dialogSell.findViewById(R.id.DialogSaleProductItem);
        sub = (Button) dialogSell.findViewById(R.id.DialogSaleProductSub);
        add = (Button) dialogSell.findViewById(R.id.DialogSaleProductAdd);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentChangingProduct != null) {
                    currentChangingProduct.setSold(currentChangingProduct.getSold() - sellPicker.getValue());
                    Product.UpdateSellProduct(currentChangingProduct);
                    mAdapterSoldProducts.ResetItems();
                    mAdapterSoldProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(Product.SumTotalProducts()));
                    soldSum.setText(String.valueOf(Product.SumSoldProducts()));
                }
                dialogSell.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentChangingProduct != null) {
                    currentChangingProduct.setSold(currentChangingProduct.getSold() + sellPicker.getValue());
                    Product.UpdateSellProduct(currentChangingProduct);
                    mAdapterSoldProducts.ResetItems();
                    mAdapterSoldProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(Product.SumTotalProducts()));
                    soldSum.setText(String.valueOf(Product.SumSoldProducts()));
                }
                dialogSell.dismiss();
            }
        });

        mAdapterSoldProducts.setOnItemClickListener(new AdapterSoldProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Product obj, int position) {
                currentChangingProduct = obj;
                product.setText(obj.getTitle() + " " + obj.getPrice() + " руб");
                sellPicker.setValue(0);
                dialogSell.show();
                dialogSell.getWindow().setAttributes(lpSell);
            }
        });
    }

    private void initProductList() {
        sellProductsView = (RecyclerView) findViewById(R.id.AcSoldProducts);
        sellProductsView.setLayoutManager(new LinearLayoutManager(this));
        sellProductsView.setHasFixedSize(true);

        //set data and list adapter
        mAdapterSoldProducts = new AdapterSoldProduct(this);
        sellProductsView.setAdapter(mAdapterSoldProducts);
    }

    private void initAddProductDialog() {
        dialogAdd = new Dialog(this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogAdd.setContentView(R.layout.dialog_add_product);
        dialogAdd.setCancelable(true);

        lpAdd = new WindowManager.LayoutParams();
        lpAdd.copyFrom(dialogAdd.getWindow().getAttributes());
        lpAdd.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpAdd.height = WindowManager.LayoutParams.MATCH_PARENT;

        productsView = (RecyclerView) dialogAdd.findViewById(R.id.DialogAddProductItems);
        productsView.setLayoutManager(new LinearLayoutManager(this));
        productsView.setHasFixedSize(true);

        mAdapterDiProducts = new AdapterProduct(this);
        productsView.setAdapter(mAdapterDiProducts);

        numberPicker = (NumberPicker) dialogAdd.findViewById(R.id.DialogAddProductNumber);
        numberPicker.setMaxValue(500);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);

        ((ImageButton) dialogAdd.findViewById(R.id.DialogAddProductClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd.dismiss();
            }
        });

        mAdapterDiProducts.setOnItemClickListener(new AdapterProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Product obj, int position) {
                if (numberPicker.getValue() == 0) {
                    Toast.makeText(getApplicationContext(), "Введите количество товара", Toast.LENGTH_SHORT).show();
                } else if (Product.SoldContains(obj)) {
                    Toast.makeText(getApplicationContext(), "Товар уже существует", Toast.LENGTH_SHORT).show();
                } else {
                    Product.AddProduct(new Product(obj, true, numberPicker.getValue()));
                    mAdapterSoldProducts.ResetItems();
                    mAdapterSoldProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(Product.SumTotalProducts()));
                    soldSum.setText(String.valueOf(Product.SumSoldProducts()));
                    numberPicker.setValue(0);
                    dialogAdd.dismiss();
                }
            }
        });
    }
}
