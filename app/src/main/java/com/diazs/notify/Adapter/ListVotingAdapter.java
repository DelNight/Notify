package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Voting;
import com.diazs.notify.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListVotingAdapter extends RecyclerView.Adapter<ListVotingAdapter.ListViewHolder>{
    private ArrayList<Voting> listVoting;
    private ListVotingAdapter.OnItemClickCallback onItemClickCallback;


    public ListVotingAdapter(ArrayList<Voting> list) {
        this.listVoting = list;
    }


    public void setOnItemClickCallback(ListVotingAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListVotingAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forum,parent,false);
        return new ListVotingAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVotingAdapter.ListViewHolder holder, int position) {
        Voting voting =listVoting.get(position);
        holder.txt_judul.setText(voting.getJudulPosting());
        holder.txt_desc.setText(voting.getDeskripsiVoting());
        holder.txt_username.setText(voting.getKadaluwarsa());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listVoting.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listVoting.size();
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
        void onItemClicked(Voting data);
    }
}