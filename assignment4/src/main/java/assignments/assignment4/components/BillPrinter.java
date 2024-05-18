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
import javafx.scene.text.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        cetakStrukLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

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
        printBillButton.setMinWidth(400);
        printBillButton.setMaxWidth(400);
        printBillButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        printBillButton.setOnAction(e -> {
            // handle strip
            this.printBill(orderIDInput.getText().strip());
        });

        // Button back
        Button backButton = new Button("Kembali");
        backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
        backButton.setMinWidth(400);
        backButton.setMaxWidth(400);
        backButton.setOnAction(e -> {
            CustomerMenu customerMenu = new CustomerMenu(this.stage, this.mainApp, this.user);
            this.mainApp.setScene(customerMenu.createBaseMenu());
        });

        // VBox untuk menyatukan button
        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(printBillButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(275);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(cetakStrukLabel, orderIDLayout, buttonLayout);
        layout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
       
        return new Scene(layout, 480, 854);
    }

    private void printBill(String orderId) {
        Order order = DepeFood.findUserOrderById(this.user, orderId);
        if (order != null) {
            VBox billLayout = new VBox(50);

            Label billLabel = new Label("Bill : ");
            billLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff;");

            Label orderIDLabel = new Label("Order ID: " + order.getOrderId());
            orderIDLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            Label dateLabel = new Label("Tanggal Pemesanan: " + order.getTanggal());
            dateLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            Label restaurantLabel = new Label("Restaurant: " + order.getRestaurant().getNama());
            restaurantLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            Label deliveryLocationLabel = new Label("Lokasi Pengiriman: " + this.user.getLokasi());
            deliveryLocationLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            Label deliveryStatusLabel = new Label("Status Pengiriman: " + (order.getOrderFinished() ? "Finished" : "Not Finished"));
            deliveryStatusLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            VBox menuItemsLayout = new VBox(5);
            menuItemsLayout.getChildren().add(new Label("Pesanan:"));
            menuItemsLayout.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            for (Menu menuItem : order.getSortedMenu()) {
                Label formattedMenuItem = new Label("- " + menuItem.getNamaMakanan() + " Rp " + menuItem.getHarga());
                formattedMenuItem.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
                menuItemsLayout.getChildren().add(formattedMenuItem);
            }

            VBox menuItems = new VBox();
            menuItems.setPadding(new Insets(10));

            StackPane stackMenuItems = new StackPane();
            stackMenuItems.getChildren().add(menuItems);
            stackMenuItems.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));

            Label deliveryCostLabel = new Label("Biaya Ongkos Kirim: Rp " + order.getOngkir());
            deliveryCostLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");

            Label totalCostLabel = new Label("Total Biaya: Rp " + order.getTotalHarga());
            totalCostLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
            
            // satukan di menuitems
            menuItems.getChildren().addAll(orderIDLabel, dateLabel, restaurantLabel, deliveryLocationLabel, deliveryStatusLabel, menuItemsLayout, deliveryCostLabel, totalCostLabel);

            Button backButton = new Button("Kembali");
            backButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #ffffff;");
            backButton.setMaxWidth(380); 
            backButton.setOnAction(e -> {
                stage.setScene(this.getScene());
            });

            billLayout.getChildren().addAll(billLabel, stackMenuItems, backButton);
            billLayout.setAlignment(Pos.CENTER);
            billLayout.setPadding(new Insets(20));
            billLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));

            Scene billScene = new Scene(billLayout, 480, 854);
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
