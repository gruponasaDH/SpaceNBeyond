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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacenbeyond.R;
import com.example.spacenbeyond.util.AppUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import static java.security.AccessController.getContext;

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

        btncriarConta.setOnClickListener(v -> {
            String nome = txtNome.getEditText().getText().toString();
            String email = txtEmail.getEditText().getText().toString();
            String senha = txtSenha.getEditText().getText().toString();

            if(verifyFields(nome, email, senha)) {
                registerUser(nome, email, senha);
            }
        });
    }

    private void registerUser(String nome, String email, String senha) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       // Salvar id do usuário para pegar os dados depois
                       AppUtil.salvarIdUsuario(getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                       Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                       startActivity(intent);

                   } else {
                       // Se deu algum erro mostramos para o usuário a mensagem
                       Snackbar.make(btncriarConta, "Erro ao cadastrar usuário -> " + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                   }
                });
    }

    private boolean verifyFields(String nome, String email, String senha) {

        if (nome.isEmpty()) {
            txtNome.setError("Nome não pode ser vazio");
            txtNome.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            txtEmail.setError("Email não pode ser vazio");
            txtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Email inválido");
            txtEmail.requestFocus();
            return false;
        }

        if (senha.isEmpty()) {
            txtSenha.setError("Senha não pode ser vazia");
            txtSenha.requestFocus();
            return false;
        }

        if (senha.length() < 6) {
            txtSenha.setError("Senha deve ser maior que 6 caracters");
            txtSenha.requestFocus();
            return false;
        }

        return true;
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