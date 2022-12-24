package com.nasa.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nasa.db.app.model.ApiResponseDTO;
import com.nasa.db.app.repository.NasaRepository;

public class NasaDetailsViewModel extends AndroidViewModel {

    private final NasaRepository nasaRepository;
    private LiveData<ApiResponseDTO> popularMoviesResponseDto;

    public NasaDetailsViewModel(@NonNull Application application) {
        super(application);
        nasaRepository = new NasaRepository();
        popularMoviesResponseDto = new MutableLiveData<>();
    }

    public void getPopularMovies(int page) {
        popularMoviesResponseDto = nasaRepository.getNasaGalleryData(page);
    }

    public LiveData<ApiResponseDTO> getNasaData() {
        return popularMoviesResponseDto;
    }
}
