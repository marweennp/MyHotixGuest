package com.hotix.myhotixguest.models;

import java.util.ArrayList;

public class BillData {

    private Double total;
    private String date;
    private ArrayList<LigneFacture> lignesFacture = null;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<LigneFacture> getLignesFacture() {
        return lignesFacture;
    }

    public void setLignesFacture(ArrayList<LigneFacture> lignesFacture) {
        this.lignesFacture = lignesFacture;
    }

    @Override
    public String toString() {
        return "BillData{" +
                "total=" + total +
                ", date='" + date + '\'' +
                '}';
    }
}
