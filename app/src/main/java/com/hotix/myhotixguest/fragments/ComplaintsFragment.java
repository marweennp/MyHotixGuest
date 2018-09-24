package com.hotix.myhotixguest.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activitys.EventDetailsActivity;
import com.hotix.myhotixguest.adapters.ComplaintsAdapter;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.GLOBAL_EVENT;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ComplaintsFragment extends Fragment {

    private static ComplaintsAdapter adapter;
    private AppCompatEditText complaintTitle;
    private AppCompatEditText complaintText;
    private TextInputLayout complaintTitleInput;
    private TextInputLayout complaintTextInput;
    private AppCompatButton addComplaint;
    private AppCompatButton cancelBt;
    private AppCompatTextView complaintDetailsState;
    private AppCompatTextView complaintDetailsTitle;
    private AppCompatTextView complaintDetailsDate;
    private AppCompatTextView complaintDetailsDesc;
    private AppCompatTextView complaintDetailsAnswerMsg;
    private RelativeLayout complaintDetailsAnswerView;
    private AppCompatButton complaintDetailsOkBt;
    private ArrayList<Complaint> dataModels;
    private ListView listView;
    private Complaint complaint;
    private FloatingActionButton _floatingActionButton;
    // Session Manager Class
    private Session session;

    // Loading View & Empty ListView
    private LinearLayout progressView;
    private RelativeLayout emptyListView;
    private AppCompatTextView emptyListText;
    private AppCompatImageView emptyListIcon;
    private AppCompatImageButton emptyListRefresh;


    public ComplaintsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaints, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        _floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.complaints_floatingActionButton_add);

        progressView = (LinearLayout) getActivity().findViewById(R.id.loading_view);
        emptyListView = (RelativeLayout) getActivity().findViewById(R.id.empty_list_view);
        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.list_tv_msg);
        emptyListIcon = (AppCompatImageView) getActivity().findViewById(R.id.empty_list_iv_icon);
        emptyListRefresh = (AppCompatImageButton) getActivity().findViewById(R.id.empty_list_ibt_refresh);

        listView = (ListView) getActivity().findViewById(R.id.complaints_list);

        dataModels = new ArrayList<>();

        _floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startComplaintDialog();
            }
        });

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadeComplaints();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                complaint = dataModels.get(position);
                startComplaintDetailsDialog(complaint);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        loadeComplaints();
    }

    /**********************************(  Start Complaint Dialog  )*************************************/
    //This method is to start a dialog window .
    private void startComplaintDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_add_complaint, null);
        complaintTitle = (AppCompatEditText) mView.findViewById(R.id.add_complaint_dialog_title);
        complaintText = (AppCompatEditText) mView.findViewById(R.id.add_complaint_dialog_text);
        complaintTitleInput = (TextInputLayout) mView.findViewById(R.id.add_complaint_dialog_input_layout_title);
        complaintTextInput = (TextInputLayout) mView.findViewById(R.id.add_complaint_dialog_input_layout_text);
        addComplaint = (AppCompatButton) mView.findViewById(R.id.btn_Add);
        cancelBt = (AppCompatButton) mView.findViewById(R.id.btn_cancel);


        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        addComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaintTitleInput.setErrorEnabled(false);
                complaintTextInput.setErrorEnabled(false);
                if (complaintTitle.getText().toString().trim().isEmpty()) {
                    complaintTitleInput.setError(getString(R.string.error_message_title_is_empty));
                } else if (complaintText.getText().toString().trim().isEmpty()) {
                    complaintTextInput.setError(getString(R.string.error_message_complaint_text_is_empty));
                } else {
                    addComplaint();
                    dialog.dismiss();
                }
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void startComplaintDetailsDialog(Complaint complaint) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_complaint_details, null);
        complaintDetailsState = (AppCompatTextView) mView.findViewById(R.id.complaint_details_state);
        complaintDetailsTitle = (AppCompatTextView) mView.findViewById(R.id.complaint_details_title);
        complaintDetailsDate = (AppCompatTextView) mView.findViewById(R.id.complaint_details_date);
        complaintDetailsDesc = (AppCompatTextView) mView.findViewById(R.id.complaint_details_desc);
        complaintDetailsAnswerMsg = (AppCompatTextView) mView.findViewById(R.id.complaint_details_answer_msg);
        complaintDetailsAnswerView = (RelativeLayout) mView.findViewById(R.id.complaint_details_answer_view);
        complaintDetailsOkBt = (AppCompatButton) mView.findViewById(R.id.complaint_details_btn_ok);

        if (complaint.getTraite()) {
            complaintDetailsState.setText(R.string.compaint_treated);
            complaintDetailsState.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_500));
        } else {
            complaintDetailsState.setText(R.string.compaint_waiting);
        }
        complaintDetailsTitle.setText(complaint.getObject());
        complaintDetailsDate.setText(complaint.getDateCreation());
        complaintDetailsDesc.setText(complaint.getDescription());




        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        complaintDetailsOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    /**********************************(  __________________ )*************************************/
    //This method is to refresh the listview.
    private void refreshList() {
        loadeComplaints();
    }

    private void addComplaint() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseBody> userCall = service.sendReclamationQuery("1", session.getChambre(), complaintTitle.getText().toString(), complaintText.getText().toString(), session.getResaId().toString());

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Response...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    loadeComplaints();
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.toString());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(getActivity().findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });

    }

    private void loadeComplaints() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Complaint>> billCall = service.getReclamationsQuery(session.getResaId().toString());

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);

        billCall.enqueue(new Callback<ArrayList<Complaint>>() {
            @Override
            public void onResponse(Call<ArrayList<Complaint>> call, Response<ArrayList<Complaint>> response) {
                progressView.setVisibility(View.GONE);
                if (response.raw().code() == 200) {

                    dataModels = response.body();
                    adapter = new ComplaintsAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);
                    emptyListText.setText(R.string.no_complaint_to_show);
                    listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));

                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Complaint>> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.baseline_signal_wifi_off_24);
                listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));
                showSnackbar(getActivity().findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }

}
