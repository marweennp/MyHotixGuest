package com.hotix.myhotixguest.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activitys.BillDetailsActivity;
import com.hotix.myhotixguest.activitys.HistoryActivity;
import com.hotix.myhotixguest.activitys.NewReservationActivity;
import com.hotix.myhotixguest.activitys.ReservationDetailsActivity;
import com.hotix.myhotixguest.activitys.SignupActivity;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RelativeLayout _guestDetails;
    private RelativeLayout _reservationDetails;
    private RelativeLayout _bill;
    private RelativeLayout _history;
    private RelativeLayout _newReservation;

    private AppCompatImageView _reservationDetailsBG;
    private AppCompatImageView _billBG;
    private AppCompatImageView _historyBG;
    private AppCompatImageView _newReservationBG;

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


        _reservationDetailsBG = (AppCompatImageView) getActivity().findViewById(R.id.home_reservation_details_imageView);
        _billBG = (AppCompatImageView) getActivity().findViewById(R.id.home_bill_imageView);
        _historyBG = (AppCompatImageView) getActivity().findViewById(R.id.home_history_imageView);
        _newReservationBG = (AppCompatImageView) getActivity().findViewById(R.id.home_new_reservation_imageView);

        //Picasso.get().load(R.drawable.room).into(_reservationDetailsBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_1.jpg").fit().placeholder(R.drawable.room).into(_reservationDetailsBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_2.jpg").fit().placeholder(R.drawable.bill).into(_billBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_3.jpg").fit().placeholder(R.drawable.history).into(_historyBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_4.jpg").fit().placeholder(R.drawable.reservation).into(_newReservationBG);



        //Reservation Details OnClickListener
        _reservationDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start the ReservationDetailsActivity
                Intent i = new Intent(getActivity(), ReservationDetailsActivity.class);
                startActivity(i);

            }
        });

        //Bill OnClickListener
        _bill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start the BillDetailsActivity
                Intent i = new Intent(getActivity(), BillDetailsActivity.class);
                startActivity(i);

            }
        });

        //History OnClickListener
        _history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start the HistoryActivity
                Intent i = new Intent(getActivity(), HistoryActivity.class);
                startActivity(i);

            }
        });

        //New Reservation OnClickListener
        _newReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start the NewReservationActivity
                Intent i = new Intent(getActivity(), NewReservationActivity.class);
                startActivity(i);

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
