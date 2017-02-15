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
    PrizeBreakdownService prizeBreakdownService;

    public void getDrawResult() throws IOException {
        Elements rows = getTableRows();
        //Loop through to parse column values.
        for (Element row : rows) {

            DrawResult drawResult = parsesDrawResult(row);
            String url = parsesURLForPrizeBreakDown(row);
            //Save Iterated results and pass parameters to getPrizeBreakdown
            drawResultRepository.save(drawResult);
            prizeBreakdownService.getPrizeBreakdown(url, drawResult);

        }
    }

    //Parsed URL to pass into getPrizeBreakdown method.
    public String parsesURLForPrizeBreakDown(Element row) {

        Element link = row.select("a").first();
        String parsedURL = link.attr("href");
        String url = "http://www.megamillions.com" + parsedURL;

        return url;
    }

    public DrawResult parsesDrawResult(Element row){

        Elements numberTr = row.getElementsByClass("number");
        String drawDatesForLotto = row.getElementsByClass("dates").html();
        Date drawDates = new Date(drawDatesForLotto);
        Integer megaBall = new Integer(row.getElementsByClass("mega").first().html());
        Integer megaPlier = Integer.parseInt(row.getElementsByClass("mega").last().html());

        List<Integer> ballNumbers = getBallNumberList(numberTr);
        Integer[] ballNumberSet = convertArray(ballNumbers);

        return new DrawResult(drawDates, ballNumberSet, megaBall, megaPlier);
    }

    //Nested loop to parse column "Balls" values.
    public List<Integer> getBallNumberList(Elements numberTr) {
        List<Integer> ballNumber = new ArrayList<>();
        for (Element rowNumber : numberTr) {
            Integer numbers = new Integer(rowNumber.getElementsByClass("number").html());
            ballNumber.add(numbers);
        }
        return ballNumber;
    }

    public Integer[] convertArray(List<Integer> ballSet) {

        Integer[] ballNumberSet = new Integer[ballSet.size()];
        ballNumberSet = ballSet.toArray(ballNumberSet);

        return ballNumberSet;
    }

    //Navigate on last 25 drawings result table.
    public Elements getTableRows() throws IOException {
        Document doc = getDocument();
        Element tableBody = doc.getElementsByTag("tbody").first();
        Elements rows = tableBody.getElementsByTag("tr");

        return rows;
    }

    protected Document getDocument() throws IOException {
        Document doc = Jsoup.connect("http://www.megamillions.com/winning-numbers/last-25-drawings").get();
        return doc;
    }
}
