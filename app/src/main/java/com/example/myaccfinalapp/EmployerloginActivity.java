package com.example.myaccfinalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerloginActivity extends AppCompatActivity {
    TextView mTvRegister;
    EditText mEtEmail, mEtPassword;
    Button mBtnlogin;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerlogin);

        getSupportActionBar().hide();

        mEtEmail = findViewById(R.id.edt_email);
        mEtPassword = findViewById(R.id.edt_password);
        mBtnlogin = findViewById(R.id.btn_login);


        mTvRegister = findViewById(R.id.tv_register);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvRegister.setTextColor(Color.BLUE);
                startActivity(new Intent(getApplicationContext(), EmployerregistrationActivity.class));
            }
        });

        mBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                startActivity(new Intent(getApplicationContext(), EmployerhomeActivity.class));
//Validate fields


//                if(email.isEmpty()){
//                    mEtEmail.setError("enter email");
//
//                }else if(password.isEmpty()){
//                    mEtPassword.setError("Enter password");
//                } else{
//                    validate(email.trim(), password.trim());
//                }
            }
        });
    }

    private void validate(String userEmail, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(EmployerloginActivity.this, EmployerhomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EmployerloginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}