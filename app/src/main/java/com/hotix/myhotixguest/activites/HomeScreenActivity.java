package com.hotix.myhotixguest.activites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.fragments.EventsFragment;
import com.hotix.myhotixguest.fragments.HomeFragment;
import com.hotix.myhotixguest.fragments.HotelFragment;
import com.hotix.myhotixguest.fragments.NotificationsFragment;
import com.hotix.myhotixguest.fragments.ServicesFragment;
import com.hotix.myhotixguest.helpers.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_MESSAGE_NOTIFICATION;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class HomeScreenActivity extends AppCompatActivity {

    // Butter Knife BindViews
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    // Fragments
    Fragment homeFragment;
    Fragment servicesFragment;
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
        servicesFragment = new ServicesFragment();
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
                    case R.id.navigation_services:
                        if (session.getISResident()) {
                            loadFragment(servicesFragment);
                        } else {
                            showSnackbar(findViewById(android.R.id.content), getString(R.string.not_resident));
                        }
                        return true;
                    case R.id.navigation_complaints:
                        if (session.getISResident()) {
                            loadFragment(notificationsFragment);
                        } else {
                            showSnackbar(findViewById(android.R.id.content), getString(R.string.not_resident));
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

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
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
            startExitDialog();
        } else {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    /** **************************************************************************************** **/

    private void startExitDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        AppCompatButton logoutBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_yes_btn);
        AppCompatButton cancelBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_cancel_btn);

        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
