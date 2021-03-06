package com.hotix.myhotixguest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.activites.BillDetailsActivity;
import com.hotix.myhotixguest.activites.GuestProfileActivity;
import com.hotix.myhotixguest.activites.HistoryActivity;
import com.hotix.myhotixguest.activites.LoginActivity;
import com.hotix.myhotixguest.activites.NewReservationActivity;
import com.hotix.myhotixguest.activites.ReservationDetailsActivity;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.Utils.dateColored;
import static com.hotix.myhotixguest.helpers.Utils.fromTodayToDate;
import static com.hotix.myhotixguest.helpers.Utils.newCalculateDaysBetween;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class HomeFragment extends Fragment {

    private static final String TAG = "FIREBASE_ID";

    // Session Manager Class
    Session session;

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
    private AppCompatImageView guestEditProfile;
    private AppCompatImageView guestLogout;

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

        if (session.getNewToken()) {
            try {
                updateFireBaseToken();
            } catch (Exception e) {
                Log.e("HOME FRAGMENT LOG", e.toString());
            }
        }

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

        guestEditProfile = (AppCompatImageView) getActivity().findViewById(R.id.home_guest_edit_icon);
        guestLogout = (AppCompatImageView) getActivity().findViewById(R.id.home_guest_logout_icon);

        homeGuestName.setText(session.getNom() + " " + session.getPrenom());
        if (session.getISResident()) {
            homeGuestResType.setText(session.getChambre());
            homeGuestDate.setText(Html.fromHtml(dateColored(session.getDateArrivee(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", false) + " - " + dateColored(session.getDateDepart(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));
            homeGuestNights.setText(newCalculateDaysBetween(session.getDateArrivee(), session.getDateDepart()));
        } else if (session.getResaId() != 0) {
            homeGuestResType.setText(fromTodayToDate(session.getDateArrivee()) + " " + getString(R.string.day_till_check_in));
            homeGuestDate.setText(Html.fromHtml(dateColored(session.getDateArrivee(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", false) + " - " + dateColored(session.getDateDepart(), "#FFFFFF", "#03A9F4", "yyyy-MM-dd'T'hh:mm:ss", true)));
            homeGuestNights.setText(newCalculateDaysBetween(session.getDateArrivee(), session.getDateDepart()));
            homeResaDetailsTitle.setText("My Reservation");
        } else {
            homeGuestResType.setText(session.getEmail());
            homeGuestDate.setText(R.string.no_reservations);
            homeGuestNights.setVisibility(View.GONE);
            homeGuestNightsIcon.setVisibility(View.GONE);
        }

        loadeImages();

        //Reservation Details OnClickListener
        _reservationDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ReservationDetailsActivity.class);
                i.putExtra("resaId", session.getResaId().toString());
                i.putExtra("histo", "");
                Log.e("MARWEN", session.getResaId().toString());
                if (session.getISResident()) {
                    startActivity(i);
                } else if (session.getResaId() != 0) {
                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.not_resident));
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
                    i.putExtra("dateIn", session.getDateArrivee());
                    i.putExtra("dateOut", session.getDateDepart());
                    i.putExtra("histo", false);

                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.not_resident));
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
                    showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.no_history));
                }

            }
        });

        //New Reservation OnClickListener
        _newReservation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (true) {
                    //Start the NewReservationActivity
                    Intent i = new Intent(getActivity(), NewReservationActivity.class);
                    startActivity(i);
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.temporarily_disabled));
                }

            }
        });

        //Edit Profile OnClickListener
        guestEditProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GuestProfileActivity.class);
                startActivity(i);
            }
        });

        //Logout OnClickListener
        guestLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startLogoutDialog();
            }
        });


    }

    private void loadeImages() {

        Picasso
                .get()
                .load(BASE_URL + "/Android/pics_guest/pic_1.jpg")
                .placeholder(R.drawable.banner_placeholder)
                .fit()
                .into(_reservationDetailsBG, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.room).fit().into(_reservationDetailsBG);
                    }
                });

        Picasso
                .get()
                .load(BASE_URL + "/Android/pics_guest/pic_2.jpg")
                .placeholder(R.drawable.banner_placeholder)
                .fit()
                .into(_billBG, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.bill).fit().into(_billBG);
                    }
                });

        Picasso
                .get()
                .load(BASE_URL + "/Android/pics_guest/pic_3.jpg")
                .placeholder(R.drawable.banner_placeholder)
                .fit()
                .into(_historyBG, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.history).fit().into(_historyBG);
                    }
                });

        Picasso
                .get()
                .load(BASE_URL + "/Android/pics_guest/pic_4.jpg")
                .placeholder(R.drawable.banner_placeholder)
                .fit()
                .into(_newReservationBG, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.reservation).fit().into(_newReservationBG);
                    }
                });


    }

    private void startLogoutDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        AppCompatButton logoutBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_yes_btn);
        AppCompatButton cancelBtn = (AppCompatButton) mView.findViewById(R.id.logout_dialog_cancel_btn);

        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                session.clearSessionDetails();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void updateFireBaseToken() {

        RetrofitInterface service = RetrofitClient.getClientHngApi().create(RetrofitInterface.class);
        Call<ResponseBody> userCall = service.updateSerialKeyQuery(session.getClientId().toString(), session.getFCMToken());

        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.raw().code() == 200) {
                    Log.e(TAG, "Refreshed token: " + response.raw().toString());
                    session.setNewToken(true);
                } else {
                    Log.e(TAG, "Refreshed token: " + response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Refreshed token: " + t.toString());
            }
        });
    }

}
