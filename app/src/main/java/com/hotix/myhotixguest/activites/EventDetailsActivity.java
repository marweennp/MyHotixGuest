package com.hotix.myhotixguest.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.SuccessResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_EVENT;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_HOTEL_INFOS;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class EventDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.event_details_img)
    AppCompatImageView eventImg;
    @BindView(R.id.participate_button)
    AppCompatButton participateButton;
    @BindView(R.id.event_details_date)
    AppCompatTextView eventDetailsDate;
    @BindView(R.id.event_details_time)
    AppCompatTextView eventDetailsTime;
    @BindView(R.id.event_details_location_text)
    AppCompatTextView eventDetailsLocationText;
    @BindView(R.id.event_details_price_text)
    AppCompatTextView eventDetailsPriceText;
    @BindView(R.id.event_details_type)
    AppCompatTextView eventDetailsType;
    @BindView(R.id.event_details_title)
    AppCompatTextView eventDetailsTitle;
    @BindView(R.id.event_details_desc)
    AppCompatTextView eventDetailsDesc;

    // Session Manager Class
    Session session;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.event_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        formatter = NumberFormat.getCurrencyInstance();
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("TND ");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        Log.e("MARWEN", new Gson().toJson(GLOBAL_EVENT.getPrticipation()));
        if (!GLOBAL_EVENT.getPrticipation()){
            participateButton.setVisibility(View.GONE);
        }
        else if ((!session.getISResident()) || (!GLOBAL_EVENT.getEtat().toUpperCase().equals("N"))) {
            participateButton.setVisibility(View.GONE);
        }

        Picasso.get().load(BASE_URL + "/Android/pics_guest/Events/" + GLOBAL_EVENT.getImage()).fit().placeholder(R.drawable.activites).into(eventImg);

        eventDetailsDate.setText(Html.fromHtml(dateColored(GLOBAL_EVENT.getDateDebut(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", false) + "-" + dateColored(GLOBAL_EVENT.getDateFin(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));
        eventDetailsTime.setText(dateFormater(GLOBAL_EVENT.getHeure(), "hh:mm:ss", "hh:mm"));
        eventDetailsLocationText.setText(GLOBAL_EVENT.getLocation());

        if (!(GLOBAL_EVENT.getPrix() == 0)) {
            eventDetailsPriceText.setTextColor(Color.parseColor("#FFFFFF"));
            eventDetailsPriceText.setText(formatter.format(GLOBAL_EVENT.getPrix()));
        } else {
            eventDetailsPriceText.setTextColor(Color.parseColor("#388E3C"));
            eventDetailsPriceText.setText(R.string.free);
        }

        eventDetailsType.setText(GLOBAL_EVENT.getCategorie());
        eventDetailsTitle.setText(GLOBAL_EVENT.getNom());
        eventDetailsDesc.setText(GLOBAL_EVENT.getDescription());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.participate_button)
    public void participationEmail() {

        try {

//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{GLOBAL_HOTEL_INFOS.getEventMail()});
//            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.event_participation);
//            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.event_confirm_participation) + " : " + GLOBAL_EVENT.getNom() + ". \n Date : " + dateFormater(GLOBAL_EVENT.getDateDebut(), "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy") + ". \n " + getString(R.string.location) + GLOBAL_EVENT.getLocation() + ".");
//            startActivity(Intent.createChooser(intent, "Send Email"));

            addMessage();

        } catch (Exception ex) {
            showSnackbar(findViewById(android.R.id.content), ex.getMessage());
        }
    }


    /**
     * *********************************************************************************************
     */

    private void addMessage() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<SuccessResponse> userCall = service.SendActivityParticipationQuery("1", GLOBAL_EVENT.getId().toString(), session.getResaId().toString(), session.getResaPaxId().toString(), session.getResaGroupeId().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Response...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    SuccessResponse _Response = response.body();
                    if (_Response.getSuccess()) {

                        participateButton.setVisibility(View.GONE);
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.event_participation_sent));
                    } else {
                        showSnackbar(findViewById(android.R.id.content), _Response.getMessage());
                    }
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.toString());
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }


}
