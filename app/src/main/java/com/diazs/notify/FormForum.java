package com.diazs.notify;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class FormForum extends AppCompatActivity {
    EditText spinner;
    TextView tvNama;
    DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;
    EditText inpJudul, inpDeskripsi;
    ImageView imgGambar;
    Button btnGambar;
    Button btnSubmit;
    DatabaseReference dbForum;
    LottieAnimationView loading;
    private StorageReference reference;
    private ProgressBar progressBar;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_forum);
//        loading = findViewById(R.id.loading_bell);
//        loading.playAnimation();
//        loading.setVisibility(View.VISIBLE);
        tvNama = findViewById(R.id.name);


        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvNama.setText(user.getNama());
//                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inpJudul = findViewById(R.id.judulforum);
        inpDeskripsi = findViewById(R.id.deskripsiforum);
        imgGambar = findViewById(R.id.img_forum);
        btnGambar = findViewById(R.id.pilih_img);
        btnSubmit = findViewById(R.id.btn_submit);
        reference = FirebaseStorage.getInstance().getReference();

        btnGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menerapkan kejadian saat tombol pilih gambar di klik
                getImage();
            }
        });
        dbForum = FirebaseDatabase.getInstance().getReference("forum");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forum forum = new Forum();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formatdate = sdf.format(c);
                imgGambar.setDrawingCacheEnabled(true);
                imgGambar.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgGambar.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //Mengkompress bitmap menjadi JPG dengan kualitas gambar 100%
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                //Lokasi lengkap dimana gambar akan disimpan
                String namaFile = UUID.randomUUID()+".jpg";
                String pathImage = "File/"+namaFile;

                UploadTask uploadTask = reference.child(pathImage).putBytes(bytes);
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(FormForum.this, "Uploading Berhasil", Toast.LENGTH_SHORT).show();
                        String judulPostingan = inpJudul.getText().toString();
                        String deskripsi =  inpDeskripsi.getText().toString();
                        FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
                        forum.setAuthor(aut.getUid());
                        forum.setJudul(judulPostingan);
                        forum.setDeskripsi(deskripsi);
                        forum.setTanggalUpload(formatdate);
                        String key = dbForum.push().getKey();
                        forum.setIdForum(key);
                        forum.setLinkImg(taskSnapshot.getUploadSessionUri().toString());
                        dbForum.child(key).setValue(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FormForum.this,"Forum Berhasil Dipost", Toast.LENGTH_LONG).show();
                                inpJudul.setText("");
                                inpDeskripsi.setText("");

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(FormForum.this, "Uploading Gagal", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.VISIBLE);
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressBar.setProgress((int) progress);
                            }
                        });
            }
        });
    }
    private void getImage(){
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //Mengambil gambar dari Kemara ponsel
                                Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                break;

                            case 1:
                                //Mengambil gambar dari galeri
                                Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(imageIntentGallery, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgGambar.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgGambar.setImageURI(uri);
                }
                break;
        }
    }

}
