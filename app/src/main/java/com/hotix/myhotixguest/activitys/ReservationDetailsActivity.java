package com.hotix.myhotixguest.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.hotix.myhotixguest.models.DetailsPax;
import com.hotix.myhotixguest.models.Sejour;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Utils.calculateDaysBetween;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;

public class ReservationDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // Butter Knife BindView RelativeLayout
    @BindView(R.id.reservation_details_main_Layout)
    RelativeLayout rLayout;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.profile_user_name)
    AppCompatTextView profileUserName;
    @BindView(R.id.profile_user_res_type)
    AppCompatTextView profileUserResType;
    @BindView(R.id.profile_user_agency)
    AppCompatTextView profileUserAgency;
    @BindView(R.id.profile_arrival_date)
    AppCompatTextView profileArrivalDate;
    @BindView(R.id.profile_departure_date)
    AppCompatTextView profileDepartureDate;
    @BindView(R.id.profile_nights)
    AppCompatTextView profileNights;
    @BindView(R.id.profile_rooms_count)
    AppCompatTextView profileRoomsCount;
    @BindView(R.id.profile_room_type)
    AppCompatTextView profileRoomType;
    @BindView(R.id.profile_rate)
    AppCompatTextView profileRate;
    @BindView(R.id.profile_arrangement)
    AppCompatTextView profileArrangement;
    @BindView(R.id.profile_adults)
    AppCompatTextView profileAdults;
    @BindView(R.id.profile_kids)
    AppCompatTextView profileKids;
    @BindView(R.id.profile_babies)
    AppCompatTextView profileBabies;
    @BindView(R.id.profile_bill_icon)
    AppCompatImageView billIcon;
    // Session Manager Class
    Session session;
    // Butter Knife BindView LinearLayout
    @BindView(R.id.guests_details_container)
    LinearLayout guestsContainer;
    @BindView(R.id.profile_bill_icon_layou)
    LinearLayoutCompat profileBillLayou;


    // Loading View & Empty ListView
    @BindView(R.id.loading_view)
    LinearLayout progressView;
    @BindView(R.id.empty_list_view)
    RelativeLayout emptyListView;
    @BindView(R.id.list_tv_msg)
    AppCompatTextView emptyListText;
    @BindView(R.id.empty_list_iv_icon)
    AppCompatImageView emptyListIcon;
    @BindView(R.id.empty_list_ibt_refresh)
    AppCompatImageButton emptyListRefresh;
    @BindView(R.id.reservation_details_main_container)
    NestedScrollView reservationDetailsContainer;

    private String resaId;
    private String billId;
    private String billAn;
    private String histo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.reservation_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resaId = extras.getString("resaId");
            histo = extras.getString("histo");
        }

        if (!histo.equals("histo")) {
            profileBillLayou.setVisibility(View.GONE);
        }

        billIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the BillDetailsActivity
                Intent i = new Intent(getApplicationContext(), BillDetailsActivity.class);
                i.putExtra("billId", billId);
                i.putExtra("billAn", billAn);
                startActivity(i);
            }
        });

        emptyListRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        loadData();
    }

    private void loadData() {

        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Sejour> userCall = service.getStayQuery(resaId);

        progressView.setVisibility(View.VISIBLE);
        reservationDetailsContainer.setVisibility(View.GONE);
        emptyListView.setVisibility(View.GONE);

        userCall.enqueue(new Callback<Sejour>() {
            @Override
            public void onResponse(Call<Sejour> call, Response<Sejour> response) {

                progressView.setVisibility(View.GONE);
                reservationDetailsContainer.setVisibility(View.VISIBLE);
                emptyListView.setVisibility(View.GONE);

                if (response.raw().code() == 200) {
                    Sejour sejour = response.body();

                    profileUserName.setText(session.getNom() + " " + session.getPrenom());
                    profileUserResType.setText(session.getChambre());
                    profileUserAgency.setText(sejour.getSociete());
                    profileArrivalDate.setText(dateFormater(sejour.getDateArrivee()));
                    profileDepartureDate.setText(dateFormater(sejour.getDateDepart()));
                    profileNights.setText(calculateDaysBetween(sejour.getDateArrivee(), sejour.getDateDepart()));
                    profileRoomsCount.setText(sejour.getChambre());
                    profileRoomType.setText(sejour.getTypeChambre());
                    profileRate.setText(sejour.getTarif());
                    profileArrangement.setText(sejour.getArrangement());
                    profileAdults.setText(sejour.getNbreA().toString());
                    profileKids.setText(sejour.getNbreE().toString());
                    profileBabies.setText(sejour.getNbreB().toString());
                    billId = sejour.getFactureId().toString();
                    billAn = sejour.getFactureAnnee().toString();

                    for (DetailsPax obj : sejour.getDetailsPax()) {
                        addGuestDetales(
                                obj.getIsMatser(),
                                "Mr",
                                obj.getClientNom() + " " + obj.getClientPrenom(),
                                obj.getClientNationalite(),
                                obj.getClientDateNaissance(),
                                obj.getClientAdresse(),
                                obj.getClientPhone(),
                                obj.getClientEmail());
                    }

                } else {
                    showSnackbar(findViewById(android.R.id.content), response.message());
                }

            }

            @Override
            public void onFailure(Call<Sejour> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                emptyListView.setVisibility(View.VISIBLE);
                emptyListText.setText(R.string.server_unreachable);
                emptyListIcon.setImageResource(R.drawable.baseline_signal_wifi_off_24);
                showSnackbar(findViewById(android.R.id.content), "Server is down please try after some time");
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addGuestDetales(boolean master, String cv, String name, String nat, String bd, String adr, String phone, String mail) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View guestView = inflater.inflate(R.layout.list_guest_details_row, null);
        AppCompatTextView guestIsMaster = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_is_master);
        AppCompatTextView guestNameOp = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_name_op);
        AppCompatTextView guestName = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_name);
        AppCompatTextView guestNationality = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_nationality);
        AppCompatImageView guestBirthDateIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_birth_date_icon);
        AppCompatTextView guestBirthDate = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_birth_date);
        AppCompatImageView guestAddressIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_address_icon);
        AppCompatTextView guestAddress = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_address);
        AppCompatImageView guestPhoneIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_phone_icon);
        AppCompatTextView guestPhone = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_phone);
        AppCompatImageView guestMailIcon = (AppCompatImageView) guestView.findViewById(R.id.profile_guest_details_mail_icon);
        AppCompatTextView guestMail = (AppCompatTextView) guestView.findViewById(R.id.profile_guest_details_mail);
        LinearLayout bdLayout = (LinearLayout) guestView.findViewById(R.id.bd_layout);
        LinearLayout adrLayout = (LinearLayout) guestView.findViewById(R.id.adr_layout);
        LinearLayout phoneLayout = (LinearLayout) guestView.findViewById(R.id.phone_layout);
        LinearLayout mailLayout = (LinearLayout) guestView.findViewById(R.id.mail_layout);

        if (!master) {
            guestIsMaster.setVisibility(View.GONE);
        }
        guestNameOp.setText(cv);
        guestName.setText(name);
        guestNationality.setText(nat);
        if (bd.trim().isEmpty()) {
            guestBirthDateIcon.setVisibility(View.GONE);
            guestBirthDate.setVisibility(View.GONE);
            bdLayout.setVisibility(View.GONE);
        } else {
            guestBirthDate.setText(bd);
        }
        if (adr.trim().isEmpty()) {
            guestAddressIcon.setVisibility(View.GONE);
            guestAddress.setVisibility(View.GONE);
            adrLayout.setVisibility(View.GONE);
        } else {
            guestAddress.setText(adr);
        }
        if (phone.trim().isEmpty()) {
            guestPhoneIcon.setVisibility(View.GONE);
            guestPhone.setVisibility(View.GONE);
            phoneLayout.setVisibility(View.GONE);
        } else {
            guestPhone.setText(phone);
        }
        if (mail.trim().isEmpty()) {
            guestMailIcon.setVisibility(View.GONE);
            guestMail.setVisibility(View.GONE);
            mailLayout.setVisibility(View.GONE);
        } else {
            guestMail.setText(mail);
        }

        guestsContainer.addView(guestView);
    }
}
