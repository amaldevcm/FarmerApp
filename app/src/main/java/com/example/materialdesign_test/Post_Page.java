package com.example.materialdesign_test;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Post_Page extends AppCompatActivity {
    private ImageView image;
    private Button from_ph;
    private Button post;
    private int PICK_IMAGE=1;
    private EditText prdname,price,desc;
    Uri uri;
    FirebaseFirestore db;
    StorageReference Folder;
    StorageReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        image=(ImageView)findViewById(R.id.image);
        from_ph=(Button)findViewById(R.id.imageph);
        post=(Button)findViewById(R.id.Post);
        prdname=(EditText)findViewById(R.id.ProductName);
        price=(EditText)findViewById(R.id.Price);
        desc=(EditText)findViewById(R.id.Description);
        Folder= FirebaseStorage.getInstance().getReference("images");
        from_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(gallery,PICK_IMAGE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri=data.getData();
            try {
                Bitmap map= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                image.setImageBitmap(map);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadfile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        StorageReference ref=Folder.child("6382444820").child(dtf.format(now));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Post_Page.this, "upload succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}