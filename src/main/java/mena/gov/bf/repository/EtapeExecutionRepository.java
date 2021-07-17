package mena.gov.bf.repository;
import mena.gov.bf.domain.EtapeExecution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the EtapeExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeExecutionRepository extends JpaRepository<EtapeExecution, Long> {

    List<EtapeExecution> findAllByDeletedIsFalse();
}
