package com.diazs.notify.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.diazs.notify.Model.Learn;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.diazs.notify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListELearnAdapter extends RecyclerView.Adapter<ListELearnAdapter.ELearnViewHolder> {
    private ArrayList<Materi> materiArrayList;
    private Context context;
    private ListELearnAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ListELearnAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public ListELearnAdapter(ArrayList<Materi> materiArrayList, Context context){
        this.materiArrayList = materiArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListELearnAdapter.ELearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_elearn, parent, false);
        return new ListELearnAdapter.ELearnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ELearnViewHolder holder, int position) {
        Materi materi = materiArrayList.get(position);

        holder.tvJudul.setText(materi.getJudulMateri());
        holder.tvDeskripsi.setText(materi.getDeskripsiMateri());
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(materi);
            }
        });
        FirebaseDatabase.getInstance().getReference("users").child(materi.getAuthorMateri()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.tvAuthor.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (materi.getImageUrl() != null){
            if (!materi.getImageUrl().equals("")){
                Glide.with(context).load(materi.getImageUrl()).into(holder.kontenFoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return materiArrayList.size();
    }

    public class ELearnViewHolder extends RecyclerView.ViewHolder {
        ImageView kontenFoto;
        TextView tvJudul, tvDeskripsi, tvAuthor, tvDetail;
        public ELearnViewHolder(@NonNull View itemView) {
            super(itemView);
            kontenFoto = (ImageView) itemView.findViewById(R.id.konten_foto);
            tvJudul = (TextView) itemView.findViewById(R.id.tv_judul);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.tv_deskripsi);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Materi data);
    }
}