package com.example.spacenbeyond.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.spacenbeyond.util.AppUtil.verificaConexaoComInternet;

public class HomeFragment extends Fragment {

    private PhotoResponse photoResponse;

    private TextView textViewMes;
    private TextView textViewAno;

    private ImageView imageViewCalendar;
    private ImageView imageViewUser;

    private ProgressBar progressBar;
    private PhotoViewModel photoViewModel;
    private TextView textViewFoto;
    private TextView textViewAutor;
    private ImageView imageViewFoto;
    private ImageView imageFavorite;
    private ImageView imageShare;
    private TextView textViewDescricao;
    private MaterialButton materialButtonPT;
    private MaterialButton materialButtonENG;
    private String dateRequest;
    public static final String API_KEY = "xePvTtG6Ef3It4NuYb1mdPnKkRTFXnAAmgk0taFl";

    private Context context;

    public HomeFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context){
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(myCalendar);

            String mes = "";
            if (monthOfYear == 1) {
                mes = "01";
            }
            else if (monthOfYear == 2) {
                mes = "02";
            }
            else if (monthOfYear == 3) {
                mes = "03";
            }
            else if (monthOfYear == 4) {
                mes = "04";
            }
            else if (monthOfYear == 5) {
                mes = "05";
            }
            else if (monthOfYear == 6) {
                mes = "06";
            }
            else if (monthOfYear == 7) {
                mes = "07";
            }
            else if (monthOfYear == 8) {
                mes = "08";
            }
            else if (monthOfYear == 9) {
                mes = "09";
            }
            dateRequest = year + "-" + mes + "-" + dayOfMonth;
            getPhotoOfDay();
        };

        imageViewCalendar.setOnClickListener(v -> new DatePickerDialog(context, date, myCalendar .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        imageViewUser.setOnClickListener(v -> replaceFragments(new EditContaFragment()));

        materialButtonPT.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getPortugueseTitle();
            getPortugueseDescription();
            progressBar.setVisibility(View.GONE);
        });
        materialButtonENG.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getEnglishTitle();
            getEnglishDescription();
            progressBar.setVisibility(View.GONE);
        });

        imageFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (verificaConexaoComInternet(getContext())) {
                    photoViewModel.salvarFavorito(photoResponse);
                }
                else {
                    PhotoEntity photoEntity = new PhotoEntity(photoResponse.getCopyright(), photoResponse.getDate(), photoResponse.getExplanation(), photoResponse.getTitle(), photoResponse.getUrl());
                    photoViewModel.insereDadosBd(photoEntity);
                }
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        photoViewModel.getLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                progressBar.setVisibility(View.GONE);
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(currentTime);
        photoViewModel.getPhotoOfDay(todayString, API_KEY);
        photoViewModel.liveData.observe(this, (PhotoResponse result) -> {

            photoResponse = result;

            textViewFoto.setText(result.getTitle());
            if (result.getCopyright() != null) {
                textViewAutor.setVisibility(View.VISIBLE);
                textViewAutor.setText(result.getCopyright());
            }
            else {
                textViewAutor.setVisibility(View.GONE);
            }
            Picasso.get().load(result.getUrl()).into(imageViewFoto);
            textViewDescricao.setText(result.getExplanation());
        });

        return view;
    }

    private void updateLabel(Calendar myCalendar) {
        Date currentTime = myCalendar.getTime();

        String d = currentTime.toString();
        String[] currentDate = d.split(" ");

        switch (currentDate[1]) {
            case "Jan":
                textViewMes.setText(getString(R.string.jan));
                break;
            case "Feb":
                textViewMes.setText(getString(R.string.fev));
                break;
            case "Mar":
                textViewMes.setText(getString(R.string.mar));
                break;
            case "Apr":
                textViewMes.setText(getString(R.string.abr));
                break;
            case "May":
                textViewMes.setText(getString(R.string.mai));
                break;
            case "Jun":
                textViewMes.setText(getString(R.string.jun));
                break;
            case "Jul":
                textViewMes.setText(getString(R.string.jul));
                break;
            case "Aug":
                textViewMes.setText(getString(R.string.ago));
                break;
            case "Sep":
                textViewMes.setText(getString(R.string.set));
                break;
            case "Oct":
                textViewMes.setText(getString(R.string.out));
                break;
            case "Nov":
                textViewMes.setText(getString(R.string.nov));
                break;
            case "Dec":
                textViewMes.setText(getString(R.string.dez));
                break;
        }

        textViewAno.setText(currentDate[5]);
    }

    private void changeDate() {

        Date currentTime = Calendar.getInstance().getTime();

        String d = currentTime.toString();
        String[] currentDate = d.split(" ");

        switch (currentDate[1]) {
            case "Jan":
                textViewMes.setText(getString(R.string.jan));
                break;
            case "Feb":
                textViewMes.setText(getString(R.string.fev));
                break;
            case "Mar":
                textViewMes.setText(getString(R.string.mar));
                break;
            case "Apr":
                textViewMes.setText(getString(R.string.abr));
                break;
            case "May":
                textViewMes.setText(getString(R.string.mai));
                break;
            case "Jun":
                textViewMes.setText(getString(R.string.jun));
                break;
            case "Jul":
                textViewMes.setText(getString(R.string.jul));
                break;
            case "Aug":
                textViewMes.setText(getString(R.string.ago));
                break;
            case "Sep":
                textViewMes.setText(getString(R.string.set));
                break;
            case "Out":
                textViewMes.setText(getString(R.string.out));
                break;
            case "Nov":
                textViewMes.setText(getString(R.string.nov));
                break;
            case "Dec":
                textViewMes.setText(getString(R.string.dez));
                break;
        }

        textViewAno.setText(currentDate[5]);
    }

    private void initViews(View view) {

        imageViewCalendar = view.findViewById(R.id.imageViewCalendar);
        imageViewUser = view.findViewById(R.id.imageViewUser);

        textViewMes = view.findViewById(R.id.textViewMes);
        textViewAno = view.findViewById(R.id.textViewAno);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoResponse = new PhotoResponse();

        progressBar = view.findViewById(R.id.progress_bar);

        textViewFoto = view.findViewById(R.id.textViewFoto);
        textViewAutor = view.findViewById(R.id.textViewAutor);
        imageViewFoto = view.findViewById(R.id.imageViewFoto);
        imageFavorite = view.findViewById(R.id.ic_favorite);
        imageShare = view.findViewById(R.id.ic_share);
        textViewDescricao = view.findViewById(R.id.textViewDescricao);
        materialButtonPT = view.findViewById(R.id.materialButtonPT);
        materialButtonENG = view.findViewById(R.id.materialButtonENG);
        changeDate();
    }

    private void getPhotoOfDay () {
        photoViewModel.getPhotoOfDay(dateRequest, API_KEY);
        photoViewModel.photo.observe(this, result -> {

            photoResponse = result;

            textViewFoto.setText(result.getTitle());
            if (result.getCopyright() != null) {
                textViewAutor.setVisibility(View.VISIBLE);
                textViewAutor.setText(result.getCopyright());
            }
            else {
                textViewAutor.setVisibility(View.GONE);
            }
            Picasso.get().load(result.getUrl()).into(imageViewFoto);
            textViewDescricao.setText(result.getExplanation());
        });
    }

    private void getPortugueseDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> {

                    englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                            translatedText -> textViewDescricao.setText(translatedText)).addOnFailureListener(
                            e -> {

                            });
                }).addOnFailureListener(
                e -> {

                });
    }

    private void getEnglishDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> {

                    englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                            translatedText -> textViewDescricao.setText(translatedText)).addOnFailureListener(
                            e -> {

                            });
                }).addOnFailureListener(
                e -> {

                });
    }

    private void getPortugueseTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> {

                    englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                            translatedText -> textViewFoto.setText(translatedText)).addOnFailureListener(
                            e -> {

                            });
                }).addOnFailureListener(
                e -> {

                });
    }

    private void getEnglishTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                v -> {

                    englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                            translatedText -> textViewFoto.setText(translatedText)).addOnFailureListener(
                            e -> {

                            });
                }).addOnFailureListener(
                e -> {

                });
    }

    private void replaceFragments(Fragment fragment){
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, 0, 0, R.animator.slide_down).replace(R.id.fragment_container, fragment).commit();
    }
}