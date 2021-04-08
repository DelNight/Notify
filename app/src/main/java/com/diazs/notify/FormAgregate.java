package com.diazs.notify;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diazs.notify.Model.Agregate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormAgregate extends AppCompatActivity {
    public static final String EXTRA_ID = "id voting";
    EditText nama,visi,misi;
    Button btn_submit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agregate);
        nama = findViewById(R.id.edt_nama);
        visi = findViewById(R.id.edt_visi);
        misi = findViewById(R.id.edt_misi);
        btn_submit = findViewById(R.id.btn_ok);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregate agregate = new Agregate();
                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("voting");
                String id = dbF.push().getKey();
                agregate.setIdAgregate(id);
                agregate.setNama(nama.getText().toString());
                agregate.setMisi(misi.getText().toString());
                agregate.setVisi(visi.getText().toString());
                agregate.setIdVoting(getIntent().getStringExtra(EXTRA_ID));
                dbF.child(getIntent().getStringExtra(EXTRA_ID)).child("agregate").child(id).setValue(agregate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FormAgregate.this,"Kandidat berhsail ditambahkan",Toast.LENGTH_LONG).show();
                        nama.setText("");
                        visi.setText("");
                        misi.setText("");
                    }
                });
            }
        });
    }
}
