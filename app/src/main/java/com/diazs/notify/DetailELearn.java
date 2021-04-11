package com.diazs.notify;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diazs.notify.Model.Materi;

public class DetailELearn extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Materi materi = getIntent().getParcelableExtra("MATERI");
        Toast.makeText(this, "Materi : " + materi.getJudulMateri(), Toast.LENGTH_SHORT).show();
    }
}
