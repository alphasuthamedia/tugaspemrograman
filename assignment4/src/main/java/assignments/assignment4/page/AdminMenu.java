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

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;

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
    

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
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
        /* Just Button */
        // Button Tambah Restoran
        Button tambahRestaurantButton = new Button("Tambah Restoran");
        tambahRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        tambahRestaurantButton.setMaxWidth(300);
        tambahRestaurantButton.setOnAction(e -> {
            stage.setScene(this.addRestaurantScene);
        });
        // Button Tambah Menun Restoran
        Button tambahMenuRestaurantButton = new Button("Tambah Menu Restoran");
        tambahMenuRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        tambahMenuRestaurantButton.setMaxWidth(300);
        tambahMenuRestaurantButton.setOnAction(e -> {
            // handleLogin();
        });
        // Button Lihat Daftar Restoran
        Button lihatDaftarRestaurantButton = new Button("Tambah Daftar Restoran");
        lihatDaftarRestaurantButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        lihatDaftarRestaurantButton.setMaxWidth(300);
        lihatDaftarRestaurantButton.setOnAction(e -> {
            // handleLogin();
        });
        // Button Logout
        Button signOutButton = new Button("Log Out");
        signOutButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        signOutButton.setMaxWidth(300);
        signOutButton.setOnAction(e -> {
            mainApp.logout(); // back to login
        });
        /* Button end */

        VBox menuLayout = new VBox(50);
        menuLayout.getChildren().addAll(tambahRestaurantButton, tambahMenuRestaurantButton, lihatDaftarRestaurantButton, signOutButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        // Label Restaurant name
        Label restaurantNameLabel = new Label("Restaurant Name: ");
        restaurantNameLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");


        // field isi nama restaurant
        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderRestaurantNameInput = new Text();
        placeholderRestaurantNameInput.setFill(Color.GRAY);
        placeholderRestaurantNameInput.setMouseTransparent(true);

        StackPane stackRestaurantNameInput = new StackPane();
        stackRestaurantNameInput.getChildren().addAll(restaurantNameInput, placeholderRestaurantNameInput);
        stackRestaurantNameInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderRestaurantNameInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderRestaurantNameInput, new Insets(0, 0, 0, 20)); // Left padding

        /* Buton */
        // Button submit
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        submitButton.setMaxWidth(300);
        submitButton.setOnAction(e -> {
            handleTambahRestoran(restaurantNameInput.getText());
        });

        // Button back
        Button backButton = new Button("Kembali");
         backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
         backButton.setMaxWidth(300);
         backButton.setOnAction(e -> {
            stage.setScene(this.scene); // back to main menu for admin
        });

        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(submitButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);
        /* Button end */

        // satuin semua
        VBox layout = new VBox(150);
        layout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(restaurantNameLabel, stackRestaurantNameInput, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 400, 600);
    }

    private Scene createAddMenuForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah menu restoran
        VBox layout = new VBox(10);
    
        return new Scene(layout, 400, 600);
    }
    
    
    private Scene createViewRestaurantsForm() {
        // TODO: Implementasikan method ini untuk menampilkan page daftar restoran
        VBox layout = new VBox(10);
    
        return new Scene(layout, 400, 600);
    }
    

    private void handleTambahRestoran(String nama) {    
        String validName = DepeFood.getValidRestaurantName(nama);
        if (validName.equals(nama)) {
            DepeFood.handleTambahRestoran(validName);
            showAlert("Success", "Restoran berhasil ditambahkan", "Restoran " + validName + " berhasil ditambahkan", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Restoran gagal ditambahkan", "Restoran " + validName + " gagal ditambahkan", Alert.AlertType.ERROR);
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        //TODO: Implementasi validasi isian menu Restoran
        if (true) {

        } else {


        }
    }
}
