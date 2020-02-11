package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        btncriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtNome.getEditText().toString();
                String email = txtEmail.getEditText().toString();
                String senha = txtSenha.getEditText().toString();
                if (nome != "" && email != "" && senha != "") {
                    Toast.makeText(CadastroActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CadastroActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
                }
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

    public void initViews() {
        btncriarConta = findViewById(R.id.material_icon_button);
        txtLogin = findViewById(R.id.textViewlogin);
        txtNome = findViewById(R.id.textInputLayout);
        txtEmail = findViewById(R.id.textInputLayout3);
        txtSenha = findViewById(R.id.textInputLayout4);
    }
}
