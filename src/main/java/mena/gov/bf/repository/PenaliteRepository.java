package mena.gov.bf.repository;
import mena.gov.bf.domain.Penalite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Penalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Long> {

}
