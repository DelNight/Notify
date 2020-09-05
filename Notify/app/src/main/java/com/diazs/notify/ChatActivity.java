package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button btnTambahObrolan = findViewById(R.id.btnTambahObrolan);
        btnTambahObrolan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahObrolanActivity tambahObrolan = new TambahObrolanActivity();
                tambahObrolan.show(getSupportFragmentManager(), "tambahObrolan");
            }
        });
    }
}