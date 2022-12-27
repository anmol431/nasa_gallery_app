package com.nasa.db.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.nasa.db.app.R;
import com.nasa.db.app.databinding.ActivityMainBinding;
import com.nasa.db.app.model.NasaDTO;
import com.nasa.db.app.view.adapter.NasaDetailsAdapter;
import com.nasa.db.app.view_model.NasaDetailsViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NasaDetailsAdapter.OnItemClickListener {
    private static final String NASA_DATA = "nasa_details";
    boolean doubleBackToExit = false;
    private ActivityMainBinding binding;
    private NasaDetailsViewModel viewModel;
    private NasaDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NasaDetailsViewModel.class);

        setUpAdapter();
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
            }
        });
    }

    private void setUpAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rcvMovies.setLayoutManager(layoutManager);

        adapter = new NasaDetailsAdapter(this);
        binding.rcvMovies.setAdapter(adapter);
    }

    @Override
    public void onImageClick(NasaDTO nasaDTO, ImageView imageView) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(NASA_DATA, nasaDTO);
        try {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                    imageView, Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));
            startActivity(intent, compat.toBundle());
        } catch (Exception e) {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            finishAndRemoveTask();
            finishAffinity();
            return;
        }
        doubleBackToExit = true;
        Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExit = false, 5000);
    }
}
