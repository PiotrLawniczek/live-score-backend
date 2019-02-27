package p.lawniczek.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableDto {
    private String team;
    private String games;
    private int points;
    private int wins;
    private int looses;
    private int draws;
    private int goalsScored;
    private int goalsConceded;
}