package com.example.spacenbeyond.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacenbeyond.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicoAjuda.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicoAjuda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicoAjuda extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int imageView_ajuda;
    private String title;
    private String subtitle;

    private OnFragmentInteractionListener mListener;

    public TopicoAjuda() {
        // Required empty public constructor
    }

    public static TopicoAjuda newInstance(int imageView_ajuda, String title, String subtitle) {
        TopicoAjuda fragment = new TopicoAjuda();
        Bundle args = new Bundle();
        args.putInt(String.valueOf(imageView_ajuda), 1203);
        args.putString(title, "Dúvida1");
        args.putString(subtitle, "Lalalalala");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageView_ajuda = getArguments().getInt(String.valueOf(1203));
            title = getArguments().getString(title);
            subtitle = getArguments().getString(subtitle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topico_ajuda, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
