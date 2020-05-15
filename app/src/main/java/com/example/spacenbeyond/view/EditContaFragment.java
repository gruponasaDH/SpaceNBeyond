package com.example.spacenbeyond.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.util.AppUtil;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import static android.content.Context.MODE_PRIVATE;

public class EditContaFragment extends Fragment {

    private ImageView imageViewVoltar;
    private ImageView imageViewSair;
    private ImageView imageViewFotoPerfil;

    private TextView textoLogout;
    private TextView textViewDelete;
    private TextView alteraFoto;

    private GoogleSignInClient googleSignInClient;

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutSenha;

    private TextInputEditText textInputEditTextNome;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextSenha;

    private MaterialButton materialButtonSalvar;

    private static boolean valid = false;
    private InputStream stream = null;
    private static final int PERMISSION_CODE = 100;

    public EditContaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_conta, container, false);

        initViews(view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        buscarDados();

        imageViewVoltar.setOnClickListener(v -> closefragment());

        imageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        alteraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        imageViewSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(googleSignInClient);
            }
        });

        materialButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = textInputLayoutNome.getEditText().getText().toString();
                String email = textInputLayoutEmail.getEditText().getText().toString();
                String senha = textInputLayoutSenha.getEditText().getText().toString();

                if (!nome.isEmpty()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        valid = true;
                                    }
                                    else {
                                        valid = false;
                                    }
                                }
                            });
                }

                if (!email.isEmpty()) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        textInputLayoutEmail.setError("Email inválido");
                        textInputLayoutEmail.requestFocus();
                        return;
                    }

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        valid = true;
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Faça login novamente para atualizar o email.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                if (!senha.isEmpty()) {

                    if (senha.length() < 6) {
                        textInputLayoutSenha.setError("Senha deve ser maior que 6 caracters");
                        textInputLayoutSenha.requestFocus();
                        return;
                    }

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(senha)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        valid = true;
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Faça login novamente para atualizar a senha.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                if (stream != null) {
                    salvarImagemFirebase(stream, "imagem-perfil");
                }

                if (valid) {
                    Toast.makeText(getContext(), "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
                }
            }
        });

        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    getActivity().finish();
                                    Toast.makeText(getActivity(), "Conta excluida com sucesso.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        textoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(googleSignInClient);
            }
        });

        return view;
    }

    private void captureImage() {
        int permissionCamera = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        int permissionStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCameraForImage(this, MODE_PRIVATE);
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    private void closefragment() {
        getFragmentManager().beginTransaction().remove(EditContaFragment.this).commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void buscarDados() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AppUtil.getIdUsuario(getContext()) + "/image/profile/imagem-perfil");

        textInputEditTextNome.setText(user.getDisplayName());
        textInputEditTextEmail.setText(user.getEmail());

        storage.getDownloadUrl()
                .addOnSuccessListener(uri -> {

                    // Mandamos o Picasso carregar a imagem com a url que veio d firebase
                    Picasso.get()
                            .load(uri)
                            .into(imageViewFotoPerfil);
                });
    }

    private void salvarImagemFirebase(InputStream stream, String name) {

        // Pegamos a referencia do storage para salvar a imagem usando o ID do usuário
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AppUtil.getIdUsuario(getContext()) + "/image/profile/" + name);

        // Subimos a imagem com ums task para o firebase
        UploadTask uploadTask = storage.putStream(stream);

        // Observamos se deu suvesso ou erro
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Se conseguiu se registrar com sucesso vamos para a home
            Toast.makeText(getContext(), "Dados registrados com sucesso", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                for (File file : imageFiles) {
                    try {

                        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getPath());

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                            float degrees = 90;
                            Matrix matrix = new Matrix();
                            matrix.setRotate(degrees);
                            imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                        }

                        imageViewFotoPerfil.setImageBitmap(imageBitmap);

                        stream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initViews(View view){
        materialButtonSalvar = view.findViewById(R.id.botao_salvar);

        imageViewVoltar = view.findViewById(R.id.ic_back);
        imageViewSair = view.findViewById(R.id.imageViewLogout);
        imageViewFotoPerfil = view.findViewById(R.id.foto_perfil);

        textoLogout = view.findViewById(R.id.textViewLogout);

        textViewDelete = view.findViewById(R.id.textViewDelete);
        alteraFoto = view.findViewById(R.id.text_view_altera_foto);

        textInputLayoutNome = view.findViewById(R.id.textInputAlteraNome);
        textInputLayoutEmail = view.findViewById(R.id.textInputAlteraEmail);
        textInputLayoutSenha = view.findViewById(R.id.textInputAlteraSenha);

        textInputEditTextNome = view.findViewById(R.id.textInputEditTextNome);
        textInputEditTextEmail = view.findViewById(R.id.textInputEditTextEmail);

    }

    private void logout(GoogleSignInClient googleSignInClient) {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}