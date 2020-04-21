package com.example.spacenbeyond.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacenbeyond.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private Button btnLogin;
    private Button gButton;
    private Button fButton;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;
    private GoogleSignInClient googleSignInClient;

    public static final String GOOGLE_ACCOUNT = "google_account";
    public static int RC_SIGN_IN = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        textoClicavel();

        btnLogin.setOnClickListener(v -> verifyFields());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        gButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == RC_SIGN_IN) {
                try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    onLoggedIn(account);
                } catch (ApiException e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível fazer o login", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GOOGLE_ACCOUNT, googleSignInAccount);
        startActivity(intent);
        finish();
    }

    private void verifyFields() {
        String email = txtEmail.getEditText().getText().toString();
        String senha = txtSenha.getEditText().getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "Por favor, forneça os dados necessários para login.", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {

        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.textViewCadastreSe);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        gButton = findViewById(R.id.googleButton);
        fButton = findViewById(R.id.facebookButton);
    }


    private void textoClicavel() {
        String text = "Ainda não tem conta? Cadastre-se.";
        SpannableString str = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.YELLOW);
                ds.setUnderlineText(false);
            }
        };

        str.setSpan(clickableSpan, 21, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 21, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtCadastro.setText(str);
        txtCadastro.setMovementMethod(LinkMovementMethod.getInstance());
    }
}