package mena.gov.bf.repository;
import mena.gov.bf.domain.StatutExecution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StatutExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutExecutionRepository extends JpaRepository<StatutExecution, Long> {

}
