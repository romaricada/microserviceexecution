package mena.gov.bf.service.dto;
import mena.gov.bf.model.DataFile;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link mena.gov.bf.domain.StatutExecution} entity.
 */
public class StatutExecutionDTO implements Serializable {

    private Long id;

    @NotNull
    private String motif;

    private Boolean suspendu;

    private Boolean reprise;

    private Boolean contratResilier;

    private LocalDate dateSuspendu;

    private LocalDate dateReprise;

    @NotNull
    private Boolean deleted;


    private Long contratId;

    private List<DataFile> files = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Boolean isSuspendu() {
        return suspendu;
    }

    public void setSuspendu(Boolean suspendu) {
        this.suspendu = suspendu;
    }

    public Boolean isReprise() {
        return reprise;
    }

    public void setReprise(Boolean reprise) {
        this.reprise = reprise;
    }

    public LocalDate getDateSuspendu() {
        return dateSuspendu;
    }

    public void setDateSuspendu(LocalDate dateSuspendu) {
        this.dateSuspendu = dateSuspendu;
    }

    public LocalDate getDateReprise() {
        return dateReprise;
    }

    public void setDateReprise(LocalDate dateReprise) {
        this.dateReprise = dateReprise;
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

    /**
     * Getter for property 'contratResilier'.
     *
     * @return Value for property 'contratResilier'.
     */
    public Boolean getContratResilier() {
        return contratResilier;
    }

    /**
     * Setter for property 'contratResilier'.
     *
     * @param contratResilier Value to set for property 'contratResilier'.
     */
    public void setContratResilier(Boolean contratResilier) {
        this.contratResilier = contratResilier;
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

        StatutExecutionDTO statutExecutionDTO = (StatutExecutionDTO) o;
        if (statutExecutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statutExecutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatutExecutionDTO{" +
            "id=" + getId() +
            ", motif='" + getMotif() + "'" +
            ", suspendu='" + isSuspendu() + "'" +
            ", reprise='" + isReprise() + "'" +
            ", dateSuspendu='" + getDateSuspendu() + "'" +
            ", dateReprise='" + getDateReprise() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", contrat=" + getContratId() +
            "}";
    }
}
