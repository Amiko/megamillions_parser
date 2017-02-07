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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by amiko on 1/23/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrizeBreakdownServiceTest {

    private DrawResult drawResult;
    private List<PrizeBreakdown> prizeBreakdown;
    private PrizeBreakdown jackpotResult;
    private String url;

    @Mock
    private PrizeBreakdownRepository prizeBreakdownRepository;

    @InjectMocks
    private PrizeBreakdownService prizeBreakdownService;

    @Before
    public void setUp() throws Exception {

        Date date = new Date(1485216000000L);
        Integer[] balls = {8,42,54,63,67};
        Integer megaball = 11;
        int megaplier = 4;
        drawResult = new DrawResult(date,balls,megaball,megaplier);
        jackpotResult = new PrizeBreakdown("5+1",0,177000000L,0, 0L,drawResult);
        prizeBreakdown = new ArrayList<>();
        url = "http://www.megamillions.com/winning-numbers";
        prizeBreakdownService = new PrizeBreakdownService(){
            @Override
            protected Document getDocument(String url) throws IOException {
                File input = new File("src/test/resources/megaMillionsParser.html");
                Document doc = Jsoup.parse(input,"UTF-8");
                return doc;
            }
        };
    }

    @Test
    public void verifyGetPrizeBreakdown() throws IOException {


        when(prizeBreakdownRepository.save(jackpotResult)).thenReturn(jackpotResult);

        prizeBreakdownService.getPrizeBreakdown(url,drawResult);

        verify(prizeBreakdownService, times(1)).getPrizeBreakdown(url,drawResult);
        verifyNoMoreInteractions(prizeBreakdownService);
    }

    @Ignore
    @Test
    public void getPrizeBreakdownParseTest() throws IOException {


    }


}