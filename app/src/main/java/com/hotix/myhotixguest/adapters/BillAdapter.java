package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.LignesFacture;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class BillAdapter extends ArrayAdapter<LignesFacture> {

    Context mContext;
    private ArrayList<LignesFacture> dataSet;

    public BillAdapter(ArrayList<LignesFacture> data, Context context) {
        super(context, R.layout.list_bill_row_item, data);
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
            convertView = inflater.inflate(R.layout.list_bill_row_item, parent, false);
            viewHolder.bill_transaction_title = (TextView) convertView.findViewById(R.id.bill_transaction_title);
            viewHolder.bill_transaction_sum = (TextView) convertView.findViewById(R.id.bill_transaction_sum);
            viewHolder.bill_transaction_date = (TextView) convertView.findViewById(R.id.bill_transaction_date);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.bill_transaction_title.setText(dataModel.getDescription());
        viewHolder.bill_transaction_sum.setText(""+dataModel.getMontant().toString());
        viewHolder.bill_transaction_date.setText(dateFormater(dataModel.getDate()));

        if (!(dataModel.getModePaiement() == 0)) {
            viewHolder.bill_transaction_sum.setTextColor(getColor(getContext(), R.color.green_500));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView bill_transaction_title;
        TextView bill_transaction_sum;
        TextView bill_transaction_date;
    }

}

