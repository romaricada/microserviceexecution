package mena.gov.bf.fileManager;

import mena.gov.bf.bean.ActiviteDTO;
import mena.gov.bf.bean.CandidatLot;
import mena.gov.bf.bean.ExerciceBudgetaire;
import mena.gov.bf.bean.Lot;
import mena.gov.bf.domain.*;
import mena.gov.bf.model.TypeDossier;
import mena.gov.bf.proxies.*;
import mena.gov.bf.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class PathSevice {
    private final Logger log = LoggerFactory.getLogger(FTPFileManagerService.class);
    private final ExerciceBudgetaireRepository exerciceBudgetaireRepository;
    private final ActiviteRepository activiteRepository;
    private final LotRepository lotRepository;
    private final CandidatLotRepository candidatLotRepository;
    private final ContentieuxRepository contentieuxRepository;
    private final ContratRepository contratRepository;
    private final AvenantRepository avenantRepository;
    private final DecisionContentieuxRepository decisionContentieuxRepository;
    private final EtapeExecutionRepository etapeExecutionRepository;
    private final LiquidationRepository liquidationRepository;
    private final PenaliteRepository penaliteRepository;
    private final StatutExecutionRepository statutExecutionRepository;
    private final ServeurRepository serveurRepository;
    private Path path;
    private String chemin = null;

    public PathSevice(ExerciceBudgetaireRepository exerciceBudgetaireRepository, ActiviteRepository activiteRepository, LotRepository lotRepository, CandidatLotRepository candidatLotRepository, ContentieuxRepository contentieuxRepository, ContratRepository contratRepository, AvenantRepository avenantRepository, DecisionContentieuxRepository decisionContentieuxRepository, EtapeExecutionRepository etapeExecutionRepository, LiquidationRepository liquidationRepository, PenaliteRepository penaliteRepository, StatutExecutionRepository statutExecutionRepository, ServeurRepository serveurRepository) {
        this.exerciceBudgetaireRepository = exerciceBudgetaireRepository;
        this.activiteRepository = activiteRepository;
        this.lotRepository = lotRepository;
        this.candidatLotRepository = candidatLotRepository;
        this.contentieuxRepository = contentieuxRepository;
        this.contratRepository = contratRepository;
        this.avenantRepository = avenantRepository;
        this.decisionContentieuxRepository = decisionContentieuxRepository;
        this.etapeExecutionRepository = etapeExecutionRepository;
        this.liquidationRepository = liquidationRepository;
        this.penaliteRepository = penaliteRepository;
        this.statutExecutionRepository = statutExecutionRepository;
        this.serveurRepository = serveurRepository;
    }

    public String getPath(Long id, TypeDossier typeDossier) {
        chemin = "/documents/";
        ExerciceBudgetaire exerciceBudgetaire = exerciceBudgetaireRepository.findCurrentExercice();
        switch (typeDossier) {
            case ACTIVITE:
                ActiviteDTO activite = activiteRepository.findActiviteById(id);
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite.getId() + "/";
                break;
            case CONTRAT:
                Contrat contrat = contratRepository.getOne(id);
                CandidatLot candidatLot2 = candidatLotRepository.getOne(
                    0L
                    //contrat.getCandidatLotId()
                );
                Lot lot6 = lotRepository.getOne(candidatLot2.getLotId());
                ActiviteDTO activite9 = activiteRepository.findActiviteById(lot6.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite9.getId() + "/" + lot6.getId() + "/"
                    + candidatLot2.getId() + "/" + contrat.getId() + "/";
                break;
            case AVENANT:
                Avenant avenant = avenantRepository.getOne(id);
                Contrat contrat2 = contratRepository.getOne(avenant.getContrat().getId());
                CandidatLot candidatLot3 = candidatLotRepository.getOne(
                    0L
                    //contrat2.getCandidatLotId()
                );
                Lot lot7 = lotRepository.getOne(candidatLot3.getLotId());
                ActiviteDTO activite10 = activiteRepository.findActiviteById(lot7.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite10.getId() + "/" + lot7.getId() + "/"
                    + candidatLot3.getId() + "/" + contrat2.getId() + "/"+avenant.getId()+"/";
                break;
            case CONTENTIEUX:
                Contentieux contentieux = contentieuxRepository.getOne(id);
                Contrat contrat3 = contratRepository.getOne(contentieux.getContrat().getId());
                CandidatLot candidatLot4 = candidatLotRepository.getOne(
                    0L
                    //contrat3.getCandidatLotId()
                );
                Lot lot8 = lotRepository.getOne(candidatLot4.getLotId());
                ActiviteDTO activite11 = activiteRepository.findActiviteById(lot8.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite11.getId() + "/" + lot8.getId() + "/"
                    + candidatLot4.getId() + "/" + contrat3.getId() + "/"+contentieux.getId()+"/";
                break;
            case DECISIONCONTENTIEUX:
                Contentieux contentieux2 = contentieuxRepository.getOne(id);
                Contrat contrat6 = contratRepository.getOne(contentieux2.getContrat().getId());
                CandidatLot candidatLot7 = candidatLotRepository.getOne(
                    0L
                    //contrat6.getCandidatLotId()
                );
                Lot lot11 = lotRepository.getOne(candidatLot7.getLotId());
                ActiviteDTO activite14 = activiteRepository.findActiviteById(lot11.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite14.getId() + "/" + lot11.getId() + "/"
                    + candidatLot7.getId() + "/" + contrat6.getId() + "/contentieux/decisions/";
                break;
            case ETAPEEXECUTION:
                EtapeExecution etapeExecution = etapeExecutionRepository.getOne(id);
                Contrat contrat4 = contratRepository.getOne(etapeExecution.getContrat().getId());
                CandidatLot candidatLot5 = candidatLotRepository.getOne(
                    0L
                    //contrat4.getCandidatLotId()
                );
                Lot lot9 = lotRepository.getOne(candidatLot5.getLotId());
                ActiviteDTO activite12 = activiteRepository.findActiviteById(lot9.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite12.getId() + "/" + lot9.getId() + "/"
                    + candidatLot5.getId() + "/" + contrat4.getId() + "/" + etapeExecution.getId() + "/";
                break;
            case LIQUIDATION:
                Liquidation liquidation = liquidationRepository.getOne(id);
                Contrat contrat5 = contratRepository.getOne(liquidation.getContrat().getId());
                CandidatLot candidatLot6 = candidatLotRepository.getOne(
                    0L
                    //contrat5.getCandidatLotId()
                );
                Lot lot10 = lotRepository.getOne(candidatLot6.getLotId());
                ActiviteDTO activite13 = activiteRepository.findActiviteById(lot10.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite13.getId() + "/" + lot10.getId() + "/"
                    + candidatLot6.getId() + "/" + contrat5.getId() + "/"+liquidation.getId()+"/";
                break;
            case PENALITE:
               /* Penalite penalite = penaliteRepository.getOne(id);
                Contrat contrat7 = contratRepository.getOne(penalite.getContrat().getId());
                CandidatLot candidatLot8 = candidatLotRepository.getOne(
                    0L
                    //contrat7.getCandidatLotId()
                );
                Lot lot12 = lotRepository.getOne(candidatLot8.getLotId());
                ActiviteDTO activite15 = activiteRepository.findActiviteById(lot12.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite15.getId() + "/" + lot12.getId() + "/"
                    + candidatLot8.getId() + "/" + contrat7.getId() + "/" + penalite.getId() + "/";*/
                break;
            case STATUTEXECUTION:
                StatutExecution statutExecution = statutExecutionRepository.getOne(id);
                Contrat contrat8 = contratRepository.getOne(statutExecution.getContrat().getId());
                CandidatLot candidatLot9 = candidatLotRepository.getOne(
                    0L
                    //contrat8.getCandidatLotId()
                );
                Lot lot13 = lotRepository.getOne(candidatLot9.getLotId());
                ActiviteDTO activite16 = activiteRepository.findActiviteById(lot13.getActiviteId());
                chemin = chemin + exerciceBudgetaire.getAnnee() + "/" + activite16.getId() + "/" + lot13.getId() + "/"
                    + candidatLot9.getId() + "/" + contrat8.getId() + "/" + statutExecution.getId() + "/";
                break;
        }
        return chemin;
    }
}
