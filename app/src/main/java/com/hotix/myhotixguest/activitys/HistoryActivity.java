package com.hotix.myhotixguest.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.StayAdapter;
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

    private static StayAdapter adapter;
    // Butter Knife BindView ListView
    @BindView(R.id.stays_list)
    ListView listView;
    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Session Manager Class
    Session session;
    ArrayList<Sejour> l_sejours;

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
                i.putExtra("histo","histo");

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

        final ProgressDialog progressDialog = new ProgressDialog(HistoryActivity.this, R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        billCall.enqueue(new Callback<ArrayList<Sejour>>() {
            @Override
            public void onResponse(Call<ArrayList<Sejour>> call, Response<ArrayList<Sejour>> response) {
                progressDialog.dismiss();
                if (response.raw().code() == 200) {
                    l_sejours = response.body();

                    adapter = new StayAdapter(l_sejours, getApplicationContext());

                    listView.setAdapter(adapter);

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Sejour>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
                //Toast.makeText(getApplicationContext(), "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
