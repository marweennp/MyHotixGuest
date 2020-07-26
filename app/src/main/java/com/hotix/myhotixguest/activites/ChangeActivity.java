package com.hotix.myhotixguest.activites;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.ChangeAdapter;
import com.hotix.myhotixguest.adapters.OrderAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.ChangeCorseData;
import com.hotix.myhotixguest.models.DeviseChange;
import com.hotix.myhotixguest.models.DeviseChangeResponse;
import com.hotix.myhotixguest.models.Order;
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

import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ChangeActivity extends AppCompatActivity {

    // Butter Knife BindView
    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.devises_list_pull_to_refresh)
    PullRefreshLayout pullLayout;
    @BindView(R.id.devisess_list)
    ListView listView;

    private ArrayList<DeviseChange> _DataModel;
    private ChangeAdapter adapter;

    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_main_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_msg_tv)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_icon_iv)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_bt)
    AppCompatButton emptyListRefresh;

    private Drawable mIconOne, mIconTwo;

    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);

        // Session Manager
        session = new Session(getApplicationContext());

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_change_grey_512, getApplicationContext().getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, getApplicationContext().getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_change_grey_512, getApplicationContext().getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, getApplicationContext().getTheme());
        }

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.text_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _DataModel = new ArrayList<>();;

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeChangeCorse();
            }
        });

        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeChangeCorse();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadeChangeCorse();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /************************************(  Loade Change Corse  )****************************************/
    public void loadeChangeCorse() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<DeviseChangeResponse> userCall = service.getChangeCorseQuery(session.getHotelId().toString());

        pullLayout.setRefreshing(false);
        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<DeviseChangeResponse>() {
            @Override
            public void onResponse(Call<DeviseChangeResponse> call, Response<DeviseChangeResponse> response) {

                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    DeviseChangeResponse _Response = response.body();
                    if (_Response.getSuccess()) {

                        _DataModel = new ArrayList<DeviseChange>();
                        for (DeviseChange _Dev : _Response.getDeviseChanges()) {
                            if (_Dev.getDevisTauxVente() != 0){
                                _DataModel.add(_Dev);
                            }
                        }

                        adapter = new ChangeAdapter(_DataModel, getApplicationContext());
                        listView.setAdapter(adapter);

                        emptyListIcon.setImageDrawable(mIconOne);
                        emptyListText.setText(R.string.no_orders_to_show);
                        listView.setEmptyView(emptyListView);

                    } else {
                        showSnackbar(findViewById(android.R.id.content), _Response.getMessage());
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<DeviseChangeResponse> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }
}
