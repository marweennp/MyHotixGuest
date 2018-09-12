package com.hotix.myhotixguest.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activitys.BillDetailsActivity;
import com.hotix.myhotixguest.activitys.HistoryActivity;
import com.hotix.myhotixguest.activitys.NewReservationActivity;
import com.hotix.myhotixguest.activitys.ReservationDetailsActivity;
import com.hotix.myhotixguest.helpers.Session;
import com.squareup.picasso.Picasso;

import static com.hotix.myhotixguest.helpers.Utils.newCalculateDaysBetween;
import static com.hotix.myhotixguest.helpers.Utils.newDateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class HomeFragment extends Fragment {

    // Session Manager Class
    Session session;

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
    private AppCompatImageView homeGuestNightsIcon;
    private AppCompatTextView homeGuestName;
    private AppCompatTextView homeGuestResType;
    private AppCompatTextView homeGuestDate;
    private AppCompatTextView homeGuestNights;
    private AppCompatTextView homeResaDetailsTitle;


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
        // Session Manager
        session = new Session(getActivity());

        _guestDetails = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_guest_details_layout);
        _reservationDetails = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_reservation_details_layout);
        _bill = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_bill_layout);
        _history = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_history_layout);
        _newReservation = (RelativeLayout) getActivity().findViewById(R.id.home_fragment_new_reservation_layout);

        _reservationDetailsBG = (AppCompatImageView) getActivity().findViewById(R.id.home_reservation_details_imageView);
        _billBG = (AppCompatImageView) getActivity().findViewById(R.id.home_bill_imageView);
        _historyBG = (AppCompatImageView) getActivity().findViewById(R.id.home_history_imageView);
        _newReservationBG = (AppCompatImageView) getActivity().findViewById(R.id.home_new_reservation_imageView);
        homeGuestNightsIcon = (AppCompatImageView) getActivity().findViewById(R.id.home_guest_nights_icon);

        homeGuestName = (AppCompatTextView) getActivity().findViewById(R.id.home_guest_name);
        homeGuestResType = (AppCompatTextView) getActivity().findViewById(R.id.home_guest_res_type);
        homeGuestDate = (AppCompatTextView) getActivity().findViewById(R.id.home_guest_date);
        homeGuestNights = (AppCompatTextView) getActivity().findViewById(R.id.home_guest_nights);
        homeResaDetailsTitle = (AppCompatTextView) getActivity().findViewById(R.id.home_reservation_details_title);

        homeGuestName.setText(session.getNom() + " " + session.getPrenom());
        if (session.getISResident()) {
            homeGuestResType.setText(session.getChambre());
            homeGuestDate.setText(newDateFormater(session.getDateArrivee()) + " -> " + newDateFormater(session.getDateDepart()));
            homeGuestNights.setText(newCalculateDaysBetween(session.getDateArrivee(), session.getDateDepart()));
        } else if (session.getResaId() != 0) {
            homeGuestResType.setText(session.getEmail());
            homeGuestDate.setText(newDateFormater(session.getDateArrivee()) + " -> " + newDateFormater(session.getDateDepart()));
            homeGuestNights.setText(newCalculateDaysBetween(session.getDateArrivee(), session.getDateDepart()));
            homeResaDetailsTitle.setText("My Reservation");
        } else {
            homeGuestResType.setText(session.getEmail());
            homeGuestDate.setText(R.string.no_reservations);
            homeGuestNights.setVisibility(View.GONE);
            homeGuestNightsIcon.setVisibility(View.GONE);
        }


        //Picasso.get().load(R.drawable.room).into(_reservationDetailsBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_1.jpg").fit().into(_reservationDetailsBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_2.jpg").fit().into(_billBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_3.jpg").fit().into(_historyBG);
        Picasso.get().load("http://196.203.219.164/android/pics_guest/pic_4.jpg").fit().into(_newReservationBG);


        //Reservation Details OnClickListener
        _reservationDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ReservationDetailsActivity.class);
                i.putExtra("resaId", session.getResaId().toString());
                i.putExtra("histo","");

                if (session.getISResident()) {
                    startActivity(i);
                } else if (session.getResaId() != 0) {
                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), "You must be a resident to use this feature");
                }
            }
        });

        //Bill OnClickListener
        _bill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (session.getISResident()) {
                    //Start the BillDetailsActivity
                    Intent i = new Intent(getActivity(), BillDetailsActivity.class);
                    i.putExtra("billId", session.getFactureId().toString());
                    i.putExtra("billAn", session.getFactureAnnee().toString());
                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), "You must be a resident to use this feature");
                }

            }
        });

        //History OnClickListener
        _history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (session.getHasHistory()) {
                    //Start the HistoryActivity
                    Intent i = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), "You have no history to check");
                }

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
