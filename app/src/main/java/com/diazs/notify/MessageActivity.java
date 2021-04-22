package com.diazs.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageView fotoLawanBicara;
    private TextView namaLawanBicara, statusLawanBicara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        inputMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        fotoLawanBicara = findViewById(R.id.profile_image);
        namaLawanBicara = findViewById(R.id.username);
        messageArrayList = new ArrayList<>();
        statusLawanBicara = findViewById(R.id.status_user);
        User.setAuthStatus("Online");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        lawanBicara = getIntent().getParcelableExtra("USER");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(lawanBicara.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                statusLawanBicara.setText(user.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        messageArrayList.clear();
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

        FirebaseDatabase.getInstance().getReference("users").child(lawanBicara.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                namaLawanBicara.setText(user.getNama());
                if (user.getImageURL() != null){
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(fotoLawanBicara);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessage(){
        ChatMessage message = new ChatMessage();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chatMessage");
        String key = reference.push().getKey();
        message.setId(key);
        message.setCreatedAt(System.currentTimeMillis());
        message.setMessage(inputMessage.getText().toString());
        message.setSeen(false);
        message.setReceiver(lawanBicara.getId());
        message.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseDatabase.getInstance().getReference("chatMessage").child(key).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MessageActivity.this, "Pesan Terkirim!", Toast.LENGTH_LONG).show();
                inputMessage.setText("");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        User.setAuthStatus("Offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.setAuthStatus("Offline");
    }

    @Override
    protected void onResume() {
        super.onResume();
        User.setAuthStatus("Online");
    }
}