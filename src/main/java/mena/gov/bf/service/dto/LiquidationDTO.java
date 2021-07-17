package mena.gov.bf.service.dto;
import mena.gov.bf.model.DataFile;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link mena.gov.bf.domain.Liquidation} entity.
 */
public class LiquidationDTO implements Serializable {

    private Long id;

    private Double montant;

    private LocalDate datePaiement;

    private LocalDate dateVisaControle;

    private LocalDate dateVisaOrdonateur;

    private LocalDate dateDeLiquidation;

    private Boolean deleted;

    private String referencePaiement;

    private String support;

    private Long avisdacId;

    private Long contratId;

    /**
     * Getter for property 'dateVisaControle'.
     *
     * @return Value for property 'dateVisaControle'.
     */
    public LocalDate getDateVisaControle() {
        return dateVisaControle;
    }

    /**
     * Setter for property 'dateVisaControle'.
     *
     * @param dateVisaControle Value to set for property 'dateVisaControle'.
     */
    public void setDateVisaControle(LocalDate dateVisaControle) {
        this.dateVisaControle = dateVisaControle;
    }

    /**
     * Getter for property 'dateVisaOrdonateur'.
     *
     * @return Value for property 'dateVisaOrdonateur'.
     */
    public LocalDate getDateVisaOrdonateur() {
        return dateVisaOrdonateur;
    }

    /**
     * Setter for property 'dateVisaOrdonateur'.
     *
     * @param dateVisaOrdonateur Value to set for property 'dateVisaOrdonateur'.
     */
    public void setDateVisaOrdonateur(LocalDate dateVisaOrdonateur) {
        this.dateVisaOrdonateur = dateVisaOrdonateur;
    }

    /**
     * Getter for property 'dateDeLiquidation'.
     *
     * @return Value for property 'dateDeLiquidation'.
     */
    public LocalDate getDateDeLiquidation() {
        return dateDeLiquidation;
    }

    /**
     * Setter for property 'dateDeLiquidation'.
     *
     * @param dateDeLiquidation Value to set for property 'dateDeLiquidation'.
     */
    public void setDateDeLiquidation(LocalDate dateDeLiquidation) {
        this.dateDeLiquidation = dateDeLiquidation;
    }

    private Long engagementId;

    private String wordFlow;

    private Long penaliteId;

    private PenaliteDTO penalite;

    private Set<PenaliteDTO> penalites = new HashSet<>();

    private ContratDTO contrat;

    private List<DataFile> files = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LiquidationDTO liquidationDTO = (LiquidationDTO) o;
        if (liquidationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), liquidationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LiquidationDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", contrat=" + getContratId() +
            "}";
    }

    /**
     * Gets penalite.
     *
     * @return Value of penalite.
     */
    public PenaliteDTO getPenalite() {
        return penalite;
    }

    /**
     * Sets new contrat.
     *
     * @param contrat New value of contrat.
     */
    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    /**
     * Sets new penalite.
     *
     * @param penalite New value of penalite.
     */
    public void setPenalite(PenaliteDTO penalite) {
        this.penalite = penalite;
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
     * Gets contrat.
     *
     * @return Value of contrat.
     */
    public ContratDTO getContrat() {
        return contrat;
    }

    /**
     * Gets penaliteId.
     *
     * @return Value of penaliteId.
     */
    public Long getPenaliteId() {
        return penaliteId;
    }

    /**
     * Sets new penaliteId.
     *
     * @param penaliteId New value of penaliteId.
     */
    public void setPenaliteId(Long penaliteId) {
        this.penaliteId = penaliteId;
    }

    /**
     * Gets support.
     *
     * @return Value of support.
     */
    public String getSupport() {
        return support;
    }

    /**
     * Gets avisdacId.
     *
     * @return Value of avisdacId.
     */
    public Long getAvisdacId() {
        return avisdacId;
    }

    /**
     * Gets referencePaiement.
     *
     * @return Value of referencePaiement.
     */
    public String getReferencePaiement() {
        return referencePaiement;
    }

    /**
     * Sets new referencePaiement.
     *
     * @param referencePaiement New value of referencePaiement.
     */
    public void setReferencePaiement(String referencePaiement) {
        this.referencePaiement = referencePaiement;
    }

    /**
     * Sets new support.
     *
     * @param support New value of support.
     */
    public void setSupport(String support) {
        this.support = support;
    }

    /**
     * Sets new avisdacId.
     *
     * @param avisdacId New value of avisdacId.
     */
    public void setAvisdacId(Long avisdacId) {
        this.avisdacId = avisdacId;
    }


    /**
     * Gets penalites.
     *
     * @return Value of penalites.
     */
    public Set<PenaliteDTO> getPenalites() {
        return penalites;
    }

    /**
     * Sets new penalites.
     *
     * @param penalites New value of penalites.
     */
    public void setPenalites(Set<PenaliteDTO> penalites) {
        this.penalites = penalites;
    }

    /**
     * Sets new datePaiement.
     *
     * @param datePaiement New value of datePaiement.
     */
    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    /**
     * Gets datePaiement.
     *
     * @return Value of datePaiement.
     */
    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    /**
     * Sets new wordFlow.
     *
     * @param wordFlow New value of wordFlow.
     */
    public void setWordFlow(String wordFlow) {
        this.wordFlow = wordFlow;
    }

    /**
     * Gets wordFlow.
     *
     * @return Value of wordFlow.
     */
    public String getWordFlow() {
        return wordFlow;
    }

    /**
     * Sets new engagementId.
     *
     * @param engagementId New value of engagementId.
     */
    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    /**
     * Gets engagementId.
     *
     * @return Value of engagementId.
     */
    public Long getEngagementId() {
        return engagementId;
    }
}
