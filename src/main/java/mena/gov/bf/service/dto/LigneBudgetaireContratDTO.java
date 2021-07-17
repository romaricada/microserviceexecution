package mena.gov.bf.service.dto;

import mena.gov.bf.bean.BesoinLigneBudgetaireDTO;
import mena.gov.bf.bean.LigneBudgetaire;
import mena.gov.bf.domain.Contrat;
import mena.gov.bf.model.DataFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LigneBudgetaireContratDTO implements Serializable {
    private Long id;
    private Long ligneBudgetaireId;
    private Long contratId;
    private List<DataFile> files = new ArrayList<>();
    private LigneBudgetaire ligneBudgetaire ;
    private LigneBudgetaire ligneBudget ;
    private Contrat contrat ;
    private Double montantdisponible;
    private Double montantdejaengage;
    private Double montantEngage;
    private Double montantEstime;

    public Double getMontantdisponible() {
        return montantdisponible;
    }

    public void setMontantdisponible(Double montantdisponible) {
        this.montantdisponible = montantdisponible;
    }

    public Double getMontantdejaengage() {
        return montantdejaengage;
    }

    public void setMontantdejaengage(Double montantdejaengage) {
        this.montantdejaengage = montantdejaengage;
    }

    public Double getMontantEngage() {
        return montantEngage;
    }

    public void setMontantEngage(Double montantEngage) {
        this.montantEngage = montantEngage;
    }

    public Double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(Double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLigneBudgetaireId() {
        return ligneBudgetaireId;
    }

    public void setLigneBudgetaireId(Long ligneBudgetaireId) {
        this.ligneBudgetaireId = ligneBudgetaireId;
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

    public LigneBudgetaire getLigneBudgetaire() {
        return ligneBudgetaire;
    }

    public void setLigneBudgetaire(LigneBudgetaire ligneBudgetaire) {
        this.ligneBudgetaire = ligneBudgetaire;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public LigneBudgetaire getLigneBudget() {
        return ligneBudget;
    }

    public void setLigneBudget(LigneBudgetaire ligneBudget) {
        this.ligneBudget = ligneBudget;
    }

    @Override
    public String toString() {
        return "LigneBudgetaireContratDTO{" +
            "id=" + id +
            ", ligneBudgetaireId=" + ligneBudgetaireId +
            ", contratId=" + contratId +
            ", files=" + files +
            ", ligneBudgetaire=" + ligneBudgetaire +
            ", ligneBudget=" + ligneBudget +
            ", montantEstime=" + montantEstime +
            ", montantEstime=" + montantEstime +
            ", montantEngage=" + montantEngage +
            ", montantdejaengage=" + montantdejaengage +
            ", montantdisponible=" + montantdisponible +
            ", contrat=" + contrat +
            '}';
    }
}
