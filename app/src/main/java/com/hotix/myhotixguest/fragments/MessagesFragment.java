package com.hotix.myhotixguest.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
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
import com.hotix.myhotixguest.adapters.MyCategorieMessageAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.models.Message;
import com.hotix.myhotixguest.models.MessageCategorie;
import com.hotix.myhotixguest.models.MessageCategorieResponse;
import com.hotix.myhotixguest.models.MessagesResponse;
import com.hotix.myhotixguest.models.SuccessResponse;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class MessagesFragment extends Fragment {

    private AppCompatEditText messageTitle;
    private AppCompatEditText messageText;
    private TextInputLayout messageTitleInput;
    private TextInputLayout messageTextInput;
    private AppCompatSpinner messageCategorie;
    private AppCompatButton addBtn;
    private AppCompatButton cancelBtn;
    private AppCompatButton listSortAll;
    private AppCompatButton listSortSent;
    private AppCompatButton listSortReceived;
    private MessageAdapter adapter;

    private MyCategorieMessageAdapter mCategorieMessageAdapter;
    private AppCompatTextView messageDetailsTitle;
    private AppCompatTextView messageDetailsDate;
    private AppCompatTextView messageDetailsTime;
    private AppCompatTextView messageDetailsDesc;
    private AppCompatTextView messageDetailsSubject;
    private AppCompatTextView messageDetailsPhone;
    private RelativeLayout messageDetailsPhoneView;
    private AppCompatButton messageDetailsOkBt;
    private ArrayList<MessageCategorie> msgCats;
    private ArrayList<Message> dataModels;
    private ArrayList<Message> myMessages;
    private ListView listView;
    private Message message;
    private FloatingActionButton _floatingActionButton;
    private PullRefreshLayout pullLayout;
    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatButton emptyListRefresh;
    private LinearLayout listSortMenu;

    private Drawable mIconOne, mIconTwo;
    Integer CategorieId = 0;

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

        _floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.messages_floatingActionButton_add);

        progressView = (LinearLayout) getActivity().findViewById(R.id.messages_progress_view);
        emptyListView = (RelativeLayout) getActivity().findViewById(R.id.empty_list_main_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.list_msg_tv);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.empty_list_icon_iv);
        emptyListRefresh = (AppCompatButton) getActivity().findViewById(R.id.empty_list_refresh_bt);

        listSortMenu = (LinearLayout) getActivity().findViewById(R.id.messages_list_sort_menu);

        listSortAll = (AppCompatButton) getActivity().findViewById(R.id.messages_list_sort_all_btn);
        listSortSent = (AppCompatButton) getActivity().findViewById(R.id.messages_list_sort_sent_btn);
        listSortReceived = (AppCompatButton) getActivity().findViewById(R.id.messages_list_sort_received_btn);

        listView = (ListView) getActivity().findViewById(R.id.messages_list);

        dataModels = new ArrayList<>();
        myMessages = new ArrayList<>();
        msgCats = new ArrayList<>();

        _floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadeCategoriesMessage();
            }
        });

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeMessages(00);
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
                loadeMessages(0);
            }
        });

        listSortAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeMessages(0);
            }
        });

        listSortSent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeMessages(1);
            }
        });

        listSortReceived.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeMessages(2);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadeMessages(0);
    }

    /**********************************(  Start Complaint Dialog  )*************************************/
    //This method is to start a dialog window .
    private void startMessageDialog(ArrayList<MessageCategorie> _Cats) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_message, null);
        messageTitle = (AppCompatEditText) mView.findViewById(R.id.add_message_dialog_title);
        messageText = (AppCompatEditText) mView.findViewById(R.id.add_message_dialog_text);
        messageTitleInput = (TextInputLayout) mView.findViewById(R.id.add_message_dialog_input_layout_title);
        messageTextInput = (TextInputLayout) mView.findViewById(R.id.add_message_dialog_input_layout_text);
        messageCategorie = (AppCompatSpinner) mView.findViewById(R.id.add_message_dialog_sp_categorie);
        addBtn = (AppCompatButton) mView.findViewById(R.id.btn_Add);
        cancelBtn = (AppCompatButton) mView.findViewById(R.id.btn_cancel);

        final ArrayList<MessageCategorie> mCats = _Cats;

        mCategorieMessageAdapter = new MyCategorieMessageAdapter (getContext(), _Cats);
        messageCategorie.setAdapter(mCategorieMessageAdapter);
        messageCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                CategorieId = mCats.get(position).getCategorieID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageTitleInput.setErrorEnabled(false);
                messageTextInput.setErrorEnabled(false);
                if (messageTitle.getText().toString().trim().isEmpty()) {
                    messageTitleInput.setError(getString(R.string.error_message_title_is_empty));
                } else if (messageText.getText().toString().trim().isEmpty()) {
                    messageTextInput.setError(getString(R.string.error_message_complaint_text_is_empty));
                } else {
                    addMessage();
                    dialog.dismiss();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

    /**
     * *********************************************************************************************
     */

    private void loadeCategoriesMessage() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<MessageCategorieResponse> userCall = service.getCategoriesMessageQuery();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<MessageCategorieResponse>() {
            @Override
            public void onResponse(Call<MessageCategorieResponse> call, Response<MessageCategorieResponse> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    MessageCategorieResponse _Response = response.body();
                    if (_Response.getSuccess()) {

                        msgCats = _Response.getMessageCategories();
                        startMessageDialog(msgCats);
                    }
                    else {
                        showSnackbar(getActivity().findViewById(android.R.id.content), _Response.getMessage());
                    }
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.toString());
                }

            }

            @Override
            public void onFailure(Call<MessageCategorieResponse> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    private void addMessage() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<SuccessResponse> userCall = service.sendMessageQuery("1", session.getResaId().toString(), session.getResaGroupeId().toString(), session.getResaPaxId().toString(),
                                                                   "1", CategorieId.toString(), (session.getNom() + " " + session.getPrenom()), messageText.getText().toString(),
                                                                    "Android", messageTitle.getText().toString(), "C");

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Response...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    SuccessResponse _Response = response.body();
                    if (_Response.getSuccess()) {

                        loadeMessages(0);
                    }
                    else {
                        showSnackbar(getActivity().findViewById(android.R.id.content), _Response.getMessage());
                    }
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.toString());
                }

            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    // int sort <0:All, 1:Sent, 2:Received>
    private void loadeMessages(int sort) {

        final int x = sort;
        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<MessagesResponse> userCall = service.getMessagesQuery(session.getHotelId().toString(), session.getResaId().toString(), session.getResaGroupeId().toString(), session.getResaPaxId().toString());

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        listSortMenu.setVisibility(View.GONE);

        userCall.enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                if (response.raw().code() == 200) {

                    MessagesResponse _Response = response.body();
                    if (_Response.getSuccess()) {

                        dataModels = _Response.getMessages();

                        if (dataModels.size() > 0) {
                            listSortMenu.setVisibility(View.VISIBLE);
                            changeColerBtns(x);
                        }

                        myMessages.clear();
                        if (x == 1) {
                            for (Message obj : dataModels) {
                                if (obj.getOrigine().toUpperCase().equals("C")) {
                                    myMessages.add(obj);
                                }
                            }
                        } else if (x == 2) {
                            for (Message obj : dataModels) {
                                if (obj.getOrigine().toUpperCase().equals("H")) {
                                    myMessages.add(obj);
                                }
                            }
                        } else {
                            myMessages = dataModels;
                        }

                        adapter = new MessageAdapter(myMessages, getActivity());
                        listView.setAdapter(adapter);
                        emptyListIcon.setImageDrawable(mIconOne);
                        emptyListText.setText(R.string.no_message_to_show);
                        listView.setEmptyView(emptyListView);

                    }
                    else {
                        showSnackbar(getActivity().findViewById(android.R.id.content), _Response.getMessage());
                    }

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<MessagesResponse> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                pullLayout.setRefreshing(false);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageDrawable(mIconTwo);
                listView.setEmptyView(emptyListView);
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });
    }

    private void changeColerBtns(int sort) {

        if (sort == 1) {
            listSortAll.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
            listSortSent.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            listSortReceived.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
        } else if (sort == 2) {
            listSortAll.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
            listSortSent.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
            listSortReceived.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        } else {
            listSortAll.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            listSortSent.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
            listSortReceived.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_70_alpha));
        }

    }


}
