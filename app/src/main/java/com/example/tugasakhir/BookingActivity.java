package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private EditText editTextLokasi;
    private Switch switchSopir;
    private CalendarView calendarView;
    private Button buttonKonfirmasi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

//        editTextLokasi = findViewById(R.id.etLokasi);
//        switchSopir = findViewById(R.id.swSopir);
//        calendarView = findViewById(R.id.calendarView);
        buttonKonfirmasi = findViewById(R.id.btKonfirmasi);

        buttonKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String lokasi = editTextLokasi.getText().toString();
//                boolean sopir = switchSopir.isChecked();
//                long tanggal = calendarView.getDate();
//
//                // Menyimpan inputan pengguna pada objek Bundle
//                Bundle data = new Bundle();
//                data.putString("lokasi", lokasi);
//                data.putBoolean("sopir", sopir);
//                data.putLong("tanggal", tanggal);

                // Membuka halaman activity_konfirmasi.xml dan mengirimkan data
                Intent intent = new Intent(BookingActivity.this, KonfirmasiActivity.class);
//                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }
}
