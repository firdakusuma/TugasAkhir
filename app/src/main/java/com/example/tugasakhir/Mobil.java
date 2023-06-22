package com.example.tugasakhir;

public class Mobil {

    private String namaMobil;
    private String jumlahPenumpang;
    private String hargaSewa;
    private String model;
    private String key;

    public Mobil(String namaMobil, String hargaSewa, String jumlahPenumpang, String model) {
        this.namaMobil = namaMobil;
        this.hargaSewa = hargaSewa;
        this.jumlahPenumpang = jumlahPenumpang;
        this.model = model;
    }

    public String getNamaMobil() {
        return namaMobil;
    }
    public String getjumlahPenumpang() {
        return jumlahPenumpang;
    }

    public String getHargaSewa() {
        return hargaSewa;
    }
    public String getKey() {
        return key;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public void setjumlahPenumpang(String jumlahPenumpang) {
        this.jumlahPenumpang = jumlahPenumpang;
    }

    public void setHargaSewa(String hargaSewa) {
        this.hargaSewa = hargaSewa;
    }
}
