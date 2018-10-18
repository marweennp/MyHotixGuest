package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.ImageSliderAdapter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_INFOS;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_SLIDES;

public class ImageSliderActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.image_slider_pager)
    ViewPager image_slider;
    @BindView(R.id.image_slider_container)
    LinearLayout infoContainer;
    @BindView(R.id.image_slider_info_tv)
    AppCompatTextView info_tv;
    @BindView(R.id.image_slider_places_btn)
    AppCompatButton places_btn;

    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        ButterKnife.bind(this);

        mIntent = getIntent();
        Bundle extras = mIntent.getExtras();
        if (extras != null) {
            if (extras.containsKey("nearby")) {
                places_btn.setVisibility(View.VISIBLE);
            }
        }

        info_tv.setText(Html.fromHtml(GLOBAL_INFOS));
        init();
    }

    @OnClick(R.id.image_slider_places_btn)
    public void nearbyPlaces() {
        Intent i = new Intent(getApplicationContext(), HotelNearbyPlacesActivity.class);
        startActivity(i);
    }

    private void init() {
        image_slider.setAdapter(new ImageSliderAdapter(ImageSliderActivity.this, GLOBAL_SLIDES));

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (image_slider.getCurrentItem() + 1 >= GLOBAL_SLIDES.size()) {
                    image_slider.setCurrentItem(0, true);
                } else {
                    image_slider.setCurrentItem(image_slider.getCurrentItem() + 1, true);
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, 8000);
    }

}
