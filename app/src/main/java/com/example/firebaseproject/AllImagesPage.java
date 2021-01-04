package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllImagesPage extends AppCompatActivity {
  private   RecyclerView recyclerView;
  private   GridLayoutManager gridLayoutManager;
  private   ImageAdapterRecycle imageAdapterRecycle;
  private   List<UploadImage> uploadImageList = new ArrayList<>();
  private DatabaseReference fdb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_images_page);

        recyclerView = findViewById(R.id.recycle_all_images);
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        fdb =FirebaseDatabase.getInstance().getReference("Uploads");
    }

    @Override
    protected void onStart() {
        super.onStart();
        fdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                  UploadImage uploadImage = dataSnapshot.getValue(UploadImage.class);
                  uploadImageList.add(uploadImage);
                }
                imageAdapterRecycle = new ImageAdapterRecycle(getApplicationContext(),uploadImageList);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(imageAdapterRecycle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Cancelled.. ERROR: " +error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}