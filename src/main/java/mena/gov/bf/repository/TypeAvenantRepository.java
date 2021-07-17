package mena.gov.bf.repository;
import mena.gov.bf.domain.TypeAvenant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeAvenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAvenantRepository extends JpaRepository<TypeAvenant, Long> {

}
