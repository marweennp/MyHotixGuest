package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activitys.HomeScreenActivity;
import com.hotix.myhotixguest.activitys.NewOrderActivity;
import com.hotix.myhotixguest.adapters.ProductAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Produit;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {


    private Toolbar toolbar;
    private PullRefreshLayout pullLayout;
    private ListView listView;
    private ArrayList<Produit> dataModels;
    private Produit produit;
    private ProductAdapter adapter;
    private FloatingActionButton floatingActionButton;
    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatButton emptyListRefresh;

    public OrdersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        pullLayout = (PullRefreshLayout) getActivity().findViewById(R.id.orders_list_pull_to_refresh);

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.orders_floatingActionButton_add);

        progressView = (LinearLayout) getActivity().findViewById(R.id.loading_view);
        emptyListView = (RelativeLayout) getActivity().findViewById(R.id.empty_list_main_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.list_msg_tv);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.empty_list_icon_iv);
        emptyListRefresh = (AppCompatButton) getActivity().findViewById(R.id.empty_list_refresh_bt);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.orders_text);

        listView = (ListView) getActivity().findViewById(R.id.orders_list);
        dataModels = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), NewOrderActivity.class);
                startActivity(i);
            }
        });

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeOrders();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                // TODO: 01/10/2018
            }
        });

        // listen refresh event
        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeOrders();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadeOrders();
    }

    private void loadeOrders() {

        pullLayout.setRefreshing(false);

//        dataModels.add(new Produit("Produit 1", "10.000", "Category 1", "Family 1", "Sub Family 1"));
//        dataModels.add(new Produit("Produit 2", "5.000", "Category 1", "Family 1", "Sub Family 2"));
//        dataModels.add(new Produit("Produit 3", "8.000", "Category 1", "Family 2", "Sub Family 3"));
//        dataModels.add(new Produit("Produit 4", "100.000", "Category 1", "Family 2", "Sub Family 4"));
//        dataModels.add(new Produit("Produit 5", "0.500", "Category 2", "Family 3", "Sub Family 5"));
//        dataModels.add(new Produit("Produit 6", "2.200", "Category 2", "Family 3", "Sub Family 6"));
//        dataModels.add(new Produit("Produit 7", "1.000", "Category 2", "Family 4", "Sub Family 7"));
//        dataModels.add(new Produit("Produit 8", "0.100", "Category 2", "Family 4", "Sub Family 8"));

        adapter = new ProductAdapter(dataModels, getActivity());
        listView.setAdapter(adapter);

        emptyListIcon.setImageResource(R.drawable.ic_add_shopping_cart_white_24);
        emptyListText.setText(R.string.no_orders_to_show);
        listView.setEmptyView(emptyListView);

    }

}
