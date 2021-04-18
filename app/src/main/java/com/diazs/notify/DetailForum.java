package com.diazs.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.diazs.notify.Model.DateFormater;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailForum extends AppCompatActivity {
    TextView judul, tanggalUpload, uploader, deskripsi;
    LinearLayout lihatKomentar;
    ImageView konten;
    BottomNavigationView bottomNavigation;
    FloatingActionButton addContent;
    Forum forum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forum);
        forum = getIntent().getParcelableExtra("FORUM");
        initializeComponent();
        initializeListener();
        setContentValue();
    }

    public void initializeComponent(){
        judul = findViewById(R.id.judul);
        tanggalUpload = findViewById(R.id.tanggal_upload);
        uploader = findViewById(R.id.uploader);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        konten = findViewById(R.id.konten_foto);
        addContent = findViewById(R.id.fab);
        deskripsi = findViewById(R.id.deskripsi);
        lihatKomentar = findViewById(R.id.lihat_komentar);
    }

    public void initializeListener(){
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AppCompatActivity context = DetailForum.this;

                switch (item.getItemId()){
                    case R.id.navigation_satu:
                        startActivity(new Intent(context, HomeActivity.class));
                        break;
                    case R.id.navigation_dua:
                        startActivity(new Intent(context, BerandaActivity.class));
                        break;
                    case R.id.navigation_tiga:
                        startActivity(new Intent(context, ChatActivity.class));
                        break;
                    case R.id.navigation_empat:
                        startActivity(new Intent(context, ProfilActivity.class));
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
                AppCompatActivity context = DetailForum.this;
                Intent intent = new Intent(context, KomentarActivity.class);
                intent.putExtra(KomentarActivity.JENIS_HALAMAN, "forum");
                intent.putExtra(KomentarActivity.POST_ID, forum.getIdForum());
                startActivity(intent);
            }
        });
    }

    public void setContentValue(){
        Glide.with(this).load(forum.getLinkImg()).into(konten);
        judul.setText(forum.getJudul());
        tanggalUpload.setText(DateFormater.getDateFormated(forum.getCreatedAt(), "dd-MM-yyyy"));
        deskripsi.setText(forum.getDeskripsi());

        FirebaseDatabase.getInstance().getReference("users").child(forum.getAuthor()).addListenerForSingleValueEvent(new ValueEventListener() {
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
