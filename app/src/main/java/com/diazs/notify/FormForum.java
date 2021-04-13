package com.diazs.notify;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormForum extends AppCompatActivity {
    EditText spinner;
    TextView tvNama;
    DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;
    EditText inpJudul, inpDeskripsi, inpKategori;
    Button btnSubmit;
    DatabaseReference dbForum;
    LottieAnimationView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_forum);
        loading = findViewById(R.id.loading_bell);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);
        tvNama = findViewById(R.id.name);


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

        inpJudul = findViewById(R.id.in_judul);
        inpDeskripsi = findViewById(R.id.in_desc);
        inpKategori = findViewById(R.id.in_kategori);

        btnSubmit = findViewById(R.id.btn_submit);

        dbForum = FirebaseDatabase.getInstance().getReference("forum");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formatdate = sdf.format(c);
                String judulPostingan = inpJudul.getText().toString();
                String deskripsi =  inpDeskripsi.getText().toString();
                String kategori = inpKategori.getText().toString();
                Forum forum = new Forum();
                FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
                forum.setAuthor(aut.getUid());
                forum.setJudul(judulPostingan);
                forum.setDeskripsi(deskripsi);
                forum.setKategori(kategori);
                forum.setTanggalUpload(formatdate);
                String key = dbForum.push().getKey();
                forum.setIdForum(key);
                dbForum.child(key).setValue(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FormForum.this,"Forum Berhasil Dipost", Toast.LENGTH_LONG).show();
                        inpJudul.setText("");
                        inpDeskripsi.setText("");
                        inpKategori.setText("");
                    }
                });
            }
        });
    }

}
