package result.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public abstract class AbsAdminModelFilter implements AdminModel {
    protected final AdminModel delegate;
    protected final Map<AdminModelObserver, AdminModelObserver> proxyObservers = new HashMap<>();

    public AbsAdminModelFilter(AdminModel delegate) {
        this.delegate = delegate;
    }

    @Override
    public void addListener(AdminModelObserver observer) {
        AdminModelObserver proxyObserver = new AdminModelObserver() {
            @Override
            public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
                observer.onDataUpdated(times.stream().filter(AbsAdminModelFilter.this::filterTime).toList(),
                        participants.stream().filter(AbsAdminModelFilter.this::filterParticipant).toList());
            }
        };

        proxyObservers.put(observer, proxyObserver);
        delegate.addListener(proxyObserver);
    }

    @Override
    public void removeListener(AdminModelObserver observer) {
        Optional.ofNullable(proxyObservers.remove(observer)).ifPresent(delegate::removeListener);
    }

    @Override
    public List<TimeDTO> getAllTimes() {
        return this.delegate.getAllTimes().stream().filter(this::filterTime).toList();
    }

    @Override
    public List<ParticipantDTO> getAllParticipants() {
        return this.delegate.getAllParticipants().stream().filter(this::filterParticipant).toList();
    }

    @Override
    public void updateTime(int raceID, TimeDTO time) {
        this.delegate.updateTime(raceID, time);
    }

    protected abstract boolean filterTime(TimeDTO time);

    protected abstract boolean filterParticipant(ParticipantDTO participant);

    public void sendPostRequest(ParticipantDTO dto, int raceId) {
        delegate.sendPostRequest(dto, raceId);
    
  }
}

