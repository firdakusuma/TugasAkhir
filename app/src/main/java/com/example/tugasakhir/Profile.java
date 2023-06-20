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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    Button btnEditProfile;
    TextView tvNama, tvEmailP;
    TextView tvNamaProfile, tvEmailProfile, tvNomorHPProfile, tvAlamatProfile, tvTTLProfile;
    ImageView btnBack;

    // atribut dialog custom
    TextView btnHapusAcc;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // menangkap data dari halaman login dan register
        tvNama = findViewById(R.id.tvNama);
        tvEmailP = findViewById(R.id.tvEmailP);
        tvNamaProfile = findViewById(R.id.tvNamaProfile);
        tvEmailProfile = findViewById(R.id.tvEmailProfile);
        tvNomorHPProfile = findViewById(R.id.tvNomorHPProfile);
        tvAlamatProfile = findViewById(R.id.tvAlamatProfile);
        tvTTLProfile = findViewById(R.id.tvTTLProfile);

        String source = getIntent().getStringExtra("source");
        if (source.equals("Login")){
            showEditData();
        } else if (source.equals("editProfile")) {
            showUpdateData();
        }

        // berpindah ke halaman edit Profile
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passData();
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
                String namaUser = tvNamaProfile.getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                reference.child(namaUser).removeValue();
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

        // pindah ke halaman sebelumnya (dashboard)
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile.this.finish();
            }
        });

    }

    // menampilkan data dari edit profile
    private void showUpdateData() {
        Intent intent = getIntent();

        String source = intent.getStringExtra("source");
        String namaUser = intent.getStringExtra("nama");
        String passUser = intent.getStringExtra("pass");
        String emailUser = intent.getStringExtra("email");
        String alamatUser = intent.getStringExtra("alamat");
        String noHPUser = intent.getStringExtra("noHP");
        String TTLUser = intent.getStringExtra("TTL");
        String ava = intent.getStringExtra("avatar");

        tvNamaProfile.setText(namaUser);
        tvNama.setText(namaUser);
        tvEmailProfile.setText(emailUser);
        tvEmailP.setText(emailUser);
        tvAlamatProfile.setText(alamatUser);
        tvNomorHPProfile.setText(noHPUser);
        tvTTLProfile.setText(TTLUser);

    }

    // menampilkan data hasil register
    private void showEditData() {
        Intent intent = getIntent();

        String source = intent.getStringExtra("source");
        String namaUser = intent.getStringExtra("nama");
        String passUser = intent.getStringExtra("pass");
        String emailUser = intent.getStringExtra("email");
        String alamatUser = intent.getStringExtra("alamat");
        String noHPUser = intent.getStringExtra("noHP");
        String TTLUser = intent.getStringExtra("TTL");
        String avatar = intent.getStringExtra("avatar");

        tvNamaProfile.setText(namaUser);
        tvNama.setText(namaUser);
        tvEmailProfile.setText(emailUser);
        tvEmailP.setText(emailUser);
        tvAlamatProfile.setText(alamatUser);
        tvNomorHPProfile.setText(noHPUser);
        tvTTLProfile.setText(TTLUser);
    }

    // mengirim data ke edit profile
    private void passData() {
        String namaUser = tvNamaProfile.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query checkDatabase = reference.orderByChild("nama").equalTo(namaUser);
        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String dbNama = snapshot.child(namaUser).child("nama").getValue(String.class);
                    String dbEmail = snapshot.child(namaUser).child("email").getValue(String.class);
                    String dbAlamat = snapshot.child(namaUser).child("alamat").getValue(String.class);
                    String dbNoHP = snapshot.child(namaUser).child("noHP").getValue(String.class);
                    String dbTTL = snapshot.child(namaUser).child("ttl").getValue(String.class);
                    String dbPass = snapshot.child(namaUser).child("pass").getValue(String.class);
                    String dbAva = snapshot.child(namaUser).child("avatar").getValue(String.class);

                    Intent intent = new Intent(Profile.this, editprofile.class);
                    intent.putExtra("nama", dbNama);
                    intent.putExtra("pass", dbPass);
                    intent.putExtra("email", dbEmail);
                    intent.putExtra("alamat", dbAlamat);
                    intent.putExtra("noHP", dbNoHP);
                    intent.putExtra("TTL", dbTTL);
                    intent.putExtra("avatar", dbAva);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}