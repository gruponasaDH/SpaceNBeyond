package com.example.spacenbeyond.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacenbeyond.Adapter.AjudaRecyclerViewAdapter;
import com.example.spacenbeyond.Interfaces.AjudaListener;
import com.example.spacenbeyond.Model.Ajuda;
import com.example.spacenbeyond.R;

import java.util.ArrayList;
import java.util.List;

public class AjudaFragment extends Fragment implements AjudaListener {

    private RecyclerView recyclerViewAjuda;
    private AjudaRecyclerViewAdapter adapter;

    public static final String AJUDA_CHAVE = "ajuda";

    public AjudaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);

        recyclerViewAjuda = view.findViewById(R.id.recycler_view_ajuda);

        adapter = new AjudaRecyclerViewAdapter(getListaAjuda(), this);
        recyclerViewAjuda.setAdapter(adapter);

        recyclerViewAjuda.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        return view;

    }

    private List<Ajuda> getListaAjuda() {
        List<Ajuda> ajuda = new ArrayList<>();

        ajuda.add(new Ajuda("Título1", "lalalalalal"));
        ajuda.add(new Ajuda("Título2", "lalalala"));
        ajuda.add(new Ajuda("Título3", "lalalala"));
        ajuda.add(new Ajuda("Título4", "lalalala"));

        return ajuda;
    }

    @Override
    public void enviaAjuda(Ajuda ajuda) {

    }
}