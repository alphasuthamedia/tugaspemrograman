package assignments.assignment2;
// package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment1.*;
// import assignments.assignment1.OrderGenerator;

import assignments.assignment1.OrderGenerator;

// import assignments.assignment1.OrderGenerator;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList;

    /* Define userLoggedIn
     * Sengaja diletakkan diluar method main agar bisa diakses oleh method lainnya
     */
    private static User userLoggedIn;

    public static void main(String[] args) {
        /** Initialize user list */
        initUser();

        boolean programRunning = true;
        while (programRunning) {
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if (command == 1) {
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                /* Cek apakah user ada dalam database */
                userLoggedIn = getUser(nama, noTelp);

                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                }

                /* Cek apakah user ada */
                boolean isLoggedIn = true;

                /* adalah admin atau customer */
                if (userLoggedIn.role == "Customer") {
                    System.out.print("Selamat datang " + nama + "!");
                    while (isLoggedIn) {
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan();
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    System.out.println("Selamat datang Admin!");
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            } else if (command == 2) {
                programRunning = false;
            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    } 

    /* Method untuk mendapatkan user berdasarkan nama dan nomor telepon 
     * Jika user tidak ditemukan, maka akan mengembalikan null
     * Jika user ditemukan, maka akan mengembalikan object user
     * Jika user adalah admin, maka akan mengembalikan object user dengan role "Admin"
    */
    public static User getUser(String nama, String nomorTelepon) {
        for (User user : userList) {
            if (user.nama.equals(nama) && user.nomorTelepon.equals(nomorTelepon)) {
                return user;
            }
        }
        return null;
    }

    public static void handleBuatPesanan() {
        System.out.println("--------------Buat Pesanan----------------");
        while (true){
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            /* Cek apakah restoran ada di database */
            boolean isExist = false;
            for (Restaurant resto : restoList){
                if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                    isExist = true;
                    break;
                }
            }

            if (isExist == false){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine();

            if (tanggalPemesanan.matches("^\\d{2}/\\d{2}/\\d{4}$") == false){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = input.nextInt();
            input.nextLine(); // DUMMY

            System.out.println("Order: ");
            
            String[] listPesanan = new String[jumlahPesanan];
            for (int i = 0; i < jumlahPesanan; i++) {
                listPesanan[i] = input.nextLine();
            }

            /* Cek apakah pesanan ada di restoran */
            int counterRestauranValid = 0;
            String tempSplittedString;
            String tempListPesanan;
            for (int j = 0; j < jumlahPesanan; j++){
                for (Restaurant resto : restoList){
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                        for (int i = 0; i < resto.getMenu().size(); i++){
                            String tempString = ""+resto.getMenu().get(i); // agar terbaca sebagai string lalu diconcat
                            String[] splittedTempString = tempString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

                            tempSplittedString = splittedTempString[0]; tempSplittedString = tempSplittedString.trim();
                            tempListPesanan = listPesanan[j]; tempListPesanan = tempListPesanan.trim();

                            if (tempListPesanan.equals(tempSplittedString)) {
                                counterRestauranValid++;
                            }
                            tempString = "";
                        }
                    }
                }
            }
            if (counterRestauranValid < jumlahPesanan) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                System.out.println();
                continue;
            }

            /* Define ongkir */
            int ongkir = 0; // initialize ongkir
            for (User user : userList) {
                if (user.nama.equals(userLoggedIn.nama) && user.nomorTelepon.equals(userLoggedIn.nomorTelepon)) {
                    String lokasi = user.lokasi;
                    if (lokasi.equals("U")) {
                        ongkir = 20000;
                    } else if (lokasi.equals("T")) {
                        ongkir = 35000;
                    } else if (lokasi.equals("S")) {
                        ongkir = 40000;
                    } else if (lokasi.equals("B")) {
                        ongkir = 60000;
                    } else if (lokasi.equals("P")) {
                        ongkir = 10000;
                    }
                }
            }

            /* Define resto */
            Restaurant inputResto = new Restaurant(namaRestoran);

            /* Define menu */
            Menu[] menu = new Menu[jumlahPesanan];
            
            double hargaMaknaan;
            for (int j = 0; j < jumlahPesanan; j++){
                for (Restaurant resto : restoList){
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                        for (int i = 0; i < resto.getMenu().size(); i++){
                            String tempString = ""+resto.getMenu().get(i); // agar terbaca sebagai string lalu diconcat
                            String[] splittedTempString = tempString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

                            String args1 = splittedTempString[0].trim();
                            String args2 = listPesanan[j].trim();

                            if (args1.equals(args2)) {
                                hargaMaknaan = Double.parseDouble(splittedTempString[1]);
                                menu[j] = new Menu(listPesanan[j], hargaMaknaan);
                            }
                        }
                    }
                }
            }    
        
            /* Generate OrderID */
            String orderID = OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, userLoggedIn.nomorTelepon);

            /* Define Order */
            Order order = new Order(orderID, tanggalPemesanan, ongkir, inputResto, menu);

            /* Add order to user */
            for (User user : userList){
                if (userLoggedIn.nama.equals(user.nama) && userLoggedIn.nomorTelepon.equals(user.nomorTelepon)){
                    user.addOrderHistory(order);
                }
            }
            System.out.print("Pesanan dengan ID " + orderID + " diterima.");
            break;
        }
    }

    /* Method untuk menangani cetak bill 
    * Method ini akan menampilkan bill berdasarkan orderID */
    public static void handleCetakBill() {
        System.out.println("--------------Cetak Bill----------------");
        while (true){
            System.out.print("Masukkan Order ID: ");
            String idPesanan = input.nextLine();
            System.out.println();

            boolean isExist = false;
            for (User user : userList){
                for (int i= 0; i < user.orderHistory.size(); i++){
                    if (idPesanan.equals(user.orderHistory.get(i).orderID)){
                        isExist = true;
                        System.out.println("Bill: ");
                        System.out.println("ID Pesanan: " + user.orderHistory.get(i).orderID);
                        System.out.println("Tanggal Pemesanan: " + user.orderHistory.get(i).tanggalPemesanan);
                        System.out.println("Restaurant: " + user.orderHistory.get(i).restaurant.getNama());
                        System.out.println("Lokasi Pengiriman : " + user.lokasi);
                        System.out.println("Status Pengiriman : " + ((user.orderHistory.get(i).getOrderFinished() == true) ? "Finised" : "Not Finished"));
                        System.out.println("Pesanan: ");
                        for (int j = 0; j < user.orderHistory.get(i).items.length; j++){
                            int hargaPerMakanan = (int) user.orderHistory.get(i).items[j].harga;
                            System.out.println("- " + user.orderHistory.get(i).items[j].namaMakanan + " " + hargaPerMakanan);
                        }
                        System.out.println("Biaya ongkos kirim: Rp " + user.orderHistory.get(i).biayaOngkosKirim);
                        double totalHarga = 0;
                        for (int j = 0; j < user.orderHistory.get(i).items.length; j++){
                            totalHarga += user.orderHistory.get(i).items[j].harga;
                        }
                        totalHarga += user.orderHistory.get(i).biayaOngkosKirim; // tambahkan ongkir
                        int totalHargaInt = (int) totalHarga;
                        System.out.print("Total biaya: Rp " + totalHargaInt);
                        if (i < user.orderHistory.size() - 1){
                            System.out.println();
                            System.out.println();
                        }
                    }
                }
            }
            
            if (isExist == false){
                System.out.println("Order ID tidak dapat ditemukan.\n");
            } else {
                break;
            }
        }
    }

    /* Method untuk menangani lihat menu 
    Method ini akan menampilkan menu berdasarkan nama restoran
    Menu telah diurutkan berdasarkan harga dan nama */
    public static void handleLihatMenu() {
        System.out.println("--------------Lihat Menu----------------");
        while (true){
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            
            boolean isExist = false;

            for (Restaurant resto : restoList){
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    isExist = true;           
                    for (int i = 0; i < resto.getMenu().size(); i++){
                        String[] tempNamaMakanan = new String[resto.getMenu().size()];
                        double[] tempHargaMakanan = new double[resto.getMenu().size()];

                        tempNamaMakanan[i] = resto.getMenu().get(i).namaMakanan;
                        tempHargaMakanan[i] = resto.getMenu().get(i).harga;
                    }
                }
            }

            /* Pengurutan berdasrkan harga */
            if (isExist == true){
                for (Restaurant resto : restoList){
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                        for (int i = 0; i < resto.getMenu().size(); i++){
                            for (int j = i+1; j < resto.getMenu().size(); j++){
                                if (resto.getMenu().get(i).harga > resto.getMenu().get(j).harga){
                                    Menu temp = resto.getMenu().get(i);
                                    resto.getMenu().set(i, resto.getMenu().get(j));
                                    resto.getMenu().set(j, temp);
                                }
                            }
                        }
                    }
                }
            }

            /* Pengurutan berdasrkan abjad atau nama menu */
            if (isExist == true){
                for (Restaurant resto : restoList){
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                        for (int i = 0; i < resto.getMenu().size(); i++){
                            for (int j = i+1; j < resto.getMenu().size(); j++){
                                if (resto.getMenu().get(i).harga == resto.getMenu().get(j).harga){
                                    if (resto.getMenu().get(i).namaMakanan.compareTo(resto.getMenu().get(j).namaMakanan) > 0){
                                        Menu temp = resto.getMenu().get(i);
                                        resto.getMenu().set(i, resto.getMenu().get(j));
                                        resto.getMenu().set(j, temp);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /* Add menu ke dalam database */
            for (Restaurant resto : restoList){
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    System.out.println("Menu:");
                    for (int i = 0; i < resto.getMenu().size(); i++){
                        double printHargaMakanan = resto.getMenu().get(i).harga;
                        int printHargaMakananInt = (int) printHargaMakanan;
                        System.out.print((i+1) + ". " + resto.getMenu().get(i).namaMakanan + printHargaMakananInt);
                        if (i < resto.getMenu().size() - 1){
                            System.out.println();
                        }
                    }
                }
            }

            if (isExist == false){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
            } else {
                break;
            }
        }
    }

    /* Method untuk menangani update status pesanan */
    public static void handleUpdateStatusPesanan() {
        System.out.println("--------------Update Status Pesanan----------------");
        while(true){
            System.out.print("Order ID: ");
            String idPesanan = input.nextLine();
            System.out.print("Status: ");
            String status = input.nextLine();

            if (status.equalsIgnoreCase("Selesai")) {
                boolean isExist = false;
                for (User user: userList){
                    for (int i = 0; i < user.orderHistory.size(); i++){
                        if (idPesanan.equals(user.orderHistory.get(i).orderID)){
                            if (user.orderHistory.get(i).getOrderFinished() == true) {
                                System.out.println("Status pesanan dengan ID " + idPesanan + " tidak berhasil diupdate!");
                                isExist = true;
                            } else {
                                user.orderHistory.get(i).setOrderFinished(true);
                                isExist = true;
                                System.out.print("Status pesanan dengan ID " + idPesanan + " berhasil diupdate!");
                            }
                        }
                    }
                }

                if (isExist == false){
                    System.out.println("Order ID tidak dapat ditemukan.\n");
                } else {
                    break;
                }
            } else {
                System.out.println("Status tidak valid, silakan coba kembali.\n");
            }
        }
    }

    /* Method untuk menangani tambah restoran */
    public static void handleTambahRestoran() {
        System.out.println("--------------Tambah Restoran----------------");

        while (true) {
            System.out.print("Nama: ");
            String namaRestoran = input.nextLine();

            /* Handle apakah nama restoran kurang dari 4 string */ 
            if (namaRestoran.length() < 4) {
                System.out.println("Nama restoran tidak valid\n");
                continue;
            }

            /* handle apakah restoran dengan nama yang sama sudah ada */
            boolean isExist = false; // initialize isExist to false
            for (Restaurant resto : restoList) {
                if (resto.getNama().equals(namaRestoran)) {
                    System.out.println("Resoran dengan nama " + namaRestoran + " sudah  pernah terdaftar." + '\s'
                    + "Mohon masukkan nama yang berbeda!\n");
                    isExist = true;
                    break;
                }
            }

            if (isExist == true) {
                continue;
            }
            
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine();
            ArrayList<Menu> menuList = new ArrayList<>();
            
            /* temporary array to store namaMakanan */
            String[] tempNamaMakanan = new String[jumlahMakanan];
            for (int i = 0; i < jumlahMakanan; i++) {
                String namaMakanan = input.nextLine();
                tempNamaMakanan[i] = namaMakanan;
            }

            /* Cek apakah format nama makanan valid */
            boolean isFormatValid = true;
            for (int i = 0; i < jumlahMakanan; i++) {
                if (tempNamaMakanan[i].matches("^[A-Za-z\\s]+\\d+$") == false) {
                    isFormatValid = false;
                }
            }

            /* jika format nama makanan valid, maka masukkan ke dalam menuList */
            if (isFormatValid == true){
                for (int i = 0; i < jumlahMakanan; i++){
                String[] splitMenuMakanan = tempNamaMakanan[i].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                Menu menuMakan = new Menu(splitMenuMakanan[0], Double.parseDouble(splitMenuMakanan[1]));
                menuList.add(menuMakan);
                }
                /* Create new Restaurant object and add it to restoList if and only if format namaMakanan valid */
                Restaurant resto = new Restaurant(namaRestoran);
                resto.setMenu(menuList);
                restoList.add(resto);
                System.out.print("Restaurant " + namaRestoran + " berhasil terdaftar.\n");
                break;
            } else {
                System.out.println("Harga menu harus bilangan bulat!\n");
                continue;
            }
            
        }
    }

    /* Method untuk menangani hapus restoran */
    public static void handleHapusRestoran() {
        System.out.println("--------------Hapus Restoran----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();

            boolean sucsesfullyRemoved = false;

            /* Cek apakah restoran ada di database */
            for (Restaurant resto : restoList) {
                if (namaRestoran.equalsIgnoreCase(resto.getNama())) {
                    restoList.remove(resto);
                    System.out.println("Restoran  berhasil dihapus.");
                    sucsesfullyRemoved = true;
                    break;
                }
            }
            if (sucsesfullyRemoved == false) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            } else {
                break;
            }
        }
    }

    public static void initUser() {
        userList = new ArrayList<User>();
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));
        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin() {
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}