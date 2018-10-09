package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Famille;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.Message;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.Reservation;
import com.hotix.myhotixguest.models.Sejour;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.models.StartData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    /**
     GET********************************************************************************************
     **/
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

    //Get All Products service call
    @GET("/HNGAPI/api/myhotixguest/GetAllProducts")
    Call<ArrayList<Famille>> getAllProductsQuery();

    //Get Activites service call
    @GET("/HNGAPI/api/myhotixguest/getactivites")
    Call<ArrayList<Event>> getActivitesQuery();

    //Get Reclamations service call
    @GET("/HNGAPI/api/myhotixguest/getreclamations?")
    Call<ArrayList<Complaint>> getReclamationsQuery(@Query("resaid") String resaid);

    //Get GetMessages service call
    @GET("/HNGAPI/api/myhotixguest/GetMessages?")
    Call<ArrayList<Message>> getMessagesQuery(@Query("ResaId") String ResaId,
                                              @Query("PaxId") String PaxId);

    //Post SendReclamation service call
    @POST("/HNGAPI/api/myhotixguest/SendReclamation?")
    Call<ResponseBody> sendReclamationQuery(@Query("hotelId") String hotelId,
                                            @Query("numChambre") String numChambre,
                                            @Query("objectRec") String objectRec,
                                            @Query("detail") String detail,
                                            @Query("ResaId") String ResaId);

    //Get All Data service call
    @GET("/HNGAPI/api/myhotixguest/getalldata")
    Call<StartData> getAllDataQuery();

    //Get Pax Resa service call
    @GET("/HNGAPI/api/myhotixguest/GetPaxResa?")
    Call<ArrayList<Pax>> getPaxResaQuery(@Query("resaId") String resaId);

    /**
    POST********************************************************************************************
    **/
    //Post UpdateSerialKey service call
    @POST("/HNGAPI/api/myhotixguest/UpdateSerialKey?")
    Call<ResponseBody> updateSerialKeyQuery(@Query("clientId") String clientId,
                                            @Query("serialKey") String serialKey);

    //Post Inscription service call
    @POST("/HNGAPI/api/myhotixguest/Inscription?")
    Call<ResponseMsg> inscriptionQuery(@Query("hotelId") String hotelId,
                                       @Query("login") String login,
                                       @Query("pwd") String pwd,
                                       @Query("email") String email,
                                       @Query("phone") String phone,
                                       @Query("nom") String nom,
                                       @Query("prenom") String prenom,
                                       @Query("datenaissance") String datenaissance,
                                       @Query("adresse") String adresse);

    //Post UpdatePass service call
    @POST("/HNGAPI/api/myhotixguest/UpdatePass?")
    Call<ResponseMsg> updatePassQuery(@Query("clientId") String clientId,
                                      @Query("pass") String pass);

    //Post UpdateProfile service call
    @POST("/HNGAPI/api/myhotixguest/UpdateProfile?")
    Call<ResponseMsg> updateProfileQuery(@Query("hotelId") String hotelId,
                                         @Query("clientId") String clientId,
                                         @Query("email") String email,
                                         @Query("phone") String phone,
                                         @Query("nom") String nom,
                                         @Query("prenom") String prenom,
                                         @Query("datenaissance") String datenaissance,
                                         @Query("adresse") String adresse);

    //Post ResetPassword service call
    @POST("/HNGAPI/api/myhotixguest/ResetPassword?")
    Call<ResponseMsg> resetPasswordQuery(@Query("email") String email);

    //Post UpdateReservationInfos service call
    @FormUrlEncoded
    @POST("/HNGAPI/api/myhotixguest/UpdateReservationInfos")
    Call<ResponseMsg> updateReservationInfosQuery(@Field("clientId") String clientId,
                                                  @Field("NomClient") String NomClient,
                                                  @Field("PrenomClient") String PrenomClient,
                                                  @Field("PaysId") String PaysId,
                                                  @Field("clientAdresse") String clientAdresse,
                                                  @Field("DateNaiss") String DateNaiss,
                                                  @Field("LieuNaiss") String LieuNaiss,
                                                  @Field("Sexe") String Sexe,
                                                  @Field("SitFam") String SitFam,
                                                  @Field("Fumeur") String Fumeur,
                                                  @Field("Handicape") String Handicape,
                                                  @Field("DocTypeId") String DocTypeId,
                                                  @Field("DocIdNum") String DocIdNum,
                                                  @Field("Email") String Email,
                                                  @Field("Gsm") String Gsm,
                                                  @Field("Profession") String Profession,
                                                  @Field("Image") String Image);


}

