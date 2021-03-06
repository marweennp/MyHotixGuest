package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Sejour;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.calculateDaysBetween;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;

public class HistoryAdapter extends ArrayAdapter<Sejour> {

    Context mContext;
    private ArrayList<Sejour> dataSet;

    public HistoryAdapter(ArrayList<Sejour> data, Context context) {
        super(context, R.layout.item_stay_row, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Sejour dataModel = getItem(position);

        HistoryAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new HistoryAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_stay_row, parent, false);

            viewHolder.stay_title = (TextView) convertView.findViewById(R.id.stay_title);
            viewHolder.stay_sub_title = (TextView) convertView.findViewById(R.id.stay_sub_title);
            viewHolder.stay_date = (TextView) convertView.findViewById(R.id.stay_date);
            viewHolder.stay_nights = (TextView) convertView.findViewById(R.id.stay_nights);
            viewHolder.stay_room = (TextView) convertView.findViewById(R.id.stay_room);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HistoryAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.stay_title.setText(dataModel.getArrangement());
        viewHolder.stay_sub_title.setText("" + dataModel.getTypeChambre());
        viewHolder.stay_date.setText(Html.fromHtml(dateColored(dataModel.getDateArrivee(), "", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", false) + " - " + dateColored(dataModel.getDateDepart(), "", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));
        viewHolder.stay_nights.setText(calculateDaysBetween(dataModel.getDateArrivee(), dataModel.getDateDepart()));
        viewHolder.stay_room.setText(mContext.getResources().getString(R.string.room) +" : " + dataModel.getChambre());

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView stay_title;
        TextView stay_sub_title;
        TextView stay_date;
        TextView stay_nights;
        TextView stay_room;
    }

}