package com.example.spacenbeyond.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoEntity;
import com.example.spacenbeyond.view.FavoritosClick;
import com.example.spacenbeyond.view.FavoritosFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritosRecyclerViewAdapter extends RecyclerView.Adapter<FavoritosRecyclerViewAdapter.ViewHolder> {

    private List<PhotoEntity> listaFotos;
    private final FavoritosClick listener;

    public FavoritosRecyclerViewAdapter(List<PhotoEntity> listaFotos, FavoritosFragment listener) {
        this.listaFotos = listaFotos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritosRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoritos_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosRecyclerViewAdapter.ViewHolder holder, int position) {

        PhotoEntity photoResponse = listaFotos.get(position);
        holder.bind(photoResponse);

        holder.fotoFavoritos.setOnClickListener(view -> listener.favoritosClickListener(photoResponse));
    }

    public void update(List<PhotoEntity> listaFotos){
        this.listaFotos = listaFotos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView fotoFavoritos;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoFavoritos = itemView.findViewById(R.id.imageFavoritos);
        }

        void bind(PhotoEntity photoResponse){
            Picasso.get().load(photoResponse.getUrl()).into(fotoFavoritos);
        }
    }
}