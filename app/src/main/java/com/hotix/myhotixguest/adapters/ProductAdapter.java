package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Produit;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Produit> {

    Context mContext;
    private ArrayList<Produit> dataSet;

    public ProductAdapter(ArrayList<Produit> data, Context context) {
        super(context, R.layout.list_product_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Produit dataModel = getItem(position);

        ProductAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ProductAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_product_row_item, parent, false);
            viewHolder.product_name = (TextView) convertView.findViewById(R.id.product_name);
            viewHolder.product_price = (TextView) convertView.findViewById(R.id.product_price);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.product_name.setText(dataModel.getName());
        viewHolder.product_price.setText(dataModel.getPrice());

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView product_name;
        TextView product_price;
    }

}

