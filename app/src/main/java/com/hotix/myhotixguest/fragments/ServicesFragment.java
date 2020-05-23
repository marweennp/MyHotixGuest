package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activites.ChangeActivity;
import com.hotix.myhotixguest.activites.OrdersActivity;
import com.hotix.myhotixguest.activites.RestaurantReservationActivity;
import com.hotix.myhotixguest.activites.ReveilActivity;
import com.hotix.myhotixguest.adapters.ServicesGridAdapter;
import com.hotix.myhotixguest.models.ServicesMenuItem;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;


public class ServicesFragment extends Fragment {

    private Toolbar toolbar;
    private GridView gvMenu;

    private ArrayList<ServicesMenuItem> mItems;
    private ServicesGridAdapter mGridAdapter;

    public ServicesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.services_text);

        mItems = new ArrayList<ServicesMenuItem>();

        mItems.add(new ServicesMenuItem(1, getString(R.string.text_change), getResources().getDrawable(R.drawable.svg_change_white_512)));
        mItems.add(new ServicesMenuItem(2, getString(R.string.text_orders), getResources().getDrawable(R.drawable.svg_hand_white_512)));
        mItems.add(new ServicesMenuItem(3, getString(R.string.text_restaurant), getResources().getDrawable(R.drawable.svg_dining_table_white_512)));
        mItems.add(new ServicesMenuItem(4, getString(R.string.text_wake_up), getResources().getDrawable(R.drawable.svg_alarm_clock_white_512)));

        gvMenu = (GridView) getActivity().findViewById(R.id.gv_services_fragment_menu);
        mGridAdapter = new ServicesGridAdapter(getActivity().getApplicationContext(), mItems);
        gvMenu.setAdapter(mGridAdapter);

        gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                switch (mItems.get(position).getId()) {
                    case 1:
                        //Start the Change Activity
                        i = new Intent(getActivity().getApplicationContext(), ChangeActivity.class);
                        startActivity(i);
                        break;

                    case 2:
                        //Start the Orders Activity
                        i = new Intent(getActivity().getApplicationContext(), OrdersActivity.class);
                        startActivity(i);
                        break;

                    case 3:
                        //Start the Change Activity
                        i = new Intent(getActivity().getApplicationContext(), RestaurantReservationActivity.class);
                        startActivity(i);
                        break;

                    case 4:
                        //Start the Change Activity
                        i = new Intent(getActivity().getApplicationContext(), ReveilActivity.class);
                        startActivity(i);
                        break;
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
