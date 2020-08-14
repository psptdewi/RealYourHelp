package com.example.rafif.realyourhelp;

public class Hasil {
//    public String id;
    public String name;
    public String tanggal;
    public String solusi;

    public Hasil() {

    }

    public Hasil(String solusi, String tanggal, String name) {
//        this.id = id;
        this.solusi = solusi;
        this.name = name;
        this.tanggal = tanggal;
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
}
