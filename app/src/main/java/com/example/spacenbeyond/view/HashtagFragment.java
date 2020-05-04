package com.example.spacenbeyond.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.viewmodel.PhotoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.content.Context.MODE_PRIVATE;
import static com.example.spacenbeyond.view.HomeFragment.API_KEY;

public class HashtagFragment extends Fragment {

    private ImageView imageViewBack;

    private ConstraintLayout constraintLayoutTop;

    private FloatingActionButton floatingActionButtonCamera;

    private TextView textViewHashUm;
    private TextView textViewHashDois;

    private PhotoViewModel photoViewModel;

    private InputStream stream = null;
    private static final int PERMISSION_CODE = 100;

    public HashtagFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hashtag, container, false);

        initViews(view);

        imageViewBack.setOnClickListener(v -> closefragment());

        floatingActionButtonCamera.setOnClickListener(view1 -> {
            captureImage();
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(currentTime);
        photoViewModel.getPhotoOfDay(todayString, API_KEY);
        photoViewModel.liveData.observe(getViewLifecycleOwner(), (PhotoResponse result) -> {

            Random random = new Random();
            String[] arr = result.getExplanation().split(" ");
            int randomIdx1 = random.nextInt(arr.length);
            int randomIdx2 = random.nextInt(arr.length);

            textViewHashUm.setText("#" + arr[randomIdx1]);
            textViewHashDois.setText("#" + arr[randomIdx2]);
        });

        return view;
    }

    private void captureImage() {
        int permissionCamera = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                for (File file : imageFiles) {
                    try {
                        // Aqui podemos modificar o tamnho do arquivo antes de enviar

                        Drawable d = Drawable.createFromPath(file.getPath());
                        constraintLayoutTop.setBackground(d);

                        stream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void closefragment() {
        getFragmentManager().beginTransaction().remove(HashtagFragment.this).commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void initViews(View view) {
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        textViewHashUm = view.findViewById(R.id.textViewHashtagUm);
        textViewHashDois = view.findViewById(R.id.textViewHashtagDois);

        constraintLayoutTop = view.findViewById(R.id.constraintTop);
        floatingActionButtonCamera = view.findViewById(R.id.floatingActionButtonCamera);
        imageViewBack = view.findViewById(R.id.ic_back);
    }
}