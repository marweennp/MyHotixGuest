package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.RestaurantReservation;
import com.hotix.myhotixguest.models.Reveil;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;

public class RestoResaAdapter extends ArrayAdapter<RestaurantReservation> {
    Context mContext;
    private ArrayList<RestaurantReservation> dataSet;

    public RestoResaAdapter(ArrayList<RestaurantReservation> data, Context context) {
        super(context, R.layout.item_resto_resa_row, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RestaurantReservation dataModel = getItem(position);

        RestoResaAdapter.ViewHolder viewHolder;


        if (convertView == null) {

            viewHolder = new RestoResaAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_resto_resa_row, parent, false);

            viewHolder.resa_name = (TextView) convertView.findViewById(R.id.resto_resa_row_name);
            viewHolder.resa_date = (TextView) convertView.findViewById(R.id.resto_resa_row_date);
            viewHolder.resa_time = (TextView) convertView.findViewById(R.id.resto_resa_row_time);
            viewHolder.resa_chair = (TextView) convertView.findViewById(R.id.resto_resa_row_chair);
            viewHolder.resa_etat = (TextView) convertView.findViewById(R.id.resto_resa_row_state);

            viewHolder.resa_color_layout = (RelativeLayout) convertView.findViewById(R.id.resto_resa_row_color_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RestoResaAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.resa_name.setText(dataModel.getRestoName());
        viewHolder.resa_date.setText(Html.fromHtml(dateColored(DateFormat.format("yyyyMMdd HH:mm", dataModel.getDateArrivee()).toString(), "", "", "yyyyMMdd HH:mm", true)));
        viewHolder.resa_time.setText(dateFormater(DateFormat.format("yyyyMMdd HH:mm", dataModel.getHeureArrivee()).toString(), "yyyyMMdd HH:mm", "HH:mm"));
        viewHolder.resa_chair.setText(dataModel.getNbrPAX().toString());
        viewHolder.resa_etat.setText(dataModel.getEtatName());

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView resa_name;
        TextView resa_date;
        TextView resa_time;
        TextView resa_chair;
        TextView resa_etat;

        RelativeLayout resa_color_layout;

    }

}

