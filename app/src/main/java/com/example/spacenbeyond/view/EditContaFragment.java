package com.example.spacenbeyond.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.util.AppUtil;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

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

    private AlertDialog alerta;

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

        imageViewFotoPerfil.setOnClickListener(v -> captureImage());

        alteraFoto.setOnClickListener(v -> captureImage());

        imageViewSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.custom_alert, null);
                builder.setView(view);
                final AlertDialog alert = builder.create();
                Button sim = (Button) view.findViewById(R.id.botao_sim);
                Button nao = (Button) view.findViewById(R.id.botao_nao);
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout(googleSignInClient);
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        });

        materialButtonSalvar.setOnClickListener(v -> {

            AppUtil.hideKeyboard(getActivity());

            String nome = textInputLayoutNome.getEditText().getText().toString();
            String email = textInputLayoutEmail.getEditText().getText().toString();
            String senha = textInputLayoutSenha.getEditText().getText().toString();

            if (!nome.isEmpty()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> valid = task.isSuccessful());
            }

            if (!email.isEmpty()) {

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputLayoutEmail.setError("Email inválido");
                    textInputLayoutEmail.requestFocus();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updateEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                valid = true;
                            } else {
                                Toast.makeText(getContext(), "Faça login novamente para atualizar o email.", Toast.LENGTH_LONG).show();
                            }
                        });
            }

            if (!senha.isEmpty()) {

                if (senha.length() < 6) {
                    textInputLayoutSenha.setError("Senha deve ser maior que 6 caracteres");
                    textInputLayoutSenha.requestFocus();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(senha)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                valid = true;
                            } else {
                                Toast.makeText(getContext(), "Faça login novamente para atualizar a senha.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            if (stream != null) {
                salvarImagemFirebase(stream);
            }

            if (valid) {
                Toast.makeText(getContext(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.custom_alert, null);
                builder.setView(view);
                final AlertDialog alert = builder.create();
                TextView dialogTextView = view.findViewById(R.id.dialog_text);
                dialogTextView.setText("Tem certeza que deseja excluir a conta?");
                Button sim = (Button) view.findViewById(R.id.botao_sim);
                Button nao = (Button) view.findViewById(R.id.botao_nao);
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        getActivity().finish();
                                        Toast.makeText(getActivity(), "Conta excluída com sucesso.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        });

        textoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.custom_alert, null);
                builder.setView(view);
                final AlertDialog alert = builder.create();
                Button sim = (Button) view.findViewById(R.id.botao_sim);
                Button nao = (Button) view.findViewById(R.id.botao_nao);
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout(googleSignInClient);
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                alert.show();
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
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        Profile profile = Profile.getCurrentProfile();

        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AppUtil.getIdUsuario(getContext()) + "/image/profile/imagem-perfil");

        textInputEditTextNome.setText(user.getDisplayName());
        textInputEditTextEmail.setText(user.getEmail());


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {

            storage.getDownloadUrl()
                    .addOnSuccessListener((Uri uri) -> Picasso.get()
                            .load(uri)
                            .into(imageViewFotoPerfil))
                    .addOnFailureListener(e -> {
                        if (acct != null) {
                            Uri personPhoto = acct.getPhotoUrl();
                            Picasso.get()
                                    .load(personPhoto)
                                    .into(imageViewFotoPerfil);
                        }

                        if (profile != null){
                            Picasso.get()
                                    .load(profile.getProfilePictureUri(100, 100))
                                    .into(imageViewFotoPerfil);
                        }
                    });
        }
        else {

            storage.getDownloadUrl()
                    .addOnSuccessListener((Uri uri) -> Picasso.get()
                            .load(uri)
                            .into(imageViewFotoPerfil))
                    .addOnFailureListener(e -> {
                        if (acct != null) {
                            Uri personPhoto = acct.getPhotoUrl();
                            Picasso.get()
                                    .load(personPhoto)
                                    .into(imageViewFotoPerfil);
                        }

                        if (profile != null){
                            Picasso.get()
                                    .load(profile.getProfilePictureUri(100, 100))
                                    .into(imageViewFotoPerfil);
                        }
                    });
        }
    }

    private void salvarImagemFirebase(InputStream stream) {

        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AppUtil.getIdUsuario(getContext()) + "/image/profile/" + "imagem-perfil");

        UploadTask uploadTask = storage.putStream(stream);

        uploadTask.addOnSuccessListener(taskSnapshot -> Toast.makeText(getContext(), "Nova foto de perfil salva.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
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

                        Bitmap bitmapImage = new AppUtil().getResizedBitmap(imageBitmap, 500);

                        imageViewFotoPerfil.setImageBitmap(bitmapImage);


                        String path = SaveImage(bitmapImage);

                        File media = new File(path);

                        stream = new FileInputStream(media);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String SaveImage(Bitmap finalBitmap) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("SAVEIMAGE", "Error creating media file, check storage permissions: ");// e.getMessage());
            return "Error creating media file, check storage permissions: ";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
            fos.close();
            return pictureFile.getAbsolutePath();
        }
        catch (FileNotFoundException e) {
            Log.d("SAVEIMAGE", "File not found: " + e.getMessage());
            return e.getMessage();
        }
        catch (IOException e) {
            Log.d("SAVEIMAGE", "Error accessing file: " + e.getMessage());
            return e.getMessage();
        }
    }

    private  File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files");


        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
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
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}