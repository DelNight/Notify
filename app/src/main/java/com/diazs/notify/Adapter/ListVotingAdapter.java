package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Voting;
import com.diazs.notify.R;

import java.util.ArrayList;


public class ListVotingAdapter extends RecyclerView.Adapter<ListVotingAdapter.ListViewHolder> {
    private ArrayList<Voting> listVoting;
    private OnItemClickCallback onItemClickCallback;

    public ListVotingAdapter(ArrayList<Voting> list) {
        this.listVoting = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_voting,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVotingAdapter.ListViewHolder holder, int position) {
        Voting voting = listVoting.get(position);
        holder.tvJudul.setText(voting.getJudulPosting());
        holder.tvDetail.setText(voting.getDeskripsiVoting());
        holder.tvUsername.setText(voting.getKadaluwarsa());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listVoting.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVoting.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul,tvDetail,tvUsername;

        public ListViewHolder(@NonNull View itemView){
            super(itemView);
            tvDetail = itemView.findViewById(R.id.txt_desc);
            tvJudul = itemView.findViewById(R.id.txt_judul);
            tvUsername = itemView.findViewById(R.id.txt_username);
        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(Voting data);
    }
}
