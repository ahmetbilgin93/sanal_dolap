package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CekmecelerActivity extends AppCompatActivity {

    Button cekmeceEkle;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CekmeceAdapter cekmeceAdapter;
    ArrayList<CekmeceData> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekmeceler);

        cekmeceEkle = (Button) findViewById(R.id.cekmeceEkle);

        recyclerView = findViewById(R.id.recCekmece);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cekmeceler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        cekmeceAdapter = new CekmeceAdapter(this, list);
        recyclerView.setAdapter(cekmeceAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CekmeceData cekmeceData = dataSnapshot.getValue(CekmeceData.class);
                    list.add(cekmeceData);
                }
                cekmeceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cekmeceAdapter.setOnItemClickListener(new CekmeceAdapter.onItemClickListener() {
            @Override
            public void onItemClick(CekmeceData cekmeceData) {
                //Toast.makeText(getApplicationContext(), cekmeceData.getId(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CekmecelerActivity.this, CekmeceListesi.class);
                intent.putExtra("cekmecen", cekmeceData.getId());
                startActivity(intent);
            }
        });

        cekmeceEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CekmecelerActivity.this, yeniCekmeceActivity.class);
                startActivity(intent);
            }
        });
    }
}