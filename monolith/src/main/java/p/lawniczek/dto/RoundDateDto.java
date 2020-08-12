package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundDateDto {
    private Long round;
    private MatchDateDto matchDateDto;
}
