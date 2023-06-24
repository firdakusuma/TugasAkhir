package com.example.tugasakhir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Mobil implements Parcelable {

    private String namaMobil;
    private String jumlahPenumpang;
    private String hargaSewa;
    private String model;
    private String key;

    public Mobil () {

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.namaMobil);
        parcel.writeString(this.hargaSewa);
        parcel.writeString(this.model);
        parcel.writeString(this.jumlahPenumpang);
    }

    private Mobil (Parcel in) {
        this.key = in.readString();
        this.namaMobil = in.readString();
        this.hargaSewa = in.readString();
        this.model = in.readString();
        this.jumlahPenumpang = in.readString();
    }

    public static final Creator<Mobil> CREATOR = new Creator<Mobil>(){

        @Override
        public Mobil createFromParcel(Parcel parcel) {
            return new Mobil(parcel);
        }

        @Override
        public Mobil[] newArray(int i) {
            return new Mobil[i];
        }
    };
}
