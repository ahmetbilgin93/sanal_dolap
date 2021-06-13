package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class yeniCekmeceActivity extends AppCompatActivity {

    EditText cekmeceName;
    Button addCekmece;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Cekmeceler");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_cekmece);

        cekmeceName = findViewById(R.id.cekmeceName);
        addCekmece = findViewById(R.id.addCekmece);

        addCekmece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=cekmeceName.getText().toString();
                myRef.child(name).child("id").setValue(name);
                Toast.makeText(getApplicationContext(), "Çekmece Eklendi!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(yeniCekmeceActivity.this, CekmecelerActivity.class);
                startActivity(intent);



               // root.setValue(name);
            }
        });
    }
}