package com.example.spacenbeyond.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacenbeyond.Interfaces.AjudaListener;
import com.example.spacenbeyond.model.DadosAjuda;
import com.example.spacenbeyond.R;

import static android.media.CamcorderProfile.get;

public class AjudaRecyclerViewAdapter extends RecyclerView.Adapter<AjudaRecyclerViewAdapter.ViewHolder> {

    private List<DadosAjuda> ajuda;

    private AjudaListener listener;


    public AjudaRecyclerViewAdapter(List<DadosAjuda> listaAjuda, AjudaListener listener){
        this.ajuda = listaAjuda;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ajuda_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DadosAjuda dadosAjuda = ajuda.get(position);

        holder.onBind(dadosAjuda);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.enviaAjuda(dadosAjuda);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ajuda.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView titleAjuda;
        private TextView subtitleAjuda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView_ajuda);
            titleAjuda = itemView.findViewById(R.id.title);
            subtitleAjuda = itemView.findViewById(R.id.text_view_subtitle);
        }

        public void onBind(DadosAjuda dadosAjuda){
            image.setImageResource(R.drawable.galaxia);
            titleAjuda.setText(dadosAjuda.getTitle());
            subtitleAjuda.setText(dadosAjuda.getSubtitle());
        }
    }
}