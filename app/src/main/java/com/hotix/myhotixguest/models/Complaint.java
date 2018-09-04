package com.hotix.myhotixguest.models;

public class Complaint {

    private String complaint_id;
    private String complaint_owner;
    private String complaint_title;
    private String complaint_text;
    private String complaint_state;
    private String complaint_date;

    public Complaint() {
    }

    public Complaint(String complaint_id, String complaint_owner, String complaint_title, String complaint_text, String complaint_state, String complaint_date) {
        this.complaint_id = complaint_id;
        this.complaint_owner = complaint_owner;
        this.complaint_title = complaint_title;
        this.complaint_text = complaint_text;
        this.complaint_state = complaint_state;
        this.complaint_date = complaint_date;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComplaint_owner() {
        return complaint_owner;
    }

    public void setComplaint_owner(String complaint_owner) {
        this.complaint_owner = complaint_owner;
    }

    public String getComplaint_title() {
        return complaint_title;
    }

    public void setComplaint_title(String complaint_title) {
        this.complaint_title = complaint_title;
    }

    public String getComplaint_text() {
        return complaint_text;
    }

    public void setComplaint_text(String complaint_text) {
        this.complaint_text = complaint_text;
    }

    public String getComplaint_state() {
        return complaint_state;
    }

    public void setComplaint_state(String complaint_state) {
        this.complaint_state = complaint_state;
    }

    public String getComplaint_date() {
        return complaint_date;
    }

    public void setComplaint_date(String complaint_date) {
        this.complaint_date = complaint_date;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaint_id='" + complaint_id + '\'' +
                ", complaint_owner='" + complaint_owner + '\'' +
                ", complaint_title='" + complaint_title + '\'' +
                ", complaint_text='" + complaint_text + '\'' +
                ", complaint_state='" + complaint_state + '\'' +
                ", complaint_date='" + complaint_date + '\'' +
                '}';
    }


}
