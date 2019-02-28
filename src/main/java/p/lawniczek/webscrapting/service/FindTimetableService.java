package p.lawniczek.webscrapting.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import p.lawniczek.dto.MatchDateDto;
import p.lawniczek.dto.MatchDto;
import p.lawniczek.dto.MatchResultDto;
import p.lawniczek.dto.TimetableDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FindTimetableService implements FindTimetable {

    public TimetableDto getTimetable(Long roundNumber) {
        String url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html?round=" + roundNumber;

        try {
            Document doc = Jsoup.connect(url).get();
            Element section = doc.select("section[class=season__games games--player margin-40-0-0-0]").first();
            Elements tableRanking = section.select("article[class=season__game grid]");

           List<MatchDto> matchDtoList = new ArrayList<>();
            for (Element element : tableRanking) {

                Element divDate = element.select("div[class=season__game-data grid-4 grid-mt-8 grid-msw-12 grid-ms-48]").first();

                MatchDateDto matchDateDto = MatchDateDto
                        .builder()
                        .day(Integer.parseInt(divDate.select("span[class=day]").first().text()))
                        .monthAndYear(divDate.select("span[class=month]").first().text())
                        .hour(divDate.select("span[class=hour]").first().text())
                        .build();

                Element divGameResult =
                        element.select("div[class=teams grid-38 grid-mt-48 grid-msw-48]").first();

                MatchResultDto matchResultDto = MatchResultDto
                        .builder()
                        .homeTeam(divGameResult.select("a[class=team]").first().text())
                        .awayTeam(divGameResult.select("a[class=team versus]").first().text())
                        .result(divGameResult.select("span[class=score]").first().text())
                        .spot(divGameResult.select("span[class=spot]").first().text())
                        .build();

                MatchDto matchDto = MatchDto
                        .builder()
                        .matchDateDto(matchDateDto)
                        .matchResultDto(matchResultDto)
                        .build();

                matchDtoList.add(matchDto);


            }
            TimetableDto timetableDto = TimetableDto
                    .builder()
                    .round(roundNumber)//TODO not static round, get from url
                    .matchDto(matchDtoList)
                    .build();
            log.info(String.valueOf(timetableDto));
            return timetableDto;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
