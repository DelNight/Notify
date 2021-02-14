package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotifAdapter adapter;
    private ArrayList<NotifModel> notifModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new NotifAdapter(notifModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void addData(){
        notifModelArrayList = new ArrayList<>();
        notifModelArrayList.add(new NotifModel("Lorem ipsum sit amet dolor", "Lorem ipsum sit amet dolor lorem ipsum sit amet dolor"));
        notifModelArrayList.add(new NotifModel("Lorem ipsum sit amet dolor", "Lorem ipsum sit amet dolor lorem ipsum sit amet dolor"));
        notifModelArrayList.add(new NotifModel("Lorem ipsum sit amet dolor", "Lorem ipsum sit amet dolor lorem ipsum sit amet dolor"));
    }
}