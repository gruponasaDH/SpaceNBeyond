package com.example.spacenbeyond.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spacenbeyond.R;
import com.google.firebase.auth.FirebaseAuth;

public class EditContaFragment extends Fragment {

    private ImageView imageViewSair;

    public EditContaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_conta, container, false);

        imageViewSair = view.findViewById(R.id.imageViewLogout);

        imageViewSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();   
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}