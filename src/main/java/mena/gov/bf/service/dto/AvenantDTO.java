package mena.gov.bf.service.dto;
import mena.gov.bf.model.DataFile;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link mena.gov.bf.domain.Avenant} entity.
 */
public class AvenantDTO implements Serializable {

    private Long id;

    @NotNull
    private String reference;

    @NotNull
    private String motif;

    @NotNull
    private Double montant;

    @NotNull
    private String temps;

    @NotNull
    private LocalDate date;

    @NotNull
    private Boolean deleted;


    private Long contratId;

    private Long typeAvenantId;

    private TypeAvenantDTO typeAvenant;

    private List<DataFile> files = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Long getTypeAvenantId() {
        return typeAvenantId;
    }

    public void setTypeAvenantId(Long typeAvenantId) {
        this.typeAvenantId = typeAvenantId;
    }

    public TypeAvenantDTO getTypeAvenant() {
        return typeAvenant;
    }

    public void setTypeAvenant(TypeAvenantDTO typeAvenant) {
        this.typeAvenant = typeAvenant;
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

        AvenantDTO avenantDTO = (AvenantDTO) o;
        if (avenantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avenantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvenantDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", motif='" + getMotif() + "'" +
            ", montant=" + getMontant() +
            ", temps='" + getTemps() + "'" +
            ", date='" + getDate() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", contrat=" + getContratId() +
            ", typeAvenant=" + getTypeAvenantId() +
            "}";
    }
}
