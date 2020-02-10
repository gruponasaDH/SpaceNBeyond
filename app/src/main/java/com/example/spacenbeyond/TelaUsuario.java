package com.example.spacenbeyond;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

<<<<<<< HEAD:app/src/main/java/com/example/spacenbeyond/MainActivity.java
public class MainActivity extends AppCompatActivity {
    private TextInputLayout inputNome;
    private TextInputLayout inputEmail;
    private TextInputLayout inputSenha;
    private Button cirarButton;
    private Button loginButton;
=======
public class TelaUsuario extends AppCompatActivity {
>>>>>>> b81d151031067f412ff7a223183a0a9284f908a5:app/src/main/java/com/example/spacenbeyond/TelaUsuario.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:app/src/main/java/com/example/spacenbeyond/MainActivity.java
        setContentView(R.layout.activity_main);

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
=======
        setContentView(R.layout.activity_tela_usuario);
>>>>>>> b81d151031067f412ff7a223183a0a9284f908a5:app/src/main/java/com/example/spacenbeyond/TelaUsuario.java
    }

    private void validarCampos(String nome, String email, String senha) {
        if (nome.isEmpty() && email.isEmpty() && senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Dados estão ok! Verifique que o seu email é válido.", Toast.LENGTH_LONG).show();
        }
    }

//    public boolean isValid(String email) {
//        String email1 = inputEmail.getText().toString();
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
//
//        Pattern pat = Pattern.compile(emailRegex);
//        if (email1 == null) {
//            return false;
//        }
//        return pat.matcher(email1).matches();
//
//        if (isValid(email)) {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//
//            Bundle bundle = new Bundle();
//
//            bundle.putString("EMAIL", email1);
//
//            intent.putExtras(bundle);
//
//            startActivity(intent);
//
//        }
//        else {
//            System.out.println("Email inválido!");
//        }
//    }
}