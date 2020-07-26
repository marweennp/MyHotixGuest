package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.OrderAdapter;
import com.hotix.myhotixguest.adapters.ReveilAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.OrdersResponse;
import com.hotix.myhotixguest.models.Reveil;
import com.hotix.myhotixguest.models.ReveilsResponse;
import com.hotix.myhotixguest.models.SuccessResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ReveilActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.reveils_list_pull_to_refresh)
    PullRefreshLayout pullLayout;
    @BindView(R.id.reveils_list)
    ListView listView;
    @BindView(R.id.reveils_floatingActionButton_add)
    FloatingActionButton floatingActionButton;

    private ArrayList<Reveil> dataModels;
    private Reveil reveil;
    private ReveilAdapter adapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveil);
        ButterKnife.bind(this);

        // Session Manager
        session = new Session(getApplicationContext());

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_cart_grey_512, getApplicationContext().getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, getApplicationContext().getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_cart_grey_512, getApplicationContext().getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, getApplicationContext().getTheme());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.reveil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataModels = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), NewReveilActivity.class);
                startActivity(i);
            }
        });

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeReveils();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                reveil = dataModels.get(position);
                //reveilDetailsDialog(reveil);
            }
        });

        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeReveils();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadeReveils();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /************************************(  Loade Reveils  )****************************************/
    public void loadeReveils() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ReveilsResponse> userCall = service.getReveilsQuery(session.getHotelId().toString(), session.getResaId().toString(), session.getResaGroupeId().toString(), session.getResaPaxId().toString());
        Log.e("MARWEN", "Request" + userCall.request());
        pullLayout.setRefreshing(false);
        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ReveilsResponse>() {
            @Override
            public void onResponse(Call<ReveilsResponse> call, Response<ReveilsResponse> response) {

                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.GONE);

                Log.e("MARWEN", "onResponse" + response.raw().code());

                if (response.raw().code() == 200) {

                    ReveilsResponse _Response = response.body();

                    if (_Response.getSuccess()) {

                        dataModels = _Response.getReveils();

                        adapter = new ReveilAdapter(dataModels, getApplicationContext());
                        listView.setAdapter(adapter);

                        emptyListIcon.setImageDrawable(mIconOne);
                        emptyListText.setText(R.string.no_alarm_to_show);
                        listView.setEmptyView(emptyListView);

                    }
                    else {
                        showSnackbar(findViewById(android.R.id.content), _Response.getMessage());
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ReveilsResponse> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    /**********************************************************************************************/

    private void reveilDetailsDialog(Order order) {

//        ArrayList<CartItem> carts = new ArrayList<>();
//
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//
//        View mView = getLayoutInflater().inflate(R.layout.dialog_order_details, null);
//
//        LinearLayoutCompat container = (LinearLayoutCompat) mView.findViewById(R.id.order_details_container);
//
//        AppCompatTextView dateTv = (AppCompatTextView) mView.findViewById(R.id.order_details_date);
//        AppCompatTextView timeTv = (AppCompatTextView) mView.findViewById(R.id.order_details_time);
//        AppCompatTextView totalTv = (AppCompatTextView) mView.findViewById(R.id.order_details_total);
//
//        AppCompatButton okBt = (AppCompatButton) mView.findViewById(R.id.order_details_btn_ok);
//
//        Double price = 0.0;
//        carts = order.getDetails();
//        for (CartItem obj : carts) {
//            price += obj.getPrixUnitaire() * obj.getQuantite();
//
//            View orderRow = getLayoutInflater().inflate(R.layout.item_order_details_row, null);
//            AppCompatTextView quantite = (AppCompatTextView) orderRow.findViewById(R.id.order_details_quantite);
//            AppCompatTextView u_name = (AppCompatTextView) orderRow.findViewById(R.id.order_details_u_name);
//            AppCompatTextView u_price = (AppCompatTextView) orderRow.findViewById(R.id.order_details_u_price);
//
//            quantite.setText(obj.getQuantite()+" X ");
//            u_name.setText(obj.getProduitName().trim());
//            u_price.setText(formatter.format(obj.getPrixUnitaire()));
//            container.addView(orderRow);
//        }
//
//        totalTv.setText(getString(R.string.total) +" "+ formatter.format(price) +" DT");
//        dateTv.setText(dateFormater(order.getDate(), "yyyyMMdd hh:mm", "dd MMM yyyy"));
//        timeTv.setText(dateFormater(order.getDate(), "yyyyMMdd hh:mm", "hh:mm"));
//
//        mBuilder.setView(mView);
//        mBuilder.setCancelable(false);
//        final AlertDialog dialog = mBuilder.create();
//        dialog.show();
//
//        okBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

    }
}
