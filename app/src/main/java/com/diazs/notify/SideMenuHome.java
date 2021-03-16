package com.diazs.notify;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SideMenuHome extends AppCompatActivity {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToogle;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;

    MenuItem signOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToogle = new ActionBarDrawerToggle(this, myDrawer, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);

        myDrawer.addDrawerListener(myToogle);
        myToogle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id .toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setLogo(R.drawable.ic_baseline_menu_24);

        MenuItem postingan_saya = (MenuItem) findViewById(R.id.postingan_saya);
        MenuItem bookmark = (MenuItem) findViewById(R.id.bookmark);
        MenuItem notif = (MenuItem) findViewById(R.id.notif);
        MenuItem signOut = (MenuItem) findViewById(R.id.sign_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        signOut = (MenuItem) findViewById(R.id.sign_out);

        switch(item.getItemId()){
            case R.id.sign_out:
                firebaseAuth.signOut();
                break;
            case R.id.bookmark:
                Intent i = new Intent(SideMenuHome.this, BookmarkActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
