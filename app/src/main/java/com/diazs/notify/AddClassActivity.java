package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.diazs.notify.Model.Kelas;
import com.diazs.notify.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends AppCompatActivity {
    @BindView(R.id.kelas)
    EditText namaKelas;
    @BindView(R.id.btn_tambah)
    Button btnTambah;

    DatabaseReference dbKelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        dbKelas = FirebaseDatabase.getInstance().getReference("kelas");

        ButterKnife.bind(this);//using butterknife fot finding widgets
        //click R.layout.activity_signup press alt + enter to generate

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClassActivity.this.tambahClass();
            }
        });
    }

    public void tambahClass(){
        String kelas = namaKelas.getText().toString().trim();
        String id = dbKelas.push().getKey();
        Kelas kls = new Kelas(id, kelas);
        dbKelas.child(id).setValue(kls);
    }

}