package com.hotix.myhotixguest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activites.AboutHotelActivity;
import com.hotix.myhotixguest.activites.ContactHotelActivity;
import com.hotix.myhotixguest.activites.ReviewHotelActivity;
import com.squareup.picasso.Picasso;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;

public class HotelFragment extends Fragment {

    private AppCompatImageView bgImg;
    private RelativeLayout info;
    private RelativeLayout contact;
    private RelativeLayout review;


    public HotelFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bgImg = (AppCompatImageView) getActivity().findViewById(R.id.hotel_frag_bg_img);
        Picasso
                .get()
                .load(BASE_URL + "/Android/pics_guest/hotel_bg.png")
                .placeholder(R.drawable.bg_placeholder)
                .fit()
                .into(bgImg, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.hotel).fit().into(bgImg);
                    }
                });

        info = (RelativeLayout) getActivity().findViewById(R.id.hotel_frag_menu_info);
        contact = (RelativeLayout) getActivity().findViewById(R.id.hotel_frag_menu_contact);
        review = (RelativeLayout) getActivity().findViewById(R.id.hotel_frag_menu_review);


        //Info OnClickListener
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the AboutHotelActivity
                Intent i = new Intent(getActivity(), AboutHotelActivity.class);
                startActivity(i);
            }
        });

        //Contact OnClickListener
        contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the ContactHotelActivity
                Intent i = new Intent(getActivity(), ContactHotelActivity.class);
                startActivity(i);
            }
        });

        //Review OnClickListener
        review.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start the ReviewHotelActivity
                Intent i = new Intent(getActivity(), ReviewHotelActivity.class);
                startActivity(i);
            }
        });


    }

}
