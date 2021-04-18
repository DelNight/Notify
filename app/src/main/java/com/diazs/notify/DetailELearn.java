package com.diazs.notify;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.diazs.notify.Model.DateFormater;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailELearn extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ImageView konten;
    TextView judul, tanggalUpload, uploader, deskripsi;
    LinearLayout lihatKomentar;
    FloatingActionButton addContent;
    Materi materi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_elearn);
        materi = getIntent().getParcelableExtra("MATERI");
        initializeComponent();
        initializeListener();
        setContentValue();
    }

    public void initializeComponent(){
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        konten = findViewById(R.id.konten_foto);
        judul = findViewById(R.id.judul);
        tanggalUpload = findViewById(R.id.tanggal_upload);
        uploader = findViewById(R.id.uploader);
        lihatKomentar = findViewById(R.id.lihat_komentar);
        addContent = findViewById(R.id.fab);
        deskripsi = findViewById(R.id.deskripsi);
    }

    public void initializeListener(){
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AppCompatActivity context = DetailELearn.this;
                switch (item.getItemId()){
                    case R.id.navigation_satu:
                        startActivity(new Intent(context, HomeActivity.class));
                        context.finish();
                        break;
                    case R.id.navigation_dua:
                        startActivity(new Intent(context, BerandaActivity.class));
                        context.finish();
                        break;
                    case R.id.navigation_tiga:
                        startActivity(new Intent(context, ChatActivity.class));
                        context.finish();
                        break;
                    case R.id.navigation_empat:
                        startActivity(new Intent(context, ProfilActivity.class));
                        context.finish();
                        break;
                }
                return false;
            }
        });

        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment pilihPostingan = new PilihPostinganActivity();
                pilihPostingan.show(getSupportFragmentManager()," string");
            }
        });

        lihatKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity context = DetailELearn.this;
                Intent intent = new Intent(context, KomentarActivity.class);
                intent.putExtra(KomentarActivity.JENIS_HALAMAN, "e-learn");
                intent.putExtra(KomentarActivity.POST_ID, materi.getIdMateri());
                startActivity(intent);
            }
        });
    }

    public void setContentValue(){
        Glide.with(this).load(materi.getImageUrl()).into(konten);
        judul.setText(materi.getJudulMateri());
        tanggalUpload.setText(DateFormater.getDateFormated(materi.getCreatedAt(), "dd-MM-yyyy"));
        deskripsi.setText(materi.getDeskripsiMateri());

        FirebaseDatabase.getInstance().getReference("users").child(materi.getAuthorMateri()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User author = snapshot.getValue(User.class);
                uploader.setText(author.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        invalidateOptionsMenu();
        bottomNavigation.getMenu().getItem(1).setChecked(true);
    }
}
