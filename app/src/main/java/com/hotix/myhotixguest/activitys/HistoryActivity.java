package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.HistoryAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Sejour;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class HistoryActivity extends AppCompatActivity {

    private static HistoryAdapter adapter;
    // Butter Knife BindView ListView
    @BindView(R.id.stays_list)
    ListView listView;
    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Session Manager Class
    Session session;
    ArrayList<Sejour> l_sejours;

    // Butter Knife BindView LinearLayout
    @BindView(R.id.stays_progress_view)
    LinearLayout progressView;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.stays_empty_list_text_view)
    AppCompatTextView emptyListText;
    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.stays_empty_list_icon)
    AppCompatImageView emptyListIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        l_sejours = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.my_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadeStays();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Intent i = new Intent(getApplicationContext(), ReservationDetailsActivity.class);
                i.putExtra("resaId", l_sejours.get(position).getResaId().toString());
                i.putExtra("histo", "histo");

                startActivity(i);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadeStays() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Sejour>> billCall = service.getStayHistoryQuery(session.getClientId().toString());

        progressView.setVisibility(View.VISIBLE);

        billCall.enqueue(new Callback<ArrayList<Sejour>>() {
            @Override
            public void onResponse(Call<ArrayList<Sejour>> call, Response<ArrayList<Sejour>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {
                    l_sejours = response.body();

                    adapter = new HistoryAdapter(l_sejours, getApplicationContext());
                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_history_to_show);
                    listView.setEmptyView(findViewById(R.id.Stays_empty));

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Sejour>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.baseline_signal_wifi_off_24);
                listView.setEmptyView(findViewById(R.id.Stays_empty));
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }


}
