package com.diazs.notify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ForumActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);
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
