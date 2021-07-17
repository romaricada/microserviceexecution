package mena.gov.bf.domain;

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
import java.io.Serializable;
/**
 * Entite de gestion des estapes d'exécution.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "etape_execution")
public class EtapeExecution extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "libelle", nullable = false)
    @NotNull
    private String libelle;
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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