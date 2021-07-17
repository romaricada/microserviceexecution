package mena.gov.bf.service.dto;

import mena.gov.bf.domain.Avenant;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.domain.enumeration.TypeOrdreCommande;

import java.io.Serializable;

public class OrdreCommandeDTO implements Serializable {
    private Long id;

    private String reference;

    private String dateSignature;

    private String dateDebutPrevu;

    private String DateFinPrevu;

    private Boolean resilier;

    private Boolean deleted;

    private TypeOrdreCommande typeOrdreCommande;

    private Long contratId;

    private Contrat contrat;

    private Long avenantId;

    private Avenant avenant;

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

    public String getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(String dateSignature) {
        this.dateSignature = dateSignature;
    }

    public String getDateDebutPrevu() {
        return dateDebutPrevu;
    }

    public void setDateDebutPrevu(String dateDebutPrevu) {
        this.dateDebutPrevu = dateDebutPrevu;
    }

    public String getDateFinPrevu() {
        return DateFinPrevu;
    }

    public void setDateFinPrevu(String dateFinPrevu) {
        DateFinPrevu = dateFinPrevu;
    }

    public Boolean getResilier() {
        return resilier;
    }

    public void setResilier(Boolean resilier) {
        this.resilier = resilier;
    }

    public TypeOrdreCommande getTypeOrdreCommande() {
        return typeOrdreCommande;
    }

    public void setTypeOrdreCommande(TypeOrdreCommande typeOrdreCommande) {
        this.typeOrdreCommande = typeOrdreCommande;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Long getAvenantId() {
        return avenantId;
    }

    public void setAvenantId(Long avenantId) {
        this.avenantId = avenantId;
    }

    public Avenant getAvenant() {
        return avenant;
    }

    public void setAvenant(Avenant avenant) {
        this.avenant = avenant;
    }

    /**
     * Gets deleted.
     *
     * @return Value of deleted.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets new deleted.
     *
     * @param deleted New value of deleted.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
