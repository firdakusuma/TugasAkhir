package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText lgnEmail, lgnPass;
    AppCompatButton btnLogin;
    TextView btnRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lgnEmail = findViewById(R.id.lgnEmail);
        lgnPass = findViewById(R.id.lgnPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lgnEmail.getText().toString();
                String password = lgnPass.getText().toString();
                login(email, password);
//                if (!validateEmail() | !validatePassword()){
//
//                } else {
//                    checkUser();
//                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void login(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Isi email dan password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                    //Menuju ke dashboard
                    Intent intent = new Intent(Login.this, dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Login.this, "Login Gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//
//    private void checkUser() {
//        String namaUser = lgnEmail.getText().toString().trim();
//        String passUser = lgnPass.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
//        Query checkDatabase = reference.orderByChild("nama").equalTo(namaUser);
//        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    lgnEmail.setError(null);
//                    String dbPass = snapshot.child(namaUser).child("pass").getValue(String.class);
//
//                    if (dbPass.equals(passUser)){
//                        lgnEmail.setError(null);
//
//                        // passing data intent
//                        String dbNama = snapshot.child(namaUser).child("nama").getValue(String.class);
//                        String dbEmail = snapshot.child(namaUser).child("email").getValue(String.class);
//                        String dbAlamat = snapshot.child(namaUser).child("alamat").getValue(String.class);
//                        String dbNoHP = snapshot.child(namaUser).child("noHP").getValue(String.class);
//                        String dbTTL = snapshot.child(namaUser).child("ttl").getValue(String.class);
//                        String dbAva = snapshot.child(namaUser).child("avatar").getValue(String.class);
//
//                        Intent intent = new Intent(Login.this, dashboard.class);
//                        intent.putExtra("source", "Login");
//                        intent.putExtra("nama", dbNama);
//                        intent.putExtra("pass", dbPass);
//                        intent.putExtra("email", dbEmail);
//                        intent.putExtra("alamat", dbAlamat);
//                        intent.putExtra("noHP", dbNoHP);
//                        intent.putExtra("TTL", dbTTL);
//                        intent.putExtra("avatar", dbAva);
//                        startActivity(intent);
//                    } else {
//                        lgnPass.setError("Password invalid");
//                        lgnPass.requestFocus();
//                    }
//                } else {
//                    lgnEmail.setError("User tidak tersedia");
//                    lgnEmail.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private boolean validatePassword() {
//        String val = lgnPass.getText().toString();
//        if (val.isEmpty()){
//            lgnPass.setError("Isi form password");
//            return false;
//        } else {
//            lgnPass.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validateEmail() {
//        String val = lgnEmail.getText().toString();
//        if (val.isEmpty()){
//            lgnEmail.setError("Isi form email");
//            return false;
//        } else {
//            lgnEmail.setError(null);
//            return true;
//        }
//    }
}
