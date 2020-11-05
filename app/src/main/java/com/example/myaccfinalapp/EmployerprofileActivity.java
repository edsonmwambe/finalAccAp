package com.example.myaccfinalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.myaccfinalapp.Adapter.MaidCustomAdapter;
import com.example.myaccfinalapp.Model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class EmployerprofileActivity extends AppCompatActivity {

    private Button mLogout;
    private RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Upload> data;
    private MaidCustomAdapter adapter;
    private ProgressDialog dialog;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerprofile);


        firebaseAuth = FirebaseAuth.getInstance();

        data = new ArrayList();
        mRecyclerView = findViewById(R.id.recyclerViewEmployer);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MaidCustomAdapter(this, data);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        dialog.show();
        Upload p1 = new Upload("Amani Zaid", "edsonmwambe4@gmail.com", "0656288050","https://firebasestorage.googleapis.com/v0/b/maid-finder-33aee.appspot.com/o/uploads%2F1604413667202.jpg?alt=media&token=7b45c71a-db99-4dd7-97d6-8f488100627b",""+System.currentTimeMillis());

        data.add(p1);

        adapter.notifyDataSetChanged();
        dialog.dismiss();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employer_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:{
                LogOut();
            }

            case R.id.employer_profile:{
                startActivity(new Intent(EmployerprofileActivity.this, EmployerprofileActivity.class));
            }

            case R.id.settings:{
                Toast.makeText(EmployerprofileActivity.this, "Working on it", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void LogOut(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(EmployerprofileActivity.this, EmployerloginActivity.class));

    }
}