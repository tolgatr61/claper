package com.example.appli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    // Initialisation des attributs.
    ImageView imageView;
    Button btOpen;
    Button btImport;
    Button btHistorique;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des attributs;
        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);
        btImport = findViewById(R.id.bt_import);
        btHistorique = findViewById(R.id.bt_historique);

        // Requête pour la permission caméra
        requestCamera();
        // Listener sur le bouton Clap !.
        eventCapture(btOpen);
        // Listener sur le bouton import.
        eventImport(btImport);
        // Listener sur le bouton historique.
        eventHistorique(btHistorique);

    }

    // Résultat de l'activité (on récupère l'image et redirige vers la seconde Activity avec l'image en argument).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Cas où on prend une photo
        if (requestCode == 100 && null != data) {
            takePictureAndRedirect(data);
        } // import
        else if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && null != data) {
            importAndRedirect(data);
        }
    }

    public void pickImageFromGallery() {
        //Intent pour prendre une image.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // Méthode qui permet de vérifier le résultat des requêtes de permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission autorisé.
                    pickImageFromGallery();
                }
                else {
                    // Permission refusé.
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void takePictureAndRedirect(@Nullable Intent data) {
        // Get l'image Capturé
        Bitmap captureImage = (Bitmap) data.getExtras().get("data");
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }

    public void importAndRedirect(@Nullable Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }

    public void eventCapture(Button bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvre la caméra
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }

    public void eventImport(Button bt) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // Permission non autorisé, il faut envoyer la requête.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // Popup pour la runtime permission.
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        // Permission déjà autorisé.
                        pickImageFromGallery();
                    }
                } else {
                    // System OS inférieur à Marshmallow.
                    pickImageFromGallery();
                }
            }
        });
    }

    public void eventHistorique(Button bt) {
        btHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FourthActivity.class);
                startActivity(intent);
            }
        });
    }

    public void requestCamera() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                    Manifest.permission.CAMERA }, 100);
        }
    }
}