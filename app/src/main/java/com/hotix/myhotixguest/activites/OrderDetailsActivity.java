package com.hotix.myhotixguest.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.CartItemsAdapter;
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
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
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
    private int amount = 1;
    private Double total = 0.0;

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
                startAddToCartDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), NewOrderActivity.class);
        startActivity(i);
        finish();

    }

    @OnClick(R.id.order_details_footer)
    public void confirmOrder() {
        try {
            sendCommande();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    private void setHeader() {

        price = 0.0;
        for (CartItem obj : GLOBAL_ORDER.getDetails()) {
            price += obj.getPrixUnitaire() * obj.getQuantite();
        }
        order_details_total.setText("Total : " + (formatter.format(price) + ""));
        order_details_products_count.setText(String.valueOf(GLOBAL_ORDER.getDetails().size()));

    }

    private void setListView() {

        dataModels = new ArrayList<>();
        dataModels = GLOBAL_ORDER.getDetails();

        adapter = new CartItemsAdapter(dataModels, this);
        listView.setAdapter(adapter);

    }

    private void startAddToCartDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        amount = cartItem.getQuantite();
        total = cartItem.getPrixUnitaire() * cartItem.getQuantite();

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_to_cart, null);
        AppCompatTextView dialogTitle = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_header);
        AppCompatTextView prodNameTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_prod_name);
        AppCompatTextView prodPriceTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_prod_price);
        final AppCompatTextView prodTotalTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_total_price);
        final AppCompatTextView prodAmountTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_amount_tv);
        AppCompatImageView addProd = (AppCompatImageView) mView.findViewById(R.id.add_to_cart_dialog_add_icon);
        AppCompatImageView removeProd = (AppCompatImageView) mView.findViewById(R.id.add_to_cart_dialog_remove_icon);
        AppCompatButton confirmBt = (AppCompatButton) mView.findViewById(R.id.btn_Add);
        AppCompatButton removeBt = (AppCompatButton) mView.findViewById(R.id.btn_remove);
        AppCompatButton cancelBt = (AppCompatButton) mView.findViewById(R.id.btn_cancel);

        cancelBt.setVisibility(View.GONE);

        dialogTitle.setText("Cart Item");
        prodNameTv.setText(cartItem.getProduitName());
        prodPriceTv.setText(getString(R.string.price) + " " + formatter.format(cartItem.getPrixUnitaire()) + "");
        prodTotalTv.setText(getString(R.string.total) + " " + formatter.format(total) + "");
        prodAmountTv.setText("" + amount);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount < 10000) {
                    amount++;
                    total = cartItem.getPrixUnitaire() * amount;
                    prodTotalTv.setText(getString(R.string.total) + " " + formatter.format(total) + "");
                    prodAmountTv.setText("" + amount);
                }
            }
        });

        removeProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 1) {
                    amount--;
                    total = cartItem.getPrixUnitaire() * amount;
                    prodTotalTv.setText(getString(R.string.total) + formatter.format(total) + "");
                    prodAmountTv.setText("" + amount);
                }

            }
        });

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = 0;
                for (CartItem obj : GLOBAL_ORDER.getDetails()) {
                    if (obj.getProduit() == cartItem.getProduit()) {
                        if (amount > 0) {
                            obj.setQuantite(amount);
                        } else {
                            GLOBAL_ORDER.getDetails().remove(index);
                        }
                    }
                    index++;
                }

                setHeader();
                setListView();
                dialog.dismiss();
            }
        });

        removeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;
                for (CartItem obj : GLOBAL_ORDER.getDetails()) {
                    if (obj.getProduit() == cartItem.getProduit()) {
                        GLOBAL_ORDER.getDetails().remove(index);
                    }
                    index++;
                }

                setHeader();
                setListView();

                if (GLOBAL_ORDER.getDetails().size() < 1) {
                    finish();
                }

                dialog.dismiss();

            }
        });

    }

    private void startSuccessDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_success, null);
        AppCompatButton okBtn = (AppCompatButton) mView.findViewById(R.id.success_dialog_ok_btn);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }

    public void sendCommande() {

        final String content_type = "application/json";

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
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
                        GLOBAL_ORDER = new Order();
                        startSuccessDialog();
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
