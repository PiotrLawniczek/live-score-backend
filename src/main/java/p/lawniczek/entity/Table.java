package p.lawniczek.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Table extends AbstractEntity {
    private String team;
    private String games;
    private int points;
    private int wins;
    private int looses;
    private int draws;
    private int goalsScored;
    private int goalsConceded;
}
