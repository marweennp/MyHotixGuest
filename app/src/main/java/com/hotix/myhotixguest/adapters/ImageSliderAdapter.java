package com.hotix.myhotixguest.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.models.Slide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hotix.myhotixguest.helpers.Settings.BASE_URL;

public class ImageSliderAdapter extends PagerAdapter {

    private ArrayList<Slide> slides;
    private LayoutInflater inflater;
    private Context context;

    public ImageSliderAdapter(Context context, ArrayList<Slide> slides) {
        this.context = context;
        this.slides = slides;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_image, view, false);
        AppCompatImageView myImage = (AppCompatImageView) myImageLayout.findViewById(R.id.slide_img_view);
        AppCompatTextView myText = (AppCompatTextView) myImageLayout.findViewById(R.id.slide_img_title);
        Picasso.get().load(BASE_URL + slides.get(position).getUrl()).fit().placeholder(R.drawable.hotel).fit().into(myImage);
        myText.setText(slides.get(position).getLegende());
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}