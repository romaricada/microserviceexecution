import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {EngagementService} from './engagement.service';
import {Engagement, IEngagement} from "app/shared/model/microserviceexecution/engagement.model";
import {Contrat, IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {ILigneBudgetaire, LigneBudgetaire} from "app/shared/model/microserviceppm/ligne-budgetaire.model";
import {ILot, Lot} from "app/shared/model/microservicedaccam/lot.model";
import {ContratService} from "app/entities/microserviceexecution/contrat/contrat.service";
import {LotService} from "app/entities/microservicedaccam/lot/lot.service";
import {ExerciceBudgetaire, IExerciceBudgetaire} from "app/shared/model/microserviceppm/exercice-budgetaire.model";
import {Activite, IActivite} from "app/shared/model/microserviceppm/activite.model";
import {ExerciceBudgetaireService} from "app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service";
import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {
  BesoinLigneBudgetaire,
  IBesoinLigneBudgetaire
} from "app/shared/model/microserviceppm/besoin-ligne-budgetaire.model";
import {BesoinLigneBudgetaireService} from "app/entities/microserviceppm/besoin-ligne-budgetaire/besoin-ligne-budgetaire.service";
import {
  EngagementLigneBudgetaire,
  IEngagementLigneBudgetaire
} from "app/shared/model/microserviceexecution/engagementLigneBudgetaire.model";
import {EngagementLigneBudgetaireService} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'jhi-engagement',
  templateUrl: './engagement.component.html',
  styleUrls: ['./engagement.scss']
})
export class EngagementComponent implements OnInit, OnDestroy {
  engagement: IEngagement;
  engagements: IEngagement[];
  engagementTMP: IEngagement[];

  contrats: IContrat[];
  contrat: IContrat;
  activites: IActivite[];
  activite: IActivite;

  ligneBudget: ILigneBudgetaire;

  besoinLigneBudgetaire: IBesoinLigneBudgetaire;
  besoinLigneBudgetaires: IBesoinLigneBudgetaire[];
  besoinLigneBudgetaireTMP: IBesoinLigneBudgetaire[];
  besoins: ILigneBudgetaire[];

  lot: ILot;
  lots: ILot[];

  exercice: IExerciceBudgetaire;
  exercices: IExerciceBudgetaire[];

  engagementLigneBudgetaire: IEngagementLigneBudgetaire;
  engagementLigneBudgetaires: IEngagementLigneBudgetaire[];
  engage: IEngagementLigneBudgetaire[];

  ligneBudgetaires: ILigneBudgetaire[];


  display: boolean;
  display1: boolean;

  contMontant: number;
  montEng: number;

  constructor(
    protected engagementService: EngagementService,
    protected besoinLigneBudgetaireService: BesoinLigneBudgetaireService,
    protected contratService: ContratService,
    protected lotService: LotService,
    protected exerciceService: ExerciceBudgetaireService,
    protected engagementLigneBudgetaireService: EngagementLigneBudgetaireService,
    protected activiteService: ActiviteService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected confirmationService: ConfirmationService,
    protected messageService: MessageService
  ) {}

