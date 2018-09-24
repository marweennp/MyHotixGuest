package com.hotix.myhotixguest.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.DetailsPax;
import com.hotix.myhotixguest.models.Sejour;
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

    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_ibt_refresh)
    AppCompatImageButton emptyListRefresh;
    @BindView(R.id.guest_profile_main_container)
    NestedScrollView guestProfileMainContainer;

    AppCompatButton logoutDialogYesBtn;
    AppCompatButton logoutDialogCancelBtn;

    private DetailsPax pax;
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
        toolbarTitle.setText("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

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

    //This method is to start a dialog window .
    private void startLogoutDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());

        View mView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        logoutDialogYesBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_yes_btn);
        logoutDialogCancelBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_cancel_btn);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        logoutDialogYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.clearSessionDetails();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                dialog.dismiss();

            }
        });

        logoutDialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //___________________________Load data _______________________

    private void loadData() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Sejour> userCall = service.getStayQuery(session.getResaId().toString());

        progressView.setVisibility(View.VISIBLE);
        guestProfileMainContainer.setVisibility(View.GONE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<Sejour>() {
            @Override
            public void onResponse(Call<Sejour> call, Response<Sejour> response) {

                progressView.setVisibility(View.GONE);
                guestProfileMainContainer.setVisibility(View.VISIBLE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    Sejour sejour = response.body();

                    pax = sejour.getDetailsPax().get(0);

                    profileDetailsFirstName.setText(sejour.getDetailsPax().get(0).getClientPrenom());
                    profileDetailsLastName.setText(sejour.getDetailsPax().get(0).getClientNom());
                    profileDetailsNationality.setText(sejour.getDetailsPax().get(0).getClientNationalite());
                    profileDetailsBirthDate.setText(sejour.getDetailsPax().get(0).getClientDateNaissance());
                    profileDetailsAddress.setText(sejour.getDetailsPax().get(0).getClientAdresse());
                    profileDetailsPhone.setText(sejour.getDetailsPax().get(0).getClientPhone());
                    profileDetailsMail.setText(sejour.getDetailsPax().get(0).getClientEmail());

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<Sejour> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.baseline_signal_wifi_off_24);
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });

    }

    private void loadEt() {

        editProfileDetailsFirstNameEt.setText(pax.getClientPrenom());
        editProfileDetailsLastNameEt.setText(pax.getClientNom());
        editProfileDetailsNationalityEt.setText(pax.getClientNationalite());
        editProfileDetailsBirthDateEt.setText(pax.getClientDateNaissance());
        editProfileDetailsAddressEt.setText(pax.getClientAdresse());
        editProfileDetailsPhoneEt.setText(pax.getClientPhone());
        editProfileDetailsMailEt.setText(pax.getClientEmail());

    }

}
