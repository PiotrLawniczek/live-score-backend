package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchResultDto {
    private String homeTeam;
    private String awayTeam;
    private String result;
    private String spot;
}
