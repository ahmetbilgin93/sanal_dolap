package com.example.mobilproje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button cekmece;
    Button etkinlik;
    Button kiyafet;
    Button kabin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cekmece = (Button) findViewById(R.id.cekmeceler);
        etkinlik = (Button) findViewById(R.id.etkinlikler);

        kabin = findViewById(R.id.kabin);

        cekmece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CekmecelerActivity.class );
                startActivity(intent);
            }
        });

        etkinlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, EtkinliklerActivity.class);
                startActivity(intent);
            }
        });



       kabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, KabinActivity.class);
                startActivity(intent);
            }
        });
    }
}