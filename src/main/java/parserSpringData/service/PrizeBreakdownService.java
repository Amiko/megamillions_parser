package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakdown;

import parserSpringData.repo.PrizeBreakdownRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
public class PrizeBreakdownService {


    private PrizeBreakdownRepository prizeBreakdownRepository;

    public PrizeBreakdownService(@Autowired PrizeBreakdownRepository prizeBreakdownRepository) {
        this.prizeBreakdownRepository = prizeBreakdownRepository;
    }

    public void getPrizeBreakdown(String url, DrawResult drawResult) throws IOException {

        Document doc = getDocument(url);

        PrizeBreakdown jackpotPrizeBreakdown = getJackpotPrizeBreakdown(doc, drawResult);
        prizeBreakdownRepository.save(jackpotPrizeBreakdown);

        List<PrizeBreakdown> prizeBreakdown = getPrizeBreakdownResults(doc,drawResult);
        prizeBreakdownRepository.save(prizeBreakdown);
    }

    protected Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public PrizeBreakdown getJackpotPrizeBreakdown(Document doc, DrawResult drawResult) {

        Elements tableForJacpot = getTableForJackpot(doc);
        //Parse Jackpot results.
        String tdMatch = tableForJacpot.get(0).html();
        Integer tdJackpotWinner = Integer.parseInt(tableForJacpot.get(1).html());
        Long jackPotPrize = Long.parseLong(tableForJacpot.get(2).html().replaceAll("[^\\d.]+",""));

        PrizeBreakdown jackpotPrizeBreakdown = new PrizeBreakdown(tdMatch,tdJackpotWinner,jackPotPrize, 0, 0L, drawResult );
        return jackpotPrizeBreakdown;
    }

    public Elements getTableForJackpot(Document doc){
        //Navigate on HTML for Jackpot Table.
        Element tableForJackpot = doc.getElementsByTag("tbody").first();
        Elements tbodyForJackpot = tableForJackpot.getElementsByTag("td");

        return tbodyForJackpot;
    }

    public List<PrizeBreakdown> getPrizeBreakdownResults(Document doc, DrawResult drawResult) {
        List<PrizeBreakdown> prizeBreakdown = new ArrayList<>();
        Elements trTags = getTableForBreakdown(doc);
        for(Element row: trTags) {

            String match = row.getElementsByTag("td").get(0).html();
            Integer winners = Integer.parseInt(row.getElementsByTag("td").get(1).html().replaceAll("[^\\d.]+", ""));
            Long prizeAmount = Long.parseLong(row.getElementsByTag("td").get(2).html().replaceAll("[^\\d.]+", ""));
            Integer megaplierWinners = Integer.parseInt(row.getElementsByTag("td").get(3).html().replaceAll("[^\\d.]+", ""));
            Long megaplierAmount = Long.parseLong(row.getElementsByTag("td").get(4).html().replaceAll("[^\\d.]+", ""));

            //Save each Iterated Result.
            prizeBreakdown.add(new PrizeBreakdown(match, winners, prizeAmount, megaplierWinners, megaplierAmount,drawResult));
        }
        return prizeBreakdown;
    }

    public Elements getTableForBreakdown(Document doc) {
        //Navigate on HTML for Prize Breakdown Table.
        Element tableForBreakDown = doc.getElementsByTag("tbody").last();
        Elements trTags = tableForBreakDown.getElementsByTag("tr");
        return  trTags;
    }
}
