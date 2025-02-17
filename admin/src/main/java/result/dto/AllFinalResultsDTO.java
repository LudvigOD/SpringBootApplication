package result.dto;

import java.util.List;

public class AllFinalResultsDTO {
    private String teamToken;
    private List<FinalResultDTO> jsonResult;

    public AllFinalResultsDTO() {
        // The automatic JSON conversion requires a default constructor
    }

    public AllFinalResultsDTO(String teamToken, List<FinalResultDTO> jsonResult) {
        //this.teamToken = teamToken;
        this.teamToken = "df0ae509-490a-4226-af1d-96eef28fb5cd";
        this.jsonResult = jsonResult;
    }

    @Override
    public String toString() {
        return String.format("AllFinalResultsDTO{teamToken='%s', jsonResult='%s'}", teamToken, jsonResult);
    }
    
}


