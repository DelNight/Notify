package com.diazs.notify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diazs.notify.Model.Agregate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilCalon extends AppCompatActivity {
    TextView tvNama, tvJmlVote, tvProfil, tvVisi, tvMisi;
    Button btnPilih;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_calon);

        tvNama = findViewById(R.id.nama_calon);
        tvJmlVote = findViewById(R.id.jmlvote);
        tvProfil = findViewById(R.id.tv_profil);
        tvVisi = findViewById(R.id.visi);
        tvMisi = findViewById(R.id.misi);
        btnPilih = findViewById(R.id.btn_pilih);

        Agregate agregate = getIntent().getParcelableExtra("AGREGATE");

        tvNama.setText(agregate.getNama());
        tvJmlVote.setText(agregate.getJumlahSuara() + " Suara");
        tvProfil.setText(agregate.getNama());
        tvVisi.setText(agregate.getVisi());
        tvMisi.setText(agregate.getMisi());
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregate.setJumlahSuara(agregate.getJumlahSuara() + 1);
                System.out.println("Debug oi : " + agregate.getJumlahSuara());
                FirebaseDatabase.getInstance().getReference("voting").child(agregate.getIdVoting()).child("agregate").child(agregate.getIdAgregate()).child("jumlahSuara").setValue(agregate.getJumlahSuara()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfilCalon.this, "Suara berhasil ditambahkan ", Toast.LENGTH_LONG).show();
                            tvJmlVote.setText(agregate.getJumlahSuara() + " Suara");
                        }
                    }
                });
            }
        });
    }
}
