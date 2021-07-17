package mena.gov.bf.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 * Entite de gestion des contrats.
 * @author TRAORE BRAHIMA 
 * @email siguizana08@gmail.com
 */
@Entity
@Table(name = "contrat")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contrat extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "reference", nullable = false)
    private String reference;

    @Basic
    @Column(name = "date_approbation")
    private LocalDate dateApprobation;

    @Basic
    @Column(name = "date_signature_soumissionnaire")
    private LocalDate dateSignatureSoumissionnaire;

    @Basic
    @Column(name = "date_demarrage")
    private LocalDate dateDemarrage;

    @Column(name = "numero_compte")
    private String numeroCompte;

    @Column(name = "cle_rib")
    private String cleRib;

    @Column(name = "libelle_banque")
    private String libelleBanque;

    @Basic
    @Column(name = "resilier_contrat")
    private Boolean resilierContrat;

    @Basic
    @Column(name = "caution_candidat_lot_id")
    private Long cautionCandidatLotId;

    @Basic
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "delais_execution")
    private Integer delaisExecution;

    @Column(name = "source_financement")
    private String sourceFinancement;

    @Column(name = "nature_marche")
    private String natureMarche;

    @Column(name = "avis_dac_libelle")
    private String avisDacLibelle;

    @Basic
    @Column(name = "candidat_id")
    private Long candidaId;

    @Basic
    @Column(name = "lot_id")
    private Long lotId;

    @Column(name = "montant")
    private Double montant;

    @Basic
    @Column(name = "avis_dac_id")
    private Long avisDacId;
    @Basic
    private String wordFlow;

    private LocalDate dateReceptionGC;
    /**
     * Getter for property 'exerciceId'.
     *
     * @return Value for property 'exerciceId'.
     */
    public Long getExerciceId() {
        return exerciceId;
    }

    /**
     * Setter for property 'exerciceId'.
     *
     * @param exerciceId Value to set for property 'exerciceId'.
     */
    public void setExerciceId(Long exerciceId) {
        this.exerciceId = exerciceId;
    }

    @Column(name = "exercice_id")
    private Long exerciceId;

    @OneToMany(mappedBy = "contrat")
    private Set<StatutExecution> statutExecutions = new HashSet<>();

    @OneToMany(mappedBy = "contrat")
    private Set<Contentieux> contentieuxs = new HashSet<>();

    @OneToMany(mappedBy = "contrat")
    private Set<Avenant> avenants = new HashSet<>();

    @OneToMany(mappedBy = "contrat")
    private Set<EtapeExecution> etapeExecutions = new HashSet<>();

    @OneToMany(mappedBy = "contrat")
    private Set<LigneBudgetaireContrat> ligneBudgetaireContrats = new HashSet<>();

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> candidatLotsId;

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

    public Boolean isResilierContrat() {
        return resilierContrat;
    }

    public void setResilierContrat(Boolean resilierContrat) {
        this.resilierContrat = resilierContrat;
    }

    public Long getCautionCandidatLotId() {
        return cautionCandidatLotId;
    }

    public void setCautionCandidatLotId(Long cautionCandidatLotId) {
        this.cautionCandidatLotId = cautionCandidatLotId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<StatutExecution> getStatutExecutions() {
        return statutExecutions;
    }

    public void setStatutExecutions(Set<StatutExecution> statutExecutions) {
        this.statutExecutions = statutExecutions;
    }

    public Set<Contentieux> getContentieuxs() {
        return contentieuxs;
    }

    public void setContentieuxs(Set<Contentieux> contentieuxs) {
        this.contentieuxs = contentieuxs;
    }

    public Set<Avenant> getAvenants() {
        return avenants;
    }

    public void setAvenants(Set<Avenant> avenants) {
        this.avenants = avenants;
    }

    public Set<EtapeExecution> getEtapeExecutions() {
        return etapeExecutions;
    }

    public void setEtapeExecutions(Set<EtapeExecution> etapeExecutions) {
        this.etapeExecutions = etapeExecutions;
    }

    /*public Set<Liquidation> getLiquidations() {
        return liquidations;
    }

    public void setLiquidations(Set<Liquidation> liquidations) {
        this.liquidations = liquidations;
    }*/

    public Set<LigneBudgetaireContrat> getLigneBudgetaireContrats() {
        return ligneBudgetaireContrats;
    }

    public void setLigneBudgetaireContrats(Set<LigneBudgetaireContrat> ligneBudgetaireContrats) {
        this.ligneBudgetaireContrats = ligneBudgetaireContrats;
    }

    public List<Long> getCandidatLotsId() {
        return candidatLotsId;
    }

    public void setCandidatLotsId(List<Long> candidatLotsId) {
        this.candidatLotsId = candidatLotsId;
    }

    public Long getCandidaId() {
        return candidaId;
    }

    public void setCandidaId(Long candidaId) {
        this.candidaId = candidaId;
    }

    public Long getAvisDacId() {
        return avisDacId;
    }

    public void setAvisDacId(Long avisDacId) {
        this.avisDacId = avisDacId;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    /**
     * Sets new lotId.
     *
     * @param lotId New value of lotId.
     */
    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    /**
     * Gets lotId.
     *
     * @return Value of lotId.
     */
    public Long getLotId() {
        return lotId;
    }

    public LocalDate getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(LocalDate dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public LocalDate getDateSignatureSoumissionnaire() {
        return dateSignatureSoumissionnaire;
    }

    public void setDateSignatureSoumissionnaire(LocalDate dateSignatureSoumissionnaire) {
        this.dateSignatureSoumissionnaire = dateSignatureSoumissionnaire;
    }

    public LocalDate getDateDemarrage() {
        return dateDemarrage;
    }

    public void setDateDemarrage(LocalDate dateDemarrage) {
        this.dateDemarrage = dateDemarrage;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getCleRib() {
        return cleRib;
    }

    public void setCleRib(String cleRib) {
        this.cleRib = cleRib;
    }

    public String getLibelleBanque() {
        return libelleBanque;
    }

    public void setLibelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
    }

    public Boolean getResilierContrat() {
        return resilierContrat;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public String getWordFlow() {
        return wordFlow;
    }

    public void setWordFlow(String wordFlow) {
        this.wordFlow = wordFlow;
    }

    public LocalDate getDateReceptionGC() {
        return dateReceptionGC;
    }

    public void setDateReceptionGC(LocalDate dateReceptionGC) {
        this.dateReceptionGC = dateReceptionGC;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", dateApprobation=" + dateApprobation +
                ", dateSignatureSoumissionnaire=" + dateSignatureSoumissionnaire +
                ", dateDemarrage=" + dateDemarrage +
                ", numeroCompte='" + numeroCompte + '\'' +
                ", cleRib='" + cleRib + '\'' +
                ", libelleBanque='" + libelleBanque + '\'' +
                ", resilierContrat=" + resilierContrat +
                ", cautionCandidatLotId=" + cautionCandidatLotId +
                ", deleted=" + deleted +
                ", candidaId=" + candidaId +
                ", lotId=" + lotId +
                ", montant=" + montant +
                ", avisDacId=" + avisDacId +
                ", exerciceId=" + exerciceId +
                ", statutExecutions=" + statutExecutions +
                ", contentieuxs=" + contentieuxs +
                ", avenants=" + avenants +
                ", etapeExecutions=" + etapeExecutions +
                ", ligneBudgetaireContrats=" + ligneBudgetaireContrats +
                ", candidatLotsId=" + candidatLotsId +
                '}';
    }

    /**
     * Gets delaisExecution.
     *
     * @return Value of delaisExecution.
     */
    public Integer getDelaisExecution() {
        return delaisExecution;
    }

    /**
     * Sets new delaisExecution.
     *
     * @param delaisExecution New value of delaisExecution.
     */
    public void setDelaisExecution(Integer delaisExecution) {
        this.delaisExecution = delaisExecution;
    }

    /**
     * Gets sourceFinancement.
     *
     * @return Value of sourceFinancement.
     */
    public String getSourceFinancement() {
        return sourceFinancement;
    }

    /**
     * Gets natureMarche.
     *
     * @return Value of natureMarche.
     */
    public String getNatureMarche() {
        return natureMarche;
    }

    /**
     * Sets new sourceFinancement.
     *
     * @param sourceFinancement New value of sourceFinancement.
     */
    public void setSourceFinancement(String sourceFinancement) {
        this.sourceFinancement = sourceFinancement;
    }

    /**
     * Sets new natureMarche.
     *
     * @param natureMarche New value of natureMarche.
     */
    public void setNatureMarche(String natureMarche) {
        this.natureMarche = natureMarche;
    }

    /**
     * Sets new avisDacLibelle.
     *
     * @param avisDacLibelle New value of avisDacLibelle.
     */
    public void setAvisDacLibelle(String avisDacLibelle) {
        this.avisDacLibelle = avisDacLibelle;
    }

    /**
     * Gets avisDacLibelle.
     *
     * @return Value of avisDacLibelle.
     */
    public String getAvisDacLibelle() {
        return avisDacLibelle;
    }
}
