package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_CART;
import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class OrderDetailsActivity extends AppCompatActivity {
    private String json;
    private LinearLayoutCompat container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Gson gson = new Gson();
        json = gson.toJson(GLOBAL_ORDER);

        container = (LinearLayoutCompat) findViewById(R.id.order_main_container);
        AppCompatButton send = (AppCompatButton) findViewById(R.id.order_send_btn);

        addView(json);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendCommande();
            }
        });


    }


    private void addView(String text) {

        AppCompatTextView tv = new AppCompatTextView(this);
        tv.setText(text);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorBackground));
        tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        container.addView(tv);

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
                        showSnackbar(findViewById(android.R.id.content), "Something went wrong.");
                        addView("Something went wrong.");
                        addView(response.toString());
                    } else {
                        GLOBAL_CART.clear();
                        addView(msg.getIsOk().toString());
                        addView(msg.getMessage());
                        addView(response.toString());
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseMsg> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });

    }

}
