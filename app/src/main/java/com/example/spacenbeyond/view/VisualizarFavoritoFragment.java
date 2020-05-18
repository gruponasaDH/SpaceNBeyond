package com.example.spacenbeyond.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoEntity;
import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.viewmodel.PhotoViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.squareup.picasso.Picasso;

import static com.example.spacenbeyond.util.AppUtil.verificaConexaoComInternet;
import static com.example.spacenbeyond.view.FavoritosFragment.FAVORITO_CHAVE;

public class VisualizarFavoritoFragment extends Fragment {

    private TextView textViewFoto;
    private TextView textViewAutor;
    private ImageView imageViewFoto;
    private ImageView imageViewFavorited;
    private TextView textViewDescricao;
    private MaterialButton materialButtonPT;
    private MaterialButton materialButtonENG;
    private PhotoViewModel photoViewModel;


    public VisualizarFavoritoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_visualizar_favorito, container, false);

        initViews(view);
        PhotoEntity photoEntity = new PhotoEntity();


        if (getArguments() != null){

            Bundle bundle = getArguments();
            photoEntity = bundle.getParcelable(FAVORITO_CHAVE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Picasso.get().load(photoEntity.getUrl()).into(imageViewFoto);
            }

            textViewFoto.setText(photoEntity.getTitle());
            textViewAutor.setText(photoEntity.getCopyright());
            textViewDescricao.setText(photoEntity.getExplanation());
        }

        materialButtonPT.setOnClickListener(v -> {
            getPortugueseTitle();
            getPortugueseDescription();
        });
        materialButtonENG.setOnClickListener(v -> {
            getEnglishTitle();
            getEnglishDescription();
        });

        PhotoEntity finalPhotoEntity = photoEntity;
        PhotoEntity finalPhotoEntity1 = photoEntity;
        imageViewFavorited.setOnClickListener(v -> {
            if (verificaConexaoComInternet(getContext())) {
                PhotoResponse photoResponse = new PhotoResponse(finalPhotoEntity.getCopyright(), finalPhotoEntity.getDate(), finalPhotoEntity1.getExplanation(), finalPhotoEntity1.getTitle(), finalPhotoEntity1.getUrl());
                photoViewModel.deletarFavorito(photoResponse);
            }
            else {
                photoViewModel.deletarPhotoEntity(textViewFoto.getText().toString());
            }

            getFragmentManager().beginTransaction().remove(VisualizarFavoritoFragment.this).commit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void getPortugueseDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                        translatedText -> textViewDescricao.setText(translatedText)).addOnFailureListener(
                        e -> {

                        })).addOnFailureListener(
                e -> {

                });
    }

    private void getEnglishDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                        translatedText -> textViewDescricao.setText(translatedText)).addOnFailureListener(
                        e -> {

                        })).addOnFailureListener(
                e -> {

                });
    }

    private void getPortugueseTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                        translatedText -> textViewFoto.setText(translatedText)).addOnFailureListener(
                        e -> {

                        })).addOnFailureListener(
                e -> {

                });
    }

    private void getEnglishTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                        translatedText -> textViewFoto.setText(translatedText)).addOnFailureListener(
                        e -> {

                        })).addOnFailureListener(
                e -> {

                });
    }

    private void initViews(View view) {
        textViewFoto = view.findViewById(R.id.textViewFoto);
        textViewAutor = view.findViewById(R.id.textViewAutor);
        imageViewFoto = view.findViewById(R.id.imageViewFoto);
        imageViewFavorited = view.findViewById(R.id.ic_favorited);
        textViewDescricao = view.findViewById(R.id.textViewDescricao);
        materialButtonPT = view.findViewById(R.id.materialButtonPT);
        materialButtonENG = view.findViewById(R.id.materialButtonENG);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
    }
}