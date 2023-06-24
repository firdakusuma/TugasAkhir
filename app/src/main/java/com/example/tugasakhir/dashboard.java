package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
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

    private RecyclerView recyclerView;
    private MobilAdapter mobilAdapter = new MobilAdapter(generateMobilList());
    TextView tvName;
    ImageView imgPhoto;
    DatabaseReference usersRef;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
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
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Mobil> mobilList = generateMobilList();
        mobilAdapter = new MobilAdapter(mobilList);
        recyclerView.setAdapter(mobilAdapter);

        mobilAdapter.setOnItemClickListener((position, view) ->  {
            Intent intent = new Intent(dashboard.this, DetailMobilActivity.class);
            startActivity(intent);
        });

    }
    
    private List<Mobil> generateMobilList() {
        List<Mobil> mobilList = new ArrayList<>();
        mobilList.add(new Mobil("Honda Civic", "Automatic", "Rp.200.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.150.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Automativ", "Rp.350.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.200.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.200.000/hari"));
        // Tambahkan data mobil lainnya sesuai kebutuhan
        return mobilList;
    }
}