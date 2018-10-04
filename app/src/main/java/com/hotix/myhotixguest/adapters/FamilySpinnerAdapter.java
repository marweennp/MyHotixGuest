package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Famille;

import java.util.ArrayList;

public class FamilySpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Famille> familles;
    LayoutInflater inflter;

    public FamilySpinnerAdapter(Context applicationContext, ArrayList<Famille> familles) {
        this.context = applicationContext;
        this.familles = familles;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return familles.size();
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
        AppCompatTextView names = (AppCompatTextView) view.findViewById(R.id.spinner_row_tv);
        icon.setImageResource(R.drawable.ic_filter_list_white_24);
        names.setText(familles.get(i).getName());
        return view;
    }
}