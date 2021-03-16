package com.diazs.notify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Learn;
import com.diazs.notify.Model.Materi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FormLearn extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 1;
    private static final int PICK_FILE_REQUEST = 1;

    private EditText lJudul;
    private EditText lDeskripsi;
    private Button btnImage;
    private Button btnVideo;
    private Button btnFile;
    private Button btnPost;
    private TextView namaFile;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    private ArrayList<Learn> learnArrayList;
    private LearnAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_learn);

        Materi materi = new Materi();
        lJudul = findViewById(R.id.judul);
        lDeskripsi = findViewById(R.id.deskripsi);
        btnImage = findViewById(R.id.chsPicture);
        btnVideo = findViewById(R.id.chsVideo);
        btnFile = findViewById(R.id.chsFile);
        btnPost = findViewById(R.id.post);
        namaFile = findViewById(R.id.fileName);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        if (materi.getGlobal() == true){
            setupRecycleview();
        }

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FormLearn.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectImage();
                } else {
                    ActivityCompat.requestPermissions(FormLearn.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                }
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideo();
            }
        });
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materi.setJudulMateri(lJudul.getText().toString().trim());
                materi.setDeskripsiMateri(lDeskripsi.getText().toString().trim());
                if (materi.getFotoMateri()!=null){
                    uploadImage(materi.getFotoMateri());
                } else if (materi.getVideoMateri()!=null){
                    uploadVideo(materi.getVideoMateri());
                } else if (materi.getPdfMateri()!=null){
                    uploadFile (materi.getPdfMateri());
                }
            }
        });
    }

    private void setupRecycleview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.recycler);
        adapter = new LearnAdapter(learnArrayList);
        myList.setLayoutManager(layoutManager);
        myList.setAdapter(adapter);
    }

    private void uploadFile(Uri pdfMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");
        storageReference.child("Learn").child(learn.getNamaFile()).putFile(pdfMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child(learn.getNamaFile()).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadVideo(Uri videoMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");
        storageReference.child("Learn").child(learn.getNamaFile()).putFile(videoMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child(learn.getNamaFile()).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(Uri fotoMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");

        storageReference.child("Learn").child(learn.getNamaFile()).putFile(fotoMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child(learn.getNamaFile()).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==3 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectImage();
        } else{
            Toast.makeText(FormLearn.this, "Please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImage(){
        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void selectVideo(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    public void selectFile(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    public void addArrayList(String fileName){
        String[] fileNames = fileName.split(".");
        String ext = fileNames[fileNames.length-1];

        if (ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg")){
            Learn learn = new Learn();
            learn.setType(1);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
        } else if (ext.equals(".mp4")){
            Learn learn = new Learn();
            learn.setType(2);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
        } else if (ext.equals(".pdf") || ext.equals(".docx") || ext.equals(".xlsx")){
            Learn learn = new Learn();
            learn.setType(3);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Materi materi = new Materi();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            materi.setGlobal(true);
            materi.setFotoMateri(data.getData());
            namaFile.setText(materi.getFotoMateri().getLastPathSegment());
            addArrayList(materi.getFotoMateri().getLastPathSegment());
        } else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            materi.setGlobal(true);
            materi.setVideoMateri(data.getData());
            namaFile.setText(materi.getVideoMateri().getLastPathSegment());
            addArrayList(materi.getVideoMateri().getLastPathSegment());
        } else if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            materi.setGlobal(true);
            materi.setPdfMateri(data.getData());
            namaFile.setText(materi.getPdfMateri().getLastPathSegment());
            addArrayList(materi.getPdfMateri().getLastPathSegment());
        }
    }
}
