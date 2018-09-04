package com.hotix.myhotixguest.fragments;

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
import com.hotix.myhotixguest.models.Complaint;

import java.util.ArrayList;

public class ComplaintsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    AppCompatEditText complaintTitle;
    AppCompatEditText complaintText;

    TextInputLayout complaintTitleInput;
    TextInputLayout complaintTextInput;

    private FloatingActionButton _floatingActionButton;

    private Toolbar toolbar;

    ArrayList<Complaint> dataModels;
    ListView listView;
    private static ComplaintsAdapter adapter;

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


        _floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingActionButton_add);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.complaints_text);

        listView = (ListView) getActivity().findViewById(R.id.complaint_list);

        dataModels = new ArrayList<>();

        dataModels.add(new Complaint("test", "test", "This is a  complaint", "Le Lorem Ipsum est simplement du faux texte employé dans", "waiting", "01/01/2018"));
        dataModels.add(new Complaint("test", "test", "This is a  complaint", "Le Lorem Ipsum est simplement du faux texte employé dans", "waiting", "01/01/2018"));
        dataModels.add(new Complaint("test", "test", "This is a  complaint", "Le Lorem Ipsum est simplement du faux texte employé dans", "treated", "01/01/2018"));
        dataModels.add(new Complaint("test", "test", "This is a  complaint", "Le Lorem Ipsum est simplement du faux texte employé dans", "treated", "01/01/2018"));
        dataModels.add(new Complaint("test", "test", "This is a  complaint", "Le Lorem Ipsum est simplement du faux texte employé dans", "waiting", "01/01/2018"));

        adapter = new ComplaintsAdapter(dataModels, getActivity());

        listView.setAdapter(adapter);


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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

                dataModels.add(new Complaint("test", "test", complaintTitle.getText().toString(), complaintText.getText().toString(), "waiting", "01/01/2018"));
                adapter = new ComplaintsAdapter(dataModels, getActivity());
                listView.setAdapter(adapter);

                dialog.dismiss();

            }
        });

    }

    /**********************************(  Rrefresh ListView )*************************************/
    //This method is to refresh the listview.
    private void refreshList() {
        adapter = new ComplaintsAdapter(dataModels, getActivity());
        listView.setAdapter(adapter);
    }

    /**********************************(  Input Validation  )*************************************/
    //This method is to validate the EditText valus.
    public boolean inputTextValidation() {

        if (!inputValidation.isInputEditTextFilled(complaintTitle, complaintTitleInput, getString(R.string.error_message_title_is_empty))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(complaintText, complaintTextInput, getString(R.string.error_message_complaint_text_is_empty))) {
            return false;
        }

        //Return true if all the inputs are valid
        return true;

    }

}
