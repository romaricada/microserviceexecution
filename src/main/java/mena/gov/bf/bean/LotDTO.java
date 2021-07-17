package mena.gov.bf.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.util.Objects;

public class LotDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String description;

    private Long activiteId;

    private Long cautionId;

    public Long getCautionId() {
        return cautionId;
    }

    public void setCautionId(Long cautionId) {
        this.cautionId = cautionId;
    }


    private Boolean deleted;

    private Boolean dossierPaye;



    @NotNull
    private Boolean infructueux;



    public Long getId() {
        return id;
    }

    public Boolean getDossierPaye() {
        return dossierPaye;
    }

    public void setDossierPaye(Boolean dossierPaye) {
        this.dossierPaye = dossierPaye;
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

    public Long getActiviteId() {
        return activiteId;
    }

    public void setActiviteId(Long activiteId) {
        this.activiteId = activiteId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isInfructueux() {
        return infructueux;
    }

    public void setInfructueux(Boolean infructueux) {
        this.infructueux = infructueux;
    }

    /**
     * Getter for property 'candidatLot'.
     *
     * @return Value for property 'candidatLot'.
     */


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LotDTO lotDTO = (LotDTO) o;
        if (lotDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lotDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LotDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", activiteId=" + getActiviteId() +
            ", deleted='" + isDeleted() + "'" +
            ", infructueux='" + isInfructueux() + "'" +
            "}";
    }
}
