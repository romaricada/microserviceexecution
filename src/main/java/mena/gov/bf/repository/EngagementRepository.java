package mena.gov.bf.repository;

import mena.gov.bf.domain.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Engagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findByContratId(Long id);
}
