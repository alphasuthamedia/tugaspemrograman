package main.java.assignments.assignment2;

import java.util.ArrayList;

public class User{
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    String nama;
    String nomorTelepon;
    String email;
    String lokasi;
    String role;
    ArrayList<Order> orderHistory = new ArrayList<>();
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini


    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void addOrderHistory(Order order) {
        this.orderHistory.add(order);
    }
}
