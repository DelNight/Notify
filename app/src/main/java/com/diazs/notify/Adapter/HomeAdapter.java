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
import com.diazs.notify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<Materi> learnArrayList;
    private Context context;

    public HomeAdapter(ArrayList<Materi> learnArrayList, Context context){
        this.learnArrayList = learnArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_buat_kamu, parent, false);
        return new HomeAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        Materi materi = learnArrayList.get(position);
        holder.tvJudul.setText(materi.getJudulMateri());
        holder.tvdeskripsi.setText(materi.getDeskripsiMateri());
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
    }

    @Override
    public int getItemCount() {
        return learnArrayList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvdeskripsi, tvAuthor;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = (TextView) itemView.findViewById(R.id.tv_judul);
            tvdeskripsi = (TextView) itemView.findViewById(R.id.tv_deskripsi);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}