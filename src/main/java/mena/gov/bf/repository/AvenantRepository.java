package mena.gov.bf.repository;
import mena.gov.bf.domain.Avenant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Avenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvenantRepository extends JpaRepository<Avenant, Long> {

}
