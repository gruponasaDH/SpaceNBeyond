package com.example.spacenbeyond.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.view.FavoritosClick;
import com.example.spacenbeyond.model.PhotoResponse;

import com.example.spacenbeyond.util.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavoritosFragment extends Fragment implements FavoritosClick {

    private RecyclerView recyclerView;
    private FavoritosRecyclerViewAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;


    public FavoritosFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new FavoritosRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(AppUtil.getIdUsuario(getContext()) + "/favorites");

        carregaFavoritos();

        return view;
    }

    private void carregaFavoritos(){
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PhotoResponse> listaFotos = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()){
                    PhotoResponse photoResponse = child.getValue(PhotoResponse.class);
                    listaFotos.add(photoResponse);
                }

                adapter.update(listaFotos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void favoritosClickListener(PhotoResponse photoResponse) {

        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
