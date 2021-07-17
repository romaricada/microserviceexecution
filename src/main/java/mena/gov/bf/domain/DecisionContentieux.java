package mena.gov.bf.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * Entite de gestion des décisions.
 * @author TRAORE BRAHIMA
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "decision_contentieux")
public class DecisionContentieux extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "decision", nullable = false)
    @NotNull
    private String decision;
    @Basic
    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;
    @Basic
    @Column(name = "structure", nullable = false)
    @NotNull
    private String structure;
    @Basic
    @Column(name = "reference_decision", nullable = false)
    @NotNull
    private String referenceDecision;
    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;

    @Column(name = "contentieux_id", nullable = false)
    private Long contentieuxId;

    @OneToMany(mappedBy = "decisionContentieux")
    private Set<Contentieux> contentieuxs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getReferenceDecision() {
        return referenceDecision;
    }

    public void setReferenceDecision(String referenceDecision) {
        this.referenceDecision = referenceDecision;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Contentieux> getContentieuxs() {
        return contentieuxs;
    }

    public void setContentieuxs(Set<Contentieux> contentieuxs) {
        this.contentieuxs = contentieuxs;
    }

    /**
     * Gets contentieuxId.
     *
     * @return Value of contentieuxId.
     */
    public Long getContentieuxId() {
        return contentieuxId;
    }

    /**
     * Sets new contentieuxId.
     *
     * @param contentieuxId New value of contentieuxId.
     */
    public void setContentieuxId(Long contentieuxId) {
        this.contentieuxId = contentieuxId;
    }
}
