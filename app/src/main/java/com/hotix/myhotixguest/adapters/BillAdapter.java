package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Utils;
import com.hotix.myhotixguest.models.LigneFacture;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.Utils.dateBefore;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class BillAdapter extends ArrayAdapter<LigneFacture> {

    Context mContext;
    private ArrayList<LigneFacture> dataSet;
    private String dateFront;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public BillAdapter(Context context, ArrayList<LigneFacture> data, String date) {
        super(context, R.layout.item_bill_row, data);

        this.mContext = context;
        this.dataSet = data;
        this.dateFront = date;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        LigneFacture dataModel = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_bill_row, parent, false);
            viewHolder.bill_transaction_title = (TextView) convertView.findViewById(R.id.bill_transaction_title);
            viewHolder.bill_transaction_sum = (TextView) convertView.findViewById(R.id.bill_transaction_sum);
            viewHolder.bill_transaction_date = (TextView) convertView.findViewById(R.id.bill_transaction_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bill_transaction_title.setText(dataModel.getDescription());
        viewHolder.bill_transaction_sum.setText(formatter.format(dataModel.getMontant()));
        viewHolder.bill_transaction_date.setText(Html.fromHtml(dateColored(dataModel.getDate(), "#757575", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));

        if (!(dataModel.getModePaiement() == 0)) {
            viewHolder.bill_transaction_sum.setTextColor(getColor(getContext(), R.color.green_600));
        } else {

            if (!dataModel.getAuto() || dateBefore(dateFormater(dataModel.getDate(), Utils.FormatsDate.F2.getFormat(), Utils.FormatsDate.F1.getFormat()), dateFormater(dateFront, Utils.FormatsDate.F1.getFormat(), Utils.FormatsDate.F1.getFormat()))) {
                viewHolder.bill_transaction_sum.setTextColor(getColor(getContext(), R.color.light_bg_dark_primary_text));
            } else {
                viewHolder.bill_transaction_sum.setTextColor(getColor(getContext(), R.color.light_bg_dark_disabled_text));
            }
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

