package com.hotix.myhotixguest.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewReservationActivity extends AppCompatActivity {

    // Loading View & Empty ListView
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_btn)
    AppCompatButton emptyListRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
        ButterKnife.bind(this);


        emptyListView.setVisibility(View.VISIBLE);
        emptyListRefresh.setVisibility(View.GONE);
        emptyListText.setText("Coming Soon");
        emptyListIcon.setImageResource(R.drawable.ic_access_time_white_24);

    }
}
