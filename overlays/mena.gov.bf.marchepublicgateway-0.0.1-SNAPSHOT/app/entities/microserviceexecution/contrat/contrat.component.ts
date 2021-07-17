import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiDataUtils, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {Contrat, IContrat} from 'app/shared/model/microserviceexecution/contrat.model';

import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {ContratService} from './contrat.service';
import {IExerciceBudgetaire} from "app/shared/model/microserviceppm/exercice-budgetaire.model";
import {ILot, Lot} from "app/shared/model/microservicedaccam/lot.model";
import {IActivite} from "app/shared/model/microserviceppm/activite.model";
import {MessageService, ConfirmationService} from "primeng/api";
import {CandidatLot, ICandidatLot} from "app/shared/model/microservicedaccam/candidat-lot.model";
import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {ExerciceBudgetaireService} from "app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service";
import {LotService} from "app/entities/microservicedaccam/lot/lot.service";
import {CandidatLotService} from "app/entities/microservicedaccam/candidat-lot/candidat-lot.service";
import {IDeliberation} from "app/shared/model/microservicedaccam/deliberation.model";
import {IPenalite, Penalite} from "app/shared/model/microserviceexecution/penalite.model";
import {IStatutExecution, StatutExecution} from "app/shared/model/microserviceexecution/statut-execution.model";
import {Contentieux, IContentieux} from "app/shared/model/microserviceexecution/contentieux.model";
import {Avenant, IAvenant} from "app/shared/model/microserviceexecution/avenant.model";
import {ILiquidation, Liquidation} from "app/shared/model/microserviceexecution/liquidation.model";
import {CandidatCautionLot, ICandidatCautionLot} from "app/shared/model/microservicedaccam/candidatCautionLot.model";
import {PenaliteService} from "app/entities/microserviceexecution/penalite/penalite.service";
import {StatutExecutionService} from "app/entities/microserviceexecution/statut-execution/statut-execution.service";
import {AvenantService} from "app/entities/microserviceexecution/avenant/avenant.service";
import {ContentieuxService} from "app/entities/microserviceexecution/contentieux/contentieux.service";
import {LiquidationService} from "app/entities/microserviceexecution/liquidation/liquidation.service";
import {CandidatCautionLotService} from "app/entities/microservicedaccam/candidatCautionLot/candidatCautionLot.service";
import {ITypeAvenant, TypeAvenant} from "app/shared/model/microserviceexecution/type-avenant.model";
import {TypeAvenantService} from "app/entities/microserviceexecution/type-avenant/type-avenant.service";
import {Fichier, IFichier} from 'app/entities/file-manager/file-menager.model';
import {DataUtils} from 'app/entities/file-manager/dataUtils';
import {TypeDossier} from "app/shared/model/enumerations/typeDossier";
import {FileMenagerService} from "app/entities/file-manager/file-menager.service";
import {IOrdreService, OrdreService} from "app/shared/model/microserviceexecution/ordre-service.model";
import {OrdreServiceService} from "app/entities/microserviceexecution/ordreService/ordreService.service";
import {Engagement, IEngagement} from "app/shared/model/microserviceexecution/engagement.model";
import {
  BesoinLigneBudgetaire,
  IBesoinLigneBudgetaire
} from "app/shared/model/microserviceppm/besoin-ligne-budgetaire.model";
import {ILigneBudgetaire, LigneBudgetaire} from "app/shared/model/microserviceppm/ligne-budgetaire.model";
import {
  EngagementLigneBudgetaire,
  IEngagementLigneBudgetaire
} from "app/shared/model/microserviceexecution/engagementLigneBudgetaire.model";
import {EngagementService} from "app/entities/microserviceexecution/engagement/engagement.service";
import {BesoinLigneBudgetaireService} from "app/entities/microserviceppm/besoin-ligne-budgetaire/besoin-ligne-budgetaire.service";
import {EngagementLigneBudgetaireService} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.service";

