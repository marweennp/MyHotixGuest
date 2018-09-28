package com.hotix.myhotixguest.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.helpers.Session;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hotix.myhotixguest.helpers.Settings.BASE_URL;
import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_EVENT;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater1;
import static com.hotix.myhotixguest.helpers.Utils.dateTowColors;
import static com.hotix.myhotixguest.helpers.Utils.timeFormater1;

public class EventDetailsActivity extends AppCompatActivity {

    // Butter Knife BindView AppCompatImageView
    @BindView(R.id.event_details_img)
    AppCompatImageView eventImg;
    // Butter Knife BindView AppCompatButton
    @BindView(R.id.participate_button)
    AppCompatButton participateButton;
    // Butter Knife BindView AppCompatTextView
    @BindView(R.id.event_details_date)
    AppCompatTextView eventDetailsDate;
    @BindView(R.id.event_details_time)
    AppCompatTextView eventDetailsTime;
    @BindView(R.id.event_details_location_text)
    AppCompatTextView eventDetailsLocationText;
    @BindView(R.id.event_details_price_text)
    AppCompatTextView eventDetailsPriceText;
    @BindView(R.id.event_details_type)
    AppCompatTextView eventDetailsType;
    @BindView(R.id.event_details_title)
    AppCompatTextView eventDetailsTitle;
    @BindView(R.id.event_details_desc)
    AppCompatTextView eventDetailsDesc;

    // Session Manager Class
    Session session;
    //___________(Currency Number format)_____________\\
    private NumberFormat formatter;
    private DecimalFormatSymbols decimalFormatSymbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        // Session Manager
        session = new Session(getApplicationContext());

        formatter = NumberFormat.getCurrencyInstance();
        decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("TND ");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        formatter.setMinimumFractionDigits(3);

        if (!session.getISResident()) {
            participateButton.setVisibility(View.GONE);
        }

        Picasso.get().load(BASE_URL+"/Android/pics_guest/Events/"+GLOBAL_EVENT.getImage()).fit().placeholder(R.drawable.activites).into(eventImg);

        eventDetailsDate.setText(Html.fromHtml(dateTowColors(dateFormater1(GLOBAL_EVENT.getDateDebut()), getApplicationContext())));
        eventDetailsTime.setText(timeFormater1(GLOBAL_EVENT.getHeure()));
        eventDetailsLocationText.setText(GLOBAL_EVENT.getLocation());

        if (!(GLOBAL_EVENT.getPrix() == 0)) {
            eventDetailsPriceText.setTextColor(Color.parseColor("#FFFFFF"));
            eventDetailsPriceText.setText(formatter.format(GLOBAL_EVENT.getPrix()));
        } else {
            eventDetailsPriceText.setTextColor(Color.parseColor("#388E3C"));
            eventDetailsPriceText.setText(R.string.free);
        }

        eventDetailsType.setText(GLOBAL_EVENT.getCategorie());
        eventDetailsTitle.setText(GLOBAL_EVENT.getNom());
        eventDetailsDesc.setText(GLOBAL_EVENT.getDescription());

    }


    @OnClick(R.id.participate_button)
    public void participationEmail() {
        Intent intent = new  Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "marweennandroid@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Event Participation");
        intent.putExtra(Intent.EXTRA_TEXT, "I confirm my participation in the event "+GLOBAL_EVENT.getNom()+". \n Date : "+ dateFormater1(GLOBAL_EVENT.getDateDebut())+". \n Location : "+GLOBAL_EVENT.getLocation()+".");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }


}
