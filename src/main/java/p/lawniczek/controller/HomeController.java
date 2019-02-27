package p.lawniczek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lawniczek.webscrapting.service.FindTable;

@RestController
public class HomeController {

    private FindTable findTable;

    @Autowired
    public HomeController(FindTable findTable) {
        this.findTable = findTable;
    }

    @GetMapping("/")
    public String home() {
        findTable.getTables();
        return null;
    }


}
