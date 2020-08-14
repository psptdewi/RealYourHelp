package com.example.rafif.realyourhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataPasien extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databasePasien;
    private EditText editTextName;
    private EditText editTextAge;
    private Button buttonSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pasien);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databasePasien = FirebaseDatabase.getInstance().getReference("pasien");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPasien();
            }
        });

    }

    private void simpanPasien() {
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString();

        if (!name.isEmpty()) {
            String id = databasePasien.push().getKey();
            startActivity(new Intent(this, KonsultasiActivity.class)
                    .putExtra("id", id)
                    .putExtra("name", name)
                    .putExtra("age",age)
            );

            Toast.makeText(this, "Data disimpan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "masukkan nama", Toast.LENGTH_LONG).show();
        }

    }
}
