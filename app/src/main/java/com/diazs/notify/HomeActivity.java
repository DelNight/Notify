package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diazs.notify.Adapter.HomeAdapter;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    private TextView mTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    private RecyclerView rvHome;
    private ArrayList<Materi> listMateri;
    private TextView tvName, tvRole;

    MenuItem signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationListener();
        firebaseAuth = FirebaseAuth.getInstance();
        listMateri = new ArrayList<>();

        rvHome = findViewById(R.id.recycler_buat_kamu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHome.setLayoutManager(layoutManager);
        tvName = findViewById(R.id.name);
        tvRole = findViewById(R.id.as);


        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvName.setText(user.getNama());
                if(user.getRole() == 1){
                    tvRole.setText("Super Admin");
                }else if(user.getRole() == 2){
                    tvRole.setText("Guru");
                }else if(user.getRole() == 3){
                    tvRole.setText("Siswa");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("learn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Materi materi = snapshot1.getValue(Materi.class);
                    listMateri.add(materi);
                    HomeAdapter adapter = new HomeAdapter(listMateri, HomeActivity.this);
                    rvHome.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();

                if (user1 == null) {
                    //user not login
                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    HomeActivity.this.finish();
                }
            }
        };

        ImageView forum = (ImageView) findViewById(R.id.forum);
        ImageView voting = (ImageView) findViewById(R.id.voting);
        ImageView learning = (ImageView) findViewById(R.id.learning);
        ImageView kalender = (ImageView) findViewById(R.id.kalender);
        ImageView chat = (ImageView) findViewById(R.id.chat);
        ImageView posting = (ImageView) findViewById(R.id.posting);
        RelativeLayout profile = findViewById(R.id.kotak1);

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ForumActivity.class);
                startActivity(i);
            }
        });

        voting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,FormVoting.class);
                startActivity(i);
            }
        });

        learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,LearnActivity.class);
                startActivity(i);
            }
        });

        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

//        chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this,ChatActivity.class);
//                startActivity(i);
//            }
//        });

        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment dialog = new PilihPostinganActivity();
                dialog.show(getSupportFragmentManager(), " string");
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProfilActivity.class);
                startActivity(i);
            }
        });

        ButterKnife.bind(this);


//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//            }
//        });

//        mTextView = findViewById(R.id.abaikanteksini);

        //kalo user mau posting
//        Button buttonPosting = findViewById(R.id.button_posting);
//        buttonPosting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PilihPostinganActivity pilihPostingan = new PilihPostinganActivity();
//                pilihPostingan.show(getSupportFragmentManager(), "pilihPosting");
//            }
//        });

//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//            }
//        });
    }

    //    @Override
//    public void OnButtonClicked(String text) {
//        mTextView.setText(text);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sign_out:
                firebaseAuth.signOut();
                System.out.println("Auth User :" + firebaseAuth.getCurrentUser().getEmail());
                break;
            case R.id.bookmark:
                Intent i = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_sidebar, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sign_out:
                firebaseAuth.signOut();
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
                break;
            case R.id.bookmark:
                Intent e = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(e);
                break;

            case R.id.notif:
                Intent notif = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(notif);
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private  void setNavigationListener(){
//        NavigationView navigationView = (NavigationView) findViewById(R.id.home_sidebar);
//        navigationView.setNavigationItemSelectedListener(this);
    }
}