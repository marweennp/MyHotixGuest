package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.models.Event;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Famille;
import com.hotix.myhotixguest.models.Guest;
import com.hotix.myhotixguest.models.HotelInfos;
import com.hotix.myhotixguest.models.HotelSettings;
import com.hotix.myhotixguest.models.MenuItem;
import com.hotix.myhotixguest.models.Message;
import com.hotix.myhotixguest.models.NearbyPlaces;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.models.Sejour;
import com.hotix.myhotixguest.models.StartData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.hotix.myhotixguest.helpers.ConstantConfig.API_VERSION;

public interface RetrofitInterface {

    /**
     * GET********************************************************************************************
     **/

    //Get Stay Details service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/getdetailsresa?")
    Call<Sejour> getStayQuery(@Query("resaid") String resaid);

    //Get Stay History Details service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/gethistosejour?")
    Call<ArrayList<Sejour>> getStayHistoryQuery(@Query("clientid") String clientid);

    //Get Bill Details service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/getdetailsfacture?")
    Call<Facture> getFactureQuery(@Query("id") String id,
                                  @Query("annee") String annee);

    //Get All Products service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetAllProducts")
    Call<ArrayList<Famille>> getAllProductsQuery();

    //Get Activites service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/getactivites")
    Call<ArrayList<Event>> getActivitesQuery();

    //Get Reclamations service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/getreclamations?")
    Call<ArrayList<Complaint>> getReclamationsQuery(@Query("resaid") String resaid);

    //Get Menu service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetMenu?")
    Call<ArrayList<MenuItem>> getMenuQuery(@Query("language") String language);

    //Get GetMessages service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetMessages?")
    Call<ArrayList<Message>> getMessagesQuery(@Query("ResaId") String ResaId,
                                              @Query("PaxId") String PaxId);

    //Get All Data service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/getalldata")
    Call<StartData> getAllDataQuery();

    //Get Pax Resa service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetPaxResa?")
    Call<ArrayList<Pax>> getPaxResaQuery(@Query("resaId") String resaId);

    //Get Commandes service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetCommandes?")
    Call<ArrayList<Order>> getCommandesQuery(@Query("ResaId") String resaId);

    //Get Hotel Infos service call
    @GET("/HNGAPI/" + API_VERSION + "/api/myhotixguest/GetHotelInfos")
    Call<HotelInfos> getHotelInfosQuery();

    /**
     * POST********************************************************************************************
     **/

    //Login service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/authentifier")
    Call<Guest> getGuestQuery(@Field("_user") String _user,
                              @Field("_pass") String _pass);

    //Post UpdateSerialKey service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/UpdateSerialKey")
    Call<ResponseBody> updateSerialKeyQuery(@Field("clientId") String clientId,
                                            @Field("serialKey") String serialKey);

    //Post Inscription service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/Inscription")
    Call<ResponseMsg> inscriptionQuery(@Field("hotelId") String hotelId,
                                       @Field("login") String login,
                                       @Field("pwd") String pwd,
                                       @Field("email") String email,
                                       @Field("phone") String phone,
                                       @Field("nom") String nom,
                                       @Field("prenom") String prenom,
                                       @Field("datenaissance") String datenaissance,
                                       @Field("adresse") String adresse,
                                       @Field("nationaliteId") String nationaliteId,
                                       @Field("civiliteId") String civiliteId,
                                       @Field("paysId") String paysId);

    //Post UpdatePass service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/UpdatePassword")
    Call<ResponseMsg> updatePassQuery(@Field("clientId") String clientId,
                                      @Field("pass") String pass);

    //Post SendReclamation service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/SendReclamation")
    Call<ResponseBody> sendReclamationQuery(@Field("hotelId") String hotelId,
                                            @Field("numChambre") String numChambre,
                                            @Field("objectRec") String objectRec,
                                            @Field("detail") String detail,
                                            @Field("ResaId") String ResaId);

    //Post UpdateProfile service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/UpdateProfile")
    Call<ResponseMsg> updateProfileQuery(@Field("hotelId") String hotelId,
                                         @Field("clientId") String clientId,
                                         @Field("email") String email,
                                         @Field("phone") String phone,
                                         @Field("nom") String nom,
                                         @Field("prenom") String prenom,
                                         @Field("datenaissance") String datenaissance,
                                         @Field("adresse") String adresse);

    //Post ResetPassword service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/ResetPassword")
    Call<ResponseMsg> resetPasswordQuery(@Field("email") String email);

    //Post UpdateReservationInfos service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/UpdateReservationInfos")
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
                                                  @Field("Image") String Image,
                                                  @Field("civilite") String civilite);

    //Post SendCommande service call
    @POST("/HNGAPI/" + API_VERSION + "/api/myhotixguest/SendCommande")
    Call<ResponseMsg> sendCommandeQuery(@Header("Content-Type") String content_type,
                                        @Body Order order);


    /**
     * GooGle Places********************************************************************************
     **/
    //Nearby Places service call
    @GET("/maps/api/place/nearbysearch/json?")
    Call<NearbyPlaces> getNearbyPlacesQuery(@Query("location") String location,
                                            @Query("radius") String radius,
                                            @Query("type") String type,
                                            @Query("key") String key);


    /**
     * Ping Serveur*********************************************************************************
     **/
    //Is Connected service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixguest/isconnected")
    Call<ResponseBody> isConnectedQuery();


    /**
     * Hotel Config*********************************************************************************
     **/
    //Get Infos service call
    @GET("/hotixsupport/api/myhotix/GetInfos?")
    Call<HotelSettings> getInfosQuery(@Query("codehotel") String codehotel,
                                      @Query("applicationId") String applicationId);


}

