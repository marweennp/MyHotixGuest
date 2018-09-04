package com.hotix.myhotixguest.activitys;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.fragments.ActivitiesFragment;
import com.hotix.myhotixguest.fragments.ComplaintsFragment;
import com.hotix.myhotixguest.fragments.HomeFragment;
import com.hotix.myhotixguest.fragments.OrdersFragment;
import com.hotix.myhotixguest.fragments.ReviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        OrdersFragment.OnFragmentInteractionListener,
        ComplaintsFragment.OnFragmentInteractionListener,
        ActivitiesFragment.OnFragmentInteractionListener,
        ReviewFragment.OnFragmentInteractionListener {

    // Butter Knife BindViews
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    // Fragments
    Fragment homeFragment;
    Fragment ordersFragment;
    Fragment complaintsFragment;
    Fragment activitesFragment;
    Fragment reviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ButterKnife.bind(this);

        homeFragment = new HomeFragment();
        ordersFragment = new OrdersFragment();
        complaintsFragment = new ComplaintsFragment();
        activitesFragment = new ActivitiesFragment();
        reviewFragment = new ReviewFragment();

        loadFragment(homeFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_Profile:
                        loadFragment(homeFragment);
                        return true;
                    case R.id.navigation_bill:
                        loadFragment(ordersFragment);
                        return true;
                    case R.id.navigation_complaints:
                        loadFragment(complaintsFragment);
                        return true;
                    case R.id.navigation_orders:
                        loadFragment(activitesFragment);
                        return true;
                    case R.id.navigation_review:
                        loadFragment(reviewFragment);
                        return true;
                }
                return false;
            }
        });
    }

    // load fragments
    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
