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
import com.hotix.myhotixguest.models.DeviseChange;
import com.hotix.myhotixguest.models.Order;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class ChangeAdapter extends ArrayAdapter<DeviseChange> {
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    Context mContext;
    private ArrayList<DeviseChange> dataSet;

    public ChangeAdapter(ArrayList<DeviseChange> data, Context context) {
        super(context, R.layout.item_change_row, data);
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

        DeviseChange dataModel = getItem(position);

        ChangeAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ChangeAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_change_row, parent, false);

            viewHolder.change_row_iso = (TextView) convertView.findViewById(R.id.change_row_iso);
            viewHolder.change_row_unite = (TextView) convertView.findViewById(R.id.change_row_unite);
            viewHolder.change_row_taux = (TextView) convertView.findViewById(R.id.change_row_taux);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChangeAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.change_row_iso.setText(dataModel.getDeviseCodeISO());
        viewHolder.change_row_unite.setText(mContext.getString(R.string.unite) +" : "+ dataModel.getDeviseUnite() +"");
        viewHolder.change_row_taux.setText( formatter.format(dataModel.getDevisTauxVente()) +"");

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView change_row_iso;
        TextView change_row_unite;
        TextView change_row_taux;
    }


}

