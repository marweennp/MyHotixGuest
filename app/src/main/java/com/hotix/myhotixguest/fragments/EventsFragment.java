package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hotix.myhotixguest.activitys.EventDetailsActivity;
import com.hotix.myhotixguest.adapters.EventAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_EVENT;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class EventsFragment extends Fragment {

    private static EventAdapter adapter;
    private ArrayList<Event> dataModels;
    private ListView listView;
    // Session Manager Class
    private Session session;
    private Toolbar toolbar;
    private PullRefreshLayout pullLayout;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatButton emptyListRefresh;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        pullLayout = (PullRefreshLayout) getActivity().findViewById(R.id.event_list_pull_to_refresh);

        progressView = (LinearLayout) getActivity().findViewById(R.id.loading_view);
        emptyListView = (RelativeLayout) getActivity().findViewById(R.id.empty_list_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.list_tv_msg);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.empty_list_iv_icon);
        emptyListRefresh = (AppCompatButton) getActivity().findViewById(R.id.empty_list_refresh_btn);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        AppCompatTextView toolbarTitle = (AppCompatTextView) toolbar.findViewById(R.id.toolbar_center_title);
        toolbarTitle.setText(R.string.events_text);

        listView = (ListView) getActivity().findViewById(R.id.event_list);
        dataModels = new ArrayList<>();

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeEvents();
            }
        });

        loadeEvents();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                GLOBAL_EVENT = dataModels.get(position);
                Intent i = new Intent(getActivity(), EventDetailsActivity.class);
                startActivity(i);

            }
        });

        // listen refresh event
        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeEvents();
            }
        });

    }

    private void loadeEvents() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Event>> userCall = service.getActivitesQuery();

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                if (response.raw().code() == 200) {

                    dataModels = response.body();
                    adapter = new EventAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_activitie_to_show);
                    listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.ic_dns_white_24);
                listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));
                showSnackbar(getActivity().findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }


}
