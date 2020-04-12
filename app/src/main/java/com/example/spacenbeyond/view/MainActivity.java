package com.example.spacenbeyond.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.spacenbeyond.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_hashtag:
                selectedFragment = new HashtagFragment();
                break;
            case R.id.nav_favorites:
                selectedFragment = new FavoritosFragment();
                break;
            case R.id.nav_help:
                selectedFragment = new AjudaFragment();
                break;
        }

        replaceFragments(selectedFragment);
        return true;
    };


    private void replaceFragments(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, 0, 0, 0).replace(R.id.fragment_container, fragment).commit();
    }
}