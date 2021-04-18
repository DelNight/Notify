package com.diazs.notify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Model.Candidate;
import com.diazs.notify.ProfilCalon;
import com.diazs.notify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailVotingAdapter extends RecyclerView.Adapter<DetailVotingAdapter.DetailVotingViewHolder> {
    private ArrayList<Candidate> listCandidates;
    private Context context;

    public DetailVotingAdapter(ArrayList<Candidate> listCandidates, Context context) {
        this.listCandidates = listCandidates;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailVotingAdapter.DetailVotingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_detail_voting, parent, false);
        return new DetailVotingAdapter.DetailVotingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVotingAdapter.DetailVotingViewHolder holder, int position) {
        Candidate candidate = listCandidates.get(position);
        holder.tvNama.setText(candidate.getNama());
        if (candidate.getUrlFoto() != "" && candidate.getUrlFoto() != null){
            Picasso.get().load(candidate.getUrlFoto()).into(holder.gambarcalon);
        }
        holder.tvJmlSuara.setText(candidate.getJumlahSuara() + " Suara");
        holder.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, ProfilCalon.class);
                    intent.putExtra("CANDIDATE", candidate);
                    context.startActivity(intent);
                }catch (Exception e){
                    System.out.println("Debug : "+ e.getMessage());
                }
            }
        });

        holder.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                candidate.setJumlahSuara(candidate.getJumlahSuara() + 1);
                FirebaseDatabase.getInstance().getReference("voting").child(candidate.getIdVoting()).child("agregate").child(candidate.getIdCandidate()).child("jumlahSuara").setValue(candidate.getJumlahSuara()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Suara berhasil ditambahkan ", Toast.LENGTH_LONG).show();
                            holder.tvJmlSuara.setText(candidate.getJumlahSuara() + " Suara");
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCandidates.size();
    }

    public class DetailVotingViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJmlSuara;
        Button btnProfile, btnPilih;
        ImageView gambarcalon;

        public DetailVotingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.namacalon);
            tvJmlSuara = (TextView) itemView.findViewById(R.id.jmlvote);
            btnProfile = (Button) itemView.findViewById(R.id.btn_detail_calon);
            btnPilih = (Button) itemView.findViewById(R.id.btnpilih);
            gambarcalon = itemView.findViewById(R.id.gambarcalon);
        }
    }
}
