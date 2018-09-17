package com.hotix.myhotixguest.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.ComplaintsAdapter;
import com.hotix.myhotixguest.helpers.InputValidation;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ComplaintsFragment extends Fragment {

    private static ComplaintsAdapter adapter;
    private AppCompatEditText complaintTitle;
    private AppCompatEditText complaintText;

    private AppCompatTextView emptyListText;

    private TextInputLayout complaintTitleInput;
    private TextInputLayout complaintTextInput;
    private ArrayList<Complaint> dataModels;
    private ListView listView;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton _floatingActionButton;
    private Toolbar toolbar;
    // Session Manager Class
    private Session session;
    private InputValidation inputValidation;

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaints, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Session Manager
        session = new Session(getActivity());

        _floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton_add);

        emptyListText = (AppCompatTextView) getActivity().findViewById(R.id.empty_list_text_view);
        emptyListText.setText(R.string.no_complaint_to_show);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.complaints_text);

        listView = (ListView) getActivity().findViewById(R.id.complaint_list);

        dataModels = new ArrayList<>();

        listView.setEmptyView(getActivity().findViewById(R.id.empty));

        _floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startComplaintDialog();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.complaint_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add:
                startComplaintDialog();
                return true;

            case R.id.action_refresh:
                refreshList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadeComplaints();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        AppCompatButton addComplaint = (AppCompatButton) mView.findViewById(R.id.btn_Add);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        addComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaintTitleInput.setErrorEnabled(false);
                complaintTextInput.setErrorEnabled(false);
                if (complaintTitle.getText().toString().trim().isEmpty()) {
                    complaintTitleInput.setError(getString(R.string.error_message_title_is_empty));
                }else if (complaintText.getText().toString().trim().isEmpty()) {
                    complaintTextInput.setError(getString(R.string.error_message_complaint_text_is_empty));
                }else{
                    addComplaint();
                    dialog.dismiss();
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        billCall.enqueue(new Callback<ArrayList<Complaint>>() {
            @Override
            public void onResponse(Call<ArrayList<Complaint>> call, Response<ArrayList<Complaint>> response) {
                progressDialog.dismiss();
                if (response.raw().code() == 200) {

                    dataModels = response.body();
                    adapter = new ComplaintsAdapter(dataModels, getActivity());
                    listView.setAdapter(adapter);

                }else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Complaint>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(getActivity().findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });
    }

}
