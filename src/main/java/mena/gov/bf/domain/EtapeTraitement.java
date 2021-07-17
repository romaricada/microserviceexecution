/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mena.gov.bf.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author nafolo
 */
@Entity
public class EtapeTraitement extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic
     @NotNull
    private Boolean actif;
    @Basic
     @NotNull
    private LocalDate date;
    @Basic
     @NotNull
    private Boolean valide;
   
  
    @ManyToOne(fetch = FetchType.LAZY)
    private Contrat contrat;
    
  
    @ManyToOne(fetch = FetchType.LAZY)
    private Etape etape;


    @ManyToOne(fetch = FetchType.LAZY)
    private Engagement engagement;

    @ManyToOne(fetch = FetchType.LAZY)
    private Liquidation liquidation;

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

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    public Liquidation getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(Liquidation liquidation) {
        this.liquidation = liquidation;
    }

    @Override
    public String toString() {
        return "EtapeTraitement{" + "id=" + id + ", actif=" + actif + ", date=" + date + ", valide=" + valide + ", contrat=" + contrat + ", etape=" + etape + '}';
    }
            
}
