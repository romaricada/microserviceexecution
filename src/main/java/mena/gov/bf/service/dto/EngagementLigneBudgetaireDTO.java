package mena.gov.bf.service.dto;

import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.domain.Engagement;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link Contrat} entity.
 */
public class EngagementLigneBudgetaireDTO implements Serializable {

    private Long id;

    private Long engagementId;

    private Long ligneBudgetaireId;

    @NotNull
    private Double montantEngageLigne;

    @NotNull
    private Boolean deleted;

    private List<LigneBudgetaire> ligneBudgetaires = new ArrayList<>();

    private List<Engagement> engagements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    public Long getLigneBudgetaireId() {
        return ligneBudgetaireId;
    }

    public void setLigneBudgetaireId(Long ligneBudgetaireId) {
        this.ligneBudgetaireId = ligneBudgetaireId;
    }

    public Double getMontantEngageLigne() {
        return montantEngageLigne;
    }

    public void setMontantEngageLigne(Double montantEngageLigne) {
        this.montantEngageLigne = montantEngageLigne;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<LigneBudgetaire> getLigneBudgetaires() {
        return ligneBudgetaires;
    }

    public void setLigneBudgetaires(List<LigneBudgetaire> ligneBudgetaires) {
        this.ligneBudgetaires = ligneBudgetaires;
    }

    public List<Engagement> getEngagements() {
        return engagements;
    }

    public void setEngagements(List<Engagement> engagements) {
        this.engagements = engagements;
    }

    @Override
    public String toString() {
        return "EngagementLigneBudgetaireDTO{" +
            "id=" + id +
            ", engagementId=" + engagementId +
            ", ligneBudgetaireId=" + ligneBudgetaireId +
            ", montantEngageLigne=" + montantEngageLigne +
            ", deleted=" + deleted +
            ", ligneBudgetaires=" + ligneBudgetaires +
            ", engagements=" + engagements +
            '}';
    }
}
