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
import com.hotix.myhotixguest.helpers.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_START_DATA;
import static com.hotix.myhotixguest.helpers.Settings.HAVE_MESSAGE_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

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
    // Session Manager Class
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

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
                        if (session.getISResident()) {
                            loadFragment(ordersFragment);
                        } else {
                            showSnackbar(findViewById(android.R.id.content), "You must be a resident to use this feature");
                        }
                        return true;
                    case R.id.navigation_complaints:
                        if (session.getISResident()) {
                            loadFragment(notificationsFragment);
                        } else {
                            showSnackbar(findViewById(android.R.id.content), "You must be a resident to use this feature");
                        }
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
        if (HAVE_MESSAGE_NOTIFICATION) {
            navigation.setSelectedItemId(R.id.navigation_complaints);
            HAVE_MESSAGE_NOTIFICATION = false;
        }

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
