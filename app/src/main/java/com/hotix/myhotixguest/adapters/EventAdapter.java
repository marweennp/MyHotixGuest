package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.Utils.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.dateTowColors;
import static com.hotix.myhotixguest.helpers.Utils.timeFormater;

public class EventAdapter extends ArrayAdapter<Event> {

    Context mContext;
    private ArrayList<Event> dataSet;

    public EventAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.list_event_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event dataModel = getItem(position);

        EventAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new EventAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_event_row_item, parent, false);

            viewHolder.event_row_head_date = (TextView) convertView.findViewById(R.id.event_row_head_date);
            viewHolder.event_row_head_time = (TextView) convertView.findViewById(R.id.event_row_head_time);
            viewHolder.event_row_head_title = (TextView) convertView.findViewById(R.id.event_row_head_title);
            viewHolder.event_row_head_desc = (TextView) convertView.findViewById(R.id.event_row_head_desc);
            viewHolder.event_row_price = (TextView) convertView.findViewById(R.id.event_row_price);
            viewHolder.event_row_cat = (TextView) convertView.findViewById(R.id.event_row_cat);

            viewHolder.event_row_image = (ImageView) convertView.findViewById(R.id.event_row_image);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.event_row_head_date.setText(Html.fromHtml(dateTowColors(dateFormater(dataModel.getDateDebut()), getContext())));
        viewHolder.event_row_head_time.setText(timeFormater("19:06:00"));
        viewHolder.event_row_head_title.setText(dataModel.getNom());
        viewHolder.event_row_head_desc.setText(dataModel.getDescription());

        viewHolder.event_row_cat.setText(dataModel.getCategorie());

        if (!(dataModel.getPrix() == 0)) {
            viewHolder.event_row_price.setTextColor(getColor(getContext(), R.color.colorBackground));
            viewHolder.event_row_price.setText(dataModel.getPrix() + " DT");
        } else {
            viewHolder.event_row_price.setTextColor(getColor(getContext(), R.color.green_700));
            viewHolder.event_row_price.setText("FREE ");
        }

        if (dataModel.getImage().equals("")) {
            Picasso.get().load(BASE_URL + "/Android/pics_guest/Events/0.png").fit().placeholder(R.drawable.activites).into(viewHolder.event_row_image);
        } else {
            Picasso.get().load(BASE_URL + "/Android/pics_guest/Events/" + dataModel.getImage()).fit().placeholder(R.drawable.activites).into(viewHolder.event_row_image);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView event_row_head_date;
        TextView event_row_head_time;
        TextView event_row_head_title;
        TextView event_row_head_desc;
        TextView event_row_price;
        TextView event_row_cat;
        ImageView event_row_image;
    }

}

