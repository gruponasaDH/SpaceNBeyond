package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtEmail = (TextView)findViewById(R.id.txtEmail);
                TextView txtSenha = (TextView)findViewById(R.id.txtSenha);
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                if (txtEmail.equals("Leandro")&& txtSenha.equals("123")){
                    alert("Login realizado com sucesso");
                    Intent intent = new Intent(LoginActivity.this, TelaUsuario.class);
                    startActivity(intent);
                }
                else {
                    alert("Login ou senha incorretos");
                }
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
