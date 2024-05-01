package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    /* Konstruktor LoginManager
     * @param adminSystem adalah objek AdminSystemCLI
     * @param customerSystem adalah objek CustomerSystemCLI
     */
    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    /* Method ini meng get sistem apa yang sesuai dengan role yang dimasukkan
     * jika role adalah "Admin" maka nanti akan akan mengembalikan CLI adminSystem (perlu di explicit cast)
     * jika role adalah "Customer" maka nanti akan akan mengembalikan CLI customerSystem (perlu di explicit cast)
     */
    public UserSystemCLI getSystem(String role){
        if(role != "Customer"){
            return (UserSystemCLI) adminSystem;
        }else{
            return (UserSystemCLI) customerSystem;
        }
    }
}
