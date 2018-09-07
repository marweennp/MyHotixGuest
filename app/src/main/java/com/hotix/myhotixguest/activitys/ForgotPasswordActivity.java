package com.hotix.myhotixguest.activitys;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hotix.myhotixguest.helpers.ConnectionChecher.checkNetwork;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.forgot_password_logo_imageView)
    AppCompatImageView forgotPasswordLogo;

    // Butter Knife BindView AppCompatEditText
    @BindView(R.id.input_forgot_password_email)
    AppCompatEditText forgotPasswordEmailText;

    // Butter Knife BindView TextInputLayout
    @BindView(R.id.text_input_layout_forgot_password_email)
    TextInputLayout forgotPasswordEmailTextInput;

    // Butter Knife BindView AppCompatButton
    @BindView(R.id.forgot_password_button)
    AppCompatButton forgotPasswordButton;

    // For input text Validation
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        Picasso.get().load("http://196.203.219.164/android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher_round).into(forgotPasswordLogo);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork(findViewById(android.R.id.content), ForgotPasswordActivity.this);
    }

    /**********************************(  Input Validation  )*************************************/
    //This method is to validate the EditText valus.
    public boolean inputTextValidation() {

        //Return true if all the inputs are valid
        return true;

    }
}
