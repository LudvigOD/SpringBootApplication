package result;

import java.util.Optional;

import javax.swing.SwingUtilities;

import org.springframework.web.reactive.function.client.WebClient;

import result.model.AdminModelImpl;
import result.view.AdminGUI;

public class AdminApplication implements Runnable {

    public static void main(String[] args) {
        AdminApplication example = new AdminApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run() {
        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api")
            .build();
        AdminModelImpl model = new AdminModelImpl(webClient);
        AdminGUI gui = new AdminGUI(model);
        gui.setVisible(true);
        model.test();
        model.getAllTimesFromServer(times -> model.updateTimeTable(times), 1, Optional.empty(), Optional.empty());
        model.getAllTimesFromServer(times -> System.out.println(times.toString()), 1, Optional.empty(), Optional.empty());
        
    }
}
