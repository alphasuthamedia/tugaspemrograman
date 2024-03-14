package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    String nama;
    ArrayList<Menu> menu;
    public Restaurant(String nama){
        // TODO: buat constructor untuk class ini
        this.nama = nama;
    }

    public void setMenu(ArrayList<Menu> menu) {
        this.menu = menu;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }
    
    public String getNama() {
        return nama;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
}
