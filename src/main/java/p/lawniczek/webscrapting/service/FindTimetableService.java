package p.lawniczek.webscrapting.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import p.lawniczek.dto.MatchDate;
import p.lawniczek.dto.TimetableDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FindTimetableService implements FindTimetable {

    public void getTimetable() {
        String url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html?round=1";

        try {
            Document doc = Jsoup.connect(url).get();
            Element section = doc.select("section[class=season__games games--player margin-40-0-0-0]").first();
            Elements tableRanking = section.select("article[class=season__game grid]");

            List<MatchDate> matchDateList = new ArrayList<>();
            for (Element element : tableRanking) {
                Element div = element.select("div[class=season__game-data grid-4 grid-mt-8 grid-msw-12 grid-ms-48]").first();
                matchDateList.add(
                        MatchDate
                                .builder()
                                .day(Integer.parseInt(div.select("span[class=day]").first().text()))
                                .monthAndYear(div.select("span[class=month]").first().text())
                                .hour(div.select("span[class=hour]").first().text())
                                .build()
                );
            }

            TimetableDto build = TimetableDto
                    .builder()
                    .round(1)//TODO not static round, get from url
                    .matchDate(matchDateList)
                    .build();

            log.info(String.valueOf(build));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
