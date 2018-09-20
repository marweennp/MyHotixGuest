package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activitys.ActivitieDetailsActivity;
import com.hotix.myhotixguest.adapters.EventAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.GLOBAL_EVENT;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ActivitiesFragment extends Fragment {

    private static EventAdapter adapter;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private ArrayList<Event> dataModels;
    private ListView listView;
    // Session Manager Class
    private Session session;
    private Toolbar toolbar;
    private LinearLayout progressView;

    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        progressView = (LinearLayout) getActivity().findViewById(R.id.event_progress_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.event_empty_list_text_view);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.event_empty_list_icon);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.events_text);

        listView = (ListView) getActivity().findViewById(R.id.event_list);
        dataModels = new ArrayList<>();


        loadeEvents();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                GLOBAL_EVENT = dataModels.get(position);
                Intent i = new Intent(getActivity(), ActivitieDetailsActivity.class);
                startActivity(i);

            }
        });

    }

    private void loadeEvents() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Event>> billCall = service.getActivitesQuery();

        progressView.setVisibility(View.VISIBLE);

        billCall.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {

                    dataModels = response.body();
                    adapter = new EventAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_activitie_to_show);
                    listView.setEmptyView(getActivity().findViewById(R.id.event_empty));

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.baseline_signal_wifi_off_24);
                listView.setEmptyView(getActivity().findViewById(R.id.event_empty));
                showSnackbar(getActivity().findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }


}
