package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Login extends AppCompatActivity {

    EditText lgnEmail, lgnPass;
    AppCompatButton btnLogin;
    TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lgnEmail = findViewById(R.id.lgnEmail);
        lgnPass = findViewById(R.id.lgnPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()){

                } else {
                    checkUser();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkUser() {
        String namaUser = lgnEmail.getText().toString().trim();
        String passUser = lgnPass.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query checkDatabase = reference.orderByChild("nama").equalTo(namaUser);
        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    lgnEmail.setError(null);
                    String dbPass = snapshot.child(namaUser).child("pass").getValue(String.class);

                    if (dbPass.equals(passUser)){
                        lgnEmail.setError(null);

                        // passing data intent
                        String dbNama = snapshot.child(namaUser).child("nama").getValue(String.class);
                        String dbEmail = snapshot.child(namaUser).child("email").getValue(String.class);
                        String dbAlamat = snapshot.child(namaUser).child("alamat").getValue(String.class);
                        String dbNoHP = snapshot.child(namaUser).child("noHP").getValue(String.class);
                        String dbTTL = snapshot.child(namaUser).child("ttl").getValue(String.class);
                        String dbAva = snapshot.child(namaUser).child("avatar").getValue(String.class);

                        Intent intent = new Intent(Login.this, dashboard.class);
                        intent.putExtra("source", "Login");
                        intent.putExtra("nama", dbNama);
                        intent.putExtra("pass", dbPass);
                        intent.putExtra("email", dbEmail);
                        intent.putExtra("alamat", dbAlamat);
                        intent.putExtra("noHP", dbNoHP);
                        intent.putExtra("TTL", dbTTL);
                        intent.putExtra("avatar", dbAva);
                        startActivity(intent);
                    } else {
                        lgnPass.setError("Password invalid");
                        lgnPass.requestFocus();
                    }
                } else {
                    lgnEmail.setError("User tidak tersedia");
                    lgnEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean validatePassword() {
        String val = lgnPass.getText().toString();
        if (val.isEmpty()){
            lgnPass.setError("Isi form password");
            return false;
        } else {
            lgnPass.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = lgnEmail.getText().toString();
        if (val.isEmpty()){
            lgnEmail.setError("Isi form email");
            return false;
        } else {
            lgnEmail.setError(null);
            return true;
        }
    }
}
