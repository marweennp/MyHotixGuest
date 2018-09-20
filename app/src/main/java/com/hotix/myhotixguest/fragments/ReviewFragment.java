package com.hotix.myhotixguest.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.MyPagerAdapter;

public class ReviewFragment extends Fragment {

    private ViewPager viewPager;
    private MyPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext, btnBack;

    public ReviewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) getActivity().findViewById(R.id.layoutDots);
        btnBack = (Button) getActivity().findViewById(R.id.btn_back);
        btnNext = (Button) getActivity().findViewById(R.id.btn_next);

        // add sliders layouts
        layouts = new int[]{
                R.layout.slide_review_start,
                R.layout.slide_review_1,
                R.layout.slide_review_2,
                R.layout.slide_review_3,
                R.layout.slide_review_4,
                R.layout.slide_review_end};

        // adding bottom dots
        addBottomDots(0);
        btnBack.setVisibility(View.GONE);

        myViewPagerAdapter = new MyPagerAdapter(layouts, getActivity());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(myViewPagerListener);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() - 1;
                if (current >= 0) {
                    viewPager.setCurrentItem(current);
                } else {

                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        });

    }

    //  ViewPager Page Change Listener
    ViewPager.OnPageChangeListener myViewPagerListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // changing button text
            if (position == 0) {
                btnNext.setText(getString(R.string.next));
                btnBack.setVisibility(View.GONE);
            } else if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.done));
                btnBack.setVisibility(View.GONE);
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

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_dark));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_light));
    }

}
