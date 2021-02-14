package com.diazs.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
//    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        mTextView = findViewById(R.id.abaikanteksini);

        //kalo user mau posting
        Button buttonPosting = findViewById(R.id.button_posting);
        buttonPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PilihPostinganActivity pilihPostingan = new PilihPostinganActivity();
                pilihPostingan.show(getSupportFragmentManager(), "pilihPosting");
            }
        });
    }

//    @Override
//    public void OnButtonClicked(String text) {
//        mTextView.setText(text);
//    }
}