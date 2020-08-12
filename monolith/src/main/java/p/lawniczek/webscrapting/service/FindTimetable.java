package p.lawniczek.webscrapting.service;

import p.lawniczek.dto.MatchDto;
import p.lawniczek.dto.RoundInfoDto;

import java.util.List;

public interface FindTimetable  {
    List<MatchDto> getTimetable(Long roundNumber);
    RoundInfoDto getNearestRoundToDate();
}
