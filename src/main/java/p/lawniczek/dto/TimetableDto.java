package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimetableDto {
    private int round;
    private List<MatchDate> matchDate;
}