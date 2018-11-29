package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
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
import com.hotix.myhotixguest.activites.NewOrderActivity;
import com.hotix.myhotixguest.adapters.OrderAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.CartItem;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class OrdersFragment extends Fragment {


    private Toolbar toolbar;
    private PullRefreshLayout pullLayout;
    private ListView listView;
    private ArrayList<Order> dataModels;
    private Order order;
    private OrderAdapter adapter;
    private FloatingActionButton floatingActionButton;
    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatButton emptyListRefresh;

    private Drawable mIconOne, mIconTwo;

    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

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

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_cart_grey_512, getActivity().getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, getActivity().getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_cart_grey_512, getActivity().getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, getActivity().getTheme());
        }

        formatter = NumberFormat.getCurrencyInstance(Locale.US);
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

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
                order = dataModels.get(position);
                orderDetailsDialog(order);
            }
        });

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
        try {
            loadeOrders();
        } catch (Exception e) {
            showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.error_message_check_settings));
        }
    }


    /************************************(  Loade Orders  )****************************************/
    public void loadeOrders() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ArrayList<Order>> userCall = service.getCommandesQuery(session.getResaId().toString());

        pullLayout.setRefreshing(false);
        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {

                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    dataModels = response.body();

                    adapter = new OrderAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);

                    emptyListIcon.setImageDrawable(mIconOne);
                    emptyListText.setText(R.string.no_orders_to_show);
                    listView.setEmptyView(emptyListView);

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    /**********************************************************************************************/

    private void orderDetailsDialog(Order order) {

        ArrayList<CartItem> carts = new ArrayList<>();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_order_details, null);

        LinearLayoutCompat container = (LinearLayoutCompat) mView.findViewById(R.id.order_details_container);

        AppCompatTextView dateTv = (AppCompatTextView) mView.findViewById(R.id.order_details_date);
        AppCompatTextView timeTv = (AppCompatTextView) mView.findViewById(R.id.order_details_time);
        AppCompatTextView totalTv = (AppCompatTextView) mView.findViewById(R.id.order_details_total);

        AppCompatButton okBt = (AppCompatButton) mView.findViewById(R.id.order_details_btn_ok);

       Double price = 0.0;
        carts = order.getDetails();
        for (CartItem obj : carts) {
            price += obj.getPrixUnitaire() * obj.getQuantite();

            View orderRow = getLayoutInflater().inflate(R.layout.item_order_details_row, null);
            AppCompatTextView quantite = (AppCompatTextView) orderRow.findViewById(R.id.order_details_quantite);
            AppCompatTextView u_name = (AppCompatTextView) orderRow.findViewById(R.id.order_details_u_name);
            AppCompatTextView u_price = (AppCompatTextView) orderRow.findViewById(R.id.order_details_u_price);

            quantite.setText(obj.getQuantite()+" X ");
            u_name.setText(obj.getProduitName().trim());
            u_price.setText(formatter.format(obj.getPrixUnitaire()));
            container.addView(orderRow);
        }

        totalTv.setText(getString(R.string.total) + formatter.format(price) +" DT");
        dateTv.setText(dateFormater(order.getDate(), "yyyyMMdd hh:mm", "dd MMM yyyy"));
        timeTv.setText(dateFormater(order.getDate(), "yyyyMMdd hh:mm", "hh:mm"));

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}
