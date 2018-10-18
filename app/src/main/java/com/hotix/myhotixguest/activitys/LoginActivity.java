package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConnectionChecher.checkNetwork;
import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;
import static com.hotix.myhotixguest.helpers.Utils.signeUpTextTowColors;

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
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.login_forgot_password_text_view)
    AppCompatTextView _loginForgotPasswordTextView;
    @BindView(R.id.login_signup_text_view)
    AppCompatTextView _loginSignupTextView;
    // Butter Knife BindView TextInputLayout
    @BindView(R.id.text_input_layout_login_email)
    TextInputLayout _loginEmailTextInput;
    @BindView(R.id.text_input_layout_login_password)
    TextInputLayout _loginPasswordTextInput;
    // Butter Knife BindView TextInputLayout
    @BindView(R.id.remember_me)
    AppCompatCheckBox _rememberMe;
    // Session Manager Class
    Session session;
    private boolean is_logged_in = false;
    private boolean remember_me = false;
    // For input text Validation
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());
        getFirebaseInstanceId();
        Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher_round).into(imagelogin);

        checkNetwork(findViewById(android.R.id.content), LoginActivity.this);

        if (session.getIsLoggedIn()) {
            _loginEmailText.setText(session.getUserName());
            _loginPasswordText.setText(session.getUserPassword());
        }

        _loginSignupTextView.setText(Html.fromHtml(signeUpTextTowColors(getString(R.string.no_account_yet), getString(R.string.sign_up), getApplicationContext())));

        inputValidation = new InputValidation(getApplicationContext());

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inputTextValidation()) { login(); }
            }
        });

        _loginForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the ForgotPasswordActivity
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        _loginSignupTextView.setOnClickListener(new View.OnClickListener() {

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**********************************(  Login Logic  )*************************************/
    public void login() {

        final String uname = _loginEmailText.getText().toString();
        final String pwd = _loginPasswordText.getText().toString();

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Guest> userCall = service.getGuestQuery(uname, pwd);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<Guest>() {
            @Override
            public void onResponse(Call<Guest> call, Response<Guest> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    Guest guest = response.body();
                    if (!(guest.getError() == -1)) {
                        showSnackbar(findViewById(android.R.id.content), "Something went wrong. please verify your login or password");
                    } else {

                        remember_me = _rememberMe.isChecked();
                        is_logged_in = _rememberMe.isChecked();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.setNewToken(true);
                        session.createNewGuestSession(
                                response.body().getISResident(),
                                response.body().getHasHistory(),
                                is_logged_in,
                                remember_me,
                                uname,
                                pwd,
                                response.body().getDateArrivee().trim(),
                                response.body().getDateDepart().trim(),
                                response.body().getChambre().trim(),
                                response.body().getEmail().trim(),
                                response.body().getNom().trim(),
                                response.body().getPrenom().trim(),
                                response.body().getPhone().trim(),
                                response.body().getDateNaissance().trim(),
                                response.body().getAdresse().trim(),
                                response.body().getNationaliteName().trim(),
                                response.body().getEtatResa(),
                                response.body().getResaId(),
                                response.body().getResaPaxId(),
                                response.body().getClientId(),
                                response.body().getFactureId(),
                                response.body().getFactureAnnee(),
                                response.body().getNationaliteId());
                        //Start the HomeScreenActivity
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(i);
                        finish();
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<Guest> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
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
