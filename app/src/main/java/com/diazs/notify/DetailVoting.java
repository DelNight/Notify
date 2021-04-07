package com.diazs.notify;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class DetailVoting extends AppCompatActivity{
    public static final String EXTRA_JUDUL = "Judul voting";
    public static final String EXTRA_DESC = "Detail voting";
    public static final String EXTRA_EXP = "expired";
    TextView judul,desc,exp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_voting);
        Toolbar toolbar = findViewById(R.id .toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_baseline_arrow_back_24);
        getView();
        setData();
    }
    public void getView(){
        judul = findViewById(R.id.judul_voting);
        desc = findViewById(R.id.desc_voting);
        exp = findViewById(R.id.exp_voting);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailvoting, menu);
        return true;
    }
}