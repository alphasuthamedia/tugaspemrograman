package assignments.assignment4.components.form;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import assignments.assignment4.page.MemberMenu;

import java.util.function.Consumer;

public class LoginForm extends MemberMenu {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        // Label welcome to DepeFood
        Label welcomeLabel = new Label("Welcome to DepeFood");
        welcomeLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #ffffff; -fx-padding: 20px 0 20px 0;");
        
        // image DepeFood
        ImageView img = new ImageView();
        img.setStyle("-fx-background-color: transparent");
        img.setImage(new Image("DepeFood.png"));


        /* Section userInput */
        // namelabel dan nameInput
        nameInput = new TextField();
        // nameInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0");
        nameInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderNameInput = new Text("Enter your name");
        placeholderNameInput.setFill(Color.GRAY);
        placeholderNameInput.setStyle("-fx-font-family: 'Source Sans Pro Regular'; -fx-font-size: 20px;");
        placeholderNameInput.setMouseTransparent(true);

        StackPane stackNameInput = new StackPane();
        stackNameInput.getChildren().addAll(nameInput, placeholderNameInput);
        stackNameInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderNameInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderNameInput, new Insets(0, 0, 0, 20)); // Left padding

        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            placeholderNameInput.setVisible(newValue.isEmpty());
        });

        // phoneNumberLabel dan phoneInput
        phoneInput = new TextField();
        phoneInput.setStyle("-fx-font-size: 20px; -fx-font-family: 'Source Sans Pro Regular'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");

        Text placeholderPhoneNumberInput = new Text("Enter your phone number");
        placeholderPhoneNumberInput.setFill(Color.GRAY);
        placeholderPhoneNumberInput.setStyle("-fx-font-family: 'Source Sans Pro Regular'; -fx-font-size: 20px;");
        placeholderPhoneNumberInput.setMouseTransparent(true);

        StackPane stackPhoneNumberInput = new StackPane();
        stackPhoneNumberInput.getChildren().addAll(phoneInput, placeholderPhoneNumberInput);
        stackPhoneNumberInput.setMaxWidth(380);
        StackPane.setAlignment(placeholderPhoneNumberInput, Pos.CENTER_LEFT);
        StackPane.setMargin(placeholderPhoneNumberInput, new Insets(0, 0, 0, 20)); // Left padding

        phoneInput.textProperty().addListener((observable, oldValue, newValue) -> {
            placeholderPhoneNumberInput.setVisible(newValue.isEmpty());
        });
        
        // jadikan satu
        VBox userInputSection = new VBox(5, stackNameInput, stackPhoneNumberInput);
        userInputSection.setAlignment(Pos.CENTER);
        
        /* End of userInput */

        // Button login (execute handleLogin)
        Button loginButton = new Button("Sign In");
        loginButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Source Sans Pro Semi-Bold'; -fx-text-fill: #000000; -fx-background-color: #f0f0f0;");
        loginButton.setMaxWidth(300);
        loginButton.setOnAction(e -> {
            nameInput.clear(); // Clear nameInput after login
            phoneInput.clear(); // Clear nameInput after login
            handleLogin();
        });

        // complete structure
        VBox mainLayout = new VBox(70, welcomeLabel, img, userInputSection, loginButton);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.backgroundProperty().set(new Background(new BackgroundFill(Color.web("#0A9680"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        return new Scene(mainLayout, 480, 854);
    }


    private void handleLogin() {
        DepeFood.initUser(); // Initialize users
        User user = DepeFood.getUser(nameInput.getText(), phoneInput.getText());
        // mainApp.setScene(new AdminMenu(stage, mainApp, user).createBaseMenu());
        // mainApp.setScene(new CustomerMenu(stage, mainApp, user).createBaseMenu());
        if (user == null) {
            showAlert("Login Failed", null, "User not found!", Alert.AlertType.ERROR);
        } else {
            mainApp.setUser(user);
            if ((user.getRole().equals("Admin"))) {
                mainApp.setScene(new AdminMenu(stage, mainApp, user).createBaseMenu());
            } else if (user.getRole().equals("Customer")) {
                mainApp.setScene(new CustomerMenu(stage, mainApp, user).createBaseMenu());
            }
        }
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

    public void refresh(){}

    public Scene createBaseMenu(){
        return null;
    }

}
