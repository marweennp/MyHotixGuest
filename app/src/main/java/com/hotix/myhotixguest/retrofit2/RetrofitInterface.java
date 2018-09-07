package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.Sejour;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //Login service call
    @GET("myhotixguest/authentifier?")
    Call<Guest> getGuestQuery(@Query("_user") String _user, @Query("_pass") String _pass);

    //Get Stay Details service call
    @GET("myhotixguest/getcurrentsejour?")
    Call<Sejour> getStayQuery(@Query("clientid") String clientid);

    //Get Bill Details service call
    @GET("myhotixguest/getdetailsfacture?")
    Call<Facture> getFactureQuery(@Query("id") String id, @Query("annee") String annee);

}

