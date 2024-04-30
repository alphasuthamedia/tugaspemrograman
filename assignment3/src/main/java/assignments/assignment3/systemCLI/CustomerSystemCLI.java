package assignments.assignment3.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import assignments.assignment1.OrderGenerator;
import assignments.assignment2.Menu;
import assignments.assignment2.Order;
import assignments.assignment2.Restaurant;
import assignments.assignment2.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

public class CustomerSystemCLI extends UserSystemCLI {
    private static ArrayList<User> tempUserList;
    private static ArrayList<Restaurant> restoList;
    private static User userLoggedIn;

    private static Scanner input = new Scanner(System.in);

    @Override
    public boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo(); // template mungkin salah, seharusnya cek saldo
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    public void displayMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public void handleBuatPesanan(){
        System.out.println("---------------Buat Pesanan-----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            
            /* Cek jika belum ada restaurant yang ada dalam database */
            if(restoList == null || restoList.isEmpty()){
                System.out.println("Belum ada restoran yang terdaftar dalam sistem.");
                System.out.println();
                return;
            }

            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.validateDate(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(! validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName.toUpperCase(), tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()), 
                    restaurant, 
                    getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!\n", order.getOrderId());
            userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    void handleCetakBill(){
        System.out.println("----------------Cetak Bill------------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
    }

    void handleLihatMenu(){
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu()+"\n");
            return;
        }
    }

    void handleBayarBill(){
        System.out.println("----------------Bayar Bill------------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId); // temporary agar bisa digunakan selanjutnya
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            if (order.getOrderFinished()) {
                System.out.println("Pesanan dengan ID ini sudah lunas!");
            } else {
                System.out.println("");
                System.out.print(outputBillPesanan(order)+"\n");
            }
            System.out.println("Opsi Pembayaran:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");
    
            System.out.print("Pilihan Metode Pembayaran: ");
            int pilihanPembayaranUser = input.nextInt();
            input.nextLine(); // flush
            
            if ((pilihanPembayaranUser == 1) && (userLoggedIn.getPaymentSystem() instanceof CreditCardPayment)) {
                CreditCardPayment creditCardPayment = new CreditCardPayment();
                creditCardPayment.setSaldo((long) userLoggedIn.getSaldo()); // set saldo di creditCardPayment yang berasal dari saldo credit card user
                long hasilProsesPembayaran = creditCardPayment.processPayment((long) order.getTotalHarga()); // harga total yang harus dibayarkan setelah ditambah tax
                if (hasilProsesPembayaran != 0) {
                    order.getRestaurant().setSaldo((long) (hasilProsesPembayaran));
                    userLoggedIn.setSaldo((long) (userLoggedIn.getSaldo() - creditCardPayment.processPayment((long) order.getTotalHarga()))); // hitung saldo user yang sekarang yang telah dikurangi dengan harga bayar
                    order.setOrderFinished(true); // order telah finished (sudah terbayarkan)
                    System.out.println("Berhasil Membayar Bill sebesar Rp " + order.getTotalHarga() +
                                        " dengan biaya transaksi sebesar " + (-((long) order.getTotalHarga() - creditCardPayment.processPayment((long) order.getTotalHarga()))));
                }
                return;
                // }
            } else if ((pilihanPembayaranUser == 2) && (userLoggedIn.getPaymentSystem() instanceof DebitPayment)) {
                DebitPayment debitPayment = new DebitPayment();
                debitPayment.setSaldo((long) userLoggedIn.getSaldo());
                long hasilProsesPembayaran = debitPayment.processPayment((long) order.getTotalHarga());
                if (hasilProsesPembayaran != 0) {
                    order.setOrderFinished(true); // set ordernya sudah finished (sudah terbayarkan)
                    order.getRestaurant().setSaldo(hasilProsesPembayaran); // tambahkan saldo restoran tanpa dikurangi tax
                    userLoggedIn.setSaldo((long) (userLoggedIn.getSaldo() - order.getTotalHarga())); // set saldo user yang baru setelah dikurangi pembelian
                    System.out.println("Berhasil Membayar Bill sebesar Rp " + hasilProsesPembayaran);
                }
                return;
            } else if ((pilihanPembayaranUser == 1) && (!(userLoggedIn.getPaymentSystem() instanceof CreditCardPayment))) {
                System.out.println("User belum memiliki metode pembayaran ini!");
                return;
            } else if ((pilihanPembayaranUser == 2) && (!(userLoggedIn.getPaymentSystem() instanceof DebitPayment))) {
                System.out.println("User belum memiliki metode pembayaran ini!");
                return;
            }    
        }

      
    }

    public void handleCekSaldo(){
        System.out.println("\nSisa saldo sebesar Rp " + userLoggedIn.getSaldo());
    }

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    /* UserLoggedIn telah diperbaharui */
    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public static void setRestoList(ArrayList<Restaurant> restoList) {
        CustomerSystemCLI.restoList = restoList;
    }

    public static Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = restoList.stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }

    public static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan -> 
            restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

    public static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public static Order getOrderOrNull(String orderId) {
        for (User user : tempUserList) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    public static String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public static String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                         "Order ID: %s%n" +
                         "Tanggal Pemesanan: %s%n" +
                         "Nama Restoran: %s%n" +
                         "Lokasi Pengiriman: %s%n" +
                         "Status Pengiriman: %s%n"+
                         "Pesanan:%n%s%n"+
                         "Biaya Ongkos Kirim: Rp %s%n"+
                         "Total Biaya: Rp %s%n",
                         order.getOrderId(),
                         order.getTanggal(),
                         order.getRestaurant().getNama(),
                         userLoggedIn.getLokasi(),
                         !order.getOrderFinished()? "Not Finished":"Finished",
                         getMenuPesananOutput(order),
                         decimalFormat.format(order.getOngkir()),
                         decimalFormat.format(order.getTotalHarga())
                         );
    }

    public static void setTempUserList(ArrayList<User> argTempUserList) {
        tempUserList = argTempUserList;
    }
}
