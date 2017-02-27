package parserSpringData.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import parserSpringData.entity.DrawResult;
import parserSpringData.repo.DrawResultRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DrawResultServiceTest {

    @Mock
    private DrawResultRepository drawResultRepository;
    @Mock
    private PrizeBreakdownService prizeBreakdownService;

    private DrawResultService drawResultService;
    private Document doc;
    private DrawResult drawResult;
    @Before
    public void setUp() throws Exception {
        drawResult = new DrawResult(new Date(1486677600000L),new Integer[]{32,39,51,62,75},14,5 );

        File input = new File("src/test/resources/trTagTableRows.html");
        doc = Jsoup.parse(input,"UTF-8");

        drawResultService = new DrawResultService(drawResultRepository, prizeBreakdownService){
            @Override
            protected Document getDocument() throws IOException {
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
    public void getTableRowsTest() throws IOException {

        Elements parsedRows = drawResultService.getTableRows();
        String actualRows = parsedRows.html();

        String expectedRows = "<td class=\"dates\"> 2/10/2017 </td> \n" +
                "<td class=\"number\"> 32 </td> \n" +
                "<td class=\"number\"> 39 </td> \n" +
                "<td class=\"number\"> 51 </td> \n" +
                "<td class=\"number\"> 62 </td> \n" +
                "<td class=\"number\"> 75 </td> \n" +
                "<td class=\"mega\"> 14 </td> \n" +
                "<td class=\"mega\"> 5 </td> \n" +
                "<td class=\"details\"> <a href=\"/winning-numbers/2-10-2017\">Details</a> </td>";

        assertEquals(expectedRows,actualRows);
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
        Integer[] expectedArray = new Integer[]{11,22,33,44,55};
        
        Integer[] convertedArray = drawResultService.convertArray(ballSet);

        assertNotEquals(expectedArray,ballSet);
        assertArrayEquals(expectedArray, convertedArray);
        assertEquals(expectedArray.getClass(),convertedArray.getClass());
    }

    @Test
    public void parsesDrawResultTest(){
        Element row = doc.getElementById("trTest");

        DrawResult actualDrawResult = drawResultService.parsesDrawResult(row);

        DrawResult expectedDrawResult = drawResult;

        assertEquals(expectedDrawResult,actualDrawResult);

    }

    @Test
    public void verifySave() throws IOException {

        drawResultService.getDrawResult();

        verify(drawResultRepository, times(1)).save(drawResult);
        verifyNoMoreInteractions(drawResultRepository);
    }

}