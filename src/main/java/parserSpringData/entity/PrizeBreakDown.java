package parserSpringData.entity;

import javax.persistence.*;


/**
 * Created by amiko on 28-Dec-16.
 */
@Entity
@Table(name= "prize_break_down")
public class PrizeBreakDown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="match")
    private String match;
    @Column(name ="winner")
    private Integer winner;
    @Column(name ="prize_amount")
    private Long prizeAmount;
    @Column(name ="megaplier_winners")
    private Integer megaplierWinners;
    @Column(name ="megaplier_amount")
    private Long megaplierAmount;

    @ManyToOne
    @JoinColumn(name = "drawresult_id", referencedColumnName = "drawresult_id")
    private DrawResult drawResult;

    public PrizeBreakDown() {
    }

    public PrizeBreakDown( String match, Integer winner, Long prizeAmount, Integer megaplierWinners, Long megaplierAmount, DrawResult drawResult) {
        this.match = match;
        this.winner = winner;
        this.prizeAmount = prizeAmount;
        this.megaplierWinners = megaplierWinners;
        this.megaplierAmount = megaplierAmount;
        this.drawResult = drawResult;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Long getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(Long prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public Integer getMegaplierWinners() {
        return megaplierWinners;
    }

    public void setMegaplierWinners(Integer megaplierWinners) {
        this.megaplierWinners = megaplierWinners;
    }

    public Long getMegaplierAmount() {
        return megaplierAmount;
    }

    public void setMegaplierAmount(Long megaplierAmount) {
        this.megaplierAmount = megaplierAmount;
    }

    @Column
    public DrawResult getDrawResult() {
        return drawResult;
    }

    public void setDrawResult(DrawResult drawResult) {
        this.drawResult = drawResult;
    }
}
