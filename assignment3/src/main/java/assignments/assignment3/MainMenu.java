package assignments.assignment3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment2.Restaurant;
import assignments.assignment2.User;
import assignments.assignment3.LoginManager;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class MainMenu {
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    public static void main(String[] args) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        /* Insialisasi User dan restoList*/
        initUser();
        restoList = new ArrayList<Restaurant>();

        MainMenu mainMenu = new MainMenu(new Scanner(System.in), new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
    }

    public void run(){
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }

    private void login(){
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        // TODO: Validasi input login
        if (getUser(nama, noTelp) != null) {

            /* Inisisasi user yang login
             * get CLI yang sesuai dengan tipe user
             */
            User userLoggedIn = getUser(nama, noTelp);

            if (userLoggedIn.role.equals("Admin")) {
                System.out.print("Selamat datang Admin!");
                AdminSystemCLI adminSystemCLI = (AdminSystemCLI) loginManager.getSystem(userLoggedIn.role);

                boolean controller = true;
                while (controller) {
                    adminSystemCLI.displayMenu();
                    int menuSelector = input.nextInt();
                    input.nextLine(); // flush

                    controller = adminSystemCLI.handleMenu(menuSelector);

                    /* Tambahkan restoList ke restoList */
                    if ((menuSelector == 1) || (menuSelector == 2)){
                        /* Perbarui restoList */
                        restoList.clear(); // flush restolist (dimungkinkan sudah ada isinya)
                        for (Restaurant adjustedRestaurant : adminSystemCLI.adjustRestaurants()) {
                            restoList.add(adjustedRestaurant);
                        }
                    }
                }
            } else {
                System.out.println("Selamat datang " + userLoggedIn.getNama());
                CustomerSystemCLI customerSystemCLI = (CustomerSystemCLI) loginManager.getSystem(userLoggedIn.role);

                boolean controller = true;
                while (controller) {
                    customerSystemCLI.displayMenu();
                    int menuSelector = input.nextInt();
                    input.nextLine(); // flush

                    customerSystemCLI.setUserLoggedIn(userLoggedIn); // set userLoggedIn
                    customerSystemCLI.setTempUserList(userList); // set tempUserList --> jika ada perubahan pada useList tidak akan mempengaruhi tempUserList karena dynamic variable
                    customerSystemCLI.setRestoList(restoList); // set restoList
                    controller = customerSystemCLI.handleMenu(menuSelector);
                    
                }
            }
        } else {
            System.out.println("User tidak ditemukan.\n");
            return;
        }
    }

    private static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void initUser(){
        userList = new ArrayList<User>();

        //TODO: Adjust constructor dan atribut pada class User di Assignment 2
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }

    private User getUser(String nama, String noTelp){
        for (User user : userList) {
            if (user.getNama().equals(nama) && user.getNomorTelepon().equals(noTelp)) {
                return user;
            }
        }
        return null; // jika dan hanya jika user tidak ditemukan
    }
}
