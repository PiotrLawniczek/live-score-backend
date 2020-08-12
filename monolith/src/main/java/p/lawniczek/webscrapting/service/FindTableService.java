package p.lawniczek.webscrapting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import p.lawniczek.dto.TableDto;
import p.lawniczek.entity.Table;
import p.lawniczek.entity.repository.TimetableRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindTableService implements FindTable {

    private final TimetableRepository timetableRepository;

    public List<TableDto> getTables() {
        String url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,37275.html#";
        try {
            Document doc = Jsoup.connect(url).get();

            Element table = doc.select("table[class=table-template table-ranking]").first();
            Elements tableRanking = table.select("td[class=team-name]");

            List<TableDto> tableDtoList = new ArrayList<>();
            for (Element tableElement : tableRanking) {
                Element games = tableElement.nextElementSibling();
                Element points = games.nextElementSibling();
                Element wins = points.nextElementSibling();
                Element looses = wins.nextElementSibling();
                Element draws = looses.nextElementSibling();
                Element goals = draws.nextElementSibling();

                tableDtoList.add(
                        TableDto.builder()
                                .team(tableElement.text())
                                .games(games.text())
                                .points(Integer.parseInt(points.text()))
                                .wins(Integer.parseInt(wins.text()))
                                .looses(Integer.parseInt(looses.text()))
                                .draws(Integer.parseInt(draws.text()))
                                .goalsScored(Integer.parseInt(goals.text().split(":")[0]))
                                .goalsConceded(Integer.parseInt(goals.text().split(":")[1]))
                                .build());
            }
            log.info("Table list" + tableDtoList);
            ModelMapper mapper = new ModelMapper();
            timetableRepository.saveAll(
                    tableDtoList.stream().map(
                            o -> mapper.map(o, Table.class)
                    ).collect(Collectors.toList()));

            return tableDtoList;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
