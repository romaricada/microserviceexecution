package mena.gov.bf.repository;
import mena.gov.bf.domain.DecisionContentieux;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DecisionContentieux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionContentieuxRepository extends JpaRepository<DecisionContentieux, Long> {

}
