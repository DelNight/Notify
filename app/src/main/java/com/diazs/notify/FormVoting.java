package com.diazs.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.diazs.notify.Model.Voting;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FormVoting extends AppCompatActivity {
    EditText spinner;
    DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;
    EditText inpJudul, inpDeskripsi;
    Button btnSubmit;
    DatabaseReference dbVoting;
    FirebaseAuth firebaseAuth;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_voting);

        spinner = findViewById(R.id.spinner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormVoting.this,android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                date = day+"/"+month+"/"+year;
                spinner.setText(date);
            }
        };

        inpJudul = findViewById(R.id.inp_judul);
        inpDeskripsi = findViewById(R.id.inp_deskripsi);

        btnSubmit = findViewById(R.id.btn_submit);

        dbVoting = FirebaseDatabase.getInstance().getReference("voting");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judulPostingan = inpJudul.getText().toString();
                String deskripsi =  inpDeskripsi.getText().toString();
                Voting voting = new Voting();
                voting.setJudulPosting(judulPostingan);
                voting.setDeskripsiVoting(deskripsi);
                voting.setKadaluwarsa(date);
                String key = dbVoting.push().getKey();
                voting.setIdVoting(key);
                dbVoting.child(key).setValue(voting).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FormVoting.this,"Voting Berhasil Dipost", Toast.LENGTH_LONG).show();
                        inpJudul.setText("");
                        inpDeskripsi.setText("");
                        spinner.setText("");
                    }
                });
            }
        });
    }

}