package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatePickerTimeline datePickerTimeline;
    private TextView textViewMes;
    private TextView textViewAno;

    private ImageView imageViewCalendar;
    private ImageView imageViewUser;
    private ImageView imageViewCamera;

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

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
            }
        };

        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragments(R.id.container, new HashtagFragment());
            }
        });

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private void initViews() {

        imageViewCalendar = findViewById(R.id.imageViewCalendar);
        imageViewUser = findViewById(R.id.imageViewUser);
        imageViewCamera = findViewById(R.id.imageViewHash);

        textViewMes = findViewById(R.id.textViewMes);
        textViewAno = findViewById(R.id.textViewAno);

        datePickerTimeline = findViewById(R.id.datePickerTimeline);

        datePickerTimeline.setInitialDate(1995, 5, 18);

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                setTextDate(month, year);
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });
        datePickerTimeline.setDateTextColor(Color.WHITE);
        datePickerTimeline.setDayTextColor(Color.WHITE);
        datePickerTimeline.setMonthTextColor(Color.WHITE);

        changeDate();
    }

    private void replaceFragments(int container, Fragment fragment){
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction()
                .setCustomAnimations( R.anim.slide_up, 0, 0, R.anim.slide_down);
        transaction.replace(container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
    }
}