package ru.konverdev.parallax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.model.classes.Product;

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Product> items;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterProduct(Context context) {
        this.items = Product.GetProducts(false);
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        public TextView title;
        public TextView price;
        public TextView category;
        public TextView count;
        public TextView about;
        public ImageView image;

        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.ItemProductImage);
            number = (TextView) v.findViewById(R.id.ItemProductNumber);
            title = (TextView) v.findViewById(R.id.ItemProductTitle);
            price = (TextView) v.findViewById(R.id.ItemProductPrice);
            category = (TextView) v.findViewById(R.id.ItemProductCategory);
            count = (TextView) v.findViewById(R.id.ItemProductCount);
            about = (TextView) v.findViewById(R.id.ItemProductAbout);
            lyt_parent = (View) v.findViewById(R.id.ItemProductLytParent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Product product = items.get(position);
            view.number.setText(String.valueOf(product.getId()));
            if (product.getTitle() != null)
                view.title.setText(product.getTitle());
            else
                view.title.setText("");

            if (product.getCount() != null)
                view.count.setText(product.getCount());
            else
                view.count.setText("");

            if (product.getCategory() != null)
                view.category.setText(product.getCategory());
            else
                view.category.setText("");

            if (product.getAbout() != null)
                view.about.setText(product.getAbout());
            else
                view.about.setText("");

            view.price.setText(String.valueOf(product.getPrice()));

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