@Component({
  selector: 'jhi-contrat',
  templateUrl: './contrat.component.html',
  styleUrls: ['./contrat.scss']
})
export class ContratComponent implements OnInit, OnDestroy {
  contrats: IContrat[];
  contratbyattributaire: IContrat[];
  contratSelected: IContrat[];
  contrat: IContrat;
  contratajout: IContrat;
  contrat12: IContrat;
  contrat1: IContrat;
  contratTMP: IContrat;
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  activites: IActivite[];
  activite: IActivite;
  penalite: IPenalite;
  penaliteTMP: IPenalite;
  ordreServiceTMP: OrdreService;
  penalites: IPenalite[];
  ordreservices: IOrdreService[];
  ordreservice: IOrdreService;
  deliberation: IDeliberation;
  candidatLots: ICandidatLot[];
  candidatLots1: ICandidatLot[];
  canditLot: ICandidatLot = new  CandidatLot();
  candidatLotSelected: ICandidatLot[];
  candidatLot: ICandidatLot;
  candidatLot12: ICandidatLot;
  listeLots: ICandidatLot[] = [];
  cautionCandidatLots: ICandidatCautionLot[];
  cautionCandidatLot: ICandidatCautionLot;
  cautionCandidatLotTMP: ICandidatCautionLot;
  statusExecution: IStatutExecution;
  statusExecutionTMP: IStatutExecution;
  statusExecutions: IStatutExecution[];
  contentieux: IContentieux[];
  contentieu: IContentieux;
  contentieuTMP: IContentieux;
  avenants: IAvenant[];
  typeAvenants: ITypeAvenant[];
  avenant: IAvenant;
  avenantTMP: IAvenant;
  liquidations: ILiquidation[];
  liquidation: ILiquidation;
  liquidationTMP: ILiquidation;
  lot: ILot;
  lots: ILot[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  dateDp: any;
  dateDpliquidation: any;
  dateDpcaution: any;
  dateDpavenant: any;
  dateDpdebut: any;
  dateDpfin: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  display: boolean;
  nomcandidat?: string;
  isSaving: boolean;
  displayPenalite: boolean;
  displaystatutexecution: boolean;
  displaycontentieux: boolean;
  displaysavenant: boolean;
  displayliquidation: boolean;
  displaycautionCandidat: boolean;
  displayTablePenalite: boolean;
  displayTableStatutExecution: boolean;
  displayTableContentieux: boolean;
  displayTableAvenant: boolean;
  displayTableLiquidation: boolean;
  displayresiliation: boolean;
  filesListe: FileList;
  modif: boolean;
  ajouterTypeAvenant: boolean;
  fileListe: FileList;
  fichiers: FileList;
  files: IFichier[];
  file: IFichier;
  showFicModal: boolean;
  showFicModalcontrat: boolean;
  showFicModalstatutexecution: boolean;
  showFicModalcontentieux: boolean;
  showFicModalavenant: boolean;
  showFicModalliquidation: boolean;
  showFicModalordreService: boolean;
  showFicModalcaution: boolean;
  displayContrat: boolean;
  isLoading: boolean;
  displayEn: boolean;
  cols: any[];

  displayOrdre: boolean;

  showlistLot: boolean;
  dataFiles: Fichier[];
  engagement: IEngagement;
  engagements: IEngagement[];
  engagementTMP: IEngagement[];
  besoinLigneBudgetaire: IBesoinLigneBudgetaire;
  besoinLigneBudgetaires: IBesoinLigneBudgetaire[];
  besoinLigneBudgetaireTMP: IBesoinLigneBudgetaire[];
  besoins: ILigneBudgetaire[];
  engagementLigneBudgetaire: IEngagementLigneBudgetaire;
  engagementLigneBudgetaires: IEngagementLigneBudgetaire[];
  engage: IEngagementLigneBudgetaire[];
  ligneBudgetaires: ILigneBudgetaire[];
  ligneBudget: ILigneBudgetaire;
  display1: boolean;
  contMontant: number;
  montEng: number;
  contratEn?: IContrat;
  lotEn?: ILot;


  constructor(
    protected engagementLigneBudgetaireService: EngagementLigneBudgetaireService,
    protected engagementService: EngagementService,
    protected besoinLigneBudgetaireService: BesoinLigneBudgetaireService,
    protected contratService: ContratService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected activiteService: ActiviteService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected fileManagerService: FileMenagerService,
    protected candidatLotService: CandidatLotService,
    protected lotService: LotService,
    protected penaliteService: PenaliteService,
    protected statusexecutionService: StatutExecutionService,
    protected avenantService: AvenantService,
    protected contentieuxService: ContentieuxService,
    protected liquidationService: LiquidationService,
    protected candidatCautionService: CandidatCautionLotService,
    protected typeAvenantService: TypeAvenantService,
    protected ordreserviceService: OrdreServiceService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected dataUtils: JhiDataUtils,
    protected fileUtils: DataUtils
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.contratService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContrat[]>) => this.paginateContrats(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/contrat'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/contrat',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  loadActivite() {
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
    });
    this.candidatLotService.findListAttributaireByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ICandidatLot[]>) => {this.candidatLots = res.body;
      window.console.log(this.candidatLots);
    });
  }

  associerLotAContrat(){
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {this.lots = res.body;});

   // this.candidatLotService.findListAttributaireByLot(this.getActiviteId(), this.candidatLot.candidatId).subscribe((res: HttpResponse<ICandidatLot[]>) => {this.candidatLots = res.body;});
    this.candidatLotService.findAttributaireByLotCandidat(this.getLotId()).subscribe((res: HttpResponse<ICandidatLot>) => {this.candidatLot = res.body;});

    this.candidatLotService.findListAttributaireByActivite(this.getActiviteId()).subscribe((ress: HttpResponse<ICandidatLot[]>) => {this.listeLots = ress.body;
      window.console.log(this.listeLots);

    });
    this.showlistLot = true;
  }

  loadAllAtributaireListbyActivite(){
    // this.candidatLotService.findListAttributaireByLot(this.getActiviteId(), this.candidatLot.candidatId).subscribe((res: HttpResponse<ICandidatLot[]>) => {this.candidatLots = res.body;
    // });
    this.candidatLotService.findAttributaireByLotCandidat(this.getLotId()).subscribe((res: HttpResponse<ICandidatLot>) => {this.candidatLot = res.body;});

  }


  loadContrat1() {
    window.console.log("============");
    window.console.log(this.contrat12.candidatLot);
    window.console.log("============");
    this.ordreserviceService.findContratByCandidat(this.contrat12.candidatLot.candidatId).subscribe((res: HttpResponse<IContrat[]>)=>{

      this.contratbyattributaire =res.body;
    })

  }



  loadPenalite() {
    this.penaliteService.findPenaliteByContrat(this.getContratId()).subscribe((res: HttpResponse<IPenalite[]>) => {
      this.penalites = res.body;
    });
  }

  loadordreService() {
    this.ordreserviceService.query(this.getContratId()).subscribe((res: HttpResponse<IOrdreService[]>) => {
      this.penalites = res.body;
    });
  }

  loadStatutExecution() {
    this.statusexecutionService.findStatusExecutionByContrat(this.getContratId()).subscribe((res: HttpResponse<IStatutExecution[]>) => {
      this.statusExecutions = res.body;
    });
  }

  loadContentieux() {
    this.contentieuxService.findContentieuxByContrat(this.getContratId()).subscribe((res: HttpResponse<IContentieux[]>) => {
      this.contentieux = res.body;
    });
  }

  loadAvenant() {
    this.avenantService.findAvenantByContrat(this.getContratId()).subscribe((res: HttpResponse<IAvenant[]>) => {
      this.avenants = res.body;
      window.console.log('=================');
      window.console.log(this.avenants);
      window.console.log('===================');
    });
  }

  loadLiquidation() {
    this.liquidationService.findLiquidationByContrat(this.getContratId()).subscribe((res: HttpResponse<ILiquidation[]>) => {
      this.liquidations = res.body;
      window.console.log(this.liquidations);
    });
  }

  loadCandidatCautionLot() {
    window.console.log(this.cautionCandidatLots);
    this.candidatCautionService.findAllCandidatCautionBycandidatLot(this.candidatLot.id).subscribe((res: HttpResponse<ICandidatCautionLot[]>) => {
      this.cautionCandidatLots = res.body;
    });
  }

  loadOrdreService() {
    this.ordreserviceService.query().subscribe((res:HttpResponse<IOrdreService[]>)=>{
      this.ordreservices = res.body;
    })
  }

  loadTypeAvenat() {
    this.typeAvenantService.query().subscribe((res: HttpResponse<ITypeAvenant[]>) => {
      this.typeAvenants = res.body;
      window.console.log(this.typeAvenants);
    });
  }

  getContratId(): number {
    if (!this.contrat || this.contrat.id !== undefined) {
      return this.contrat.id;
    } else {
      return 0;
    }
  }

  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
    } else {
      return 0;
    }
  }
