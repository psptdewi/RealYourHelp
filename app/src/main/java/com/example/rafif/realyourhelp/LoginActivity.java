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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button email_sign_in_button;
    private Button register_button;
    private EditText editTextEmail;
    private EditText editTextPasssword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));

        }

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPasssword = (EditText) findViewById(R.id.editTextPassword);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        register_button = (Button) findViewById(R.id.register_button);

        email_sign_in_button.setOnClickListener(this);
        register_button.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPasssword.getText().toString().trim();

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

        progressDialog.setMessage("Sign In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), BerandaActivity.class));
                        } else  {
                            Toast.makeText(LoginActivity.this, "Email dan Password yang anda masukkan salah", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == email_sign_in_button){
            userLogin();
        }

        if(view == register_button){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
