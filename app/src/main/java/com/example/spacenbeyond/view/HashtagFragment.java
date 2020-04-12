package com.example.spacenbeyond.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spacenbeyond.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HashtagFragment extends Fragment {

    private ImageView imageViewBack;
    private FloatingActionButton floatingActionButtonCamera;

    public HashtagFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hashtag, container, false);

        initViews(view);

        imageViewBack.setOnClickListener(v -> closefragment());

        floatingActionButtonCamera.setOnClickListener(view1 -> {
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cInt, 1);
        });

        return view;
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.animator.slide_up, 0, 0, R.animator.slide_down).remove(this).commit();
    }

    private void initViews(View view) {

        floatingActionButtonCamera = view.findViewById(R.id.floatingActionButtonCamera);
        imageViewBack = view.findViewById(R.id.ic_back);
    }
}