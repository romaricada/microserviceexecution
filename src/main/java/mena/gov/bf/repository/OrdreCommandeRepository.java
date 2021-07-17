package mena.gov.bf.repository;

import mena.gov.bf.domain.OrdreCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdreCommandeRepository extends JpaRepository<OrdreCommande, Long> {
}
