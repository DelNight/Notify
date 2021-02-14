package com.diazs.notify;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Forum extends AppCompatActivity {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);

        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToogle = new ActionBarDrawerToggle(this, myDrawer, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);

        myDrawer.addDrawerListener(myToogle);
        myToogle.syncState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setLogo(R.drawable.ic_baseline_menu_24);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToogle.onOptionsItemSelected(item)) {
            return true;
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
