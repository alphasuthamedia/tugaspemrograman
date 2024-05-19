package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;


import assignments.assignment3.payment.*;

import javafx.scene.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;


//extends MemberMenu
public class CustomerMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;

    Label balanceLabel = new Label("");

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        balanceLabel = new Label(""+user.getSaldo());
        restoList = DepeFood.getRestoList();
        refresh();
        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    // @Override
    public Scene createBaseMenu() {
        // Label Halo user
        Label haloUserLabel = new Label("Halo, " + user.getNama());
        haloUserLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        
        /* Just Button */
        // Label setAksi
        Label setAksiLabel = new Label("  Silahkan pilih aksi yang ingin dilakukan");
        setAksiLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // Button Buat Pesanan
        Button handleBuatPesananButton = new Button("Buat Pesanan");
        handleBuatPesananButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        handleBuatPesananButton.setMaxWidth(380);
        handleBuatPesananButton.setOnAction(e -> {
            stage.setScene(this.addOrderScene);
        });
        // Button Cetak Bill
        Button handleCetakBillButton = new Button("Cetak Bill");
        handleCetakBillButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        handleCetakBillButton.setMaxWidth(380);
        handleCetakBillButton.setOnAction(e -> {
            stage.setScene(this.printBillScene);
        });
        // Button Bayar Bill
        Button handleBayarBillButton = new Button("Bayar Bill");
        handleBayarBillButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        handleBayarBillButton.setMaxWidth(380);
        handleBayarBillButton.setOnAction(e -> {
            stage.setScene(this.payBillScene);
        });
        // Button Cek Saldo
        Button handleCekSaldoButton = new Button("Cek Saldo");
        handleCekSaldoButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        handleCekSaldoButton.setMaxWidth(380);
        handleCekSaldoButton.setOnAction(e -> {
            balanceLabel.setText(""+user.getSaldo());
            System.out.println(user.getSaldo());
            stage.setScene(this.cekSaldoScene);
        });
        // Button sign out
        Button signOutButton = new Button("Log Out");
        signOutButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        signOutButton.setMaxWidth(380);
        signOutButton.setOnAction(e -> {
            mainApp.logout(); // back to login
        });
        // satuin button
        VBox buttonLayout = new VBox(30);
        buttonLayout.getChildren().addAll(handleBuatPesananButton, handleCetakBillButton, handleBayarBillButton, handleCekSaldoButton, signOutButton);
        buttonLayout.setAlignment(Pos.CENTER);
        VBox buttonLayoutandLabel = new VBox(20);
        buttonLayoutandLabel.getChildren().addAll(setAksiLabel, buttonLayout);
        /* Button end */

        VBox menuLayout = new VBox(200);
        menuLayout.getChildren().addAll(haloUserLabel, buttonLayoutandLabel);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(menuLayout, 480, 854);
    }

    private Scene createTambahPesananForm() {
        // Label Buat Pesanan
        Label buatPesananLabel = new Label("Buat Pesanan");
        buatPesananLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // Label Restaurant
        Label restaurantLabel = new Label("Select Restaurant :");
        restaurantLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // ComboBox Restaurant
        restaurantComboBox.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        
        // Label Tanggal Pemesanan
        Label tanggalPemesananLabel = new Label("Enter the date : ");
        tanggalPemesananLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // TextField Tanggal Pemesanan
        TextField tanggalPemesananInput = new TextField();
        tanggalPemesananInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderTanggalPemesananInput = new Text();
        placeholderTanggalPemesananInput.setFill(Color.GRAY);
        placeholderTanggalPemesananInput.setMouseTransparent(true);

        StackPane stackTanggalPemesananInput = new StackPane();
        stackTanggalPemesananInput.getChildren().addAll(tanggalPemesananInput, placeholderTanggalPemesananInput);
        stackTanggalPemesananInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderTanggalPemesananInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderTanggalPemesananInput, new Insets(0, 0, 0, 20)); // Left padding

        // Button Menu (untuk showMenu)
        Button menuButton = new Button("Menu");
        menuButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        menuButton.setMaxWidth(300);
        menuButton.setOnAction(e -> {
            findMenuRestaurantOrNull(restaurantComboBox.getValue()); // back to main menu for admin
        });

        // Label Menu Items
        Label menuItemsLabel = new Label("Menu Items :");
        menuItemsLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // Button buat pesanan
        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        buatPesananButton.setMaxWidth(300);
        buatPesananButton.setOnAction(e -> {
            if (restaurantComboBox.getValue() == null) {
                showAlert("Error", null, "Anda belum memilih restoran", AlertType.ERROR);
                return;
            }
            if (tanggalPemesananInput.getText().isEmpty()) {
                showAlert("Error", null, "Tanggal pemesanan tidak boleh kosong", AlertType.ERROR);
                return;
            } else if (!tanggalPemesananInput.getText().strip().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                showAlert("Error", null, "Format tanggal pemesanan salah (DD/MM/YYYY)", AlertType.ERROR);
                return;
            }
            // kalau yang dipilih kosong
            if (menuItemsListView.getSelectionModel().getSelectedItems().size() == 0){
                showAlert("Error", null, "Menu belum dipilih", AlertType.ERROR);
                return;
            }
            // handle strip
            handleBuatPesanan(restaurantComboBox.getValue(), tanggalPemesananInput.getText().strip(), getNamaMenuSelected());
        });

        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(300);
        backButton.setOnAction(e -> {
            restaurantComboBox.setValue(null); // Clear the restaurantComboBox
            tanggalPemesananInput.clear(); // Clear the tanggalPemesananInput
            menuItemsListView.getItems().clear(); // Clear the menuItemsListView
            stage.setScene(this.scene); // back to main menu for admin
        });

        VBox menuLayout = new VBox(10);
        menuLayout.getChildren().addAll(buatPesananLabel, restaurantLabel, restaurantComboBox, tanggalPemesananLabel, stackTanggalPemesananInput, menuButton, menuItemsLabel, menuItemsListView, buatPesananButton, backButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(menuLayout, 480, 854);
    }

    private Scene createBillPrinter(){
        BillPrinter billPrinter = new BillPrinter(stage, mainApp, user);
        return billPrinter.createBillPrinterForm();
    }

    private Scene createBayarBillForm() {
        // Label Masukkan Struk
        Label masukkanStrukLabel = new Label("Bayar Pesanan");
        masukkanStrukLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");

        // Label Masukkan orderID
        Label orderIDLabel = new Label("Order ID : ");
        orderIDLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");

        // TextField orderIDInput
        TextField orderIDInput = new TextField();
        orderIDInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderOrderIDInput = new Text();
        placeholderOrderIDInput.setFill(Color.GRAY);
        placeholderOrderIDInput.setMouseTransparent(true);

        StackPane stackOrderIDInput = new StackPane();
        stackOrderIDInput.getChildren().addAll(orderIDInput, placeholderOrderIDInput);
        stackOrderIDInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderOrderIDInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderOrderIDInput, new Insets(0, 0, 0, 20)); // Left padding

        // ComboBox opsi pembayaran
        ComboBox<String> pilihOpsiPembayaranComboBox = new ComboBox<>();
        pilihOpsiPembayaranComboBox.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        pilihOpsiPembayaranComboBox.getItems().addAll("Credit Card", "Debit");

        // Button Bayar Bill
        Button bayarBillButton = new Button("Bayar");
        bayarBillButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        bayarBillButton.setMaxWidth(380);
        bayarBillButton.setOnAction(e -> {
            if ((pilihOpsiPembayaranComboBox.getValue() == null) && (orderIDInput.getText().isEmpty())) {
                showAlert("Error", null, "Order ID belum dimasukkan", AlertType.ERROR);
                return;
            } else if (pilihOpsiPembayaranComboBox.getValue() == null) {
                showAlert("Error", null, "Anda belum memilih opsi pembayaran", AlertType.ERROR);
                return;
            } else {
                if (pilihOpsiPembayaranComboBox.getValue().equals("Credit Card")) {
                    // handle strip
                    handleBayarBill(orderIDInput.getText().strip(), 1);
                } else {
                    // handle strip
                    handleBayarBill(orderIDInput.getText().strip(), 2);
                }
            }
        });

        // Button Back Button
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(380);
        backButton.setOnAction(e -> {
            orderIDInput.clear(); // Clear the orderIDInput
            pilihOpsiPembayaranComboBox.setValue(null); // Clear the pilihOpsiPembayaranComboBox
            stage.setScene(this.scene); // back to main menu for admin
        });

        // VBox untuk menyatukan ID dan selection pembayaran
        VBox masukkanID = new VBox(10);
        masukkanID.getChildren().addAll(orderIDLabel, stackOrderIDInput, pilihOpsiPembayaranComboBox);
        masukkanID.setAlignment(Pos.CENTER);

        // VBox untuk menyatukan button
        VBox menuButtonLayout = new VBox(10);
        menuButtonLayout.getChildren().addAll(bayarBillButton,backButton);
        menuButtonLayout.setAlignment(Pos.CENTER);

        VBox menuLayout = new VBox(170);
        menuLayout.getChildren().addAll(masukkanStrukLabel,masukkanID, menuButtonLayout);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));


        return new Scene(menuLayout, 480,854);
    }


    private Scene createCekSaldoScene() {
        // Label Cek Saldo
        Label cekSaldoLabel = new Label("Cek Saldo");
        cekSaldoLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        cekSaldoLabel.setAlignment(Pos.CENTER);

        // Label your balance
        Label yourBalanceLabel = new Label("Saldo Anda : ");
        yourBalanceLabel.setStyle("-fx-font-size: 36px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        yourBalanceLabel.setAlignment(Pos.CENTER);

        // Label for showing the balance
        balanceLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-alignment: center;");
        balanceLabel.setAlignment(Pos.CENTER);
    
        // VBox for the balance
        VBox balanceLayout = new VBox(10);
        balanceLayout.getChildren().addAll(yourBalanceLabel, balanceLabel);
        balanceLayout.setAlignment(Pos.CENTER);

        // Back button to return to the main menu
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(300);
        backButton.setOnAction(e -> {
            stage.setScene(this.scene); // Return to the main menu
        });
    
        // Layout for the scene
        VBox menuLayout = new VBox(250);
        menuLayout.getChildren().addAll(cekSaldoLabel, balanceLayout, backButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
    
        

        return new Scene(menuLayout, 480, 854);
    }
    

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        List<String> tempMenuItems = new ArrayList<>();
        List<String> hasilMenuItems = new ArrayList<>();
        for (String menu : menuItems) {
            // Menghapus nomor urut di awal dan memisahkan nama menu dari harga
            String cleanedMenu = menu.replaceAll("^\\d+\\.\\s*", ""); // Menghapus angka dan titik di awal
            cleanedMenu = (cleanedMenu.split(" - Rp")[0].trim());
            hasilMenuItems.add(cleanedMenu);
        }

        try {
            String orderID = DepeFood.handleBuatPesanan(user, namaRestoran, tanggalPemesanan, 1, hasilMenuItems);
            showAlert("Success", null, "Order dengan ID " + orderID + " berhasil ditambahkan", AlertType.INFORMATION);
        } catch (Exception e) {
            System.out.println("SOMETHING SKECTHY"); // shouldnt be executed
        }
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        Order order = DepeFood.getOrderOrNull(orderID);
        if (order == null) {
            showAlert("Error", null, "Order ID tidak ditemukan", Alert.AlertType.ERROR);
            return;
        }
    
        if (order.getOrderFinished()) {
            showAlert("Error", null, "Pesanan dengan ID ini sudah lunas", Alert.AlertType.ERROR);
            return;
        }
    
        DepeFoodPaymentSystem paymentSystem = user.getPaymentSystem();
        boolean isCreditCard = paymentSystem instanceof CreditCardPayment;
        long amountToPay = (long) order.getTotalHarga();
        long tax = 0;
    
        if (isCreditCard && pilihanPembayaran == 1) {
            tax = (long) (amountToPay * 0.02);
            amountToPay += tax;
        } else if (!isCreditCard && pilihanPembayaran == 2) {
            // No tax for Debit
        } else {
            showAlert("Error", null, "Terjadi kesalahan saat memproses pembayaran", Alert.AlertType.ERROR);
            return;
        }
        System.out.println(amountToPay);
        if (user.getSaldo() < amountToPay) {
            showAlert("Error", null, "Saldo tidak mencukupi", Alert.AlertType.ERROR);
            return;
        }
    
        user.setSaldo(user.getSaldo() - amountToPay);
        System.out.println(user.getSaldo());
        DepeFood.handleUpdateStatusPesanan(order);
        showAlert("Success", null, "Pembayaran berhasil dilakukan", Alert.AlertType.INFORMATION);
    }

    @Override
    public void refresh() {
        List<String> restaurantNames = DepeFood.getRestoList().stream()
            .map(Restaurant::getNama)
            .collect(Collectors.toList());
        restaurantComboBox.setItems(FXCollections.observableArrayList(restaurantNames));
        // restaurantComboBoxView.setItems(FXCollections.observableArrayList(restaurantNames));
    }

    private void findMenuRestaurantOrNull(String restaurantName) {
        if (findMenuRestaurant(restaurantName) == 0) {
            showAlert("Error", null, "Menu belum ditambahkan", Alert.AlertType.ERROR);
        } else if (findMenuRestaurant(restaurantName) == -1) {
            showAlert("Error", null, "Anda belum memilih restoran", Alert.AlertType.ERROR);
        } else {
            findMenuRestaurant(restaurantName);
            // refresh the restaurant list
            List<String> restaurantNames = DepeFood.getRestoList().stream()
            .map(Restaurant::getNama)
            .collect(Collectors.toList());
            restaurantComboBox.setItems(FXCollections.observableArrayList(restaurantNames));
        }
    }

    private int findMenuRestaurant(String restaurantName){
        Restaurant restaurant = DepeFood.getRestaurantByName(restaurantName);
        
        try {
            restaurant.sortMenu();
        }
        catch (NullPointerException e) {
            menuItemsListView.getItems().clear();    
            return -1; // no menu
        }

        ArrayList<Menu> menu = restaurant.sortMenu();
        if (menu.size() == 0) {
            menuItemsListView.getItems().clear();
            return 0; // no menu
        }

        menuItemsListView.getItems().clear();
        menuItemsListView.setStyle("-fx-font-size: 18px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        int n = 1; // counter
        for (Menu menuItem : menu) {
            System.out.println("MENU " + menuItem.getNamaMakanan());
            menuItemsListView.getItems().add((n++) + ". " + menuItem.getNamaMakanan() + " - Rp. " + menuItem.getHarga());
        }
        n = 0; // reset counter
        return 1; // success
    }

    private List<String> getNamaMenuSelected(){
        ObservableList<String> selectedItems = menuItemsListView.getSelectionModel().getSelectedItems();
        System.out.println("Selected items: " + selectedItems);
        return selectedItems;
    }
}