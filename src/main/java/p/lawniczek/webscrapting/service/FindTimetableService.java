package p.lawniczek.webscrapting.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import p.lawniczek.dto.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class FindTimetableService implements FindTimetable {
    String url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html?round=";

    public List<MatchDto> getTimetable(Long roundNumber) {
        url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html?round=" + roundNumber;

        try {
            Document doc = Jsoup.connect(url).get();
            Elements tableRanking = getTableRanking(doc);

            List<MatchDto> matchDtoList = new ArrayList<>();
            for (Element element : tableRanking) {

                Element divDate = element.select("div[class=season__game-data grid-4 grid-mt-8 grid-msw-12 grid-ms-48]").first();

                MatchDateDto matchDateDto = getMatchDateDto(divDate);

                Element divGameResult =
                        element.select("div[class=teams grid-38 grid-mt-48 grid-msw-48]").first();

                MatchResultDto matchResultDto = MatchResultDto
                        .builder()
                        .homeTeam(divGameResult.select("a[class=team]").first().text())
                        .awayTeam(divGameResult.select("a[class=team versus]").first().text())
                        .result(null)
                        .spot(divGameResult.select("span[class=spot]").first().text())
                        .build();
                Element score = divGameResult.select("span[class=score]").first();
                if (score != null) {
                    matchResultDto.setResult(score.text());
                } else {
                    matchResultDto.setResult("-");
                }

                MatchDto matchDto = MatchDto
                        .builder()
                        .matchDateDto(matchDateDto)
                        .matchResultDto(matchResultDto)
                        .build();

                matchDtoList.add(matchDto);


            }
            /*TimetableDto timetableDto = TimetableDto
                    .builder()
                    .round(roundNumber)//TODO not static round, get from url
                    .matchDto(matchDtoList)
                    .build();
            log.info(String.valueOf(timetableDto));*/
            log.info(String.valueOf(matchDtoList));

            return matchDtoList;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private MatchDateDto getMatchDateDto(Element divDate) {
        return MatchDateDto
                .builder()
                .day(Integer.parseInt(divDate.select("span[class=day]").first().text()))
                .monthAndYear(divDate.select("span[class=month]").first().text())
                .hour(divDate.select("span[class=hour]").first().text())
                .build();
    }

    private Elements getTableRanking(Document doc) {
        Element section = doc.select("section[class=season__games games--player margin-40-0-0-0]").first();
        return section.select("article[class=season__game grid]");
    }

    public RoundInfoDto getNearestRoundToDate() {
        url = "https://www.laczynaspilka.pl/rozgrywki/nizsze-ligi,26883.html?round=0";
        long round = 1;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tableRanking = getTableRanking(doc);

            List<RoundDateDto> roundDateDtoList = new ArrayList<>();

            for (Element element : tableRanking) {

                Element divDate = element.select("div[class=season__game-data grid-4 grid-mt-8 grid-msw-12 grid-ms-48]").first();
                Element divRound =
                        element.select("div[class=season__game-action grid-16 grid-mt-12 grid-msw-48]").first();
                Element divEvent = divRound.select("div[class=event]").first();


                if (StringUtils.contains(divEvent.text(), round + " kolejka")) {
                    MatchDateDto matchDateDto = getMatchDateDto(divDate);

                    roundDateDtoList.add(RoundDateDto
                            .builder()
                            .round(round)
                            .matchDateDto(matchDateDto)
                            .build());

                    round++;
                }
            }
            long ret = 0;
            long result = 0;
            for (RoundDateDto roundDateDto : roundDateDtoList) {
                String roundDate =
                        roundDateDto.getMatchDateDto().getDay() + "/" + roundDateDto.getMatchDateDto().getMonthAndYear();

                try {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = simpleDateFormat.parse(roundDate);
                    String currentDate = simpleDateFormat.format(new Date());
                    Date currentDate2 = simpleDateFormat.parse(currentDate);

                    if (Math.abs(currentDate2.getTime() - ret) > Math.abs(currentDate2.getTime() - date.getTime())) {
                        ret = date.getTime();
                        result = roundDateDto.getRound();
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            log.info(String.valueOf(result));
            RoundInfoDto build = RoundInfoDto
                    .builder()
                    .nearestRound(result)
                    .numberOfRounds(roundDateDtoList.get(roundDateDtoList.size() - 1).getRound())
                    .build();
            log.info(String.valueOf(build));
            return build;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
