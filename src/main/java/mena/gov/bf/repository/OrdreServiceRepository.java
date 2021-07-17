package mena.gov.bf.repository;
import mena.gov.bf.domain.OrdreService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrdreService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdreServiceRepository extends JpaRepository<OrdreService, Long> {
    OrdreService findOrdreServiceByContratAndDeletedIsFalse(Long id);

}
