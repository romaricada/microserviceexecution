package mena.gov.bf.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mena.gov.bf.domain.Contrat;
import mena.gov.bf.domain.enumeration.TypeOs;
import mena.gov.bf.domain.enumeration.EtatOs;
import mena.gov.bf.model.DataFile;

/**
 * A DTO for the {@link mena.gov.bf.domain.OrdreService} entity.
 */
public class OrdreServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private TypeOs type;

    @NotNull
    private EtatOs etat;

    
    private LocalDate dateDemarrage;

    private LocalDate dateFin;


    private Integer delai;

    private Long NumeroOs;
    private Long numeroOs1;

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }



    private String libelle;

    private Long contratId;

    private ContratDTO contrat = new ContratDTO();

    private List<DataFile> files = new ArrayList<>();

    @NotNull
    private Boolean deleted;

    public Long getNumeroOs1() {
        return numeroOs1;
    }

    public void setNumeroOs1(Long numeroOs1) {
        this.numeroOs1 = numeroOs1;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOs getType() {
        return type;
    }


    public Long getNumeroOs() {
        return NumeroOs;
    }

    public void setNumeroOs(Long numeroOs) {
        NumeroOs = numeroOs;
    }

    public void setType(TypeOs type) {
        this.type = type;
    }

    public EtatOs getEtat() {
        return etat;
    }

    public LocalDate getDateDemarrage() {
        return dateDemarrage;
    }

    public void setDateDemarrage(LocalDate dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
    }

    public void setEtat(EtatOs etat) {
        this.etat = etat;
    }

   /* public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
*/
    public Integer getDelai() {
        return delai;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdreServiceDTO ordreServiceDTO = (OrdreServiceDTO) o;
        if (ordreServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordreServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "OrdreServiceDTO{" +
            "id=" + id +
            ", type=" + type +
            ", etat=" + etat +
            ", dateDemarrage=" + dateDemarrage +
            ", dateFin=" + dateFin +
            ", delai=" + delai +
            ", NumeroOs=" + NumeroOs +
            ", libelle='" + libelle + '\'' +
            ", contratId=" + contratId +
            ", contrat=" + contrat +
            ", files=" + files +
            ", deleted=" + deleted +
            '}';
    }

}
