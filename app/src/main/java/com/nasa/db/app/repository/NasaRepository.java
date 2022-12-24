package com.nasa.db.app.repository;

import static com.nasa.db.app.constants.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.nasa.db.app.model.ApiResponseDTO;
import com.nasa.db.app.service.ApiRequest;
import com.nasa.db.app.service.RetrofitRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NasaRepository {
    private final ApiRequest apiRequest;

    public NasaRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<ApiResponseDTO> getNasaGalleryData(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getNasaData(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }
}
