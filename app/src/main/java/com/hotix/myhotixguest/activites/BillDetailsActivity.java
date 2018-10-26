package com.hotix.myhotixguest.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class BillDetailsActivity extends AppCompatActivity {

    private static BillAdapter adapter;
    // Butter Knife BindView
    // ListView
    @BindView(R.id.bill_list)
    ListView listView;
    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_btn)
    AppCompatButton emptyListRefresh;

    // Session Manager Class
    Session session;
    ArrayList<LignesFacture> l_factures;
    private AppCompatTextView billTotalTTC;
    private AppCompatTextView billHeadTotalTTC;
    private AppCompatTextView billDate;
    private AppCompatTextView billOwner;
    private AppCompatTextView billNumber;
    private View header;
    private View footer;
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
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        header = (View) getLayoutInflater().inflate(R.layout.content_bill_header, null);
        footer = (View) getLayoutInflater().inflate(R.layout.content_bill_footer, null);
        billNumber = (AppCompatTextView) header.findViewById(R.id.bill_number_text);
        billOwner = (AppCompatTextView) header.findViewById(R.id.bill_owner_text);
        billDate = (AppCompatTextView) header.findViewById(R.id.bill_date_text);
        billHeadTotalTTC = (AppCompatTextView) header.findViewById(R.id.bill_header_total_ttc_text);
        billTotalTTC = (AppCompatTextView) footer.findViewById(R.id.bill_total_ttc_text);

    }

    @OnClick(R.id.empty_list_refresh_btn)
    public void refresh() {
        try {
            loadeBills();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadeBills();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadeBills() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<Facture> billCall = service.getFactureQuery(billId, billAn);

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        billCall.enqueue(new Callback<Facture>() {
            @Override
            public void onResponse(Call<Facture> call, Response<Facture> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {
                    Facture facture = response.body();
                    l_factures = facture.getLignesFacture();

                    adapter = new BillAdapter(l_factures, getApplicationContext());

                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_bills_to_show);
                    listView.setEmptyView(findViewById(R.id.empty_list_view));

                    listView.addHeaderView(header);
                    listView.addFooterView(footer);

                    billOwner.setText(session.getNom() + " " + session.getPrenom());
                    billDate.setText(Html.fromHtml(dateColored(session.getDateArrivee(), "#9E9E9E", "#FFFFFF", "dd/MM/yyyy", true) + " - " + dateColored(session.getDateDepart(), "#9E9E9E", "#FFFFFF", "dd/MM/yyyy", true)));

                    billNumber.setText(facture.getId() + "-" + facture.getAnnee());
                    billHeadTotalTTC.setText(formatter.format(facture.getTotalTTC()) + " " + facture.getDevise());
                    billTotalTTC.setText(formatter.format(facture.getTotalTTC()) + " " + facture.getDevise());
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<Facture> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.ic_dns_white_24);
                listView.setEmptyView(findViewById(R.id.empty_list_view));
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }
}
