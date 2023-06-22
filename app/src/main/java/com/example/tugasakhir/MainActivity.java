package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mobil;
    private MobilAdapter mobilAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    TextView tvName;
    Button btAdd;
    private ArrayList <Mobil> mobilArrayList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btAdd = findViewById(R.id.btAdd);
        tvName = findViewById(R.id.tvNama);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllData();

        databaseReference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        mobil = this.databaseReference.child("mobil");

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart () {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            tvName.setText(currentUser.getEmail());
        }
    }

    private void getAllData() {
        this.mobil.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mobilArrayList = new ArrayList<>();
                for (DataSnapshot s: snapshot.getChildren()){
                    Mobil d = s.getValue(Mobil.class);
                    System.out.println(d.getNamaMobil());
                    d.setKey(s.getKey());
                    mobilArrayList.add(d);
                }
                mobilAdapter = new MobilAdapter(mobilArrayList);
                recyclerView.setAdapter(mobilAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("eror");
            }
        });
    }

}
