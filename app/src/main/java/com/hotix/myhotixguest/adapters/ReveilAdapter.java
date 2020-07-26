package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.hotix.myhotixguest.R;

import com.hotix.myhotixguest.models.Reveil;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class ReveilAdapter extends ArrayAdapter<Reveil> {
    Context mContext;
    private ArrayList<Reveil> dataSet;

    public ReveilAdapter(ArrayList<Reveil> data, Context context) {
        super(context, R.layout.item_reveil_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Reveil dataModel = getItem(position);

        ReveilAdapter.ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new ReveilAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_reveil_row, parent, false);

            viewHolder.reveil_date = (TextView) convertView.findViewById(R.id.reveil_row_date);
            viewHolder.reveil_time = (TextView) convertView.findViewById(R.id.reveil_row_time);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ReveilAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.reveil_date.setText(Html.fromHtml(dateColored(DateFormat.format("yyyyMMdd hh:mm", dataModel.getReveilDatee()).toString(), "", "", "yyyyMMdd hh:mm", true)));
        viewHolder.reveil_time.setText(dateFormater(DateFormat.format("yyyyMMdd hh:mm", dataModel.getReveilHeure()).toString(), "yyyyMMdd hh:mm", "HH:mm"));

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView reveil_date;
        TextView reveil_time;

    }

}

