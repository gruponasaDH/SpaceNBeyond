package com.example.spacenbeyond.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private Button btnLogin;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        textoClicavel();

        btnLogin.setOnClickListener(v -> verifyFields());

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