package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KombinlerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    KombinAdapter kombinAdapter;
    ArrayList<KombinData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kombinler);

        recyclerView = findViewById(R.id.recKombin);
        databaseReference = FirebaseDatabase.getInstance().getReference("Kombinler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        kombinAdapter = new KombinAdapter(this, list);
        recyclerView.setAdapter(kombinAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    KombinData kombinData = dataSnapshot.getValue(KombinData.class);
                    list.add(kombinData);
                }
                kombinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        kombinAdapter.setOnItemClickListener(new KombinAdapter.onItemClickListener() {
            @Override
            public void onCopClick(KombinData kombinData) {

                AlertDialog.Builder builder = new AlertDialog.Builder(KombinlerActivity.this);

                builder.setTitle("Kombin Silinecek!");
                builder.setMessage("Mevcut kombin kalıcı olarak silinecek. Emin misin?");
                builder.setCancelable(true);
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                databaseReference.child(kombinData.getId()).removeValue();
                Toast.makeText(getApplicationContext(), kombinData.getBas(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(KombinlerActivity.this, KombinlerActivity.class);
                startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Kombin Silindi!  ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
}