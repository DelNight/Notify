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
import com.diazs.notify.Model.Comment;
import com.diazs.notify.Model.DateFormater;
import com.diazs.notify.Model.Learn;
import com.diazs.notify.Model.User;
import com.diazs.notify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.KomentarViewHolder> {
    private ArrayList<Comment> komentarArrayList;
    private Context context;

    public KomentarAdapter(ArrayList<Comment> komentarArrayList, Context context){
        this.komentarArrayList = komentarArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public KomentarAdapter.KomentarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_komentar, parent, false);
        return new KomentarAdapter.KomentarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KomentarViewHolder holder, int position) {
        Comment komentar = komentarArrayList.get(position);
        holder.komentar.setText(komentar.getIsiKomentar());
        long perbedaanWaktu = System.currentTimeMillis() - komentar.getCreatedAt();

        long diffHours = perbedaanWaktu / (60 * 60 * 1000) % 24;
        holder.waktu.setText(String.valueOf(diffHours) + "h ago");

        FirebaseDatabase.getInstance().getReference("users").child(komentar.getCommenter()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User commenter = snapshot.getValue(User.class);
                holder.username.setText(commenter.getNama());
                if (commenter.getImageURL() != null && !commenter.getImageURL().equals("")){
                    Glide.with(context).load(commenter.getImageURL()).into(holder.imageAccount);
                }else{
                    Glide.with(context).load(context.getDrawable(R.drawable.ic_baseline_account_circle_24)).into(holder.imageAccount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return komentarArrayList.size();
    }



    public class KomentarViewHolder extends RecyclerView.ViewHolder {
        TextView username, komentar, waktu;
        ImageView imageAccount;
        public KomentarViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            komentar = itemView.findViewById(R.id.komentar);
            waktu = itemView.findViewById(R.id.waktu);
            imageAccount = itemView.findViewById(R.id.image_account);
        }
    }
}