package mena.gov.bf.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mena.gov.bf.domain.EtapeExecution} entity.
 */
public class EtapeExecutionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private Boolean deleted;


    private Long contratId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EtapeExecutionDTO etapeExecutionDTO = (EtapeExecutionDTO) o;
        if (etapeExecutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapeExecutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapeExecutionDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", contrat=" + getContratId() +
            "}";
    }
}
