package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater1;

public class GuestProfileActivity extends AppCompatActivity {

    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Butter Knife BindView LinearLayout
    @BindView(R.id.profile_details_container)
    LinearLayout profileDetailsContainer;
    @BindView(R.id.edit_profile_details_container)
    LinearLayout editProfileDetailsContainer;
    // Butter Knife BindView AppCompatButton
    @BindView(R.id.edit_profile_details_save_btn)
    AppCompatButton editProfileDetailsSaveBtn;
    @BindView(R.id.edit_profile_details_cancel_btn)
    AppCompatButton editProfileDetailsCancelBtn;
    @BindView(R.id.profile_login_details_change_pwd_btn)
    AppCompatButton profileLoginDetailsChange;
    @BindView(R.id.profile_login_details_save_btn)
    AppCompatButton profileLoginDetailsSave;
    @BindView(R.id.profile_login_details_cancel_btn)
    AppCompatButton profileLoginDetailsCancel;

    //AppCompatTextView
    @BindView(R.id.profile_details_first_name)
    AppCompatTextView profileDetailsFirstName;
    @BindView(R.id.profile_details_last_name)
    AppCompatTextView profileDetailsLastName;
    @BindView(R.id.profile_details_nationality)
    AppCompatTextView profileDetailsNationality;
    @BindView(R.id.profile_details_birth_date)
    AppCompatTextView profileDetailsBirthDate;
    @BindView(R.id.profile_details_address)
    AppCompatTextView profileDetailsAddress;
    @BindView(R.id.profile_details_phone)
    AppCompatTextView profileDetailsPhone;
    @BindView(R.id.profile_details_mail)
    AppCompatTextView profileDetailsMail;

    //TextInputLayout
    @BindView(R.id.edit_profile_details_first_name_il)
    TextInputLayout editProfileDetailsFirstNameIl;
    @BindView(R.id.edit_profile_details_last_name_il)
    TextInputLayout editProfileDetailsLastNameIl;
    @BindView(R.id.edit_profile_details_nationality_il)
    TextInputLayout editProfileDetailsNationalityIl;
    @BindView(R.id.edit_profile_details_birth_date_il)
    TextInputLayout editProfileDetailsBirthDateIl;
    @BindView(R.id.edit_profile_details_address_il)
    TextInputLayout editProfileDetailsAddressIl;
    @BindView(R.id.edit_profile_details_phone_il)
    TextInputLayout editProfileDetailsPhoneIl;
    @BindView(R.id.edit_profile_details_mail_il)
    TextInputLayout editProfileDetailsMailIl;
    @BindView(R.id.profile_login_details_new_pwd_il)
    TextInputLayout profileLoginDetailsNewPssIl;
    @BindView(R.id.profile_login_details_confirm_pwd_il)
    TextInputLayout profileLoginDetailsConfirmPassIl;

    //AppCompatEditText
    @BindView(R.id.edit_profile_details_first_name_et)
    AppCompatEditText editProfileDetailsFirstNameEt;
    @BindView(R.id.edit_profile_details_last_name_et)
    AppCompatEditText editProfileDetailsLastNameEt;
    @BindView(R.id.edit_profile_details_nationality_et)
    AppCompatEditText editProfileDetailsNationalityEt;
    @BindView(R.id.edit_profile_details_birth_date_et)
    AppCompatEditText editProfileDetailsBirthDateEt;
    @BindView(R.id.edit_profile_details_address_et)
    AppCompatEditText editProfileDetailsAddressEt;
    @BindView(R.id.edit_profile_details_phone_et)
    AppCompatEditText editProfileDetailsPhoneEt;
    @BindView(R.id.edit_profile_details_mail_et)
    AppCompatEditText editProfileDetailsMailEt;
    @BindView(R.id.profile_login_details_new_pwd_et)
    AppCompatEditText profileLoginDetailsNewPssEt;
    @BindView(R.id.profile_login_details_confirm_pwd_et)
    AppCompatEditText profileLoginDetailsConfirmPassEt;

    // Session Manager Class
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadData();
    }

    @OnClick(R.id.edit_profile_details_save_btn)
    public void saveProfile() {

    }

    @OnClick(R.id.edit_profile_details_cancel_btn)
    public void cancelEdit() {
        profileDetailsContainer.setVisibility(View.VISIBLE);
        editProfileDetailsContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.profile_login_details_change_pwd_btn)
    public void changePassword() {
        profileLoginDetailsNewPssIl.setVisibility(View.VISIBLE);
        profileLoginDetailsConfirmPassIl.setVisibility(View.VISIBLE);
        profileLoginDetailsSave.setVisibility(View.VISIBLE);
        profileLoginDetailsCancel.setVisibility(View.VISIBLE);
        profileLoginDetailsChange.setVisibility(View.GONE);

    }

    @OnClick(R.id.profile_login_details_save_btn)
    public void saveNewPassword() {

    }

    @OnClick(R.id.profile_login_details_cancel_btn)
    public void cancelNewPassword() {
        profileLoginDetailsNewPssIl.setVisibility(View.GONE);
        profileLoginDetailsConfirmPassIl.setVisibility(View.GONE);
        profileLoginDetailsSave.setVisibility(View.GONE);
        profileLoginDetailsCancel.setVisibility(View.GONE);
        profileLoginDetailsChange.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                profileDetailsContainer.setVisibility(View.GONE);
                editProfileDetailsContainer.setVisibility(View.VISIBLE);
                loadEt();
                return true;
            case R.id.action_logout:
                session.clearSessionDetails();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //___________________________Load data _______________________

    private void loadData() {

        profileDetailsFirstName.setText(session.getPrenom());
        profileDetailsLastName.setText(session.getNom());
        profileDetailsNationality.setText(session.getNationaliteName());
        profileDetailsBirthDate.setText(dateFormater1(session.getBirthDay()));
        profileDetailsAddress.setText(session.getAddress());
        profileDetailsPhone.setText(session.getPhone());
        profileDetailsMail.setText(session.getEmail());

    }

    private void loadEt() {

        editProfileDetailsFirstNameEt.setText(session.getPrenom());
        editProfileDetailsLastNameEt.setText(session.getNom());
        editProfileDetailsNationalityEt.setText(session.getNationaliteName());
        editProfileDetailsBirthDateEt.setText(dateFormater1(session.getBirthDay()));
        editProfileDetailsAddressEt.setText(session.getAddress());
        editProfileDetailsPhoneEt.setText(session.getPhone());
        editProfileDetailsMailEt.setText(session.getEmail());

    }

}
