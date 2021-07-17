package mena.gov.bf.service.dto;

import mena.gov.bf.bean.*;
import mena.gov.bf.domain.LigneBudgetaireContrat;
import mena.gov.bf.domain.LigneBudgetaireEngagement;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * A DTO for the {@link mena.gov.bf.domain.Contrat} entity.
 */
public class EngagementDTO implements Serializable {

    private Long id;

    @NotNull
    private Double montantEngage;

    @NotNull
    private LocalDate date;

    private Long contratId;

    private Long besoinLigneBudgetaireId;

    private Long ligneBudgetaireContratId;

    private Long lotId;

    private Long avisDacId;

    private Long ligneBudgetaireId;

    private ActiviteDTO activite;
    private ContratDTO contratEn;
    private LotDTO lotEn;
    private String wordFlow;
    private Set<LigneBudgetaireEngagementDTO> ligneBudgetaireEngagements= new HashSet<>();

    private LigneBudgetaireEngagementDTO ligneBudgetaireEngagementDTO;
    private List<LigneBudgetaireContratDTO> ligneBudgetaireContrats =new ArrayList<>();

    @NotNull
    private Boolean deleted;

    private List<ContratDTO> contrat = new ArrayList<>();

    private List<Lot> lot = new ArrayList<>();

    private List<BesoinLigneBudgetaire> besoinLigneBudgetaire = new ArrayList<>();

    private List<LigneBudgetaire> ligneBudgetaires = new ArrayList<>();

    private LigneBudgetaireEngagement ligneBudgetaireEngagement;
    private String reference;
    private String intitule;

    /**
     * Getter for property 'avisDacId'.
     *
     * @return Value for property 'avisDacId'.
     */
    public Long getAvisDacId() { return avisDacId; }


    /**
     * Setter for property 'activiteId'.
     *
     * @param avisDacId Value to set for property 'avisDacId'.
     */
    public void setAvisDacId(Long avisDacId) { this.avisDacId = avisDacId; }


    public List<ContratDTO> getContrat() {
        return contrat;
    }

    public void setContrat(List<ContratDTO> contrat) {
        this.contrat = contrat;
    }

    public Long getLigneBudgetaireId() {
        return ligneBudgetaireId;
    }

    public void setLigneBudgetaireId(Long ligneBudgetaireId) {
        this.ligneBudgetaireId = ligneBudgetaireId;
    }

    public List<LigneBudgetaireContratDTO> getLigneBudgetaireContrats() {
        return ligneBudgetaireContrats;
    }

    public void setLigneBudgetaireContrats(List<LigneBudgetaireContratDTO> ligneBudgetaireContrats) {
        this.ligneBudgetaireContrats = ligneBudgetaireContrats;
    }

    /**
     * Getter for property 'contrat'.
     *
     * @return Value for property 'contrat'.
     */





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontantEngage() {
        return montantEngage;
    }

    public void setMontantEngage(Double montantEngage) {
        this.montantEngage = montantEngage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Long getBesoinLigneBudgetaireId() {
        return besoinLigneBudgetaireId;
    }

    public void setBesoinLigneBudgetaireId(Long besoinLigneBudgetaireId) {
        this.besoinLigneBudgetaireId = besoinLigneBudgetaireId;
    }

    public Set<LigneBudgetaireEngagementDTO> getLigneBudgetaireEngagements() {
        return ligneBudgetaireEngagements;
    }

    public void setLigneBudgetaireEngagements(Set<LigneBudgetaireEngagementDTO> ligneBudgetaireEngagements) {
        this.ligneBudgetaireEngagements = ligneBudgetaireEngagements;
    }

    public LigneBudgetaireEngagementDTO getLigneBudgetaireEngagementDTO() {
        return ligneBudgetaireEngagementDTO;
    }

    public void setLigneBudgetaireEngagementDTO(LigneBudgetaireEngagementDTO ligneBudgetaireEngagementDTO) {
        this.ligneBudgetaireEngagementDTO = ligneBudgetaireEngagementDTO;
    }

    public LigneBudgetaireEngagement getLigneBudgetaireEngagement() {
        return ligneBudgetaireEngagement;
    }

    public void setLigneBudgetaireEngagement(LigneBudgetaireEngagement ligneBudgetaireEngagement) {
        this.ligneBudgetaireEngagement = ligneBudgetaireEngagement;
    }

    /**
     * Getter for property 'activite'.
     *
     * @return Value for property 'activite'.
     */
    public ActiviteDTO getActivite() {
        return activite;
    }

    /**
     * Setter for property 'activite'.
     *
     * @param activite Value to set for property 'activite'.
     */
    public void setActivite(ActiviteDTO activite) {
        this.activite = activite;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<BesoinLigneBudgetaire> getBesoinLigneBudgetaire() {
        return besoinLigneBudgetaire;
    }

    public void setBesoinLigneBudgetaire(List<BesoinLigneBudgetaire> besoinLigneBudgetaire) {
        this.besoinLigneBudgetaire = besoinLigneBudgetaire;
    }

    /**
     * Getter for property 'contratEn'.
     *
     * @return Value for property 'contratEn'.
     */
    public ContratDTO getContratEn() {
        return contratEn;
    }

    /**
     * Setter for property 'contratEn'.
     *
     * @param contratEn Value to set for property 'contratEn'.
     */
    public void setContratEn(ContratDTO contratEn) {
        this.contratEn = contratEn;
    }

    /**
     * Getter for property 'lotEn'.
     *
     * @return Value for property 'lotEn'.
     */
    public LotDTO getLotEn() {
        return lotEn;
    }

    /**
     * Setter for property 'lotEn'.
     *
     * @param lotEn Value to set for property 'lotEn'.
     */
    public void setLotEn(LotDTO lotEn) {
        this.lotEn = lotEn;
    }

    public List<Lot> getLot() {
        return lot;
    }

    public void setLot(List<Lot> lot) {
        this.lot = lot;
    }

    public Long getLigneBudgetaireContratId() {
        return ligneBudgetaireContratId;
    }

    public void setLigneBudgetaireContratId(Long ligneBudgetaireContratId) {
        this.ligneBudgetaireContratId = ligneBudgetaireContratId;
    }

    public List<LigneBudgetaire> getLigneBudgetaires() {
        return ligneBudgetaires;
    }

    public void setLigneBudgetaires(List<LigneBudgetaire> ligneBudgetaires) {
        this.ligneBudgetaires = ligneBudgetaires;
    }

    public String getWordFlow() {
        return wordFlow;
    }

    public void setWordFlow(String wordFlow) {
        this.wordFlow = wordFlow;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return "EngagementDTO{" +
            "id=" + id +
            ", montantEngage=" + montantEngage +
            ", date=" + date +
            ", contratId=" + contratId +
            ", besoinLigneBudgetaireId=" + besoinLigneBudgetaireId +
            ", ligneBudgetaireContratId=" + ligneBudgetaireContratId +
            ", lotId=" + lotId +
            ", avisDacId=" + avisDacId +
            ", ligneBudgetaireId=" + ligneBudgetaireId +
            ", activite=" + activite +
            ", contratEn=" + contratEn +
            ", lotEn=" + lotEn +
            ", ligneBudgetaireEngagements=" + ligneBudgetaireEngagements +
            ", ligneBudgetaireEngagementDTO=" + ligneBudgetaireEngagementDTO +
            ", deleted=" + deleted +
            ", contrat=" + contrat +
            ", lot=" + lot +
            ", besoinLigneBudgetaire=" + besoinLigneBudgetaire +
            ", ligneBudgetaires=" + ligneBudgetaires +
            '}';
    }
}
