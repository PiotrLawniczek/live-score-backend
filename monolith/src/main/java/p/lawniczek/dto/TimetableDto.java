package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimetableDto {
    private Long round;
    private List<MatchDto> matchDto;
}