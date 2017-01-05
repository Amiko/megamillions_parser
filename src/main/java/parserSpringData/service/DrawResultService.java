package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parserSpringData.entity.DrawResult;
import parserSpringData.configFiles.DataSourceConfig;
import parserSpringData.repo.DrawResultRepository;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amiko on 29-Dec-16.
 */
@Service
public class DrawResultService {
    @Autowired
    DrawResultRepository drawResultRepository;
    @Autowired
    DataSourceConfig dataSourceConfig;
    @Autowired
    PrizeBreakDownService prizeBreakDownService;

    public void getDrawResult() throws IOException {

        Document dateForDrawing = Jsoup.connect("http://www.megamillions.com/winning-numbers/last-25-drawings").get();
        Element table = dateForDrawing.getElementsByTag("tbody").first();
        Elements rows = table.getElementsByTag("tr");

        for (Element row : rows) {

            List<Integer> ballNumber = new ArrayList<>();
            Elements numberTr = row.getElementsByClass("number");
            String drawDatesForLotto = row.getElementsByClass("dates").html();
            Date drawDates = new Date(drawDatesForLotto);
            Integer megaBall = new Integer(row.getElementsByClass("mega").first().html());
            Integer megaPlier = Integer.parseInt(row.getElementsByClass("mega").last().html());
            Element link = row.select("a").first();
            String parsedURL = link.attr("href");
            String url = "http://www.megamillions.com" + parsedURL;


            for (Element rowNumber : numberTr) {

                Integer numbers = new Integer(rowNumber.getElementsByClass("number").html());
                ballNumber.add(numbers);
            }

            Integer[] ballNumberSet = convertArray(ballNumber);

            DrawResult drawResult = new DrawResult(drawDates, ballNumberSet, megaBall,megaPlier);

            drawResultRepository.save(drawResult);
            prizeBreakDownService.getPrizeBreakDown(url, drawResult);

        }
    }

    public Integer[]convertArray(List<Integer> ballSet) {

        Integer[] ballNumberSet = new Integer[ballSet.size()];
        ballNumberSet = ballSet.toArray(ballNumberSet);

        return ballNumberSet;
    }
}
