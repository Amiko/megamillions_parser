package parserSpringData.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import parserSpringData.entity.DrawResult
import parserSpringData.repo.DrawResultRepository
import spock.lang.Specification

/**
 * Created by amiko on 2/21/2017.
 */
class DrawResultServiceSpockTest extends Specification {

    def drawResultRepository = Mock(DrawResultRepository)
    def prizeBreakdownService = Mock(PrizeBreakdownService)
    def drawResultService
    def doc
    def drawResult
    def setup(){
        drawResult = new DrawResult(new Date(1486677600000L),[32,39,51,62,75]as Integer[],14,5 )
        File input = new File("src/test/resources/trTagTableRows.html")
        doc = Jsoup.parse(input,"UTF-8")
        drawResultService = new DrawResultService(drawResultRepository, prizeBreakdownService){
                @Override
                protected Document getDocument() throws IOException {
                    return doc
                }
            }
        }
    def "verify-interaction-on-save"(){
        given:

        when:
        drawResultService.getDrawResult();

        then:
        1 * drawResultRepository.save(drawResult)
        0 * drawResultRepository.save(drawResult)
    }
    def "url-parse-test"(){

        given: "some rows for test"
        Element row = doc.getElementById("trTest")
        String expectedURL = "http://www.megamillions.com/winning-numbers/2-10-2017"

        when: "parse method"
        String actualURL = drawResultService.parsesURLForPrizeBreakDown(row)

        then: "compare results"
        expectedURL == actualURL
    }

    def "convert-array-test"() {

        given: "Some numbers to test"
        def ballSet = [11,22,33,44,55]
        Integer[] expectedArray = [11,22,33,44,55]

        when: "Convert Array"
        Integer[] actualArray = drawResultService.convertArray(ballSet)

        then: "Array compare"
        actualArray == expectedArray
        actualArray.class == expectedArray.class
        actualArray.class != ballSet.class
    }

    def "get-ballNumber-list-test"(){

        given: "Some Number Rows"
        Elements numberTr = doc.getElementsByClass("number")
        def expectedResult = [32,39,51,62,75]

        when: "Getting Number List"
        def actualResult = drawResultService.getBallNumberList(numberTr)

        then: "Comparing Lists"
        actualResult == expectedResult

    }

    def "parses-drawResult-test"(){

        given:
        Element row = doc.getElementById("trTest")
        DrawResult expectedDrawResult = drawResult

        when:
        DrawResult actualDrawResult = drawResultService.parsesDrawResult(row)

        then:
        expectedDrawResult == actualDrawResult
    }

    def "get-table-rows-test"(){
        given:
        String expectedRows = "<td class=\"dates\"> 2/10/2017 </td> \n" +
                "<td class=\"number\"> 32 </td> \n" +
                "<td class=\"number\"> 39 </td> \n" +
                "<td class=\"number\"> 51 </td> \n" +
                "<td class=\"number\"> 62 </td> \n" +
                "<td class=\"number\"> 75 </td> \n" +
                "<td class=\"mega\"> 14 </td> \n" +
                "<td class=\"mega\"> 5 </td> \n" +
                "<td class=\"details\"> <a href=\"/winning-numbers/2-10-2017\">Details</a> </td>"

        when:
        Elements parsedRows = drawResultService.getTableRows()
        String actualRows = parsedRows.html()

        then:
        parsedRows != actualRows
        expectedRows == actualRows
    }

}