loadContratList() {
    this.contratService.findContratListBycandidatLot(this.getLotId()).subscribe((ret:HttpResponse<IContrat>)=>{
      this.contrat1 = ret.body;
    })
}

  loadContrat(id: number) {
    this.contratService.findContratBycandidatLot(id).subscribe((res: HttpResponse<IContrat>) => {
      this.contrat = res.body;
      window.console.log("=853*968520585*9639*96205");
      window.console.log(this.contrat);
    })
  }

  loadEngagement(){
    this.engagementService.query().subscribe((resEn: HttpResponse<IEngagement[]>) =>{
      this.engagements = resEn.body;
    });
  }

  loadExercicice() {
    this.activiteService.findAllByAnneeExercice(this.getExerciceId()).subscribe((res: HttpResponse<IActivite[]>) => {
      if (res.body.length > 0) {
        res.body.forEach(value => {
          value.nomActivite = value.codeLignePlan + ' ' + value.naturePrestationLibelle;
        });
      }
      this.activites = res.body;
    });
  }

  loadAttributaireByLot() {
    this.candidatLotService.findAttributaireByLotCandidat(this.getLotId()).subscribe((res: HttpResponse<ICandidatLot>) => {
      if (res.body !== null) {
        this.candidatLot = res.body;
        window.console.log("===============code=================");
         this.loadContrat(this.candidatLot.id);

        this.loadContratList();
      } else {
        this.candidatLot = res.body;
        this.loadContrat(this.candidatLot.id);
      }
    });
    this.getLotId();

  }

  getLotId(): number {
    if (this.lot !== null) {
      return this.lot.id;
    } else {
      return 0;
    }
  }

  getCandidatLotId(): number {
    if (this.candidatLot !== null) {
      return this.candidatLot.id;
    } else {
      return 0;
    }
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
  }

  ifExistContrat(): boolean {
    if (this.contrat.id) {
      return this.contrats.some(value => value.id !== this.contrat.id && value.reference === this.contrat.reference);
    } else {
      return this.contrats.some(value => value.reference === this.contrat.reference);
    }
  }

  onSaveSuccess() {
    this.isSaving = false;
    this.display = false;
    this.loadContrat(this.getCandidatLotId());
  }

  onSaveError() {
    this.isSaving = false;
  }

  showMessage(cle: string, severite: string, resume: string, detaille: string) {
    this.messageService.add({
      key: cle,
      severity: severite,
      summary: resume,
      detail: detaille
    });
  }

  saveOrdreService(){

    this.isSaving = true;
    this.ordreservice.contrats = this.contratSelected;
    if (this.ordreservice.id !== undefined) {
      this.ordreserviceService.update(this.ordreservice).subscribe((res: HttpResponse<IOrdreService>) => {
        this.isSaving = false;
        window.console.log(res.body);

        this.sucessMessage(true);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.ordreserviceService.create(this.ordreservice).subscribe((res: HttpResponse<IOrdreService>) => {
        this.isSaving = false;
        window.console.log(res.body);

        this.sucessMessage(false);
      }, () => {
        this.erroMessage(false);
      });
    }
    this.displayOrdre= false;
  }

  saveContrat1() {
    this.displayContrat = true;
    this.contratajout.candidatLots = this.candidatLotSelected;

    window.console.log("===============candidatLot===============");
    window.console.log(this.contratajout.candidatLots);
    window.console.log("==============================");

    this.contratService.saveAllContrat(this.contratajout).subscribe((re:HttpResponse<IContrat>)=>{

      window.console.log("==============================");
      window.console.log(re.body);
      window.console.log("==============================");
    });
  }

  saveContrat() {
    this.isSaving = true;
    this.contrat.candidatLotId = this.candidatLot.id;
    if (this.contrat.id !== undefined) {
      this.contratService.update(this.contrat).subscribe((res: HttpResponse<IContrat>) => {
        this.isSaving = false;
        this.fichiers = undefined;
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.contratService.create(this.contrat).subscribe((res: HttpResponse<IContrat>) => {
        this.isSaving = false;
        this.fichiers = undefined;
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }
  }

  supprimerContrat(contrat: IContrat) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (contrat === null) {
          return;
        } else {
          contrat.deleted = true;
          this.contratService.delete(contrat.id).subscribe(
            () => {
              this.loadAll();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  ngOnInit() {
    this.loadAll();
    this.contrat = new Contrat();
    this.contrat1 = new Contrat();
    this.contrat12 = new Contrat();
    this.contratajout = new Contrat();
    this.activite =  null;
    this.ordreservice = new OrdreService();
    this.ordreservices = [];
    this.contratbyattributaire = [];
    this.contratSelected = [];
    this.candidatLots1 = [];
    this.penalite = new Penalite();
    this.statusExecution = new StatutExecution();
    this.contentieu = new Contentieux();
    this.avenant = new Avenant();
    this.avenant.typeAvenant = new TypeAvenant();
    this.liquidation = new Liquidation();
    this.cautionCandidatLot = new CandidatCautionLot();
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new CandidatLot();
    this.lot = null;
    this.cols = this.candidatLots;
    this.contrat.candidatLot = new CandidatLot();
    this.loadTypeAvenat();
    this.loadOrdreService();
    this.ajouterTypeAvenant = false;
    this.displayTablePenalite = false;
    this.displayTableStatutExecution = false;
    this.displayTableContentieux = false;
    this.displayTableAvenant = false;
    this.displayTableLiquidation = false;
    this.besoinLigneBudgetaireService.query().subscribe((res: HttpResponse<IBesoinLigneBudgetaire[]>) => {
      this.besoinLigneBudgetaires = null;
      this.besoinLigneBudgetaireTMP = res.body;
    });
    this.exerciceBudgetaireService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });

    this.engagementService.query().subscribe((resEn: HttpResponse<IEngagement[]>) =>{
      this.engagements = resEn.body;
    });
    this.registerChangeInContrats();
    this.displayEn = false;
    this.contMontant = 0;
    this.montEng = 0;
    this.engagement = new Engagement();
    this.engagements = [];

    this.contrats = [];
    this.ligneBudget = new LigneBudgetaire();

    this.besoinLigneBudgetaire = new BesoinLigneBudgetaire();
    this.besoinLigneBudgetaires = [];
    this.besoins = [];

    this.lots = [];
    this.lot = new Lot();

    this.exercices = [];

    this.activites = [];

    this.engagementLigneBudgetaires = [];
    this.engage = [];

    this.ligneBudgetaires = [];
    this.contratEn = new Contrat();
    this.lotEn = new Lot();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  addContrat(contrat: IContrat) {
    contrat === null ? (this.contrat = new Contrat(), this.showlistLot=true) : (this.contrat = contrat);
    this.modif = !this.modif;
    this.showlistLot = false;
  }

  addPenalite(penalite: IPenalite) {
    penalite === null ? (this.penalite = new Penalite()) : (this.penalite = penalite);
    this.displayPenalite = true;
  }

  addOrdreService(ordre: IOrdreService) {

    if(ordre === null){
      this.ordreservice = new OrdreService();
    }
    else{
      this.ordreservice = ordre;
      window.console.log("contart========================" +ordre.id);

      this.ordreserviceService.findContratByOrdreService(ordre.id).subscribe((rest:HttpResponse<IContrat[]>)=>{
        this.contratbyattributaire =rest.body;

      });

      this.ordreserviceService.findCandidatLotbyContrat(this.contratbyattributaire[0].id).subscribe((ret:HttpResponse<ICandidatLot>)=>{

         this.contrat12.candidatLot = this.candidatLots.find(a=>a.id === ret.body.id);
        window.console.log("===========bien=============");
        window.console.log("===========CONId=============" +ret.body);
        window.console.log("===========bien1=============");

      });
      this.candidatLotService.findContratByCandidat(ordre.contratId).subscribe((res:HttpResponse<ICandidatLot[]>)=>this.candidatLots1 =res.body);

      this.contrat.candidatLot= this.candidatLots1[0];

      window.console.log("valeurcandidat"+this.contrat.candidatLot);

    }

    this.displayOrdre = true;
    // this.loadContrat1();
    //  this.associerLotAContrat();

  }

  addstatusExecution(status: IStatutExecution) {
    status === null ? (this.statusExecution = new StatutExecution()) : (this.statusExecution = status);
    this.displaystatutexecution = true;
  }

  addContentieux(contentieux: IContentieux) {
    contentieux === null ? (this.contentieu = new Contentieux()) : (this.contentieu = contentieux);
    this.displaycontentieux = true;

  }

  addAvenant(avenant: IAvenant) {
    if (avenant !== null) {
      this.avenant = avenant;
    } else {
      this.avenant = new Avenant();
      this.avenant.typeAvenant = new TypeAvenant();
    }
    // avenant === null ? (this.avenant = new Avenant()) : (this.avenant = avenant);
    this.displaysavenant = true;

  }

  addLiquidation(liquidation: ILiquidation) {
    liquidation === null ? (this.liquidation = new Liquidation()) : (this.liquidation = liquidation);
    this.displayliquidation = true;
  }
  addResiliation(statusExecution: IStatutExecution) {
    statusExecution === null ? (this.statusExecution = new StatutExecution()) : (this.statusExecution = statusExecution);
    this.displayresiliation = true;
  }

  addCaution(cautionCandidatLot: ICandidatCautionLot) {
    cautionCandidatLot === null ? (this.cautionCandidatLot = new CandidatCautionLot()) : (this.cautionCandidatLot = cautionCandidatLot);
    this.displaycautionCandidat = true;
  }

  selectTypeAvenant(): void {
    if (!this.ajouterTypeAvenant) {
      this.avenant.typeAvenant = new TypeAvenant();
      this.avenant.typeAvenantId = undefined;
      this.ajouterTypeAvenant = true;
    } else {
      this.avenant.typeAvenant = new TypeAvenant();
      this.ajouterTypeAvenant = false;
    }
  }

  sucessMessage(etat: boolean) {
    if (!etat) {
      this.showMessage('myKey', 'success', 'Enregistrement', 'Enregistrement effectué avec succès !');
    } else {
      this.showMessage('myKey', 'success', 'Mise à jour', 'Mise à jour effectuée avec succès !');
    }
  }

  erroMessage(etat: boolean) {
    if (!etat) {
      this.showMessage('myKey', 'success', 'Enregistrement', 'Echec de l\'enregistrement !');
    } else {
      this.showMessage('myKey', 'success', 'Mise à jour', 'Echec de la mise à jour !');
    }
  }

  savePenalite() {
    this.isSaving = true;
    this.penalite.contratId = this.contrat.id;
    if (this.penalite.id !== undefined) {
      this.penaliteService.update(this.penalite).subscribe((res: HttpResponse<IPenalite>) => {
        this.displayPenalite = false;
        this.isSaving = false;
        this.fichiers = undefined;
        this.loadPenalite();
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.penaliteService.create(this.penalite).subscribe((res: HttpResponse<IPenalite>) => {
        this.displayPenalite = false;
        this.isSaving = false;
        this.fichiers = undefined;
        this.loadPenalite();
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }
  }

  supprimerPenalite(penalite: IPenalite) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (penalite === null) {
          return;
        } else {
          penalite.deleted = true;
          this.penaliteService.delete(penalite.id).subscribe(
            () => {
              this.loadAll();
              this.loadPenalite();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  supprimerOrdre(ordre: IOrdreService) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (ordre === null) {
          return;
        } else {
          ordre.deleted = true;
          this.ordreserviceService.delete(ordre.id).subscribe(
            () => {
              this.loadAll();
              this.loadordreService();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
    this.loadOrdreService();
  }

  saveStatusExecution() {
    this.isSaving = true;
    this.statusExecution.contratId = this.contrat.id;
    if (this.statusExecution.id !== undefined) {
      this.statusexecutionService.update(this.statusExecution).subscribe((res: HttpResponse<IStatutExecution>) => {
        this.loadStatutExecution();
        this.sucessMessage(true);
        window.console.log(res.body);
        this.isSaving = false;
        this.displaystatutexecution = false;
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.statusexecutionService.create(this.statusExecution).subscribe((res: HttpResponse<IStatutExecution>) => {
        this.loadStatutExecution();
        this.sucessMessage(false);
        window.console.log(res.body);
        this.isSaving = false;
        this.displaystatutexecution = false;
      }, () => {
        this.erroMessage(false);
      });
    }
  }

  supprimerstatusExecution(statusExecution: IStatutExecution) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (statusExecution === null) {
          return;
        } else {
          statusExecution.deleted = true;
          this.statusexecutionService.delete(statusExecution.id).subscribe(
            () => {
              this.loadAll();
              this.loadStatutExecution();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  saveContentieu() {
    this.isSaving = true;
    this.contentieu.contratId = this.contrat.id;
    if (this.contentieu.id !== undefined) {
      this.contentieuxService.update(this.contentieu).subscribe((res: HttpResponse<IContentieux>) => {
        this.isSaving = false;
        this.displaycontentieux = false;
        this.loadContentieux();
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.contentieuxService.create(this.contentieu).subscribe((res: HttpResponse<IContentieux>) => {
        this.isSaving = false;
        this.displaycontentieux = false;
        this.loadContentieux();
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }
  }

  supprimercontentieu(contentieux: IContentieux) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (contentieux === null) {
          return;
        } else {
          contentieux.deleted = true;
          this.contentieuxService.delete(contentieux.id).subscribe(
            () => {
              this.loadAll();
              this.loadContentieux();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  saveAvenant() {
    this.isSaving = true;
    this.avenant.contratId = this.contrat.id;
    if (this.avenant.id !== undefined) {
      this.avenantService.update(this.avenant).subscribe((res: HttpResponse<IAvenant>) => {
        this.isSaving = false;
        this.ajouterTypeAvenant = false;
        this.displaysavenant = false;
        this.loadTypeAvenat();
        this.loadAvenant();
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.avenantService.create(this.avenant).subscribe((res: HttpResponse<IAvenant>) => {
        this.isSaving = false;
        this.ajouterTypeAvenant = false;
        this.displaysavenant = false;
        this.loadTypeAvenat();
        this.loadAvenant();
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }

  }

  supprimerAvenant(avenant: IAvenant) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (avenant === null) {
          return;
        } else {
          avenant.deleted = true;
          this.avenantService.delete(avenant.id).subscribe(
            () => {
              this.loadAll();
              this.loadAvenant();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  saveLiquidation() {
    this.isSaving = true;
    this.liquidation.contratId = this.contrat.id;
    this.liquidation.activiteId = this.activite.id;
    if (this.liquidation.id !== undefined) {
      this.liquidationService.update(this.liquidation).subscribe((res: HttpResponse<ILiquidation>) => {
        this.isSaving = false;
        this.displayliquidation = false;
        this.loadLiquidation();
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.liquidationService.create(this.liquidation).subscribe((res: HttpResponse<ILiquidation>) => {
        this.isSaving = false;
        this.displayliquidation = false;
        this.loadLiquidation();
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }

  }

  supprimerLiquidation(liquidation: ILiquidation) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (liquidation === null) {
          return;
        } else {
          liquidation.deleted = true;
          this.liquidationService.delete(liquidation.id).subscribe(
            () => {
              this.loadAll();
              this.loadLiquidation();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  saveCaution() {
    this.isSaving = true;
    this.cautionCandidatLot.candidatLotId = this.candidatLot.id;
    if (this.cautionCandidatLot.id !== undefined) {
      this.candidatCautionService.update(this.cautionCandidatLot).subscribe((res: HttpResponse<ICandidatCautionLot>) => {
        this.isSaving = false;
        this.displaycautionCandidat = false;
        this.loadCandidatCautionLot();
        this.sucessMessage(true);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(true);
      });
    } else {
      this.candidatCautionService.create(this.cautionCandidatLot).subscribe((res: HttpResponse<ICandidatCautionLot>) => {
        this.isSaving = false;
        this.displaycautionCandidat = false;
        this.loadCandidatCautionLot();
        this.sucessMessage(false);
        window.console.log(res.body);
      }, () => {
        this.erroMessage(false);
      });
    }
  }
  showcontrat (){
    this.displayContrat = true;
  }

  supprimerCaution(cautionCandidatLot: ICandidatCautionLot) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (cautionCandidatLot === null) {
          return;
        } else {
          cautionCandidatLot.deleted = true;
          this.candidatCautionService.delete(cautionCandidatLot.id).subscribe(
            () => {
              this.loadAll();
              this.loadCandidatCautionLot();
              this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  annulerStatutExecution() {
    this.statusExecution = new StatutExecution();
    this.displaystatutexecution = false;
    this.loadStatutExecution();
  }
  annulerContrat() {
    // this.showlistLot = false;
    this.displayContrat = false;
    this.contrat = new Contrat();
  }
  annulerResiliation() {
    this.statusExecution = new StatutExecution();
    this.displayresiliation = false;
    this.loadStatutExecution();
  }
  annulerOrdreService() {
    this.ordreservice = new OrdreService();
    this.displayOrdre = false;
  }

  annulerContentieu() {
    this.contentieu = new Contentieux();
    this.displaycontentieux = false;
    this.loadContentieux();
  }

  annulerAvenant() {
    this.avenant = new Avenant();
    this.avenant.typeAvenant = new TypeAvenant();
    this.displaysavenant = false;
    this.loadAvenant();
  }

  annulerPenalite() {
    this.penalite = new Penalite();
    this.displayPenalite = false;
    this.loadPenalite();
  }

  annulerLiquidation() {
    this.liquidation = new Liquidation();
    this.displayliquidation = false;
    this.loadLiquidation();
  }

  annulerCaution() {
    this.cautionCandidatLot = new CandidatCautionLot();
    this.displaycautionCandidat = false;
    this.loadCandidatCautionLot();
  }

  trackId(index: number, item: IContrat) {
    return item.id;
  }

  registerChangeInContrats() {
    this.eventSubscriber = this.eventManager.subscribe('contratListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  tabViewChange(event) {
    if (event.index === 0) {
      if (this.contrat.id!==undefined) {
        this.loadPenalite();
      }
    } if (event.index === 1) {
      if (this.contrat.id!==undefined) {
        this.loadCandidatCautionLot();
      }
    } else if (event.index === 2) {
      this.loadOrdreService();
    } else if (event.index === 3) {
      this.loadEngagement();
    } else if (event.index === 4) {
      this.loadLiquidation();
    } else if (event.index === 5) {
      this.loadAvenant();
    } else if (event.index === 6) {
      this.loadContentieux();
    }
    window.console.log(event);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>) {
    result.subscribe(
      () => {
        this.showMessage('myKey', 'success', 'ENREGISTREMENT', 'Un contrat ajouté avec succès!');
        this.onSaveSuccess();
      },
      () => {
        this.showMessage('myKey', 'error', 'ENREGISTREMENT', "Echec d'enregistrement!");
        this.onSaveError();
      }
    );
  }

  protected paginateContrats(data: IContrat[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contrats = data;
  }

  sucessMessageResiliation(etat: boolean) {
    if (!etat) {
      this.showMessage('myKey', 'success', 'Enregistrement', 'Marché résilié avec succès !');
    } else {
      this.showMessage('myKey', 'success', 'Mise à jour', 'Marché non résilié avec succès !');
    }
  }


  saveResiliation(statutExecution: IStatutExecution) {
    this.displayresiliation =false;
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir résilier ce marché ?',
      accept: () => {
        if (statutExecution === null) {
          return;
        } else {
          statutExecution.contratId = this.contrat.id;
          statutExecution.contratResilier = true;
          this.contrat.resilierContrat = true;
          window.console.log(statutExecution);
          this.contratService.changeStatus(statutExecution).subscribe(
            () => {
              this.loadStatutExecution();
              this.sucessMessageResiliation(false);
            },
            () => this.sucessMessageResiliation(true)

          );
        }
      }
    });
  }

  supprimer() {

  }
  /*
  * fichier contat
  * */
  setFileDataContrat(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.contrat.files = this.files;
    }
  }


  getFilesContrat(contrat: IContrat) {
    this.isLoading = true;
    this.contratTMP = contrat;
    this.contratService.find(contrat.id).subscribe((res: HttpResponse<IContrat>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalcontrat = true;
  }

  setFileAddDataContrat(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.contratTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }


  retirerFihierContrat(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.contratTMP.id, TypeDossier.CONTRAT, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesContrat(this.contratTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  addFileContrat(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.contratService.update(this.contratTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesContrat(this.contratTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }
  dowloadFichier(file) {
    this.dataUtils.downloadFile(file.fileContentType, file.file, file.fileName);
  }


  /*
  * fichier ordre
  * */
  addFileOrdre(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.ordreserviceService.update(this.ordreServiceTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesOrdre(this.ordreServiceTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }

  setFileAddDataOrdre(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.contratTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  /*
* fichier penalite
* */
  setFileDataPenalite(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.penalite.files = this.files;
    }
  }

  getFilesPenalite(penalite: IPenalite) {
    this.isLoading = true;
    this.penaliteTMP = penalite;
    this.penaliteService.find(penalite.id).subscribe((res: HttpResponse<IPenalite>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModal = true;
  }

  getFilesOrdre(ordreService: IOrdreService) {
    this.isLoading = true;
    this.ordreServiceTMP = ordreService;
    this.ordreserviceService.find(ordreService.id).subscribe((res: HttpResponse<IOrdreService>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalordreService = true;
  }

  retirerFihierPenalite(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.penaliteTMP.id, TypeDossier.PENALITE, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesPenalite(this.penaliteTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataPenalite(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.penaliteTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFilePenalite(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.penaliteService.update(this.penaliteTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesPenalite(this.penaliteTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }

  /*
* fichier statutexecution
* */
  setFileDataStatutExecution(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.statusExecution.files = this.files;
    }
  }

  getFilesStatutExecution(statutExecution: IStatutExecution) {
    this.isLoading = true;
    this.statusExecutionTMP = statutExecution;
    this.statusexecutionService.find(statutExecution.id).subscribe((res: HttpResponse<IStatutExecution>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalstatutexecution = true;
  }

  retirerFihierStatutExecution(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.statusExecutionTMP.id, TypeDossier.STATUTEXECUTION, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesStatutExecution(this.statusExecutionTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataStatutExecution(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.statusExecutionTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFileStatutExecution(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.statusexecutionService.update(this.statusExecutionTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesStatutExecution(this.statusExecutionTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }
  /*
* fichier contentieux
* */
  setFileDataContentieux(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.contentieu.files = this.files;
    }
  }

  getFilesContentieux(contentieux: ICandidatCautionLot) {
    this.isLoading = true;
    this.contentieuTMP = contentieux;
    this.contentieuxService.find(contentieux.id).subscribe((res: HttpResponse<IContentieux>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalcontentieux = true;
  }

  retirerFihierContentizux(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.contentieuTMP.id, TypeDossier.CONTENTIEUX, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesContentieux(this.contentieuTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataContentieux(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.contentieuTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFileContentieux(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.contentieuxService.update(this.contentieuTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesContentieux(this.contentieuTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }
  /*
* fichier Avenant
* */
  setFileDataAvenant(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.avenant.files = this.files;
    }
  }

  getFilesAvenant(avenant: IAvenant) {
    this.isLoading = true;
    this.avenantTMP = avenant;
    this.avenantService.find(avenant.id).subscribe((res: HttpResponse<IAvenant>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalavenant = true;
  }

  retirerFihierAvenant(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.avenantTMP.id, TypeDossier.AVENANT, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesAvenant(this.avenantTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataAvenant(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.avenantTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFileAvenant(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.avenantService.update(this.avenantTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesAvenant(this.avenantTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }
  /*
* fichier Liquidation
* */
  setFileDataLiquidation(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.liquidation.files = this.files;
    }
  }

  getFilesLiquidation(liquidation: ILiquidation) {
    this.isLoading = true;
    this.liquidationTMP = liquidation;
    this.liquidationService.find(liquidation.id).subscribe((res: HttpResponse<ILiquidation>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalliquidation = true;
  }

  retirerFihierLiquidation(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.liquidationTMP.id, TypeDossier.LIQUIDATION, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesLiquidation(this.liquidationTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  retirerFihierOrdr(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.ordreServiceTMP.id, TypeDossier.LIQUIDATION, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesLiquidation(this.liquidationTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataLiquidation(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.liquidationTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFileLiquidation(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.liquidationService.update(this.liquidationTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesLiquidation(this.liquidationTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }

  addFileordreService(vale) {
    if (vale != null && this.fichiers.length !== 0) {
      this.isSaving = true;
      this.ordreserviceService.update(this.ordreServiceTMP).subscribe(() => {
        this.fichiers = undefined;
        this.getFilesOrdre(this.ordreServiceTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }
  /*
* fichier CandidatCautionLot
* */
  setFileDataCandidatCautionLot(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.cautionCandidatLot.files = this.files;
    }
  }

  getFilesCandidatCautionLot(candidatCautionLot: ICandidatCautionLot) {
    this.isLoading = true;
    this.cautionCandidatLotTMP = candidatCautionLot;
    this.candidatCautionService.find(candidatCautionLot.id).subscribe((res: HttpResponse<ICandidatCautionLot>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModalcaution = true;
  }

  retirerFihierCandidatCautionLot(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.cautionCandidatLotTMP.id, TypeDossier.CANDIDATCAUTIONLOT, file.fileName).subscribe(() => {
          this.showMessage('mykey','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFilesCandidatCautionLot(this.cautionCandidatLotTMP);
        }, () => {
          this.showMessage('mykey','warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddDataCandidatCautionLot(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.cautionCandidatLotTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  setFileAddDataOrdreService(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.ordreservice.files = this.files;
    }
  }


  addFileCandidatCautionLot(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.candidatCautionService.update(this.cautionCandidatLotTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFilesCandidatCautionLot(this.cautionCandidatLotTMP);
        this.isSaving = false;
        this.showMessage('mykey','success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.showMessage('mykey','warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }

  saveAll() {
    this.contrat.candidatLots = this.candidatLotSelected;
    this.contratService.saveAll(this.contrat).subscribe(
      () => {
        this.loadAllAtributaireListbyActivite();
        this.showMessage('myKey1', 'success', 'Enregistrement', 'Enregistrement effectuée avec succès !');
      },
      () => this.showMessage('myKey1', 'error', 'SUPPRESSION', 'Echec de l\'enregistrement !')
    );
  }


  add() {
    this.engagement = new Engagement();
    this.besoinLigneBudgetaires = this.besoinLigneBudgetaireTMP.filter(value => value.activiteId === this.activite.id);
    window.console.log("===============================3");
    window.console.log(this.besoinLigneBudgetaires);
    window.console.log("===============================3");
    // this.ligneBudget = new LigneBudgetaire();
    this.contratEn = new Contrat();
    this.lotEn = new Lot();
    this.besoinLigneBudgetaire.montant = 0;
    this.displayEn = true;
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

  modifEn(enga: IEngagement) {
    if (enga) {
      this.engagement = enga;
      window.console.log("===============================4");
      window.console.log(enga);
      window.console.log("===============================4");
      this.contratEn = this.contrats.find(value => value.id === enga.contratId);
      // this.ligneBudget = this.ligneBudgets.find(value => value.id === enga.ligneBudgetaireId);
      this.lotEn = this.lots.find(value => value.id === enga.lotId);
      /* this.engagementLigneBudgetaireService.findAllBeoin(enga.id, enga.activiteId).subscribe((res: HttpResponse<IBesoinLigneBudgetaire[]>) => {
        this.besoinLigneBudgetaires = res.body;
      }); */
      this.engagementLigneBudgetaireService.findAllLigneByEngagement(enga.id).subscribe((res: HttpResponse<IEngagementLigneBudgetaire[]>) => {
        this.engagementLigneBudgetaires = res.body;
        window.console.log("===============================50");
        window.console.log(this.engagementLigneBudgetaires);
        window.console.log("===============================50");
        this.contMontant = enga.montantEngage;
        this.displayEn = true;
        this.display1 = false;
      });

    }
  }
  annulerEn(){
    this.displayEn = false;
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



  saveEn() {
    this.contrat = this.contrats[0];
    window.console.log("===========contrat========");
    window.console.log(this.engagement);
    window.console.log("===================2");
    this.engagement.contratEn = this.contrat;
    this.engagement.lotEn = this.lot;
    this.engagement.contratId = this.contrat.id;
    this.engagement.montantEngage = this.contMontant;
// valide
    if (this.activite.id !== undefined) {
      this.engagement.ligneBudget = this.besoins;
      this.engagement.activiteId = this.activite.id;
      this.engagement.lotId = this.lot.id;
      this.engagement.engagementLigneBudgetaires = this.engage;
    }
    if (this.engagement.id !== undefined) {
      this.engagementService.update(this.engagement).subscribe(() => {
          this.loadEngagement();
          this.displayEn = false;
          this.showMessage('success', 'Modification', 'Modification effectuée avec succès !', '1');
        }, () => this.showMessage('error', 'Modification', 'Echec de la modification !', '1')
      );
    } else {
      this.engagementService.create(this.engagement).subscribe(() =>
        {
          this.loadEngagement();
          this.displayEn = false;
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
              this.loadEngagement();
              this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !', '1');
            },
            () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !', '1')
          );
        }
      }
    });
  }
}
