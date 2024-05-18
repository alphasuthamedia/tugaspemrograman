package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import assignments.assignment3.Menu;
import assignments.assignment3.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.collections.*;

public class AdminMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    private TextField restaurantMenuNameInput;
    private TextField restaurantMenuPriceInput;
    ComboBox<String> restaurantComboBoxView = new ComboBox<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {

        restoList = DepeFood.getRestoList(); // initial condition
        
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        // Label Hello Admon
        Label helloAdminLabel = new Label("Halo Admin!");
        helloAdminLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");
        
        /* Just Button */
        // Label silahkan pilih aksi yang ingin dilakukan
        Label silahkanPilihAksiLabel1 = new Label("  Silahkan pilih aksi yang ingin dilakukan : ");
        silahkanPilihAksiLabel1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        VBox silahkanPilihAksiLabel = new VBox(silahkanPilihAksiLabel1); // untuk menghindari centering
        
        // Button Tambah Restoran
        Button tambahRestaurantButton = new Button("Tambah Restoran");
        tambahRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        tambahRestaurantButton.setMaxWidth(380);
        tambahRestaurantButton.setOnAction(e -> {
            stage.setScene(this.addRestaurantScene);
        });
        // Button Tambah Menun Restoran
        Button tambahMenuRestaurantButton = new Button("Tambah Menu Restoran");
        tambahMenuRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        tambahMenuRestaurantButton.setMaxWidth(380);
        tambahMenuRestaurantButton.setOnAction(e -> {
            stage.setScene(this.addMenuScene);
        });
        // Button Lihat Daftar Restoran
        Button lihatDaftarRestaurantButton = new Button("Lihat Daftar Restoran");
        lihatDaftarRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        lihatDaftarRestaurantButton.setMaxWidth(380);
        lihatDaftarRestaurantButton.setOnAction(e -> {
            stage.setScene(this.viewRestaurantsScene);
        });
        // Button Logout
        Button signOutButton = new Button("Log Out");   
        signOutButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        signOutButton.setMaxWidth(380);
        signOutButton.setOnAction(e -> {
            mainApp.logout(); // back to login
        });
        // gabung semua button
        VBox buttonLayout = new VBox(30);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(silahkanPilihAksiLabel, tambahRestaurantButton, tambahMenuRestaurantButton, lihatDaftarRestaurantButton, signOutButton);
        /* Button end */

        VBox menuLayout = new VBox(200);
        menuLayout.getChildren().addAll(helloAdminLabel, buttonLayout);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(menuLayout, 480, 850);
    }

    private Scene createAddRestaurantForm() {
        // field isi nama restaurant
        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderRestaurantNameInput = new Text();
        placeholderRestaurantNameInput.setFill(Color.GRAY);
        placeholderRestaurantNameInput.setMouseTransparent(true);

        StackPane stackRestaurantNameInput = new StackPane();
        stackRestaurantNameInput.getChildren().addAll(restaurantNameInput, placeholderRestaurantNameInput);
        stackRestaurantNameInput.setMaxWidth(450);
        StackPane.setAlignment(placeholderRestaurantNameInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderRestaurantNameInput, new Insets(0, 0, 0, 20)); // Left padding

        /* Buton */
        // Button submit
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        submitButton.setMaxWidth(380);
        submitButton.setOnAction(e -> {
            // handle strip
            if (handleTambahRestoran(restaurantNameInput.getText().strip()) == 1) {
                restaurantNameInput.clear();
            }
        });

        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(380);
        backButton.setOnAction(e -> {
            refresh();
            restaurantNameInput.clear();
            stage.setScene(this.scene); // back to main menu for admin
        });

        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(submitButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);
        /* Button end */

        // satuin semua
        Label restaurantNameLabelCreateRestaurant = new Label("Tambah Restoran");
        restaurantNameLabelCreateRestaurant.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");
        
        // label masukkan nama restaurant
        Label restaurantNameLabel = new Label("Nama Restoran : ");
        restaurantNameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        VBox justForRestaurantNameLabel = new VBox(restaurantNameLabel); // agar tidak tercenter

        // Main layout for the add restaurant form textfield
        VBox textFieldlayout = new VBox(20, restaurantNameLabel, stackRestaurantNameInput);

        // Main layout for the add restaurant form
        VBox layout = new VBox(210, restaurantNameLabelCreateRestaurant, textFieldlayout, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(layout, 480, 850);
    }

    private Scene createAddMenuForm() {
        // label tambah menu
        Label addMenuLabel1 = new Label("Tambah Menu");
        addMenuLabel1.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        
        // label restaurant
        Label addMenuLabel2 = new Label("Restoran");
        addMenuLabel2.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        VBox addMenuLabel = new VBox(addMenuLabel1, addMenuLabel2);
        addMenuLabel.setAlignment(Pos.CENTER);


        // label nama restaurant
        Label restaurantNameLabel = new Label("Restaurant Name: ");
        restaurantNameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");

        // ComboBox Restaurant
        restaurantComboBox.setMinWidth(380);
        restaurantComboBox.setMaxWidth(380);
        restaurantComboBox.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        
        // field tambah menu item name
        restaurantMenuNameInput = new TextField();
        restaurantMenuNameInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        // label menu item name
        Label restaurantMenuItemNameLabel = new Label("Menu Item Name: ");
        restaurantMenuItemNameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // field nama menu item
        Text placeholderRestaurantMenuNameInput = new Text();
        placeholderRestaurantMenuNameInput.setFill(Color.GRAY);
        placeholderRestaurantMenuNameInput.setMouseTransparent(true);

        StackPane stackRestaurantMenuNameInput = new StackPane();
        stackRestaurantMenuNameInput.getChildren().addAll(restaurantMenuNameInput, placeholderRestaurantMenuNameInput);
        stackRestaurantMenuNameInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderRestaurantMenuNameInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderRestaurantMenuNameInput, new Insets(0, 0, 0, 20)); // Left padding

        // Label Restaurant name
        Label restaurantMenuPriceLabel = new Label("Price: ");
        restaurantMenuPriceLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // field harga menu item
        restaurantMenuPriceInput = new TextField();
        restaurantMenuPriceInput.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderRestaurantMenuPriceInput = new Text();
        placeholderRestaurantMenuPriceInput.setFill(Color.GRAY);
        placeholderRestaurantMenuPriceInput.setMouseTransparent(true);

        StackPane stackRestaurantMenuPriceInput = new StackPane();
        stackRestaurantMenuPriceInput.getChildren().addAll(restaurantMenuPriceInput, placeholderRestaurantMenuPriceInput);
        stackRestaurantMenuPriceInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderRestaurantMenuPriceInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderRestaurantMenuPriceInput, new Insets(0, 0, 0, 20)); // Left padding

        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(380);
        backButton.setOnAction(e -> {
            restaurantMenuNameInput.clear();
            restaurantMenuPriceInput.clear();
            restaurantComboBox.setValue(null);
            stage.setScene(this.scene); // back to main menu for admin
        });

        // Button Add Menu Item
        Button addMenuButton = new Button("Add Menu Item");
        addMenuButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        addMenuButton.setMaxWidth(380);
        addMenuButton.setOnAction(e -> {
            if (restaurantComboBox.getValue() == null) {
                showAlert("Error", "Menu gagal ditambahkan", "Anda belum memilih restoran", Alert.AlertType.ERROR);
            } else {
                // handle strip
                if (restaurantMenuNameInput.getText().strip().length() < 4){
                    showAlert("Error", "Menu gagal ditambahkan", "Nama menu harus lebih dari 4 karakter", Alert.AlertType.ERROR);
                    } else {
                        try {
                            // handle endspace dan firstspace dengna strip
                            Double.parseDouble(restaurantMenuPriceInput.getText().strip());
                            handleTambahMenuRestoran(restaurantComboBox.getValue(), restaurantMenuNameInput.getText().strip(), Double.parseDouble(restaurantMenuPriceInput.getText().strip()));
                            restaurantMenuNameInput.clear();
                            restaurantMenuPriceInput.clear();
                        } catch (NumberFormatException ex) {
                            showAlert("Error", "Menu gagal ditambahkan", "Harga menu harus lebih dari 0", Alert.AlertType.ERROR);    
                    }
                }
            }
        });
        
        // gabung untuk selection restaurant dan menu
        VBox menuSelectionLayout = new VBox(10);
        menuSelectionLayout.setAlignment(Pos.CENTER);
        menuSelectionLayout.getChildren().addAll(restaurantNameLabel, restaurantComboBox, restaurantMenuItemNameLabel, stackRestaurantMenuNameInput, restaurantMenuPriceLabel, stackRestaurantMenuPriceInput);

        // gabung untuk button
        VBox buttonLayout = new VBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(addMenuButton, backButton);

        // gabung semua
        VBox layout = new VBox(120);
        layout.getChildren().addAll(addMenuLabel, menuSelectionLayout, buttonLayout);
        layout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setAlignment(Pos.CENTER);
        
        return new Scene(layout, 480, 854);
    }
    
    
    private Scene createViewRestaurantsForm() {
        // label lihat daftar restaurant
        Label viewRestaurantsLabel1 = new Label("Lihat Daftar");
        viewRestaurantsLabel1.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // label lihat daftar restaurant
        Label viewRestaurantsLabel2 = new Label("Menu Restoran");
        viewRestaurantsLabel2.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        VBox viewRestaurantsLabel = new VBox(viewRestaurantsLabel1, viewRestaurantsLabel2);
        viewRestaurantsLabel.setAlignment(Pos.CENTER);
        
        // setMenuItems list
        if (!menuItemsListView.getItems().isEmpty()) {
            menuItemsListView.setStyle("-fx-font-size: 18px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
            menuItemsListView.setMinWidth(400);
            menuItemsListView.setMaxWidth(400);
        } else {
            menuItemsListView.setMinWidth(400);
            menuItemsListView.setMaxWidth(400);
        }
        

        // Label Restaurant name
        Label restaurantNameLabelView = new Label("Restaurant Name: ");
        restaurantNameLabelView.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        
        // ComboBox Restaurant
        restaurantComboBoxView.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        restaurantComboBoxView.setItems(FXCollections.observableArrayList(" ")); // clear the combobox (DUMMY VALUE)
        restaurantComboBoxView.setMinWidth(380);
        restaurantComboBoxView.setMaxWidth(380);

        // Button find menu
        Button findMenuButton = new Button("Find!");
        findMenuButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        findMenuButton.setMaxWidth(380);
        findMenuButton.setOnAction(e -> {
            findMenuRestaurantOrNull(restaurantComboBoxView.getValue());
        });


        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMaxWidth(380);
        backButton.setOnAction(e -> {
            restaurantComboBoxView.setValue(null);
            menuItemsListView.getItems().clear();
            stage.setScene(this.scene); // back to main menu for admin
        });

        // jadikan satu find dan menuItemsListCIew
        VBox tempLayout = new VBox(10, restaurantNameLabelView, restaurantComboBoxView, findMenuButton, menuItemsListView);
        tempLayout.setAlignment(Pos.CENTER);

        // gabung semua
        VBox layout = new VBox(50);
        layout.getChildren().addAll(viewRestaurantsLabel, tempLayout, backButton);
        layout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 480, 854);
    }
    
    private int handleTambahRestoran(String nama) {    
        String validName = DepeFood.getValidRestaurantName(nama);
        if (validName.equals(nama)) {
            DepeFood.handleTambahRestoran(validName);
            showAlert("Success", "Restoran berhasil ditambahkan", "Restoran " + validName + " berhasil ditambahkan", Alert.AlertType.INFORMATION);
            return 1; // success
        } else {
            showAlert("Error", "Restoran gagal ditambahkan", "Restoran " + validName + " gagal ditambahkan", Alert.AlertType.ERROR);
            return 0; // failed
        }
    }
    
    @Override
    public void refresh() {
        List<String> restaurantNames = DepeFood.getRestoList().stream()
            .map(Restaurant::getNama)
            .collect(Collectors.toList());
        restaurantComboBox.setItems(FXCollections.observableArrayList(restaurantNames));
        restaurantComboBoxView.setItems(FXCollections.observableArrayList(restaurantNames));
    }

    private void handleTambahMenuRestoran(String restaurantName, String itemName, double price) {
        Restaurant restaurant = DepeFood.getRestaurantByName(restaurantName);
        DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
        showAlert("Success", "Menu berhasil ditambahkan", "Menu " + itemName + " berhasil ditambahkan ke " + restaurantName, Alert.AlertType.INFORMATION);
    }

    private void findMenuRestaurantOrNull(String restaurantName) {
        if (findMenuRestaurant(restaurantName) == 0) {
            showAlert("Error", null, "Menu belum ditambahkan", Alert.AlertType.ERROR);
            // restaurantComboBoxView.setItems(FXCollections.observableArrayList(""));
        } else if (findMenuRestaurant(restaurantName) == -1) {
            showAlert("Error", null, "Anda belum memilih restoran", Alert.AlertType.ERROR);
        }else {
            findMenuRestaurant(restaurantName);
            // refresh the restaurant list
            List<String> restaurantNames = DepeFood.getRestoList().stream()
            .map(Restaurant::getNama)
            .collect(Collectors.toList());
            restaurantComboBoxView.setItems(FXCollections.observableArrayList(restaurantNames));
        }
    }

    private int findMenuRestaurant(String restaurantName){
        Restaurant restaurant = DepeFood.getRestaurantByName(restaurantName);
        
        try {
            restaurant.getMenu();
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
        int n = 1;
        menuItemsListView.setStyle("-fx-font-size: 18px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        for (Menu menuItem : menu) {
            menuItemsListView.getItems().add((n++) + ". " + menuItem.getNamaMakanan() + " - Rp. " + menuItem.getHarga());
        }
        n = 1;
        return 1; // success
        
    }
}
