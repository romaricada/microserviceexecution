package mena.gov.bf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 * Entite de gestion des avenant.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "avenant")
public class Avenant extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false)
    @NotNull
    private String reference;
    @Basic
    @Column(name = "motif", nullable = false)
    @NotNull
    private String motif;
    @Basic
    @Column(name = "montant", nullable = false)
    @NotNull
    private Double montant;
    @Basic
    @Column(name = "temps", nullable = false)
    @NotNull
    private String temps;
    @Basic
    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;
    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;
    @ManyToOne(fetch = FetchType.LAZY)
    private Contrat contrat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("avenants")
    private TypeAvenant typeAvenant;

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

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public TypeAvenant getTypeAvenant() {
        return typeAvenant;
    }

    public void setTypeAvenant(TypeAvenant typeAvenant) {
        this.typeAvenant = typeAvenant;
    }

    @Override
    public String toString() {
        return "Avenant{" +
            "id=" + id +
            ", reference='" + reference + '\'' +
            ", motif='" + motif + '\'' +
            ", montant=" + montant +
            ", temps='" + temps + '\'' +
            ", date=" + date +
            ", deleted=" + deleted +
            ", typeAvenant=" + typeAvenant +
            '}';
    }
}
