package p.lawniczek.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lawniczek.dto.TimetableDto;
import p.lawniczek.webscrapting.service.FindTimetableService;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    private FindTimetableService findTimetableService;

    public TimetableController(FindTimetableService findTimetableService) {
        this.findTimetableService = findTimetableService;
    }

    @GetMapping("/round={roundNumber}")
    public TimetableDto timetable(@PathVariable("roundNumber") Long roundNumber) {
        return findTimetableService.getTimetable(roundNumber);
    }
}
