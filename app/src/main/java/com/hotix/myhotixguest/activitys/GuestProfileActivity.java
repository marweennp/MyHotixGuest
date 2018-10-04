package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
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
import com.hotix.myhotixguest.fragments.ProfileDatePickerFragment;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

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
    // For input text Validation
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);
        ButterKnife.bind(this);
        inputValidation = new InputValidation(getApplicationContext());
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
        if (profileValidation()) {
            updateProfile();
        }
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

        if (passwordValidation()) {
            updatePassword();
        }

    }

    @OnClick(R.id.profile_login_details_cancel_btn)
    public void cancelNewPassword() {
        profileLoginDetailsNewPssIl.setVisibility(View.GONE);
        profileLoginDetailsConfirmPassIl.setVisibility(View.GONE);
        profileLoginDetailsSave.setVisibility(View.GONE);
        profileLoginDetailsCancel.setVisibility(View.GONE);
        profileLoginDetailsChange.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.edit_profile_details_birth_date_et)
    public void datePicker() {
        showDatePickerDialog();
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

    //______________________________________________________________________________________________

    public void showDatePickerDialog() {
        DialogFragment newFragment = new ProfileDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void loadData() {

        profileDetailsFirstName.setText(session.getPrenom().trim());
        profileDetailsLastName.setText(session.getNom().trim());
        profileDetailsNationality.setText(session.getNationaliteName().trim());
        profileDetailsBirthDate.setText(dateFormater(session.getBirthDay(), "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy").trim());
        profileDetailsAddress.setText(session.getAddress().trim());
        profileDetailsPhone.setText(session.getPhone().trim());
        profileDetailsMail.setText(session.getEmail().trim());

    }

    private void loadEt() {

        editProfileDetailsFirstNameEt.setText(session.getPrenom().trim());
        editProfileDetailsLastNameEt.setText(session.getNom().trim());
        editProfileDetailsBirthDateEt.setText(dateFormater(session.getBirthDay(), "yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy").trim());
        editProfileDetailsAddressEt.setText(session.getAddress().trim());
        editProfileDetailsPhoneEt.setText(session.getPhone().trim());
        editProfileDetailsMailEt.setText(session.getEmail().trim());

    }

    private void updatePassword() {

        final String password = profileLoginDetailsNewPssEt.getText().toString();
        final String userId = session.getClientId().toString();

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.updatePassQuery(userId, password);

        final ProgressDialog progressDialog = new ProgressDialog(GuestProfileActivity.this, R.style.AppThemeDialog);
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

                        showSnackbar(findViewById(android.R.id.content), getString(R.string.password_updated_successfully));
                        profileLoginDetailsNewPssIl.setVisibility(View.GONE);
                        profileLoginDetailsConfirmPassIl.setVisibility(View.GONE);
                        profileLoginDetailsSave.setVisibility(View.GONE);
                        profileLoginDetailsCancel.setVisibility(View.GONE);
                        profileLoginDetailsChange.setVisibility(View.VISIBLE);
                        session.setUserPassword(password);

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

    private void updateProfile() {


        final String hotelId = "1";
        final String clientId = session.getClientId().toString().trim();
        final String email = editProfileDetailsMailEt.getText().toString().trim();
        final String phone = editProfileDetailsPhoneEt.getText().toString().trim();
        final String nom = editProfileDetailsLastNameEt.getText().toString().trim();
        final String prenom = editProfileDetailsFirstNameEt.getText().toString().trim();
        final String datenaissance = dateFormater(editProfileDetailsBirthDateEt.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        final String adresse = editProfileDetailsAddressEt.getText().toString().trim();

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.updateProfileQuery(hotelId, clientId, email, phone, nom, prenom, datenaissance, adresse);

        final ProgressDialog progressDialog = new ProgressDialog(GuestProfileActivity.this, R.style.AppThemeDialog);
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

                        showSnackbar(findViewById(android.R.id.content), getString(R.string.profile_updated_successfully));
                        profileDetailsContainer.setVisibility(View.VISIBLE);
                        editProfileDetailsContainer.setVisibility(View.GONE);
                        session.setEmail(email);
                        session.setPhone(phone);
                        session.setNom(nom);
                        session.setPrenom(prenom);
                        session.setBirthDay(dateFormater(datenaissance, "yyyyMMdd", "yyyy-MM-dd'T'hh:mm:ss"));
                        session.setAddress(adresse);

                        loadData();

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

    private boolean passwordValidation() {

        if (!inputValidation.isInputEditTextFilled(profileLoginDetailsNewPssEt, profileLoginDetailsNewPssIl, getString(R.string.error_message_password_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextShort(profileLoginDetailsNewPssEt, profileLoginDetailsNewPssIl, getString(R.string.error_message_password_too_short), 4)) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(profileLoginDetailsConfirmPassEt, profileLoginDetailsConfirmPassIl, getString(R.string.error_message_password_empty))) {
            return false;
        }

        if (!inputValidation.isInputEditTextMatches(profileLoginDetailsConfirmPassEt, profileLoginDetailsNewPssEt, profileLoginDetailsConfirmPassIl, getString(R.string.error_message_password_does_not_match))) {
            return false;
        }

        return true;
    }

    private boolean profileValidation() {

        if (!inputValidation.isInputEditTextFilled(editProfileDetailsFirstNameEt, editProfileDetailsFirstNameIl, getString(R.string.error_message_field_required))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(editProfileDetailsLastNameEt, editProfileDetailsLastNameIl, getString(R.string.error_message_field_required))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(editProfileDetailsBirthDateEt, editProfileDetailsBirthDateIl, getString(R.string.error_message_field_required))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(editProfileDetailsAddressEt, editProfileDetailsAddressIl, getString(R.string.error_message_field_required))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(editProfileDetailsPhoneEt, editProfileDetailsPhoneIl, getString(R.string.error_message_field_required))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(editProfileDetailsMailEt, editProfileDetailsMailIl, getString(R.string.error_message_field_required))) {
            return false;
        }

        return true;
    }

}
