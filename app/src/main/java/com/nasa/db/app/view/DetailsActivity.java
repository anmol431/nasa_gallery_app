package com.nasa.db.app.view;

import static com.nasa.db.app.constants.Constants.MOVIE_DETAILS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nasa.db.app.databinding.ActivityDetailsBinding;
import com.nasa.db.app.model.NasaDTO;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private NasaDTO nasaDTO = new NasaDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        nasaDTO = (NasaDTO) getIntent().getSerializableExtra(MOVIE_DETAILS);
        binding.setNasaDTO(nasaDTO);

        setUpListener();
    }

    private void setUpListener() {
        binding.tvBack.setOnClickListener(v -> onBackPressed());
    }
}