  loadAll() {
    this.engagementService
      .query()
      .subscribe((res: HttpResponse<IEngagement[]>) => {
        this.engagementTMP = res.body;
        this.engagements = null;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.display = false;
    this.contMontant = 0;
    this.montEng = 0;

    this.engagement = new Engagement();
    this.engagements = [];

    this.contrats = [];
    this.contrat = new Contrat();

    this.ligneBudget = new LigneBudgetaire();

    this.besoinLigneBudgetaire = new BesoinLigneBudgetaire();
    this.besoinLigneBudgetaires = [];
    this.besoins = [];

    this.lots = [];
    this.lot = new Lot();

    this.exercice = new ExerciceBudgetaire();
    this.exercices = [];

    this.activite = new Activite();
    this.activites = [];

    this.engagementLigneBudgetaires = [];
    this.engage = [];

    this.ligneBudgetaires = [];

    this.contratService
      .query()
      .subscribe((res: HttpResponse<IContrat[]>) =>{
        this.contrats = res.body;
      });

    this.exerciceService
      .query()
      .subscribe((res: HttpResponse<IExerciceBudgetaire[]>) =>{
      this.exercices = res.body;
    });

    this.activiteService
      .query().subscribe((res: HttpResponse<IActivite[]>) =>{
        this.activites = res.body;
    });

    this.besoinLigneBudgetaireService.query().subscribe((res: HttpResponse<IBesoinLigneBudgetaire[]>) => {
      this.besoinLigneBudgetaires = null;
      this.besoinLigneBudgetaireTMP = res.body;
    });

  }

  ngOnDestroy() {
    // this.eventManager.destroy(this.eventSubscriber);
  }

  annuler() {
    this.display = false;
  }

  ligne() {

  }

  exerciceLoad() {
    this.activiteService.findAllByAnneeExerciceNew(isNaN(this.exercice.id) ? 0 : this.exercice.id).subscribe((res: HttpResponse<IActivite[]>) => {
      if (res.body.length > 0) {
        res.body.forEach(value => {
          value.nomActivite = value.codeLignePlan + ' ' + value.nomActivite;
        });
      }
      this.activites = res.body;
    });
  }

  activiteLoad() {
    if (this.activite) {
      this.lotService.findLotByActivite(isNaN(this.activite.id) ? 0 : this.activite.id).subscribe((res: HttpResponse<ILot[]>) => {
        this.lots = res.body;
        window.console.log("=======================Ò========1==" + this.besoinLigneBudgetaireTMP + "===================");
        // this.besoinLigneBudgetaires = this.besoinLigneBudgetaireTMP.filter(value => value.activiteId === this.activite.id);
        window.console.log("===============================1=======================");
        window.console.log(this.activite);
        this.engagements = this.engagementTMP.filter(value => value.activiteId === this.activite.id);
        window.console.log(this.engagements);
      });
    }
  }

  lotLoad() {
    if (this.lot !== undefined) {
      this.contratService.findCandidatByLot(this.lot.id).subscribe((res: HttpResponse<IContrat[]>) => {
        this.contrats = res.body;
      });
    }
  }

  add() {
    this.engagement = new Engagement();
    this.besoinLigneBudgetaires = this.besoinLigneBudgetaireTMP.filter(value => value.activiteId === this.activite.id);
    window.console.log("===============================3");
    window.console.log(this.besoinLigneBudgetaires);
    window.console.log("===============================3");
   // this.ligneBudget = new LigneBudgetaire();
    this.contrat = new Contrat();
    this.lot = new Lot();
    this.besoinLigneBudgetaire.montant = 0;
    this.display = true;
    this.display1 = true;
    this.contMontant = 0;
  }

  total(mont, besoi) {
    window.console.log("salut");
    window.console.log(besoi);
    this.engagementLigneBudgetaireService.montantEngage(besoi.id).subscribe((res: HttpResponse<number>) => {
      this.montEng = res.body;
      window.console.log("===========================");
      window.console.log(this.montEng);
      window.console.log("============================");

        window.console.log("ù$$$$$$$$$$$$$$$$$$$$$$$$$$$ù");
        window.console.log(besoi.id);
        window.console.log("ù$$$$$$$$$$$$$$$$$$$$$$$$$$$ù");
        this.contMontant = this.contMontant + mont;
        this.besoins.push(besoi);
        window.console.log(this.besoins);
        const engagement: IEngagementLigneBudgetaire = new EngagementLigneBudgetaire();
        engagement.ligneBudgetaireId = besoi.id;
        engagement.montantEngageLigne = mont;
        this.engage.push(engagement);
    });

  }

  modif(enga: IEngagement) {
    if (enga) {
      this.engagement = enga;
      window.console.log("===============================4");
      window.console.log(enga);
      window.console.log("===============================4");
      this.contrat = this.contrats.find(value => value.id === enga.contratId);
      // this.ligneBudget = this.ligneBudgets.find(value => value.id === enga.ligneBudgetaireId);
      this.lot = this.lots.find(value => value.id === enga.lotId);
      /* this.engagementLigneBudgetaireService.findAllBeoin(enga.id, enga.activiteId).subscribe((res: HttpResponse<IBesoinLigneBudgetaire[]>) => {
        this.besoinLigneBudgetaires = res.body;
      }); */
      this.engagementLigneBudgetaireService.findAllLigneByEngagement(enga.id).subscribe((res: HttpResponse<IEngagementLigneBudgetaire[]>) => {
        this.engagementLigneBudgetaires = res.body;
        window.console.log("===============================50");
        window.console.log(this.engagementLigneBudgetaires);
        window.console.log("===============================50");
        this.contMontant = enga.montantEngage;
        this.display = true;
        this.display1 = false;
      });

    }
  }

  totalModif(mont, lignes) {
    window.console.log(this.engagementLigneBudgetaires);
    this.contMontant = 0;
    this.engagementLigneBudgetaires.forEach(c => {
      if (c.id === lignes.id) {
        this.contMontant = this.contMontant + mont;
      } else
        this.contMontant = this.contMontant + c.montantEngageLigne;
    });

    this.besoins.push(lignes);

    const engagement: IEngagementLigneBudgetaire = lignes;
    engagement.id = lignes.id;
    engagement.montantEngageLigne = mont;
    this.engage.push(engagement);

    window.console.log("salut");
    window.console.log(this.engage);
  }

  save() {
    this.contrat = this.contrats[0];
    this.engagement.contratId = this.contrat.id;
    this.engagement.montantEngage = this.contMontant;

    if (this.activite.id !== undefined) {
      this.engagement.ligneBudget = this.besoins;
      this.engagement.activiteId = this.activite.id;
      this.engagement.lotId = this.lot.id;
      this.engagement.engagementLigneBudgetaires = this.engage;
    }
    window.console.log("===================1");
    window.console.log(this.engagement);
    window.console.log("===================2");
    if (this.engagement.id !== undefined) {
       this.engagementService.update(this.engagement).subscribe(() => {
         this.loadAll();
         this.display = false;
         this.showMessage('success', 'Modification', 'Modification effectuée avec succès !', '1');
       }, () => this.showMessage('error', 'Modification', 'Echec de la modification !', '1')
       );
     } else {
       this.engagementService.create(this.engagement).subscribe(() =>
       {
         this.loadAll();
         this.display = false;
         this.showMessage('success', 'Ajout', 'Engagement ajouté avec succés !', '1');
       }, () =>  this.showMessage('error', 'Ajout', "Echec de l'ajout !", '1')
       );
     }
  }

  sup(modif: IEngagement) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (modif === null) {
          return;
        } else {
          modif.deleted = true;
            this.engagementService.update(modif).subscribe(
            () => {
              this.loadAll();
              this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !', '1');
            },
            () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !', '1')
          );
        }
      }
    });
  }

  showMessage(sever: string, sum: string, det: string, myKey: string) {
    this.messageService.add({
      severity: sever,
      summary: sum,
      detail: det,
      key: myKey
    });
  }
}
