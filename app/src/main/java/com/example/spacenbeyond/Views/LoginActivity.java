package com.example.spacenbeyond.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacenbeyond.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextView txtcadastreSe;
    private Button btnLogin;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifyFields();
            }
        });

        txtcadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, com.example.spacenbeyond.Views.CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
    public void verifyFields() {
        String email = txtEmail.getEditText().getText().toString();
        String senha = txtSenha.getEditText().getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "Por favor, forneça os dados necessários para login.", Toast.LENGTH_LONG).show();
        }
    }
    public void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        txtcadastreSe = findViewById(R.id.textCadastreSe);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }
}