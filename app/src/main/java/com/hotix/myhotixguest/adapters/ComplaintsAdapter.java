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
import com.hotix.myhotixguest.models.Complaint;

import java.util.ArrayList;


public class ComplaintsAdapter extends ArrayAdapter<Complaint> {

    private ArrayList<Complaint> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView complaint_title;
        TextView complaint_text;
        TextView complaint_date;
        TextView complaint_state;

        RelativeLayout complaint_color_text_layout;
        RelativeLayout complaint_color_layout;

    }

    public ComplaintsAdapter(ArrayList<Complaint> data, Context context) {
        super(context, R.layout.complaint_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Complaint dataModel = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.complaint_row_item, parent, false);
            viewHolder.complaint_title = (TextView) convertView.findViewById(R.id.complaint_title);
            viewHolder.complaint_text = (TextView) convertView.findViewById(R.id.complaint_text);
            viewHolder.complaint_date = (TextView) convertView.findViewById(R.id.complaint_date);
            viewHolder.complaint_state = (TextView) convertView.findViewById(R.id.complaint_state);
            viewHolder.complaint_color_layout = (RelativeLayout) convertView.findViewById(R.id.complaint_color_layout);
            viewHolder.complaint_color_text_layout = (RelativeLayout) convertView.findViewById(R.id.complaint_color_text_layout);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.complaint_title.setText(dataModel.getComplaint_title());
        viewHolder.complaint_text.setText(dataModel.getComplaint_text());
        viewHolder.complaint_date.setText(dataModel.getComplaint_date());
        viewHolder.complaint_state.setText(dataModel.getComplaint_state());

        if (dataModel.getComplaint_state().equals("treated")) {
            viewHolder.complaint_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_500));
            viewHolder.complaint_color_text_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_500));
        } else {
            viewHolder.complaint_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_500));
            viewHolder.complaint_color_text_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_500));
        }


        // Return the completed view to render on screen
        return convertView;
    }

}

