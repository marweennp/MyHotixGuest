package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.BillData;
import com.hotix.myhotixguest.models.LigneFacture;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;

public class BillExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;

    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    private ArrayList<BillData> dataSet;

    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;

    public BillExpandableAdapter(Context context, ArrayList<BillData> data) {
        this._context = context;
        this.dataSet = data;

        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();


        for(BillData BData : dataSet){

            ArrayList<HashMap<String, String>> childArrayList =new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapParent = new HashMap<String, String>();

            mapParent.put("date",BData.getDate());

            for(LigneFacture ligneFacture : BData.getLignesFacture()) {

                HashMap<String, String> mapChild = new HashMap<String, String>();

                mapChild.put("Description",ligneFacture.getDescription());

                childArrayList.add(mapChild);
            }

            childItems.add(childArrayList);
            parentItems.add(mapParent);

        }

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);
    }


    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return (childItems.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderParent viewHolderParent;

        if (convertView == null) {

            viewHolderParent = new ViewHolderParent();

            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.item_bill_group_row, parent, false);

            viewHolderParent.bill_transaction_total = (TextView) convertView.findViewById(R.id.tv_bill_group_total);
            viewHolderParent.bill_transaction_date = (TextView) convertView.findViewById(R.id.tv_bill_group_date);

            BillData billData = dataSet.get(groupPosition);

            viewHolderParent.bill_transaction_total.setText(formatter.format(billData.getTotal()));
            viewHolderParent.bill_transaction_date.setText(Html.fromHtml(dateColored(billData.getDate(), "#757575", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (convertView == null) {

            viewHolderChild = new ViewHolderChild();

            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.item_bill_row, parent, false);

            viewHolderChild.bill_transaction_title = (TextView) convertView.findViewById(R.id.bill_transaction_title);
            viewHolderChild.bill_transaction_sum = (TextView) convertView.findViewById(R.id.bill_transaction_sum);

            LigneFacture ligneFacture = dataSet.get(groupPosition).getLignesFacture().get(childPosition);

            viewHolderChild. bill_transaction_title.setText(ligneFacture.getDescription());
            viewHolderChild.bill_transaction_sum.setText(formatter.format(ligneFacture.getMontant()));
            if (!(ligneFacture.getModePaiement() == 0)) {
                viewHolderChild.bill_transaction_sum.setTextColor(getColor(_context, R.color.green_600));
            }

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private class ViewHolderParent {

        TextView bill_transaction_total;
        TextView bill_transaction_date;
    }

    private class ViewHolderChild {

        TextView bill_transaction_title;
        TextView bill_transaction_sum;
    }

}
