package com.example.tugasakhir;

public class Mobil {

    private String namaMobil;
    private String tipeMobil;
    private String hargaSewa;

    public Mobil(String namaMobil, String tipeMobil, String hargaSewa) {
        this.namaMobil = namaMobil;
        this.tipeMobil = tipeMobil;
        this.hargaSewa = hargaSewa;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public String getTipeMobil() {
        return tipeMobil;
    }

    public String getHargaSewa() {
        return hargaSewa;
    }
}
