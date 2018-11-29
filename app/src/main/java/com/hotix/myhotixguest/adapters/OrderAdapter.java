package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Order;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class OrderAdapter extends ArrayAdapter<Order> {
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;
    Context mContext;
    private ArrayList<Order> dataSet;
    private Double price = 0.0;
    private ArrayList<CartItem> carts ;

    public OrderAdapter(ArrayList<Order> data, Context context) {
        super(context, R.layout.item_order_row, data);
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

        carts = new ArrayList<>();

        Order dataModel = getItem(position);

        OrderAdapter.ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new OrderAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_order_row, parent, false);

            viewHolder.order_total = (TextView) convertView.findViewById(R.id.order_row_total);
            viewHolder.order_date = (TextView) convertView.findViewById(R.id.order_row_date);
            viewHolder.order_time = (TextView) convertView.findViewById(R.id.order_row_time);
            viewHolder.order_state = (TextView) convertView.findViewById(R.id.order_row_state);
            viewHolder.order_cart = (TextView) convertView.findViewById(R.id.order_row_cart);

            viewHolder.order_color_layout = (RelativeLayout) convertView.findViewById(R.id.order_row_color_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderAdapter.ViewHolder) convertView.getTag();
        }


        price = 0.0;
        carts = dataModel.getDetails();
        for (CartItem obj : carts) {
            price += obj.getPrixUnitaire() * obj.getQuantite();
        }

        viewHolder.order_total.setText(mContext.getString(R.string.total) + formatter.format(price) +" DT");
        viewHolder.order_date.setText(Html.fromHtml(dateColored(dataModel.getDate(), "", "", "yyyyMMdd hh:mm", true)));
        viewHolder.order_time.setText(dateFormater(dataModel.getDate(), "yyyyMMdd hh:mm", "HH:mm"));
        viewHolder.order_cart.setText(dataModel.getDetails().size()+"");

        if (dataModel.getEtat() == 3) {
            viewHolder.order_state.setText("done");
            viewHolder.order_state.setTextColor(ContextCompat.getColor(mContext, R.color.green_500));
            viewHolder.order_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_500));
        } else if (dataModel.getEtat() == 2) {
            viewHolder.order_state.setText("processing");
            viewHolder.order_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue_500));
            viewHolder.order_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_500));
        } else {
            viewHolder.order_state.setText("waiting");
            viewHolder.order_state.setTextColor(ContextCompat.getColor(mContext, R.color.grey_500));
            viewHolder.order_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_500));
        }




        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView order_total;
        TextView order_date;
        TextView order_time;
        TextView order_state;
        TextView order_cart;

        RelativeLayout order_color_layout;

    }


}

