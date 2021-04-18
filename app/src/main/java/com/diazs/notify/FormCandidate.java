package com.diazs.notify;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diazs.notify.Model.Candidate;
import com.diazs.notify.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class FormCandidate extends AppCompatActivity {
    public static final String EXTRA_ID = "id voting";
    EditText nama, visi, misi;
    TextView maker;
    Button btn_submit;
    ImageView btn_gbr;
    private Uri imageUri;
    private StorageReference reference;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);

        maker = findViewById(R.id.name);
        nama = findViewById(R.id.edt_nama);
        visi = findViewById(R.id.edt_visi);
        misi = findViewById(R.id.edt_misi);
        btn_gbr = findViewById(R.id.imgProfil);
        reference = FirebaseStorage.getInstance().getReference();
        btn_gbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menerapkan kejadian saat tombol pilih gambar di klik
                getImage();

            }
        });

        btn_submit = findViewById(R.id.btn_ok);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidate candidate = new Candidate();
                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("voting");
                btn_gbr.setDrawingCacheEnabled(true);
                btn_gbr.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) btn_gbr.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //Mengkompress bitmap menjadi JPG dengan kualitas gambar 100%
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                //Lokasi lengkap dimana gambar akan disimpan
                String namaFile = UUID.randomUUID()+".jpg";
                String pathImage = "File/"+namaFile;

                UploadTask uploadTask = reference.child(pathImage).putBytes(bytes);
                final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image = uri.toString();
                                String id = dbF.push().getKey();
                                candidate.setIdCandidate(id);
                                candidate.setNama(nama.getText().toString());
                                candidate.setMisi(misi.getText().toString());
                                candidate.setVisi(visi.getText().toString());
                                candidate.setUrlFoto(image);
                                candidate.setIdVoting(getIntent().getStringExtra(EXTRA_ID));
                                dbF.child(getIntent().getStringExtra(EXTRA_ID)).child("candidate").child(id).setValue(candidate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(FormCandidate.this,"Kandidat berhsail ditambahkan",Toast.LENGTH_LONG).show();
                                        nama.setText("");
                                        visi.setText("");
                                        misi.setText("");
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                maker.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    btn_gbr.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    imageUri = data.getData();
                    btn_gbr.setImageURI(imageUri);
                }
                break;
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
