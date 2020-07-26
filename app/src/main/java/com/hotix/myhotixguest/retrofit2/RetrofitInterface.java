package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.BookingConfirmation;
import com.hotix.myhotixguest.models.BookingData;
import com.hotix.myhotixguest.models.BookingAvailability;
import com.hotix.myhotixguest.models.Complaint;
import com.hotix.myhotixguest.models.DeviseChangeResponse;
import com.hotix.myhotixguest.models.EventsResponse;
import com.hotix.myhotixguest.models.Facture;
import com.hotix.myhotixguest.models.Famille;
import com.hotix.myhotixguest.models.HotelInfos;
import com.hotix.myhotixguest.models.HotelSettings;
import com.hotix.myhotixguest.models.LoginResponse;
import com.hotix.myhotixguest.models.MenuItem;
import com.hotix.myhotixguest.models.MessageCategorieResponse;
import com.hotix.myhotixguest.models.MessagesResponse;
import com.hotix.myhotixguest.models.MobileResa;
import com.hotix.myhotixguest.models.NearbyPlaces;
import com.hotix.myhotixguest.models.Order;
import com.hotix.myhotixguest.models.OrdersResponse;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.models.RestaurantReservation;
import com.hotix.myhotixguest.models.RestaurantReservationsResponse;
import com.hotix.myhotixguest.models.RestaurantsResponse;
import com.hotix.myhotixguest.models.Reveil;
import com.hotix.myhotixguest.models.ReveilsResponse;
import com.hotix.myhotixguest.models.Sejour;
import com.hotix.myhotixguest.models.StartData;
import com.hotix.myhotixguest.models.SuccessResponse;

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
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/getdetailsresa?")
    Call<Sejour> getStayQuery(@Query("resaid") String resaid);

    //Get Stay History Details service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/gethistosejour?")
    Call<ArrayList<Sejour>> getStayHistoryQuery(@Query("clientid") String clientid);

    //Get Bill Details service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/getdetailsfacture?")
    Call<Facture> getFactureQuery(@Query("id") String id,
                                  @Query("annee") String annee);

    //Get All Products service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetAllProducts")
    Call<ArrayList<Famille>> getAllProductsQuery();

    //Get Activites service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetActivites")
    Call<EventsResponse> getActivitesQuery(@Query("HotelId") String HotelId,
                                           @Query("ClientId") String ClientId,
                                           @Query("CatId") String CatId);

    //Get Reclamations service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/getreclamations?")
    Call<ArrayList<Complaint>> getReclamationsQuery(@Query("resaid") String resaid);

    //Get Menu service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetMenu?")
    Call<ArrayList<MenuItem>> getMenuQuery(@Query("language") String language);

    //Get Get Categories Message service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetAllCategoriesMessage")
    Call<MessageCategorieResponse> getCategoriesMessageQuery();

    //Get GetMessages service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetMessages?")
    Call<MessagesResponse> getMessagesQuery(@Query("HotelId") String HotelId,
                                            @Query("ResaId") String ResaId,
                                            @Query("ResaGroupeId") String GroupeId,
                                            @Query("PaxId") String PaxId);

    //Get All Data service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/getalldata")
    Call<StartData> getAllDataQuery();

    //Get Pax Resa service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetPaxResa?")
    Call<ArrayList<Pax>> getPaxResaQuery(@Query("resaId") String resaId);

    //Get Commandes service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetCommandes?")
    Call<OrdersResponse> getCommandesQuery(@Query("HotelId") String hotelId,
                                           @Query("ResaId") String resaId,
                                           @Query("EtatId") String etatId);

    //Get Hotel Infos service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetHotelInfos")
    Call<HotelInfos> getHotelInfosQuery();

    //Get Booking Data service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetBookingData")
    Call<BookingData> getBookingDataQuery();

    //Get Change Corse service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetChangeCorse")
    Call<DeviseChangeResponse> getChangeCorseQuery(@Query("HotelId") String hotelId);

    //Get Restaurants service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetRestaurants")
    Call<RestaurantsResponse> getRestaurantsQuery();

    //Get Reservations Restaurant service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetReservationsRestaurant?")
    Call<RestaurantReservationsResponse> getRestaurantReservationsQuery(@Query("HotelId") String HotelId,
                                                                        @Query("RestId") String RestId,
                                                                        @Query("ClientId") String ClientId,
                                                                        @Query("OrigineId") String OrigineId);

    //Get Reveils service call
    @GET("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/GetReveils?")
    Call<ReveilsResponse> getReveilsQuery(@Query("HotelId") String HotelId,
                                          @Query("ResaId") String ResaId,
                                          @Query("ResaGroupeId") String GroupeId,
                                          @Query("PaxId") String PaxId);

    /**
     * POST********************************************************************************************
     **/

    //Login service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/authentifier")
    Call<LoginResponse> getGuestQuery(@Field("_user") String _user,
                                      @Field("_pass") String _pass);

    //Post UpdateSerialKey service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/UpdateSerialKey")
    Call<ResponseBody> updateSerialKeyQuery(@Field("clientId") String clientId,
                                            @Field("serialKey") String serialKey);

    //Post Inscription service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/Inscription")
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
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/UpdatePassword")
    Call<ResponseMsg> updatePassQuery(@Field("clientId") String clientId,
                                      @Field("pass") String pass);

    //Post SendMessage service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/SendMessage")
    Call<SuccessResponse> sendMessageQuery(@Field("hotelId") String hotelId,
                                           @Field("ResaId") String resaId,
                                           @Field("ResaGroupeId") String groupeId,
                                           @Field("PaxId") String paxId,
                                           @Field("TypeId") String typeId,
                                           @Field("CatId") String catId,
                                           @Field("MsgFrom") String from,
                                           @Field("MsgDetail") String detail,
                                           @Field("WorkStation") String workStation,
                                           @Field("Subject") String subject,
                                           @Field("Origine") String origine);

    //Post SendReclamation service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/SendReclamation")
    Call<ResponseBody> sendReclamationQuery(@Field("hotelId") String hotelId,
                                            @Field("numChambre") String numChambre,
                                            @Field("objectRec") String objectRec,
                                            @Field("detail") String detail,
                                            @Field("ResaId") String ResaId,
                                            @Field("origineRec") String origineRec);

    //Post SendActivityParticipation service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/SendActivityParticipation")
    Call<SuccessResponse> SendActivityParticipationQuery(@Field("hotelId") String hotelId,
                                                         @Field("ActivityId") String activityId,
                                                         @Field("ClientId") String clientId);

    //Post UpdateProfile service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/UpdateProfile")
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
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/ResetPassword")
    Call<ResponseMsg> resetPasswordQuery(@Field("email") String email);

    //Post UpdateReservationInfos service call
    @FormUrlEncoded
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/UpdateReservationInfos")
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
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/SendCommande")
    Call<SuccessResponse> sendCommandeQuery(@Header("Content-Type") String content_type,
                                            @Body Order order);

    //Check Booking Availability service call
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/CheckBookingAvailability")
    Call<BookingAvailability> checkBookingAvailabilityQuery(@Header("Content-Type") String content_type,
                                                            @Body MobileResa mobileResa);

    //Confirm Booking service call
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/ConfirmBooking")
    Call<BookingConfirmation> confirmBookingQuery(@Header("Content-Type") String content_type,
                                                  @Body MobileResa mobileResa);

    //Reserver Restaurant service call
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/ReserverRestaurant")
    Call<SuccessResponse> reserverRestaurantQuery(@Header("Content-Type") String content_type,
                                                  @Body RestaurantReservation restaurantReservation);

    //Add Reveil service call
    @POST("/HNGAPI/" + API_VERSION + "/api/MyHotixGuest/AddReveil")
    Call<SuccessResponse> addReveilQuery(@Header("Content-Type") String content_type,
                                         @Body Reveil Reveil);


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

