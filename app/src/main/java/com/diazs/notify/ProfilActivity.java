package com.diazs.notify;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Adapter.ListForumAdapter;
import com.diazs.notify.Adapter.ListVotingAdapter;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    ImageButton btnEdit;
    ImageButton btnLogout;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private ArrayList<Forum> list = new ArrayList<>();

    private TextView tvNama, tvKelas,tvUser, tvEmail, tvJenkel, tvTname;

    private BottomNavigationView bottomNavigation;
    private FloatingActionButton addContent;

    private ImageButton btnBack;
    private ImageView imgProfil;
    private ProgressDialog progressDialog;

    private LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        noFade();
        findView();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recycler_profil);
        recyclerView.setHasFixedSize(true);

        showRecyclerList();

        loading = findViewById(R.id.loading_bell);
        loading.setVisibility(View.VISIBLE);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        setListener();
        getData(currentUser.getUid());
    }

    void findView(){
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        addContent = findViewById(R.id.fab);
        btnEdit = findViewById(R.id.btn_edit);
        btnBack = findViewById(R.id.btn_back);
        tvKelas = findViewById(R.id.class_user);
        tvTname = findViewById(R.id.nama);
        tvNama = findViewById(R.id.name);
        tvJenkel = findViewById(R.id.gender_user);
        tvUser = findViewById(R.id.username);
        tvEmail = findViewById(R.id.email);
        btnLogout = findViewById(R.id.btn_logout);
        imgProfil = findViewById(R.id.img_profil);

    }

    void getData(String uID){
        final Query q = mDatabase.child("users").child(uID);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    setData(user);
                    loading.setVisibility(View.GONE);
//                    Query q = mDatabase.child("Kelas").child(siswa.getKelas());
//                    q.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Kelas kelas = snapshot.getValue(Kelas.class);
//                            tvKelas.setText(kelas.getNama());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setData(User user){
        tvNama.setText(user.getNama().toUpperCase());
        tvTname.setText(user.getNama());
        tvJenkel.setText(user.getJenisKelamin());
        tvKelas.setText(user.getKelas());
        tvEmail.setText(user.getEmail());
        tvUser.setText(user.getUsername());
        if (user.getImageURL() != null){
            Picasso.get().load(user.getImageURL()).into(imgProfil);
        }
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(ProfilActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListForumAdapter ListForumAdapter = new ListForumAdapter(list);
        recyclerView.setAdapter(ListForumAdapter);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    private void noFade(){
//        Fade fade = new Fade();
//        View decor = getWindow().getDecorView();
//        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
//        fade.excludeTarget(android.R.id.statusBarBackground, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
    }

    void signOut(){
        mAuth.signOut();
        Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void logoutDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setMessage("Yakin untuk logout?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void setListener(){
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AppCompatActivity context = ProfilActivity.this;

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
                pilihPostingan.show(getSupportFragmentManager(), " string");
            }
        });

        invalidateOptionsMenu();
        bottomNavigation.getMenu().getItem(3).setChecked(true);
    }
}