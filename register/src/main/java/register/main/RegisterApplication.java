package register.main;

import javax.swing.SwingUtilities;

import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.view.RegisterGUI;

public class RegisterApplication {

    /*
     * We finally got it working!
     * To test the application, start the backend server first, then run this
     * application. When registering a time, the application will send both a
     * GET request to get all times of start number "01" and a POST request to
     * add the new time.
     */

    public static void main(String[] args) {
        RegisterModel model = new RegisterModelImpl();
        SwingUtilities.invokeLater(() -> {
            RegisterGUI gui = new RegisterGUI(model);
            gui.setVisible(true);
        });
    }

}
