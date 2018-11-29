package com.hotix.myhotixguest.activites;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Butter Knife BindView
    // AppCompatImageView
    @BindView(R.id.forgot_password_logo_imageView)
    AppCompatImageView forgotPasswordLogo;
    //AppCompatEditText
    @BindView(R.id.input_forgot_password_email)
    AppCompatEditText forgotPasswordEmailText;
    //TextInputLayout
    @BindView(R.id.text_input_layout_forgot_password_email)
    TextInputLayout forgotPasswordEmailTextInput;
    //ppCompatButton
    @BindView(R.id.forgot_password_button)
    AppCompatButton forgotPasswordButton;
    // For input text Validation
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        inputValidation = new InputValidation(getApplicationContext());

        Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher).into(forgotPasswordLogo);

    }

    @OnClick(R.id.forgot_password_button)
    public void forgotPassword() {
        if (inputValidation()) {
            try {
                newPassword();
            } catch (Exception e) {
                showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
    }

    /**********************************(  Input Validation  )*************************************/
    //This method is to validate the EditText valus.
    public boolean inputValidation() {

        if (!inputValidation.isInputEditTextFilled(forgotPasswordEmailText, forgotPasswordEmailTextInput, getString(R.string.error_message_email_is_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(forgotPasswordEmailText, forgotPasswordEmailTextInput, getString(R.string.error_message_email_invalid))) {
            return false;
        }

        //Return true if all the inputs are valid
        return true;

    }

    public void newPassword() {

        final String mail = forgotPasswordEmailText.getText().toString();

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.resetPasswordQuery(mail);

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<ResponseMsg>() {
            @Override
            public void onResponse(Call<ResponseMsg> call, Response<ResponseMsg> response) {
                progressDialog.dismiss();
                if (response.raw().code() == 200) {
                    ResponseMsg msg = response.body();
                    if (!msg.getIsOk()) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.something_wen_wrong));
                    } else {
                        finish();
                    }
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseMsg> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }
}
