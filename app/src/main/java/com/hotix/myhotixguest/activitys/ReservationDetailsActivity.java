package com.hotix.myhotixguest.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservationDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // Butter Knife BindView LinearLayout
    @BindView(R.id.guests_details_container)
    LinearLayout guestsContainer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.reservation_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addGuestDetales();
        addGuestDetales();
        addGuestDetales();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addGuestDetales() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View guestView = inflater.inflate(R.layout.guest_details_row, null);
        AppCompatTextView guestIsMaster = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_is_master);
        AppCompatTextView guestNameOp = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_name_op);
        AppCompatTextView guestName = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_name);
        AppCompatTextView guestNationality = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_nationality);
        AppCompatImageView guestBirthDateIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_birth_date_icon);
        AppCompatTextView guestBirthDate = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_birth_date);
        AppCompatImageView guestAddressIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_address_icon);
        AppCompatTextView guestAddress = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_address);
        AppCompatImageView guestPhoneIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_phone_icon);
        AppCompatTextView guestPhone = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_phone);
        AppCompatImageView guestMailIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_mail_icon);
        AppCompatTextView guestMail = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_mail);
//        fieldTitle.setText(title);
//        fieldText.setText(text);

        guestsContainer.addView(guestView);
    }
}
