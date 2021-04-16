package com.diazs.notify;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.diazs.notify.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.nama)
    EditText nama;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.kelas)
    Spinner kelasSpinner;
    @BindView(R.id.jenkel)
    RadioGroup jenkel;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_up_button)
    Button signUpButton;
    @BindView(R.id.btn_back)
    ImageButton back;

    private FirebaseAuth firebaseAuth;
    private RadioButton radioButton;
    DatabaseReference dbUsers;
    DatabaseReference dbkelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbkelas = FirebaseDatabase.getInstance().getReference("kelas");

//        dbkelas.child("kelas").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                final List<String> kelas = new ArrayList<String>();
//
//                for (DataSnapshot kelasSnapshot : dataSnapshot.getChildren()) {
//                    String namaKelas = kelasSnapshot.child("nama_kelas").getValue(String.class);
//                    kelas.add(namaKelas);
//                }
//                ArrayAdapter<String> kelasAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_item, kelas);
//                kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                kelasSpinner.setAdapter(kelasAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        ButterKnife.bind(this);//using butterknife fot finding widgets
        //click R.layout.activity_signup press alt + enter to generate

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.jenkel);

        if (radioGroup.getCheckedRadioButtonId() == R.id.male){
            female.setChecked(false);
        }
        else{
            male.setChecked(false);
        }

        //firebase authentication instance
        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.registerUser();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void registerUser() {
        String name = nama.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        int role = 3;

        int selectedId = jenkel.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String jeniskel = radioButton.getText().toString().trim();

        String noHp = "08**********";
        String idkelas = kelasSpinner.getSelectedItem().toString();
        String status = "offline";
        String bio = "Avaiable";
        String image = "";
        String search = userName.toLowerCase();

        if(TextUtils.isEmpty(name)){
            showToast("Enter Your Name!");
            return;
        }
        if (idkelas == null){
            showToast("Enter Your Class!");
            return;
        }
        if(jeniskel.matches("") || jeniskel.isEmpty() || jeniskel == null || selectedId == -1 ||TextUtils.isEmpty(jeniskel)){
            showToast("Enter Your Gender!");
            return;
        }

        if(TextUtils.isEmpty(userName)){
            showToast("Enter Username!");
            return;
        }

        if (TextUtils.isEmpty(userEmail)) {
            showToast("Enter email address!");
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            showToast("Enter Password!");
            return;
        }

        if(userPassword.length() < 6){
            showToast("Password too short, enter minimum 6 characters");
            return;
        }

        dbUsers.orderByChild("email").equalTo(userEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    showToast("Email Already Exist!");
                }
                else{
                    //register user
                    firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "New user registration: " + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        SignupActivity.this.showToast("Authentication failed. " + task.getException());
                                    } else {
                                        String id = firebaseAuth.getUid();
                                        User user = new User(id, name, role, idkelas, jeniskel, userName, userEmail, noHp, image, bio, status, search);
                                        dbUsers.child(id).setValue(user);
                                        SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        SignupActivity.this.finish();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}