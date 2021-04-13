package com.diazs.notify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Adapter.ListForumAdapter;
import com.diazs.notify.Adapter.ListVotingAdapter;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.Materi;
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

public class ForumActivity extends AppCompatActivity {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToogle;
    private RecyclerView recyclerView;
    private ArrayList<Forum> list = new ArrayList<>();
    MaterialCardView cardView;
    ProgressDialog progressDialog;
//    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);
        showProgressDialog();

        cardView = findViewById(R.id.card_forum);
        list = new ArrayList<>();
        getData();
    }

    void getData(){
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("forum");
//        loading.setVisibility(LottieAnimationView.VISIBLE);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                    Forum forum = npsnapshot.getValue(Forum.class);
                    list.add(forum);
                }
                ListForumAdapter listForumAdapter = new ListForumAdapter(list);
                setRecyclerView();
                recyclerView.setAdapter(listForumAdapter);
                progressDialog.dismiss();
                listForumAdapter.setOnItemClickCallback(data -> showSelectedItem(data));

//                loading.setVisibility(LottieAnimationView.INVISIBLE);
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

//    private void showRecyclerList(){
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ListForumAdapter listForumAdapter = new ListForumAdapter(list);
//        recyclerView.setAdapter(listForumAdapter);
//
//        listForumAdapter.setOnItemClickCallback(data -> {
//            showSelectedItem(data);
//        });
//    }
    private void showSelectedItem(Forum forum){
        Intent intent = new Intent(ForumActivity.this,DetailVoting.class);
        intent.putExtra(DetailVoting.EXTRA_JUDUL,forum.getJudul());
        intent.putExtra(DetailVoting.EXTRA_DESC,forum.getDeskripsi());
        intent.putExtra(DetailVoting.EXTRA_EXP,forum.getAuthor());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecyclerView(){
        recyclerView = findViewById(R.id.recycleforum);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}