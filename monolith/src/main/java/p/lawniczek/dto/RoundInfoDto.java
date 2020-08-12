package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundInfoDto {
    private long nearestRound;
    private long numberOfRounds;
}
