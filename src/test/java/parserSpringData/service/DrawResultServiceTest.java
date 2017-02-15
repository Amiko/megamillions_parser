package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito.*;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakdown;
import parserSpringData.repo.PrizeBreakdownRepository;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by amiko on 1/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DrawResultServiceTest {

    private DrawResult drawResult;
    private DrawResultService drawResultService;
    private PrizeBreakdownRepository prizeBreakdownRepository;
    private Document doc;

    @Before
    public void setUp() throws Exception {

        File input = new File("src/test/resources/trTagTableRows.html");
        doc = Jsoup.parse(input,"UTF-8");

        drawResultService = new DrawResultService(){
            @Override
            protected Document getDocument() throws IOException {
                File input = new File("src/test/resources/trTagTableRows.html");
                Document doc = Jsoup.parse(input,"UTF-8");
                return doc;
            }
        };
    }

    @Test
    public void urlParseTest() throws IOException {
        Element row = doc.getElementById("trTest");

        String parsedURL = drawResultService.parsesURLForPrizeBreakDown(row);

        assertEquals("http://www.megamillions.com/winning-numbers/2-10-2017",parsedURL);
    }

    @Test
    public void getBallNumberListTest() throws IOException {
        Elements numberTr = doc.getElementsByClass("number");

        List<Integer> actualResult = drawResultService.getBallNumberList(numberTr);

        List<Integer> expectedResult = new ArrayList<>(Arrays.asList(32,39,51,62,75));

        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void convertArrayTest() {
        List<Integer> ballSet = new ArrayList<>(asList(11,22,33,44,55));

        Integer[] convertedArray = drawResultService.convertArray(ballSet);

        assertArrayEquals(new Integer[]{11,22,33,44,55}, convertedArray);
    }

    @Test
    public void parsesDrawResultTest(){
        Element row = doc.getElementById("trTest");

        DrawResult actualDrawResult = drawResultService.parsesDrawResult(row);

        DrawResult expectedDrawResult = new DrawResult(new Date(1486677600000L),new Integer[]{32,39,51,62,75},14,5 );

        assertEquals(expectedDrawResult,actualDrawResult);

    }

    @Ignore
    @Test
    public void getTableRowsTest() throws IOException {
        Elements actualRows = drawResultService.getTableRows();

        Elements expectedRows = new Elements();
    }

    @Ignore
    @Test
    public void verifyGetDrawResult() throws IOException {

        drawResultService.getDrawResult();

        verify(drawResultService, times(1)).getDrawResult();
    }



}