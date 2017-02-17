package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakdown;
import parserSpringData.repo.PrizeBreakdownRepository;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PrizeBreakdownServiceTest {

    private DrawResult drawResult;
    private String url;
    private Document doc;
    private List<PrizeBreakdown> prizeBreakdown;
    private PrizeBreakdown jackpotPrizeBreakdown;
    @Mock
    private PrizeBreakdownRepository prizeBreakdownRepository;

    private PrizeBreakdownService prizeBreakdownService;

    @Before
    public void setUp() throws Exception {

        File input = new File("src/test/resources/megaMillionsWinningNumbersPage.html");
        doc = Jsoup.parse(input,"UTF-8");

        drawResult = new DrawResult(new Date(1485216000000L), new Integer[]{8,42,54,63,67},11,4);
        jackpotPrizeBreakdown = new PrizeBreakdown("5 + 1",0,177000000L,0, 0L,drawResult);
        prizeBreakdown = new ArrayList<>();
        prizeBreakdown.add(new PrizeBreakdown("5 + 0",2,1000000L,0,4000000L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("4 + 1",29,5000L,3,20000L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("4 + 0",412,500L,58,2000L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("3 + 1",2002,50L,229,200L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("3 + 0",27852,5L,3046,20L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("2 + 1",46742,5L,5077,20L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("1 + 1",413861,2L,46158,8L,drawResult));
        prizeBreakdown.add(new PrizeBreakdown("0 + 1",1132284,1L,128342,4L,drawResult));

        prizeBreakdownService = new PrizeBreakdownService(prizeBreakdownRepository){
            @Override
            protected Document getDocument(String url) throws IOException {
                return doc;
            }
        };


    }

    @Test
    public void verifyGetPrizeBreakdown() throws IOException {


        prizeBreakdownService.getPrizeBreakdown(url,drawResult);

        verify(prizeBreakdownRepository,times(1)).save(jackpotPrizeBreakdown);
        verify(prizeBreakdownRepository,times(1)).save(prizeBreakdown);
        verifyNoMoreInteractions(prizeBreakdownRepository);

        InOrder inOrder = inOrder(prizeBreakdownRepository);
        inOrder.verify(prizeBreakdownRepository).save(jackpotPrizeBreakdown);
        inOrder.verify(prizeBreakdownRepository).save(prizeBreakdown);
    }

    @Test
    public void getPrizeBreakdownResultsTest() throws IOException {

        List<PrizeBreakdown> actualPrizeBreakdownResult = prizeBreakdownService.getPrizeBreakdownResults(doc,drawResult);

        List<PrizeBreakdown> expectedPrizeBreakdownResult = prizeBreakdown;

        assertEquals(expectedPrizeBreakdownResult,actualPrizeBreakdownResult);

    }

    @Test
    public void getJackpotPrizeBreakdownTest() throws Exception {

        PrizeBreakdown actualPrizeBreakdown = prizeBreakdownService.getJackpotPrizeBreakdown(doc,drawResult);

        PrizeBreakdown expectedPrizeBreakdown = jackpotPrizeBreakdown;

        assertEquals(expectedPrizeBreakdown, actualPrizeBreakdown);
    }

    @Test
    public void getTableForBreakdownTest() throws IOException{

        Elements parsedlTrTags = prizeBreakdownService.getTableForBreakdown(doc);
        String actualTrTags = parsedlTrTags.html();

        String expectedTrTags = "<td>5 + 0</td> \n" + "<td>2</td> \n" + "<td>$1,000,000</td> \n" + "<td>0</td> \n" + "<td>$4,000,000</td>\n" +
                "<td>4 + 1</td> \n" + "<td>29</td> \n" + "<td>$5,000</td> \n" + "<td>3</td> \n" + "<td>$20,000</td>\n" +
                "<td>4 + 0</td> \n" + "<td>412</td> \n" + "<td>$500</td> \n" + "<td>58</td> \n" + "<td>$2,000</td>\n" +
                "<td>3 + 1</td> \n" + "<td>2,002</td> \n" + "<td>$50</td> \n" + "<td>229</td> \n" + "<td>$200</td>\n" +
                "<td>3 + 0</td> \n" + "<td>27,852</td> \n" + "<td>$5</td> \n" + "<td>3,046</td> \n" + "<td>$20</td>\n" +
                "<td>2 + 1</td> \n" + "<td>46,742</td> \n" + "<td>$5</td> \n" + "<td>5,077</td> \n" + "<td>$20</td>\n" +
                "<td>1 + 1</td> \n" + "<td>413,861</td> \n" + "<td>$2</td> \n" + "<td>46,158</td> \n" + "<td>$8</td>\n" +
                "<td>0 + 1</td> \n" + "<td>1,132,284</td> \n" + "<td>$1</td> \n" + "<td>128,342</td> \n" + "<td>$4</td>";

        assertEquals(expectedTrTags,actualTrTags);
    }

    @Test
    public void getTableForJacpotTest() throws IOException{

        Elements parsedTableForJackpot = prizeBreakdownService.getTableForJackpot(doc);
        String actualTableForJackpot = parsedTableForJackpot.html();

        String expectedTableForJackpot = "5 + 1\n" + "0\n" + "$177,000,000";

        assertEquals(expectedTableForJackpot,actualTableForJackpot);
    }

}