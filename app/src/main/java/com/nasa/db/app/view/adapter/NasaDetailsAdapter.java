package com.nasa.db.app.view.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nasa.db.app.databinding.AdapterNasaDetailsBinding;
import com.nasa.db.app.model.NasaDTO;

import java.util.ArrayList;
import java.util.List;

public class NasaDetailsAdapter extends RecyclerView.Adapter<NasaDetailsAdapter.ViewHolder> {
    ArrayList<NasaDTO> nasaDTOS;
    OnItemClickListener listener;

    public NasaDetailsAdapter(OnItemClickListener clickListener) {
        this.listener = clickListener;
        nasaDTOS = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<NasaDTO> list) {
        nasaDTOS.addAll(list);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearList() {
        nasaDTOS.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterNasaDetailsBinding binding = AdapterNasaDetailsBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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

        Glide.with(holder.itemView.getContext()).load(nasaDTOS.get(position).getUrl())
                .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.binding.progress.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.binding.progress.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.binding.ivBanner);

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return nasaDTOS.size();
    }

    public interface OnItemClickListener {
        void onImageClick(NasaDTO nasaDTO, ImageView imageView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterNasaDetailsBinding binding;

        public ViewHolder(AdapterNasaDetailsBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
