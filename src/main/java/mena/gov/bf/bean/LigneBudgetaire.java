package mena.gov.bf.bean;

import java.util.ArrayList;
import java.util.List;

public class LigneBudgetaire {
    private Long id;

    private String budget;

    private String ligneCredit;

    private String section;

    private String programme;

    private String action;

    private String chapitre;

    private String activite;

    private String article;

    private String paragraphe;

    private Double dotInitAE;

    private Double dotInitCP;

    private Double dotCorAE;

    private Double dotCorCP;

    private Double engage;
    private Double montantdisponible;
    private Double montantdejaengage;
    private Double montantEngage;

    private Double engageCF;

    private Double liquide;

    private Double ordonne;

    private Double vbp;

    private Double ecp;

    private Boolean deleted;

    private Long exerciceId;

    private Double montantEstime;

    private List<BesoinLigneBudgetaire> besoinLigneBudgetaireList = new ArrayList();

    public List<BesoinLigneBudgetaire> getBesoinLigneBudgetaireList() {
        return besoinLigneBudgetaireList;
    }

    public void setBesoinLigneBudgetaireList(List<BesoinLigneBudgetaire> besoinLigneBudgetaireList) {
        this.besoinLigneBudgetaireList = besoinLigneBudgetaireList;
    }

    public Double getMontantEngage() {
        return montantEngage;
    }

    public void setMontantEngage(Double montantEngage) {
        this.montantEngage = montantEngage;
    }

    public Double getMontantdisponible() {
        return montantdisponible;
    }

    public void setMontantdisponible(Double montantdisponible) {
        this.montantdisponible = montantdisponible;
    }

    public Double getMontantdejaengage() {
        return montantdejaengage;
    }

    public void setMontantdejaengage(Double montantdejaengage) {
        this.montantdejaengage = montantdejaengage;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    private Integer annee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(Long exerciceId) {
        this.exerciceId = exerciceId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChapitre() {
        return chapitre;
    }

    public void setChapitre(String chapitre) {
        this.chapitre = chapitre;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getParagraphe() {
        return paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }

    public Double getDotInitAE() {
        return dotInitAE;
    }

    public void setDotInitAE(Double dotInitAE) {
        this.dotInitAE = dotInitAE;
    }

    public Double getDotInitCP() {
        return dotInitCP;
    }

    public void setDotInitCP(Double dotInitCP) {
        this.dotInitCP = dotInitCP;
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

    public Double getEngage() {
        return engage;
    }

    public void setEngage(Double engage) {
        this.engage = engage;
    }

    public Double getEngageCF() {
        return engageCF;
    }

    public void setEngageCF(Double engageCF) {
        this.engageCF = engageCF;
    }

    public Double getLiquide() {
        return liquide;
    }

    public void setLiquide(Double liquide) {
        this.liquide = liquide;
    }

    public Double getOrdonne() {
        return ordonne;
    }

    public void setOrdonne(Double ordonne) {
        this.ordonne = ordonne;
    }

    public Double getVbp() {
        return vbp;
    }

    public void setVbp(Double vbp) {
        this.vbp = vbp;
    }

    public Double getEcp() {
        return ecp;
    }

    public void setEcp(Double ecp) {
        this.ecp = ecp;
    }

    public Double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(Double montantEstime) {
        this.montantEstime = montantEstime;
    }

    @Override
    public String toString() {
        return "LigneBudgetaire{" +
            "id=" + id +
            ", budget='" + budget + '\'' +
            ", ligneCredit='" + ligneCredit + '\'' +
            ", section='" + section + '\'' +
            ", programme='" + programme + '\'' +
            ", action='" + action + '\'' +
            ", chapitre='" + chapitre + '\'' +
            ", activite='" + activite + '\'' +
            ", article='" + article + '\'' +
            ", paragraphe='" + paragraphe + '\'' +
            ", dotInitAE=" + dotInitAE +
            ", dotInitCP=" + dotInitCP +
            ", dotCorAE=" + dotCorAE +
            ", dotCorCP=" + dotCorCP +
            ", engage=" + engage +
            ", engageCF=" + engageCF +
            ", liquide=" + liquide +
            ", ordonne=" + ordonne +
            ", vbp=" + vbp +
            ", ecp=" + ecp +
            ", deleted=" + deleted +
            ", exerciceId=" + exerciceId +
            ", montantEstime=" + montantEstime +
            ", besoinLigneBudgetaireList=" + besoinLigneBudgetaireList +
            ", annee=" + annee +
            '}';
    }
}
