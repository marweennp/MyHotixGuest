package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.ProductAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Produit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_CART;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class NewOrderActivity extends AppCompatActivity {

    // Butter Knife BindView RelativeLayout
    @BindView(R.id.orders_sort_menu_title_view)
    RelativeLayout sortMenuTitleView;
    @BindView(R.id.orders_sort_menu_categories_title_view)
    RelativeLayout sortMenuCategoriesTitleView;
    @BindView(R.id.orders_sort_menu_family_title_view)
    RelativeLayout sortMenuFamilyTitleView;
    @BindView(R.id.orders_sort_menu_sub_family_title_view)
    RelativeLayout sortMenuSubFamilyTitleView;

    // Butter Knife BindView LinearLayout
    @BindView(R.id.orders_sort_menu_categories_view)
    LinearLayout sortMenuCategoriesView;
    @BindView(R.id.orders_sort_menu_family_view)
    LinearLayout sortMenuFamilyView;
    @BindView(R.id.orders_sort_menu_sub_family_view)
    LinearLayout sortMenuSubFamilyView;

    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.orders_sort_menu_title_icon)
    AppCompatImageView sortMenuTitleIcon;
    @BindView(R.id.orders_sort_menu_categories_title_icon)
    AppCompatImageView sortMenuCategoriesTitleIcon;
    @BindView(R.id.orders_sort_menu_family_title_icon)
    AppCompatImageView sortMenuFamilyTitleIcon;
    @BindView(R.id.orders_sort_menu_sub_family_title_icon)
    AppCompatImageView sortMenuSubFamilyTitleIcon;

    // Butter Knife BindView NestedScrollView
    @BindView(R.id.orders_sort_menu_filters_view)
    NestedScrollView sortMenuFiltersView;

    // Butter Knife BindView PullRefreshLayout
    @BindView(R.id.orders_list_pull_to_refresh)
    PullRefreshLayout pullLayout;

    // Butter Knife BindView ListView
    @BindView(R.id.orders_list)
    ListView listView;

    private ArrayList<Produit> dataModels;
    private Produit produit;
    private ProductAdapter adapter;
    // Session Manager Class
    private Session session;
    //
    private Double price = 0.0;

    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

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

        price = 0.0;
        for (Produit obj : GLOBAL_CART) {
            price += Double.valueOf(obj.getPrice());
        }
        cartTotalTv.setText(formatter.format(price));
        cartShowProductsCountTv.setText(GLOBAL_CART.size() + "");

        loadeData();

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeData();
            }
        });

        // listen refresh event
        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeData();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                produit = dataModels.get(position);
                GLOBAL_CART.add(produit);
                price = 0.0;
                for (Produit obj : GLOBAL_CART) {
                    price += Double.valueOf(obj.getPrice());
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

        //Sort Menu Categories Title View
        sortMenuCategoriesTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortMenuCategoriesView.getVisibility() == View.GONE) {
                    sortMenuCategoriesView.setVisibility(View.VISIBLE);
                    sortMenuCategoriesTitleIcon.setImageResource(R.drawable.ic_expand_less);
                    sortMenuFamilyView.setVisibility(View.GONE);
                    sortMenuFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                    sortMenuSubFamilyView.setVisibility(View.GONE);
                    sortMenuSubFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                } else {
                    sortMenuCategoriesView.setVisibility(View.GONE);
                    sortMenuCategoriesTitleIcon.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });

        //Sort Menu Family Title View
        sortMenuFamilyTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortMenuFamilyView.getVisibility() == View.GONE) {
                    sortMenuFamilyView.setVisibility(View.VISIBLE);
                    sortMenuFamilyTitleIcon.setImageResource(R.drawable.ic_expand_less);
                    sortMenuCategoriesView.setVisibility(View.GONE);
                    sortMenuCategoriesTitleIcon.setImageResource(R.drawable.ic_expand_more);
                    sortMenuSubFamilyView.setVisibility(View.GONE);
                    sortMenuSubFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                } else {
                    sortMenuFamilyView.setVisibility(View.GONE);
                    sortMenuFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });

        //Sort Menu Categories Title View
        sortMenuSubFamilyTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortMenuSubFamilyView.getVisibility() == View.GONE) {
                    sortMenuSubFamilyView.setVisibility(View.VISIBLE);
                    sortMenuSubFamilyTitleIcon.setImageResource(R.drawable.ic_expand_less);
                    sortMenuFamilyView.setVisibility(View.GONE);
                    sortMenuFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                    sortMenuCategoriesView.setVisibility(View.GONE);
                    sortMenuCategoriesTitleIcon.setImageResource(R.drawable.ic_expand_more);
                } else {
                    sortMenuSubFamilyView.setVisibility(View.GONE);
                    sortMenuSubFamilyTitleIcon.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });

        //Sort Menu Categories Title View
        ordersShowCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                startActivity(i);
            }
        });

    }

    private void loadeData() {

        dataModels.add(new Produit("Produit 1", "10.000", "Category 1", "Family 1", "Sub Family 1"));
        dataModels.add(new Produit("Produit 2", "5.000", "Category 1", "Family 1", "Sub Family 2"));
        dataModels.add(new Produit("Produit 3", "8.000", "Category 1", "Family 2", "Sub Family 3"));
        dataModels.add(new Produit("Produit 4", "100.000", "Category 1", "Family 2", "Sub Family 4"));
        dataModels.add(new Produit("Produit 5", "0.500", "Category 2", "Family 3", "Sub Family 5"));
        dataModels.add(new Produit("Produit 6", "2.200", "Category 2", "Family 3", "Sub Family 6"));
        dataModels.add(new Produit("Produit 7", "1.000", "Category 2", "Family 4", "Sub Family 7"));
        dataModels.add(new Produit("Produit 8", "0.100", "Category 2", "Family 4", "Sub Family 8"));

        adapter = new ProductAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);

        emptyListIcon.setImageResource(R.drawable.ic_shopping_cart_white_24);
        emptyListText.setText(R.string.no_products_to_show);
        listView.setEmptyView(emptyListView);

        pullLayout.setRefreshing(false);

    }



}
