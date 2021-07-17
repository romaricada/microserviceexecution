package mena.gov.bf.repository;
import mena.gov.bf.domain.Contentieux;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Contentieux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentieuxRepository extends JpaRepository<Contentieux, Long> {

}
