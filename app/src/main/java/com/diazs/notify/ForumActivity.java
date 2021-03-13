package com.diazs.notify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Adapter.ListVotingAdapter;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.android.material.navigation.NavigationView;

public class ForumActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToogle;
    private RecyclerView recyclerView;
    private ListVotingAdapter listVotingAdapter;
    private ArrayList<Voting> list = new ArrayList<>();
    MaterialCardView cardView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigationListener();

        final DrawerLayout drawerLayout = findViewById(R.id.myDrawer);

        findViewById(R.id.button_sidebarforum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.fragment_forum);
        showProgressDialog();
        recyclerView = findViewById(R.id.list_post);
        recyclerView.setHasFixedSize(true);
        cardView = findViewById(R.id.card_forum);
        showRecyclerList();
        list = new ArrayList<>();
        getData();
    }

    void getData(){
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("voting");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Voting voting = npsnapshot.getValue(Voting.class);
                        list.add(voting);
                    }
                    ListVotingAdapter listVotingAdapter = new ListVotingAdapter(list);
                    recyclerView.setAdapter(listVotingAdapter);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(ForumActivity.this);
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
        ListVotingAdapter listVotingAdapter = new ListVotingAdapter(list);
        recyclerView.setAdapter(listVotingAdapter);

        listVotingAdapter.setOnItemClickCallback(data -> {
            showSelectedItem(data);
        });
    }
    private void showSelectedItem(Voting voting){
        Intent intent = new Intent(ForumActivity.this,DetailVoting.class);
        intent.putExtra(DetailVoting.EXTRA_JUDUL,voting.getJudulPosting());
        intent.putExtra(DetailVoting.EXTRA_DESC,voting.getDeskripsiVoting());
        intent.putExtra(DetailVoting.EXTRA_EXP,voting.getKadaluwarsa());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forum_sidebar, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.beranda:
                Intent beranda = new Intent(ForumActivity.this,HomeActivity.class);
                startActivity(beranda);

            case R.id.logout:
                Intent i = new Intent(ForumActivity.this,LoginActivity.class);
                startActivity(i);
                break;
            case R.id.bookmark:
                Intent e = new Intent(ForumActivity.this, BookmarkActivity.class);
                startActivity(e);
                break;

            case R.id.notif:
                Intent notif = new Intent(ForumActivity.this, NotificationActivity.class);
                startActivity(notif);
                break;

            case R.id.profile:
                Intent prof = new Intent(ForumActivity.this, ProfilActivity.class);
                startActivity(prof);
                break;

            case R.id.chat:
                Intent chat = new Intent(ForumActivity.this, ChatActivity.class);
                startActivity(chat);
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private  void setNavigationListener(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.forum_sidebar);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
