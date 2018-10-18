package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.NearbyPlaces;
import com.hotix.myhotixguest.models.Result;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Settings.G_PLACES_API_KEY;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class HotelNearbyPlacesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String mLocation = ("36.85166946,10.20361863");
    private String mRadius = ("10000");
    private ArrayList<Result> mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_nearby_places);
        ButterKnife.bind(this);
        isServicesOK();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @OnClick(R.id.nearby_place_restaurant_view)
    public void showRestaurant() {
        loadNearbyPlaces(mLocation, mRadius, "restaurant", G_PLACES_API_KEY);
    }

    @OnClick(R.id.nearby_place_atm_view)
    public void showAirport() {
        loadNearbyPlaces(mLocation, mRadius, "atm", G_PLACES_API_KEY);
    }

    @OnClick(R.id.nearby_place_park_view)
    public void showSchool() {
        loadNearbyPlaces(mLocation, mRadius, "park", G_PLACES_API_KEY);
    }

    @OnClick(R.id.nearby_place_shopping_mall_view)
    public void showBank() {
        loadNearbyPlaces(mLocation, mRadius, "shopping_mall", G_PLACES_API_KEY);
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
        LatLng mLocation = new LatLng(36.85166946, 10.20361863);
        LatLng hotel = new LatLng(36.38089258, 10.54804663);
        mMap.addMarker(new MarkerOptions().position(mLocation).title("HOTIX"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    public void loadNearbyPlaces(String location, String radius, final String type, String key) {

        final String mType = type;

        RetrofitInterface service = RetrofitClient.getClientUrl().create(RetrofitInterface.class);
        Call<NearbyPlaces> userCall = service.getNearbyPlacesQuery(location, radius, mType, key);

        final ProgressDialog progressDialog = new ProgressDialog(HotelNearbyPlacesActivity.this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loding...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<NearbyPlaces>() {
            @Override
            public void onResponse(Call<NearbyPlaces> call, Response<NearbyPlaces> response) {
                progressDialog.dismiss();

                mResult = new ArrayList<>();

                if (response.raw().code() == 200) {
                    NearbyPlaces nearbyPlaces = response.body();
                    mResult.addAll(nearbyPlaces.getResults());
                    if (!(nearbyPlaces.getStatus().equals("OK"))) {
                        showSnackbar(findViewById(android.R.id.content), "Something went wrong. please verify your login or password");
                    } else {
                        showNearbyPlaces(mResult,mType);
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.toString());
                }

            }

            @Override
            public void onFailure(Call<NearbyPlaces> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });

    }

    public void showNearbyPlaces(ArrayList<Result> results, String type) {

        BitmapDescriptor pin_resto = BitmapDescriptorFactory.fromResource(R.drawable.pin_resto);
        BitmapDescriptor pin_atm = BitmapDescriptorFactory.fromResource(R.drawable.pin_atm);
        BitmapDescriptor pin_park = BitmapDescriptorFactory.fromResource(R.drawable.pin_park);
        BitmapDescriptor pin_shop = BitmapDescriptorFactory.fromResource(R.drawable.pin_shop);


        mMap.clear();
        for (Result obj : results) {
            MarkerOptions marker = new MarkerOptions();

            String placeName = obj.getName();
            String placeVicinity = obj.getVicinity();

            double lat = obj.getGeometry().getLocation().getLat();
            double lng = obj.getGeometry().getLocation().getLng();

            LatLng latLng = new LatLng(lat, lng);
            marker.position(latLng);
            marker.title(placeName + " : " + placeVicinity);

            if (type.equals("restaurant")) {
                marker.icon(pin_resto);
            } else if (type.equals("atm")) {
                marker.icon(pin_atm);
            } else if (type.equals("park")) {
                marker.icon(pin_park);
            } else if (type.equals("shopping_mall")) {
                marker.icon(pin_shop);
            }


            mMap.addMarker(marker);
        }

        LatLng mLocation = new LatLng(36.85166946, 10.20361863);
        mMap.addMarker(new MarkerOptions().position(mLocation).title("HOTIX"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));


    }

}
