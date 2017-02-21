package parserSpringData.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.mockito.Mock
import parserSpringData.entity.DrawResult
import parserSpringData.repo.PrizeBreakdownRepository
import spock.lang.Specification

/**
 * Created by amiko on 2/21/2017.
 */
class PrizeBreakdownServiceSpockTest extends Specification {

    def prizeBreakdownService
    def prizeBreakdownRepository = Mock(PrizeBreakdownRepository)
    def doc
    def url

    def setup(){

        url = new String("")
        File input = new File("src/test/resources/megaMillionsWinningNumbersPage.html")
        doc = Jsoup.parse(input,"UTF-8")
        prizeBreakdownService = new PrizeBreakdownService(prizeBreakdownRepository){
                @Override
                protected Document getDocument(String url) throws IOException {
                    return doc
                }
            }
        }


    def "get-table-for-Breakdown"(){

        given:
        String expectedTableForJackpot = "5 + 1\n" + "0\n" + '$177,000,000'

        when:
        Elements parsedTableForJackpot = prizeBreakdownService.getTableForJackpot(doc)
        String actualTableForJackpot = parsedTableForJackpot.html()

        then:
        expectedTableForJackpot == actualTableForJackpot
    }


}
