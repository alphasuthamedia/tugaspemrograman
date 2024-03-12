package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Menu {
    String namaMakanan;
    double harga;
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Menu(String namaMakanan, double harga){
        // TODO: buat constructor untuk class ini
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    public String getNamaMakanan() {
        return this.namaMakanan;
    }

    public double getHarga() {
        return this.harga;
    }

    @Override
    public String toString() {
        return this.namaMakanan + this.harga;
    }
}
