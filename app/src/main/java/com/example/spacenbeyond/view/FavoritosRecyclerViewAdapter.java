package com.example.spacenbeyond.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritosRecyclerViewAdapter extends RecyclerView.Adapter<FavoritosRecyclerViewAdapter.ViewHolder> {

    private List<PhotoEntity> listaFotos;
    private FavoritosClick listener;

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

        holder.fotoFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.favoritosClickListener(photoResponse);
            }
        });
    }

    public void update(List<PhotoEntity> listaFotos){
        this.listaFotos = listaFotos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoFavoritos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoFavoritos = itemView.findViewById(R.id.imageFavoritos);
        }

        public void bind(PhotoEntity photoResponse){
            Picasso.get().load(photoResponse.getUrl()).into(fotoFavoritos);
        }
    }
}