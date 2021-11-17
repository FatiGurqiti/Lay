package com.example.fibuv2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Admin extends AppCompatActivity {

    private static final int GALLERY_CODE = 1;
    private EditText FilmAd;
    private EditText FilmTur;
    private EditText Film_Yil;
    private EditText Film_Aciklama;
    private Button EkleBtn;
    private ImageView ResimEkleBtn;
    private ImageView IMG;

   /* private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Filmler");
   */ private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
}