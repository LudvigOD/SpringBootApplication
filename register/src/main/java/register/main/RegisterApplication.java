package register.main;

import javax.swing.SwingUtilities;

import org.springframework.web.reactive.function.client.WebClient;

import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.view.RegisterGUI;

public class RegisterApplication {

    

    public static void main(String[] args) {
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
