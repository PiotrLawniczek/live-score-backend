package p.lawniczek.webscrapting.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import p.lawniczek.dto.TableDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FindTableService implements FindTable {

    public void getTables() {

        String url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html";
        try {
            Document doc = Jsoup.connect(url).get();

            Element table = doc.select("table[class=table-template table-ranking]").first();
            List<TableDto> team = new ArrayList<>();

            Elements tableRanking = table.select("td[class=team-name]");

            for (Element tableElement : tableRanking) {
                Element games = tableElement.nextElementSibling();
                Element points = games.nextElementSibling();
                Element wins = points.nextElementSibling();
                Element looses = wins.nextElementSibling();
                Element draws = looses.nextElementSibling();
                Element goals = draws.nextElementSibling();

                team.add(
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
            log.info("Table list" + team);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
