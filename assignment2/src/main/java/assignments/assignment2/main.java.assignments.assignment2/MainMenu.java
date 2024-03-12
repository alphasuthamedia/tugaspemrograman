package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
//import assignment1.src.main.java.assignments.assignment1.*;
// import assignments.assignment1.OrderGenerator;

import assignments.assignment1.OrderGenerator;


public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList;

    // import orderGenerator from assignment1
    OrderGenerator orderGenerator = new OrderGenerator();

    public static void main(String[] args) {

        // initialize user list
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

                // cek apakah user ada di userList
                User userLoggedIn = getUser(nama, noTelp);

                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                }

                boolean isLoggedIn = true;
                if (userLoggedIn.role == "Customer") {
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
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
//                            case 5 -> isLoggedIn = false;
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

    public static User getUser(String nama, String nomorTelepon) {
        // TODO: Implementasi method untuk mendapat user dari userList
        String role;
        if (nama.equals("Admin") && nomorTelepon.equals("123456789")) {
            role = "Admin";
        }
        for (User user : userList) {
            if (user.nama.equals(nama) && user.nomorTelepon.equals(nomorTelepon)) {
                role = "Customer";
                return user;
            }
        }
        return null;
    }

    public static void handleBuatPesanan() {
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();

        System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
        String tanggalPemesanan = input.nextLine();

        System.out.print("Jumlah Pesanan: ");
        int jumlahPesanan = input.nextInt();
        input.nextLine(); // DUMMY

        System.out.println("Order: ");
        
        String[] listPesanan = new String[jumlahPesanan];
        for (int i = 0; i < jumlahPesanan; i++) {
            listPesanan[i] = input.nextLine();
        }

        // Verifikasi apakah listPesanan telah terdaftar
        for (Restaurant resto : restoList){
            System.out.println(resto.getNama());
        }
    }

    public static void handleCetakBill() {
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu() {
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan() {
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    public static void handleTambahRestoran() {
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        while (true) {
            System.out.print("Nama: ");
            String namaRestoran = input.nextLine();

            // handle apakah nama restoran kurang dari 4 string
            if (namaRestoran.length() < 4) {
                System.out.println("Nama restoran tidak valid\n");
                continue;
            }

            // handle apakah restoran dengan nama yang sama sudah ada
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
            
            // temporary array to store namaMakanan
            String[] tempNamaMakanan = new String[jumlahMakanan];
            for (int i = 0; i < jumlahMakanan; i++) {
                String namaMakanan = input.nextLine();
                tempNamaMakanan[i] = namaMakanan;
            }

            // cek jika format nama makanan tidak sesuai
            boolean isFormatValid = true;
            for (int i = 0; i < jumlahMakanan; i++) {
                if (tempNamaMakanan[i].matches("^[A-Za-z\\s]+\\d+$") == false) {
                    isFormatValid = false;
                    break;
                }
            }

            // jika format nama makanan valid, maka masukkan ke dalam menuList
            if (isFormatValid == true){
                for (int i = 0; i < jumlahMakanan; i++){
                String[] splitMenuMakanan = tempNamaMakanan[i].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                Menu menuMakan = new Menu(splitMenuMakanan[0], Double.parseDouble(splitMenuMakanan[1]));
                menuList.add(menuMakan);
                }
                // Create new Restaurant object and add it to restoList if and only if format namaMakanan valid
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

    public static void handleHapusRestoran() {
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Hapus Restoran----------------");
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();

          // coba print restorant yang ada
        for (Restaurant resto : restoList) {
            System.out.println("Nama Restoran: " + resto.getNama());
            System.out.println(resto.getMenu());
            System.out.println();
            if (namaRestoran.equals(resto.getNama())) {
                restoList.remove(resto);
                System.out.println("Restoran " + namaRestoran + " berhasil dihapus.");
                break;
            } else {
                System.out.println("Restoran tidak terdaftar pada sistem.");
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