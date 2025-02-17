package restserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restserver.entity.Participant;
import restserver.service.ParticipantService;
import shared.dto.ParticipantDTO;
import shared.dto.UpdateParticipantDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/races/{raceId}/participants")
public class ParticipantsController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping()
    public void createParticipant(@PathVariable int raceId,
            @RequestBody ParticipantDTO participantDTO) {
        participantService.addParticipant(raceId, participantDTO.getStartNbr(),
                participantDTO.getName());
    }

    @GetMapping()
    public List<ParticipantDTO> fetchParticipants(@PathVariable int raceId) {
        List<Participant> participants = participantService.fetchAllParticipants(raceId);

        return participants.stream().map(participant -> new ParticipantDTO(participant.getStartNbr(),
                participant.getName())).collect(Collectors.toList());

    }

    @PutMapping("{participantId}")
    public void updateParticipant(@PathVariable int raceId, @PathVariable long participantId,
            @RequestBody UpdateParticipantDTO updateDto) {
        participantService.editParticipant(participantId, updateDto.getStartNbr(), updateDto.getName());
    }

    @DeleteMapping("{participantId}")
    public void deleteParticipant(@PathVariable int raceId, @PathVariable long participantId) {
        participantService.deleteParticipant(participantId);
    }
}
