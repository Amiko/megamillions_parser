package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakdown;
import parserSpringData.repo.PrizeBreakdownRepository;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class PrizeBreakdownServiceTest {

    private DrawResult drawResult;
    private List<PrizeBreakdown> prizeBreakdown;
    private PrizeBreakdown jackpotPrizeBreakdown;
    private String url;
    private Document doc;
    @Mock
    private PrizeBreakdownRepository prizeBreakdownRepository;

    private PrizeBreakdownService prizeBreakdownService;

    @Before
    public void setUp() throws Exception {

        drawResult = new DrawResult(new Date(1485216000000L), new Integer[]{8,42,54,63,67},11,4);
        jackpotPrizeBreakdown = new PrizeBreakdown("5+1",0,177000000L,0, 0L,drawResult);

        prizeBreakdownService = new PrizeBreakdownService(){
            @Override
            protected Document getDocument(String url) throws IOException {
                File input = new File("src/test/resources/megaMillionsWinningNumbersPage.html");
                Document doc = Jsoup.parse(input,"UTF-8");
                return doc;
            }
        };

        File input = new File("src/test/resources/megaMillionsWinningNumbersPage.html");
        doc = Jsoup.parse(input,"UTF-8");
    }
    @Ignore
    @Test
    public void getJackpotPrizeBreakdownTest() throws Exception {

        PrizeBreakdown actualPrizeBreakdown = prizeBreakdownService.getJackpotPrizeBreakdown(doc,drawResult);

        PrizeBreakdown expectedPrizeBreakdown = new PrizeBreakdown("5+1",0,177000000L,0, 0L,drawResult);

        assertEquals(expectedPrizeBreakdown,actualPrizeBreakdown);
    }

    @Ignore
    @Test
    public void verifyGetPrizeBreakdown() throws IOException {


        prizeBreakdownService.getPrizeBreakdown(url,drawResult);

        verify(prizeBreakdownRepository,times(1)).save(jackpotPrizeBreakdown);
    }

    @Ignore
    @Test
    public void getPrizeBreakdownParseTest() throws IOException {


    }


}