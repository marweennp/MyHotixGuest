package com.hotix.myhotixguest.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.MessageAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Message;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class MessagesFragment extends Fragment {


    private MessageAdapter adapter;
    private AppCompatTextView messageDetailsTitle;
    private AppCompatTextView messageDetailsDate;
    private AppCompatTextView messageDetailsTime;
    private AppCompatTextView messageDetailsDesc;
    private AppCompatTextView messageDetailsSubject;
    private AppCompatTextView messageDetailsPhone;
    private RelativeLayout messageDetailsPhoneView;
    private AppCompatButton messageDetailsOkBt;
    private ArrayList<Message> dataModels;
    private ListView listView;
    private Message message;
    private PullRefreshLayout pullLayout;
    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatButton emptyListRefresh;

    private Drawable mIconOne, mIconTwo;

    public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        //Check android vertion and load image
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mIconOne = getResources().getDrawable(R.drawable.svg_notifications_grey_512, getActivity().getTheme());
            mIconTwo = getResources().getDrawable(R.drawable.svg_server_grey_512, getActivity().getTheme());
        } else {
            mIconOne = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_notifications_grey_512, getActivity().getTheme());
            mIconTwo = VectorDrawableCompat.create(this.getResources(), R.drawable.svg_server_grey_512, getActivity().getTheme());
        }

        pullLayout = (PullRefreshLayout) getActivity().findViewById(R.id.messages_list_pull_to_refresh);

        progressView = (LinearLayout) getActivity().findViewById(R.id.messages_progress_view);
        emptyListView = (RelativeLayout) getActivity().findViewById(R.id.empty_list_main_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.list_msg_tv);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.empty_list_icon_iv);
        emptyListRefresh = (AppCompatButton) getActivity().findViewById(R.id.empty_list_refresh_bt);

        listView = (ListView) getActivity().findViewById(R.id.messages_list);

        dataModels = new ArrayList<>();

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeMessages();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                message = dataModels.get(position);
                messageDetailsDialog(message);

            }
        });

        // listen refresh event
        pullLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                loadeMessages();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadeMessages();
    }

    /**
     * *********************************************************************************************
     */
    private void loadeMessages() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ArrayList<Message>> userCall = service.getMessagesQuery(session.getResaId().toString(), session.getResaPaxId().toString());

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                if (response.raw().code() == 200) {

                    dataModels = response.body();

                    adapter = new MessageAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);
                    emptyListIcon.setImageDrawable(mIconOne);
                    emptyListText.setText(R.string.no_message_to_show);
                    listView.setEmptyView(emptyListView);

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                listView.setEmptyView(emptyListView);
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void messageDetailsDialog(Message message) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_message_details, null);
        messageDetailsTitle = (AppCompatTextView) mView.findViewById(R.id.message_details_title);
        messageDetailsDate = (AppCompatTextView) mView.findViewById(R.id.message_details_date);
        messageDetailsTime = (AppCompatTextView) mView.findViewById(R.id.message_details_time);
        messageDetailsSubject = (AppCompatTextView) mView.findViewById(R.id.message_details_subject);
        messageDetailsDesc = (AppCompatTextView) mView.findViewById(R.id.message_details_desc);
        messageDetailsPhone = (AppCompatTextView) mView.findViewById(R.id.message_details_phone);
        messageDetailsPhoneView = (RelativeLayout) mView.findViewById(R.id.message_details_phone_view);
        messageDetailsOkBt = (AppCompatButton) mView.findViewById(R.id.message_details_btn_ok);

        if (!message.getTel().isEmpty()) {
            messageDetailsPhone.setText(message.getTel());
        } else {
            messageDetailsPhoneView.setVisibility(View.GONE);
        }
        messageDetailsTitle.setText(getString(R.string.message_from) +" "+ message.getFrom());
        messageDetailsDate.setText(dateFormater(message.getDate(), "yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy"));
        messageDetailsTime.setText(dateFormater(message.getDate(), "yyyy-MM-dd'T'hh:mm:ss", "hh:mm"));
        messageDetailsSubject.setText(message.getSubject());
        messageDetailsDesc.setText(message.getDetails());

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        messageDetailsOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


}
