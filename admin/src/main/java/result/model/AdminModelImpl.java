package result.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import shared.dto.TimeDTO;

public class AdminModelImpl implements AdminModel {

    private List<TimeDTO> times;

    private WebClient webClient;

    // Ändra så att man kan ha fler tävlingar i senare skede, dessa attribut får flyttas dit isf.
    private int nbrCompetitors;
    private int nbrStations;

    public AdminModelImpl(WebClient webClient) {
        this.times = new ArrayList<>();
        
        this.webClient = webClient;
    }
    // @Override
    // public void addListener(AdminView view) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    // }

    // @Override
    // public void removeListener(AdminView view) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    // }


    @Override
    public List<TimeDTO> getParticipantTimes(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getParticipantTimes'");
    }
    @Override
    public void startCompetition(int nbrCompetitors, int nbrStations) {
        this.nbrCompetitors = nbrCompetitors;
        this.nbrStations = nbrStations;
    }

    public int getNbrCompetitors() {
        return nbrCompetitors;
    }

    public int getNbrStations() {
        return nbrStations;
    }

}