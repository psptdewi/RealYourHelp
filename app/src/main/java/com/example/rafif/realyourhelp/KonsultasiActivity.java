package com.example.rafif.realyourhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KonsultasiActivity extends AppCompatActivity {

    private String pasienIid;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databasePasien;
    private EditText editTextName;
    private EditText editTextAge;
    private ListView listView;
    private MyAdapter adapter;
    private float treshold = (float) 0.75;
    private float min = 0;
    private ArrayList<Gejala> dataGejalas;
    private String id;
    private String kode_pasien;

    private String pasienId, namaPasien, umurPasien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databasePasien = FirebaseDatabase.getInstance().getReference("pasien");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Menerima data yang dikirimkan oleh DataPasien.java
        pasienId = getIntent().getStringExtra("id");
        namaPasien = getIntent().getStringExtra("name");
        umurPasien = getIntent().getStringExtra("age");


    }

    @Override
    protected void onStart() {
        super.onStart();

        listView = (ListView) findViewById(R.id.gejala_list);
//        listView.setLayoutManager(new LinearLayoutManager(this));

        dataGejalas = new ArrayList<Gejala>();

        databasePasien = FirebaseDatabase.getInstance().getReference().child("gejala");
        databasePasien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Gejala d = dataSnapshot1.getValue(Gejala.class);
                    dataGejalas.add(d);
                }
                adapter = new MyAdapter(KonsultasiActivity.this, dataGejalas);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(KonsultasiActivity.this, "Upss... Gagal mencari data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cari_tahu(View view) {


        if (adapter.gejalanyas.size() > 0) {
            final List<String> kabehId = new ArrayList<>();
            final Map<String, Float> hasilKemiripan = new HashMap<>();

            for (Gejala d : adapter.gejalanyas) {
                kabehId.add(d.getId());
            }

            DatabaseReference kasus = FirebaseDatabase.getInstance().getReference().child("kasus");
            kasus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataKasus : dataSnapshot.getChildren()) {
                        float kemiripan = 0;
                        for (DataSnapshot gejalaKasus : dataKasus.child("gejala").getChildren()) {
                            if (kabehId.contains(gejalaKasus.getValue().toString())) {
                                kemiripan++;
                            }
                        }

                        float pembagi = Math.max((float) (dataKasus.child("gejala").getChildrenCount()), kabehId.size());

                        float hasil_bagi = kemiripan / pembagi;

                        hasilKemiripan.put(dataKasus.getKey(), hasil_bagi);
                    }

                    for (Map.Entry<String, Float> entry : hasilKemiripan.entrySet()) {
                        if (entry.getValue() > min) {
                            min = entry.getValue();
                            id = entry.getKey();
                        }
                    }

                    if (id != null) {
                        System.out.println(id + " - " + min);
                        gejala(id, min);
                    } else {
                        Toast.makeText(KonsultasiActivity.this, "Kasus yang mirip tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(KonsultasiActivity.this, "Upss... Gagal mencari data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(KonsultasiActivity.this, "Anda belum mengisi gejala yang anak anda rasakan", Toast.LENGTH_SHORT).show();
        }
    }

    private void gejala(final String id, final float min) {
        final String id_kasus = id;
        final int nilai_kemiripan = (int) (min * 100);

        databasePasien = FirebaseDatabase.getInstance().getReference().child("gejala");

        DatabaseReference refSakit = FirebaseDatabase.getInstance().getReference().child("kasus").child(id_kasus);
        refSakit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String diagnosaGejala = dataSnapshot.child("solusi").getValue().toString();

                if (diagnosaGejala.equals("Perlunya kasih sayang & perhatian orang tua dalam hal apapun dan orang tua menjadi tempat curhat untuk anak-anaknya") || diagnosaGejala.equals("Memberikan pembelajaran agama dan memberikan teguran secara tegas") || diagnosaGejala.equals("Perlunya memberikan pembelajaran agama yang dilakukan sejak dini dan orang tua harus mendukung hobi & cita-cita anaknya")
                        || diagnosaGejala.equals("Orang tua menjadi tempat curhat untuk anak-anaknya dan membimbing dia ketika ia sedang menghadapi masalah") || diagnosaGejala.equals("Memberi dukungan kepada anak-anaknya dan orang tua menjadi tempat curhat untuk anak-anaknya") || diagnosaGejala.equals("Orang tua dapat meluangkan waktu untuk anaknya dan anak menceritakan masalah yang dihadapi & memberikan solusi")
                        || diagnosaGejala.equals("Perlunya memberikan pembelajaran agama dan dukungan kepada anak") || diagnosaGejala.equals("Memberikan dukungan kepada anak dan perhatian juga kasih sayang kepada  anaknya") || diagnosaGejala.equals("Perlunya memberi kasih sayang dan orang tua menjadi tempat curhat untuk anak-anaknya") || diagnosaGejala.equals("Orang tua mendengarkan keinginan anak dan memberikan teguran secara tegas")) {
                    if (nilai_kemiripan > treshold * 100) {
                        DatabaseReference save_hasil = FirebaseDatabase.getInstance().getReference().child("hasil").push();

//                        save_hasil.child("id").getKey();
//                        save_hasil.child("umur").setValue(pasienData);
//                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        SimpleDateFormat format_tanggal = new SimpleDateFormat("dd MMMM yyyy");
                        SimpleDateFormat format_waktu = new SimpleDateFormat("HH:MM:SS");
                        Date date = new Date();

                        String tanggal = format_tanggal.format(date);
                        String waktu = format_waktu.format(date);

//                        save_hasil.child("solusi").setValue(diagnosaGejala);
//                        save_hasil.child("tanggal").setValue(tanggal);
//                        save_hasil.child("jam").setValue(waktu);


                        Pasien pasien = new Pasien(
                                pasienId,
                                namaPasien,
                                umurPasien,
                                waktu,
                                tanggal,
                                diagnosaGejala
                        );

                        DatabaseReference simpanPasien = FirebaseDatabase.getInstance().getReference().child("pasien");
                        simpanPasien.push().setValue(pasien);

//                        FirebaseDatabase.getInstance().getReference().child(userId).getKey();
//                        save_hasil.child("kode_pasien").setValue(userId);

                        Intent intent = new Intent(KonsultasiActivity.this, HasilActivity.class);
                        Bundle extras = new Bundle();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(KonsultasiActivity.this, DataPasien.class);
                        Bundle extras = new Bundle();
//                        extras.putString("sakitMirip", diagnosaGejala);
//                        extras.putString("catatan", "Gejala yang anda alami mengarah pada diagnosis untuk penyakit " + diagnosaGejala + " dengan nilai diagnosis kemiripan sebesar " + nilai_kemiripan + "%");
//                        intent.putExtras(extras);
                        startActivity(intent);
                        Toast.makeText(KonsultasiActivity.this, "Mohon maaf kami belum menemukan solusi dari gejala yang anda pilih", Toast.LENGTH_SHORT).show();
//                        String kode = getIntent().getStringExtra("id")
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(KonsultasiActivity.this, "Upss... data tidak ditemukan.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
