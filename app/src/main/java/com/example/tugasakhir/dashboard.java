package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tugasakhir.databinding.ActivityMainBinding;
import com.example.tugasakhir.databinding.ActivityProfileBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {
    TextView tvName;
    ImageView imgPhoto;
    DatabaseReference usersRef;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    RecyclerView recyclerView;
    DatabaseReference mobil;
    private MobilAdapter mobilAdapter;
    TextView tvName;
    Button btAdd;
    private ArrayList<Mobil> mobilArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        imgPhoto = findViewById(R.id.imgPhoto);
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            usersRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        ModelUser user = snapshot.getValue(ModelUser.class);
                        tvName.setText("Hello, "+user.getNama());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, Profile.class);
              
        btAdd = findViewById(R.id.btAdd);
        tvName = findViewById(R.id.tvNama);

        mobil = FirebaseDatabase.getInstance("https://tugasakhir-187318-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("mobil");

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, AddPage.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getAllData();
    }

    private void getAllData() {
        this.mobil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mobilArrayList = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
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
                System.out.println("error");
            }
        });
    }
}