package com.hotix.myhotixguest.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.fragments.EventsFragment;
import com.hotix.myhotixguest.fragments.HomeFragment;
import com.hotix.myhotixguest.fragments.HotelFragment;
import com.hotix.myhotixguest.fragments.NotificationsFragment;
import com.hotix.myhotixguest.fragments.OrdersFragment;
import com.hotix.myhotixguest.fragments.ReviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity {

    // Butter Knife BindViews
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    // Fragments
    Fragment homeFragment;
    Fragment ordersFragment;
    Fragment notificationsFragment;
    Fragment activitesFragment;
    Fragment hotelFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ButterKnife.bind(this);

        homeFragment = new HomeFragment();
        ordersFragment = new OrdersFragment();
        notificationsFragment = new NotificationsFragment();
        activitesFragment = new EventsFragment();
        hotelFragment = new HotelFragment();

        loadFragment(homeFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        loadFragment(homeFragment);
                        return true;
                    case R.id.navigation_orders:
                        loadFragment(ordersFragment);
                        return true;
                    case R.id.navigation_complaints:
                        loadFragment(notificationsFragment);
                        return true;
                    case R.id.navigation_activities:
                        loadFragment(activitesFragment);
                        return true;
                    case R.id.navigation_hotel:
                        loadFragment(hotelFragment);
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
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.navigation_home) {
            finish();
        } else {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
    }
}
