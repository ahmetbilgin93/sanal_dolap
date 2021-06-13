package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class yeniEtkinlikActivity extends AppCompatActivity {

    EditText etismi, ettur, ettar, etlok;
    Button btnEtk;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_etkinlik);

        etismi = findViewById(R.id.etismi);
        ettur = findViewById(R.id.ettur);
        ettar = findViewById(R.id.ettar);
        etlok = findViewById(R.id.etlok);

        btnEtk = findViewById(R.id.btnEtk);

        myRef = database.getReference("Etkinlikler");

        btnEtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String randomKey = UUID.randomUUID().toString();
                String is = etismi.getText().toString();
                String tu = ettur.getText().toString();
                String ta = ettar.getText().toString();
                String lo = etlok.getText().toString();

                myRef.child(randomKey).child("isim").setValue(is);
                myRef.child(randomKey).child("tur").setValue(tu);
                myRef.child(randomKey).child("tarih").setValue(ta);
                myRef.child(randomKey).child("lokasyon").setValue(lo);
                myRef.child(randomKey).child("id").setValue(randomKey);

                Toast.makeText(getApplicationContext(), "Etkinlik Kaydedildi!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(yeniEtkinlikActivity.this, EtkinliklerActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}