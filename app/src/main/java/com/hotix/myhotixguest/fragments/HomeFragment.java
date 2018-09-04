package com.hotix.myhotixguest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hotix.myhotixguest.R;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RelativeLayout _guestDetails;
    private RelativeLayout _reservationDetails;
    private RelativeLayout _bill;
    private RelativeLayout _history;
    private RelativeLayout _newReservation;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _guestDetails = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_guest_details_layout);
        _reservationDetails = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_reservation_details_layout);
        _bill = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_bill_layout);
        _history = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_history_layout);
        _newReservation = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_new_reservation_layout);

        //Reservation Details OnClickListener
        _reservationDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Reservation Details", Toast.LENGTH_LONG).show();

            }
        });

        //Bill OnClickListener
        _bill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Bill", Toast.LENGTH_LONG).show();

            }
        });

        //History OnClickListener
        _history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "History", Toast.LENGTH_LONG).show();

            }
        });

        //New Reservation OnClickListener
        _newReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "New Reservation", Toast.LENGTH_LONG).show();

            }
        });

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
}
