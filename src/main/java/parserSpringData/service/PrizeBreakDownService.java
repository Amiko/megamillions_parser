package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakDown;

import parserSpringData.repo.PrizeBreakDownRepository;

import java.io.IOException;


/**
 * Created by amiko on 29-Dec-16.
 */

@Service
public class PrizeBreakDownService {

    @Autowired
    PrizeBreakDownRepository prizeBreakDownRepository;

    public void getPrizeBreakDown(String url, DrawResult drawResult) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Element table2 = doc.getElementsByTag("tbody").last();
        Elements trTags = table2.getElementsByTag("tr");
        Element table1 = doc.getElementsByTag("tbody").first();
        Elements tbody1 = table1.getElementsByTag("td");

        String tdMatch = tbody1.get(0).html();
        Integer tdJackpotWinner = Integer.parseInt(tbody1.get(1).html());
        Long jackPotPrize = Long.parseLong(tbody1.get(2).html().replaceAll("[^\\d.]+",""));

        PrizeBreakDown jackpot = new PrizeBreakDown(tdMatch,tdJackpotWinner,jackPotPrize, 0, 0L, drawResult );
        prizeBreakDownRepository.save(jackpot);


        for(Element row: trTags) {

            String match = row.getElementsByTag("td").get(0).html();
            Integer winners = Integer.parseInt(row.getElementsByTag("td").get(1).html().replaceAll("[^\\d.]+", ""));
            Long prizeAmount = Long.parseLong(row.getElementsByTag("td").get(2).html().replaceAll("[^\\d.]+", ""));
            Integer megaplierWinners = Integer.parseInt(row.getElementsByTag("td").get(3).html().replaceAll("[^\\d.]+", ""));
            Long megaplierAmount = Long.parseLong(row.getElementsByTag("td").get(4).html().replaceAll("[^\\d.]+", ""));

            PrizeBreakDown breakDown = new PrizeBreakDown(match, winners, prizeAmount, megaplierWinners, megaplierAmount,drawResult );
            prizeBreakDownRepository.save(breakDown);
        }
    }
}
