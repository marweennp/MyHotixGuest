package com.hotix.myhotixguest.activites;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.BillAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.helpers.Utils;
import com.hotix.myhotixguest.models.BillData;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.LigneFacture;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.getColor;
import static com.hotix.myhotixguest.helpers.Utils.dateBefore;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.round;
import static com.hotix.myhotixguest.helpers.Utils.sameDate;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class BillDetailsActivity extends AppCompatActivity {

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

    @BindView(R.id.rl_bill_header)
    RelativeLayout rlBillHeader;
    //
    @BindView(R.id.bill_header_total_ttc_text)
    AppCompatTextView billHeadTotalTTC;
    @BindView(R.id.bill_date_text)
    AppCompatTextView billDate;
    @BindView(R.id.bill_owner_text)
    AppCompatTextView billOwner;
    @BindView(R.id.bill_number_text)
    AppCompatTextView billNumber;
    @BindView(R.id.bill_header_total_ttc_title_text)
    AppCompatTextView headDevise;
    @BindView(R.id.chb_bill_header_whole_stay)
    AppCompatCheckBox chbWholeStay;

    // Session Manager Class
    Session session;
    Facture facture;
    ArrayList<LigneFacture> l_factures;
    //**************************************************
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private AppCompatTextView billTotalTTC;
    private AppCompatTextView footDevise;
    private View footer;
    private String billId;
    private String billAn;
    private String dateIn;
    private String dateOut;
    private boolean histo;
    private Drawable mIconTwo;
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

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            billId = extras.getString("billId");
            billAn = extras.getString("billAn");
            dateIn = extras.getString("dateIn");
            dateOut = extras.getString("dateOut");
            histo = extras.getBoolean("histo");
        }

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.bill_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        footer = (View) getLayoutInflater().inflate(R.layout.content_bill_footer, null);
        billTotalTTC = (AppCompatTextView) footer.findViewById(R.id.bill_total_ttc_text);
        footDevise = (AppCompatTextView) footer.findViewById(R.id.bill_total_ttc_title_text);

        //Whole Stay True/False
        chbWholeStay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                showData(facture, bChecked);
            }
        });

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
        setBaseUrl(this);
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

    //**********************************************************************************************

    public void showData(Facture fact, boolean all) {

        l_factures = new ArrayList<>();
        l_factures.clear();
        Double total = 0.000;
        formatter.setMinimumFractionDigits(facture.getDeviseDecimal());

        billOwner.setText(session.getNom() + " " + session.getPrenom());
        billNumber.setText(facture.getId() + "-" + facture.getAnnee());

        headDevise.setText(facture.getDevise());
        footDevise.setText(facture.getDevise());

        billDate.setText(Html.fromHtml(dateColored(dateIn, "#9E9E9E", "#FFFFFF", "yyyy-MM-dd'T'hh:mm:ss", false) + " - " + dateColored(dateOut, "#9E9E9E", "#FFFFFF", "yyyy-MM-dd'T'hh:mm:ss", true)));

        for (LigneFacture lf : fact.getLignesFacture()) {

            if ((!all)) {

                if (!lf.getAuto() || dateBefore(dateFormater(lf.getDate(), Utils.FormatsDate.F2.getFormat(), Utils.FormatsDate.F1.getFormat()), dateFormater(fact.getDateFront(), Utils.FormatsDate.F1.getFormat(), Utils.FormatsDate.F1.getFormat()))) {

                    l_factures.add(lf);
                    total += Double.valueOf(lf.getMontant());
                }

            } else {
                l_factures.add(lf);
                total += Double.valueOf(lf.getMontant());
            }
        }

        total = round(total, 3);

        listView.removeFooterView(footer);
        if (l_factures.size() > 0){
            BillAdapter listAdapter = new BillAdapter(this, l_factures, fact.getDateFront());
            listView.setAdapter(listAdapter);
            listView.addFooterView(footer);
        }

        billHeadTotalTTC.setText(formatter.format(total));
        billTotalTTC.setText(formatter.format(total));

        if (total > 0){
            billHeadTotalTTC.setTextColor(getResources().getColor( R.color.white));
            billTotalTTC.setTextColor(getResources().getColor( R.color.white));
        }else if (total < 0){
            billHeadTotalTTC.setTextColor(getResources().getColor( R.color.green_500));
            billTotalTTC.setTextColor(getResources().getColor( R.color.green_500));
        }else{
            billHeadTotalTTC.setTextColor(getResources().getColor( R.color.blue_500));
            billTotalTTC.setTextColor(getResources().getColor( R.color.blue_500));
        }

    }

    public ArrayList<BillData> sortData(ArrayList<LigneFacture> lignesFacture) {

        ArrayList<BillData> data = new ArrayList<BillData>();

        for (LigneFacture lf : lignesFacture) {

            if (data.size() < 1) {
                BillData bDat = new BillData();
                bDat.setDate(lf.getDate());
                bDat.setTotal(lf.getMontant());
                ArrayList<LigneFacture> lfList = new ArrayList<LigneFacture>();
                lfList.add(lf);
                bDat.setLignesFacture(lfList);
                data.add(bDat);

            } else {
                boolean exist = false;
                for (BillData bd : data) {

                    if (sameDate(dateFormater(lf.getDate(), Utils.FormatsDate.F2.getFormat(), Utils.FormatsDate.F1.getFormat()), dateFormater(bd.getDate(), Utils.FormatsDate.F2.getFormat(), Utils.FormatsDate.F1.getFormat()))) {
                        Double total = bd.getTotal();
                        bd.setTotal(total += lf.getMontant());
                        ArrayList<LigneFacture> lfList = bd.getLignesFacture();
                        lfList.add(lf);
                        bd.setLignesFacture(lfList);
                        exist = true;
                    }
                }

                if (!exist) {
                    BillData bDat = new BillData();
                    bDat.setDate(lf.getDate());
                    bDat.setTotal(lf.getMontant());
                    ArrayList<LigneFacture> lfList = new ArrayList<LigneFacture>();
                    lfList.add(lf);
                    bDat.setLignesFacture(lfList);
                    data.add(bDat);
                }
            }

        }
        return data;
    }

    //**********************************************************************************************
    private void loadeBills() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<Facture> billCall = service.getFactureQuery(billId, billAn);

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rlBillHeader.setVisibility(View.GONE);


        billCall.enqueue(new Callback<Facture>() {
            @Override
            public void onResponse(Call<Facture> call, Response<Facture> response) {
                progressView.setVisibility(View.GONE);
                rlBillHeader.setVisibility(View.VISIBLE);
                if (response.raw().code() == 200) {

                    facture = response.body();
                    showData(facture, true);

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<Facture> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                rlBillHeader.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                listView.setEmptyView(findViewById(R.id.empty_list_view));
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }
}
