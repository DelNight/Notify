package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.ForumRev;
import com.diazs.notify.R;

import java.util.ArrayList;

public class ForumRevAdapter extends RecyclerView.Adapter<ForumRevAdapter.ForumViewHolder> {

    private ArrayList<ForumRev> forum;

    public ForumRevAdapter(ArrayList<ForumRev> forum) {
        this.forum = forum;
    }

    @NonNull
    @Override
    public ForumRevAdapter.ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumRevAdapter.ForumViewHolder holder, int position) {

        holder.pTitle.setText(forum.get(position).getPostTitle());
        holder.pDesc.setText(forum.get(position).getPostDesc());
        holder.pUsername.setText(forum.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return (forum!= null) ? forum.size() : 0;
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {

        private TextView pTitle, pDesc, pUsername;

        public ForumViewHolder (View view) {
            super(view);
            pTitle = view.findViewById(R.id.post_title);
            pDesc = view.findViewById(R.id.post_desc);
            pUsername = view.findViewById(R.id.username);
        }
    }
}
