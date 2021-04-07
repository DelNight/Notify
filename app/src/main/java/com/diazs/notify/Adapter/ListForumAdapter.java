package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Forum;
import com.diazs.notify.Model.Voting;
import com.diazs.notify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
    public void onBindViewHolder(@NonNull ListForumAdapter.ListViewHolder holder, int position) {
        Forum forum =listForum.get(position);
        holder.txt_judul.setText(forum.getJudul());
        holder.txt_desc.setText(forum.getDeskripsi());
        holder.txt_username.setText(forum.getTanggalUpload());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listForum.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listForum.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView txt_judul,txt_desc,txt_username;

        public ListViewHolder(@NonNull View itemView){
            super(itemView);
            txt_judul = itemView.findViewById(R.id.txt_judul);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_username = itemView.findViewById(R.id.txt_username);
        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(Forum data);
    }
}