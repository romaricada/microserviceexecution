/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.domain.Etape;

/**
 *
 * @author nafolo
 */
public class EtapeTraitementDTO implements Serializable {
    private Long id;
    @NotNull
    private Boolean actif;
    @NotNull
    private LocalDate date;
    @NotNull
    private Boolean valide;
    
    private ContratDTO contrat;
    
    private Long contratId;
    
    private Etape etape;
    
    private Long etapeId;

    private EngagementDTO engagement;

    private Long engagementId;

    private LiquidationDTO liquidation;

    private Long  liquidationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public Long getEtapeId() {
        return etapeId;
    }

    public void setEtapeId(Long etapeId) {
        this.etapeId = etapeId;
    }

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    public EngagementDTO getEngagement() {
        return engagement;
    }

    public void setEngagement(EngagementDTO engagement) {
        this.engagement = engagement;
    }

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    public LiquidationDTO getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(LiquidationDTO liquidation) {
        this.liquidation = liquidation;
    }

    public Long getLiquidationId() {
        return liquidationId;
    }

    public void setLiquidationId(Long liquidationId) {
        this.liquidationId = liquidationId;
    }
}
