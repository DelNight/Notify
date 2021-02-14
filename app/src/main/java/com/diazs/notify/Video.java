package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Video extends AppCompatActivity {
    RecyclerView recyclerView;
    String s1[], s2[];
    int images[] = {R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_account_circle_24};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.username);
        s2 = getResources().getStringArray(R.array.description);

        KomentarAdapter komentarAdapter = new KomentarAdapter(this, s1, s2, images);
        recyclerView.setAdapter(komentarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu5, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option) {
            Toast.makeText(getApplicationContext(), "You click option", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.bookmark) {
            Toast.makeText(getApplicationContext(), "You click bookmark", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}