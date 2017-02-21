package parserSpringData.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.mockito.Mock
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
    def setup(){
        File input = new File("src/test/resources/trTagTableRows.html")
        doc = Jsoup.parse(input,"UTF-8")
        drawResultService = new DrawResultService(drawResultRepository, prizeBreakdownService){
                @Override
                protected Document getDocument() throws IOException {
                    return doc
                }
            }
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

    def "url-parse-test"(){

        given: "some rows for test"
        Element row = doc.getElementById("trTest")
        String expectedURL = "http://www.megamillions.com/winning-numbers/2-10-2017"

        when: "parse method"
        String actualURL = drawResultService.parsesURLForPrizeBreakDown(row)

        then: "compare results"
        expectedURL == actualURL
    }

    

}