package com.hotix.myhotixguest.activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.helpers.Settings;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.HotelInfos;
import com.hotix.myhotixguest.models.HotelSettings;
import com.hotix.myhotixguest.models.LoginResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConnectionChecher.isNetworkAvailable;
import static com.hotix.myhotixguest.helpers.ConstantConfig.FINAL_APP_ID;
import static com.hotix.myhotixguest.helpers.ConstantConfig.FINAL_HOTEL_CODE;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_HOTEL_INFOS;
import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_COMPLAINT_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_MESSAGE_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.Utils.clearImageDiskCache;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.stringEmptyOrNull;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 200;
    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.splash_screen_imageView)
    AppCompatImageView splashScreen;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.tv_spalsh_progress)
    AppCompatTextView splashScreenFooter;

    // Empty View
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_btn)
    AppCompatButton emptyListRefresh;
    @BindView(R.id.rl_main_container)
    RelativeLayout mainContainer;
    // Session Manager Class
    Session session;
    // Settings Class
    Settings settings;
    private TextInputLayout ilHotelCode;
    private AppCompatEditText etHotelCode;
    private InputValidation mInputValidation;
    private Intent intent;

    private Drawable mIconOne, mIconTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());
        //settings
        settings = new Settings(getApplicationContext());

        //Inputs Validation
        mInputValidation = new InputValidation(this);

        //Cleare Picasso Cash
        clearImageDiskCache(getApplicationContext());
        //settings.setFirstStart(true); // tests

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_internet_grey_512, this.getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_internet_grey_512, this.getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

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

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        splashScreenFooter.setText(R.string.checking_internet_providers);
//        if (isNetworkAvailable(this)) {

        if (settings.getConfigured()) {
            setBaseUrl(this);
        }

        try {
            lodeHotelConfig();
        } catch (Exception e) {
            Log.e("SPLASH LOG", e.toString());
        }

