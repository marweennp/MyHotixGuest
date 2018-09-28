package com.hotix.myhotixguest.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.widget.DatePicker;

import com.hotix.myhotixguest.R;

public class ProfileDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, 2000, 0, 1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Do something with the date chosen by the user
        String y = view.getYear() + "";
        String m = (view.getMonth() + 1) + "";
        String d = view.getDayOfMonth() + "";
        String date;

        if (view.getMonth() < 9) {
            m = "0" + m;
        }
        if (view.getDayOfMonth() < 10) {
            d = "0" + d;
        }

        //date = y +  m  + d;
        date = d+"/"+ m+"/"+y;

        AppCompatEditText text = (AppCompatEditText) getActivity().findViewById(R.id.edit_profile_details_birth_date_et);
        text.setText(date);

    }


}
