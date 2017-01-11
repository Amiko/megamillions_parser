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

        //Navigate on HTML for Jackpot Table.
        Element tableForJackpot = doc.getElementsByTag("tbody").first();
        Elements tbodyForJackpot = tableForJackpot.getElementsByTag("td");

        //Parse Jackpot results.
        String tdMatch = tbodyForJackpot.get(0).html();
        Integer tdJackpotWinner = Integer.parseInt(tbodyForJackpot.get(1).html());
        Long jackPotPrize = Long.parseLong(tbodyForJackpot.get(2).html().replaceAll("[^\\d.]+",""));

        //Save Jackpot to DB.
        PrizeBreakDown jackpot = new PrizeBreakDown(tdMatch,tdJackpotWinner,jackPotPrize, 0, 0L, drawResult );
        prizeBreakDownRepository.save(jackpot);

        //Navigate on HTML for Prize Breakdown Table.
        Element tableForBreakDown = doc.getElementsByTag("tbody").last();
        Elements trTags = tableForBreakDown.getElementsByTag("tr");

        //Loop through Tags and parse breakdown Result.
        for(Element row: trTags) {

            String match = row.getElementsByTag("td").get(0).html();
            Integer winners = Integer.parseInt(row.getElementsByTag("td").get(1).html().replaceAll("[^\\d.]+", ""));
            Long prizeAmount = Long.parseLong(row.getElementsByTag("td").get(2).html().replaceAll("[^\\d.]+", ""));
            Integer megaplierWinners = Integer.parseInt(row.getElementsByTag("td").get(3).html().replaceAll("[^\\d.]+", ""));
            Long megaplierAmount = Long.parseLong(row.getElementsByTag("td").get(4).html().replaceAll("[^\\d.]+", ""));

            //Save each Iterated Result.
            PrizeBreakDown breakDown = new PrizeBreakDown(match, winners, prizeAmount, megaplierWinners, megaplierAmount,drawResult );
            prizeBreakDownRepository.save(breakDown);
        }
    }
}