//        } else {
//            mainContainer.setVisibility(View.GONE);
//            emptyListView.setVisibility(View.VISIBLE);
//            emptyListText.setText(R.string.check_internet_connection);
//            emptyListIcon.setImageDrawable(mIconOne);
//        }


    }

    /**********************************************************************************************/

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

    //This method show Download Hotel Settings dialog.
    private void startDownloadSettingsDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_hotel_settings, null);
        AppCompatButton btnDownload = (AppCompatButton) mView.findViewById(R.id.btn_dialog_hotel_settings_download);
        AppCompatButton btnCancel = (AppCompatButton) mView.findViewById(R.id.btn_dialog_hotel_settings_cancel);
        ilHotelCode = (TextInputLayout) mView.findViewById(R.id.il_dialog_hotel_settings_code);
        etHotelCode = (AppCompatEditText) mView.findViewById(R.id.et_dialog_hotel_settings_code);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInputValidation.isInputEditTextFilled(etHotelCode, ilHotelCode, getString(R.string.error_message_field_required))) {

                    settings.setHotelCode(etHotelCode.getText().toString());
                    settings.setFirstStart(false);
                    recreate();

                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

    }

    /**********************************(  Login Logic  )*************************************/
    public void login(final String uname, final String pwd) {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<LoginResponse> userCall = service.getGuestQuery(uname, pwd);
        final String uName = uname;
        final String uPwd = pwd;

        splashScreenFooter.setText(R.string.login);
        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.raw().code() == 200) {
                    LoginResponse _LoginResponse = response.body();

                    if (_LoginResponse.getSuccess()) {

                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.setNewToken(true);
                        session.createNewGuestSession(
                                1,
                                _LoginResponse.getGuest().getISResident(),
                                _LoginResponse.getGuest().getHasHistory(),
                                true,
                                true,
                                uname,
                                pwd,
                                _LoginResponse.getGuest().getDateArrivee(),
                                _LoginResponse.getGuest().getDateDepart(),
                                _LoginResponse.getGuest().getChambre(),
                                _LoginResponse.getGuest().getEmail(),
                                _LoginResponse.getGuest().getNom(),
                                _LoginResponse.getGuest().getPrenom(),
                                _LoginResponse.getGuest().getPhone(),
                                _LoginResponse.getGuest().getDateNaissance(),
                                _LoginResponse.getGuest().getAdresse(),
                                _LoginResponse.getGuest().getNationaliteName(),
                                _LoginResponse.getGuest().getEtatResa(),
                                _LoginResponse.getGuest().getResaId(),
                                _LoginResponse.getGuest().getResaGroupeId(),
                                _LoginResponse.getGuest().getResaPaxId(),
                                _LoginResponse.getGuest().getClientId(),
                                _LoginResponse.getGuest().getFactureId(),
                                _LoginResponse.getGuest().getFactureAnnee(),
                                _LoginResponse.getGuest().getNationaliteId());

                        //Start the HomeScreenActivity
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                startActivity(i);
                finish();
            }
        });

    }

    /**********************************(  Loade Hotel Config )*************************************/
    public void lodeHotelConfig() {

        Log.e("MARWEN LOG", "lodeHotelConfig()");

        RetrofitInterface service = RetrofitClient.getHotixSupportApi().create(RetrofitInterface.class);
        Call<HotelSettings> userCall = service.getInfosQuery(FINAL_HOTEL_CODE, FINAL_APP_ID);

        splashScreenFooter.setText(R.string.loading_hotel_settings);

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

                        //Get Hotel ID
                        if (!stringEmptyOrNull(hotelSettings.getCode())) {
                            settings.setHotelCode(hotelSettings.getCode());
                        } else {
                            settings.setHotelCode("0000");
                        }

                        //Get Hotel Name
                        if (!stringEmptyOrNull(hotelSettings.getName())) {
                            settings.setHotelName(hotelSettings.getName());
                        } else {
                            settings.setHotelName("HOTEL");
                        }

                        settings.setConfigured(true);

                        setBaseUrl(getApplicationContext());

                    }

                } else {
                    Log.e("SPLASH LOG", response.message());
                }

                if (settings.getConfigured()) {

                    try {
                        loadHotelInfos();
                    } catch (Exception e) {

                        mainContainer.setVisibility(View.GONE);
                        emptyListView.setVisibility(View.VISIBLE);
                        emptyListText.setText(R.string.check_internet_connection);
                        emptyListIcon.setImageDrawable(mIconOne);

                    }

                } else {
                    startConnectionFailedDialog();
                }

            }

            @Override
            public void onFailure(Call<HotelSettings> call, Throwable t) {
                Log.e("SPLASH LOG", getString(R.string.server_down));
                Log.e("MARWEN LOG", "onFailure()");
                Log.e("MARWEN LOG", t.getMessage());
                if (settings.getConfigured()) {

                    try {
                        loadHotelInfos();
                    } catch (Exception e) {

                        mainContainer.setVisibility(View.GONE);
                        emptyListView.setVisibility(View.VISIBLE);
                        emptyListText.setText(R.string.check_internet_connection);
                        emptyListIcon.setImageDrawable(mIconOne);

                    }

                } else {
                    startConnectionFailedDialog();
                }
            }
        });

    }

    //**** Load Hotel Infos ************************************************************************
    private void loadHotelInfos() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<HotelInfos> userCall = service.getHotelInfosQuery();

        splashScreenFooter.setText(R.string.loading_hotel_infos);

        userCall.enqueue(new Callback<HotelInfos>() {
            @Override
            public void onResponse(Call<HotelInfos> call, Response<HotelInfos> response) {

                if (response.raw().code() == 200) {

                    HotelInfos hotelInfos;
                    hotelInfos = response.body();
                    GLOBAL_HOTEL_INFOS = hotelInfos;

                }

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

            @Override
            public void onFailure(Call<HotelInfos> call, Throwable t) {

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
        });

    }

}
