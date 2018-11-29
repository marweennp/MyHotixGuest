package com.hotix.myhotixguest.activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.MenuItem;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_INFOS;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_SLIDES;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class AboutHotelActivity extends AppCompatActivity {

    // Butter Knife BindView
    @BindView(R.id.about_hotel_menu_1_view)
    RelativeLayout menu_1;
    @BindView(R.id.about_hotel_menu_1_bg_img)
    AppCompatImageView menu_1_bg;
    @BindView(R.id.about_hotel_menu_1_title)
    AppCompatTextView menu_1_tv;

    @BindView(R.id.about_hotel_menu_2_view)
    RelativeLayout menu_2;
    @BindView(R.id.about_hotel_menu_2_bg_img)
    AppCompatImageView menu_2_bg;
    @BindView(R.id.about_hotel_menu_2_title)
    AppCompatTextView menu_2_tv;

    @BindView(R.id.about_hotel_menu_3_view)
    RelativeLayout menu_3;
    @BindView(R.id.about_hotel_menu_3_bg_img)
    AppCompatImageView menu_3_bg;
    @BindView(R.id.about_hotel_menu_3_title)
    AppCompatTextView menu_3_tv;

    @BindView(R.id.about_hotel_menu_4_view)
    RelativeLayout menu_4;
    @BindView(R.id.about_hotel_menu_4_bg_img)
    AppCompatImageView menu_4_bg;
    @BindView(R.id.about_hotel_menu_4_title)
    AppCompatTextView menu_4_tv;

    @BindView(R.id.about_hotel_menu_5_view)
    RelativeLayout menu_5;
    @BindView(R.id.about_hotel_menu_5_bg_img)
    AppCompatImageView menu_5_bg;
    @BindView(R.id.about_hotel_menu_5_title)
    AppCompatTextView menu_5_tv;


    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_btn)
    AppCompatButton emptyListRefresh;
    @BindView(R.id.about_hotel_main_container)
    LinearLayoutCompat mainContainer;

    // Session Manager Class
    Session session;

    private ArrayList<MenuItem> mMenu;

    private Drawable mIconTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_hotel);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        try {
            loadMenu();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }

    @OnClick(R.id.about_hotel_menu_1_view)
    public void hotelInfo() {

        if (mMenu.size() > 0) {
            GLOBAL_SLIDES.clear();
            GLOBAL_SLIDES.addAll(mMenu.get(0).getSlides());
            GLOBAL_INFOS = mMenu.get(0).getInfos();

            Intent i = new Intent(getApplicationContext(), ImageSliderActivity.class);
            i.putExtra("nearby", true);
            startActivity(i);
        }
    }

    @OnClick(R.id.about_hotel_menu_2_view)
    public void hotelRooms() {
        if (mMenu.size() > 0) {
            GLOBAL_SLIDES.clear();
            GLOBAL_SLIDES.addAll(mMenu.get(1).getSlides());
            GLOBAL_INFOS = mMenu.get(1).getInfos();

            Intent i = new Intent(getApplicationContext(), ImageSliderActivity.class);
            startActivity(i);
        }
    }

    @OnClick(R.id.about_hotel_menu_3_view)
    public void hotelSpa() {
        if (mMenu.size() > 0) {
            GLOBAL_SLIDES.clear();
            GLOBAL_SLIDES.addAll(mMenu.get(2).getSlides());
            GLOBAL_INFOS = mMenu.get(2).getInfos();

            Intent i = new Intent(getApplicationContext(), ImageSliderActivity.class);
            startActivity(i);
        }
    }

    @OnClick(R.id.about_hotel_menu_4_view)
    public void hotelResto() {
        if (mMenu.size() > 0) {
            GLOBAL_SLIDES.clear();
            GLOBAL_SLIDES.addAll(mMenu.get(3).getSlides());
            GLOBAL_INFOS = mMenu.get(3).getInfos();

            Intent i = new Intent(getApplicationContext(), ImageSliderActivity.class);
            startActivity(i);
        }
    }

    @OnClick(R.id.about_hotel_menu_5_view)
    public void hotelConf() {
        if (mMenu.size() > 0) {
            GLOBAL_SLIDES.clear();
            GLOBAL_SLIDES.addAll(mMenu.get(4).getSlides());
            GLOBAL_INFOS = mMenu.get(4).getInfos();

            Intent i = new Intent(getApplicationContext(), ImageSliderActivity.class);
            startActivity(i);
        }
    }

    /**
     * Load The Menu**********************************************************************************
     **/

    private void loadMenu() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ArrayList<MenuItem>> userCall = service.getMenuQuery("Fr");

        progressView.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ArrayList<MenuItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuItem>> call, Response<ArrayList<MenuItem>> response) {

                progressView.setVisibility(View.GONE);
                mainContainer.setVisibility(View.VISIBLE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {

                    mMenu = new ArrayList<>();
                    mMenu = response.body();

                    if (mMenu.size() > 0) {

                        Picasso.get().load(BASE_URL + mMenu.get(0).getBackground()).fit().placeholder(R.drawable.royal_hotel_bg).fit().into(menu_1_bg);
                        Picasso.get().load(BASE_URL + mMenu.get(1).getBackground()).fit().placeholder(R.drawable.royal_hotel_bg).fit().into(menu_2_bg);
                        Picasso.get().load(BASE_URL + mMenu.get(2).getBackground()).fit().placeholder(R.drawable.royal_hotel_bg).fit().into(menu_3_bg);
                        Picasso.get().load(BASE_URL + mMenu.get(3).getBackground()).fit().placeholder(R.drawable.royal_hotel_bg).fit().into(menu_4_bg);
                        Picasso.get().load(BASE_URL + mMenu.get(4).getBackground()).fit().placeholder(R.drawable.royal_hotel_bg).fit().into(menu_5_bg);

                        menu_1_tv.setText(mMenu.get(0).getTitle());
                        menu_2_tv.setText(mMenu.get(1).getTitle());
                        menu_3_tv.setText(mMenu.get(2).getTitle());
                        menu_4_tv.setText(mMenu.get(3).getTitle());
                        menu_5_tv.setText(mMenu.get(4).getTitle());
                    } else {
                        mainContainer.setVisibility(View.GONE);
                        emptyListView.setVisibility(View.VISIBLE);
                        emptyListText.setText(R.string.server_unreachable);
                        emptyListIcon.setImageDrawable(mIconTwo);
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                    mainContainer.setVisibility(View.GONE);
                    emptyListView.setVisibility(View.VISIBLE);
                    emptyListText.setText(R.string.server_unreachable);
                    emptyListIcon.setImageDrawable(mIconTwo);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MenuItem>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

}
