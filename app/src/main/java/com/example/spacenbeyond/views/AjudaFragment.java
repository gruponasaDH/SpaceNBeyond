package com.example.spacenbeyond.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private TextView Title;
    private TextView Subtitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);

        Title = view.findViewById(R.id.title);
        Subtitle = view.findViewById(R.id.subtitle);

        if (getArguments() != null) {

            Bundle bundle = getArguments();

            Ajuda ajuda = bundle.getParcelable(AJUDA_CHAVE);

            Title.setText(ajuda.getTitle());
            Subtitle.setText(ajuda.getSubtitle());
        }


        recyclerViewAjuda = view.findViewById(R.id.recycler_view_ajuda);

        adapter = new AjudaRecyclerViewAdapter(getListaAjuda(), this);

        recyclerViewAjuda.setAdapter(adapter);

        recyclerViewAjuda.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        return view;
    }

    private List<Ajuda> getListaAjuda() {
        List<Ajuda> ajuda = new ArrayList<>();

        ajuda.add(new Ajuda("Como ver foto?", "lalalalalal"));
        ajuda.add(new Ajuda("Como fazer x?", "lalalalalal"));
        ajuda.add(new Ajuda("Como acessar y?", "lalalalalal"));
        ajuda.add(new Ajuda("Como executar z?", "lalalalalal"));

        return ajuda;
    }

    @Override
    public void enviaAjuda(Ajuda ajuda) {
        Fragment fragment = new AjudaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AJUDA_CHAVE, ajuda);
        fragment.setArguments(bundle);

        replaceFragment(fragment);

    }

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment)
                .commit();
    }

}