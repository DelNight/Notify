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
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.diazs.notify.Model.User;
import com.diazs.notify.Model.Voting;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FormVoting extends AppCompatActivity {
    EditText spinner;
    TextView tvNama;
    DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;
    EditText inpJudul, inpDeskripsi;
    Button btnSubmit;
    DatabaseReference dbVoting;
    String date;
    LottieAnimationView loading;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_voting);
        loading = findViewById(R.id.loading_bell);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);
        tvNama = findViewById(R.id.name);
        spinner = findViewById(R.id.spinner);

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvNama.setText(user.getNama());
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        spinner.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    setDateListener();
                }
            }
        });

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateListener();
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
                FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
                voting.setVotingMaker(aut.getUid());
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

    public void setDateListener(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                FormVoting.this,android.R.style.Theme_Holo_Dialog_MinWidth
                ,setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}