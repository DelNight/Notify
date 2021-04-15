package com.diazs.notify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diazs.notify.Model.Candidate;
import com.diazs.notify.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FormCandidate extends AppCompatActivity {
    public static final String EXTRA_ID = "id voting";
    EditText nama, visi, misi;
    TextView maker;
    Button btn_submit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agregate);

        maker = findViewById(R.id.name);
        nama = findViewById(R.id.edt_nama);
        visi = findViewById(R.id.edt_visi);
        misi = findViewById(R.id.edt_misi);

        btn_submit = findViewById(R.id.btn_ok);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidate candidate = new Candidate();
                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("voting");
                String id = dbF.push().getKey();
                candidate.setIdCandidate(id);
                candidate.setNama(nama.getText().toString());
                candidate.setMisi(misi.getText().toString());
                candidate.setVisi(visi.getText().toString());
                candidate.setIdVoting(getIntent().getStringExtra(EXTRA_ID));

                dbF.child(getIntent().getStringExtra(EXTRA_ID)).child("candidate").child(id).setValue(candidate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FormCandidate.this,"Kandidat berhsail ditambahkan",Toast.LENGTH_LONG).show();
                        nama.setText("");
                        visi.setText("");
                        misi.setText("");
                    }
                });
            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                maker.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
