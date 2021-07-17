package mena.gov.bf.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import mena.gov.bf.domain.enumeration.EtatOs;
import mena.gov.bf.domain.enumeration.TypeOs;
/**
 * Entite de gestion des ordres de service.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "ordre_service")
public class OrdreService extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeOs type;

    @Basic
    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EtatOs etat;

    @Basic
    @Column(name = "date_demarrage", nullable = false)
    private LocalDate dateDemarrage;

    @Basic
    private String dateFin;

    @Basic
    @Column(name = "delai")
    private Integer delai;

    @Basic
    @Column(name = "Numero_os")
    private Long NumeroOs;


    @Basic
    @Column(name = "libelle")
    private String libelle;

    @Basic
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contrat contrat;


    public Long getNumeroOs() {
        return NumeroOs;
    }

    public void setNumeroOs(Long numeroOs) {
        NumeroOs = numeroOs;
    }

    public LocalDate getDateDemarrage() {
        return dateDemarrage;
    }

    public void setDateDemarrage(LocalDate dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOs getType() {
        return type;
    }

    public void setType(TypeOs type) {
        this.type = type;
    }

    public EtatOs getEtat() {
        return etat;
    }

    public void setEtat(EtatOs etat) {
        this.etat = etat;
    }

   /* public LocalDate getDateDebut() {
        return dateDemarrage;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDemarrage = dateDebut;
    }*/

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getDelai() {
        return delai;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
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
