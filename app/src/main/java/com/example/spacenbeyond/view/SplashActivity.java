package com.example.spacenbeyond.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spacenbeyond.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        ImageView marca = findViewById(R.id.marca);
        TextView descricao = findViewById(R.id.descricao);

        marca.setAnimation(topAnimation);
        descricao.setAnimation(bottomAnimation);

        int splashTimeOut = 5000;
        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(homeIntent);
            finish();
        }, splashTimeOut);
    }
}