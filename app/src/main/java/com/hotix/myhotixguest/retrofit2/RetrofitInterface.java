package com.hotix.myhotixguest.retrofit2;

import com.hotix.myhotixguest.models.FactureModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("myhotixguest/getdetailsfacture?")
    Call<FactureModel> getFactureModelQuery(@Query("id") String id, @Query("annee") String annee);

}

