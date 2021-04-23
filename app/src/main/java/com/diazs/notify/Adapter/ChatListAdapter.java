package com.diazs.notify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diazs.notify.MessageActivity;
import com.diazs.notify.Model.ChatMessage;
import com.diazs.notify.Model.DateFormater;
import com.diazs.notify.Model.User;
import com.diazs.notify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>{
    private ArrayList<ChatMessage> listChatArrayList;
    private Context context;

    public ChatListAdapter(ArrayList<ChatMessage> listChatArrayList, Context context) {
        this.listChatArrayList = listChatArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatListAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_chat, parent, false);
        return new ChatListAdapter.ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ChatMessage chatMessage = listChatArrayList.get(position);

        //Jika lawan bicara
        if (!chatMessage.getReceiver().equals(user.getUid())){
            setOnclick(chatMessage.getReceiver(), holder, chatMessage);
            setListData(chatMessage.getReceiver(), holder, chatMessage);
        }else{
            setOnclick(chatMessage.getSender(), holder, chatMessage);
            setListData(chatMessage.getSender(), holder, chatMessage);
        }
    }

    @Override
    public int getItemCount() {
        return listChatArrayList.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        ImageView profilImage;
        CardView cardChat;
        TextView tvNama, tvPesan, tvWaktu;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            profilImage = itemView.findViewById(R.id.profile_image);
            cardChat = itemView.findViewById(R.id.card_chat);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvPesan = itemView.findViewById(R.id.tv_pesan);
            tvWaktu = itemView.findViewById(R.id.waktu_kirim);
        }
    }

    public void setOnclick(String idLawanBicara, ChatListViewHolder holder, ChatMessage chatMessage){
        holder.cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("users").child(idLawanBicara).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        Intent intent = new Intent(context, MessageActivity.class);
                        intent.putExtra("USER", user1);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void setListData(String idLawanBicara, ChatListViewHolder holder, ChatMessage chatMessage){
        FirebaseDatabase.getInstance().getReference("users").child(idLawanBicara).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tvNama.setText(user.getNama());
                holder.tvPesan.setText(chatMessage.getMessage());
                holder.tvWaktu.setText(DateFormater.getDateFormated(chatMessage.getCreatedAt(), DateFormater.FORMAT_SIX));
                if (user.getImageURL() != null){
                    Glide.with(context).load(user.getImageURL()).into(holder.profilImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
