package com.nasa.db.app.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nasa.db.app.databinding.ActivityDetailsBinding;
import com.nasa.db.app.view.adapter.NasaDetailsAdapter;
import com.nasa.db.app.view_model.NasaDetailsViewModel;

public class DetailsActivity extends AppCompatActivity {
    private static final String POSITION = "position";
    private ActivityDetailsBinding binding;
    private NasaDetailsViewModel viewModel;
    private NasaDetailsAdapter adapter;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NasaDetailsViewModel.class);

        if (getIntent().hasExtra(POSITION)) {
            position = getIntent().getIntExtra(POSITION, 0);
        }

        setUpAdapter();
        setUpListener();
        getMovieList();
        setObserver();
    }

    private void getMovieList() {
        adapter.clearList();
        viewModel.getNasaGalleryData();
    }

    private void setObserver() {
        viewModel.getNasaData().observe(this, response -> {
            if (response.size() > 0) {
                adapter.clearList();
                adapter.submitList(response);
                binding.vpNasa.setCurrentItem(position, false);
            }
        });
    }

    private void setUpListener() {
        binding.tvBack.setOnClickListener(v -> onBackPressed());
    }

    private void setUpAdapter() {
        adapter = new NasaDetailsAdapter();
        binding.vpNasa.setAdapter(adapter);
    }
}
