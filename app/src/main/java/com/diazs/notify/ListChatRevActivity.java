package com.diazs.notify;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Adapter.ListChatRevAdapter;
import com.diazs.notify.Model.ListChatRev;

import java.util.ArrayList;

public class ListChatRevActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListChatRevAdapter listChatRevAdapter;
    private ArrayList<ListChatRev> listChatRevArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getData();

        recyclerView = findViewById(R.id.recycleview);
        listChatRevAdapter = new ListChatRevAdapter(listChatRevArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListChatRevActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listChatRevAdapter);

    }

    public void getData(){
        listChatRevArrayList = new ArrayList<>();
        listChatRevArrayList.add(new ListChatRev("Username 1", "tes tes, chat di sini", R.drawable.ic_baseline_account_circle));
        listChatRevArrayList.add(new ListChatRev("Username 2", "tes tes, chat di sini", R.drawable.ic_baseline_account_circle));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

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