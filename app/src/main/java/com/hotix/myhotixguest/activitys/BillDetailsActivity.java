package com.hotix.myhotixguest.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.BillAdapter;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.FactureModel;
import com.hotix.myhotixguest.models.LignesFacture;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

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

    private AppCompatTextView billTotalTTC;
    private AppCompatTextView billDate;
    private AppCompatTextView billOwner;
    private AppCompatTextView billNumber;

    ArrayList<LignesFacture> l_factures;
    private static BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        ButterKnife.bind(this);

        l_factures = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View header = (View)getLayoutInflater().inflate(R.layout.bill_header,null);
        View footer = (View)getLayoutInflater().inflate(R.layout.bill_footer,null);
        billNumber = (AppCompatTextView) header.findViewById(R.id.bill_number_text);
        billOwner = (AppCompatTextView) header.findViewById(R.id.bill_owner_text);
        billDate = (AppCompatTextView) header.findViewById(R.id.bill_date_text);
        billTotalTTC = (AppCompatTextView) footer.findViewById(R.id.bill_total_ttc_text);
        listView.addHeaderView(header);
        listView.addFooterView(footer);

        loadeBills();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadeBills() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<FactureModel> billCall = service.getFactureModelQuery("4371","2018");

        billCall.enqueue(new Callback<FactureModel>() {
            @Override
            public void onResponse(Call<FactureModel> call, Response<FactureModel> response) {

                FactureModel factureM = response.body();
                l_factures = factureM.getFacture().getLignesFacture();

                adapter= new BillAdapter(l_factures,getApplicationContext());

                listView.setAdapter(adapter);

                billNumber.setText(factureM.getFacture().getId()+"-"+factureM.getFacture().getAnnee());
                billTotalTTC.setText(""+factureM.getFacture().getTotalTTC()+" "+factureM.getFacture().getDevise());

            }

            @Override
            public void onFailure(Call<FactureModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
