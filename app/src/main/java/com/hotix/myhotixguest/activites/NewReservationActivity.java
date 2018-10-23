package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.Toast;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewReservationActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    // Session Manager Class
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.new_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initData();

    }

    @OnClick(R.id.new_reservation_search_btn)
    public void refresh() {

    }

    @OnClick(R.id.new_reservation_check_in_et)
    public void checkInDate() {
        startDatePickerDialog(etCheckIn);
    }

    @OnClick(R.id.new_reservation_check_out_et)
    public void checkOutDate() {
        startDatePickerDialog(etCheckOut);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initData() {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        etCheckIn.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etCheckOut.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        etAdults.setText("0");
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

}
