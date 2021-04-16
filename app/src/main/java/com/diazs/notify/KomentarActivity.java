package com.diazs.notify;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diazs.notify.Adapter.KomentarAdapter;
import com.diazs.notify.Model.Comment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class KomentarActivity extends AppCompatActivity {
    public static String JENIS_HALAMAN = "jenis_halaman";
    public static String POST_ID = "post_id";
    EditText isiKomentar;
    ImageButton btnSubmit;
    ArrayList<Comment> listKomentar;
    RecyclerView recyclerView;
    KomentarAdapter komentarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);

        String idPost = getIntent().getStringExtra(POST_ID);
        listKomentar = new ArrayList<>();
        isiKomentar = findViewById(R.id.isi_komentar);
        btnSubmit = findViewById(R.id.btn_submit);

        FirebaseDatabase.getInstance().getReference("komentar").orderByChild("commenter").equalTo(idPost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listKomentar.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment komentar = dataSnapshot.getValue(Comment.class);
                    listKomentar.add(komentar);
                }

                komentarAdapter = new KomentarAdapter(listKomentar, KomentarActivity.this);
                setRecyclerView();
                recyclerView.setAdapter(komentarAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(isiKomentar.getText().toString())){
                    Toast.makeText(KomentarActivity.this, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }else{
                    Comment komentar = new Comment();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("komentar");
                    String idKomentar = reference.push().getKey();
                    komentar.setCommenter(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    komentar.setIdPost(idPost);
                    komentar.setIdComment(idKomentar);
                    komentar.setCreatedAt(System.currentTimeMillis());
                    komentar.setIsiKomentar(isiKomentar.getText().toString());

                    reference.setValue(komentar).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(KomentarActivity.this, "Komentar berhasil di posting!", Toast.LENGTH_SHORT).show();
                            komentarAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    public void setRecyclerView(){
        recyclerView = findViewById(R.id.recycler_komentar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}