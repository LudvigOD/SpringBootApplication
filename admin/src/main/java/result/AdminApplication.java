package result;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.SwingUtilities;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import result.model.AdminModelImpl;
import result.view.AdminGUI;

public class AdminApplication implements Runnable {

    private AdminModelImpl model;
    private static String url;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument passed for which server to connect to, defaulting to localhost.");
        }
        url = args.length == 0 ? "http://localhost:8080/api" : "http://" + args[0] + "/api";
        AdminApplication example = new AdminApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run() {

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .build();

        try {
            webClient.get()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientException e) {
            if (!e.getMessage().contains("404")) {
                System.out.println("Can't connect to server.");
                System.exit(0);
            }
        }

        model = new AdminModelImpl(webClient);
        AdminGUI gui = new AdminGUI(model);
        gui.setVisible(true);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            model.fetchUpdates();
        }, 0, 3, java.util.concurrent.TimeUnit.SECONDS);
    }
}
