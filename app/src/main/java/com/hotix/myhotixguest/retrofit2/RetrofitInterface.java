package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.Sejour;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //Login service call
    @GET("/HNGAPI/api/myhotixguest/authentifier?")
    Call<Guest> getGuestQuery(@Query("_user") String _user,
                              @Query("_pass") String _pass);

    //Get Stay Details service call
    @GET("/HNGAPI/api/myhotixguest/getdetailsresa?")
    Call<Sejour> getStayQuery(@Query("resaid") String resaid);

    //Get Stay History Details service call
    @GET("/HNGAPI/api/myhotixguest/gethistosejour?")
    Call<ArrayList<Sejour>> getStayHistoryQuery(@Query("clientid") String clientid);

    //Get Bill Details service call
    @GET("/HNGAPI/api/myhotixguest/getdetailsfacture?")
    Call<Facture> getFactureQuery(@Query("id") String id,
                                  @Query("annee") String annee);

    //Get Activites service call
    @GET("/HNGAPI/api/myhotixguest/getactivites")
    Call<ArrayList<Event>> getActivitesQuery();

    //Get Reclamations service call
    @GET("/HNGAPI/api/myhotixguest/getreclamations?")
    Call<ArrayList<Complaint>> getReclamationsQuery(@Query("resaid") String resaid);

    //Post SendReclamation service call
    @POST("/HNGAPI/api/myhotixguest/SendReclamation?")
    Call<ResponseBody> sendReclamationQuery(@Query("hotelId") String hotelId,
                                            @Query("numChambre") String numChambre,
                                            @Query("objectRec") String objectRec,
                                            @Query("detail") String detail,
                                            @Query("ResaId") String ResaId);

    //Post UpdateSerialKey service call
    @POST("/HNGAPI/api/myhotixguest/UpdateSerialKey?")
    Call<ResponseBody> updateSerialKeyQuery(@Query("clientId") String clientId,
                                            @Query("serialKey") String serialKey);


}

