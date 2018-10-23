package com.hotix.myhotixguest.retrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL;
import static com.hotix.myhotixguest.helpers.ConstantConfig.BASE_URL_2;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitUrl = null;

    public static Retrofit getClientHngApi() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(null)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    //Retrofit Client Without Base_Url
    public static Retrofit getClientGooglePlaces() {
        if (retrofitUrl == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(null)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofitUrl = new Retrofit.Builder()
                    .baseUrl(BASE_URL_2)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitUrl;
    }


}
