package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.CartItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartItemsAdapter extends ArrayAdapter<CartItem> {

    Context mContext;
    private ArrayList<CartItem> dataSet;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public CartItemsAdapter(ArrayList<CartItem> data, Context context) {
        super(context, R.layout.item_cart_details_row, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        CartItem dataModel = getItem(position);

        CartItemsAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new CartItemsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_cart_details_row, parent, false);
            viewHolder.item_quantite = (TextView) convertView.findViewById(R.id.cart_row_quantite);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.cart_row_item_name);
            viewHolder.item_price = (TextView) convertView.findViewById(R.id.cart_row_item_price);
            viewHolder.total_price = (TextView) convertView.findViewById(R.id.cart_row_total_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CartItemsAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.item_quantite.setText(String.valueOf(dataModel.getQuantite()));
        viewHolder.item_name.setText(dataModel.getProduitName().trim());
        viewHolder.item_price.setText(mContext.getString(R.string.price) + formatter.format(dataModel.getPrixUnitaire()));
        viewHolder.total_price.setText(mContext.getString(R.string.total) + formatter.format(dataModel.getPrixUnitaire() * dataModel.getQuantite()));

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView item_quantite;
        TextView item_name;
        TextView item_price;
        TextView total_price;
    }

}

