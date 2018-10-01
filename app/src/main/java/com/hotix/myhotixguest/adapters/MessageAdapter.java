package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Message;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater1;
import static com.hotix.myhotixguest.helpers.Utils.timeFormater2;

public class MessageAdapter extends ArrayAdapter<Message> {

    Context mContext;
    private ArrayList<Message> dataSet;

    public MessageAdapter(ArrayList<Message> data, Context context) {
        super(context, R.layout.list_message_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message dataModel = getItem(position);

        MessageAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new MessageAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_message_row_item, parent, false);
            viewHolder.message_title = (TextView) convertView.findViewById(R.id.message_row_title);
            viewHolder.message_text = (TextView) convertView.findViewById(R.id.message_row_text);
            viewHolder.message_date = (TextView) convertView.findViewById(R.id.message_row_date);
            viewHolder.message_time = (TextView) convertView.findViewById(R.id.message_row_time);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MessageAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.message_title.setText(mContext.getString(R.string.message_from) + dataModel.getFrom());
        viewHolder.message_text.setText(dataModel.getDetails());
        viewHolder.message_date.setText(dateFormater1(dataModel.getDate()));
        viewHolder.message_time.setText(timeFormater2(dataModel.getDate()));


        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView message_title;
        TextView message_text;
        TextView message_date;
        TextView message_time;

    }

}
