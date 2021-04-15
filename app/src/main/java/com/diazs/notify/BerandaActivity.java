package com.diazs.notify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Adapter.ListELearnAdapter;
import com.diazs.notify.Adapter.ListForumAdapter;
import com.diazs.notify.Adapter.ListVotingAdapter;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.Voting;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BerandaActivity extends AppCompatActivity implements ListVotingAdapter.OnItemClickCallback, AdapterView.OnItemSelectedListener {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToogle;
    private RecyclerView recyclerView;
    private ArrayList<Voting> list;
    private ArrayList<Materi> listMateri;
    private ArrayList<Forum> listForum;
    MaterialCardView cardView;
    ProgressDialog progressDialog;
    ArrayList listKategori;
    Spinner spinner;
    LottieAnimationView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_beranda);
        setRecyclerView();
        cardView = findViewById(R.id.card_forum);
        list = new ArrayList<>();
        listMateri = new ArrayList<>();
        listForum = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);

        listKategori = new ArrayList();
        listKategori.add("E-Learn");
        listKategori.add("Forum");
        listKategori.add("Voting");
        loading = (LottieAnimationView) findViewById(R.id.loading_bell);

        loading.playAnimation();
        loading.setSpeed((float) 0.75);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_dropdown, listKategori);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        getDataELearn();
    }

    void getDataVoting(){
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("voting");
        loading.setVisibility(LottieAnimationView.VISIBLE);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                    Voting voting = npsnapshot.getValue(Voting.class);
                    list.add(voting);
                }
                ListVotingAdapter listVotingAdapter = new ListVotingAdapter(list);
                setRecyclerView();
                recyclerView.setAdapter(listVotingAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
                listVotingAdapter.setOnItemClickCallback(data -> showSelectedItem(data));

                loading.setVisibility(LottieAnimationView.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getDataForum(){
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("forum");
        loading.setVisibility(LottieAnimationView.VISIBLE);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listForum.clear();
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                    Forum forum = npsnapshot.getValue(Forum.class);
                    listForum.add(forum);
                }
                ListForumAdapter listForumAdapter = new ListForumAdapter(listForum);
                setRecyclerView();
                recyclerView.setAdapter(listForumAdapter);
                listForumAdapter.setOnItemClickCallback(data -> showSelectedItem(data));

                loading.setVisibility(LottieAnimationView.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(LottieAnimationView.INVISIBLE);
            }
        });
    }

    public void getDataELearn(){
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("learn");
        loading.setVisibility(LottieAnimationView.VISIBLE);
        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMateri.clear();
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                    Materi materi = npsnapshot.getValue(Materi.class);
                    listMateri.add(materi);
                }
                ListELearnAdapter listELearnAdapter = new ListELearnAdapter(listMateri, BerandaActivity.this);
                setRecyclerView();
                recyclerView.setAdapter(listELearnAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
                listELearnAdapter.setOnItemClickCallback(data -> showSelectedItem(data));

                loading.setVisibility(LottieAnimationView.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(LottieAnimationView.INVISIBLE);
            }
        });
    }
    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(BerandaActivity.this);
        progressDialog.setMessage(message); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }

    public void setRecyclerView(){
        recyclerView = findViewById(R.id.list_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showSelectedItem(Voting voting){
        Intent intent = new Intent(BerandaActivity.this, DetailVoting.class);
        intent.putExtra(DetailVoting.EXTRA_ID, voting.getIdVoting());
        intent.putExtra(DetailVoting.EXTRA_JUDUL, voting.getJudulPosting());
        intent.putExtra(DetailVoting.EXTRA_DESC, voting.getDeskripsiVoting());
        intent.putExtra(DetailVoting.EXTRA_EXP, voting.getKadaluwarsa());
        startActivity(intent);
    }

    private void showSelectedItem(Materi materi){
        Intent intent = new Intent(BerandaActivity.this, DetailELearn.class);
        intent.putExtra("MATERI", materi);
        startActivity(intent);
    }

    private void showSelectedItem(Forum forum){
        Intent intent = new Intent(BerandaActivity.this, DetailForum.class);
        intent.putExtra("FORUM", forum);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.spinner){
            Toast.makeText(this, "Spinner Clicked!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beranda, menu);
        return true;
    }

    @Override
    public void onItemClicked(Voting data) {
        Intent intent = new Intent(BerandaActivity.this, DetailVoting.class);
        intent.putExtra(DetailVoting.EXTRA_JUDUL,data.getJudulPosting());
        intent.putExtra(DetailVoting.EXTRA_DESC,data.getDeskripsiVoting());
        intent.putExtra(DetailVoting.EXTRA_EXP,data.getKadaluwarsa());
        startActivity(intent);
    }

    public void onClickMenuForum(View view){
        Toast.makeText(this, "Spinner Clicked : " + view.getId(), Toast.LENGTH_LONG).show();
        System.out.println(view.getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0){
            System.out.println("Getting data Learn");
            getDataELearn();
        }else if(i == 1){
            System.out.println("Getting data Forum");
            getDataForum();
        }else if(i == 2){
            System.out.println("Getting data Voting");
            getDataVoting();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
