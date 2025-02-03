package result;

import java.util.Optional;

import javax.swing.SwingUtilities;
import org.springframework.web.reactive.function.client.WebClient;

import result.model.AdminModelImpl;
import result.view.AdminGUI;


public class AdminApplication implements Runnable {


    private AdminModelImpl model;
    public static void main(String[] args) {
        AdminApplication example = new AdminApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run() {
        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api")
            .build();
        model = new AdminModelImpl(webClient);
        AdminGUI gui = new AdminGUI(model);
        gui.setVisible(true);
        model.test();
       
        new Thread(() -> {
            while (true) {
                model.getAllTimesFromServer(times -> model.updateTimeTable(times), 1, Optional.empty(), Optional.empty());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println("Fetching thread interrupted.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();

    }

}
