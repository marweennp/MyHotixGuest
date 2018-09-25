package com.hotix.myhotixguest.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.hotix.myhotixguest.R;

import static com.hotix.myhotixguest.helpers.Utils.dateFormater3;

public class SignupDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

        TextInputEditText text = (TextInputEditText) getActivity().findViewById(R.id.signup_2_birth_date_et);
        text.setText(date);

    }
}