package mena.gov.bf.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "penalite")
public class Penalite extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Basic
    @Column(name = "motif_penalite", nullable = false)
    @NotNull
    private String motifPenalite;

    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;

    @Basic
    @Column(name = "montant_penalite", nullable = false)
    private Double montantPenalite;

    @Basic
    @Column(name = "contrat_id", nullable = false)
    private Long contratId;

    //@Basic
    @Column(name = "liquidation_id", nullable = false)
    private Long liquidationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotifPenalite() {
        return motifPenalite;
    }

    public void setMotifPenalite(String motifPenalite) {
        this.motifPenalite = motifPenalite;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    /**
     * Sets new montantPenalite.
     *
     * @param montantPenalite New value of montantPenalite.
     */
    public void setMontantPenalite(Double montantPenalite) {
        this.montantPenalite = montantPenalite;
    }

    /**
     * Gets montantPenalite.
     *
     * @return Value of montantPenalite.
     */
    public Double getMontantPenalite() {
        return montantPenalite;
    }

    @Override
    public String toString() {
        return "Penalite{" +
            "id=" + id +
            ", motifPenalite='" + motifPenalite + '\'' +
            ", deleted=" + deleted +
            ", montantPenalite=" + montantPenalite +
            ", contratId=" + contratId +
            '}';
    }

    /**
     * Gets contratId.
     *
     * @return Value of contratId.
     */
    public Long getContratId() {
        return contratId;
    }

    /**
     * Sets new contratId.
     *
     * @param contratId New value of contratId.
     */
    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    /**
     * Gets liquidationId.
     *
     * @return Value of liquidationId.
     */
    public Long getLiquidationId() {
        return liquidationId;
    }

    /**
     * Sets new liquidationId.
     *
     * @param liquidationId New value of liquidationId.
     */
    public void setLiquidationId(Long liquidationId) {
        this.liquidationId = liquidationId;
    }
}
