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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class EventAdapter extends ArrayAdapter<Event> {

    Context mContext;
    private ArrayList<Event> dataSet;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    public EventAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.item_event_row, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        formatter = NumberFormat.getCurrencyInstance();
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("TND ");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        Event dataModel = getItem(position);

        EventAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new EventAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_event_row, parent, false);

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

        viewHolder.event_row_head_date.setText(Html.fromHtml(dateColored(dataModel.getDateDebut(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", false)+"-"+dateColored(dataModel.getDateFin(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));
        viewHolder.event_row_head_time.setText(dateFormater(dataModel.getHeure(), "hh:mm:ss", "HH:mm"));
        viewHolder.event_row_head_title.setText(dataModel.getNom());
        viewHolder.event_row_head_desc.setText(dataModel.getDescription());

        viewHolder.event_row_cat.setText(dataModel.getCategorie());

        if (!(dataModel.getPrix() == 0)) {
            viewHolder.event_row_price.setTextColor(getColor(getContext(), R.color.colorPrimaryDark));
            viewHolder.event_row_price.setTextSize(18);
            viewHolder.event_row_price.setText(formatter.format(dataModel.getPrix()));
        } else {
            viewHolder.event_row_price.setTextColor(getColor(getContext(), R.color.green_600));
            viewHolder.event_row_price.setTextSize(26);
            viewHolder.event_row_price.setText(R.string.free);
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

