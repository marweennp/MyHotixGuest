package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.Sejour;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //Login service call
    @GET("myhotixguest/authentifier?")
    Call<Guest> getGuestQuery(@Query("_user") String _user, @Query("_pass") String _pass);

    //Get Stay Details service call
    @GET("myhotixguest/getdetailsresa?")
    Call<Sejour> getStayQuery(@Query("resaid") String resaid);

    //Get Stay History Details service call
    @GET("myhotixguest/gethistosejour?")
    Call<ArrayList<Sejour>> getStayHistoryQuery(@Query("clientid") String clientid);

    //Get Bill Details service call
    @GET("myhotixguest/getdetailsfacture?")
    Call<Facture> getFactureQuery(@Query("id") String id, @Query("annee") String annee);



}

