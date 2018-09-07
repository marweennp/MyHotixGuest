package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Utils;
import com.hotix.myhotixguest.models.LignesFacture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillAdapter extends ArrayAdapter<LignesFacture> {

    Context mContext;
    private ArrayList<LignesFacture> dataSet;

    public BillAdapter(ArrayList<LignesFacture> data, Context context) {
        super(context, R.layout.bill_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LignesFacture dataModel = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bill_row_item, parent, false);
            viewHolder.bill_transaction_title = (TextView) convertView.findViewById(R.id.bill_transaction_title);
            viewHolder.bill_transaction_sum = (TextView) convertView.findViewById(R.id.bill_transaction_sum);
            viewHolder.bill_transaction_currency = (TextView) convertView.findViewById(R.id.bill_transaction_currency);
            viewHolder.bill_transaction_date = (TextView) convertView.findViewById(R.id.bill_transaction_date);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.bill_transaction_title.setText(dataModel.getDescription());
        viewHolder.bill_transaction_sum.setText(""+dataModel.getMontant().toString());
        viewHolder.bill_transaction_currency.setText(dataModel.getDevise());
        viewHolder.bill_transaction_date.setText(dateFormater(dataModel.getDate()));

        if (!(dataModel.getModePaiement() == 0)) {
            viewHolder.bill_transaction_sum.setTextColor(Color.parseColor("#4CAF50"));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView bill_transaction_title;
        TextView bill_transaction_sum;
        TextView bill_transaction_currency;
        TextView bill_transaction_date;
    }


    public static String dateFormater(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date result;
        try {
            result = df.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        }catch (Exception e){}

        return "dd MMM yyyy";
    }

}

