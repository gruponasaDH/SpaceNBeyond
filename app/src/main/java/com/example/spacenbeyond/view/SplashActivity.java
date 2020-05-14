package com.example.spacenbeyond.view;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.spacenbeyond.R;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            AccessToken accessToken = AccessToken.getCurrentAccessToken();

            if (user != null) {
                Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
//            else if (account != null) {
//                account.
//            }
//            else if(accessToken != null) {
//                accessToken.get
//            }
            else {
                Intent homeIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, splashTimeOut);
    }
}