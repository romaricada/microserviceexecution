package mena.gov.bf.repository;
import mena.gov.bf.domain.Contrat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Contrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    //Contrat findContratByCandidatLotIdAndDeletedIsFalse(Long id);
    List<Contrat> findAllByDeletedIsFalse();
    Contrat findContratByIdAndDeletedIsFalse(Long id);
    List<Contrat> findAllByDeletedIsNotNullAndDeletedIsFalse();
}
