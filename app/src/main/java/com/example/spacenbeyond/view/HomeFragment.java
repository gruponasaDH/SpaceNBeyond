package com.example.spacenbeyond.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.viewmodel.PhotoViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.squareup.picasso.Picasso;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private DatePickerTimeline datePickerTimeline;
    private TextView textViewMes;
    private TextView textViewAno;

    private ImageView imageViewCalendar;
    private ImageView imageViewUser;

    private FragmentManager manager;

    private ProgressBar progressBar;
    private PhotoViewModel photoViewModel;
    private TextView textViewFoto;
    private TextView textViewAutor;
    private ImageView imageViewFoto;
    private TextView textViewDescricao;
    private MaterialButton materialButtonPT;
    private MaterialButton materialButtonENG;
    private ScrollView container;
    public String dateRequest;
    public static final String API_KEY = "xePvTtG6Ef3It4NuYb1mdPnKkRTFXnAAmgk0taFl";

    private Context context;

    public HomeFragment(){ }

    @Override
    public void onAttach(Context context){
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
                dateRequest = year + "/" + mes + "/" + dayOfMonth;
                getPhotoOfDay();
            }
        };

        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(R.id.frameContainer, new EditContaFragment());
            }
        });

        materialButtonPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getPortugueseTitle();
                getPortugueseDescription();
                progressBar.setVisibility(View.GONE);
            }
        });
        materialButtonENG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getEnglishTitle();
                getEnglishDescription();
                progressBar.setVisibility(View.GONE);
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
        photoViewModel.liveData.observe(this, result -> {
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
        Date[] dates = { myCalendar.getTime() };
        Date currentTime = myCalendar.getTime();

        String d = currentTime.toString();
        String[] currentDate = d.split(" ");

        if (currentDate[1].equals("Jan")) {
            textViewMes.setText("Jan");
        }
        else if (currentDate[1].equals("Feb")) {
            textViewMes.setText("Fev");
        }
        else if (currentDate[1].equals("Mar")) {
            textViewMes.setText("Mar");
        }
        else if (currentDate[1].equals("Apr")) {
            textViewMes.setText("Abr");
        }
        else if (currentDate[1].equals("May")) {
            textViewMes.setText("Mai");
        }
        else if (currentDate[1].equals("Jun")) {
            textViewMes.setText("Jul");
        }
        else if (currentDate[1].equals("Jul")) {
            textViewMes.setText("Jul");
        }
        else if (currentDate[1].equals("Aug")) {
            textViewMes.setText("Ago");
        }
        else if (currentDate[1].equals("Sep")) {
            textViewMes.setText("Set");
        }
        else if (currentDate[1].equals("Oct")) {
            textViewMes.setText("Out");
        }
        else if (currentDate[1].equals("Nov")) {
            textViewMes.setText("Nov");
        }
        else if (currentDate[1].equals("Dec")) {
            textViewMes.setText("Dez");
        }

        textViewAno.setText(currentDate[5].toString());
        datePickerTimeline.deactivateDates(dates);
        datePickerTimeline.setActiveDate(myCalendar);
    }

    private void changeDate() {

        Date[] dates = { Calendar.getInstance().getTime() };
        Date currentTime = Calendar.getInstance().getTime();

        String d = currentTime.toString();
        String[] currentDate = d.split(" ");

        if (currentDate[1].equals("Jan")) {
            textViewMes.setText("Jan");
        }
        else if (currentDate[1].equals("Feb")) {
            textViewMes.setText("Fev");
        }
        else if (currentDate[1].equals("Mar")) {
            textViewMes.setText("Mar");
        }
        else if (currentDate[1].equals("Apr")) {
            textViewMes.setText("Abr");
        }
        else if (currentDate[1].equals("May")) {
            textViewMes.setText("Mai");
        }
        else if (currentDate[1].equals("Jun")) {
            textViewMes.setText("Jul");
        }
        else if (currentDate[1].equals("Jul")) {
            textViewMes.setText("Jul");
        }
        else if (currentDate[1].equals("Aug")) {
            textViewMes.setText("Ago");
        }
        else if (currentDate[1].equals("Sep")) {
            textViewMes.setText("Set");
        }
        else if (currentDate[1].equals("Out")) {
            textViewMes.setText("Out");
        }
        else if (currentDate[1].equals("Nov")) {
            textViewMes.setText("Nov");
        }
        else if (currentDate[1].equals("Dec")) {
            textViewMes.setText("Dez");
        }

        final Calendar myCalendar = Calendar.getInstance();

        textViewAno.setText(currentDate[5].toString());
        datePickerTimeline.deactivateDates(dates);
        datePickerTimeline.setActiveDate(myCalendar);
        datePickerTimeline.setNextFocusUpId(800);
    }

    public void setTextDate(int month, int year) {
        month++;
        if (month == 1) {
            textViewMes.setText("Jan");
        }
        else if (month == 2) {
            textViewMes.setText("Fev");
        }
        else if (month == 3) {
            textViewMes.setText("Mar");
        }
        else if (month == 4) {
            textViewMes.setText("Abr");
        }
        else if (month == 5) {
            textViewMes.setText("Mai");
        }
        else if (month == 6) {
            textViewMes.setText("Jun");
        }
        else if (month == 7) {
            textViewMes.setText("Jul");
        }
        else if (month == 8) {
            textViewMes.setText("Ago");
        }
        else if (month == 9) {
            textViewMes.setText("Set");
        }
        else if (month == 10) {
            textViewMes.setText("Out");
        }
        else if (month == 11) {
            textViewMes.setText("Nov");
        }
        else if (month == 12) {
            textViewMes.setText("Dez");
        }

        String ano = String.valueOf(year);
        textViewAno.setText(ano);
    }

    private void initViews(View view) {

        imageViewCalendar = view.findViewById(R.id.imageViewCalendar);
        imageViewUser = view.findViewById(R.id.imageViewUser);

        textViewMes = view.findViewById(R.id.textViewMes);
        textViewAno = view.findViewById(R.id.textViewAno);

        datePickerTimeline = view.findViewById(R.id.datePickerTimeline);

        datePickerTimeline.setDateTextColor(Color.WHITE);
        datePickerTimeline.setDayTextColor(Color.WHITE);
        datePickerTimeline.setMonthTextColor(Color.WHITE);
        datePickerTimeline.setInitialDate(1995, 5, 18);

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                setTextDate(month, year);
                String mes = "";
                month++;
                if (month == 1) {
                    mes = "01";
                }
                else if (month == 2) {
                    mes = "02";
                }
                else if (month == 3) {
                    mes = "03";
                }
                else if (month == 4) {
                    mes = "04";
                }
                else if (month == 5) {
                    mes = "05";
                }
                else if (month == 6) {
                    mes = "06";
                }
                else if (month == 7) {
                    mes = "07";
                }
                else if (month == 8) {
                    mes = "08";
                }
                else if (month == 9) {
                    mes = "09";
                }
                dateRequest = year + "-" + mes + "-" + day;
                getPhotoOfDay();
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        progressBar = view.findViewById(R.id.progress_bar);

        textViewFoto = view.findViewById(R.id.textViewFoto);
        textViewAutor = view.findViewById(R.id.textViewAutor);
        imageViewFoto = view.findViewById(R.id.imageViewFoto);
        textViewDescricao = view.findViewById(R.id.textViewDescricao);
        materialButtonPT = view.findViewById(R.id.materialButtonPT);
        materialButtonENG = view.findViewById(R.id.materialButtonENG);
        changeDate();
    }

    private void getPhotoOfDay () {
        photoViewModel.getPhotoOfDay(dateRequest, API_KEY);
        photoViewModel.photo.observe(this, result -> {

//            if (!dateRequest.equals(result.getDate())) {
//                Snackbar.make(container, R.string.nao_ha_imagem, Snackbar.LENGTH_LONG).show();
//            }

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

    public void getPortugueseDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        textViewDescricao.setText(translatedText);
                                    }
                                }).addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });
                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });
    }

    public void getEnglishDescription() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        englishPortugueseTranslator.translate(textViewDescricao.getText().toString()).addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        textViewDescricao.setText(translatedText);
                                    }
                                }).addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });
                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });
    }

    public void getPortugueseTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.EN).setTargetLanguage(FirebaseTranslateLanguage.PT).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        textViewFoto.setText(translatedText);
                                    }
                                }).addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });
                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });
    }

    public void getEnglishTitle() {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder().setSourceLanguage(FirebaseTranslateLanguage.PT).setTargetLanguage(FirebaseTranslateLanguage.EN).build();
        final FirebaseTranslator englishPortugueseTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        englishPortugueseTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Model downloaded successfully. Okay to start translating.
                        // (Set a flag, unhide the translation UI, etc.)
                        englishPortugueseTranslator.translate(textViewFoto.getText().toString()).addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        textViewFoto.setText(translatedText);
                                    }
                                }).addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });
                    }
                }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Model couldn’t be downloaded or other internal error.
                        // ...
                    }
                });
    }

    private void replaceFragments(int container, Fragment fragment){
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction()
                .setCustomAnimations( R.anim.slide_up, 0, 0, R.anim.slide_down);
        transaction.replace(container, fragment);
        transaction.commit();
    }
}