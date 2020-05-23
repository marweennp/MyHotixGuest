package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Arrangement;
import com.hotix.myhotixguest.models.RoomType;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getColor;

public class MyRoomAdapter extends BaseAdapter {

    Context context;
    ArrayList<RoomType> mData;
    LayoutInflater inflter;

    public MyRoomAdapter(Context applicationContext, ArrayList<RoomType> mData) {
        this.context = applicationContext;
        this.mData = mData;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_row_item, null);
        AppCompatImageView icon = (AppCompatImageView) view.findViewById(R.id.spinner_row_img);
        icon.setVisibility(View.GONE);
        AppCompatTextView names = (AppCompatTextView) view.findViewById(R.id.spinner_row_tv);
        names.setText(mData.get(i).getRoomTypeName());
        if (mData.get(i).getRoomTypeID() == -1) {
            names.setTextColor(getColor(context, R.color.grey_500));
        } else {
            names.setTextColor(getColor(context, R.color.colorBackground));
        }

        return view;
    }
}