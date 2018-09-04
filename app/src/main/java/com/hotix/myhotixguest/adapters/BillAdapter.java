package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Bill;

import java.util.ArrayList;

public class BillAdapter extends ArrayAdapter<Bill> {

    private ArrayList<Bill> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView bill_transaction_title;
        TextView bill_transaction_sum;
        TextView bill_transaction_date;
        TextView bill_transaction_tva;
    }

    public BillAdapter(ArrayList<Bill> data, Context context) {
        super(context, R.layout.bill_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Bill dataModel = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bill_row_item, parent, false);
            viewHolder.bill_transaction_title = (TextView) convertView.findViewById(R.id.bill_transaction_title);
            viewHolder.bill_transaction_sum = (TextView) convertView.findViewById(R.id.bill_transaction_sum);
            viewHolder.bill_transaction_date = (TextView) convertView.findViewById(R.id.bill_transaction_date);
            viewHolder.bill_transaction_tva = (TextView) convertView.findViewById(R.id.bill_transaction_tva);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.bill_transaction_title.setText(dataModel.getBill_transaction_title());
        viewHolder.bill_transaction_sum.setText(dataModel.getBill_transaction_sum());
        viewHolder.bill_transaction_date.setText(dataModel.getBill_transaction_date());
        viewHolder.bill_transaction_tva.setText(dataModel.getBill_transaction_tva());

        // Return the completed view to render on screen
        return convertView;
    }

}

