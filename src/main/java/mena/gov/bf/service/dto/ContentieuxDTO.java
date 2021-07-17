package mena.gov.bf.service.dto;
import mena.gov.bf.domain.enumeration.TypeIncidentExecution;
import mena.gov.bf.model.DataFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * A DTO for the {@link mena.gov.bf.domain.Contentieux} entity.
 */
public class ContentieuxDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean deleted;

    private String reference;

    private String motif;

    private Double montant;

    private String temps;

    private LocalDate date;

    private Long contratId;

    private Long decisionContentieuxId;

    private List<DataFile> files = new ArrayList<>();

    private TypeIncidentExecution typeIncidentExecution;

    private Set<DecisionContentieuxDTO> decisionContentieuxes = new HashSet<>();
    private DecisionContentieuxDTO decisionContentieux = new DecisionContentieuxDTO();

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

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Long getDecisionContentieuxId() {
        return decisionContentieuxId;
    }

    public void setDecisionContentieuxId(Long decisionContentieuxId) {
        this.decisionContentieuxId = decisionContentieuxId;
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

        ContentieuxDTO contentieuxDTO = (ContentieuxDTO) o;
        if (contentieuxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentieuxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentieuxDTO{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", contrat=" + getContratId() +
            ", decisionContentieux=" + getDecisionContentieuxId() +
            "}";
    }

    /**
     * Gets decisionContentieuxes.
     *
     * @return Value of decisionContentieuxes.
     */
    public Set<DecisionContentieuxDTO> getDecisionContentieuxes() {
        return decisionContentieuxes;
    }

    /**
     * Sets new decisionContentieuxes.
     *
     * @param decisionContentieuxes New value of decisionContentieuxes.
     */
    public void setDecisionContentieuxes(Set<DecisionContentieuxDTO> decisionContentieuxes) {
        this.decisionContentieuxes = decisionContentieuxes;
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
     * Gets temps.
     *
     * @return Value of temps.
     */
    public String getTemps() {
        return temps;
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
     * Sets new reference.
     *
     * @param reference New value of reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
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
     * Gets reference.
     *
     * @return Value of reference.
     */
    public String getReference() {
        return reference;
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
     * Sets new montant.
     *
     * @param montant New value of montant.
     */
    public void setMontant(Double montant) {
        this.montant = montant;
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
     * Gets motif.
     *
     * @return Value of motif.
     */
    public String getMotif() {
        return motif;
    }
}
