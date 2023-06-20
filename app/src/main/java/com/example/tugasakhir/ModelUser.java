package com.example.tugasakhir;

public class ModelUser {
    private String nama;
    private String pass;
    private String email;
    private String TTL;
    private String noHP;
    private String alamat;
//    private String avatar;

    public ModelUser(String nama, String pass, String email, String TTL, String noHP, String alamat) {
        this.nama = nama;
        this.pass = pass;
        this.email = email;
        this.TTL = TTL;
        this.noHP = noHP;
        this.alamat = alamat;
//        this.avatar = avatar;
    }

//    public modelUser(String nama, String pass, String email, String TTL, String noHP, String alamat) {
//        this.nama = nama;
//        this.pass = pass;
//        this.email = email;
//        this.TTL = TTL;
//        this.noHP = noHP;
//        this.alamat = alamat;
//    }

    public ModelUser(){}

//    public String getAvatar() {
//        return avatar;
//    }

    public String getNama() {
        return this.nama;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public String getNoHP() {
        return this.noHP;
    }

    public String getTTL() {
        return this.TTL;
    }

//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public void setTTL(String TTL) {
        this.TTL = TTL;
    }
}



