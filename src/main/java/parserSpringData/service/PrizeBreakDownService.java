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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by amiko on 29-Dec-16.
 */

@Service
public class PrizeBreakDownService {

    @Autowired
    private
    PrizeBreakDownRepository prizeBreakDownRepository;

    public void getPrizeBreakDown(String url, DrawResult drawResult) throws IOException {

        Document doc = Jsoup.connect(url).get();

        PrizeBreakDown jackpotResult = getJackpotResults(doc, drawResult);
        prizeBreakDownRepository.save(jackpotResult);

        List<PrizeBreakDown> prizeBreakdown = getBreakDownResults(doc,drawResult);
        prizeBreakDownRepository.save(prizeBreakdown);
    }
    private PrizeBreakDown getJackpotResults(Document doc, DrawResult drawResult) {

        Elements tableForJacpot = getTableForJackpot(doc);
        //Parse Jackpot results.
        String tdMatch = tableForJacpot.get(0).html();
        Integer tdJackpotWinner = Integer.parseInt(tableForJacpot.get(1).html());
        Long jackPotPrize = Long.parseLong(tableForJacpot.get(2).html().replaceAll("[^\\d.]+",""));

        PrizeBreakDown jackpot = new PrizeBreakDown(tdMatch,tdJackpotWinner,jackPotPrize, 0, 0L, drawResult );
        return jackpot;
    }

    private Elements getTableForJackpot(Document doc){
        //Navigate on HTML for Jackpot Table.
        Element tableForJackpot = doc.getElementsByTag("tbody").first();
        Elements tbodyForJackpot = tableForJackpot.getElementsByTag("td");

        return tbodyForJackpot;
    }

    private List<PrizeBreakDown> getBreakDownResults(Document doc, DrawResult drawResult) {
        List<PrizeBreakDown> prizeBreakdown = new ArrayList<>();
        Elements trTags = getTableForBreakDown(doc);
        for(Element row: trTags) {

            String match = row.getElementsByTag("td").get(0).html();
            Integer winners = Integer.parseInt(row.getElementsByTag("td").get(1).html().replaceAll("[^\\d.]+", ""));
            Long prizeAmount = Long.parseLong(row.getElementsByTag("td").get(2).html().replaceAll("[^\\d.]+", ""));
            Integer megaplierWinners = Integer.parseInt(row.getElementsByTag("td").get(3).html().replaceAll("[^\\d.]+", ""));
            Long megaplierAmount = Long.parseLong(row.getElementsByTag("td").get(4).html().replaceAll("[^\\d.]+", ""));

            //Save each Iterated Result.
            prizeBreakdown.add(new PrizeBreakDown(match, winners, prizeAmount, megaplierWinners, megaplierAmount,drawResult));
        }
        return prizeBreakdown;
    }

    private Elements getTableForBreakDown(Document doc) {
        //Navigate on HTML for Prize Breakdown Table.
        Element tableForBreakDown = doc.getElementsByTag("tbody").last();
        Elements trTags = tableForBreakDown.getElementsByTag("tr");
        return  trTags;
    }
}
