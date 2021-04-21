package com.diazs.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diazs.notify.Adapter.MessageAdapter;
import com.diazs.notify.Fragments.APIService;
import com.diazs.notify.Model.ChatMessage;
import com.diazs.notify.Model.User;
import com.diazs.notify.Notifications.Client;
import com.diazs.notify.Notifications.Data;
import com.diazs.notify.Notifications.MyResponse;
import com.diazs.notify.Notifications.Sender;
import com.diazs.notify.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    private EditText inputMessage;
    private ImageButton btnSend;
    private ArrayList<ChatMessage> messageArrayList;
    private RecyclerView recyclerView;
    private User lawanBicara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        lawanBicara = getIntent().getParcelableExtra("USER");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        getData();
    }

    public void getData(){
        FirebaseDatabase.getInstance().getReference("chatMessage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ChatMessage chatMessage = snapshot1.getValue(ChatMessage.class);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (chatMessage.getSender().equals(user.getUid()) && chatMessage.getReceiver().equals(lawanBicara.getId()) ||
                                chatMessage.getSender().equals(lawanBicara.getId()) && chatMessage.getReceiver().equals(user.getUid())){
                                if (chatMessage.getReceiver().equals(lawanBicara.getId())){
                                    chatMessage.setSeen(true);
                                }
                                messageArrayList.add(chatMessage);
                                MessageAdapter adapter = new MessageAdapter(MessageActivity.this, messageArrayList);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void sendMessage(){
        ChatMessage message = new ChatMessage();
        message.setCreatedAt(System.currentTimeMillis());
        message.setMessage(inputMessage.getText().toString());
        message.setSeen(false);
        message.setReceiver(lawanBicara.getId());
        message.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseDatabase.getInstance().getReference("chatMessage").setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MessageActivity.this, "Pesan Terkirim!", Toast.LENGTH_LONG).show();
            }
        });
    }
}