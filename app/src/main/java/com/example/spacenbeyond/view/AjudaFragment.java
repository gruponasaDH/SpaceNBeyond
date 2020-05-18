package com.example.spacenbeyond.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacenbeyond.R;

public class AjudaFragment extends Fragment {

    public AjudaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);

       // BottomNavigationView bottomNav = super.getView().findViewById(R.id.bottom_navigation);
        ImageView btnVoltar = view.findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            getFragmentManager().beginTransaction().remove(AjudaFragment.this).commit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}