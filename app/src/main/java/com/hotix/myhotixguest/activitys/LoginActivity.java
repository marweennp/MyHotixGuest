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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hotix.myhotixguest.helpers.ConnectionChecher.checkNetwork;

public class LoginActivity extends AppCompatActivity {

    // Butter Knife BindView RelativeLayout
    @BindView(R.id.login_main_Layout)
    RelativeLayout rLayout;

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

    // Timer for tests
    private static int LOGIN_TIME_OUT = 3000;

    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Picasso.get().load("http://196.203.219.164/android/pics_guest/logo.png").fit().placeholder(R.drawable.ic_launcher_foreground).into(imagelogin);


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

//                if (inputTextValidation()) {
//                    login();
//                }
                login();

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

        // TODO: Login....
        checkNetwork(findViewById(android.R.id.content), LoginActivity.this);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                        //Start the HomeScreenActivity
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(i);

                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                        finish();
                    }
                }, LOGIN_TIME_OUT);

    }

    /**********************************(  Input Validation  )*************************************/
    //This method is to validate the EditText valus.
    public boolean inputTextValidation() {

        if (!inputValidation.isInputEditTextFilled(_loginEmailText, _loginEmailTextInput, getString(R.string.error_message_email_is_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(_loginEmailText, _loginEmailTextInput, getString(R.string.error_message_email_invalid))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(_loginPasswordText, _loginPasswordTextInput, getString(R.string.error_message_password_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextShort(_loginPasswordText, _loginPasswordTextInput, getString(R.string.error_message_password_too_short), 6)) {
            return false;
        }

        //Return true if all the inputs are valid
        return true;

    }


}
