package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.provider.MediaStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class yeniKiyafetActivity extends AppCompatActivity {

    ImageView kiyafetFoto;
    public Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Button addKiyafet, addAll;
    EditText desen, renk, tur, fiyat, tarih;
    String ckm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_kiyafet);

        kiyafetFoto = findViewById(R.id.fotosu);

        addKiyafet =findViewById(R.id.kiyafetSec);
        addAll = findViewById(R.id.addKiyafet);

        desen = findViewById(R.id.deseni);
        renk = findViewById(R.id.rengi);
        tur = findViewById(R.id.turu);
        fiyat = findViewById(R.id.fiyati);
        tarih = findViewById(R.id.tarihi);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ckm = bundle.getString("cekmecen");
        }

        myRef = database.getReference("Cekmeceler/"+ckm+"/Kiyafetler");

        addKiyafet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSec();
            }
        });

        addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAll();

            }
        });
    }

    private void resimSec() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim Seç"), 1) ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 1) {
                // Get the url of the image from data
                imageUri = data.getData();
                if (null != imageUri) {
                    // update the preview image in the layout
                    kiyafetFoto.setImageURI(imageUri);
                    //uploadPicture();
                }
            }
        }
    }

    private void uploadAll() {

        final String randomKey = UUID.randomUUID().toString();

        String d=desen.getText().toString();
        myRef.child(randomKey).child("desen").setValue(d);

        String r=renk.getText().toString();
        myRef.child(randomKey).child("renk").setValue(r);

        String t=tur.getText().toString();
        myRef.child(randomKey).child("tur").setValue(t);

        String f=fiyat.getText().toString();
        myRef.child(randomKey).child("fiyat").setValue(f);

        String tar=tarih.getText().toString();
        myRef.child(randomKey).child("tarih").setValue(tar);

        myRef.child(randomKey).child("id").setValue(randomKey);


        // Create a reference to "mountains.jpg"
       // StorageReference mountainsRef = storageReference.child("mountains.jpg");



// Create a reference to 'images/mountains.jpg'
        StorageReference imagesRef = storageReference.child("images/"+randomKey);

        imagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "Kıyafet Çekmeceye Eklendi", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(yeniKiyafetActivity.this, CekmeceListesi.class);
                finish();
                intent.putExtra("cekmecen", ckm);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "HATA! Kıyafet eklenemedi!", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

            }
        });

// While the file names are the same, the references point to different files
   //     mountainsRef.getName().equals(mountainImagesRef.getName());    // true
     //   mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
    }
}