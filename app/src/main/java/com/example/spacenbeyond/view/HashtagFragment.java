package com.example.spacenbeyond.view;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.spacenbeyond.R;
import com.example.spacenbeyond.model.PhotoResponse;
import com.example.spacenbeyond.viewmodel.PhotoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import static android.content.Context.MODE_PRIVATE;
import static com.example.spacenbeyond.view.HomeFragment.API_KEY;
import static com.facebook.FacebookSdk.getApplicationContext;

public class HashtagFragment extends Fragment {

    private ImageView imageViewBack;
    private ImageView imageShare;
    private ImageView imageViewTOP;

    private FloatingActionButton floatingActionButtonCamera;

    private TextView textViewHashUm;
    private TextView textViewHashDois;

    private PhotoViewModel photoViewModel;

    private InputStream stream = null;
    private static final int PERMISSION_CODE = 100;

    private Context context;

    public HashtagFragment() { }

    @Override
    public void onAttach(@NonNull Context context){
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hashtag, container, false);

        initViews(view);

        imageViewBack.setOnClickListener(v -> closefragment());

        floatingActionButtonCamera.setOnClickListener(view1 -> captureImage());

        imageShare.setOnClickListener(v -> {

            try {
                BitmapDrawable drawable = (BitmapDrawable) imageViewTOP.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                String savedFile = SaveImage(bitmap);

                File media = new File(savedFile);
                Uri imageUri =  FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", media);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT, textViewHashUm.getText() + "\n" + textViewHashDois.getText());
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Label", textViewHashUm.getText() + "\n" + textViewHashDois.getText());
                clipboard.setPrimaryClip(clip);

                startActivity(Intent.createChooser(share, "Share Image"));
            }
            catch (Throwable e) {
                Toast.makeText(getContext(), "Não foi possível executar a ação.", Toast.LENGTH_LONG).show();
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(currentTime);
        photoViewModel.getPhotoOfDay(todayString, API_KEY);
        photoViewModel.liveData.observe(getViewLifecycleOwner(), (PhotoResponse result) -> {

            sorteioHashtags(result);
        });

        return view;
    }

    private void sorteioHashtags(PhotoResponse result) {
        Random random = new Random();
        String[] arr = result.getExplanation().split(" ");

        int len = arr.length;
        String[] novoArr = new String[len];
        int j = 0;

        for (int i=0; i < len -1; i++){

            if (!arr[i].equals("the") && !arr[i].equals("is") && !arr[i].equals("on") && !arr[i].equals("at")
                    && !arr[i].equals("in") && !arr[i].equals("of") && !arr[i].equals("that")
                    && !arr[i].equals("are") && !arr[i].equals("The") && !arr[i].equals("and")
                    && !arr[i].equals("than")&& !arr[i].equals("to")&& !arr[i].equals("from")
                    && !arr[i].equals("do")&& !arr[i].equals("this")&& !arr[i].equals("don't")
                    && !arr[i].equals("doesn't")&& !arr[i].equals("will")&& !arr[i].equals("a")
                    && !arr[i].equals("an")&& !arr[i].equals("our")&& !arr[i].equals("mine")
                    && !arr[i].equals("ours")&& !arr[i].equals("it") && !arr[i].equals("those")
                    && !arr[i].equals(" ") && !arr[i].equals("  ") && !arr[i].equals("with")
                    && !arr[i].equals("its") && !arr[i].equals("if")){
                novoArr[j++] = arr[i];
            }
        }

        int randomIdx1 = random.nextInt(j);
        int randomIdx2 = random.nextInt(j);

        if (novoArr[randomIdx1].equals(novoArr[randomIdx2])){
            randomIdx2 = random.nextInt(j);
        }

        textViewHashUm.setText("#" + novoArr[randomIdx1]);
        textViewHashDois.setText("#" + novoArr[randomIdx2]);
    }

    private String SaveImage(Bitmap finalBitmap) {

        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("SAVEIMAGE", "Error creating media file, check storage permissions: ");// e.getMessage());
            return "Error creating media file, check storage permissions: ";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
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
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                for (File file : imageFiles) {
                    try {

                        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getPath());

                        // Aqui podemos modificar o tamnho do arquivo antes de enviar
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                            float degrees = 90;//rotation degree
                            Matrix matrix = new Matrix();
                            matrix.setRotate(degrees);
                            imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                        }

                        imageViewTOP.setImageBitmap(imageBitmap);
                        //Drawable d = Drawable.createFromPath(file.getPath());
                        //constraintLayoutTop.setBackground(d);

                        stream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void closefragment() {
        getFragmentManager().beginTransaction().remove(HashtagFragment.this).commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void initViews(View view) {
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        textViewHashUm = view.findViewById(R.id.textViewHashtagUm);
        textViewHashDois = view.findViewById(R.id.textViewHashtagDois);

        floatingActionButtonCamera = view.findViewById(R.id.floatingActionButtonCamera);

        imageViewBack = view.findViewById(R.id.ic_back);
        imageShare = view.findViewById(R.id.ic_share);
        imageViewTOP = view.findViewById(R.id.imageViewTop);
    }
}