package com.diazs.notify;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import androidx.core.app.ActivityCompat;

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
    private EditText spinner;
    private TextView tvNama;
    private DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;
    private EditText inpJudul, inpDeskripsi;
    private ImageView imgGambar;
    private Button btnGambar;
    private Button btnSubmit;
    private Bitmap bitmap;
    private DatabaseReference dbForum;
    private LottieAnimationView loading;
    private Uri imageUri;
    private StorageReference reference;
    private ProgressBar progressBar;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_forum);
        loading = findViewById(R.id.loading_bell);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);
        tvNama = findViewById(R.id.name);
        progressBar = findViewById(R.id.progressBar);


        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvNama.setText(user.getNama());
                loading.setVisibility(View.GONE);
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
        reference = FirebaseStorage.getInstance().getReference("Forum");

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
                imgGambar.setDrawingCacheEnabled(true);
                imgGambar.buildDrawingCache();

                final StorageReference fileRef = reference.child(System.currentTimeMillis() + UUID.randomUUID().toString() + "." + getFileExtension(imageUri == null ? getImageUri(bitmap) : imageUri));

                fileRef.putFile(imageUri == null ? getImageUri(bitmap) : imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(FormForum.this, "Uploading Berhasil", Toast.LENGTH_SHORT).show();
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String formatdate = sdf.format(c);
                            String judulPostingan = inpJudul.getText().toString();
                            String deskripsi =  inpDeskripsi.getText().toString();
                            FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
                            forum.setAuthor(aut.getUid());
                            forum.setJudul(judulPostingan);
                            forum.setDeskripsi(deskripsi);
                            forum.setTanggalUpload(formatdate);
                            String key = dbForum.push().getKey();
                            forum.setIdForum(key);
                            forum.setLinkImg(image);
                            forum.setCreatedAt(System.currentTimeMillis());
                            dbForum.child(key).setValue(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(FormForum.this,"Forum Berhasil Dipost", Toast.LENGTH_LONG).show();
                                    inpJudul.setText("");
                                    inpDeskripsi.setText("");

                                }
                            });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                progressBar.setVisibility(View.GONE);
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
                                try {
                                    if (ActivityCompat.checkSelfPermission(FormForum.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(FormForum.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                                    } else {
                                        Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 1:
                                //Mengambil gambar dari galeri
                                try {
                                    if (ActivityCompat.checkSelfPermission(FormForum.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(FormForum.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                                    } else {
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }

    public void uploadForumData(Forum forum){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formatdate = sdf.format(c);
        String judulPostingan = inpJudul.getText().toString();
        String deskripsi =  inpDeskripsi.getText().toString();
        FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
        forum.setAuthor(aut.getUid());
        forum.setJudul(judulPostingan);
        forum.setDeskripsi(deskripsi);
        forum.setTanggalUpload(formatdate);
        String key = dbForum.push().getKey();
        forum.setIdForum(key);
        dbForum.child(key).setValue(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FormForum.this,"Forum Berhasil Dipost", Toast.LENGTH_LONG).show();
                inpJudul.setText("");
                inpDeskripsi.setText("");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imgGambar.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    System.out.println();
                    imageUri = data.getData();
                    imgGambar.setImageURI(imageUri);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
                } else {
                    Toast.makeText(this, "App tidak diberi akses", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(galleryIntent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "App tidak diberi akses", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
