package com.hotix.myhotixguest.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.hotix.myhotixguest.models.Contacte;
import com.hotix.myhotixguest.models.HotelContactes;
import com.hotix.myhotixguest.models.HotelInfos;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_HOTEL_INFOS;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;
import static com.hotix.myhotixguest.helpers.Utils.stringEmptyOrNull;

public class ContactHotelActivity extends FragmentActivity implements OnMapReadyCallback {

    // Butter Knife BindView
    @BindView(R.id.tv_contact_hotel_name)
    AppCompatTextView tvHotelName;
    @BindView(R.id.tv_contact_hotel_address)
    AppCompatTextView tvHotelAddress;
    @BindView(R.id.ll_hotel_contactes)
    LinearLayout llHotelContacts;

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
    @BindView(R.id.ll_main_container)
    LinearLayout mainContainer;

    // Session Manager Class
    Session session;
    private GoogleMap mMap;
    private Drawable mIconTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hotel);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());
        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadHotelInfos();
                } catch (Exception e) {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                }
            }
        });

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        try {
            loadHotelInfos();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    //**********************************************************************************************

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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

    //**********************************************************************************************

    public void init() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        isServicesOK();

    }

    public void addMarker() {


        if ((!stringEmptyOrNull(GLOBAL_HOTEL_INFOS.getLatitude())) && (!stringEmptyOrNull(GLOBAL_HOTEL_INFOS.getLatitude()))) {
            mMap.clear();
            // Add a marker
            LatLng hotel = new LatLng(Double.valueOf(GLOBAL_HOTEL_INFOS.getLatitude()), Double.valueOf(GLOBAL_HOTEL_INFOS.getLongitude()));
            MarkerOptions marker = new MarkerOptions();
            marker.position(hotel);
            marker.title(GLOBAL_HOTEL_INFOS.getHotelName());

            mMap.addMarker(marker).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hotel));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }

    }

    public void addContactes() {

        llHotelContacts.removeAllViews();

        Drawable mIconPhone;
        Drawable mIconFax;
        Drawable mIconMail;
        Drawable mIconWeb;
        Drawable mIconFb;
        Drawable mIconGp;
        Drawable mIconTwit;
        Drawable mIconTrip;
        Drawable mIconInsta;
        Drawable mIconYoutub;
        Drawable mIconPint;


        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconPhone = getResources().getDrawable(R.drawable.svg_telephone_grey_512, this.getTheme());
            mIconFax = getResources().getDrawable(R.drawable.svg_fax_grey_512, this.getTheme());
            mIconMail = getResources().getDrawable(R.drawable.svg_mail_grey_512, this.getTheme());
            mIconWeb = getResources().getDrawable(R.drawable.svg_web_grey_512, this.getTheme());

            mIconFb = getResources().getDrawable(R.drawable.svg_facebook_512, this.getTheme());
            mIconGp = getResources().getDrawable(R.drawable.svg_google_plus_512, this.getTheme());
            mIconTwit = getResources().getDrawable(R.drawable.svg_twitter_512, this.getTheme());
            mIconTrip = getResources().getDrawable(R.drawable.svg_tripadvisor_512, this.getTheme());
            mIconInsta = getResources().getDrawable(R.drawable.svg_instagram_512, this.getTheme());
            mIconYoutub = getResources().getDrawable(R.drawable.svg_youtube_512, this.getTheme());
            mIconPint = getResources().getDrawable(R.drawable.svg_pinterest_512, this.getTheme());

        } else {
            mIconPhone = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_telephone_grey_512, this.getTheme());
            mIconFax = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_fax_grey_512, this.getTheme());
            mIconMail = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_mail_grey_512, this.getTheme());
            mIconWeb = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_web_grey_512, this.getTheme());

            mIconFb = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_facebook_512, this.getTheme());
            mIconGp = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_google_plus_512, this.getTheme());
            mIconTwit = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_twitter_512, this.getTheme());
            mIconTrip = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_tripadvisor_512, this.getTheme());
            mIconInsta = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_instagram_512, this.getTheme());
            mIconYoutub = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_youtube_512, this.getTheme());
            mIconPint = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_pinterest_512, this.getTheme());
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HotelContactes con : GLOBAL_HOTEL_INFOS.getHotelContactes()) {
            final View catView = inflater.inflate(R.layout.item_contacts_category, null);
            AppCompatTextView tvCat = (AppCompatTextView) catView.findViewById(R.id.tv_contact_category_title);
            tvCat.setText(con.getCategory());
            llHotelContacts.addView(catView);

            for (final Contacte co : con.getContactes()) {
                final View contactView = inflater.inflate(R.layout.item_hotel_contact, null);
                AppCompatTextView tvContact = (AppCompatTextView) contactView.findViewById(R.id.tv_hotel_contact_text);
                AppCompatImageView imgContact = (AppCompatImageView) contactView.findViewById(R.id.img_hotel_contact_icon);
                tvContact.setText(co.getNomContact());
                final String value = co.getNomContact();

                switch (co.getTypeContact()) {
                    case 1:
                        imgContact.setImageDrawable(mIconMail);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendEmail(value);
                            }
                        });
                        break;
                    case 2:
                        imgContact.setImageDrawable(mIconPhone);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callPhone(value);
                            }
                        });
                        break;
                    case 3:
                        imgContact.setImageDrawable(mIconFax);
                        tvContact.setTextColor(getResources().getColor(R.color.light_bg_dark_primary_text));
                        break;
                    case 4:
                        imgContact.setImageDrawable(mIconWeb);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 5:
                        imgContact.setImageDrawable(mIconFb);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 6:
                        imgContact.setImageDrawable(mIconInsta);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 7:
                        imgContact.setImageDrawable(mIconTwit);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 8:
                        imgContact.setImageDrawable(mIconTrip);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 9:
                        imgContact.setImageDrawable(mIconGp);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 10:
                        imgContact.setImageDrawable(mIconPint);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                    case 11:
                        imgContact.setImageDrawable(mIconYoutub);
                        tvContact.setTextColor(getResources().getColor(R.color.purple_500));
                        contactView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                browsWebSite(value);
                            }
                        });
                        break;
                }


                llHotelContacts.addView(contactView);
            }
        }

    }

    public void sendEmail(String mail) {

        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
            startActivity(Intent.createChooser(intent, "Send Email"));

        } catch (Exception e) {

        }

    }

    public void callPhone(String phone) {

        try {

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intent);

        } catch (Exception e) {

        }


    }

    public void browsWebSite(String webSite) {

        try {
            Uri webpage = Uri.parse(webSite);

            if (!webSite.startsWith("http://") && !webSite.startsWith("https://")) {
                webpage = Uri.parse("http://" + webSite);
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, webpage);
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        } catch (Exception e) {

        }


    }

    //**** Load Hotel Infos ************************************************************************
    private void loadHotelInfos() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<HotelInfos> userCall = service.getHotelInfosQuery();

        progressView.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<HotelInfos>() {
            @Override
            public void onResponse(Call<HotelInfos> call, Response<HotelInfos> response) {

                progressView.setVisibility(View.GONE);
                mainContainer.setVisibility(View.VISIBLE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {

                    HotelInfos hotelInfos;
                    hotelInfos = response.body();
                    GLOBAL_HOTEL_INFOS = hotelInfos;
                    addMarker();
                    addContactes();
                    tvHotelName.setText(GLOBAL_HOTEL_INFOS.getHotelName());
                    tvHotelAddress.setText(GLOBAL_HOTEL_INFOS.getHotelAdresse());

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                    mainContainer.setVisibility(View.GONE);
                    emptyListView.setVisibility(View.VISIBLE);
                    emptyListText.setText(R.string.server_unreachable);
                    emptyListIcon.setImageDrawable(mIconTwo);
                }
            }

            @Override
            public void onFailure(Call<HotelInfos> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }
}
