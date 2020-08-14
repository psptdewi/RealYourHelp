package com.example.rafif.realyourhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BerandaActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
//    private Button buttonLogout;
    private Button buttonKonsultasi;
    private Button buttonHasilKonsultasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Selamat Datang\n"+" "+user.getEmail());
//        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonKonsultasi = (Button) findViewById(R.id.buttonKonsultasi);
        buttonHasilKonsultasi = (Button) findViewById(R.id.buttonHasilKonsultasi);

//        buttonLogout.setOnClickListener(this);
        buttonKonsultasi.setOnClickListener(this);
        buttonHasilKonsultasi.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_option, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }


    @Override
    public void onClick(View view) {
//        if(view == buttonLogout){
//            firebaseAuth.signOut();
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
        if(view == buttonKonsultasi){
            startActivity(new Intent(this, DataPasien.class));
        }
        if(view == buttonHasilKonsultasi){
            startActivity(new Intent(this, HasilActivity.class));
        }
    }
}
