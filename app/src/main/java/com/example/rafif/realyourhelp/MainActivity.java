package com.example.rafif.realyourhelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonDaftar;
    private EditText editTextEmail;
    private EditText editTextPasssword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));

        }

        progressDialog = new ProgressDialog(this);

        buttonDaftar = (Button) findViewById(R.id.buttonDaftar);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPasssword = (EditText) findViewById(R.id.editTextPassword);

        buttonDaftar.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password =  editTextPasssword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Email Requires");
            Toast.makeText(this, "Silakan Masukkan Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            editTextPasssword.setError("Password Requires");
            Toast.makeText(this, "Silakan Masukkan Password", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password terlalu pendek, minimal 6 karakter", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), BerandaActivity.class));
                        } else  {
                            Toast.makeText(MainActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonDaftar){
            registerUser();
        }
    }
}

