package com.hotix.myhotixguest.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.helpers.Settings;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.HotelSettings;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.ConstantConfig.FINAL_APP_ID;
import static com.hotix.myhotixguest.helpers.ConstantConfig.FINAL_HOTEL_ID;
import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_COMPLAINT_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_MESSAGE_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.stringEmptyOrNull;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 200;
    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.splash_screen_imageView)
    AppCompatImageView splashScreen;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.splash_screen_footer_text)
    AppCompatTextView splashScreenFooter;
    // Session Manager Class
    Session session;
    // Settings Class
    Settings settings;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());
        //settings
        settings = new Settings(getApplicationContext());

        intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                HAVE_MESSAGE_NOTIFICATION = true;
            } else if (extras.containsKey("complaint")) {
                HAVE_MESSAGE_NOTIFICATION = true;
                HAVE_COMPLAINT_NOTIFICATION = true;
            }
        }

        Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher).into(splashScreen);

        if (settings.getConfigured()) {
            setBaseUrl(this);
        }

        try {
            lodeHotelConfig();
        } catch (Exception e) {
            Log.e("SPLASH LOG", e.toString());
        }

    }

    /**********************************************************************************************/

    private void init() {

        Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher).into(splashScreen);

         if (session.getIsLoggedIn()) {
            try {
                login(session.getUserName(), session.getUserPassword());
            } catch (Exception e) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        } else {
            startDelay();
        }

    }

    private void startDelay() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }

    private void startConnectionFailedDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_connection_failed, null);
        AppCompatButton retryBtn = (AppCompatButton) mView.findViewById(R.id.connection_failed_dialog_retry_btn);
        AppCompatButton exitBtn = (AppCompatButton) mView.findViewById(R.id.connection_failed_dialog_exit_btn);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    lodeHotelConfig();
                } catch (Exception e) {
                    Log.e("SPLASH LOG", e.toString());
                }
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }

    /**********************************(  Login Logic  )*************************************/
    public void login(final String uname, final String pwd) {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<Guest> userCall = service.getGuestQuery(uname, pwd);
        final String uName = uname;
        final String uPwd = pwd;
        userCall.enqueue(new Callback<Guest>() {
            @Override
            public void onResponse(Call<Guest> call, Response<Guest> response) {

                if (response.raw().code() == 200) {
                    Guest guest = response.body();
                    if (!(guest.getError() == -1)) {
                        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.clearSessionDetails();
                        session.createNewGuestSession(
                                response.body().getISResident(),
                                response.body().getHasHistory(),
                                true,
                                true,
                                uName,
                                uPwd, response.body().getDateArrivee(),
                                response.body().getDateDepart(),
                                response.body().getChambre(),
                                response.body().getEmail(),
                                response.body().getNom(),
                                response.body().getPrenom(),
                                response.body().getPhone(),
                                response.body().getDateNaissance(),
                                response.body().getAdresse(),
                                response.body().getNationaliteName(),
                                response.body().getEtatResa(),
                                response.body().getResaId(),
                                response.body().getResaPaxId(),
                                response.body().getClientId(),
                                response.body().getFactureId(),
                                response.body().getFactureAnnee(),
                                response.body().getNationaliteId());
                        startActivity(i);
                        finish();
                    }

                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Guest> call, Throwable t) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                startActivity(i);
                finish();
            }
        });

    }

    /**********************************(  Loade Hotel Config )*************************************/
    public void lodeHotelConfig() {

        RetrofitInterface service = RetrofitClient.getHotixSupportApi().create(RetrofitInterface.class);
        Call<HotelSettings> userCall = service.getInfosQuery(FINAL_HOTEL_ID, FINAL_APP_ID);

        userCall.enqueue(new Callback<HotelSettings>() {
            @Override
            public void onResponse(Call<HotelSettings> call, Response<HotelSettings> response) {

                if (response.raw().code() == 200) {
                    HotelSettings hotelSettings = response.body();
                    //Check if hotel id > 0
                    if (!(hotelSettings.getId() > 0)) {
                        //Hotel do not exist
                        Log.e("SPLASH LOG", "Hotel do not exist");
                    } else {

                        //Get Public IP
                        if (!stringEmptyOrNull(hotelSettings.getIPPublic())) {
                            settings.setPublicIp(hotelSettings.getIPPublic());
                            settings.setPublicBaseUrl("http://" + hotelSettings.getIPPublic() + "/");
                            settings.setPublicIpEnabled(true);
                        } else {
                            settings.setPublicIp("xxx.xxx.xxx.xxx");
                            settings.setPublicIpEnabled(false);
                        }

                        //Get Local IP
                        if (!stringEmptyOrNull(hotelSettings.getIPLocal())) {
                            settings.setLocalIp(hotelSettings.getIPLocal());
                            settings.setLocalBaseUrl("http://" + hotelSettings.getIPLocal() + "/");
                            settings.setLocalIpEnabled(true);
                        } else {
                            settings.setLocalIp("xxx.xxx.xxx.xxx");
                            settings.setLocalIpEnabled(false);
                        }

                        settings.setConfigured(true);

                        setBaseUrl(getApplicationContext());

                    }

                } else {
                    Log.e("SPLASH LOG", response.message());
                }

                if (settings.getConfigured()) {
                    init();
                }else{
                    startConnectionFailedDialog();
                }

            }

            @Override
            public void onFailure(Call<HotelSettings> call, Throwable t) {
                Log.e("SPLASH LOG", getString(R.string.server_down));
                if (settings.getConfigured()) {
                    init();
                }else{
                    startConnectionFailedDialog();
                }
            }
        });

    }

}
