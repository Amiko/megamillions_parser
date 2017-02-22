package parserSpringData.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.junit.Ignore
import parserSpringData.entity.DrawResult
import parserSpringData.entity.PrizeBreakdown
import parserSpringData.repo.PrizeBreakdownRepository
import spock.lang.Specification


/**
 * Created by amiko on 2/21/2017.
 */
class PrizeBreakdownServiceSpockTest extends Specification {

    def prizeBreakdownService
    def prizeBreakdownRepository  = Mock(PrizeBreakdownRepository)
    def doc
    def url
    def drawResult
    def jackpotPrizeBreakdown
    def prizeBreakdown

    def setup(){

        url = new String("")
        File input = new File("src/test/resources/megaMillionsWinningNumbersPage.html")
        doc = Jsoup.parse(input,"UTF-8")

        drawResult = new DrawResult(new Date(1485216000000L), [8,42,54,63,67]as Integer[],11,4)
        jackpotPrizeBreakdown = new PrizeBreakdown("5 + 1",0,177000000L,0, 0L,drawResult)
        prizeBreakdown = [] as ArrayList
        prizeBreakdown << new PrizeBreakdown("5 + 0",2,1000000L,0,4000000L,drawResult)
        prizeBreakdown << new PrizeBreakdown("4 + 1",29,5000L,3,20000L,drawResult)
        prizeBreakdown << new PrizeBreakdown("4 + 0",412,500L,58,2000L,drawResult)
        prizeBreakdown << new PrizeBreakdown("3 + 0",27852,5L,3046,20L,drawResult)
        prizeBreakdown << new PrizeBreakdown("2 + 1",46742,5L,5077,20L,drawResult)
        prizeBreakdown << new PrizeBreakdown("3 + 1",2002,50L,229,200L,drawResult)
        prizeBreakdown << new PrizeBreakdown("1 + 1",413861,2L,46158,8L,drawResult)
        prizeBreakdown << new PrizeBreakdown("0 + 1",1132284,1L,128342,4L,drawResult)

        prizeBreakdownService = new PrizeBreakdownService(prizeBreakdownRepository){
                @Override
                protected Document getDocument(String url) throws IOException {
                    return doc
                }
            }
        }

    def "verify-interaction-on-save"(){
        given:

        when:
        prizeBreakdownService.getPrizeBreakdown(url,drawResult)

        then:
        1 * prizeBreakdownRepository.save(jackpotPrizeBreakdown)
        //TODO: Add save on prizeBreakdown

    }
    def "get-table-for-jackpot-test"(){
        given:
        String expectedTableForJackpot = "5 + 1\n" + "0\n" + '$177,000,000'

        when:
        Elements parsedTableForJackpot = prizeBreakdownService.getTableForJackpot(doc)
        String actualTableForJackpot = parsedTableForJackpot.html()

        then:
        expectedTableForJackpot == actualTableForJackpot
    }


    def "get-jackpot-PrizeBreakdown-test"(){
        given:
        PrizeBreakdown expectedPrizeBreakdown = jackpotPrizeBreakdown

        when:
        PrizeBreakdown actualPrizeBreakdown = prizeBreakdownService.getJackpotPrizeBreakdown(doc,drawResult)

        then:
        expectedPrizeBreakdown == actualPrizeBreakdown
    }

    def "get-PrizeBreakdown-results-test"(){
        given:
        List<PrizeBreakdown> expectedPrizeBreakdownResult = prizeBreakdown

        when:
        List<PrizeBreakdown> actualPrizeBreakdownResult = prizeBreakdownService.getPrizeBreakdownResults(doc,drawResult)

        then:
        expectedPrizeBreakdownResult == actualPrizeBreakdownResult

    }
}
