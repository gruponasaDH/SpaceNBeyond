package com.example.spacenbeyond.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacenbeyond.Interfaces.AjudaListener;
import com.example.spacenbeyond.Model.Ajuda;
import com.example.spacenbeyond.R;

public class AjudaRecyclerViewAdapter extends RecyclerView.Adapter<AjudaRecyclerViewAdapter.ViewHolder> {

    private List <Ajuda> ajuda;

    private AjudaListener listener;


    public AjudaRecyclerViewAdapter(List<Ajuda> listaAjuda, AjudaListener listener){
        this.ajuda = listaAjuda;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_ajuda_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final Ajuda ajuda = ajuda.get(position);
        holder.onBind((Ajuda) ajuda);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.enviaAjuda((Ajuda) ajuda);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ajuda.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleAjuda;
        private TextView subtitleAjuda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleAjuda = itemView.findViewById(R.id.title);
            subtitleAjuda = itemView.findViewById(R.id.subtitle);
        }

        public void onBind(Ajuda ajuda){
            titleAjuda.setText(ajuda.getTitle());
            subtitleAjuda.setText(ajuda.getSubtitle());
        }
    }
}