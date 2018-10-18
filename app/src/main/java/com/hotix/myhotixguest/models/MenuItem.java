package com.hotix.myhotixguest.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItem {

    @SerializedName("Background")
    @Expose
    private String background;
    @SerializedName("Infos")
    @Expose
    private String infos;
    @SerializedName("Slides")
    @Expose
    private List<Slide> slides = null;
    @SerializedName("Title")
    @Expose
    private String title;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
