package com.diazs.notify;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Adapter.ForumRevAdapter;
import com.diazs.notify.Model.ForumRev;

import java.util.ArrayList;

public class ForumRevActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ForumRevAdapter forumRevAdapter;
    private ArrayList<ForumRev> forumRevArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_rev);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getData();
        recyclerView = findViewById(R.id.recycleview);
        forumRevAdapter = new ForumRevAdapter(forumRevArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ForumRevActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(forumRevAdapter);
    }

    public void getData() {
        forumRevArrayList = new ArrayList<>();
        forumRevArrayList.add(new ForumRev("Ini judul postingan", "Ini paragraf deskripsi postingan. Ini paragraf deskripsi postingan.", "Username"));
        forumRevArrayList.add(new ForumRev("Ini judul voting", "Ini paragraf deskripsi postingan. Ini paragraf deskripsi postingan.", "Username"));
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listchat, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search...");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}