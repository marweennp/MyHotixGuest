package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.FamilySpinnerAdapter;
import com.hotix.myhotixguest.adapters.ProductAdapter;
import com.hotix.myhotixguest.adapters.SubFamilySpinnerAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Famille;
import com.hotix.myhotixguest.models.Produit;
import com.hotix.myhotixguest.models.SFamille;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_CART;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class NewOrderActivity extends AppCompatActivity {


    FamilySpinnerAdapter familySpinnerAdapter;
    SubFamilySpinnerAdapter subFamilySpinnerAdapter;
    @BindView(R.id.orders_sort_menu_family_sp)
    AppCompatSpinner family_sp;
    @BindView(R.id.orders_sort_sub_family_sp)
    AppCompatSpinner sub_family_sp;

    // Butter Knife BindView RelativeLayout
    @BindView(R.id.orders_sort_menu_title_view)
    RelativeLayout sortMenuTitleView;
    @BindView(R.id.orders_sort_menu_family_view)
    RelativeLayout sortMenuFamilyView;
    @BindView(R.id.orders_sort_menu_sub_family_view)
    RelativeLayout sortMenuSubFamilyView;

    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.orders_sort_menu_title_icon)
    AppCompatImageView sortMenuTitleIcon;

    // Butter Knife BindView NestedScrollView
    @BindView(R.id.orders_sort_menu_filters_view)
    NestedScrollView sortMenuFiltersView;

    // Butter Knife BindView PullRefreshLayout
    @BindView(R.id.orders_list_pull_to_refresh)
    PullRefreshLayout pullLayout;

    // Butter Knife BindView ListView
    @BindView(R.id.orders_list)
    ListView listView;
    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_main_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_msg_tv)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_icon_iv)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_refresh_bt)
    AppCompatButton emptyListRefresh;
    @BindView(R.id.complaints_list_sort_menu)
    LinearLayout listSortMenu;
    @BindView(R.id.orders_cart_view)
    RelativeLayout ordersCartView;
    @BindView(R.id.orders_show_cart_view)
    RelativeLayout ordersShowCartView;
    @BindView(R.id.orders_cart_total_tv)
    AppCompatTextView cartTotalTv;
    @BindView(R.id.orders_cart_show_products_count_tv)
    AppCompatTextView cartShowProductsCountTv;
    private ArrayList<Famille> dataModels;
    private ArrayList<Produit> produits;
    private ArrayList<SFamille> sFamilles;
    private ArrayList<Famille> familles;
    private Produit produit;
    private ProductAdapter adapter;
    // Session Manager Class
    private Session session;
    //
    private Double price = 0.0;
    private int familles_id = -1;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        dataModels = new ArrayList<>();
        produits = new ArrayList<>();
        sFamilles = new ArrayList<>();
        familles = new ArrayList<>();

        price = 0.0;
        for (Produit obj : GLOBAL_CART) {
            price += Double.valueOf(obj.getPrix());
        }
        cartTotalTv.setText(formatter.format(price));
        cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadeData();
            }
        });

        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadeData();
                sortMenuFiltersView.setVisibility(View.GONE);
                sortMenuTitleIcon.setImageResource(R.drawable.ic_expand_more);
                pullLayout.setRefreshing(false);
            }
        });

        family_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                familles_id = familles.get(position).getId();
                produits.clear();
                if (!(familles.get(position).getId() == -1)) {
                    sFamilles.clear();
                    sFamilles.add(new SFamille(-1, "All Sub"));
                    for (Famille obj : dataModels) {
                        if (obj.getId() == familles.get(position).getId()) {
                            sFamilles.addAll(obj.getSFamilles());
                            for (SFamille sf : obj.getSFamilles()) {
                                produits.addAll(sf.getProduits());
                            }
                        }
                    }
                    subFamilySpinnerAdapter = new SubFamilySpinnerAdapter(getApplicationContext(), sFamilles);
                    sub_family_sp.setAdapter(subFamilySpinnerAdapter);
                    sortMenuSubFamilyView.setVisibility(View.VISIBLE);
                } else {
                    sortMenuSubFamilyView.setVisibility(View.GONE);
                    for (Famille obj : dataModels) {
                        for (SFamille sf : obj.getSFamilles()) {
                            produits.addAll(sf.getProduits());
                        }
                    }
                }
                adapter = new ProductAdapter(produits, getApplicationContext());
                listView.setAdapter(adapter);
                emptyListText.setText(R.string.no_products_to_show);
                emptyListIcon.setImageResource(R.drawable.ic_shopping_cart_white_24);
                listView.setEmptyView(emptyListView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sub_family_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                produits.clear();
                if (!(sFamilles.get(position).getId() == -1)) {
                    for (Famille obj : dataModels) {
                        for (SFamille sf : obj.getSFamilles()) {
                            if (sf.getId() == sFamilles.get(position).getId()) {
                                produits.addAll(sf.getProduits());
                            }
                        }
                    }
                } else {
                    for (Famille obj : dataModels) {
                        if (obj.getId() == familles_id) {
                            for (SFamille sf : obj.getSFamilles()) {
                                produits.addAll(sf.getProduits());
                            }
                        }
                    }
                }
                adapter = new ProductAdapter(produits, getApplicationContext());
                listView.setAdapter(adapter);
                emptyListText.setText(R.string.no_products_to_show);
                emptyListIcon.setImageResource(R.drawable.ic_shopping_cart_white_24);
                listView.setEmptyView(emptyListView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                produit = produits.get(position);
                GLOBAL_CART.add(produit);
                price = 0.0;
                for (Produit obj : GLOBAL_CART) {
                    price += Double.valueOf(obj.getPrix());
                }
                cartTotalTv.setText(formatter.format(price));
                cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");

            }
        });


        //Sort Menu Title View
        sortMenuTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortMenuFiltersView.getVisibility() == View.GONE) {
                    sortMenuFiltersView.setVisibility(View.VISIBLE);
                    sortMenuTitleIcon.setImageResource(R.drawable.ic_expand_less);
                } else {
                    sortMenuFiltersView.setVisibility(View.GONE);
                    sortMenuTitleIcon.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });

        //Show Cart View
        ordersShowCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadeData();
    }

    private void loadeData() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Famille>> userCall = service.getAllProductsQuery();

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        sortMenuTitleView.setVisibility(View.GONE);
        ordersCartView.setVisibility(View.GONE);
        produits.clear();

        userCall.enqueue(new Callback<ArrayList<Famille>>() {
            @Override
            public void onResponse(Call<ArrayList<Famille>> call, Response<ArrayList<Famille>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {
                    dataModels = response.body();

                    if (dataModels.size() > 0) {

                        sortMenuTitleView.setVisibility(View.VISIBLE);
                        ordersCartView.setVisibility(View.VISIBLE);
                    }

                    for (Famille obj : dataModels) {
                        for (SFamille sf : obj.getSFamilles()) {
                            produits.addAll(sf.getProduits());
                        }
                    }

                    adapter = new ProductAdapter(produits, getApplicationContext());
                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_products_to_show);
                    emptyListIcon.setImageResource(R.drawable.ic_shopping_cart_white_24);
                    listView.setEmptyView(emptyListView);

                    familles.clear();
                    familles.add(new Famille(-1, "All Famly"));
                    familles.addAll(dataModels);
                    familySpinnerAdapter = new FamilySpinnerAdapter(getApplicationContext(), familles);
                    family_sp.setAdapter(familySpinnerAdapter);

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Famille>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.ic_dns_white_24);
                listView.setEmptyView(emptyListView);
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }


}
