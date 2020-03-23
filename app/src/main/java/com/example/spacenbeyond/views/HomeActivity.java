package com.example.spacenbeyond.Views;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.Views.FavoritoFragment;
import com.example.spacenbeyond.Views.HashtagFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        replaceFragments(R.id.container, new HomeFragmento());
    }

    private void replaceFragments(int container, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment);
        transaction.commit();   
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_hashtags: {
                Fragment hashtagFragment = HashtagFragment.newInstance();
                openFragment(hashtagFragment);
                break;
            }
            case R.id.navigation_favoritos: {
                Fragment favoritoFragment = FavoritoFragment.newInstance();
                openFragment(favoritoFragment);
                break;
            }
            case R.id.navigation_ajuda: {
                Fragment ajudaFragment = AjudaFragment.newInstance();
                openFragment(ajudaFragment);
                break;
            }
        }
        return true;
    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}