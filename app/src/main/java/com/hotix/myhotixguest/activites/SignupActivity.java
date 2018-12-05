package com.hotix.myhotixguest.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.NationalitySpinnerAdapter;
import com.hotix.myhotixguest.adapters.PayesSpinnerAdapter;
import com.hotix.myhotixguest.adapters.SignupCiviliteSpinnerAdapter;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.models.Signup;
import com.hotix.myhotixguest.models.StartData;
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

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_START_DATA;
import static com.hotix.myhotixguest.helpers.ConstantConfig.TERMS_OF_SERVICE_URL;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class SignupActivity extends AppCompatActivity {

    // Butter Knife Bind Views
    @BindView(R.id.signup_pager)
    ViewPager viewPager;
    @BindView(R.id.signup_dots_layout)
    LinearLayout dotsLayout;
    @BindView(R.id.signup_next_btn)
    AppCompatButton btnNext;
    @BindView(R.id.signup_back_btn)
    AppCompatButton btnBack;
    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_btn)
    AppCompatButton emptyListRefresh;
    @BindView(R.id.rl_signup_main_container)
    RelativeLayout rlMainContainer;

    private Drawable mIconTwo;

    private SignupViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    private int[] layouts;
    // For input text Validation
    private InputValidation inputValidation;
    private Signup newSignup;
    //  ViewPager Page Change Listener
    ViewPager.OnPageChangeListener myViewPagerListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == 0) {
                slideOneLoading();
            }
            if (position == 1) {
                slideTwoLoading();
                final TextInputEditText text = (TextInputEditText) findViewById(R.id.signup_2_birth_date_et);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDatePickerDialog(text);
                    }
                });
            }
            if (position == 2) {
                slideThreeLoading();
            }
            if (position == 3) {
                //AppCompatTextView
                AppCompatTextView agreeTv = (AppCompatTextView) findViewById(R.id.signup_3_i_agree_tv);
                //AppCompatTextView
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.signup_3_progress_bar);
                progressBar.setVisibility(View.GONE);

                agreeTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_OF_SERVICE_URL));
                        startActivity(browserIntent);
                    }
                });
            }
            // changing button text
            if (position == 0) {
                btnNext.setText(getString(R.string.next));
                btnBack.setVisibility(View.GONE);
            } else if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.done));
                //btnBack.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnBack.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        inputValidation = new InputValidation(getApplicationContext());

        newSignup = new Signup();

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

        // add sliders layouts
        layouts = new int[]{
                R.layout.slide_signup_1,
                R.layout.slide_signup_2,
                R.layout.slide_signup_3,
                R.layout.slide_signup_4};

        // adding bottom dots
        addBottomDots(0);
        btnBack.setVisibility(View.GONE);

        myViewPagerAdapter = new SignupViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(myViewPagerListener);

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadingStartData();
                } catch (Exception e) {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        try {
            loadingStartData();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @OnClick(R.id.signup_next_btn)
    public void nextBtn() {
        int current = viewPager.getCurrentItem() + 1;
        if (current < layouts.length) {
            if (viewPager.getCurrentItem() == 0 && !slideOneValidation()) {
                return;
            }
            if (viewPager.getCurrentItem() == 1 && !slideTwoValidation()) {
                return;
            }
            if (viewPager.getCurrentItem() == 2 && !slideThreeValidation()) {
                return;
            }
            viewPager.setCurrentItem(current);
        } else {
            if (viewPager.getCurrentItem() == 3 && !termsOfServicValidation()) {
                return;
            }
            try {
                addNewUser();
            } catch (Exception e) {
                showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
            }
        }

    }

    @OnClick(R.id.signup_back_btn)
    public void backBtn() {

        int current = viewPager.getCurrentItem() - 1;
        if (current >= 0) {
            viewPager.setCurrentItem(current);
        } else {

        }

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getApplicationContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_dark));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_light));
    }

    private boolean slideOneValidation() {

        //TextInputLayout
        TextInputLayout usernameIl = (TextInputLayout) findViewById(R.id.signup_1_username_il);
        TextInputLayout passwordIl = (TextInputLayout) findViewById(R.id.signup_1_password_il);
        TextInputLayout emailIl = (TextInputLayout) findViewById(R.id.signup_1_email_il);
        TextInputLayout phoneIl = (TextInputLayout) findViewById(R.id.signup_1_phone_il);

        //TextInputEditText
        TextInputEditText usernameEt = (TextInputEditText) findViewById(R.id.signup_1_username_et);
        TextInputEditText passwordEt = (TextInputEditText) findViewById(R.id.signup_1_password_et);
        TextInputEditText emailEt = (TextInputEditText) findViewById(R.id.signup_1_email_et);
        TextInputEditText phoneEt = (TextInputEditText) findViewById(R.id.signup_1_phone_et);

        if (!inputValidation.isInputEditTextFilled(usernameEt, usernameIl, getString(R.string.error_message_username_is_empty))) {
            return false;
        } else {
            newSignup.setUserName(usernameEt.getText().toString().trim());
        }
        if (!inputValidation.isInputEditTextFilled(passwordEt, passwordIl, getString(R.string.error_message_password_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextShort(passwordEt, passwordIl, getString(R.string.error_message_password_too_short), 4)) {
            return false;
        } else {
            newSignup.setPassword(passwordEt.getText().toString().trim());
        }
        if (!inputValidation.isInputEditTextFilled(emailEt, emailIl, getString(R.string.error_message_email_is_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(emailEt, emailIl, getString(R.string.error_message_email_invalid))) {
            return false;
        } else {
            newSignup.setEmail(emailEt.getText().toString().trim());
        }
        if (!inputValidation.isInputEditTextFilled(phoneEt, phoneIl, getString(R.string.error_message_phone_is_empty))) {
            return false;
        } else {
            newSignup.setPhone(phoneEt.getText().toString().trim());
        }

        return true;
    }

    private boolean slideTwoValidation() {

        AppCompatSpinner civiliteSp = (AppCompatSpinner) findViewById(R.id.sp_signup_civility);

        //TextInputLayout
        TextInputLayout firstNameIl = (TextInputLayout) findViewById(R.id.signup_2_first_name_il);
        TextInputLayout lastNameIl = (TextInputLayout) findViewById(R.id.signup_2_last_name_il);
        TextInputLayout birthDateIl = (TextInputLayout) findViewById(R.id.signup_2_birth_date_il);

        //TextInputEditText
        TextInputEditText firstNameEt = (TextInputEditText) findViewById(R.id.signup_2_first_name_et);
        TextInputEditText lastNameEt = (TextInputEditText) findViewById(R.id.signup_2_last_name_et);
        TextInputEditText birthDateEt = (TextInputEditText) findViewById(R.id.signup_2_birth_date_et);

        if (!inputValidation.isInputEditTextFilled(firstNameEt, firstNameIl, getString(R.string.error_message_field_required))) {
            return false;
        } else {
            newSignup.setFirstName(firstNameEt.getText().toString().trim());
        }
        if (!inputValidation.isInputEditTextFilled(lastNameEt, lastNameIl, getString(R.string.error_message_field_required))) {
            return false;
        } else {
            newSignup.setLastName(lastNameEt.getText().toString().trim());
        }
        if (!inputValidation.isInputEditTextFilled(birthDateEt, birthDateIl, getString(R.string.error_message_field_required))) {
            return false;
        } else {
            newSignup.setBerthDate(dateFormater(birthDateEt.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd"));
        }

        newSignup.setCivilityId(GLOBAL_START_DATA.getCivilites().get(civiliteSp.getSelectedItemPosition()).getId());

        return true;
    }

    private boolean slideThreeValidation() {

        AppCompatSpinner nationalitySp = (AppCompatSpinner) findViewById(R.id.sp_signup_nationality);
        AppCompatSpinner countrySp = (AppCompatSpinner) findViewById(R.id.sp_signup_country);

        //TextInputLayout
        TextInputLayout addressIl = (TextInputLayout) findViewById(R.id.signup_2_address_il);

        //TextInputEditText
        TextInputEditText addressEt = (TextInputEditText) findViewById(R.id.signup_2_address_et);

        if (!inputValidation.isInputEditTextFilled(addressEt, addressIl, getString(R.string.error_message_field_required))) {
            return false;
        } else {
            newSignup.setAddress(addressEt.getText().toString().trim());
        }

        newSignup.setNationalityId(GLOBAL_START_DATA.getNationalites().get(nationalitySp.getSelectedItemPosition()).getId());
        newSignup.setCountryId(GLOBAL_START_DATA.getPays().get(countrySp.getSelectedItemPosition()).getId());

        return true;
    }

    private boolean termsOfServicValidation() {

        //AppCompatCheckBox
        AppCompatCheckBox agreeCbox = (AppCompatCheckBox) findViewById(R.id.signup_3_i_agree_cbox);

        if (!agreeCbox.isChecked()) {
            showSnackbar(findViewById(android.R.id.content), "Read the terms of service");
            return false;
        }
        return true;
    }

    private void slideOneLoading() {

        //TextInputEditText
        TextInputEditText usernameEt = (TextInputEditText) findViewById(R.id.signup_1_username_et);
        TextInputEditText passwordEt = (TextInputEditText) findViewById(R.id.signup_1_password_et);
        TextInputEditText emailEt = (TextInputEditText) findViewById(R.id.signup_1_email_et);
        TextInputEditText phoneEt = (TextInputEditText) findViewById(R.id.signup_1_phone_et);

        usernameEt.setText(newSignup.getUserName());
        passwordEt.setText(newSignup.getPassword());
        emailEt.setText(newSignup.getEmail());
        phoneEt.setText(newSignup.getPhone());
    }

    private void slideTwoLoading() {

        AppCompatSpinner civiliteSp = (AppCompatSpinner) findViewById(R.id.sp_signup_civility);
        // loade spinner civilite
        if (GLOBAL_START_DATA.getCivilites() != null) {
            SignupCiviliteSpinnerAdapter civiliteSpinnerAdapter = new SignupCiviliteSpinnerAdapter(getApplicationContext(), GLOBAL_START_DATA.getCivilites());
            civiliteSp.setAdapter(civiliteSpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getCivilites().size(); i++) {
                if (GLOBAL_START_DATA.getCivilites().get(i).getId() == newSignup.getCivilityId())
                    civiliteSp.setSelection(i);
            }
        }

        //TextInputEditText
        TextInputEditText firstNameEt = (TextInputEditText) findViewById(R.id.signup_2_first_name_et);
        TextInputEditText lastNameEt = (TextInputEditText) findViewById(R.id.signup_2_last_name_et);
        TextInputEditText birthDateEt = (TextInputEditText) findViewById(R.id.signup_2_birth_date_et);

        firstNameEt.setText(newSignup.getFirstName());
        lastNameEt.setText(newSignup.getLastName());
        birthDateEt.setText(newSignup.getBerthDate());
    }

    private void slideThreeLoading() {

        AppCompatSpinner nationalitySp = (AppCompatSpinner) findViewById(R.id.sp_signup_nationality);
        // loade spinner
        if (GLOBAL_START_DATA.getNationalites() != null) {
            NationalitySpinnerAdapter nationalitySpinnerAdapter = new NationalitySpinnerAdapter(getApplicationContext(), GLOBAL_START_DATA.getNationalites());
            nationalitySp.setAdapter(nationalitySpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getNationalites().size(); i++) {
                if (GLOBAL_START_DATA.getNationalites().get(i).getId() == newSignup.getNationalityId())
                    nationalitySp.setSelection(i);
            }
        }

        AppCompatSpinner countrySp = (AppCompatSpinner) findViewById(R.id.sp_signup_country);
        // loade spinner
        if (GLOBAL_START_DATA.getPays() != null) {
            PayesSpinnerAdapter payesSpinnerAdapter = new PayesSpinnerAdapter(getApplicationContext(), GLOBAL_START_DATA.getPays());
            countrySp.setAdapter(payesSpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getPays().size(); i++) {
                if (GLOBAL_START_DATA.getPays().get(i).getId() == newSignup.getCountryId())
                    countrySp.setSelection(i);
            }
        }

        //TextInputEditText
        TextInputEditText addressEt = (TextInputEditText) findViewById(R.id.signup_2_address_et);

        addressEt.setText(newSignup.getAddress());
    }

    private void addNewUser() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.inscriptionQuery(
                "1",
                newSignup.getUserName(),
                newSignup.getPassword(),
                newSignup.getEmail(),
                newSignup.getPhone(),
                newSignup.getLastName(),
                newSignup.getFirstName(),
                newSignup.getBerthDate(),
                newSignup.getAddress(),
                String.valueOf(newSignup.getNationalityId()),
                String.valueOf(newSignup.getCivilityId()),
                String.valueOf(newSignup.getCountryId()));

        //ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.signup_3_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        userCall.enqueue(new Callback<ResponseMsg>() {
            @Override
            public void onResponse(Call<ResponseMsg> call, Response<ResponseMsg> response) {

                progressBar.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    ResponseMsg msg = response.body();
                    if (msg.getIsOk()) {
                        finish();
                    } else {
                        if (msg.getMessage().equals("User")) {
                            showSnackbar(findViewById(android.R.id.content), getString(R.string.used_username));
                            viewPager.setCurrentItem(0);
                            slideOneLoading();
                        } else if (msg.getMessage().equals("Mail")) {
                            showSnackbar(findViewById(android.R.id.content), getString(R.string.used_email));
                            viewPager.setCurrentItem(0);
                            slideOneLoading();
                        } else {
                            showSnackbar(findViewById(android.R.id.content), getString(R.string.something_wrong));
                        }
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.toString());
                }

            }

            @Override
            public void onFailure(Call<ResponseMsg> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    private void startDatePickerDialog(final TextInputEditText et) {
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

    /**********************************(  Loading Start Data  )*************************************/
    public void loadingStartData() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<StartData> userCall = service.getAllDataQuery();

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rlMainContainer.setVisibility(View.GONE);

        userCall.enqueue(new Callback<StartData>() {
            @Override
            public void onResponse(Call<StartData> call, Response<StartData> response) {

                rlMainContainer.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    GLOBAL_START_DATA = response.body();
                    rlMainContainer.setVisibility(View.VISIBLE);
                    progressView.setVisibility(View.GONE);
                    emptyListView.setVisibility(View.GONE);
                } else {
                    rlMainContainer.setVisibility(View.GONE);
                    progressView.setVisibility(View.GONE);
                    emptyListView.setVisibility(View.VISIBLE);
                    emptyListText.setText(R.string.server_unreachable);
                    emptyListIcon.setImageDrawable(mIconTwo);
                }
            }

            @Override
            public void onFailure(Call<StartData> call, Throwable t) {
                rlMainContainer.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    // View pager adapter
    public class SignupViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public SignupViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
