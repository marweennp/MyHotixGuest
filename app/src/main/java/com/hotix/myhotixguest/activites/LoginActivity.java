package com.hotix.myhotixguest.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.helpers.Settings;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.LoginResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.hotix.myhotixguest.views.kbv.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FIREBASE_ID";

    // Butter Knife BindView RelativeLayout
    @BindView(R.id.login_main_Layout)
    RelativeLayout rLayout;

    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.login_logo_imageView)
    AppCompatImageView imagelogin;

    // Butter Knife BindView AppCompatEditText
    @BindView(R.id.input_login_email)
    AppCompatEditText _loginEmailText;
    @BindView(R.id.input_login_password)
    AppCompatEditText _loginPasswordText;

    // Butter Knife BindView AppCompatButton
    @BindView(R.id.login_button)
    AppCompatButton _loginButton;

    // Butter Knife BindView LinearLayout
    @BindView(R.id.ll_login_forgot_password)
    LinearLayout _loginForgotPassword;
    @BindView(R.id.ll_login_signup)
    LinearLayout _loginSignup;

    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.tv_hotel_name)
    AppCompatTextView _loginHotelName;
    @BindView(R.id.tv_login_version)
    AppCompatTextView _loginVersion;

    // Butter Knife BindView TextInputLayout
    @BindView(R.id.text_input_layout_login_email)
    TextInputLayout _loginEmailTextInput;
    @BindView(R.id.text_input_layout_login_password)
    TextInputLayout _loginPasswordTextInput;

    // Butter Knife BindView TextInputLayout
    @BindView(R.id.remember_me)
    AppCompatCheckBox _rememberMe;

    // Butter Knife BindView ProgressBar
    @BindView(R.id.pb_login)
    ProgressBar pbLogin;

    // Session Manager Class
    Session session;
    // Settings Class
    Settings settings;

    private boolean is_logged_in = false;
    private boolean remember_me = false;
    private KenBurnsView mKenBurns;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());
        //settings
        settings = new Settings(getApplicationContext());

        getFirebaseInstanceId();
        //Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.drawable.logo).into(imagelogin);
        //checkNetwork(findViewById(android.R.id.content), LoginActivity.this);

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.hotel);
        loadeImage();

        _loginHotelName.setText(settings.getHotelName());

//        try {
//            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
//            _loginVersion.setText(getString(R.string.text_version) + " " + pInfo.versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

        if (session.getIsLoggedIn()) {
            _loginEmailText.setText(session.getUserName());
            _loginPasswordText.setText(session.getUserPassword());
        }

        inputValidation = new InputValidation(getApplicationContext());

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inputTextValidation()) {
                    try {
                        login();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                    }
                }
            }
        });

        _loginForgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the ForgotPasswordActivity
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        _loginSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the SignupActivity
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        loadeImage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**********************************(  Login Logic  )*************************************/
    public void login() {

        final String uname = _loginEmailText.getText().toString();
        final String pwd = _loginPasswordText.getText().toString();

        pbLogin.setVisibility(View.VISIBLE);
        _loginButton.setEnabled(false);

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<LoginResponse> userCall = service.getGuestQuery(uname, pwd);

        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                pbLogin.setVisibility(View.GONE);
                _loginButton.setEnabled(true);

                if (response.raw().code() == 200) {

                    LoginResponse _Response = response.body();

                    if (_Response.getSuccess()) {

                        remember_me = _rememberMe.isChecked();
                        is_logged_in = _rememberMe.isChecked();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.setNewToken(true);
                        session.createNewGuestSession(
                                1,
                                _Response.getGuest().getISResident(),
                                _Response.getGuest().getHasHistory(),
                                is_logged_in,
                                remember_me,
                                uname,
                                pwd,
                                _Response.getGuest().getDateArrivee(),
                                _Response.getGuest().getDateDepart(),
                                _Response.getGuest().getChambre(),
                                _Response.getGuest().getEmail(),
                                _Response.getGuest().getNom(),
                                _Response.getGuest().getPrenom(),
                                _Response.getGuest().getPhone(),
                                _Response.getGuest().getDateNaissance(),
                                _Response.getGuest().getAdresse(),
                                _Response.getGuest().getNationaliteName(),
                                _Response.getGuest().getEtatResa(),
                                _Response.getGuest().getResaId(),
                                _Response.getGuest().getResaGroupeId(),
                                _Response.getGuest().getResaPaxId(),
                                _Response.getGuest().getClientId(),
                                _Response.getGuest().getFactureId(),
                                _Response.getGuest().getFactureAnnee(),
                                _Response.getGuest().getNationaliteId());

                        //Start the HomeScreenActivity
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {

                        switch (_Response.getError()) {
                            case 0:
                                showSnackbar(findViewById(android.R.id.content), _Response.getMessage());
                                break;
                            case 1:
                                showSnackbar(findViewById(android.R.id.content), getString(R.string.wrong_login));
                                break;
                            case 2:
                                showSnackbar(findViewById(android.R.id.content), getString(R.string.inactive_account));
                                break;
                            default:
                                showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                                break;
                        }
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pbLogin.setVisibility(View.GONE);
                _loginButton.setEnabled(true);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    //This method is to validate the EditText valus.
    public boolean inputTextValidation() {

        if (!inputValidation.isInputEditTextFilled(_loginEmailText, _loginEmailTextInput, getString(R.string.error_message_username_is_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(_loginPasswordText, _loginPasswordTextInput, getString(R.string.error_message_password_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextShort(_loginPasswordText, _loginPasswordTextInput, getString(R.string.error_message_password_too_short), 4)) {
            return false;
        }

        //Return true if all the inputs are valid
        return true;

    }

    private void loadeImage() {

        Picasso.get().load(BASE_URL + "Android/pics_guest/hotel.jpg").into(new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {
            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
                mKenBurns.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

        });
    }

    // Get Firebase InstanceId
    public void getFirebaseInstanceId() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        session.setFCMToken(task.getResult().getToken());
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e(TAG, msg);
                    }
                });
    }

}
