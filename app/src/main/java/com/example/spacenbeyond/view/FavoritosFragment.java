package com.example.spacenbeyond.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoEntity;

import com.example.spacenbeyond.util.AppUtil;
import com.example.spacenbeyond.view.adapter.FavoritosRecyclerViewAdapter;
import com.example.spacenbeyond.viewmodel.PhotoViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacenbeyond.util.AppUtil.verificaConexaoComInternet;

public class FavoritosFragment extends Fragment implements FavoritosClick {

    private RecyclerView recyclerView;
    private FavoritosRecyclerViewAdapter adapter;
    private PhotoViewModel photoViewModel;
    public static final String FAVORITO_CHAVE = "favorito";

    private DatabaseReference reference;

    public FavoritosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        initViews(view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (verificaConexaoComInternet(getContext())) {
            photoViewModel.carregaFavoritos(adapter);
        } else {
            photoViewModel.carregaDadosBD();
            photoViewModel.liveDataPhoto.observe(getViewLifecycleOwner(), (List<PhotoEntity> result) -> {

                List<PhotoEntity> listaFotos = new ArrayList<>();

                for (PhotoEntity child : result){
                    PhotoEntity photoResponse = new PhotoEntity(child.getCopyright(), child.getDate(), child.getExplanation(), child.getTitle(), child.getUrl());
                    listaFotos.add(photoResponse);
                }

                adapter.update(listaFotos);
            });
        }

        ImageView btnVoltar = view.findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            getFragmentManager().beginTransaction().remove(FavoritosFragment.this).commit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        adapter = new FavoritosRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference(AppUtil.getIdUsuario(getContext()) + "/favorites");
    }

    @Override
    public void favoritosClickListener(PhotoEntity photo) {

        if (verificaConexaoComInternet(getContext())) {
            reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Fragment fragment = new VisualizarFavoritoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(FAVORITO_CHAVE, photo);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Fragment fragment = new VisualizarFavoritoFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(FAVORITO_CHAVE, photo);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }
}
