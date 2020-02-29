package com.example.spacenbeyond.Views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spacenbeyond.Model.Imagem;
import com.example.spacenbeyond.R;

import static com.example.spacenbeyond.constantes.Constantes.IMAGES_CHAVES;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImagensFragment extends Fragment {
    private ImageView imagem;


    public ImagensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate( R.layout.fragment_imagens, container, false );

         imagem = view.findViewById(R.id.image_favorito);

        if (getArguments() != null){

            Bundle bundle = getArguments();

            Imagem imagem = bundle.getParcelable(IMAGES_CHAVES);
            imagem.setImagem(imagem.getImagem());
        }
        return view;
    }

}
