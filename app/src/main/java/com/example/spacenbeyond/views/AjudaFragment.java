package com.example.spacenbeyond.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacenbeyond.Adapter.AjudaRecyclerViewAdapter;
import com.example.spacenbeyond.Interfaces.AjudaListener;
import com.example.spacenbeyond.Model.DadosAjuda;
import com.example.spacenbeyond.R;
import java.util.ArrayList;
import java.util.List;

public class AjudaFragment extends Fragment implements AjudaListener {

    private ImageButton bttn_voltar;
    private ImageView imageView_ajuda;
    private TextView textViewTitle;
    private TextView textViewSubtitle;
    private RecyclerView ajudaRecyclerView;
    private AjudaRecyclerViewAdapter adapter;

    public static final String AJUDA_CHAVE = "ajuda";

    public AjudaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);

        initViews(view);

        bttn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                closefragment();
            }
        });

        imageView_ajuda = view.findViewById(R.id.imageView_ajuda);
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewSubtitle = view.findViewById(R.id.text_view_subtitle);

        adapter = new AjudaRecyclerViewAdapter(getListaAjuda(), this);
        ajudaRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ajudaRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private List<DadosAjuda> getListaAjuda() {
        List<DadosAjuda> ajuda = new ArrayList<>();

        ajuda.add(new DadosAjuda(19203, "Título1", "lalalalalal"));
        ajuda.add(new DadosAjuda(838, "Título2", "lalalala"));
        ajuda.add(new DadosAjuda(912, "Título3", "lalalala"));
        ajuda.add(new DadosAjuda(29303, "Título4", "lalalala"));

        return ajuda;
    }

    @Override
    public void enviaAjuda(DadosAjuda dadosAjuda) {
        Fragment fragment = new TopicoAjuda();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AJUDA_CHAVE, dadosAjuda);
        fragment.setArguments(bundle);
    }

    private void initViews(View view) {
        ajudaRecyclerView = view.findViewById(R.id.recycler_view_ajuda);
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down).remove(this).commit();
    }

    public static AjudaFragment newInstance() {
        return new AjudaFragment();
    }

}