package com.example.spacenbeyond.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.Views.HomeActivity;
import com.example.spacenbeyond.Views.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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

        btncriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFields();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void verifyFields() {
        String nome = (txtNome.getEditText()).getText().toString();
        String email = (txtEmail.getEditText()).getText().toString();
        String senha = (txtSenha.getEditText()).getText().toString();
        if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
            Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(CadastroActivity.this, "Por favor, forneça os dados necessários para cadastro.", Toast.LENGTH_LONG).show();
        }
    }

    public void initViews() {
        btncriarConta = findViewById(R.id.material_icon_button);
        txtLogin = findViewById(R.id.textViewlogin);
        txtNome = findViewById(R.id.textInputLayoutNome);
        txtEmail = findViewById(R.id.textInputLayoutEmail);
        txtSenha = findViewById(R.id.textInputLayoutSenha);
    }
}