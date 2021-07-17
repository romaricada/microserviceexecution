package mena.gov.bf.domain;

import mena.gov.bf.domain.enumeration.TypeIncidentExecution;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entite de gestion des contentieux.
 * @author TRAORE BRAHIMA
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "contentieux")
public class Contentieux extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false)
    private String reference;
    @Basic
    @Column(name = "motif", nullable = false)
    private String motif;
    @Basic
    @Column(name = "montant", nullable = false)
    private Double montant;
   @Basic
    @Column(name = "temps", nullable = false)
    private String temps;
    @Basic
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;
    @ManyToOne
    private Contrat contrat;

    private TypeIncidentExecution typeIncidentExecution;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DecisionContentieux decisionContentieux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public DecisionContentieux getDecisionContentieux() {
        return decisionContentieux;
    }

    public void setDecisionContentieux(DecisionContentieux decisionContentieux) {
        this.decisionContentieux = decisionContentieux;
    }

    /**
     * Gets typeIncidentExecution.
     *
     * @return Value of typeIncidentExecution.
     */
    public TypeIncidentExecution getTypeIncidentExecution() {
        return typeIncidentExecution;
    }

    /**
     * Sets new typeIncidentExecution.
     *
     * @param typeIncidentExecution New value of typeIncidentExecution.
     */
    public void setTypeIncidentExecution(TypeIncidentExecution typeIncidentExecution) {
        this.typeIncidentExecution = typeIncidentExecution;
    }

    /**
     * Gets montant.
     *
     * @return Value of montant.
     */
    public Double getMontant() {
        return montant;
    }

    /**
     * Sets new temps.
     *
     * @param temps New value of temps.
     */
    public void setTemps(String temps) {
        this.temps = temps;
    }

    /**
     * Gets temps.
     *
     * @return Value of temps.
     */
    public String getTemps() {
        return temps;
    }

    /**
     * Sets new motif.
     *
     * @param motif New value of motif.
     */
    public void setMotif(String motif) {
        this.motif = motif;
    }

    /**
     * Sets new date.
     *
     * @param date New value of date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets reference.
     *
     * @return Value of reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Gets date.
     *
     * @return Value of date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets new reference.
     *
     * @param reference New value of reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets motif.
     *
     * @return Value of motif.
     */
    public String getMotif() {
        return motif;
    }

    /**
     * Sets new montant.
     *
     * @param montant New value of montant.
     */
    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
