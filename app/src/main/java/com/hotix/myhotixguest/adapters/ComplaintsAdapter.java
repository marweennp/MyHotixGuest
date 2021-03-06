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
import com.hotix.myhotixguest.models.Complaint;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;


public class ComplaintsAdapter extends ArrayAdapter<Complaint> {

    Context mContext;
    private ArrayList<Complaint> dataSet;

    public ComplaintsAdapter(ArrayList<Complaint> data, Context context) {
        super(context, R.layout.item_complaint_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Complaint dataModel = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_complaint_row, parent, false);
            viewHolder.complaint_title = (TextView) convertView.findViewById(R.id.complaint_title);
            viewHolder.complaint_text = (TextView) convertView.findViewById(R.id.complaint_text);
            viewHolder.complaint_date = (TextView) convertView.findViewById(R.id.complaint_row_date);
            viewHolder.complaint_state = (TextView) convertView.findViewById(R.id.complaint_state);
            viewHolder.complaint_color_layout = (RelativeLayout) convertView.findViewById(R.id.complaint_color_layout);
            viewHolder.complaint_color_text_layout = (RelativeLayout) convertView.findViewById(R.id.complaint_color_text_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.complaint_title.setText(dataModel.getObject());
        viewHolder.complaint_text.setText(dataModel.getDescription());
        viewHolder.complaint_date.setText(Html.fromHtml(dateColored(dataModel.getDateCreation(), "", "#03A9F4", "dd/MM/yyyy", true)));

        if (dataModel.getTraite()) {
            viewHolder.complaint_state.setText(R.string.compaint_treated);
            viewHolder.complaint_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_500));
            viewHolder.complaint_color_text_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_500));
        } else {
            viewHolder.complaint_state.setText(R.string.compaint_waiting);
            viewHolder.complaint_color_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_500));
            viewHolder.complaint_color_text_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_500));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView complaint_title;
        TextView complaint_text;
        TextView complaint_date;
        TextView complaint_state;

        RelativeLayout complaint_color_text_layout;
        RelativeLayout complaint_color_layout;
    }

}

