package mena.gov.bf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 * Entite de gestion des liquidation.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "liquidation")
public class Liquidation extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Basic
    @Column(name = "montant", nullable = false)
    @NotNull
    private Double montant;

    @Basic
    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "date_visa_controle", nullable = false)
    private LocalDate dateVisaControle;

    @Column(name = "date_visa_ordonateur", nullable = false)
    private LocalDate dateVisaOrdonateur;

    @Column(name = "date_visa_liquidation", nullable = false)
    private LocalDate dateDeLiquidation;

    @Column(name = "avis_dac_id", nullable = false)
    private Long avisdacId;

    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;

    @Basic
    @Column(name = "support", nullable = false)
    private String support;

    @Basic
    @Column(name = "reference_paiement", nullable = false)
    private String referencePaiement;

    @Basic
    @Column(name = "word_flow", nullable = false)
    private String wordFlow;

    @ManyToOne
    private Contrat contrat;

    @ManyToOne
    private Engagement engagement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getReferencePaiement() {
        return referencePaiement;
    }

    public void setReferencePaiement(String referencePaiement) {
        this.referencePaiement = referencePaiement;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    public Long getAvisdacId() { return avisdacId; }

    public void setAvisdacId(Long avisdacId) {
        this.avisdacId = avisdacId;
    }

    @Override
    public String toString() {
        return "Liquidation{" +
            "id=" + id +
            ", montant=" + montant +
            ", datePaiement=" + datePaiement +
            ", avisdacId=" + avisdacId +
            ", deleted=" + deleted +
            ", support='" + support + '\'' +
            ", referencePaiement='" + referencePaiement + '\'' +
            ", contrat=" + contrat +
            ", engagement=" + engagement +
            '}';
    }

    /**
     * Sets new datePaiement.
     *
     * @param datePaiement New value of datePaiement.
     */
    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    /**
     * Gets datePaiement.
     *
     * @return Value of datePaiement.
     */
    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    /**
     * Sets new wordFlow.
     *
     * @param wordFlow New value of wordFlow.
     */
    public void setWordFlow(String wordFlow) {
        this.wordFlow = wordFlow;
    }

    /**
     * Gets wordFlow.
     *
     * @return Value of wordFlow.
     */
    public String getWordFlow() {
        return wordFlow;
    }

    /**
     * Sets new dateVisaControle.
     *
     * @param dateVisaControle New value of dateVisaControle.
     */
    public void setDateVisaControle(LocalDate dateVisaControle) {
        this.dateVisaControle = dateVisaControle;
    }

    /**
     * Gets dateVisaControle.
     *
     * @return Value of dateVisaControle.
     */
    public LocalDate getDateVisaControle() {
        return dateVisaControle;
    }

    /**
     * Gets dateDeLiquidation.
     *
     * @return Value of dateDeLiquidation.
     */
    public LocalDate getDateDeLiquidation() {
        return dateDeLiquidation;
    }

    /**
     * Sets new dateDeLiquidation.
     *
     * @param dateDeLiquidation New value of dateDeLiquidation.
     */
    public void setDateDeLiquidation(LocalDate dateDeLiquidation) {
        this.dateDeLiquidation = dateDeLiquidation;
    }

    /**
     * Gets dateVisaOrdonateur.
     *
     * @return Value of dateVisaOrdonateur.
     */
    public LocalDate getDateVisaOrdonateur() {
        return dateVisaOrdonateur;
    }

    /**
     * Sets new dateVisaOrdonateur.
     *
     * @param dateVisaOrdonateur New value of dateVisaOrdonateur.
     */
    public void setDateVisaOrdonateur(LocalDate dateVisaOrdonateur) {
        this.dateVisaOrdonateur = dateVisaOrdonateur;
    }
}
