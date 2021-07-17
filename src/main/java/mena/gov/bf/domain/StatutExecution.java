package mena.gov.bf.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "statut_execution")
public class StatutExecution extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "motif", nullable = false)
    @NotNull
    private String motif;
    @Basic
    @Column(name = "suspendu", nullable = false)
    private Boolean suspendu;
    @Basic
    @Column(name = "contrat_resilier")
    private Boolean contratResilier;
    @Basic
    @Column(name = "reprise", nullable = false)
    private Boolean reprise;
    @Basic
    @Column(name = "date_suspendu", nullable = false)
    private LocalDate dateSuspendu;
    @Basic
    @Column(name = "date_reprise", nullable = false)
    private LocalDate dateReprise;
    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;
    @ManyToOne(fetch = FetchType.LAZY)
    private Contrat contrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Boolean isSuspendu() {
        return suspendu;
    }

    public void setSuspendu(Boolean suspendu) {
        this.suspendu = suspendu;
    }

    public Boolean isContratResilier() {
        return contratResilier;
    }

    public void setContratResilier(Boolean contratResilier) {
        this.contratResilier = contratResilier;
    }

    public Boolean isReprise() {
        return reprise;
    }

    public void setReprise(Boolean reprise) {
        this.reprise = reprise;
    }

    public LocalDate getDateSuspendu() {
        return dateSuspendu;
    }

    public void setDateSuspendu(LocalDate dateSuspendu) {
        this.dateSuspendu = dateSuspendu;
    }

    public LocalDate getDateReprise() {
        return dateReprise;
    }

    public void setDateReprise(LocalDate dateReprise) {
        this.dateReprise = dateReprise;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

}