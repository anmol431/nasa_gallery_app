package com.nasa.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nasa.db.app.model.NasaDTO;
import com.nasa.db.app.repository.NasaRepository;

import java.util.ArrayList;

public class NasaDetailsViewModel extends AndroidViewModel {

    private final NasaRepository nasaRepository;
    private LiveData<ArrayList<NasaDTO>> popularMoviesResponseDto;

    public NasaDetailsViewModel(@NonNull Application application) {
        super(application);
        nasaRepository = new NasaRepository(application);
        popularMoviesResponseDto = new MutableLiveData<>();
    }

    public void getNasaGalleryData() {
        popularMoviesResponseDto = nasaRepository.getNasaGallery();
    }

    public LiveData<ArrayList<NasaDTO>> getNasaData() {
        return popularMoviesResponseDto;
    }
}
