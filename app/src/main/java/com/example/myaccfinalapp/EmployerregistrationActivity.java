package com.example.myaccfinalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myaccfinalapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerregistrationActivity extends AppCompatActivity {
    private EditText eTname,eTphone,eTemail,eTpassword, eTconf_password;
    private Button mBtnregister;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerregistration);
        getSupportActionBar().hide();

        eTname = findViewById(R.id.edt_name);
        eTphone = findViewById(R.id.edt_phone);
        eTemail = findViewById(R.id.edt_email);
        eTpassword = findViewById(R.id.edt_password);
        eTconf_password = findViewById(R.id.edt_confpassword);
        mBtnregister = findViewById(R.id.btn_employer_reg);
//
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        mBtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    // save data to db
                    final String user_name = eTname.getText().toString().trim();
                    final String user_email = eTemail.getText().toString().trim();
                    final String user_phone = eTphone.getText().toString().trim();
                    final String user_password = eTpassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //Save User

                                    User user = new User();
                                    user.setEmail(user_email);
                                    user.setName(user_name);
                                    user.setPhone(user_phone);
                                    user.setPassword(user_password);

                                    //use email to Key
                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(EmployerregistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EmployerregistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EmployerregistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

//        mBtnregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if(validate()){
//                    // save data to db
//                    String user_name = eTname.getText().toString().trim();
//                    String user_email = eTemail.getText().toString().trim();
//                    String user_phone = eTphone.getText().toString().trim();
//                    String user_password = eTpassword.getText().toString().trim();
//                Log.d("EMAIL", "onClick: "+user_email);
//                Log.d("PASSWORD", "onClick: "+user_password);
//
////                Realtime DB
//
////                long time = System.currentTimeMillis();
////                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users/"+time);
////                User user = new User(user_email, user_password, user_name, user_phone);
////                ref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        if(task.isSuccessful()){
////                            Toast.makeText(EmployerregistrationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(EmployerregistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
////                        }
////
////                    }
////                });
//
//                //Firebase Auth
//
//
//                firebaseAuth.createUserWithEmailAndPassword("edsomwambe2@gmail.com", "njsudffnsfffsfsdfr")
//                            .addOnCompleteListener(EmployerregistrationActivity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Log.d("TASK", "onComplete: "+task.isSuccessful());
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(EmployerregistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//
//                                        startActivity(new Intent(EmployerregistrationActivity.this, EmployerhomeActivity.class));
//                                    } else{
//                                        Toast.makeText(EmployerregistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//
////                }
//            }
//        });



    }

    private Boolean validate(){
        Boolean result = false;

        String name = eTname.getText().toString();
        String phone = eTphone.getText().toString();
        String email = eTemail.getText().toString();
        String password = eTpassword.getText().toString();
        String confirm_password = eTconf_password.getText().toString();

        if(name.isEmpty()){
            eTname.setError("Enter name");
        } else if(phone.isEmpty()){
            eTphone.setError("Enter phone");
        } else if(email.isEmpty()){
            eTemail.setError("Enter email");

        } else if(password.isEmpty()){
            eTpassword.setError("Enter password");
        } else if(confirm_password.isEmpty()){
            eTconf_password.setError("confirm password");
        } else if(!password.equals(confirm_password)){
            Toast.makeText(this, "confirm password does not match password", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;

    }
}