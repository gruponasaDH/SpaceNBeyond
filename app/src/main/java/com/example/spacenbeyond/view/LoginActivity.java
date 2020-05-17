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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.util.AppUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;
    private Button btnLogin;
    private Button gButton;
    private Button fButton;
    private TextInputLayout txtEmail;
    private TextInputLayout txtSenha;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    public static final String GOOGLE_ACCOUNT = "google_account";
    public static int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        textoClicavel();

        btnLogin.setOnClickListener(v -> verifyFields());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        gButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        fButton.setOnClickListener(view -> loginFacebook());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("GOOGLE", "Google sign in failed", e);

            }
        }

    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GOOGLE_ACCOUNT, googleSignInAccount);
        startActivity(intent);
        firebaseAuthWithGoogle(googleSignInAccount);
        finish();
    }

    private void loginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancelado pelo usuário", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Erro ao logar com Facebook", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("GOOGLE", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("GOOGLE", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            irParaHome(user.getUid());
                        } else {
                            Log.w("GOOGLE", "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.googleButton), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FACE", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("FACE", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            irParaHome(user.getUid());
                        } else {
                            Log.w("FACE", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void irParaHome(String uiid){
        AppUtil.salvarIdUsuario(this, uiid);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void verifyFields() {
        String email = txtEmail.getEditText().getText().toString();
        String senha = txtSenha.getEditText().getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            AppUtil.salvarIdUsuario(getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Snackbar.make(btnLogin, "Erro ao tentar logar \n" + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });

        }
        else {
            Toast.makeText(LoginActivity.this, "Por favor, forneça os dados necessários para login.", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {

        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.textViewCadastreSe);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        gButton = findViewById(R.id.googleButton);
        fButton = findViewById(R.id.facebookButton);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

    }

    private void textoClicavel() {
        String text = "Ainda não tem conta? Cadastre-se.";
        SpannableString str = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.YELLOW);
                ds.setUnderlineText(false);
            }
        };

        str.setSpan(clickableSpan, 21, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 21, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtCadastro.setText(str);
        txtCadastro.setMovementMethod(LinkMovementMethod.getInstance());
    }
}