package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.hotix.myhotixguest.adapters.MyRoomAdapter;
import com.hotix.myhotixguest.adapters.MySpinnerAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Arrangement;
import com.hotix.myhotixguest.models.BookingConfirmation;
import com.hotix.myhotixguest.models.BookingData;
import com.hotix.myhotixguest.models.BookingAvailability;
import com.hotix.myhotixguest.models.MobileResa;
import com.hotix.myhotixguest.models.RoomType;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class NewReservationActivity extends AppCompatActivity {

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
    @BindView(R.id.rl_booking_total)
    RelativeLayout rlBookingTotal;

    @BindView(R.id.new_reservation_check_in_et)
    AppCompatEditText etCheckIn;
    @BindView(R.id.new_reservation_check_out_et)
    AppCompatEditText etCheckOut;

    @BindView(R.id.new_reservation_adults_et)
    AppCompatEditText etAdults;
    @BindView(R.id.new_reservation_kids_et)
    AppCompatEditText etKids;
    @BindView(R.id.new_reservation_babies_et)
    AppCompatEditText etBabies;

    @BindView(R.id.sp_bookings_room_types)
    AppCompatSpinner spRoomTypes;
    @BindView(R.id.sp_bookings_arrengements)
    AppCompatSpinner spArrengements;

    @BindView(R.id.new_reservation_check_btn)
    AppCompatButton btnReservationCheck;
    @BindView(R.id.new_reservation_confirm_btn)
    AppCompatButton btnReservationConfirm;

    @BindView(R.id.booking_total_ttc_tv)
    AppCompatTextView tvBookingTotalTTC;
    @BindView(R.id.booking_total_cur_tv)
    AppCompatTextView tvBookingTotalCUR;

    @BindView(R.id.pb_new_booking)
    ProgressBar pbNewBooking;

    // Session Manager Class
    private Session session;

    private Drawable mIconTwo;
    private MyArrangAdapter mArrangAdapter;
    private MyRoomAdapter mRoomAdapter;
    private ArrayList<Arrangement> mArrangements;
    private ArrayList<RoomType> mRoomTypes;
    private int mArrangId = -1;
    private int mRoomId = -1;

    private MobileResa _Resa = new MobileResa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
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

    @OnClick(R.id.new_reservation_check_btn)
    public void checkAvailability() {
        checkBookingAvailability();
    }

    @OnClick(R.id.new_reservation_confirm_btn)
    public void confirmBooking() {
        confrimBooking();
    }

    @OnClick(R.id.new_reservation_check_in_et)
    public void checkInDate() {
        startDatePickerDialog(etCheckIn);
    }

    @OnClick(R.id.new_reservation_check_out_et)
    public void checkOutDate() { startDatePickerDialog(etCheckOut); }

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

    private void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.new_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        etCheckIn.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etCheckOut.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        etAdults.setText("1");
        etKids.setText("0");
        etBabies.setText("0");
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

            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et.setText(selectedHour + ":" + selectedMinute);
            }

        }, _Hour, _Minute, true);
        timePickerDialog.show();

    }

    //**********************************************************************************************
    private void loadeData() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<BookingData> userCall = service.getBookingDataQuery();

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rlMainContainer.setVisibility(View.GONE);


        userCall.enqueue(new Callback<BookingData>() {
            @Override
            public void onResponse(Call<BookingData> call, Response<BookingData> response) {
                progressView.setVisibility(View.GONE);
                rlMainContainer.setVisibility(View.VISIBLE);
                if (response.raw().code() == 200) {

                    BookingData _Data = response.body();

                    mRoomTypes = _Data.getRoomTypes();
                    mArrangements = _Data.getArrangements();
                    mRoomAdapter = new MyRoomAdapter(getApplicationContext(), mRoomTypes);
                    mArrangAdapter = new MyArrangAdapter(getApplicationContext(), mArrangements);

                    spRoomTypes.setAdapter(mRoomAdapter);
                    spRoomTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                            mRoomId = mRoomTypes.get(position).getRoomTypeID();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });

                    spArrengements.setAdapter(mArrangAdapter);
                    spArrengements.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                            mArrangId = mArrangements.get(position).getArrangID();
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
            public void onFailure(Call<BookingData> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                rlMainContainer.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void checkBookingAvailability() {

        final String content_type = "application/json";

        _Resa.setDateStart(etCheckIn.getText().toString());
        _Resa.setDateEnd(etCheckOut.getText().toString());
        _Resa.setArrangeID(mArrangId);
        _Resa.setRoomType(mRoomId);
        _Resa.setNbA(Integer.parseInt(etAdults.getText().toString()));
        _Resa.setNbE(Integer.parseInt(etKids.getText().toString()));
        _Resa.setNbB(Integer.parseInt(etBabies.getText().toString()));

        Log.e("MARWEN", new Gson().toJson(GLOBAL_ORDER));

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<BookingAvailability> userCall = service.checkBookingAvailabilityQuery(content_type, _Resa);

        pbNewBooking.setVisibility(View.VISIBLE);
        btnReservationCheck.setEnabled(false);

        userCall.enqueue(new Callback<BookingAvailability>() {
            @Override
            public void onResponse(Call<BookingAvailability> call, Response<BookingAvailability> response) {

                pbNewBooking.setVisibility(View.GONE);
                btnReservationCheck.setEnabled(true);

                if (response.raw().code() == 200) {

                    BookingAvailability _Data = response.body();

                    if (_Data.getSuccess()) {

                        if (_Data.getAvailable()) {

                            btnReservationCheck.setVisibility(View.GONE);
                            btnReservationConfirm.setVisibility(View.VISIBLE);
                            rlBookingTotal.setVisibility(View.VISIBLE);
                            tvBookingTotalTTC.setText(_Data.getPrice() + "");

                        } else {
                            //showSnackbar(findViewById(android.R.id.content), "No Room Is Available");
                            showSnackbar(findViewById(android.R.id.content), _Data.getMessage());
                        }

                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Failed!");
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingAvailability> call, Throwable t) {
                pbNewBooking.setVisibility(View.GONE);
                btnReservationCheck.setEnabled(true);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void confrimBooking() {

        final String content_type = "application/json";

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<BookingConfirmation> userCall = service.confirmBookingQuery(content_type, _Resa);

        pbNewBooking.setVisibility(View.VISIBLE);
        btnReservationCheck.setEnabled(false);

        userCall.enqueue(new Callback<BookingConfirmation>() {
            @Override
            public void onResponse(Call<BookingConfirmation> call, Response<BookingConfirmation> response) {

                pbNewBooking.setVisibility(View.GONE);
                btnReservationCheck.setEnabled(true);

                if (response.raw().code() == 200) {

                    BookingConfirmation _Data = response.body();

                    if (_Data.getSuccess()) {

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
                        etCheckIn.setText(dateFormatter.format(Calendar.getInstance().getTime()));
                        etCheckOut.setText(dateFormatter.format(Calendar.getInstance().getTime()));

                        etAdults.setText("1");
                        etKids.setText("0");
                        etBabies.setText("0");

                        rlBookingTotal.setVisibility(View.GONE);
                        btnReservationConfirm.setVisibility(View.GONE);
                        btnReservationCheck.setVisibility(View.VISIBLE);
                        btnReservationCheck.setEnabled(true);

                        showSnackbar(findViewById(android.R.id.content), "booking successful");
                    } else {
                        showSnackbar(findViewById(android.R.id.content), "Failed!");
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingConfirmation> call, Throwable t) {
                pbNewBooking.setVisibility(View.GONE);
                btnReservationCheck.setEnabled(true);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }
}
