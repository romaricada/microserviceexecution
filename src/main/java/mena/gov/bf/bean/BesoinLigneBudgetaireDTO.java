package mena.gov.bf.bean;

import java.io.Serializable;
import java.util.Objects;

public class BesoinLigneBudgetaireDTO implements Serializable {

    private Long id;

    private Boolean deleted;

    private Long ligneBudgetId;

    private String budget;

    private String ligneCredit;

    private Boolean aecp;

    private Double montantEstime;

    private Long besoinId;

    private String besoinLibelle;

    private Long activiteId;

    private String activiteLibelle;

    private Double dotCorAE;

    private Double dotCorCP;

    private Double montant;

    private LigneBudgetaire ligneBudgetaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLigneBudgetId() {
        return ligneBudgetId;
    }

    public void setLigneBudgetId(Long ligneBudgetId) {
        this.ligneBudgetId = ligneBudgetId;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getLigneCredit() {
        return ligneCredit;
    }

    public void setLigneCredit(String ligneCredit) {
        this.ligneCredit = ligneCredit;
    }

    public Double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(Double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public Long getBesoinId() {
        return besoinId;
    }

    public void setBesoinId(Long besoinId) {
        this.besoinId = besoinId;
    }

    public String getBesoinLibelle() {
        return besoinLibelle;
    }

    public void setBesoinLibelle(String besoinLibelle) {
        this.besoinLibelle = besoinLibelle;
    }

    public Long getActiviteId() {
        return activiteId;
    }

    public void setActiviteId(Long activiteId) {
        this.activiteId = activiteId;
    }

    public String getActiviteLibelle() {
        return activiteLibelle;
    }

    public void setActiviteLibelle(String activiteLibelle) {
        this.activiteLibelle = activiteLibelle;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean isAecp() {
        return aecp;
    }

    public void setAecp(Boolean aecp) {
        this.aecp = aecp;
    }

    public Double getDotCorAE() {
        return dotCorAE;
    }

    public void setDotCorAE(Double dotCorAE) {
        this.dotCorAE = dotCorAE;
    }

    public Double getDotCorCP() {
        return dotCorCP;
    }

    public void setDotCorCP(Double dotCorCP) {
        this.dotCorCP = dotCorCP;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LigneBudgetaire getLigneBudgetaire() {
        return ligneBudgetaire;
    }

    public void setLigneBudgetaire(LigneBudgetaire ligneBudgetaire) {
        this.ligneBudgetaire = ligneBudgetaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BesoinLigneBudgetaireDTO besoinLigneBudgetaireDTO = (BesoinLigneBudgetaireDTO) o;
        if (besoinLigneBudgetaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), besoinLigneBudgetaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BesoinLigneBudgetaireDTO{" +
            "id=" + id +
            ", deleted=" + deleted +
            ", ligneBudgetId=" + ligneBudgetId +
            ", budget='" + budget + '\'' +
            ", ligneCredit='" + ligneCredit + '\'' +
            ", aecp=" + aecp +
            ", montantEstime=" + montantEstime +
            ", besoinId=" + besoinId +
            ", besoinLibelle='" + besoinLibelle + '\'' +
            ", activiteId=" + activiteId +
            ", activiteLibelle='" + activiteLibelle + '\'' +
            ", dotCorAE=" + dotCorAE +
            ", dotCorCP=" + dotCorCP +
            ", montant=" + montant +
            ", ligneBudgetaire=" + ligneBudgetaire +
            '}';
    }
}
