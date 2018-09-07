package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.hotix.myhotixguest.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 200;
    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.splash_screen_imageView)
    AppCompatImageView splashScreen;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.splash_screen_footer_text)
    AppCompatTextView splashScreenFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        Picasso.get().load("http://196.203.219.164/android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher_round).into(splashScreen);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
