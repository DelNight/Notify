package com.diazs.notify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.diazs.notify.Adapter.ChatListAdapter;
import com.diazs.notify.Model.ChatMessage;
import com.diazs.notify.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ListChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private ArrayList<String> chatList;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        User.setAuthStatus("Online");

        setListener();

        chatList = new ArrayList<>();
        chatMessageArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        FirebaseDatabase.getInstance().getReference("chatMessage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                ChatMessage chatMessage = snapshot1.getValue(ChatMessage.class);
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                //Jika chat message milik user dari session login
                                if (chatMessage.getReceiver().equals(user.getUid()) ||
                                        chatMessage.getSender().equals(user.getUid())){
                                    //Mencari lawan bicara, ditampilkan berdasarkan pesan terbaru
                                    //Jika lawan bicara & belum ditambahkan ke chatList
                                    if (!chatMessage.getSender().equals(user.getUid()) && !chatList.contains(chatMessage.getSender())){
                                        chatList.add(chatMessage.getSender());
                                        addData(chatMessage);
                                    }else if (!chatMessage.getReceiver().equals(user.getUid()) && !chatList.contains(chatMessage.getReceiver())){
                                        chatList.add(chatMessage.getReceiver());
                                        addData(chatMessage);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment dialog = new PilihPostinganActivity();
                dialog.show(getSupportFragmentManager(), " string");
            }
        });
    }

    public void addData(ChatMessage chatMessage){
        chatMessageArrayList.add(chatMessage);
        ChatListAdapter adapter = new ChatListAdapter(chatMessageArrayList, ListChatActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void setListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AppCompatActivity context = ListChatActivity.this;

                switch (item.getItemId()) {
                    case R.id.navigation_satu:
                        startActivity(new Intent(context, HomeActivity.class));
                        break;
                    case R.id.navigation_dua:
                        startActivity(new Intent(context, BerandaActivity.class));
                        break;
                    case R.id.navigation_tiga:
                        startActivity(new Intent(context, ListChatActivity.class));
                        break;
                    case R.id.navigation_empat:
                        startActivity(new Intent(context, ProfilActivity.class));
                        break;
                }
                return false;
            }
        });

        invalidateOptionsMenu();
        bottomNavigation.getMenu().getItem(2).setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.setAuthStatus("Offline");
    }

    @Override
    protected void onPause() {
        super.onPause();
        User.setAuthStatus("Offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        User.setAuthStatus("Online");
    }
}
