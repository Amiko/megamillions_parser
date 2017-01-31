package parserSpringData.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import parserSpringData.entity.PrizeBreakdown;


/**
 * Created by amiko on 29-Dec-16.
 */
public interface PrizeBreakdownRepository extends JpaRepository<PrizeBreakdown, Long> {


}
