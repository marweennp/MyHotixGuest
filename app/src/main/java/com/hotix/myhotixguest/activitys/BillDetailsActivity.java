package com.hotix.myhotixguest.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.BillAdapter;
import com.hotix.myhotixguest.models.Bill;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView ListView
    @BindView(R.id.bill_list)
    ListView listView;

    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<Bill> dataModels;
    private static BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View header = (View)getLayoutInflater().inflate(R.layout.bill_header,null);
        View footer = (View)getLayoutInflater().inflate(R.layout.bill_footer,null);
        listView.addHeaderView(header);
        listView.addFooterView(footer);

        dataModels= new ArrayList<>();

        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "100.0 DT","TVA 0.0%"));
        dataModels.add(new Bill("Frais de service", "01/01/2018", "100.0 DT","TVA 0.0%"));

        adapter= new BillAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);


    }
}
