package com.saikalyandaroju.homeoprojecr.NetworkService;

import com.saikalyandaroju.homeoprojecr.POJOS.CrewResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyApiInterface {
    @GET("crew")
    Observable<List<CrewResponse>> getUsers();

}
