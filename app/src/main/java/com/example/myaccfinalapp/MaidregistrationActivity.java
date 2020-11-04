package com.example.myaccfinalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myaccfinalapp.Model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class MaidregistrationActivity extends AppCompatActivity {
    private EditText eTname, eTphone, eTemail,  eTpassword, eTconf_password;
    private Button mBtnregister;
    private TextView userLogin, UploadImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseAuth fireBaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maidregistration);

        eTname = findViewById(R.id.edt_name);
        eTphone = findViewById(R.id.edt_phone);
        eTemail = findViewById(R.id.edt_email);
        eTpassword = findViewById(R.id.edt_password);
        eTconf_password = findViewById(R.id.edt_confpassword);
        mBtnregister = findViewById(R.id.btn_maid_reg);
        userLogin = findViewById(R.id.tv_login);

        mImageView = findViewById(R.id.imageView);


        UploadImage = findViewById(R.id.tv_upload);

        fireBaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });


        mBtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String email = eTemail.getText().toString().trim();
                    String password = eTpassword.getText().toString().trim();

                    fireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MaidregistrationActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                uploadFile();
                            } else {
                                Toast.makeText(MaidregistrationActivity.this, "User not created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

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
        }  else if(password.isEmpty()){
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

    //Open a file chooser
    public void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //Set the chosen image to the imageview
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null){
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).into(mImageView);

        }
    }

    //Getting a file extention
    private  String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Uploading the image and the name
    private void uploadFile(){
        if (mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Thread delay = new Thread(){
                        @Override
                        public void run() {
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    delay.start();
                    Toast.makeText(MaidregistrationActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    String user_name = eTname.getText().toString().trim();
                    String user_phone = eTphone.getText().toString().trim();
                    String user_email = eTemail.getText().toString().trim();
                    String user_password = eTpassword.getText().toString().trim();
                    Upload upload = new Upload(user_name,user_email,user_phone,taskSnapshot.getDownloadUrl().toString(),String.valueOf(System.currentTimeMillis()));
                    String uploadID = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(System.currentTimeMillis()+"").setValue(upload)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(MaidregistrationActivity.this, "Real time DB", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MaidhomeActivity.class));

                                    } else{
                                        Toast.makeText(MaidregistrationActivity.this, "Real DB failed", Toast.LENGTH_SHORT).show();
                                        Log.d("DB", "onFailed: ");
                                    }

                                }
                            });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MaidregistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "No file selected for upload", Toast.LENGTH_SHORT).show();
        }
    }

}