package com.nasa.db.app.service;

import com.nasa.db.app.model.ApiResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("movie/popular")
    Call<ApiResponseDTO> getNasaData(@Query("api_key") String api_key, @Query("page") int page);
}
