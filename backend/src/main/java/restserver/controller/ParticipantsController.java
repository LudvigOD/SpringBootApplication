package restserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restserver.entity.Participant;
import restserver.service.ParticipantService;
import shared.dto.ParticipantDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/races/{raceId}/participants")
public class ParticipantsController {

    @Autowired
    private ParticipantService participantService;

    /**
     * @deprecated Use RaceConfigController instead
     */
    @PostMapping()
    public void createParticipant(@PathVariable int raceId,
            @RequestBody ParticipantDTO participantDTO) {
        participantService.addParticipant(raceId, participantDTO.getStartNbr(),
                participantDTO.getName());
    }

    /**
     * @deprecated Use RaceConfigController instead
     */
    @GetMapping()
    public List<ParticipantDTO> fetchParticipants(@PathVariable int raceId) {
        List<Participant> participants = participantService.fetchAllParticipants(raceId);

        return participants.stream().map(participant -> new ParticipantDTO(participant.getStartNbr(),
                participant.getName())).collect(Collectors.toList());

    }
}
