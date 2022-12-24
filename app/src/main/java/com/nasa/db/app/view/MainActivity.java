package com.nasa.db.app.view;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static com.nasa.db.app.constants.Constants.MOVIE_DETAILS;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nasa.db.app.R;
import com.nasa.db.app.databinding.ActivityMainBinding;
import com.nasa.db.app.model.NasaDTO;
import com.nasa.db.app.view.adapter.NasaDetailsAdapter;
import com.nasa.db.app.view_model.NasaDetailsViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NasaDetailsAdapter.OnItemClickListener {
    private final ArrayList<NasaDTO> nasaDTOS = new ArrayList<>();
    boolean doubleBackToExit = false;
    private ActivityMainBinding binding;
    private NasaDetailsViewModel viewModel;
    private int currentPage = 1;
    private int totalPages;
    private boolean mIsLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NasaDetailsViewModel.class);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rcvMovies.setLayoutManager(layoutManager);
        binding.rcvMovies.addOnScrollListener(onScrollLoadList(layoutManager));

        binding.srlMovies.setOnRefreshListener(() -> {
            nasaDTOS.clear();
            binding.rcvMovies.setAdapter(null);
            currentPage = 1;
            getMovieList();
        });

        setObserver();
    }

    private void getMovieList() {
        if (isNetworkAvailable()) {
            binding.srlMovies.setRefreshing(true);
            viewModel.getPopularMovies(currentPage);
        } else {
            Toast.makeText(this, getString(R.string.no_network_text), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMovieClick(NasaDTO nasaDTO, ImageView imageView) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_DETAILS, nasaDTO);
        try {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                    imageView, Objects.requireNonNull(ViewCompat.getTransitionName(imageView)));
            startActivity(intent, compat.toBundle());
        } catch (Exception e) {
            startActivity(intent);
        }
    }

    private void setObserver() {
        viewModel.getNasaData().observe(this, response -> {
            if (response != null) {
                totalPages = response.getTotal_pages();
                nasaDTOS.addAll(response.getResults());
                setUpAdapter(response.getResults().size());
            }
            binding.srlMovies.setRefreshing(false);
        });
    }

    private void setUpAdapter(int size) {
        NasaDetailsAdapter adapter = new NasaDetailsAdapter(nasaDTOS, this);
        binding.rcvMovies.setAdapter(adapter);
        binding.rcvMovies.scrollToPosition(nasaDTOS.size() - size);
        mIsLoading = false;
    }

    private RecyclerView.OnScrollListener onScrollLoadList(final LinearLayoutManager mLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 20) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount > PAGE_SIZE) {
                            if (isNetworkAvailable()) {
                                if (totalPages > currentPage) {
                                    currentPage++;
                                    mIsLoading = true;
                                    getMovieList();
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
