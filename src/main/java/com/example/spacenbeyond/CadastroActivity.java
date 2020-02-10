package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class CadastroActivity extends AppCompatActivity {

    private TextInputLayout inputNome;
    private TextInputLayout inputEmail;
    private TextInputLayout inputSenha;
    private Button cirarButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputNome = findViewById(R.id.textInputLayout);
        inputEmail = findViewById(R.id.textInputLayout3);
        inputSenha = findViewById(R.id.textInputLayout4);
        cirarButton = findViewById(R.id.material_icon_button);
        //loginButton = findViewById(R.id.button2);

        cirarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = inputNome.getEditText().toString();
                String email = inputEmail.getEditText().toString();
                String senha = inputSenha.getEditText().toString();

                validarCampos(nome, email, senha);
            }
        });
    }

    private void validarCampos(String nome, String email, String senha) {
        if (nome.isEmpty() && email.isEmpty() && senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Dados estão ok! Verifique que o seu email é válido.", Toast.LENGTH_LONG).show();
        }
    }
}
