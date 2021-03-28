package com.saikalyandaroju.homeoprojecr.NetworkService;

import com.saikalyandaroju.homeoprojecr.NetworkService.MyApiInterface;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL = "https://api.spacexdata.com/v4/";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MyApiInterface requestApi = retrofit.create(MyApiInterface.class);

    public static MyApiInterface getRequestApi() {
        return requestApi;
    }
}
