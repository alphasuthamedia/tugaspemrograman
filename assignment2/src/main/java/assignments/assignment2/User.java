package assignments.assignment2;

import java.util.ArrayList;
import assignments.assignment3.payment.*;

public class User {
    
    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    public String role;
    private DepeFoodPaymentSystem payment;
    private String lokasi;
    private long saldo;

    /* Constructor Solusi TP2*/
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        orderHistory = new ArrayList<Order>();
    }

    /* Constructor yang disesuaikan TP3*/
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.saldo = saldo; // Jumlah saldo yang dimiliki pengguna
        this.payment = payment; // Methode payment yang digunakan oleh pengguna
        orderHistory = new ArrayList<Order>();
    }

    /* Method ini digunakan jika Constructor
     * yang dipilih menggunakan Constructor TP2
     */
    public void setPaymentSystem(DepeFoodPaymentSystem payment) {
        this.payment = payment;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    /* Method default */
    public DepeFoodPaymentSystem getPaymentSystem() {
        return payment;
    }
    public String getEmail() {
        return email;
    }
    public String getNama() {
        return nama;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    public long getSaldo() {
        return saldo;
    }
    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

}
