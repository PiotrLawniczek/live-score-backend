package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDto {
    private MatchDateDto matchDateDto;
    private MatchResultDto matchResultDto;
}
