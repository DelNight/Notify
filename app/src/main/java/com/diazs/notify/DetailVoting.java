package com.diazs.notify;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Adapter.DetailVotingAdapter;
import com.diazs.notify.Model.Agregate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailVoting extends AppCompatActivity{
    public static final String EXTRA_ID = "id voting";
    public static final String EXTRA_JUDUL = "Judul voting";
    public static final String EXTRA_DESC = "Detail voting";
    public static final String EXTRA_EXP = "expired";
    TextView judul, desc, exp;
    Button btn_add;
    RecyclerView rvAgregates;
    ArrayList<Agregate> listAgregates;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_voting);
        Toolbar toolbar = findViewById(R.id .toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_baseline_arrow_back_24);
        listAgregates = new ArrayList<>();
        rvAgregates = (RecyclerView) findViewById(R.id.recycler_calon);
        rvAgregates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getView();

        FirebaseDatabase.getInstance().getReference("voting").child(getIntent().getStringExtra(EXTRA_ID)).child("agregate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAgregates.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Agregate agregate = snapshot1.getValue(Agregate.class);
                    listAgregates.add(agregate);
                    DetailVotingAdapter adapter = new DetailVotingAdapter(listAgregates, DetailVoting.this);
                    rvAgregates.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailVoting.this, FormAgregate.class);
                String id = getIntent().getStringExtra(EXTRA_ID);
                i.putExtra(FormAgregate.EXTRA_ID,id);
                startActivity(i);
            }
        });
        setData();
    }
    public void getView(){
        judul = findViewById(R.id.judul_voting);
        desc = findViewById(R.id.desc_voting);
        exp = findViewById(R.id.exp_voting);
        btn_add = findViewById(R.id.btn_addvote);
    }
    public void setData(){
        String title = getIntent().getStringExtra(EXTRA_JUDUL);
        judul.setText(title);
        String desk = getIntent().getStringExtra(EXTRA_DESC);
        desc.setText(desk);
        String kad = getIntent().getStringExtra(EXTRA_EXP);
        exp.setText(kad);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailvoting, menu);
        return true;
    }
}
