package assignments.assignment3.systemCLI;

import java.util.Scanner;

public abstract class UserSystemCLI {
    protected Scanner input; // Scanner untuk input dari user
    /* Concrete class dari UserSystemCLi */
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }
    /* Abstract class dari UserSystemCLI
     * Method ini akan menampilkan menu yang sesuai dengan role user
     * oleh karena itu, method ini harus di override oleh class yang meng-extend class ini
     */
    public abstract void displayMenu();
    public abstract boolean handleMenu(int command);
}
