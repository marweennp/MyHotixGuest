package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.CartItemsAdapter;
import com.hotix.myhotixguest.adapters.OrderAdapter;
import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.ResponseMsg;
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

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_CART;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_INFOS;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_SLIDES;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class OrderDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.cart_items_list)
    ListView listView;
    @BindView(R.id.order_details_total_actv)
    AppCompatTextView order_details_total;
    @BindView(R.id.order_details_products_count_tv)
    AppCompatTextView order_details_products_count;


    private ArrayList<CartItem> dataModels;
    private CartItem cartItem;
    private CartItemsAdapter adapter;
    private Double price = 0.0;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        setHeader();
        setListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                cartItem = dataModels.get(position);
            }
        });

    }

    @OnClick(R.id.order_details_footer)
    public void confirmOrder() {
        sendCommande();
    }

    private void setHeader(){

        price = 0.0;
        for (CartItem obj : GLOBAL_ORDER.getDetails()) {
            price += obj.getPrixUnitaire() * obj.getQuantite();
        }
        order_details_total.setText("Total : "+(formatter.format(price) + " DT"));
        order_details_products_count.setText(String.valueOf(GLOBAL_ORDER.getDetails().size()));

    }

    private void setListView(){

        dataModels = new ArrayList<>();
        dataModels =GLOBAL_ORDER.getDetails();

        adapter = new CartItemsAdapter(dataModels, this);
        listView.setAdapter(adapter);

    }

    public void sendCommande() {

        final String content_type = "application/json";

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.sendCommandeQuery(content_type, GLOBAL_ORDER);

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loding...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<ResponseMsg>() {
            @Override
            public void onResponse(Call<ResponseMsg> call, Response<ResponseMsg> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    ResponseMsg msg = response.body();
                    if (!msg.getIsOk()) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.something_wrong));

                    } else {
                        GLOBAL_CART.clear();
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseMsg> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

}
