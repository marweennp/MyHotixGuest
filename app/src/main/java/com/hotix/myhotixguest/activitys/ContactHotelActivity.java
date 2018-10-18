package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hotix.myhotixguest.helpers.ConstantConfig.WEB_SITE_URL;

public class ContactHotelActivity extends FragmentActivity implements OnMapReadyCallback {

    // Butter Knife BindView
    @BindView(R.id.contact_hotel_phone)
    AppCompatTextView phone_tv;
    @BindView(R.id.contact_hotel_mail_1)
    AppCompatTextView mail_1_tv;
    @BindView(R.id.contact_hotel_mail_2)
    AppCompatTextView mail_2_tv;
    @BindView(R.id.contact_hotel_mail_3)
    AppCompatTextView mail_3_tv;
    @BindView(R.id.contact_hotel_mail_4)
    AppCompatTextView mail_4_tv;
    @BindView(R.id.contact_hotel_web_site)
    AppCompatTextView web_site_tv;

    // Session Manager Class
    Session session;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hotel);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        isServicesOK();
    }

    @OnClick(R.id.contact_hotel_phone_layout)
    public void callPhone() {
        callPhone(phone_tv.getText().toString());
    }

    @OnClick(R.id.contact_hotel_mail_layout_1)
    public void sendMail_1() {
        sendEmail(mail_1_tv.getText().toString());
    }

    @OnClick(R.id.contact_hotel_mail_layout_2)
    public void sendMail_2() {
        sendEmail(mail_2_tv.getText().toString());
    }

    @OnClick(R.id.contact_hotel_mail_layout_3)
    public void sendMail_3() {
        sendEmail(mail_3_tv.getText().toString());
    }

    @OnClick(R.id.contact_hotel_mail_layout_4)
    public void sendMail_4() {
        sendEmail(mail_4_tv.getText().toString());
    }

    @OnClick(R.id.contact_hotel_web_site_layout)
    public void web_site() {
        browsWebSite(WEB_SITE_URL);
    }

    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Toast.makeText(this, "An error occured on map requests", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker
        LatLng hotel = new LatLng(36.38089258, 10.54804663);
        MarkerOptions marker = new MarkerOptions();
        marker.position(hotel);
        marker.title("Le Royal Hotels & Resorts - Hammamet");

        mMap.addMarker(marker).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hotel));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    public void sendEmail(String mail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    public void browsWebSite(String webSite) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSite));
        startActivity(browserIntent);
    }
}
