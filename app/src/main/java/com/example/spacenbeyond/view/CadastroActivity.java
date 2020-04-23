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

public class CadastroActivity extends AppCompatActivity {

    private TextView txtLogin;
    private Button btncriarConta;
    private TextInputLayout txtNome;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initViews();

        textoClicavel();

        btncriarConta.setOnClickListener(v -> verifyFields());
    }

    private void verifyFields() {
        String nome = txtNome.getEditText().getText().toString();
        String email = txtEmail.getEditText().getText().toString();
        String senha = txtSenha.getEditText().getText().toString();
        if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
            Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(CadastroActivity.this, "Por favor, forneça os dados necessários para cadastro.", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        btncriarConta = findViewById(R.id.material_icon_button);
        txtLogin = findViewById(R.id.textViewLogin);
        txtNome = findViewById(R.id.textInputLayout);
        txtEmail = findViewById(R.id.textInputLayout3);
        txtSenha = findViewById(R.id.textInputLayout4);
    }

    private void textoClicavel() {
        String text = "Já tem conta? Faça login.";
        SpannableString str = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.YELLOW);
                ds.setUnderlineText(false);
            }
        };

        str.setSpan(clickableSpan, 19, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 19, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLogin.setText(str);
        txtLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }
}