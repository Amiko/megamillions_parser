package parserSpringData.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import parserSpringData.entity.DrawResult;

import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created by amiko on 1/23/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrizeBreakdownServiceTest {

    private DrawResult drawResult;
    private String url;

    @Mock
    PrizeBreakdownService prizeBreakdownService;
    @Before
    public void setUp() throws Exception {

        Date date = new Date(1485216000000L);
        Integer[] balls = {8,42,54,63,67};
        Integer megaball = 11;
        int megaplier = 4;
        drawResult = new DrawResult(date,balls,megaball,megaplier);
        url = "/resources/megaMillionsParser.html";
    }

    @Test
    public void verifyGetPrizeBreakdown() throws IOException {

        prizeBreakdownService.getPrizeBreakdown(url,drawResult);

        verify(prizeBreakdownService, times(1)).getPrizeBreakdown(url, drawResult);
        verifyNoMoreInteractions(prizeBreakdownService);
    }
    @Ignore
    @Test
    public void getPrizeBreakdownParseTest() throws IOException {

    }


}