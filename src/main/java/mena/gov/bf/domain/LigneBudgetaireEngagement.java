package mena.gov.bf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class LigneBudgetaireEngagement {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ligne_budgetaire_id")
    private Long ligneBudgetaireId;

    @Column(name = "montant_engage")
    private Double montantEngage;

    @ManyToOne
    private Engagement engagement;

    @Column(name = "deleted")
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLigneBudgetaireId() {
        return ligneBudgetaireId;
    }

    public void setLigneBudgetaireId(Long ligneBudgetaireId) {
        this.ligneBudgetaireId = ligneBudgetaireId;
    }

    public Double getMontantEngage() {
        return montantEngage;
    }

    public void setMontantEngage(Double montantEngage) {
        this.montantEngage = montantEngage;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneBudgetaireEngagement)) return false;
        LigneBudgetaireEngagement that = (LigneBudgetaireEngagement) o;
        return getId().equals(that.getId()) &&
            Objects.equals(getLigneBudgetaireId(), that.getLigneBudgetaireId()) &&
            Objects.equals(getMontantEngage(), that.getMontantEngage()) &&
            Objects.equals(getEngagement(), that.getEngagement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "LigneBudgetaireEngagement{" +
            "id=" + id +
            ", ligneBudgetaireId=" + ligneBudgetaireId +
            ", montantEngage=" + montantEngage +
            ", engagement=" + engagement +
            ", deleted=" + deleted +
            '}';
    }
}
