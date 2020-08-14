package com.example.rafif.realyourhelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HasilActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databasePasien;
    private DatabaseReference databaseSolusi;

    private DatabaseReference databaseHasil;
    ListView listViewPasien;
    ListView listViewHasil;

    List<Pasien> pasienList;
    List<Hasil> hasilList;
    private String hasilId;
//    List<Hasil> hasilList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//        databaseHasil = FirebaseDatabase.getInstance().getReference("hasil");
        databasePasien = FirebaseDatabase.getInstance().getReference("pasien");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        listViewHasil = (ListView) findViewById(R.id.listViewHasil);
//        listViewHasil = (ListView) findViewById(R.id.listViewHasil);

        pasienList = new ArrayList<>();
//        hasilList = new ArrayList<>();

        listViewHasil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Hasil hasil = hasilList.get(i);

                 Pasien pasien = pasienList.get(i);
//                showUpdateDialog(hasil.getSolusi(), hasil.getTanggal(), hasil.getName());
                showUpdateDialog(pasien.getPasienId(), pasien.getSolusi(), pasien.getTanggal(), pasien.getJam(), pasien.getName());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePasien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pasienList.clear();

                for (DataSnapshot hasilSnapshot : dataSnapshot.getChildren()) {
                    Pasien pasien = hasilSnapshot.getValue(Pasien.class);
                    pasienList.add(pasien);
                }
                InfoList adapter = new InfoList(HasilActivity.this,pasienList);
                listViewHasil.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String pasienId, String solusi, String tanggal, String jam, String name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_update, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextAge = (EditText) dialogView.findViewById(R.id.editTextAge);

//        final Button buttonLihat = (Button) dialogView.findViewById(R.id.buttonLihat);
//        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

//        dialogBuilder.setTitle("Updating Pasien"+" "+name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editTextName.getText().toString().trim();
//                String age = editTextAge.getText().toString();
//
//                if(TextUtils.isEmpty(name)){
//                    editTextName.setError("Name Requires");
//                    return;
//                }
//                if(TextUtils.isEmpty(age)){
//                    editTextAge.setError("Age Requires");
//                    return;
//                }
//                updatePasien(pasienId, name, age);
//                alertDialog.dismiss();
//            }
//        });
//
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePasien(pasienId);
                alertDialog.dismiss();
            }
        });

//        buttonLihat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showSolusi(hasilId);
//            }
//        });
    }

//    private void showSolusi(String id) {
//        startActivity(new Intent(this, LihatActivity.class));
//    }

    private void deletePasien(String pasienId) {
        DatabaseReference drPasien = FirebaseDatabase.getInstance().getReference("pasien").child(pasienId);

        drPasien.removeValue();

        Toast.makeText(this, "Data Pasien Dihapus", Toast.LENGTH_SHORT).show();
    }

//    private void updatePasien(String id, String name, String age) {
//
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pasien").child(id);
//
////        Pasien pasien = new Pasien(id, name, age);
//
////        databaseReference.setValue(pasien);
//
//        Toast.makeText(this, "Sukses update pasien", Toast.LENGTH_SHORT).show();
//    }

}
