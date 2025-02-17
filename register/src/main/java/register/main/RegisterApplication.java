package register.main;

import javax.swing.SwingUtilities;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.view.RegisterGUI;

public class RegisterApplication {
    
    private static String url;

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("No argument passed for which server to connect to, defaulting to localhost.");
        }
        url = args.length == 0 ? "http://localhost:8080/api" : "http://" + args[0] + "/api";

        WebClient webClient = WebClient.builder()
            .baseUrl(url)
            .build();

        try {
            webClient.get()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .block(); 
                }
        catch (WebClientException e) {
            if(!e.getMessage().contains("404")) {
                    System.out.println("Can't connect to server.");
                    System.exit(0);
            }
        }
            
        RegisterModel model = new RegisterModelImpl(webClient);
        SwingUtilities.invokeLater(() -> {
            RegisterGUI gui = new RegisterGUI(model);
            gui.setVisible(true);
        });
    }

}
