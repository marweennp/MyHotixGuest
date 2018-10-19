package com.hotix.myhotixguest.activites;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.fragments.PaxDetailsFragment;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.StartData;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_PAX_LIST;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_START_DATA;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class EditPaxDetailsActivity extends AppCompatActivity {

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
    PaxDetailsFragment mFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    // Session Manager Class
    private Session session;
    private String resaId;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pax_details);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        loadingStartData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.guest_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resaId = extras.getString("resaId");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager_pax);
        loadResaPax();
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResaPax();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int i = 0;
        for (Pax obj : GLOBAL_PAX_LIST) {
            PaxDetailsFragment frag = PaxDetailsFragment.newInstance(i);
            adapter.addFragment(frag, obj.getPrenom());
            i++;
        }
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        for (int i = 0; i < GLOBAL_PAX_LIST.size(); i++) {
            tabLayout.getTabAt(i).setIcon(R.drawable.ic_account_circle_white_24);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }

    /**********************************(  load Resa Pax  )*************************************/
    public void loadResaPax() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Pax>> userCall = service.getPaxResaQuery(resaId);

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ArrayList<Pax>>() {
            @Override
            public void onResponse(Call<ArrayList<Pax>> call, Response<ArrayList<Pax>> response) {

                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    GLOBAL_PAX_LIST = response.body();
                    setupViewPager(viewPager);
                    setupTabIcons();
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pax>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.ic_dns_white_24);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    /**********************************(  Loading Start Data  )*************************************/
    public void loadingStartData() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<StartData> userCall = service.getAllDataQuery();

        userCall.enqueue(new Callback<StartData>() {
            @Override
            public void onResponse(Call<StartData> call, Response<StartData> response) {

                if (response.raw().code() == 200) {
                    GLOBAL_START_DATA = response.body();
                }
            }

            @Override
            public void onFailure(Call<StartData> call, Throwable t) {
            }
        });

    }

}
