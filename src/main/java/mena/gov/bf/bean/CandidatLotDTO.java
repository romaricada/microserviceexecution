package mena.gov.bf.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CandidatLotDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean estCandidat;

    @NotNull
    private Boolean soumissionnaire;

    @NotNull
    private Boolean attributaire;

    @NotNull
    private Boolean titulaire;

    private Float montant;

    @NotNull
    private Boolean dossierPaye;

    private Double nombrePoint;

    @NotNull
    private Boolean deleted;

    private Long lotId;

    private Long candidatId;

    private Long deliberationId;

    private Long depouillementId;

    private Boolean isCaution;

    private Boolean retenu;

    private Long contratId;

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }


    private List<LotDTO>  lots = new ArrayList<>();

    public List<LotDTO> getLots() { return lots; }



    public void setLots(List<LotDTO> lots) { this.lots = lots; }


    private LotDTO lot= new LotDTO();


    public LotDTO getLot() {
        return lot;
    }

    public void setLot(LotDTO lot) {
        this.lot = lot;
    }

    public Boolean getRetenu() {
        return retenu;
    }

    public void setRetenu(Boolean retenu) {
        this.retenu = retenu;
    }

    private Long reclamationId;

      public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstCandidat() {
        return estCandidat;
    }

    public void setEstCandidat(Boolean estCandidat) {
        this.estCandidat = estCandidat;
    }

    public Boolean isSoumissionnaire() {
        return soumissionnaire;
    }

    public void setSoumissionnaire(Boolean soumissionnaire) {
        this.soumissionnaire = soumissionnaire;
    }

    public Boolean isAttributaire() {
        return attributaire;
    }

    public void setAttributaire(Boolean attributaire) {
        this.attributaire = attributaire;
    }

    public Boolean isTitulaire() {
        return titulaire;
    }

    public void setTitulaire(Boolean titulaire) {
        this.titulaire = titulaire;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Boolean isDossierPaye() {
        return dossierPaye;
    }

    public void setDossierPaye(Boolean dossierPaye) {
        this.dossierPaye = dossierPaye;
    }

    public Double getNombrePoint() {
        return nombrePoint;
    }

    public void setNombrePoint(Double nombrePoint) {
        this.nombrePoint = nombrePoint;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    public Long getDeliberationId() {
        return deliberationId;
    }

    public void setDeliberationId(Long deliberationId) {
        this.deliberationId = deliberationId;
    }

    public Long getDepouillementId() {
        return depouillementId;
    }

    public void setDepouillementId(Long depouillementId) {
        this.depouillementId = depouillementId;
    }

    public Long getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(Long reclamationId) {
        this.reclamationId = reclamationId;
    }

    public Boolean getCaution() {
        return isCaution;
    }

    public void setCaution(Boolean caution) {
        isCaution = caution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatLotDTO candidatLotDTO = (CandidatLotDTO) o;
        if (candidatLotDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals( getId(), candidatLotDTO.getId() );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( getId() );
    }

    @Override
    public String toString() {
        return "CandidatLotDTO{" +
            "id=" + id +
            ", estCandidat=" + estCandidat +
            ", soumissionnaire=" + soumissionnaire +
            ", attributaire=" + attributaire +
            ", titulaire=" + titulaire +
            ", montant=" + montant +
            ", dossierPaye=" + dossierPaye +
            ", nombrePoint=" + nombrePoint +
            ", deleted=" + deleted +
            ", lotId=" + lotId +
            ", candidatId=" + candidatId +
            ", deliberationId=" + deliberationId +
            ", depouillementId=" + depouillementId +
            ", isCaution=" + isCaution +
            ", retenu=" + retenu +
            ", contratId=" + contratId +
             ", lots=" + lots +
            ", lot=" + lot +
            ", reclamationId=" + reclamationId +
            '}';
    }
}
