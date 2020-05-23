package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.RestaurantReservation;
import com.hotix.myhotixguest.models.ReveilData;
import com.hotix.myhotixguest.models.SuccessResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ReveilActivity extends AppCompatActivity {

    // Butter Knife BindView
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

    @BindView(R.id.rl_main_container)
    RelativeLayout rlMainContainer;

    @BindView(R.id.et_reveil_date)
    AppCompatEditText etReveilDate;
    @BindView(R.id.et_reveil_time)
    AppCompatEditText etReveilTime;

    @BindView(R.id.btn_reveil_confirm)
    AppCompatButton btnReveilConfirm;

    @BindView(R.id.pb_reveil)
    ProgressBar pbReveil;

    // Session Manager Class
    private Session session;

    private Drawable mIconTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveil);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

        init();
    }

    @OnClick(R.id.btn_reveil_confirm)
    public void confirmAlarm() {
        try {
            confrimReveil();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @OnClick(R.id.et_reveil_date)
    public void setReveilDate() {
        startDatePickerDialog(etReveilDate);
    }

    @OnClick(R.id.et_reveil_time)
    public void setReveilTime() {
        startTimePickerDialog(etReveilTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //**********************************************************************************************

    private void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.reveil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        etReveilDate.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etReveilTime.setText(timeFormatter.format(Calendar.getInstance().getTime()));
    }

    private void startDatePickerDialog(final AppCompatEditText et) {
        Calendar currentTime = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
                et.setText(dateFormatter.format(newDate.getTime()));
            }

        }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void startTimePickerDialog(final AppCompatEditText et) {
        Calendar currentTime = Calendar.getInstance();
        int _Hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int _Minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int _Hour, int _Minute) {
                Calendar _DateTime = Calendar.getInstance();
                Date _Date = new Date();
                _DateTime.set(_Date.getYear(), _Date.getMonth(), _Date.getMonth(), _Hour, _Minute);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
                et.setText(dateFormatter.format(_DateTime.getTime()));
            }

        }, _Hour, _Minute, true);
        timePickerDialog.show();

    }

    //**********************************************************************************************

    private void confrimReveil() {

        final String content_type = "application/json";
        ReveilData _Reveil = new ReveilData();
        _Reveil.setHotelID(1);
        _Reveil.setResaID(session.getResaId());
        _Reveil.setResaGroupeId(-1);
        _Reveil.setPaxID(session.getResaPaxId());
        _Reveil.setComment("");
        _Reveil.setDateCreation(new Date());
        try {
            _Reveil.setReveilDate(new SimpleDateFormat("dd MMM yyyy").parse(etReveilDate.getText().toString()));
            _Reveil.setReveilHeure(new SimpleDateFormat("HH:mm").parse(etReveilTime.getText().toString()));
        } catch (ParseException e) {
            showSnackbar(findViewById(android.R.id.content), e.getMessage());
        }

        Log.e("MARWEN", new Gson().toJson(_Reveil));
        //etReveilTime.setText(new Gson().toJson(_Reveil));

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<SuccessResponse> userCall = service.addReveilQuery(content_type, _Reveil);

        pbReveil.setVisibility(View.VISIBLE);
        btnReveilConfirm.setEnabled(false);

        userCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                pbReveil.setVisibility(View.GONE);
                btnReveilConfirm.setEnabled(true);

                if (response.raw().code() == 200) {

                    SuccessResponse _Data = response.body();

                    if (_Data.getSuccess()) {
                        showSnackbar(findViewById(android.R.id.content), "Successful Reveil Add");
                        finish();
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Failed!" + _Data.getMessage());
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                pbReveil.setVisibility(View.GONE);
                btnReveilConfirm.setEnabled(true);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }
}
