package parserSpringData.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amiko on 28-Dec-16.
 */
@Entity
@Table(name = "draw_result")
public class DrawResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drawresult_id")
    private Long id;

    @Column(name = "draw_dates")
    private Date drawDates;

    @Column(name = "balls")
    @Type(type = "parserSpringData.configFiles.GenericArrayUserType")
    private Integer[] balls;

    @Column(name = "megaball")
    private Integer megaBall;

    @Column(name = "megaplier")
    private int megaPlier;

    @OneToMany(mappedBy = "drawResult")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<PrizeBreakdown> prizeBreakdown = new ArrayList<>();


    public DrawResult(Date drawDates, Integer[] balls, Integer megaBall, int megaPlier) {
        this.drawDates = drawDates;
        this.balls = balls;
        this.megaBall = megaBall;
        this.megaPlier = megaPlier;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDrawDates() {
        return drawDates;
    }

    public void setDrawDates(Date drawDates) {
        this.drawDates = drawDates;
    }

    public Integer[] getBalls() {
        return balls;
    }

    public void setBalls(Integer[] balls) {
        this.balls = balls;
    }

    public Integer getMegaBall() {
        return megaBall;
    }

    public void setMegaBall(Integer megaBall) {
        this.megaBall = megaBall;
    }

    public int getMegaPlier() {
        return megaPlier;
    }

    public void setMegaPlier(int megaPlier) {
        this.megaPlier = megaPlier;
    }

    public List<PrizeBreakdown> getPrizeBreakdown() {
        return prizeBreakdown;
    }

    public void setPrizeBreakdown(List<PrizeBreakdown> prizeBreakdown) {
        this.prizeBreakdown = prizeBreakdown;
    }
}
