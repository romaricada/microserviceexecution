package mena.gov.bf.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import mena.gov.bf.bean.ActiviteDTO;
/**
 * Entite de gestion des engagements.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "engagement")
public class Engagement extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   // @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    private String reference;
    @Basic
    @Column(name = "montant_engage", nullable = false)
    @NotNull
    private Double montantEngage;
    @Basic
    @Column(name = "date_engagement", nullable = false)
    @NotNull
    private LocalDate date;
    @Basic
    @Column(name = "lot_id")
    private Long lotId;
    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;
    @ManyToOne
    private LigneBudgetaireContrat ligneBudgetaireContrat;
    private  Long contratId;
    @Basic
    @Column(name = "avis_dac_id")
    private Long avisDacId;

    @Column(name = "ligne_budgetaire_id")
    private Long ligneBudgetaireId;

    @Basic
    private String wordFlow;

    @Basic
    private String intitule;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getLigneBudgetaireId() {
        return ligneBudgetaireId;
    }

    public void setLigneBudgetaireId(Long ligneBudgetaireId) {
        this.ligneBudgetaireId = ligneBudgetaireId;
    }

    public Long getAvisDacId() {
        return avisDacId;
    }

    public void setAvisDacId(Long avisDacId) {
        this.avisDacId = avisDacId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontantEngage() {
        return montantEngage;
    }

    public void setMontantEngage(Double montantEngage) {
        this.montantEngage = montantEngage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LigneBudgetaireContrat getLigneBudgetaireContrat() {
        return ligneBudgetaireContrat;
    }

    public void setLigneBudgetaireContrat(LigneBudgetaireContrat ligneBudgetaireContrat) {
        this.ligneBudgetaireContrat = ligneBudgetaireContrat;
    }

    public String getWordFlow() {
        return wordFlow;
    }

    public void setWordFlow(String wordFlow) {
        this.wordFlow = wordFlow;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    @Override
    public String toString() {
        return "Engagement{" +
            "id=" + id +
            ", montantEngage=" + montantEngage +
            ", date=" + date +
            ", lotId=" + lotId +
            ", deleted=" + deleted +
            ", ligneBudgetaireContrat=" + ligneBudgetaireContrat +
            ", avisDacId=" + avisDacId +
            ", ligneBudgetaireId=" + ligneBudgetaireId +
            '}';
    }
}
