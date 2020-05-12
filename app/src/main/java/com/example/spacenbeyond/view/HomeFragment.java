package com.example.spacenbeyond.view;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.spacenbeyond.util.AppUtil.verificaConexaoComInternet;
import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    private PhotoResponse photoResponse;

    private TextView textViewDia;
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
                    imageFavorited();
                }
                else {
                    PhotoEntity photoEntity = new PhotoEntity(photoResponse.getCopyright(), photoResponse.getDate(), photoResponse.getExplanation(), photoResponse.getTitle(), photoResponse.getUrl());
                    photoViewModel.insereDadosBd(photoEntity);
                    imageFavorited();
                }
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                try {
                    BitmapDrawable drawable = (BitmapDrawable) imageViewFoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String savedFile = SaveImage(bitmap);

                    File media = new File(savedFile);
                    Uri imageUri =  FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", media);

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    share.putExtra(Intent.EXTRA_TEXT, textViewFoto.getText() + "\nCompartilhado de SpaceNBeyond");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Label", textViewFoto.getText().toString());
                    clipboard.setPrimaryClip(clip);

                    startActivity(Intent.createChooser(share, "Share Image"));
                }
                catch (Throwable e) {
                    Toast.makeText(getContext(), "Não foi possível executar a ação.", Toast.LENGTH_LONG).show();
                }
            }
        });

        photoViewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
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
        photoViewModel.liveData.observe(getViewLifecycleOwner(), (PhotoResponse result) -> {

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

    public void imageFavorited(){
        imageFavorite.setImageResource(R.drawable.ic_favorited);
        Toast.makeText(getApplicationContext(), "Item salvo com sucesso", Toast.LENGTH_LONG).show();
    }

    private String SaveImage(Bitmap finalBitmap) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("SAVEIMAGE", "Error creating media file, check storage permissions: ");// e.getMessage());
            return "Error creating media file, check storage permissions: ";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            return pictureFile.getAbsolutePath();
        }
        catch (FileNotFoundException e) {
            Log.d("SAVEIMAGE", "File not found: " + e.getMessage());
            return e.getMessage();
        }
        catch (IOException e) {
            Log.d("SAVEIMAGE", "Error accessing file: " + e.getMessage());
            return e.getMessage();
        }
    }

    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = textViewFoto.getText() + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void updateLabel(Calendar myCalendar) {
        Date currentTime = myCalendar.getTime();

        String d = currentTime.toString();
        String[] currentDate = d.split(" ");

        switch (currentDate[1]) {
            case "Jan":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jan));
                break;
            case "Feb":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.fev));
                break;
            case "Mar":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.mar));
                break;
            case "Apr":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.abr));
                break;
            case "May":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.mai));
                break;
            case "Jun":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jun));
                break;
            case "Jul":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jul));
                break;
            case "Aug":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.ago));
                break;
            case "Sep":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.set));
                break;
            case "Oct":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.out));
                break;
            case "Nov":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.nov));
                break;
            case "Dec":
                textViewDia.setText(currentDate[2]);
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
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jan));
                break;
            case "Feb":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.fev));
                break;
            case "Mar":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.mar));
                break;
            case "Apr":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.abr));
                break;
            case "May":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.mai));
                break;
            case "Jun":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jun));
                break;
            case "Jul":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.jul));
                break;
            case "Aug":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.ago));
                break;
            case "Sep":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.set));
                break;
            case "Out":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.out));
                break;
            case "Nov":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.nov));
                break;
            case "Dec":
                textViewDia.setText(currentDate[2]);
                textViewMes.setText(getString(R.string.dez));
                break;
        }

        textViewAno.setText(currentDate[5]);
    }

    private void initViews(View view) {

        imageViewCalendar = view.findViewById(R.id.imageViewCalendar);
        imageViewUser = view.findViewById(R.id.imageViewUser);

        textViewDia = view.findViewById(R.id.textViewDia);
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
        photoViewModel.photo.observe(getViewLifecycleOwner(), result -> {

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