package com.diazs.notify.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User implements Parcelable {
    protected String id;
    protected String nama;
    protected int role;
    protected String kelas;
    protected String jenisKelamin;
    protected String username;
    protected String email;
    protected String noHP;
    protected String imageURL;
    protected String bio;
    protected String status;
    protected String search;

    public User(String id, String nama, int role, String kelas, String jenisKelamin, String username, String email, String noHP, String imageURL, String bio, String status, String search) {
        this.id = id;
        this.nama = nama;
        this.role = role;
        this.kelas = kelas;
        this.jenisKelamin = jenisKelamin;
        this.username = username;
        this.email = email;
        this.noHP = noHP;
        this.imageURL = imageURL;
        this.bio = bio;
        this.status = status;
        this.search = search;
    }

    public User() {
    }

    protected User(Parcel in) {
        id = in.readString();
        nama = in.readString();
        role = in.readInt();
        kelas = in.readString();
        jenisKelamin = in.readString();
        username = in.readString();
        email = in.readString();
        noHP = in.readString();
        imageURL = in.readString();
        bio = in.readString();
        status = in.readString();
        search = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static void setAuthStatus(String status){
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(fuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                user.setStatus(status);
                databaseReference.child(user.getId()).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nama);
        parcel.writeInt(role);
        parcel.writeString(kelas);
        parcel.writeString(jenisKelamin);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(noHP);
        parcel.writeString(imageURL);
        parcel.writeString(bio);
        parcel.writeString(status);
        parcel.writeString(search);
    }
}
