package com.example.spacenbeyond.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.spacenbeyond.Interface.ComuicacaoFragmentHome;
import com.example.spacenbeyond.Model.DadosHome;
import com.example.spacenbeyond.R;

import static com.example.spacenbeyond.constantes.Constantes.DADOS_HOME;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        replaceFragments(R.id.container, new HomeFragmento());
    }

    private void replaceFragments(int container, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment);
        transaction.commit();
    }
}