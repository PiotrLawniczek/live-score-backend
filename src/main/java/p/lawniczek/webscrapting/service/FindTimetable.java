package p.lawniczek.webscrapting.service;

import p.lawniczek.dto.TimetableDto;

public interface FindTimetable  {
    TimetableDto getTimetable(Long roundNumber);
}
