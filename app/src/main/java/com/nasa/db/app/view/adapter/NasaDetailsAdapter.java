package com.nasa.db.app.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nasa.db.app.databinding.AdapterNasaDetailsBinding;
import com.nasa.db.app.model.NasaDTO;

import java.util.ArrayList;

public class NasaDetailsAdapter extends RecyclerView.Adapter<NasaDetailsAdapter.ViewHolder> {
    ArrayList<NasaDTO> nasaDTOS;
    OnItemClickListener listener;

    public NasaDetailsAdapter(ArrayList<NasaDTO> list, OnItemClickListener clickListener) {
        this.nasaDTOS = list;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterNasaDetailsBinding binding = AdapterNasaDetailsBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setNasaDTO(nasaDTOS.get(position));
        holder.binding.setListener(listener);

        if (position % 2 == 0) {
            holder.binding.space.setVisibility(View.GONE);
        } else {
            holder.binding.space.setVisibility(View.INVISIBLE);
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return nasaDTOS.size();
    }

    public interface OnItemClickListener {
        void onMovieClick(NasaDTO nasaDTO, ImageView imageView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterNasaDetailsBinding binding;

        public ViewHolder(AdapterNasaDetailsBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
