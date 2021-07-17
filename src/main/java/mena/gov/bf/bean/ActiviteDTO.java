package mena.gov.bf.bean;

import java.io.Serializable;
import java.util.*;

public class ActiviteDTO implements Serializable {

    private Long id;

    private String codeLignePlan;

    private String gestionnaireCredit;

    private Boolean deleted;

    private Boolean reported;

    private Long passationId;

    private String modePassationLibelle;

    private Long naturePrestationId;

    private String naturePrestationLibelle;



    private String nomActivite;

    private Double etatAvancement;

    /**
     * Getter for property 'reported'.
     *
     * @return Value for property 'reported'.
     */
    public Boolean getReported() {
        return reported;
    }

    /**
     * Setter for property 'reported'.
     *
     * @param reported Value to set for property 'reported'.
     */
    public void setReported(Boolean reported) {
        this.reported = reported;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeLignePlan() {
        return codeLignePlan;
    }

    public void setCodeLignePlan(String codeLignePlan) {
        this.codeLignePlan = codeLignePlan;
    }

    public String getGestionnaireCredit() {
        return gestionnaireCredit;
    }

    public void setGestionnaireCredit(String gestionnaireCredit) {
        this.gestionnaireCredit = gestionnaireCredit;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getPassationId() {
        return passationId;
    }

    public void setPassationId(Long modePassationId) {
        this.passationId = modePassationId;
    }

    public Long getNaturePrestationId() {
        return naturePrestationId;
    }

    public void setNaturePrestationId(Long naturePrestationId) {
        this.naturePrestationId = naturePrestationId;
    }

    public String getModePassationLibelle() {
        return modePassationLibelle;
    }

    public void setModePassationLibelle(String modePassationLibelle) {
        this.modePassationLibelle = modePassationLibelle;
    }

    public String getNaturePrestationLibelle() {
        return naturePrestationLibelle;
    }

    public void setNaturePrestationLibelle(String naturePrestationLibelle) {
        this.naturePrestationLibelle = naturePrestationLibelle;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActiviteDTO activiteDTO = (ActiviteDTO) o;
        if (activiteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activiteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActiviteDTO{" +
            "id=" + id +
            ", codeLignePlan='" + codeLignePlan + '\'' +
            ", gestionnaireCredit='" + gestionnaireCredit + '\'' +
            ", deleted=" + deleted +
            ", reported=" + reported +
            ", passationId=" + passationId +
            ", modePassationLibelle='" + modePassationLibelle + '\'' +
            ", naturePrestationId=" + naturePrestationId +
            ", naturePrestationLibelle='" + naturePrestationLibelle + '\'' +
            ", nomActivite=" + nomActivite +
            '}';
    }

    public Double getEtatAvancement() {
        return etatAvancement;
    }

    public void setEtatAvancement(Double etatAvancement) {
        this.etatAvancement = etatAvancement;
    }
}
