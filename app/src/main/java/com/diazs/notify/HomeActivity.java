package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.diazs.notify.Adapter.ListELearnAdapter;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    private RecyclerView rvHome;
    private ArrayList<Materi> listMateri;
    private TextView tvName, tvRole;
    private BottomNavigationView bottomNavigation;
    private FloatingActionButton addContent;
    private ImageView profileImage;
    private LottieAnimationView loading;
    MenuItem signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationListener();
        firebaseAuth = FirebaseAuth.getInstance();
        listMateri = new ArrayList<>();
        loading = findViewById(R.id.loading_bell);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);

        rvHome = findViewById(R.id.recycler_buat_kamu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHome.setLayoutManager(layoutManager);
        tvName = findViewById(R.id.name);
        tvRole = findViewById(R.id.as);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        addContent = findViewById(R.id.fab);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        profileImage = findViewById(R.id.profile_image);

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

        setListener();
        setContentValue();


//        chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this,ChatActivity.class);
//                startActivity(i);
//            }
//        });

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

    private void setContentValue(){
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvName.setText(user.getNama());
                if (!user.getImageURL().equals("") && user.getImageURL() != null){
                    Glide.with(HomeActivity.this).load(user.getImageURL()).into(profileImage);
                }

                if(user.getRole() == 1){
                    tvRole.setText("Super Admin");
                }else if(user.getRole() == 2){
                    tvRole.setText("Guru");
                }else if(user.getRole() == 3){
                    tvRole.setText("Siswa");
                }
                loading.setVisibility(View.GONE);
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
                    ListELearnAdapter adapter = new ListELearnAdapter(listMateri, HomeActivity.this);
                    adapter.setOnItemClickCallback(data -> showSelectedItem(data));
                    rvHome.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setListener(){
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AppCompatActivity context = HomeActivity.this;

                switch (item.getItemId()){
                    case R.id.navigation_satu:
                        startActivity(new Intent(context, HomeActivity.class));
                        break;
                    case R.id.navigation_dua:
                        startActivity(new Intent(context, BerandaActivity.class));
                        break;
                    case R.id.navigation_tiga:
                        startActivity(new Intent(context, ListChatActivity.class));
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
    }

    public void showSelectedItem(Materi materi){
        Intent intent = new Intent(HomeActivity.this, DetailELearn.class);
        intent.putExtra("MATERI", materi);
        startActivity(intent);
    }
}