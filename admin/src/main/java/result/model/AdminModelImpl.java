package result.model;

import org.springframework.web.reactive.function.client.WebClient;

public class AdminModelImpl implements AdminModel{


    public AdminModelImpl(WebClient webClient) {

    }
    @Override
    public void addListener(AdminView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public void removeListener(AdminView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    // @Override
    // public void registerTime(String startNbr) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'registerTime'");
    // }
    // Behöver vi denna?

    @Override
    public void editTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editTime'");
    }

    @Override
    public void deleteTime(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTime'");
    }

    @Override
    public void editStartNbr(String startNbr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editStartNbr'");
    }

    @Override
    public void registerParticipant() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerParticipant'");
    }
    
}
