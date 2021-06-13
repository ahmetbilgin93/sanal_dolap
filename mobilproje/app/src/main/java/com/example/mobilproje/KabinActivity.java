package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class KabinActivity extends AppCompatActivity {

    ImageView imgBas, imgUst1, imgUst2, imgAlt, imgAyak;
    String ba, u1, u2, al, ay;
    FloatingActionButton addBas, addUst1, addUst2, addAlt, addAyak;
    Button addKombin, paylasKombin;
    TextView showAll;
    Context context=this;
    SP sp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    Uri imageUri, imageUri2, imageUri3, imageUri4, imageUri5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabin);

        sp=new SP();

        verifyStoragePermission(this);

        imgBas = findViewById(R.id.imgBas);
        imgUst1 = findViewById(R.id.imgUst1);
        imgUst2 = findViewById(R.id.imgUst2);
        imgAlt = findViewById(R.id.imgAlt);
        imgAyak = findViewById(R.id.imgAyak);

        addBas = findViewById(R.id.addBas);
        addUst1 = findViewById(R.id.addUst1);
        addUst2 = findViewById(R.id.addUst2);
        addAlt = findViewById(R.id.addAlt);
        addAyak = findViewById(R.id.addAyak);

        addKombin = findViewById(R.id.addKombin);
        paylasKombin = findViewById(R.id.paylasKombin);

        showAll = findViewById(R.id.showAll);

        addBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 1);
                sp.saveString(context, "aranan", "bas");
                Intent intent=new Intent(KabinActivity.this, Cek2Activity.class);
                startActivity(intent);
            }
        });

        addUst1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 1);
                sp.saveString(context, "aranan", "ust1");
                Intent intent=new Intent(KabinActivity.this, Cek2Activity.class);
                startActivity(intent);
            }
        });

        addUst2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 1);
                sp.saveString(context, "aranan", "ust2");
                Intent intent=new Intent(KabinActivity.this, Cek2Activity.class);
                startActivity(intent);
            }
        });

        addAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 1);
                sp.saveString(context, "aranan", "alt");
                Intent intent=new Intent(KabinActivity.this, Cek2Activity.class);
                startActivity(intent);
            }
        });

        addAyak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 1);
                sp.saveString(context, "aranan", "ayak");
                Intent intent=new Intent(KabinActivity.this, Cek2Activity.class);
                startActivity(intent);
            }


        });

        ba = sp.getStringValue(context, "bas");
        u1 = sp.getStringValue(context, "ust1");
        u2 = sp.getStringValue(context, "ust2");
        al = sp.getStringValue(context, "alt");
        ay = sp.getStringValue(context, "ayak");

        if (ba!=null){
            imageUri = Uri.parse(ba);
            Picasso.with(context).load(imageUri).into(imgBas);
        }
        if (u1!=null){
            imageUri2 = Uri.parse(u1);
            Picasso.with(context).load(imageUri2).into(imgUst1);
        }
        if (u2!=null){
            imageUri3 = Uri.parse(u2);
            Picasso.with(context).load(imageUri3).into(imgUst2);
        }
        if (al!=null){
            imageUri4 = Uri.parse(al);
            Picasso.with(context).load(imageUri4).into(imgAlt);
        }
        if (ay!=null){
            imageUri5 = Uri.parse(ay);
            Picasso.with(context).load(imageUri5).into(imgAyak);
        }


        addKombin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference("Kombinler");
                final String randomKey = UUID.randomUUID().toString();

                myRef.child(randomKey).child("bas").setValue(ba);
                myRef.child(randomKey).child("ust1").setValue(u1);
                myRef.child(randomKey).child("ust2").setValue(u2);
                myRef.child(randomKey).child("alt").setValue(al);
                myRef.child(randomKey).child("ayak").setValue(ay);
                myRef.child(randomKey).child("id").setValue(randomKey);

                Toast.makeText(getApplicationContext(), "Kombin Kaydedildi!", Toast.LENGTH_LONG).show();

                sp.remove(context, "bas");
                sp.remove(context, "ust1");
                sp.remove(context, "ust2");
                sp.remove(context, "alt");
                sp.remove(context, "ayak");

                Intent intent = getIntent();
                finish();
                startActivity(getIntent());
            }
        });


showAll.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(KabinActivity.this, KombinlerActivity.class);
        startActivity(intent);
    }
});

        paylasKombin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenShot(getWindow().getDecorView().getRootView(), "result");
            }
        });


    }

    protected  File takeScreenShot(View view, String fileName) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        try {
            String dirPath = Environment.getExternalStorageDirectory().toString();
            File fileDir = new File(dirPath);
            if(!fileDir.exists()){
                boolean mkdir = fileDir.mkdir();
            }
                String path = dirPath+"/"+fileName+"-"+format +".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality=100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareScreenShot(imageFile);
            return imageFile;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

/*

                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            String path = mainDir + "/" + "TrendOceans" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                imageFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Application from Instagram");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    //Permissions Check

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}