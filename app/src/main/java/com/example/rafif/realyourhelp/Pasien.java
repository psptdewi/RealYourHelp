package com.example.rafif.realyourhelp;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class Pasien {
    public String pasienId;
    public String name;
    public String age;
    public String jam;
    public String tanggal;
    public String solusi;
//    public Hasil hasil = new Hasil();

    public Pasien() {
    }

    public Pasien(String pasienId, String name, String age, String jam, String tanggal, String solusi) {
        this.pasienId = pasienId;
        this.name = name;
        this.age = age;
        this.jam = jam;
        this.tanggal = tanggal;
        this.solusi = solusi;
    }

    public String getJam() {
        return jam;
    }

    public String getPasienId() {
        return pasienId;
    }

    public String getName() {
        return name;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getSolusi() {
        return solusi;
    }

    public String getAge() {
        return age;
    }

    public void setPasienId(String pasienId) {
        this.pasienId = pasienId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public void setAge(String age) {
        this.age = age;
    }
}