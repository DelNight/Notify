package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Forum;
import com.diazs.notify.R;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private ArrayList<Forum> dataForum;

    public ProfileAdapter(ArrayList<Forum> dataForum){
        this.dataForum = dataForum;
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
        holder.txt_username.setText(forum.getTanggalUpload());

    }

    @Override
    public int getItemCount() {
        return dataForum.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView txt_judul,txt_desc,txt_username;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_judul = itemView.findViewById(R.id.txt_judul);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_username = itemView.findViewById(R.id.txt_username);
        }
    }
}
