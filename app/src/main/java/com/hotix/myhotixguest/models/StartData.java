package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StartData {

    @SerializedName("Civilites")
    @Expose
    private ArrayList<Civilite> civilites = null;
    @SerializedName("Pays")
    @Expose
    private ArrayList<Pay> pays = null;
    @SerializedName("Pieces")
    @Expose
    private ArrayList<Piece> pieces = null;
    @SerializedName("Nationalites")
    @Expose
    private ArrayList<Nationalite> nationalites = null;

    public ArrayList<Civilite> getCivilites() {
        return civilites;
    }

    public void setCivilites(ArrayList<Civilite> civilites) {
        this.civilites = civilites;
    }

    public ArrayList<Pay> getPays() {
        return pays;
    }

    public void setPays(ArrayList<Pay> pays) {
        this.pays = pays;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<Nationalite> getNationalites() {
        return nationalites;
    }

    public void setNationalites(ArrayList<Nationalite> nationalites) {
        this.nationalites = nationalites;
    }

}
