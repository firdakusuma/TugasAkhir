package com.example.tugasakhir;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tugasakhir.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT_PROFILE = 1;

    FirebaseAuth mAuth;
    DatabaseReference usersRef;
    ActivityProfileBinding binding;

    // atribut dialog custom
    TextView btnHapusAcc;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String userUid = currentUser.getUid();

            usersRef.child(userUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        ModelUser user = snapshot.getValue(ModelUser.class);
                        showData(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, "Database Error", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, dashboard.class);
                    startActivity(intent);
                }
            });
        }

        // berpindah ke halaman dashboard
        binding.btnBack.setOnClickListener(view -> {
            Profile.this.finish();
        });

        // berpindah ke halaman edit Profile
        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat intent untuk EditProfile
                Intent intent = new Intent(Profile.this, editprofile.class);

                // Mendapatkan data pengguna dari tampilan
                String nama = binding.tvNamaProfile.getText().toString();
                String email = binding.tvEmailProfile.getText().toString();
                String alamat = binding.tvAlamatProfile.getText().toString();
                String noHP = binding.tvNomorHPProfile.getText().toString();
                String ttl = binding.tvTTLProfile.getText().toString();
                String avatar = ""; // Mengambil URL foto profil dari Firebase Realtime Database

                // Menambahkan data pengguna ke intent
                intent.putExtra("nama", nama);
                intent.putExtra("email", email);
                intent.putExtra("alamat", alamat);
                intent.putExtra("noHP", noHP);
                intent.putExtra("ttl", ttl);
                intent.putExtra("avatar", avatar);

                // Memulai aktivitas EditProfile dengan intent yang sudah diisi
                startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE);
            }
        });

        btnHapusAcc = findViewById(R.id.btnHapusAcc);
        // membuat dialog alert untuk menghapus akun
        dialog = new Dialog(this);
        dialog.setContentView(androidx.core.R.layout.custom_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);

        // setting animation pada dialog
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btnDeleteConfirm = dialog.findViewById(R.id.btnDeleteConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnDeleteConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kode untuk menghapus data dari database
                usersRef.child(currentUser.getUid()).removeValue();
                Toast.makeText(Profile.this, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
                Toast.makeText(Profile.this, "Akun Anda berhasil dihapus", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnHapusAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menampilkan dialog custom
                dialog.show();
            }
        });

    }

    private void showData(ModelUser user) {
        // Menampilkan data User pada tampilan ProfilePage
        binding.tvNama.setText(user.getNama());
        binding.tvEmailP.setText(user.getEmail());
        binding.tvNamaProfile.setText(user.getNama());
        binding.tvEmailProfile.setText(user.getEmail());
        binding.tvAlamatProfile.setText(user.getAlamat());
        binding.tvNomorHPProfile.setText(user.getNoHP());
        binding.tvTTLProfile.setText(user.getTTL());

        // Memuat foto profil menggunakan Glide
        Glide.with(this)
                .load(user.getAvatar())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avaProfile);
    }
}