package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.User;
import com.diazs.notify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.internal.cache.DiskLruCache;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private ArrayList<Forum> dataForum;
    FirebaseDatabase dbForum = FirebaseDatabase.getInstance();
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
    private ListForumAdapter.OnItemClickCallback onItemClickCallback;

    public ProfileAdapter(ArrayList<Forum> dataForum){
        this.dataForum = dataForum;
    }
    public void setOnItemClickCallback(ListForumAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forum, parent,false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Forum forum = dataForum.get(position);
        holder.txt_judul.setText(forum.getJudul());
        holder.txt_desc.setText(forum.getDeskripsi());
        Picasso.get().load(forum.getLinkImg()).into(holder.gbrForum);
        dbForum.getReference("users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    holder.txt_username.setText(user.getNama().toString());
                    Picasso.get().load(user.getImageURL()).into(holder.imgProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataForum.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView txt_judul,txt_desc,txt_username;
        ImageView gbrForum,imgProfile;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_judul = itemView.findViewById(R.id.judul);
            txt_desc = itemView.findViewById(R.id.isi);
            txt_username = itemView.findViewById(R.id.nama);
            gbrForum = itemView.findViewById(R.id.gbrforum);
            imgProfile = itemView.findViewById(R.id.profile);
        }
    }
}
