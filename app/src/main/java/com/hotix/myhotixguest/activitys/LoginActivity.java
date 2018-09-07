package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;

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
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class LoginActivity extends AppCompatActivity {

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
    // Session Manager Class
    Session session;
    // For input text Validation
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        Picasso.get().load("http://196.203.219.164/android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher_round).into(imagelogin);

/********************************( Signup TextView HTML Format )***********************************/
        String color1 = "#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.white)).substring(2, 8);
        String color2 = "#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)).substring(2, 8);

        String text1 = getString(R.string.no_account_yet);
        String text2 = getString(R.string.sign_up);

        String text = "<font color=" + color1 + ">" + text1 + "</font> <font color=" + color2 + "><b>" + text2 + "</b></color>";
        _loginSignupTextView.setText(Html.fromHtml(text));
/**************************************************************************************************/

        inputValidation = new InputValidation(getApplicationContext());

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (inputTextValidation()) {
                    login();
                }
                //login();

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
        checkNetwork(findViewById(android.R.id.content), LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**********************************(  Login Logic  )*************************************/
    public void login() {

        String uname = _loginEmailText.getText().toString();
        String pwd = _loginPasswordText.getText().toString();

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

                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.clearSessionDetails();
                        session.createNewGuestSession(
                                response.body().getISResident(),
                                response.body().getHasHistory(),
                                response.body().getDateArrivee(),
                                response.body().getDateDepart(),
                                response.body().getChambre(),
                                response.body().getEmail(),
                                response.body().getNom(),
                                response.body().getPrenom(),
                                response.body().getResaId(),
                                response.body().getClientId(),
                                response.body().getFactureId(),
                                response.body().getFactureAnnee());
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

    /**********************************(  Input Validation  )*************************************/
    //This method is to validate the EditText valus.
    public boolean inputTextValidation() {

        if (!inputValidation.isInputEditTextFilled(_loginEmailText, _loginEmailTextInput, getString(R.string.error_message_email_is_empty))) {
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


}
