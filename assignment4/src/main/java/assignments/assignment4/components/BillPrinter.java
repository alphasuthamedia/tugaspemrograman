package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import assignments.assignment4.page.*;
import javafx.scene.layout.*;

import java.util.Stack;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;


public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    public Scene createBillPrinterForm(){
        // Label Cetak Struk
        Label cetakStrukLabel = new Label("Cetak Struk");
        cetakStrukLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

        // Label masukkan order ID
        Label orderIDLabel = new Label("Masukkan Order ID :");
        orderIDLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");
        
        // put order ID
        TextField orderIDInput = new TextField();
        // orderIDInput.setPromptText("Masukkan Order ID");
        orderIDInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        orderIDInput.setMaxWidth(380);

        // vbox set masukkan order ID dan field untuk memasukkan order ID
        VBox orderIDLayout = new VBox(10);
        orderIDLayout.getChildren().addAll(orderIDLabel, orderIDInput);
        orderIDLayout.setAlignment(Pos.CENTER);

        // Button print Bill
        Button printBillButton = new Button("Print Bill");
        printBillButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        printBillButton.setOnAction(e -> {
            this.printBill(orderIDInput.getText());
        });

        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setOnAction(e -> {
            CustomerMenu customerMenu = new CustomerMenu(this.stage, this.mainApp, this.user);
            this.mainApp.setScene(customerMenu.createBaseMenu());
        });

        // VBox untuk menyatukan button
        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(printBillButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(120);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(cetakStrukLabel, orderIDLayout, buttonLayout);
        layout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
       
        return new Scene(layout, 400, 600);
    }

    private void printBill(String orderId) {
        Order order = DepeFood.findUserOrderById(this.user, orderId);
        if (order != null) {
            VBox billLayout = new VBox(10);

            Label billLabel = new Label("Bill");
            billLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");

            Label orderIDLabel = new Label("Order ID: " + order.getOrderId());
            Label dateLabel = new Label("Tanggal Pemesanan: " + order.getTanggal());
            Label restaurantLabel = new Label("Restaurant: " + order.getRestaurant().getNama());
            Label deliveryLocationLabel = new Label("Lokasi Pengiriman: " + this.user.getLokasi());
            Label deliveryStatusLabel = new Label("Status Pengiriman: " + (order.getOrderFinished() ? "Finished" : "Not Finished"));

            VBox menuItemsLayout = new VBox(5);
            menuItemsLayout.getChildren().add(new Label("Pesanan:"));
            for (Menu menuItem : order.getSortedMenu()) {
                menuItemsLayout.getChildren().add(new Label("- " + menuItem.getNamaMakanan() + " Rp " + menuItem.getHarga()));
            }

            VBox menuItems = new VBox();
            menuItems.getChildren().addAll(orderIDLabel, dateLabel, restaurantLabel, deliveryLocationLabel, deliveryStatusLabel, menuItemsLayout);

            StackPane stackMenuItems = new StackPane();
            stackMenuItems.getChildren().add(menuItems);
            stackMenuItems.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            Label deliveryCostLabel = new Label("Biaya Ongkos Kirim: Rp " + order.getOngkir());
            Label totalCostLabel = new Label("Total Biaya: Rp " + order.getTotalHarga());

            Button backButton = new Button("Kembali");
            backButton.setOnAction(e -> {
                stage.setScene(this.getScene());
            });

            billLayout.getChildren().addAll(billLabel, stackMenuItems, backButton);
            billLayout.setAlignment(Pos.CENTER);
            billLayout.setPadding(new Insets(20));
            billLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

            Scene billScene = new Scene(billLayout, 400, 600);
            stage.setScene(billScene);
        } else {
            MemberMenu.showAlert("Error", "Order ID tidak ditemukan", "Silakan masukkan Order ID yang valid.", Alert.AlertType.ERROR);
        }
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
