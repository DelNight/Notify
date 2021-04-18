package com.diazs.notify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.diazs.notify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListForumAdapter extends RecyclerView.Adapter<ListForumAdapter.ListViewHolder>{
    private ArrayList<Forum> listForum;
    private OnItemClickCallback onItemClickCallback;

    public ListForumAdapter(ArrayList<Forum> list) {
        this.listForum = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forum,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Forum forum = listForum.get(position);
        holder.txt_judul.setText(forum.getJudul());
        if (forum.getLinkImg() != null){
            Picasso.get().load(forum.getLinkImg()).into(holder.iv_gambar);
        }
        holder.txt_desc.setText(forum.getDeskripsi());
        FirebaseDatabase.getInstance().getReference("users").child(forum.getAuthor()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.txt_username.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listForum.get(holder.getAdapterPosition()));
            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getImageURL() != null){
                    Picasso.get().load(user.getImageURL()).into(holder.iv_user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listForum.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView txt_judul,txt_desc,txt_username;
        ImageView iv_gambar, iv_user;
        public ListViewHolder(@NonNull View itemView){
            super(itemView);
            iv_user = itemView.findViewById(R.id.profile);
            txt_judul = itemView.findViewById(R.id.judul);
            txt_desc = itemView.findViewById(R.id.isi);
            txt_username = itemView.findViewById(R.id.nama);
            iv_gambar = itemView.findViewById(R.id.gbrforum);
        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(Forum data);
    }
}