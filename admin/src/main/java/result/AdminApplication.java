package result;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            model.getAllTimesFromServer(times -> model.filterNewTimes(times), 1, Optional.empty(), Optional.empty());
        }, 0, 3, java.util.concurrent.TimeUnit.SECONDS);

    }

}
