package com.example.analyticslibrary;

import com.example.analyticslibrary.models.AppRatingRequest;
import com.example.analyticslibrary.models.GeolocationRequest;
import com.example.analyticslibrary.models.LogRequest;
import com.example.analyticslibrary.models.UpdateUserRequest;
import com.example.analyticslibrary.models.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/api/users")
    Call<Void> registerUserWithHeaders(
            @Header("Content-Type") String contentType,
            @Body UserRequest request
    );


    @PUT("/api/users/{userId}")
    Call<Void> updateUserWithHeaders(
            @Header("Content-Type") String contentType,
            @Path("userId") String userId,
            @Body UpdateUserRequest request
    );

    @POST("/api/app-ratings")
    Call<Void> rateAppWithHeaders(
            @Header("Content-Type") String contentType,
            @Body AppRatingRequest request
    );
    @POST("/api/logs")
    Call<Void> createLog(
            @Header("Content-Type") String contentType,
            @Body LogRequest request
    );

    @POST("/api/geolocation")
    Call<Void> sendGeolocation(
            @Header("Content-Type") String contentType,
            @Body GeolocationRequest request
    );

}