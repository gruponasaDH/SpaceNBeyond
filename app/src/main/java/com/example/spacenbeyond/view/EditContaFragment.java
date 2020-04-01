package com.example.spacenbeyond.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacenbeyond.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditContaFragment extends Fragment {


    public EditContaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_conta, container, false);

        return view;
    }

}
