package mena.gov.bf.repository;
import mena.gov.bf.domain.Liquidation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Liquidation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiquidationRepository extends JpaRepository<Liquidation, Long> {
List<Liquidation> findByEngagementId(Long id);
}
