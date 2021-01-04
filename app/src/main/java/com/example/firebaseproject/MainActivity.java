package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

private Button save,choose,display_recycle;
private EditText setName;
private ImageView image_view;
public static final int REQUEST_IMAGE = 1;
private StorageTask uploadTask;
private Uri imageUri;
private FirebaseAuth firebaseAuth;
private DatabaseReference firebaseDB;
private StorageReference firebaseSt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_view = findViewById(R.id.imageView_display);
        save = findViewById(R.id.button_save);
        choose = findViewById(R.id.button_choose);
        display_recycle = findViewById(R.id.button_display);
        setName = findViewById(R.id.editTextSearch);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance().getReference("Uploads");
        firebaseSt = FirebaseStorage.getInstance().getReference("Uploads");

         display_recycle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity( new Intent(MainActivity.this,AllImagesPage.class));
                 finish();
             }
         });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileToChooseImage();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask!=null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"The task is in progress..",Toast.LENGTH_LONG).show();
                }
                  else{
                    saveImageStore();
                }

            }
        });
      }


    private String getFileExtension(Uri uri)
    {
        String extension;
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        extension= mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }


    private void saveImageStore() {
    String nameImage = setName.getText().toString().trim();
    if(nameImage.isEmpty()){
      setName.setError("Image name is must");
      setName.requestFocus();
      return;
    }
    StorageReference srf = firebaseSt.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
    srf.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content


                        Task<Uri > uriTask =taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        UploadImage uploadImage = new UploadImage(nameImage, downloadUri.toString());
                        String databaseUploadKey =  firebaseDB.push().getKey();
                        firebaseDB.child(databaseUploadKey).setValue(uploadImage);
                        Toast.makeText(getApplicationContext(),"This image is stored successfully..",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(),"Failed to restore..",Toast.LENGTH_LONG).show();
                    }
                });

    }


    private void openFileToChooseImage() {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult(intent,REQUEST_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(image_view);
        }
    }
}