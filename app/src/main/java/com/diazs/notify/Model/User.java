package com.diazs.notify.Model;

public class User {
    private String id;
    private String nama;
    private String role;
    private String kelas;
    private String jenisKelamin;
    private String username;
    private String password;
    private String email;
    private String noHP;

    public User() {
    }

    public User(String id, String nama, String role, String kelas, String jenisKelamin, String username, String password, String email, String noHP) {
        this.id = id;
        this.nama = nama;
        this.role = role;
        this.kelas = kelas;
        this.jenisKelamin = jenisKelamin;
        this.username = username;
        this.password = password;
        this.email = email;
        this.noHP = noHP;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
