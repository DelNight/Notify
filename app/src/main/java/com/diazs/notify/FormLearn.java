package com.diazs.notify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
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

import com.diazs.notify.Adapter.LearnAdapter;
import com.diazs.notify.Model.Learn;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FormLearn extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private static final int PICK_FILE_REQUEST = 3;

    private EditText lJudul;
    private EditText lDeskripsi;
    private Button btnImage;
    private Button btnVideo;
    private Button btnFile;
    private Button btnPost;
    private TextView tvUsername;
    private String dbKey = null;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    Materi materi;

    private ArrayList<Learn> learnArrayList;
    private LearnAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_learn);

        materi = new Materi();
        learnArrayList = new ArrayList<>();
        lJudul = findViewById(R.id.judul);
        lDeskripsi = findViewById(R.id.deskripsi);
        btnImage = findViewById(R.id.chsPicture);
        btnVideo = findViewById(R.id.chsVideo);
        btnFile = findViewById(R.id.chsFile);
        btnPost = findViewById(R.id.post);
        tvUsername = findViewById(R.id.username);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = firebaseStorage.getReference();
        setupRecycleview();

        firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvUsername.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (materi.getGlobal() == true){
            setupRecycleview();
        }

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FormLearn.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    if (materi.getFotoMateri() != null){
                        Toast.makeText(FormLearn.this, "Hanya dapat memilih 1 foto", Toast.LENGTH_LONG).show();
                    }else {
                        selectImage();
                    }
                } else {
                    ActivityCompat.requestPermissions(FormLearn.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                }
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materi.getVideoMateri() != null){
                    Toast.makeText(FormLearn.this, "Hanya dapat memilih 1 video", Toast.LENGTH_LONG).show();
                }else {
                    selectVideo();
                }
            }
        });

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materi.getPdfMateri() != null){
                    Toast.makeText(FormLearn.this, "Hanya dapat memilih 1 dokumen", Toast.LENGTH_LONG).show();
                }else {
                    selectFile();
                }
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materi.setJudulMateri(lJudul.getText().toString().trim());
                materi.setDeskripsiMateri(lDeskripsi.getText().toString().trim());
                materi.setAuthorMateri(FirebaseAuth.getInstance().getCurrentUser().getUid());
                materi.setCreatedAt(System.currentTimeMillis());
                dbKey = firebaseDatabase.getReference("learn").push().getKey();

                if (materi.getFotoMateri()!=null){
                    uploadImage(materi.getFotoMateri());
                }
                if (materi.getVideoMateri()!=null){
                    uploadVideo(materi.getVideoMateri());
                }
                if (materi.getPdfMateri()!=null){
                    uploadFile (materi.getPdfMateri());
                }
                materi.setFotoMateri(null);
                materi.setVideoMateri(null);
                materi.setPdfMateri(null);

                materi.setIdMateri(dbKey);
                firebaseDatabase.getReference("learn").child(dbKey).setValue(materi).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FormLearn.this, "Materi berhasil di posting", Toast.LENGTH_SHORT).show();
                    }
                });

                kondisiAwal();
            }
        });
    }

    private void setupRecycleview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.recycler);
        adapter = new LearnAdapter(learnArrayList, this);
        myList.setLayoutManager(layoutManager);
        myList.setAdapter(adapter);
    }

    private void uploadFile(Uri pdfMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        storageReference.child("learn").child(learn.getNamaFile()+"."+getFileExtension(pdfMateri)).putFile(pdfMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("learn");
                        progressDialog.dismiss();
                        databaseReference.child(dbKey).child("fileUrl").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting file berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting file gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
            }
        });
    }

    private void uploadVideo(Uri videoMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        storageReference.child("learn").child(learn.getNamaFile()+"."+getFileExtension(videoMateri)).putFile(videoMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("learn");
                        progressDialog.dismiss();
                        databaseReference.child(dbKey).child("videoUrl").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting video berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting video gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gagal", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
            }
        });
    }

    private void uploadImage(Uri fotoMateri) {
        Learn learn = new Learn();
        learn.setNamaFile(System.currentTimeMillis()+"");
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        storageReference.child("learn").child(learn.getNamaFile()+"."+getFileExtension(fotoMateri)).putFile(fotoMateri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("learn");
                        progressDialog.dismiss();
                        databaseReference.child(dbKey).child("imageUrl").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(FormLearn.this, "Posting gambar berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(FormLearn.this, "Posting gambar gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FormLearn.this, "Posting gambar gagal", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
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

    public void addArrayList(String fileName, String ext){
        System.out.println("Debug Oi : "+ ext);
        if (ext.equals("images/*")){
            Toast.makeText(this, "Gambar Berhasil Dipilih!", Toast.LENGTH_SHORT).show();
            Learn learn = new Learn();
            learn.setType(1);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
            adapter.notifyDataSetChanged();
        } else if (ext.equals("video/mp4")){
            Toast.makeText(this, "Video Berhasil Dipilih!", Toast.LENGTH_SHORT).show();
            Learn learn = new Learn();
            learn.setType(2);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
            adapter.notifyDataSetChanged();
        } else if (ext.equals("application/pdf") || ext.equals("application/*")){
            Toast.makeText(this, "Dokumen Berhasil Dipilih!", Toast.LENGTH_SHORT).show();
            Learn learn = new Learn();
            learn.setType(3);
            learn.setNamaFile(fileName);
            learnArrayList.add(learn);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            Uri uri = data.getData();
            String result;
            int cut;
            materi.setGlobal(true);
            result = uri.getPath();
            cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
            ContentResolver cr = this.getContentResolver();
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                materi.setGlobal(true);
                materi.setFotoMateri(data.getData());
                addArrayList(result, cr.getType(uri) );
            } else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                materi.setGlobal(true);
                materi.setVideoMateri(data.getData());
                addArrayList(result , cr.getType(uri));
            } else if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                materi.setGlobal(true);
                materi.setPdfMateri(data.getData());
                addArrayList(result , cr.getType(uri));
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void kondisiAwal(){
        learnArrayList.clear();
        adapter.notifyDataSetChanged();
        lJudul.setText("");
        lDeskripsi.setText("");
    }
}