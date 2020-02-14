package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void verifyFields() {
        String email = txtEmail.getEditText().toString();
        String senha = txtSenha.getEditText().toString();

        if (email.isEmpty() && senha.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(LoginActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
        }
    }

    public void initViews() {

        btnLogin = findViewById(R.id.btnLogin);
        txtcadastreSe = findViewById(R.id.textCadastreSe);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }
}