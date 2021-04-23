package com.diazs.notify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import
        android.graphics.drawable.ColorDrawable;
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
import com.diazs.notify.Model.Event;
import com.diazs.notify.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class InputEvent extends AppCompatActivity {
    EditText spinner;
    TextView tvNama;
    EditText judul, deskripsi;
    Button btn_submit;
    String date;
    DatabaseReference dbEvent;
    DatePickerDialog.OnDateSetListener setListener;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_event);

        spinner = findViewById(R.id.spinner);
        judul = findViewById(R.id.judulEvent);
        deskripsi = findViewById(R.id.deskripsiEvent);
        tvNama = findViewById(R.id.tvNama);

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvNama.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_submit = findViewById(R.id.btn_submit);

        dbEvent = FirebaseDatabase.getInstance().getReference("event");

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
                        InputEvent.this,android.R.style.Theme_Holo_Dialog_MinWidth
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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judulEvent = judul.getText().toString();
                String descEvent = deskripsi.getText().toString();
                Event event = new Event();
                FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
                event.setEventMaker(auth.getUid());
                event.setJudulEvent(judulEvent);
                event.setDeskripsi(descEvent);
                event.setTanggalEvent(date);
                event.setCreatedAt(System.currentTimeMillis());
                String id = dbEvent.push().getKey();
                event.setIdEvent(id);
                dbEvent.child(id).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InputEvent.this,"Voting Berhasil Dipost", Toast.LENGTH_LONG).show();
                        judul.setText("");
                        deskripsi.setText("");
                        spinner.setText("");
                    }
                });
            }
        });
    }

}