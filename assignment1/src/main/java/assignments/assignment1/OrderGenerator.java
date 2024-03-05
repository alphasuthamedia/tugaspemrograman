package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    String namaString;
    private static final Scanner input = new Scanner(System.in);
    // Method  ini untuk menampilkan menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static void repeatingShowMenu(){
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static String generateOrderID(String argNamaRestoran, String argTanggalOrder, String argNoTelepon) {
        String orderID = "";
        // inisiasi namaRestoran
        String namaRestoran = getNamaRestoran(argNamaRestoran);
        if (namaRestoran == "Err") {
            return "";
        } else {
            orderID += namaRestoran; // concat orderID
            // inisiasi tanggalOrder
            String tanggalOrder = getTanggalOrder(argTanggalOrder);
            if (tanggalOrder == "Err") {
                return "";
            } else {
                orderID += tanggalOrder; // concat orderID
                // inisiasi noTelepon
                String noTelepon = getNoTelepon(argNoTelepon); 
                if (noTelepon == "Err") {
                    return "";
                } else {
                    orderID += noTelepon; // concat orderID
                    // inisiasi checkSum
                    String checkSum = getCheckSum(orderID);
                    if (checkSum == "Err") {
                        return "";
                    } else {
                        orderID += checkSum; // concat orderID
                    }
                }
            }
        }
        return orderID;
        }
    
    public static String generateBill(String OrderID, String lokasi){
        lokasi = lokasi.toUpperCase();
        String hargaPengiriman = "0";
        // CEK APAKAH FORMAT ORDERID VALID

        // DUMMY - JIKA KURANG DARI 16 TIDAK AAKAN TEREKSUKSI
        boolean namaRestoranValid = true;
        boolean nilaiStringIndex4_13Valid = true;
        boolean nilaiStringIndex0_13Valid = namaRestoranValid && nilaiStringIndex4_13Valid; 
        if (OrderID.length() >= 16) {
            // CEK APAKAH NAMA RESTORAN VALID (set kasus tidak valid)
            namaRestoranValid = getNamaRestoran(OrderID.substring(0, 4)) != "Err";
            // CEK APAKAH STRING INDEX 4-13 VALID
            for (int i = 0; i < 10; i++){
            if ((Integer.valueOf(OrderID.charAt(i + 4)) < 48) || (Integer.valueOf(OrderID.charAt(i + 4)) > 57)){
                nilaiStringIndex4_13Valid = false;
                break;
            }
        }
        // CEK ULANG APAKAH STRING INDEX 0 - 13 VALID
        nilaiStringIndex0_13Valid = namaRestoranValid && nilaiStringIndex4_13Valid; 
        }

        if (OrderID.length() < 16) {
            System.out.println("Order ID minimal 16 karakter");
            System.out.println();
            return "Err";
        }
        else if (OrderID.length() != 16) {
            System.out.println("Silahkan masukkan Order ID yang valid!");
            System.out.println();
            return "Err";
        } else if ((OrderID.length() == 16)){
            if (nilaiStringIndex0_13Valid == false) {
                System.out.println("Silahkan masukkan Order ID yang valid!");
                System.out.println();
                return "Err";
            } else {
            String tempOrderIDWithoutCheckSum = OrderID.substring(0, 14);
            String tempCheckSum = getCheckSum(tempOrderIDWithoutCheckSum);
            if (OrderID.equals(tempOrderIDWithoutCheckSum+tempCheckSum) == false) {
                System.out.println("Silahkan masukkan Order ID yang valid!");
                System.out.println();
                return "Err";
            } 
        }
    }
    if (lokasi.equals("U")) {
        hargaPengiriman = "20000";
    } else if (lokasi.equals("T")) {
        hargaPengiriman = "35000";
    } else if (lokasi.equals("S")) {
        hargaPengiriman = "40000";
    } else if (lokasi.equals("B")) {
        hargaPengiriman = "60000";
    } else if (lokasi.equals("P")) {
        hargaPengiriman = "10000";
    } else {
        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
        System.out.println();
        return "Err";
    }
    return "Bill:\n" + //
            "Order ID: "+OrderID+"\n" + //  
            "Tanggal Pemesanan: "+OrderID.substring(4,6)+"/"+OrderID.substring(6, 8)+"/"+OrderID.substring(8,12)+"\n" + //
            "Lokasi Pengiriman: "+lokasi+"\n" + //
            "Biaya Ongkos Kirim: Rp "+hargaPengiriman.substring(0,2) + "." + hargaPengiriman.substring(2)+"\n" + //
            "";
    }

    public static void main(String[] args) {

        System.out.println("\033[2J\033[1;1H");
        // show menu
        OrderGenerator.showMenu();
        while (true) { 
            System.out.println("--------------------------------------------");
            // input pilihan menu
            System.out.print("Pilihan menu: ");
            int getSelectMenu = input.nextInt(); 
            input.nextLine();
            
            if (getSelectMenu == 1){
                System.out.println();
                while (true) {
                    System.out.print("Nama Restoran: ");
                    String namaRestoran = input.nextLine();
                    if (getNamaRestoran(namaRestoran) == "Err") {
                        System.out.println();
                        continue; 
                }
                    System.out.print("Tanggal Pemesanan: ");
                    String tanggalOrder = input.nextLine();
                    if (getTanggalOrder(tanggalOrder) == "Err") {
                        System.out.println();
                        continue;
                }
                    System.out.print("No. Telpon: ");
                    String noTelepon = input.nextLine();
                    if (getNoTelepon(noTelepon) == "Err") {
                        System.out.println();
                        continue; 
                }
                    // print jika semua input valid
                    System.out.println("Orded ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!");
                    OrderGenerator.repeatingShowMenu();
                    break;
                }
            }
            else if (getSelectMenu == 3){
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;
            } else if (getSelectMenu == 2) {
                while (true) {
                    System.out.print("Order ID : ");
                    String getUserID = input.nextLine();
                    String getBillValid = OrderGenerator.generateBill(getUserID, "s");
                    if (getBillValid != "Err"){
                        System.out.print("Lokasi Pengiriman: ");
                        String getLokasiPengiriman = input.nextLine();
                        System.out.println();
                        // cek apakah lokasinya tersedia
                        if (OrderGenerator.generateBill(getUserID, getLokasiPengiriman) != "Err") {
                            System.out.println(OrderGenerator.generateBill(getUserID, getLokasiPengiriman)); // Print output
                            break;
                        }
                    }
                }
            } else {
                continue;
            }
        }
    }
    
    public static String getNamaRestoran(String argNamaRestoran){
        // wrapping namaRestoran
        String namaRestoran = "";
        namaRestoran = argNamaRestoran.replace(" ", "");
        if (namaRestoran.length() < 4) {    // cek namaRestoran kurang dari 4 karakter
            System.out.println("Nama restoran tidak valid!");
            return "Err";
        } else {    // return 4 karakter pertama dari namaRestoran
            namaRestoran = argNamaRestoran.substring(0, 4).toUpperCase();
            return namaRestoran;
        }
    }

    public static String getTanggalOrder(String argTanggalOrder){
        // wrapping tanggalOrder
        if (argTanggalOrder.charAt(2) != '/' || argTanggalOrder.charAt(5) != '/') {
            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
            return "Err";
            
        }
        String tanggalOrder = argTanggalOrder.replaceAll("/", "");
        if (tanggalOrder.length() != 8){
            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
            return "Err";
        }
        return tanggalOrder;
    }
    
    public static String getNoTelepon(String argNoTelepon){
        // wrapping noTelepon
        // error msg
        String errorMsg = "Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.";
        // cek apakah contains non numeric
        for (int i = 0; i < argNoTelepon.length(); i++){
            char temp = argNoTelepon.charAt(i);
            if ((Integer.valueOf(temp) < 48) || (Integer.valueOf(temp) > 57)){
                    System.out.println(errorMsg);
                    return "Err"; // return 0 berarti error (bukan numerik)
            }
        }
        if (argNoTelepon == "0") {    // cek no telp. positive integer
            System.out.println(errorMsg);
            return "Err";
        } else {    // return 2 digit terakhir dari modulo noTelepon
            int storedSumNoTelepon = 0;
            for (int i = 0; i < argNoTelepon.length(); i++){
                storedSumNoTelepon += Character.getNumericValue(argNoTelepon.charAt(i));
            }
            storedSumNoTelepon = storedSumNoTelepon % 100;
            return String.format("%02d", storedSumNoTelepon);
        }
    }

    public static String getCheckSum(String argCheckSum){
        // wrapping checkSum

        // inisiasi odd dan even checkSum dengan nilai awal
        int oddCheckSumValue = 0;
        int evenCheckSumValue = 0;
        
        // cek apakah numerik
        // iterasikan tiap String
        for (int i = 0; i < argCheckSum.length(); i++){
            // konversikan sesuai dictionary di soal
            char tempArgCheckSumCharacter = argCheckSum.charAt(i);
            int valueOfTempArgCheckSumCharacter = Integer.valueOf(tempArgCheckSumCharacter);
            if ((valueOfTempArgCheckSumCharacter >= 48) && (valueOfTempArgCheckSumCharacter <= 57)) {   // jika alphabet
                // konversikan sesuai dictionary di soal
                int tempdictionaryKonversi = valueOfTempArgCheckSumCharacter - 48;
                if (i % 2 == 0) { // jika genap
                    evenCheckSumValue += tempdictionaryKonversi;
                } else {    // jika ganjil
                    oddCheckSumValue += tempdictionaryKonversi;
                }
            // cek apakah upperCase
            } else if (((valueOfTempArgCheckSumCharacter >= 65) && (valueOfTempArgCheckSumCharacter <= 90))) {
                // konversikan sesuai dictionary di soal
                int tempdictionaryKonversi = valueOfTempArgCheckSumCharacter - 55;
                if (i % 2 == 0) { // jika genap
                    evenCheckSumValue += tempdictionaryKonversi;
                } else {    // jika ganjil
                    oddCheckSumValue += tempdictionaryKonversi;
                }
            }
        }
        
        // return hasil modulo 36
        String resultEvenCheckSum = Integer.toString(evenCheckSumValue);
        String resultOddCheckSum = Integer.toString(evenCheckSumValue);
        if (evenCheckSumValue % 36 > 9) {
            char tempConvertEvenCheckSumValue = (char) ((evenCheckSumValue % 36) + 55);
            resultEvenCheckSum = tempConvertEvenCheckSumValue + "";
        } else {
            resultEvenCheckSum = Integer.toString(evenCheckSumValue % 36);
        }
        
        if (oddCheckSumValue % 36 > 9) {
            char tempConvertoddCheckSumValue = (char) ((oddCheckSumValue % 36) + 55);
            resultOddCheckSum = tempConvertoddCheckSumValue + "";
        } else {
            resultOddCheckSum = Integer.toString(oddCheckSumValue % 36);
        }
        return resultEvenCheckSum + resultOddCheckSum;
    }
}
