package com.example.spacenbeyond.Views;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hashtag, container, false);

        initViews(view);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });

        floatingActionButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, 1);
            }
        });

        return view;
    }
    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_up, 0, 0, R.anim.slide_down).remove(this).commit();
    }

    public void initViews(View view) {

        floatingActionButtonCamera = view.findViewById(R.id.floatingActionButtonCamera);
        imageViewBack = view.findViewById(R.id.ic_back);
    }
}