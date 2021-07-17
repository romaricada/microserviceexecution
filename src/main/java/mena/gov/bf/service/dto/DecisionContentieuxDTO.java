package mena.gov.bf.service.dto;
import mena.gov.bf.model.DataFile;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link mena.gov.bf.domain.DecisionContentieux} entity.
 */
public class DecisionContentieuxDTO implements Serializable {

    private Long id;

    @NotNull
    private String decision;

    @NotNull
    private LocalDate date;

    @NotNull
    private String structure;

    @NotNull
    private String referenceDecision;

    @NotNull
    private Boolean deleted;

    private Long contentieuxId;

    private List<DataFile> files = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getReferenceDecision() {
        return referenceDecision;
    }

    public void setReferenceDecision(String referenceDecision) {
        this.referenceDecision = referenceDecision;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

        DecisionContentieuxDTO decisionContentieuxDTO = (DecisionContentieuxDTO) o;
        if (decisionContentieuxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), decisionContentieuxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DecisionContentieuxDTO{" +
            "id=" + getId() +
            ", decision='" + getDecision() + "'" +
            ", date='" + getDate() + "'" +
            ", structure='" + getStructure() + "'" +
            ", referenceDecision='" + getReferenceDecision() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }

    /**
     * Gets contentieuxId.
     *
     * @return Value of contentieuxId.
     */
    public Long getContentieuxId() {
        return contentieuxId;
    }

    /**
     * Sets new contentieuxId.
     *
     * @param contentieuxId New value of contentieuxId.
     */
    public void setContentieuxId(Long contentieuxId) {
        this.contentieuxId = contentieuxId;
    }
}
