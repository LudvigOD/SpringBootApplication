package result.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import result.AdminView;

public class AdminModelImpl implements AdminModel {


    public AdminModelImpl(WebClient webClient) {

    }

    private List<TimeDTO> times;

    private WebClient webClient;

    // Ändra så att man kan ha fler tävlingar i senare skede, dessa attribut får
    // flyttas dit isf.
    private int nbrCompetitors;
    private int nbrStations;

    public AdminModelImpl(WebClient webClient) {
        this.times = new ArrayList<>();

        this.webClient = webClient;
    }
    // @Override
    // public void addListener(AdminView view) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'addListener'");
    // }

    // @Override
    // public void removeListener(AdminView view) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'removeListener'");
    // }

    @Override
    public List<TimeDTO> getParticipantTimes(String startNbr) {
    // public void registerTime(String startNbr) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'registerTime'");
    // }
    // Behöver vi denna?

    public void editTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editTime'");
    }

    public void deleteTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTime'");
    }

    public void editStartNbr(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editStartNbr'");
    }

    public void registerParticipant() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerParticipant'");
    }

    public void addListener(AdminView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");

    @Override
    public void startCompetition(int nbrCompetitors, int nbrStations) {
        this.nbrCompetitors = nbrCompetitors;
        this.nbrStations = nbrStations;
    }

    public void removeListener(AdminView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    public int getNbrStations() {
        return nbrStations;
    }



  public void sendNonBlockingGetRequest(
      Consumer<List<TimeDTO>> responseHandler) {
    webClient.get()
        .uri("/time/startNbr/01")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        // Use ParameterizedTypeReference to keep the generic type information, rather
        // than just a List.class
        .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        })
        // .bodyToFlux(TimeDTO.class)
        .subscribe(responseHandler);
  }

  public List<TimeDTO> sendBlockingGetRequest(String startNbr) {
    return webClient.get()
        .uri("/time/startNbr/" + startNbr)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        })
        .block(); 
  }
    public void registerTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerTime'");
    }

}

    @Override
    public List<List<TimeDTO>> getAllTimes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTimes'");
    }

    @Override
    public int getTotalTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalTime'");
    }

}
