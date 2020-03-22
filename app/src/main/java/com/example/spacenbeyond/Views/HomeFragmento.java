package com.example.spacenbeyond.views;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spacenbeyond.Interfaces.ComunicacaoFragmentHome;
import com.example.spacenbeyond.Model.DadosHome;
import com.example.spacenbeyond.R;

import static com.example.spacenbeyond.constantes.Constantes.DADOS_HOME;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmento extends Fragment implements ComunicacaoFragmentHome {
    private ComunicacaoFragmentHome dadosHome;

    private Button eng;
    private Button pt;
    private ImageView imagem;
    private TextView nomeFoto;
    private TextView nomeAutor;
    private TextView descricao;
    private ImageView favoritos;
    private ImageView compartilhar;
    private ImageView dowlo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dadosHome = (ComunicacaoFragmentHome) context;
        } catch (Exception ex){
        ex.printStackTrace();
    } }

    public HomeFragmento() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragmento, container, false);
        initViews(view);
        return view;
    }
    private void initViews(View view) {
        eng = view.findViewById(R.id.buttonEng);
        pt = view.findViewById(R.id.button_pt);
        imagem = view.findViewById(R.id.imagem);
        nomeAutor = view.findViewById(R.id.nome_Autor);
        dowlo = view.findViewById(R.id.dowlowd);
        nomeFoto = view.findViewById(R.id.fotonome);
        compartilhar = view.findViewById(R.id.compartilhar);
        favoritos = view.findViewById(R.id.favoritos);
        descricao = view.findViewById(R.id.descricao);
    }
    @Override
    public void enviarDadosFragmentHome(DadosHome dadosHome) {

        if (getArguments() != null) {

            Bundle bundle = getArguments();

            DadosHome dadosHome1 = bundle.getParcelable(DADOS_HOME);

            nomeAutor.setText(dadosHome.getNomeAutor());
            nomeFoto.setText(dadosHome.getNomeFoto());
            descricao.setText(dadosHome.getDescricao());

            Drawable drawable = getResources().getDrawable(dadosHome.getImagem());
            Drawable drawable1 = getResources().getDrawable(dadosHome.getCompartilhar());
            Drawable drawable2 = getResources().getDrawable(dadosHome.getFavoritos());
            Drawable drawable3 = getResources().getDrawable(dadosHome.getDowlo());

            imagem.setImageDrawable(drawable);
            favoritos.setImageDrawable(drawable);
            compartilhar.setImageDrawable(drawable);
            dowlo.setImageDrawable(drawable);
        }
    }
}
