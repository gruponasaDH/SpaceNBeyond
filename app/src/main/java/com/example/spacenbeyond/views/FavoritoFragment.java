package com.example.spacenbeyond.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacenbeyond.Interface.ImageRecyclerViewListener;
import com.example.spacenbeyond.Model.Imagem;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.adapter.ImagesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacenbeyond.constantes.Constantes.IMAGES_CHAVES;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment implements ImageRecyclerViewListener {
    private RecyclerView recyclerViewImages;

    private ImagesRecyclerViewAdapter adapter;


    public FavoritoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorito, container, false);

        recyclerViewImages = view.findViewById(R.id.recyclerView_images);

        adapter = new ImagesRecyclerViewAdapter(getListImages(), (ImageRecyclerViewListener) this);

        recyclerViewImages.setAdapter(adapter);

        recyclerViewImages.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

    private List<Imagem> getListImages() {
        List<Imagem> listaImagens = new ArrayList<>();

        return listaImagens;
    }

    @Override
    public void enviaImages(Imagem imagem) {

        Fragment fragment = new ImagensFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IMAGES_CHAVES, imagem);
        fragment.setArguments(bundle);

        replaceFragment(fragment);
    }


    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment)
                .commit();
    }

    public static FavoritoFragment newInstance() {
        return new FavoritoFragment();
    }
}