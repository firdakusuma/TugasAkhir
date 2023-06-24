package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Invoice extends AppCompatActivity {

    private Button button;
    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        button = findViewById(R.id.button2);
        btn_download = findViewById(R.id.btn_download);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Invoice.this, Pembayaran.class);
                startActivity(intent);
            }
        });
        btn_download.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Download file...");
            progressDialog.show();

            String filename = "my_image.jpg";
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("images/IMG1687248466146.jpeg");
            File foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File localFile = new File(foto, filename);

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Invoice.this, "Berhasil Download Gambar", Toast.LENGTH_SHORT).show();
                    // File download success, you can now access the file using 'localFile' variable
                    // The downloaded file is saved at the specified location
                    // Perform further operations on the downloaded file if needed
                    // For example, you can insert the downloaded image into the gallery using MediaStore
                    try {
                        MediaStore.Images.Media.insertImage(getContentResolver(), localFile.getAbsolutePath(), filename, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(Invoice.this, "Gagal Download Gambar", Toast.LENGTH_SHORT).show();
                    // Handle any errors that occur during the download process
                }
            });
        });
    }
}