package com.example.spacenbeyond.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spacenbeyond.R;
import com.google.android.material.textfield.TextInputEditText;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private TextInputEditText txtEmail;
    private Button btnEnviar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initViews();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void verifyFields() {
        String email = (txtEmail.getText()).toString();
        if (!email.isEmpty()) {
            Intent intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RecuperarSenhaActivity.this, "Por favor informe um email cadastrado para recuperar a senha", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        txtEmail = findViewById(R.id.textInputLayoutEmail);
        btnEnviar = findViewById(R.id.btnEnviar);

    }
}
