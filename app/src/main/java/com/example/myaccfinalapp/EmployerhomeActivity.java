package com.example.myaccfinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myaccfinalapp.Model.Item;
import com.example.myaccfinalapp.Model.Upload;
import com.example.myaccfinalapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmployerhomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerPeople;
    private ArrayList<Upload> data;
    private CustomAdapter adapter;
    private ProgressDialog dialog;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerhome);

        data = new ArrayList();
        mRecyclerPeople = findViewById(R.id.my_phone_list);
        mRecyclerPeople.setHasFixedSize(true);
        mRecyclerPeople.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(this,data);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        dialog.show();
        Upload p1 = new Upload("Amani Zaid", "edsonmwambe4@gmail.com", "0656288050","https://firebasestorage.googleapis.com/v0/b/maid-finder-33aee.appspot.com/o/uploads%2F1604413667202.jpg?alt=media&token=7b45c71a-db99-4dd7-97d6-8f488100627b",""+System.currentTimeMillis());
        Upload p2 = new Upload("Ally John", "edsonmwambe4@gmail.com", "0656288050","https://firebasestorage.googleapis.com/v0/b/maid-finder-33aee.appspot.com/o/uploads%2F1604413667202.jpg?alt=media&token=7b45c71a-db99-4dd7-97d6-8f488100627b",""+System.currentTimeMillis());
        Upload p3 = new Upload("Erick Seif", "edsonmwambe4@gmail.com", "0656288050","https://firebasestorage.googleapis.com/v0/b/maid-finder-33aee.appspot.com/o/uploads%2F1604413667202.jpg?alt=media&token=7b45c71a-db99-4dd7-97d6-8f488100627b",""+System.currentTimeMillis());

        data.add(p1);
        data.add(p2);
        data.add(p3);

        adapter.notifyDataSetChanged();
        dialog.dismiss();

        //From DB

//        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                data.clear();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    Upload upload= postSnapshot.getValue(Upload.class);
//                    upload.setKey(postSnapshot.getKey());
//                    data.add(upload);
//                    Collections.reverse(data);
//                }
//                Upload p1 = new Upload("Edson", "edsonmwambe4@gmail.com", "0656288050","https://firebasestorage.googleapis.com/v0/b/maid-finder-33aee.appspot.com/o/uploads%2F1604413667202.jpg?alt=media&token=7b45c71a-db99-4dd7-97d6-8f488100627b",""+System.currentTimeMillis());
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(EmployerhomeActivity.this, "Database is locked", Toast.LENGTH_SHORT).show();
//            }
//        });


        mRecyclerPeople.setAdapter(adapter);

    }
}