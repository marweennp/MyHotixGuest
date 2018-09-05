package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.Facture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("myhotixguest/getfacture?")
    Call<Facture> getFactureQuery(@Query("chambre") String chambre);

}

