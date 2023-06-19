package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class KonfirmasiActivity extends AppCompatActivity {

    private Button buttonKonfirmasi;
    private EditText editTextLokasi;
    private Switch switchSopir;
    private CalendarView calendarView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        buttonKonfirmasi = findViewById(R.id.button4);
        editTextLokasi = findViewById(R.id.etLokasi);
        switchSopir = findViewById(R.id.swSopir);
        calendarView = findViewById(R.id.calendarView);

        buttonKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KonfirmasiActivity.this, OverviewActivity.class);
                startActivity(intent);
            }
        });
    }

}
