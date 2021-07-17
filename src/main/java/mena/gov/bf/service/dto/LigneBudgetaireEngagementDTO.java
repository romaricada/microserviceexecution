package mena.gov.bf.service.dto;

import mena.gov.bf.domain.Engagement;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class LigneBudgetaireEngagementDTO {

        @NotNull
        private Long id;

        private Long ligneBudgetaireId;

        private Double montantEngage;

        private Engagement engagement;

        private Long engagementId;

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

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof mena.gov.bf.domain.LigneBudgetaireEngagement)) return false;
            mena.gov.bf.domain.LigneBudgetaireEngagement that = (mena.gov.bf.domain.LigneBudgetaireEngagement) o;
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
