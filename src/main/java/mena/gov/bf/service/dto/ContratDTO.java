package mena.gov.bf.service.dto;

import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.model.DataFile;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link mena.gov.bf.domain.Contrat} entity.
 */
public class ContratDTO implements Serializable {

    private Long id;

    @NotNull
    private String reference;

    private LocalDate dateApprobation;

    private Long cautionCandidatLotId;

    @NotNull
    private Boolean deleted;

    private Boolean resilierContrat;

    private Integer delaisExecution;

    private String sourceFinancement;

    private String natureMarche;

    private Long candidaId;

    private Long avisDacId;

    private Long exerciceId;

    private Long lotId;

    private LocalDate dateSignatureSoumissionnaire;

    private LocalDate dateDemarrage;

    private String numeroCompte;

    private String cleRib;

    private String libelleBanque;

    private String avisDacLibelle;

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

    private Double montant;

    private Set<ContentieuxDTO> contentieuxs = new HashSet<>();

    private Set<AvenantDTO> avenants = new HashSet<>();

    private Set<EtapeExecutionDTO> etapeExecutions = new HashSet<>();

    private Set<LiquidationDTO> liquidations = new HashSet<>();

    private Set<LigneBudgetaireContratDTO> ligneBudgetaireContrats = new HashSet<>();

    private Set<LigneBudgetaire> ligneBudgetaires = new HashSet<>();

    private List<DataFile> files = new ArrayList<>();

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

    /**
     * Getter for property 'resilierContrat'.
     *
     * @return Value for property 'resilierContrat'.
     */
    public Boolean getResilierContrat() {
        return resilierContrat;
    }

    /**
     * Setter for property 'resilierContrat'.
     *
     * @param resilierContrat Value to set for property 'resilierContrat'.
     */
    public void setResilierContrat(Boolean resilierContrat) {
        this.resilierContrat = resilierContrat;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    public Set<ContentieuxDTO> getContentieuxs() {
        return contentieuxs;
    }

    public void setContentieuxs(Set<ContentieuxDTO> contentieuxs) {
        this.contentieuxs = contentieuxs;
    }

    public Set<AvenantDTO> getAvenants() {
        return avenants;
    }

    public void setAvenants(Set<AvenantDTO> avenants) {
        this.avenants = avenants;
    }

    public Set<EtapeExecutionDTO> getEtapeExecutions() {
        return etapeExecutions;
    }

    public void setEtapeExecutions(Set<EtapeExecutionDTO> etapeExecutions) {
        this.etapeExecutions = etapeExecutions;
    }

    public Set<LiquidationDTO> getLiquidations() {
        return liquidations;
    }

    public void setLiquidations(Set<LiquidationDTO> liquidations) {
        this.liquidations = liquidations;
    }

    public Set<LigneBudgetaireContratDTO> getLigneBudgetaireContrats() {
        return ligneBudgetaireContrats;
    }

    public void setLigneBudgetaireContrats(Set<LigneBudgetaireContratDTO> ligneBudgetaireContrats) {
        this.ligneBudgetaireContrats = ligneBudgetaireContrats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContratDTO contratDTO = (ContratDTO) o;
        if (contratDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contratDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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

    /**
     * Sets new ligneBudgetaires.
     *
     * @param ligneBudgetaires New value of ligneBudgetaires.
     */
    public void setLigneBudgetaires(Set<LigneBudgetaire> ligneBudgetaires) {
        this.ligneBudgetaires = ligneBudgetaires;
    }

    /**
     * Gets ligneBudgetaires.
     *
     * @return Value of ligneBudgetaires.
     */
    public Set<LigneBudgetaire> getLigneBudgetaires() {
        return ligneBudgetaires;
    }

    public LocalDate getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(LocalDate dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public Boolean getDeleted() {
        return deleted;
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
        return "ContratDTO{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", dateApprobation=" + dateApprobation +
                ", cautionCandidatLotId=" + cautionCandidatLotId +
                ", deleted=" + deleted +
                ", resilierContrat=" + resilierContrat +
                ", candidaId=" + candidaId +
                ", avisDacId=" + avisDacId +
                ", exerciceId=" + exerciceId +
                ", lotId=" + lotId +
                ", dateSignatureSoumissionnaire=" + dateSignatureSoumissionnaire +
                ", dateDemarrage=" + dateDemarrage +
                ", numeroCompte='" + numeroCompte + '\'' +
                ", cleRib='" + cleRib + '\'' +
                ", libelleBanque='" + libelleBanque + '\'' +
                ", montant=" + montant +
                ", contentieuxs=" + contentieuxs +
                ", avenants=" + avenants +
                ", etapeExecutions=" + etapeExecutions +
                ", liquidations=" + liquidations +
                ", ligneBudgetaireContrats=" + ligneBudgetaireContrats +
                ", ligneBudgetaires=" + ligneBudgetaires +
                ", files=" + files +
                ", candidatLotsId=" + candidatLotsId +
                '}';
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
     * Sets new natureMarche.
     *
     * @param natureMarche New value of natureMarche.
     */
    public void setNatureMarche(String natureMarche) {
        this.natureMarche = natureMarche;
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
     * Sets new delaisExecution.
     *
     * @param delaisExecution New value of delaisExecution.
     */
    public void setDelaisExecution(Integer delaisExecution) {
        this.delaisExecution = delaisExecution;
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
     * Gets delaisExecution.
     *
     * @return Value of delaisExecution.
     */
    public Integer getDelaisExecution() {
        return delaisExecution;
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
