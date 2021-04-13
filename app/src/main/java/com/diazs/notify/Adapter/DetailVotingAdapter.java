package com.diazs.notify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.DetailVoting;
import com.diazs.notify.Model.Agregate;
import com.diazs.notify.Model.Materi;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.diazs.notify.ProfilCalon;
import com.diazs.notify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailVotingAdapter extends RecyclerView.Adapter<DetailVotingAdapter.DetailVotingViewHolder> {
    private ArrayList<Agregate> listAgregates;
    private Context context;

    public DetailVotingAdapter(ArrayList<Agregate> listAgregates, Context context) {
        this.listAgregates = listAgregates;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailVotingAdapter.DetailVotingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_detail_posting, parent, false);
        return new DetailVotingAdapter.DetailVotingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVotingAdapter.DetailVotingViewHolder holder, int position) {
        Agregate agregate = listAgregates.get(position);
        holder.tvNama.setText(agregate.getNama());
        holder.tvJmlSuara.setText(agregate.getJumlahSuara() + " Suara");
        holder.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, ProfilCalon.class);
                    intent.putExtra("AGREGATE", agregate);
                    context.startActivity(intent);
                }catch (Exception e){
                    System.out.println("Debug : "+ e.getMessage());
                }
            }
        });

        holder.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregate.setJumlahSuara(agregate.getJumlahSuara() + 1);
                FirebaseDatabase.getInstance().getReference("voting").child(agregate.getIdVoting()).child("agregate").child(agregate.getIdAgregate()).child("jumlahSuara").setValue(agregate.getJumlahSuara()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Suara berhasil ditambahkan ", Toast.LENGTH_LONG).show();
                            holder.tvJmlSuara.setText(agregate.getJumlahSuara() + " Suara");
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAgregates.size();
    }

    public class DetailVotingViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJmlSuara;
        Button btnProfile, btnPilih;

        public DetailVotingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.namacalon);
            tvJmlSuara = (TextView) itemView.findViewById(R.id.jmlvote);
            btnProfile = (Button) itemView.findViewById(R.id.btn_detail_calon);
            btnPilih = (Button) itemView.findViewById(R.id.btnpilih);
        }
    }
}
