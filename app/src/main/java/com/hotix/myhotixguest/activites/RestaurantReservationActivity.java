package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.MyArrangAdapter;
import com.hotix.myhotixguest.adapters.MyRestaurantAdapter;
import com.hotix.myhotixguest.adapters.MyRoomAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.BookingConfirmation;
import com.hotix.myhotixguest.models.BookingData;
import com.hotix.myhotixguest.models.Restaurant;
import com.hotix.myhotixguest.models.RestaurantReservation;
import com.hotix.myhotixguest.models.RestaurantsData;
import com.hotix.myhotixguest.models.SuccessResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class RestaurantReservationActivity extends AppCompatActivity {

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

    @BindView(R.id.sp_restaurant)
    AppCompatSpinner spRestaurant;

    @BindView(R.id.iv_resto_resa_call)
    AppCompatImageView ivRstoResaCall;

    @BindView(R.id.et_resto_resa_date)
    AppCompatEditText etRestoResaDate;
    @BindView(R.id.et_resto_resa_time)
    AppCompatEditText etRestoResaTime;
    @BindView(R.id.et_resto_resa_seats)
    AppCompatEditText etRestoResaSeats;
    @BindView(R.id.et_resto_resa_name)
    AppCompatEditText etRestoResaName;
    @BindView(R.id.et_resto_resa_email)
    AppCompatEditText etRestoResaEmail;
    @BindView(R.id.et_resto_resa_tel)
    AppCompatEditText etRestoResaTel;

    @BindView(R.id.btn_resto_resa_confirm)
    AppCompatButton btnRestoResaConfirm;

    @BindView(R.id.pb_resto_resa)
    ProgressBar pbRestoResa;

    // Session Manager Class
    private Session session;

    private Drawable mIconTwo;
    private MyRestaurantAdapter mRestaurantAdapter;
    private ArrayList<Restaurant> mRestaurants;
    private int mRestaurantId = -1;
    private String mRestoTel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_reservation);
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

    @OnClick(R.id.btn_resto_resa_confirm)
    public void confirmReservation() {
        try {
            confrimRestoResa();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @OnClick(R.id.et_resto_resa_date)
    public void setRestoResaDate() {
        startDatePickerDialog(etRestoResaDate);
    }

    @OnClick(R.id.et_resto_resa_time)
    public void setRestoResaTime() {
        startTimePickerDialog(etRestoResaTime);
    }

    @OnClick(R.id.iv_resto_resa_call)
    public void callRestaurant() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mRestoTel));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        try {
            loadeData();
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
    private void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.restaurant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        etRestoResaDate.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etRestoResaTime.setText(timeFormatter.format(Calendar.getInstance().getTime()));

        etRestoResaSeats.setText("1");
        etRestoResaName.setText(session.getPrenom() + " " + session.getNom());
        etRestoResaEmail.setText(session.getEmail());
        etRestoResaTel.setText(session.getPhone());
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

    private void loadeData() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<RestaurantsData> userCall = service.getRestaurantsQuery();

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rlMainContainer.setVisibility(View.GONE);

        userCall.enqueue(new Callback<RestaurantsData>() {
            @Override
            public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                progressView.setVisibility(View.GONE);
                rlMainContainer.setVisibility(View.VISIBLE);
                if (response.raw().code() == 200) {

                    RestaurantsData _Data = response.body();

                    mRestaurants = _Data.getRestaurants();
                    mRestaurantAdapter= new MyRestaurantAdapter(getApplicationContext(), mRestaurants);

                    spRestaurant.setAdapter(mRestaurantAdapter);
                    spRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                            mRestaurantId = mRestaurants.get(position).getId();
                            mRestoTel = mRestaurants.get(position).getNumTel();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<RestaurantsData> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                rlMainContainer.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void confrimRestoResa() {

        final String content_type = "application/json";
        RestaurantReservation _Resa = new RestaurantReservation();
        _Resa.setRestID(mRestaurantId);
        _Resa.setEtatResa(1);
        _Resa.setReference(-1);
        _Resa.setNbrPAX(Integer.parseInt(etRestoResaSeats.getText().toString().trim()));
        _Resa.setNom(session.getNom().trim());
        _Resa.setPrenom(session.getPrenom().trim());
        _Resa.setNumTel(session.getPhone().trim());
        _Resa.setEmail(session.getEmail().trim());
        _Resa.setCommentaire("");
        _Resa.setDateResa(new Date());
        try {
            _Resa.setDateArrivee(new SimpleDateFormat("dd MMM yyyy").parse(etRestoResaDate.getText().toString()));
            _Resa.setHeureArrivee(new SimpleDateFormat("HH:mm").parse(etRestoResaTime.getText().toString()));
        } catch (ParseException e) {
            showSnackbar(findViewById(android.R.id.content), e.getMessage());
        }

        Log.e("MARWEN", new Gson().toJson(_Resa));
        //etRestoResaTel.setText(new Gson().toJson(_Resa));

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<SuccessResponse> userCall = service.reserverRestaurantQuery(content_type, _Resa);

        pbRestoResa.setVisibility(View.VISIBLE);
        btnRestoResaConfirm.setEnabled(false);

        userCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                pbRestoResa.setVisibility(View.GONE);
                btnRestoResaConfirm.setEnabled(true);

                if (response.raw().code() == 200) {

                    SuccessResponse _Data = response.body();

                    if (_Data.getSuccess()) {
                        showSnackbar(findViewById(android.R.id.content), "Successful Reservation");
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
                pbRestoResa.setVisibility(View.GONE);
                btnRestoResaConfirm.setEnabled(true);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

}
