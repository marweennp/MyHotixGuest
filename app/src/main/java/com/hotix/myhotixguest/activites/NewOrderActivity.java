package com.hotix.myhotixguest.activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.hotix.myhotixguest.models.CartItem;
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

import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_CART;
import static com.hotix.myhotixguest.helpers.ConstantConfig.GLOBAL_ORDER;
import static com.hotix.myhotixguest.helpers.Utils.setBaseUrl;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class NewOrderActivity extends AppCompatActivity {


    FamilySpinnerAdapter familySpinnerAdapter;
    SubFamilySpinnerAdapter subFamilySpinnerAdapter;
    @BindView(R.id.orders_sort_menu_family_sp)
    AppCompatSpinner family_sp;
    @BindView(R.id.orders_sort_sub_family_sp)
    AppCompatSpinner sub_family_sp;

    @BindView(R.id.orders_sort_menu_search_et)
    AppCompatEditText sortMenuSearchEt;

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

    //
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
    private int amount = 1;
    private Double total = 0.0;

    private Drawable mIconOne, mIconTwo;

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

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_cart_grey_512, this.getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, this.getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_cart_grey_512, this.getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, this.getTheme());
        }

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
        for (CartItem obj : GLOBAL_CART) {
            price += obj.getPrixUnitaire() * obj.getQuantite();
        }
        cartTotalTv.setText(formatter.format(price) + "");
        cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadeData();
                } catch (Exception e) {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                }
            }
        });

        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    loadeData();
                } catch (Exception e) {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
                }
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
                    sFamilles.add(new SFamille(-1, getString(R.string.all_sub)));
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
                emptyListIcon.setImageDrawable(mIconOne);
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
                emptyListIcon.setImageDrawable(mIconOne);
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
                startAddToCartDialog();
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
                GLOBAL_ORDER.setHotelId(session.getHotelId());
                GLOBAL_ORDER.setResaId(session.getResaId());
                GLOBAL_ORDER.setGroupeId(session.getResaGroupeId());
                GLOBAL_ORDER.setPaxId(session.getResaPaxId());
                GLOBAL_ORDER.setDetails(GLOBAL_CART);
                if (GLOBAL_CART.size() > 0) {
                    startActivity(i);
                    finish();
                } else {
                    showSnackbar(findViewById(android.R.id.content), getString(R.string.cart_is_empty));
                }

            }
        });


        sortMenuSearchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                filterList(sortMenuSearchEt.getText().toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBaseUrl(this);
        try {
            loadeData();
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
        price = 0.0;
        for (CartItem obj : GLOBAL_CART) {
            price += obj.getPrixUnitaire() * obj.getQuantite();
        }
        cartTotalTv.setText(formatter.format(price) + "");
        cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");
    }

    private void loadeData() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
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
                    emptyListIcon.setImageDrawable(mIconOne);
                    listView.setEmptyView(emptyListView);

                    familles.clear();
                    familles.add(new Famille(-1, getString(R.string.all_families)));
                    familles.addAll(dataModels);
                    familySpinnerAdapter = new FamilySpinnerAdapter(getApplicationContext(), familles);
                    family_sp.setAdapter(familySpinnerAdapter);
                    //adapter.getFilter().filter("F");
                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Famille>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                listView.setEmptyView(emptyListView);
                showSnackbar(findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void filterList(String str) {
        ArrayList<Produit> tempProduits = new ArrayList<Produit>();
        if (str != null && str.length() > 0) {
            tempProduits.clear();
            for (Produit prod : produits) {
                if (prod.getName().toLowerCase().contains(str.toLowerCase())) {
                    tempProduits.add(prod);
                }
            }

        } else {
            tempProduits.clear();
            tempProduits = produits;
        }

        adapter = new ProductAdapter(tempProduits, getApplicationContext());
        listView.setAdapter(adapter);
        emptyListText.setText(R.string.no_products_to_show);
        emptyListIcon.setImageDrawable(mIconOne);
        listView.setEmptyView(emptyListView);

    }

    private void startAddToCartDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewOrderActivity.this);

        amount = 1;
        total = 0.0;

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_to_cart, null);
        AppCompatTextView prodNameTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_prod_name);
        AppCompatTextView prodPriceTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_prod_price);
        final AppCompatTextView prodTotalTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_total_price);
        final AppCompatTextView prodAmountTv = (AppCompatTextView) mView.findViewById(R.id.add_to_cart_dialog_amount_tv);
        AppCompatImageView addProd = (AppCompatImageView) mView.findViewById(R.id.add_to_cart_dialog_add_icon);
        AppCompatImageView removeProd = (AppCompatImageView) mView.findViewById(R.id.add_to_cart_dialog_remove_icon);
        AppCompatButton confirmBt = (AppCompatButton) mView.findViewById(R.id.btn_Add);
        AppCompatButton removeBt = (AppCompatButton) mView.findViewById(R.id.btn_remove);
        AppCompatButton cancelBt = (AppCompatButton) mView.findViewById(R.id.btn_cancel);

        removeBt.setVisibility(View.GONE);

        prodNameTv.setText(produit.getName());
        prodPriceTv.setText(getString(R.string.price) +" "+ formatter.format(produit.getPrix()) + "");
        total += Double.valueOf(formatter.format(produit.getPrix()));
        prodTotalTv.setText(getString(R.string.total) +" "+ formatter.format(total) + "");
        prodAmountTv.setText("" + amount);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount < 10000) {
                    amount++;
                    total = 0.0;
                    for (int i = 1; i <= amount; i++) {
                        total += Double.valueOf(formatter.format(produit.getPrix()));
                    }
                    prodTotalTv.setText(getString(R.string.total) +" "+ formatter.format(total) + "");
                    prodAmountTv.setText("" + amount);
                }
            }
        });

        removeProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 1) {
                    amount--;
                    total = 0.0;
                    for (int i = 1; i <= amount; i++) {
                        total += Double.valueOf(produit.getPrix());
                    }
                    prodTotalTv.setText(getString(R.string.total)+" "+ formatter.format(total) + "");
                    prodAmountTv.setText("" + amount);
                }
            }
        });

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = 0;
                for (CartItem obj : GLOBAL_CART) {
                    if (obj.getProduit() == produit.getId()) {
                        amount += obj.getQuantite();
                        GLOBAL_CART.remove(index);
                    }
                    index++;
                }
                GLOBAL_CART.add(new CartItem(produit.getId(), produit.getName().trim(), amount, produit.getPrix()));


                price = 0.0;
                for (CartItem obj : GLOBAL_CART) {
                    price += obj.getPrixUnitaire() * obj.getQuantite();
                }
                cartTotalTv.setText(formatter.format(price) + "");
                cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");
                dialog.dismiss();
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}
