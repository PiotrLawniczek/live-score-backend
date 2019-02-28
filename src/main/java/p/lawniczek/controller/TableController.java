package p.lawniczek.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lawniczek.dto.TableDto;
import p.lawniczek.webscrapting.service.FindTableService;

import java.util.List;

@RestController
@RequestMapping("/table")
public class TableController {

    private FindTableService findTableService;

    public TableController(FindTableService findTableService) {
        this.findTableService = findTableService;
    }

    @GetMapping
    public List<TableDto> getTables () {
        return findTableService.getTables();
    }
}
