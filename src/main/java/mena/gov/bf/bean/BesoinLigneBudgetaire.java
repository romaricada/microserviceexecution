package mena.gov.bf.bean;

import java.util.ArrayList;
import java.util.List;

public class BesoinLigneBudgetaire {

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

    private List<LigneBudgetaire> ligneBudgetaires;

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

    public List<LigneBudgetaire> getLigneBudgetaires() {
        return ligneBudgetaires;
    }

    public void setLigneBudgetaires(List<LigneBudgetaire> ligneBudgetaires) {
        this.ligneBudgetaires = ligneBudgetaires;
    }
}
