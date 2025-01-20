package register.main;

import javax.swing.SwingUtilities;

import org.springframework.web.reactive.function.client.WebClient;

import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.view.RegisterGUI;

public class RegisterApplication {

    /*
     * We finally got it working!
     * To test the application, start the backend server first, then run this
     * application. When registering a time, the application will send both a POST
     * request to add the new time to the server and a GET request to get all times
     * of start number "01".
     */

    public static void main(String[] args) {
        // The url should be defined as a constant somewhere
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .build();
        RegisterModel model = new RegisterModelImpl(webClient);
        SwingUtilities.invokeLater(() -> {
            RegisterGUI gui = new RegisterGUI(model);
            gui.setVisible(true);
        });
    }

}
