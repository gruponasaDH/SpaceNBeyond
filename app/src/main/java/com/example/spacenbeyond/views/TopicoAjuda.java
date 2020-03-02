package com.example.spacenbeyond.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.spacenbeyond.R;

public class TopicoAjuda extends Fragment {

    private ImageButton bttn_voltar;
    private String title;
    private String subtitle;

    public TopicoAjuda() {
        // Required empty public constructor
    }

    public static TopicoAjuda newInstance(String title, String subtitle) {
        TopicoAjuda fragment = new TopicoAjuda();
        Bundle args = new Bundle();
        args.putString(title, "Dúvida1");
        args.putString(subtitle, "Lalalalala");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(title);
            subtitle = getArguments().getString(subtitle);
        }

        bttn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                closefragment();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topico_ajuda, container, false);

    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down).remove(this).commit();
    }
}