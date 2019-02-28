package p.lawniczek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lawniczek.webscrapting.service.FindTableService;
import p.lawniczek.webscrapting.service.FindTimetableService;

@RestController
public class HomeController {

    private FindTableService findTableService;
    private FindTimetableService findTimetableService;

    @Autowired
    public HomeController(FindTableService findTableService, FindTimetableService findTimetableService) {
        this.findTableService = findTableService;
        this.findTimetableService = findTimetableService;
    }

    @GetMapping("/")
    public String home() {
        findTableService.getTables();
       // findTimetableService.getTimetable(null);
        return null;
    }


}
