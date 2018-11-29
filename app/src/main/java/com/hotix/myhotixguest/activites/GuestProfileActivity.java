package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.helpers.Settings;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.RECEIVE_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;
import static com.hotix.myhotixguest.helpers.Utils.stringEmptyOrNull;

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

    //Settings
    @BindView(R.id.settings_notification_switch)
    SwitchCompat settings_notification;
    @BindView(R.id.settings_nearby_radius_acsb)
    AppCompatSeekBar settings_nearby_radius;
    @BindView(R.id.settings_nearby_radius_value_actv)
    AppCompatTextView settings_nearby_radius_value;

    // Session Manager Class
    Session session;
    // Settings Class
    Settings settings;
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

        //settings
        settings = new Settings(getApplicationContext());
        RECEIVE_NOTIFICATION = settings.getReceiveNotification();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        settings_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    settings_notification.setText("ON");
                    settings.setReceiveNotification(true);
                    RECEIVE_NOTIFICATION = true;
                } else {
                    settings_notification.setText("OFF");
                    settings.setReceiveNotification(false);
                    RECEIVE_NOTIFICATION = true;
                }
            }
        });

        settings_nearby_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                settings_nearby_radius_value.setText(progress + " m");
                settings.setNearbyRadius(progress);
            }
        });

        loadData();
        loadSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
    }

    @OnClick(R.id.edit_profile_details_save_btn)
    public void saveProfile() {
        if (profileValidation()) {
            try {
                updateProfile();
            } catch (Exception e) {
                showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
            }
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
            try {
                updatePassword();
            } catch (Exception e) {
                showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
            }
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
        startDatePickerDialog(editProfileDetailsBirthDateEt);
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //______________________________________________________________________________________________

    private void loadData() {

        if (!stringEmptyOrNull(session.getPrenom())) {
            profileDetailsFirstName.setText(session.getPrenom().trim());
        } else {
            profileDetailsFirstName.setText("-");
        }
        if (!stringEmptyOrNull(session.getNom())) {
            profileDetailsLastName.setText(session.getNom().trim());
        } else {
            profileDetailsLastName.setText("-");
        }
        if (!stringEmptyOrNull(session.getNationaliteName())) {
            profileDetailsNationality.setText(session.getNationaliteName().trim());
        } else {
            profileDetailsNationality.setText("-");
        }
        if (!stringEmptyOrNull(session.getBirthDay())) {
            profileDetailsBirthDate.setText(dateFormater(session.getBirthDay(), "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy").trim());
        } else {
            profileDetailsBirthDate.setText("-");
        }
        if (!stringEmptyOrNull(session.getAddress())) {
            profileDetailsAddress.setText(session.getAddress().trim());
        } else {
            profileDetailsAddress.setText("-");
        }
        if (!stringEmptyOrNull(session.getPhone())) {
            profileDetailsPhone.setText(session.getPhone().trim());
        } else {
            profileDetailsPhone.setText("-");
        }
        if (!stringEmptyOrNull(session.getEmail())) {
            profileDetailsMail.setText(session.getEmail().trim());
        } else {
            profileDetailsMail.setText("-");
        }


    }

    private void loadSettings() {

        settings_notification.setChecked(settings.getReceiveNotification());
        settings_nearby_radius.setProgress(settings.getNearbyRadius());
        settings_nearby_radius_value.setText(settings.getNearbyRadius() + " m");


    }

    private void loadEt() {

        if (!stringEmptyOrNull(session.getPrenom())) {
            editProfileDetailsFirstNameEt.setText(session.getPrenom().trim());
        }
        if (!stringEmptyOrNull(session.getNom())) {
            editProfileDetailsLastNameEt.setText(session.getNom().trim());
        }
        if (!stringEmptyOrNull(session.getBirthDay())) {
            editProfileDetailsBirthDateEt.setText(dateFormater(session.getBirthDay(), "yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy").trim());
        }
        if (!stringEmptyOrNull(session.getAddress())) {
            editProfileDetailsAddressEt.setText(session.getAddress().trim());
        }
        if (!stringEmptyOrNull(session.getPhone())) {
            editProfileDetailsPhoneEt.setText(session.getPhone().trim());
        }
        if (!stringEmptyOrNull(session.getEmail())) {
            editProfileDetailsMailEt.setText(session.getEmail().trim());
        }

    }

    private void updatePassword() {

        final String password = profileLoginDetailsNewPssEt.getText().toString();
        final String userId = session.getClientId().toString();

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
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

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
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

    private void startDatePickerDialog(final AppCompatEditText et) {
        Calendar currentTime = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                et.setText(dateFormatter.format(newDate.getTime()));
            }

        }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

}
