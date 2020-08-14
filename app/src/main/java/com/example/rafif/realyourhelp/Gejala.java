package com.example.rafif.realyourhelp;

public class Gejala {

    public String nama;
    public String id;
    boolean isSelected;

    public Gejala() {

    }

    public Gejala(String nama, String id, boolean isSelected) {
        this.nama = nama;
        this.id = id;
        this.isSelected = isSelected;
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}