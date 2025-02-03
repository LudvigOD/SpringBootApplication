package result;

import java.util.Optional;

import javax.swing.SwingUtilities;

import org.springframework.scheduling.annotation.Scheduled;
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
        fetchLatestTimes();
       
        

    
   //model.getAllTimesFromServer(times -> model.updateTimeTable(times), 1, Optional.empty(), Optional.empty());
    //model.getAllTimesFromServer(times -> System.out.println(times.toString()), 1, Optional.empty(), Optional.empty());
        
    }

    @Scheduled(fixedRate = 3000)
    public void fetchLatestTimes() {
    model.getAllTimesFromServer(times -> model.updateTimeTable(times), 1, Optional.empty(), Optional.empty());
}

}
