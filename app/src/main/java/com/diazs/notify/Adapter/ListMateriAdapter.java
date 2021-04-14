package com.diazs.notify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.Voting;
import com.diazs.notify.R;

import java.util.ArrayList;


public class ListMateriAdapter extends RecyclerView.Adapter<ListMateriAdapter.ListViewHolder> {
    private ArrayList<Materi> listMateri;
    private OnItemClickCallback onItemClickCallback;

    public ListMateriAdapter(ArrayList<Materi> list) {
        this.listMateri = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_materi,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMateriAdapter.ListViewHolder holder, int position) {
        Materi materi = listMateri.get(position);
        holder.tvJudul.setText(materi.getJudulMateri());
        holder.tvDetail.setText(materi.getDeskripsiMateri());
        holder.tvUsername.setText(materi.getAuthorMateri());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMateri.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMateri.size();
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
        void onItemClicked(Materi data);
    }
}
