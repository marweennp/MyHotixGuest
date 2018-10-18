package com.hotix.myhotixguest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotix.myhotixguest.R;

import java.util.ArrayList;
import java.util.List;

import static com.hotix.myhotixguest.helpers.ConstantConfig.HAVE_COMPLAINT_NOTIFICATION;

public class NotificationsFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;


    public NotificationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager_note);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (HAVE_COMPLAINT_NOTIFICATION) {
            viewPager.setCurrentItem(2);
            HAVE_COMPLAINT_NOTIFICATION = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MessagesFragment(), "Messages");
        adapter.addFragment(new ComplaintsFragment(), "Reclamation");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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
            return mFragmentTitleList.get(position);
        }
    }


}
