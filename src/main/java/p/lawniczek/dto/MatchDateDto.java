package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDateDto {
    private int day;
    private String monthAndYear;
    private String hour;
}