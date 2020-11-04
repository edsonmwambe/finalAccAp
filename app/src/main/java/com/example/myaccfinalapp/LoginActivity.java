package com.example.myaccfinalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView mTv_maidLogin;
    private EditText mEmail, mPassword;
    private Button mBtnlogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mEmail = findViewById(R.id.edt_email);
        mPassword = findViewById(R.id.edt_password);
        mBtnlogin = findViewById(R.id.btn_login);
        mTv_maidLogin = findViewById(R.id.tv_register);
        mTv_maidLogin.getTextColors().getDefaultColor();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        //check if the user login
        if(user != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MaidhomeActivity.class));
        }

        //Login link

        mBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                //test
//                startActivity(new Intent(getApplicationContext(), MaidhomeActivity.class));

                //Real
                if(email.isEmpty()){
                    mEmail.setError("Enter email");
                } else if(password.isEmpty()){
                    mPassword.setError("Enter password");
                } else {
                    validate(email, password);
                }

            }
        });

        //Registration link
        mTv_maidLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTv_maidLogin.setTextColor(Color.BLUE);
                startActivity(new Intent(getApplicationContext(), MaidregistrationActivity.class));

            }
        });
    }

    //validate method
    private void validate(String userEmail, String userPassword){
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MaidhomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}