package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllImagesPage extends AppCompatActivity {
  private   RecyclerView recyclerView;
  private   GridLayoutManager gridLayoutManager;
  private   ImageAdapterRecycle imageAdapterRecycle;
  private   List<UploadImage> uploadImageList = new ArrayList<>();
  private   DatabaseReference fdb ;
  private FirebaseStorage fB_Storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_images_page);
        fB_Storage = FirebaseStorage.getInstance();
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
                uploadImageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                  UploadImage uploadImage = dataSnapshot.getValue(UploadImage.class);
                  uploadImage.setKey(dataSnapshot.getKey());
                  uploadImageList.add(uploadImage);
                }
                imageAdapterRecycle = new ImageAdapterRecycle(getApplicationContext(),uploadImageList);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(imageAdapterRecycle);

                imageAdapterRecycle.itemRecycleClick(new ImageAdapterRecycle.OnItemRecycleImageListener() {
                    @Override
                    public void onItemClickImage(int position) {
                           String textImage = uploadImageList.get(position).getImageName();
                           Toast.makeText(getApplicationContext()," Image : " +textImage +"\n"
                                   +" Entry Serial : " + position,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onViewImageItem(int position) {
                        String textImage = uploadImageList.get(position).getImageName();
                        Toast.makeText(getApplicationContext()," Name : " +textImage +"\n"
                                +" But this feature is still updating",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteItem(int position) {

                        UploadImage selectedImage = uploadImageList.get(position);
                       final String key = selectedImage.getKey();
                        StorageReference storageReference = fB_Storage.getReferenceFromUrl(selectedImage.getImageUrl());
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                             fdb.child(key).removeValue();
                             Toast.makeText(getApplicationContext(),"This image has been deleted",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onMarkImportant(int position) {
                        Toast.makeText(getApplicationContext(),"This option will work soon",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Cancelled.. ERROR: " +error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}