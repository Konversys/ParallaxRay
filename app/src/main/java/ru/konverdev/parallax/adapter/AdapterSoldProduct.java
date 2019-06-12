package ru.konverdev.parallax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.model.classes.Product;

public class AdapterSoldProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String SOLD = "Продано ";
    private static final String OF = " из ";

    private List<Product> items;

    public void ResetItems() {
        this.items = Product.GetProducts(true);
    }

    private Context ctx;
    private AdapterSoldProduct.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int position);
    }

    public void setOnItemClickListener(final AdapterSoldProduct.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterSoldProduct(Context context) {
        this.items = Product.GetProducts(true);
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        public TextView title;
        public TextView price;
        public TextView count;
        public TextView about;
        public TextView sold;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.ItemSimpleProductNumber);
            title = (TextView) v.findViewById(R.id.ItemSimpleProductTitle);
            price = (TextView) v.findViewById(R.id.ItemSimpleProductPrice);
            count = (TextView) v.findViewById(R.id.ItemSimpleProductCount);
            about = (TextView) v.findViewById(R.id.ItemSimpleProductAbout);
            sold = (TextView) v.findViewById(R.id.ItemSimpleProductSelled);
            lyt_parent = (View) v.findViewById(R.id.ItemSimpleProductLyt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sold_product, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterSimpleProduct.OriginalViewHolder) {
            AdapterSimpleProduct.OriginalViewHolder view = (AdapterSimpleProduct.OriginalViewHolder) holder;

            Product item = items.get(position);
            view.number.setText(String.valueOf(item.getId()));
            view.title.setText(item.getTitle());
            view.price.setText(String.valueOf(item.getPrice()));
            view.about.setText(item.getAbout());
            view.count.setText(item.getCount());
            view.sold.setText(SOLD + item.getSold() + OF + item.getTotal());
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}