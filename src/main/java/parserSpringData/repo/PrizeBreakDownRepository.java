package parserSpringData.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import parserSpringData.entity.PrizeBreakDown;


/**
 * Created by amiko on 29-Dec-16.
 */
public interface PrizeBreakDownRepository extends JpaRepository<PrizeBreakDown, Long> {


}
