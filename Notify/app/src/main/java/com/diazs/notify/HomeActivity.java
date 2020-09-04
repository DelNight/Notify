package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView buttonPosting = findViewById(R.id.button_posting);
        buttonPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PilihPostinganActivity pilihPostingan = new PilihPostinganActivity();
                pilihPostingan.show(getSupportFragmentManager(), "pilihPosting");
            }
        });
    }
}