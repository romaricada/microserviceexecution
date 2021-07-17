/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.repository;

import mena.gov.bf.domain.EtapeTraitement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author nafolo
 */
public interface EtapeTraitementRepository extends JpaRepository<EtapeTraitement, Long>{

    List<EtapeTraitement> findAllByContratId(Long id);
    List<EtapeTraitement> findAllByEngagementId(Long id);
    List<EtapeTraitement> findAllByLiquidationId(Long id);

    Optional<EtapeTraitement> findTo1pByContratIdAndEtapeId(Long idContrat, String etatId);
    Optional<EtapeTraitement> findTo1pByEngagementIdAndEtapeId(Long idEngagement, String etatId);
    Optional<EtapeTraitement> findTo1pByLiquidationIdAndEtapeId(Long idLiquidation, String etatId);
}
