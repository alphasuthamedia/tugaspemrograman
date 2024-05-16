package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class MemberMenu {
    private Scene scene;

    abstract public Scene createBaseMenu();

    public static void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }

    abstract public void refresh(); // Abstract method to refresh the scene

}
