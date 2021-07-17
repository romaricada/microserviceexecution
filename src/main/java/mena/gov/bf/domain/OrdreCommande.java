package mena.gov.bf.domain;

import javax.persistence.*;

import mena.gov.bf.domain.enumeration.TypeOrdreCommande;

import java.io.Serializable;

/**
 * Entite de gestion des ordres de commandes.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "ordre_commande")
public class OrdreCommande extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "sequenceGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Basic
    @Column(name = "reference", nullable = false)
    private String reference;
    @Basic
    @Column(name = "date_signature", nullable = false)
    private String dateSignature;
    @Basic
    @Column(name = "date_debut_prevu", nullable = false)
    private String dateDebutPrevu;
    @Basic
    @Column(name = "date_fin_prevu", nullable = false)
    private String DateFinPrevu;
    @Basic
    @Column(name = "resilier", nullable = false)
    private Boolean resilier;
    @Basic
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
    @OneToOne
    private Contrat contrat;
    @OneToOne
    private Avenant avenant;

    private TypeOrdreCommande typeOrdreCommande;

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

    public String getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(String dateSignature) {
        this.dateSignature = dateSignature;
    }

    public String getDateDebutPrevu() {
        return dateDebutPrevu;
    }

    public void setDateDebutPrevu(String dateDebutPrevu) {
        this.dateDebutPrevu = dateDebutPrevu;
    }

    public String getDateFinPrevu() {
        return DateFinPrevu;
    }

    public void setDateFinPrevu(String DateFinPrevu) {
        this.DateFinPrevu = DateFinPrevu;
    }

    public Boolean getResilier() {
        return resilier;
    }

    public void setResilier(Boolean resilier) {
        this.resilier = resilier;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Avenant getAvenant() {
        return avenant;
    }

    public void setAvenant(Avenant avenant) {
        this.avenant = avenant;
    }

    public TypeOrdreCommande getTypeOrdreCommande() {
        return typeOrdreCommande;
    }

    public void setTypeOrdreCommande(TypeOrdreCommande typeOrdreCommande) {
        this.typeOrdreCommande = typeOrdreCommande;
    }

    /**
     * Gets deleted.
     *
     * @return Value of deleted.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets new deleted.
     *
     * @param deleted New value of deleted.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
