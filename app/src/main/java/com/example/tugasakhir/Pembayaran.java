package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Pembayaran extends AppCompatActivity {
    BottomNavigationView buttonNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation item selection here
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.lokasi:
                    fragment = new MapsFragment();
                    break;
                case R.id.home:
                    fragment = new MapsFragment();
                    break;
                case R.id.profile:
                    fragment = new MapsFragment();
                    break;
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }

            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        buttonNavigation = findViewById(R.id.navigation_menu);
        buttonNavigation.setOnNavigationItemSelectedListener(navigation);
    }
}