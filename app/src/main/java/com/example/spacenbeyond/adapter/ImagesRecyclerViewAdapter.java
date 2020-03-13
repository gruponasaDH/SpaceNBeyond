package com.example.spacenbeyond.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.spacenbeyond.Interface.ImageRecyclerViewListener;
import com.example.spacenbeyond.Model.Imagem;
import com.example.spacenbeyond.R;

import java.util.List;

public class ImagesRecyclerViewAdapter extends Adapter<ImagesRecyclerViewAdapter.ViewHolder> {

    private List<Imagem> listaImagens;

    private ImageRecyclerViewListener listener;

    public ImagesRecyclerViewAdapter(List<Imagem> listaImagens, ImageRecyclerViewListener listener) {
        this.listaImagens = listaImagens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_item_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Imagem imagem = listaImagens.get(position);
        holder.onBind(imagem);

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.enviaImages(imagem);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return listaImagens.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.image_favorito);
        }

        public void onBind(Imagem imagem) {
            imagem.setImagem(imagem.getImagem());
        }
    }
}
