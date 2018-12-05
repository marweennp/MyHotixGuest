package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Civilite;
import com.hotix.myhotixguest.models.Nationalite;

import java.util.ArrayList;

public class NationalitySpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Nationalite> nationalites = new ArrayList<>() ;
    LayoutInflater inflter;

    public NationalitySpinnerAdapter(Context applicationContext, ArrayList<Nationalite> nationalites) {
        this.context = applicationContext;
        this.nationalites = nationalites;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return nationalites.size();
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
        view = inflter.inflate(R.layout.spinner_nationality_row_item, null);
        AppCompatTextView names = (AppCompatTextView) view.findViewById(R.id.spinner_civilite_row_tv);
        names.setText(nationalites.get(i).getName());
        return view;
    }
}