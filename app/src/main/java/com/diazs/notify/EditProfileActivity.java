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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private DatabaseReference mdbUsers;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mUser;
    private Uri imageUri;
    private StorageReference reference;
    EditText edit_nama,edit_username;
    Spinner kelas;
    String id, email,status, noHp;
    int role;
    RadioGroup jk;
    RadioButton pilih;
    ImageView img_edit;
    MaterialButton simpan,cancel;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbUsers = FirebaseDatabase.getInstance().getReference();
        mUser = firebaseAuth.getCurrentUser();
        reference = FirebaseStorage.getInstance().getReference();

        userInformation(mUser.getUid());
        findView();
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menerapkan kejadian saat tombol pilih gambar di klik
                getImage();
            }
        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                img_edit.setDrawingCacheEnabled(true);
                img_edit.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) img_edit.getDrawable()).getBitmap();
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
                                user.setNama(edit_nama.getText().toString());
                                user.setUsername(edit_username.getText().toString());
                                user.setKelas(kelas.getSelectedItem().toString());
                                user.setImageURL(image);
                                user.setId(id);
                                user.setEmail(email);
                                int selected = jk.getCheckedRadioButtonId();
                                pilih = findViewById(selected);
                                user.setJenisKelamin(pilih.getText().toString());
                                mdbUsers.child("users").child(mUser.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent i = new Intent(EditProfileActivity.this, ProfilActivity.class);
                                        Toast.makeText(EditProfileActivity.this,"Data Berhasil Diperbarui", Toast.LENGTH_LONG).show();
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void userInformation(String uID) {
        final Query q = mdbUsers.child("users").child(uID);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User Uinfo = dataSnapshot.getValue(User.class);
                    setData(Uinfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setData(User info) {
        id = info.getId();
        email = info.getEmail();
        status = info.getStatus();
        role = info.getRole();
        noHp = info.getNoHP();
        edit_nama.setText(info.getNama());
        edit_username.setText(info.getUsername());
        kelas.setSelected(false);
        jk.setSelected(false);
    }

    private void findView(){
        simpan = findViewById(R.id.simpan);
        cancel = findViewById(R.id.cancel);
        img_edit = findViewById(R.id.img_edit);
        edit_nama = findViewById(R.id.edit_nama);
        edit_username = findViewById(R.id.edit_username);
        kelas = findViewById(R.id.kelas);
        jk = findViewById(R.id.jk);

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
                    img_edit.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    imageUri = data.getData();
                    img_edit.setImageURI(imageUri);
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
