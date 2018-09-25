package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Settings.BASE_URL;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 200;
    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.splash_screen_imageView)
    AppCompatImageView splashScreen;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.splash_screen_footer_text)
    AppCompatTextView splashScreenFooter;
    // Session Manager Class
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        Picasso.get().load(BASE_URL + "/Android/pics_guest/logo.png").fit().placeholder(R.mipmap.ic_launcher_round).into(splashScreen);

        if (session.getIsLoggedIn()) {
            login(session.getUserName(), session.getUserPassword());
        } else {
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

    /**********************************(  Login Logic  )*************************************/
    public void login(final String uname, final String pwd) {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Guest> userCall = service.getGuestQuery(uname, pwd);
        final String uName = uname;
        final String uPwd = pwd;
        userCall.enqueue(new Callback<Guest>() {
            @Override
            public void onResponse(Call<Guest> call, Response<Guest> response) {

                if (response.raw().code() == 200) {
                    Guest guest = response.body();
                    if (!(guest.getError() == -1)) {
                        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        session.clearSessionDetails();
                        session.createNewGuestSession(
                                response.body().getISResident(),
                                response.body().getHasHistory(),
                                true,
                                true,
                                uName,
                                uPwd,response.body().getDateArrivee(),
                                response.body().getDateDepart(),
                                response.body().getChambre(),
                                response.body().getEmail(),
                                response.body().getNom(),
                                response.body().getPrenom(),
                                response.body().getPhone(),
                                response.body().getDateNaissance(),
                                response.body().getAdresse(),
                                response.body().getNationaliteName(),
                                response.body().getEtatResa(),
                                response.body().getResaId(),
                                response.body().getClientId(),
                                response.body().getFactureId(),
                                response.body().getFactureAnnee(),
                                response.body().getNationaliteId());
                        startActivity(i);
                        finish();
                    }

                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Guest> call, Throwable t) {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                startActivity(i);
                finish();
            }
        });

    }


}
