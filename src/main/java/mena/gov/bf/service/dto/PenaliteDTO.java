package mena.gov.bf.service.dto;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.model.DataFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link mena.gov.bf.domain.Penalite} entity.
 */
public class PenaliteDTO implements Serializable {

    private Long id;

    @NotNull
    private String motifPenalite;

    @NotNull
    private Boolean deleted;

    private Double montantPenalite;

    private Long contratId;

    private Long liquidationId;

    private Contrat contrat;

    private List<DataFile> files = new ArrayList<>();

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

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PenaliteDTO penaliteDTO = (PenaliteDTO) o;
        if (penaliteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), penaliteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PenaliteDTO{" +
            "id=" + id +
            ", motifPenalite='" + motifPenalite + '\'' +
            ", deleted=" + deleted +
            ", montantPenalite=" + montantPenalite +
            ", contratId=" + contratId +
            ", contrat=" + contrat +
            ", files=" + files +
            '}';
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

    /**
     * Gets contrat.
     *
     * @return Value of contrat.
     */
    public Contrat getContrat() {
        return contrat;
    }

    /**
     * Sets new contrat.
     *
     * @param contrat New value of contrat.
     */
    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
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
