package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView txtcadastreSe;
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                if (email != "" && senha != ""){
                    Toast.makeText(LoginActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Assim como você estamos muito animados. Por favor, aguarde mais um pouco e teremos um app maravilhoso para você.", Toast.LENGTH_LONG).show();
                }
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

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void initViews() {

        btnLogin = findViewById(R.id.btnLogin);
        txtcadastreSe = findViewById(R.id.textCadastreSe);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }
}
