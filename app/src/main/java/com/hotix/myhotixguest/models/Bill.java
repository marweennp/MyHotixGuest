package com.hotix.myhotixguest.models;

public class Bill {

    private String bill_id;
    private String bill_owner;
    private String bill_number;
    private String bill_transaction_title;
    private String bill_transaction_date;
    private String bill_transaction_sum;
    private String bill_transaction_tva;
    private String bill_total_sum;
    private String bill_total_sum_ttc;
    private String bill_tva;
    private String bill_fdcst;

    public Bill() {

    }

    public Bill(String bill_transaction_title, String bill_transaction_date, String bill_transaction_sum, String bill_transaction_tva) {
        this.bill_transaction_title = bill_transaction_title;
        this.bill_transaction_date = bill_transaction_date;
        this.bill_transaction_sum = bill_transaction_sum;
        this.bill_transaction_tva = bill_transaction_tva;
    }

    public Bill(String bill_id, String bill_owner, String bill_number, String bill_transaction_title, String bill_transaction_date, String bill_transaction_sum, String bill_transaction_tva, String bill_total_sum, String bill_total_sum_ttc, String bill_tva, String bill_fdcst) {
        this.bill_id = bill_id;
        this.bill_owner = bill_owner;
        this.bill_number = bill_number;
        this.bill_transaction_title = bill_transaction_title;
        this.bill_transaction_date = bill_transaction_date;
        this.bill_transaction_sum = bill_transaction_sum;
        this.bill_transaction_tva = bill_transaction_tva;
        this.bill_total_sum = bill_total_sum;
        this.bill_total_sum_ttc = bill_total_sum_ttc;
        this.bill_tva = bill_tva;
        this.bill_fdcst = bill_fdcst;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_owner() {
        return bill_owner;
    }

    public void setBill_owner(String bill_owner) {
        this.bill_owner = bill_owner;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getBill_transaction_title() {
        return bill_transaction_title;
    }

    public void setBill_transaction_title(String bill_transaction_title) {
        this.bill_transaction_title = bill_transaction_title;
    }

    public String getBill_transaction_date() {
        return bill_transaction_date;
    }

    public void setBill_transaction_date(String bill_transaction_date) {
        this.bill_transaction_date = bill_transaction_date;
    }

    public String getBill_transaction_sum() {
        return bill_transaction_sum;
    }

    public void setBill_transaction_sum(String bill_transaction_sum) {
        this.bill_transaction_sum = bill_transaction_sum;
    }

    public String getBill_transaction_tva() {
        return bill_transaction_tva;
    }

    public void setBill_transaction_tva(String bill_transaction_tva) {
        this.bill_transaction_tva = bill_transaction_tva;
    }

    public String getBill_total_sum() {
        return bill_total_sum;
    }

    public void setBill_total_sum(String bill_total_sum) {
        this.bill_total_sum = bill_total_sum;
    }

    public String getBill_total_sum_ttc() {
        return bill_total_sum_ttc;
    }

    public void setBill_total_sum_ttc(String bill_total_sum_ttc) {
        this.bill_total_sum_ttc = bill_total_sum_ttc;
    }

    public String getBill_tva() {
        return bill_tva;
    }

    public void setBill_tva(String bill_tva) {
        this.bill_tva = bill_tva;
    }

    public String getBill_fdcst() {
        return bill_fdcst;
    }

    public void setBill_fdcst(String bill_fdcst) {
        this.bill_fdcst = bill_fdcst;
    }
}