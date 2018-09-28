package com.hotix.myhotixguest.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.InputValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hotix.myhotixguest.helpers.Settings.TERMS_OF_SERVICE_URL;

public class ReviewHotelActivity extends AppCompatActivity {

    // Butter Knife Bind Views
    @BindView(R.id.review_hotel_pager)
    ViewPager viewPager;
    @BindView(R.id.review_hotel_dots_layout)
    LinearLayout dotsLayout;
    @BindView(R.id.review_hotel_next_btn)
    AppCompatButton btnNext;
    @BindView(R.id.review_hotel_back_btn)
    AppCompatButton btnBack;

    private ReviewViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    private int[] layouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_hotel);
        ButterKnife.bind(this);

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

        myViewPagerAdapter = new ReviewViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(myViewPagerListener);


    }

    @OnClick(R.id.review_hotel_next_btn)
    public void nextBtn() {
        int current = viewPager.getCurrentItem() + 1;
        if (current < layouts.length) {
            viewPager.setCurrentItem(current);
        } else {
            finish();
        }
    }

    @OnClick(R.id.review_hotel_back_btn)
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

    // View pager adapter
    public class ReviewViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public ReviewViewPagerAdapter() {
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

}
