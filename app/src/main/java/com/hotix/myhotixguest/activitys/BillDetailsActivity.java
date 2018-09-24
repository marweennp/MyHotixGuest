package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.BillAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.LignesFacture;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.newDateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class BillDetailsActivity extends AppCompatActivity {

    private static BillAdapter adapter;
    // Butter Knife BindView ListView
    @BindView(R.id.bill_list)
    ListView listView;
    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Session Manager Class
    Session session;
    ArrayList<LignesFacture> l_factures;
    private AppCompatTextView billTotalTTC;
    private AppCompatTextView billHeadTotalTTC;
    private AppCompatTextView billDate;
    private AppCompatTextView billOwner;
    private AppCompatTextView billNumber;
    private String billId;
    private String billAn;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            billId = extras.getString("billId");
            billAn = extras.getString("billAn");
        }

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        l_factures = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View header = (View) getLayoutInflater().inflate(R.layout.list_bill_header, null);
        View footer = (View) getLayoutInflater().inflate(R.layout.list_bill_footer, null);
        billNumber = (AppCompatTextView) header.findViewById(R.id.bill_number_text);
        billOwner = (AppCompatTextView) header.findViewById(R.id.bill_owner_text);
        billDate = (AppCompatTextView) header.findViewById(R.id.bill_date_text);
        billHeadTotalTTC = (AppCompatTextView) header.findViewById(R.id.bill_header_total_ttc_text);
        billTotalTTC = (AppCompatTextView) footer.findViewById(R.id.bill_total_ttc_text);
        listView.addHeaderView(header);
        listView.addFooterView(footer);

        billOwner.setText(session.getNom() + " " + session.getPrenom());
        billDate.setText(newDateFormater(session.getDateArrivee()) + " - " + newDateFormater(session.getDateDepart()));

        loadeBills();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadeBills() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Facture> billCall = service.getFactureQuery(billId, billAn);

        final ProgressDialog progressDialog = new ProgressDialog(BillDetailsActivity.this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        billCall.enqueue(new Callback<Facture>() {
            @Override
            public void onResponse(Call<Facture> call, Response<Facture> response) {
                progressDialog.dismiss();
                if (response.raw().code() == 200) {
                    Facture facture = response.body();
                    l_factures = facture.getLignesFacture();

                    adapter = new BillAdapter(l_factures, getApplicationContext());

                    listView.setAdapter(adapter);

                    billNumber.setText(facture.getId() + "-" + facture.getAnnee());
                    billHeadTotalTTC.setText(formatter.format(facture.getTotalTTC()) + " " + facture.getDevise());
                    billTotalTTC.setText(formatter.format(facture.getTotalTTC()) + " " + facture.getDevise());
                    //billTotalTTC.setText(String.format("%.3f",facture.getTotalTTC()) + " " + facture.getDevise());
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<Facture> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
                //Toast.makeText(getApplicationContext(), "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
