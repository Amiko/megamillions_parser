package parserSpringData.entity;

import javax.persistence.*;


/**
 * Created by amiko on 28-Dec-16.
 */
@Entity
@Table(name= "prize_break_down")
public class PrizeBreakdown {

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

    public PrizeBreakdown() {
    }

    public PrizeBreakdown(String match, Integer winner, Long prizeAmount, Integer megaplierWinners, Long megaplierAmount, DrawResult drawResult) {
        this.match = match;
        this.winner = winner;
        this.prizeAmount = prizeAmount;
        this.megaplierWinners = megaplierWinners;
        this.megaplierAmount = megaplierAmount;
        this.drawResult = drawResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrizeBreakdown that = (PrizeBreakdown) o;

        if (match != null ? !match.equals(that.match) : that.match != null) return false;
        if (winner != null ? !winner.equals(that.winner) : that.winner != null) return false;
        if (prizeAmount != null ? !prizeAmount.equals(that.prizeAmount) : that.prizeAmount != null) return false;
        if (megaplierWinners != null ? !megaplierWinners.equals(that.megaplierWinners) : that.megaplierWinners != null)
            return false;
        if (megaplierAmount != null ? !megaplierAmount.equals(that.megaplierAmount) : that.megaplierAmount != null)
            return false;
        return drawResult != null ? drawResult.equals(that.drawResult) : that.drawResult == null;
    }

    @Override
    public int hashCode() {
        int result = match != null ? match.hashCode() : 0;
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        result = 31 * result + (prizeAmount != null ? prizeAmount.hashCode() : 0);
        result = 31 * result + (megaplierWinners != null ? megaplierWinners.hashCode() : 0);
        result = 31 * result + (megaplierAmount != null ? megaplierAmount.hashCode() : 0);
        result = 31 * result + (drawResult != null ? drawResult.hashCode() : 0);
        return result;
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

    public DrawResult getDrawResult() {
        return drawResult;
    }

    public void setDrawResult(DrawResult drawResult) {
        this.drawResult = drawResult;
    }
}
