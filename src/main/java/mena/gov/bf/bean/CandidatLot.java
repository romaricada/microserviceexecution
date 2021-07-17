package mena.gov.bf.bean;

public class CandidatLot {

    private Long id;

    private Boolean estCandidat;

    private Boolean soumissionnaire;

    private Boolean attributaire;

    private Boolean titulaire;

    private Float montant;

    private Boolean dossierPaye;

    private Double nombrePoint;

    private Boolean deleted;

    private Long lotId;

    private Long candidatId;

    private Long deliberationId;

    private Long depouillementId;

    private Long contratId;

    /**
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Getter for property 'estCandidat'.
     *
     * @return Value for property 'estCandidat'.
     *
     */
    public Boolean getEstCandidat() {
        return estCandidat;
    }

    /**
     * Setter for property 'estCandidat'.
     *
     * @param estCandidat Value to set for property 'estCandidat'.
     */
    public void setEstCandidat(Boolean estCandidat) {
        this.estCandidat = estCandidat;
    }

    /**
     * Getter for property 'soumissionnaire'.
     *
     * @return Value for property 'soumissionnaire'.
     */
    public Boolean getSoumissionnaire() {
        return soumissionnaire;
    }

    /**
     * Setter for property 'soumissionnaire'.
     *
     * @param soumissionnaire Value to set for property 'soumissionnaire'.
     */
    public void setSoumissionnaire(Boolean soumissionnaire) {
        this.soumissionnaire = soumissionnaire;
    }

    /**
     * Getter for property 'attributaire'.
     *
     * @return Value for property 'attributaire'.
     */
    public Boolean getAttributaire() {
        return attributaire;
    }

    /**
     * Setter for property 'attributaire'.
     *
     * @param attributaire Value to set for property 'attributaire'.
     */
    public void setAttributaire(Boolean attributaire) {
        this.attributaire = attributaire;
    }

    /**
     * Getter for property 'titulaire'.
     *
     * @return Value for property 'titulaire'.
     */
    public Boolean getTitulaire() {
        return titulaire;
    }

    /**
     * Setter for property 'titulaire'.
     *
     * @param titulaire Value to set for property 'titulaire'.
     */
    public void setTitulaire(Boolean titulaire) {
        this.titulaire = titulaire;
    }

    /**
     * Getter for property 'montant'.
     *
     * @return Value for property 'montant'.
     */
    public Float getMontant() {
        return montant;
    }

    /**
     * Setter for property 'montant'.
     *
     * @param montant Value to set for property 'montant'.
     */
    public void setMontant(Float montant) {
        this.montant = montant;
    }

    /**
     * Getter for property 'dossierPaye'.
     *
     * @return Value for property 'dossierPaye'.
     */
    public Boolean getDossierPaye() {
        return dossierPaye;
    }

    /**
     * Setter for property 'dossierPaye'.
     *
     * @param dossierPaye Value to set for property 'dossierPaye'.
     */
    public void setDossierPaye(Boolean dossierPaye) {
        this.dossierPaye = dossierPaye;
    }

    /**
     * Getter for property 'nombrePoint'.
     *
     * @return Value for property 'nombrePoint'.
     */
    public Double getNombrePoint() {
        return nombrePoint;
    }

    /**
     * Setter for property 'nombrePoint'.
     *
     * @param nombrePoint Value to set for property 'nombrePoint'.
     */
    public void setNombrePoint(Double nombrePoint) {
        this.nombrePoint = nombrePoint;
    }

    /**
     * Getter for property 'deleted'.
     *
     * @return Value for property 'deleted'.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Setter for property 'deleted'.
     *
     * @param deleted Value to set for property 'deleted'.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Getter for property 'lotId'.
     *
     * @return Value for property 'lotId'.
     */
    public Long getLotId() {
        return lotId;
    }

    /**
     * Setter for property 'lotId'.
     *
     * @param lotId Value to set for property 'lotId'.
     */
    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    /**
     * Getter for property 'candidatId'.
     *
     * @return Value for property 'candidatId'.
     */
    public Long getCandidatId() {
        return candidatId;
    }

    /**
     * Setter for property 'candidatId'.
     *
     * @param candidatId Value to set for property 'candidatId'.
     */
    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    /**
     * Getter for property 'deliberationId'.
     *
     * @return Value for property 'deliberationId'.
     */
    public Long getDeliberationId() {
        return deliberationId;
    }

    /**
     * Setter for property 'deliberationId'.
     *
     * @param deliberationId Value to set for property 'deliberationId'.
     */
    public void setDeliberationId(Long deliberationId) {
        this.deliberationId = deliberationId;
    }

    /**
     * Getter for property 'depouillementId'.
     *
     * @return Value for property 'depouillementId'.
     */
    public Long getDepouillementId() {
        return depouillementId;
    }

    /**
     * Setter for property 'depouillementId'.
     *
     * @param depouillementId Value to set for property 'depouillementId'.
     */
    public void setDepouillementId(Long depouillementId) {
        this.depouillementId = depouillementId;
    }



    /**
     * Sets new contratId.
     *
     * @param contratId New value of contratId.
     */
    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    /**
     * Gets contratId.
     *
     * @return Value of contratId.
     */
    public Long getContratId() {
        return contratId;
    }
}
