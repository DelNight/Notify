package com.diazs.notify.Adapter;

import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.ListChatRev;
import com.diazs.notify.R;

import java.util.ArrayList;

public class ListChatRevAdapter extends RecyclerView.Adapter<ListChatRevAdapter.ListChatViewHolder> {

    private ArrayList<ListChatRev> listChat;

    public ListChatRevAdapter(ArrayList<ListChatRev> listChat) {
        this.listChat = listChat;
    }

    @NonNull
    @Override
    public ListChatRevAdapter.ListChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_item_chat_list, parent, false);
        return new ListChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListChatRevAdapter.ListChatViewHolder holder, int position) {
        holder.tvUsername.setText(listChat.get(position).getUsername());
        holder.tvMessage.setText(listChat.get(position).getTxmessage());
        holder.iProfpic.setImageResource(listChat.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return (listChat!= null) ? listChat.size() : 0;
    }

    public class ListChatViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername, tvMessage;
        private ImageView iProfpic;

        public ListChatViewHolder (View view){
            super(view);
            tvUsername = view.findViewById(R.id.text_username);
            tvMessage = view.findViewById(R.id.text_message);
            iProfpic = view.findViewById(R.id.profile_image);
        }
    }
}
