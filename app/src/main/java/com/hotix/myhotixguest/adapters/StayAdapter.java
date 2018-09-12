package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Sejour;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.calculateDaysBetween;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class StayAdapter extends ArrayAdapter<Sejour> {

    Context mContext;
    private ArrayList<Sejour> dataSet;

    public StayAdapter(ArrayList<Sejour> data, Context context) {
        super(context, R.layout.stay_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Sejour dataModel = getItem(position);

        StayAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new StayAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stay_row_item, parent, false);

            viewHolder.stay_title = (TextView) convertView.findViewById(R.id.stay_title);
            viewHolder.stay_sub_title = (TextView) convertView.findViewById(R.id.stay_sub_title);
            viewHolder.stay_date = (TextView) convertView.findViewById(R.id.stay_date);
            viewHolder.stay_nights = (TextView) convertView.findViewById(R.id.stay_nights);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StayAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.stay_title.setText(dataModel.getArrangement());
        viewHolder.stay_sub_title.setText("" + dataModel.getTypeChambre());
        viewHolder.stay_date.setText(dateFormater(dataModel.getDateArrivee()) + "->" + dateFormater(dataModel.getDateDepart()));
        viewHolder.stay_nights.setText(calculateDaysBetween(dataModel.getDateArrivee(), dataModel.getDateDepart()));

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView stay_title;
        TextView stay_sub_title;
        TextView stay_date;
        TextView stay_nights;
    }

}