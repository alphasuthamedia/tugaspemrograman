package assignments.assignment2;

public class Order {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    String orderID;
    String tanggalPemesanan;
    int biayaOngkosKirim;
    Restaurant restaurant;
    Menu[] items;
    boolean orderFinished;
    public Order(String orderID, String tanggalPemesanan, int biayaOngkosKirim, Restaurant restaurant, Menu[] items){
        // TODO: buat constructor untuk class ini
        this.orderID = orderID;
        this.tanggalPemesanan = tanggalPemesanan;
        this.biayaOngkosKirim = biayaOngkosKirim;
        this.restaurant = restaurant;
        this.items = items;
    }
    
    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    public String getOrderId() {
        return orderID;
    }

    public boolean getOrderFinished(){
        return orderFinished;   
    }

    @Override
    public String toString() {
        return orderID + tanggalPemesanan + biayaOngkosKirim + restaurant + items.toString();
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
