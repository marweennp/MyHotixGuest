package com.hotix.myhotixguest.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.BillAdapter;
import com.hotix.myhotixguest.models.Bill;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.FactureData;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView ListView
    @BindView(R.id.bill_list)
    ListView listView;

    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AppCompatTextView billTotalHt;

    ArrayList<FactureData> factures;
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
        billTotalHt = (AppCompatTextView) footer.findViewById(R.id.bill_total_ht_text);
        listView.addHeaderView(header);
        listView.addFooterView(footer);

        factures = new ArrayList<>();

        loadeBills();

//        dataModels= new ArrayList<>();
//
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "60.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "100.0 DT","TVA 0.0%"));
//        dataModels.add(new Bill("Frais de service", "01/01/2018", "100.0 DT","TVA 0.0%"));
//
//        adapter= new BillAdapter(dataModels,getApplicationContext());
//
//        listView.setAdapter(adapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadeBills() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Facture> userCall = service.getFactureQuery("7201");

        userCall.enqueue(new Callback<Facture>() {
            @Override
            public void onResponse(Call<Facture> call, Response<Facture> response) {

                Double sum = 0.00;

                Facture facture = response.body();

                factures = facture.getData();
                for (FactureData obj : factures) {
                    if(obj.getComment().equals("Fact. Auto")||obj.getComment().equals("Taxe de Séjour")){sum +=obj.getMontant();}

                }

                adapter= new BillAdapter(factures,getApplicationContext());

                listView.setAdapter(adapter);

                billTotalHt.setText(""+sum);
            }

            @Override
            public void onFailure(Call<Facture> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
