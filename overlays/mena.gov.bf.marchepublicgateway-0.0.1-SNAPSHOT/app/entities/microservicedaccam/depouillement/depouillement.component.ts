import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiDataUtils, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Depouillement, IDepouillement} from 'app/shared/model/microservicedaccam/depouillement.model';
import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {DepouillementService} from './depouillement.service';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {ILot} from 'app/shared/model/microservicedaccam/lot.model';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {CandidatLotService} from 'app/entities/microservicedaccam/candidat-lot/candidat-lot.service';
import {CandidatLot, ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {Candidat, ICandidat} from 'app/shared/model/microservicedaccam/candidat.model';
import {IPieceCandidat} from 'app/shared/model/microservicedaccam/piece-candidat.model';
import {ConfirmationService, MenuItem, MessageService} from 'primeng/api';
import {CandidatCautionLot, ICandidatCautionLot} from 'app/shared/model/microservicedaccam/candidatCautionLot.model';
import {TypeCautionService} from 'app/entities/microservicedaccam/type-caution/type-caution.service';
import {ITypeCaution} from 'app/shared/model/microservicedaccam/typeCaution.model';
import {Caution, ICaution} from 'app/shared/model/microservicedaccam/caution.model';
import {CautionService} from 'app/entities/microservicedaccam/caution/caution.service';
import {FileMenagerService} from 'app/entities/file-manager/file-menager.service';
import {TypeDossier} from 'app/shared/model/enumerations/typeDossier';
import {Fichier} from 'app/entities/file-manager/file-menager.model';
import {DataUtils} from 'app/entities/file-manager/dataUtils';
import {CandidatService} from 'app/entities/microservicedaccam/candidat/candidat.service';

@Component({
  selector: 'jhi-depouillement',
  templateUrl: './depouillement.component.html',
  styleUrls: ['./depouillement.scss']
})
export class DepouillementComponent implements OnInit, OnDestroy {
  depouillements: IDepouillement[];
  depouillement: IDepouillement;
  dateDp: any;
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  activites: IActivite[];
  activite: IActivite;
  lots: ILot[];
  lotsSelected: ICandidatLot[];
  display: boolean;
  displayCaution: boolean;
  isSaving: boolean;
  etat: boolean;
  displaySoumissionnaireModal: boolean;
  candidatLots: ICandidatLot[];
  candidatLot: ICandidatLot;
  soumissionnairesSelected: ICandidatLot[];
  candidatLotTemp: ICandidatLot;
  pieceCandiats: IPieceCandidat[];
  pieceCandiatsTemp: IPieceCandidat[];
  pieceCandiatsSelected: IPieceCandidat[];
  candidatCautionLot: ICandidatCautionLot;
  typeCautions: ITypeCaution[];
  cautions: Caution[];
  actif: boolean;
  infructueux: boolean;
  invalideLot: boolean;
  fichiers: FileList;
  showFicModal: boolean;
  headers: HttpHeaders;
  fileListe: FileList;
  depouillementTMP: IDepouillement;
  file: Fichier;
  files: Fichier[];
  dataFiles: Fichier[];
  isLoading: boolean;
  items: MenuItem[];
  candidats: ICandidat[];
  lotModal: boolean;
  candidatTMP: ICandidat;
  lotTMP: ILot;
  index: number;
  nbrsLots:any;

  constructor(
    protected depouillementService: DepouillementService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected exerciceService: ExerciceBudgetaireService,
    protected activiteService: ActiviteService,
    protected candidatLotService: CandidatLotService,
    protected candidatService: CandidatService,
    protected lotService: LotService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected typeCautionService: TypeCautionService,
    protected cautionService: CautionService,
    protected fileManagerService: FileMenagerService,
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

  loadAll(activiteId: number) {
    this.depouillementService
      .query(activiteId, {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDepouillement[]>) => {
        this.paginateDepouillements(res.body, res.headers)
        window.console.log('========= liste ============');
        window.console.log(this.depouillements);
        window.console.log('=====================');
      });
  }

  init() {
    this.exercice = null;
    this.activite = null;
    this.display = false;
    this.index = 0;
    this.displayCaution = false;
    this.isSaving = false;
    this.actif = true;
    this.isLoading = true;
    this.etat = false;
    this.lotModal = false;
    this.invalideLot = false;
    this.showFicModal = false;
    this.infructueux = false;
    this.displaySoumissionnaireModal = false;
    this.depouillement = new Depouillement();
    this.depouillement.candidats = [];
    this.loadAllExercice();
    this.loadAllTypeCaution();
    this.candidatLots = [];
    this.files = [];
    this.lots = [];
    this.pieceCandiats = [];
    this.pieceCandiatsTemp = [];
    this.pieceCandiatsSelected = [];
    this.soumissionnairesSelected = [];
    this.candidatLotTemp = new CandidatLot();
    this.candidatCautionLot = new CandidatCautionLot();

    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new Candidat();

    this.items = [
      {label: 'Step 1'},
      {label: 'Step 2'},
      {label: 'Step 3'},
    ];
  }

  loadAllTypeCaution() {
    this.typeCautionService.query().subscribe((res: HttpResponse<ITypeCaution[]>) => {
      this.typeCautions = res.body;
    });
  }

  ngOnInit() {
    this.init();
    this.registerChangeInDepouillements();
  }

  loadAllExercice() {
    this.exerciceService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });
  }

  exerciciceChange() {
    this.activiteService.findAllByAnneeExercice(this.getExerciceId()).subscribe((res: HttpResponse<IActivite[]>) => {
      if (res.body.length > 0) {
        res.body.forEach(value => {
          value.nomActivite = value.codeLignePlan + ' ' + value.naturePrestationLibelle;
        });
      }
      this.activites = res.body;
    });
  }

  loaAllLot(idActivite: number, status: boolean) {
    this.lotService.findLotByActiviteWithCandidat(idActivite, status).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
      window.console.log('=================');
      window.console.log(this.lots);
      window.console.log('=================');
    });
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
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

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepouillement) {
    return item.id;
  }

  registerChangeInDepouillements() {
    this.eventSubscriber = this.eventManager.subscribe('depouillementListModification', () => this.loadAll(this.getActiviteId()));
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDepouillements(data: IDepouillement[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.depouillements = data;
    if (this.depouillements.length > 0) {
      this.invalideLot = false;
    }

    window.console.log('========= liste ============');
    window.console.log(this.depouillements);
    window.console.log('=====================');
  }

  showModal() {
    this.lotsSelected = null;
    this.lots = [];
    this.depouillement = new Depouillement();
    this.depouillement.candidats = [];
    this.isSaving = false;
    this.findAllCandidatByActivite(this.getActiviteId());
    this.display = true;
  }

  initLot(activiteId: number) {
    this.candidatLotService.iniLot(activiteId).subscribe((res: HttpResponse<ICandidatLot[]>) => {
      this.candidatLots = res.body;
      if (this.candidatLots.some(value => value.lot.infructueux === true)) {
        this.invalideLot = true;
      }
    });
  }

  annuler() {
    this.display = false;
  }

  annulerSomissionaire() {
    this.displaySoumissionnaireModal = false;
    this.soumissionnairesSelected = [];
  }

  filterDepouillement() {
    this.loadAll(this.getActiviteId());
    this.initLot(this.getActiviteId());
  }

  findAllCandidatByActivite(activiteId: number) {
    if (this.depouillement.id === undefined) {
      /* this.candidatLotService.findAllByCandidatByActivte(activiteId).subscribe((res: HttpResponse<ICandidatLot[]>) => {
       this.depouillement.candidats = res.body.map(value => value.candidat);
       window.console.log('========= candidat ==========');
       window.console.log(this.depouillement.candidats);
       window.console.log('===================');
     }); */
      this.candidatService.findAllByActivite(activiteId).subscribe((res: HttpResponse<ICandidat[]>) => {
        this.depouillement.candidats = res.body;
        window.console.log('========= candidat ==========');
        window.console.log(this.depouillement.candidats);
        window.console.log('===================');
      });
    }
  }

  onRowSelect() {
    /* if (this.lotsSelected != null) {
      this.findCautionByLot();
    } */
    // window.console.log(this.lotsSelected.candidatLots);
  }

  showSoumissionnaireModal() {
    this.isSaving = false;
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new Candidat();
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
      // eslint-disable-next-line no-console
      console.log(this.lots);
      this.nbrsLots = this.lots.length;


    });
    // this.candidatLot.lotId = this.lotsSelected.id;
    this.displaySoumissionnaireModal = true;
  }

  saveSoumissionaire() {
    window.console.log('======lot========');
    window.console.log(this.lots);
    window.console.log('==============');
    this.candidatLot.lots= this.lots;
    this.candidatLot.dossierPaye = false;
    this.candidatLotService.create(this.candidatLot).subscribe((res: HttpResponse<ICandidatLot>) => {
      this.candidatService.find(res.body.candidatId).subscribe((candidat: HttpResponse<ICandidat>) => {
        this.candidatLots.forEach(value => {
          if (this.candidatLot.lots.some(value1 => value1.id === value.lotId)) {
            candidat.body.soumisionniares.push(value);
          }
        });
        this.depouillement.candidats.push(candidat.body);
      });
      this.displaySoumissionnaireModal = false;
    });
  }

  addCandidat() {
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new Candidat();
    // this.candidatLot.lotId = this.lotsSelected.id;
    this.candidatLots.push(this.candidatLot);
  }

/*  retireSoumissionnaire(soumissionaire) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de retirer ce soummissionaire de la liste?',
      accept: () => {
        // this.lotsSelected.candidatLots = this.lotsSelected.candidatLots.filter(value => value.id !== soumissionaire.id);
      }
    });

  } */

  /* addEngagement(soumissionaire) {
    this.candidatLotTemp = soumissionaire;
    this.lots.forEach(value => {
      if (value.id === this.lotsSelected.id) {
        value.candidatLots.forEach(value1 => {
          if (value1.id === this.candidatLotTemp.id) {
            if (value1.candidatCautionLots[0] != null && value1.candidatCautionLots[0].depouillement) {
              this.candidatCautionLot = value1.candidatCautionLots[0];
            } else {
              this.candidatCautionLot = new CandidatCautionLot();
            }
          }
        });
      }
    });
    this.displayPieceModal = true;
  } */

  save() {
    this.isSaving = true;
    window.console.log('==============');
    window.console.log(this.depouillement);
    window.console.log('==============');
    if (this.depouillement.id !== undefined) {
      this.subscribeToSaveResponse(this.depouillementService.update(this.depouillement));
    } else {
      this.depouillement.activiteId = this.activite.id;
      this.subscribeToSaveResponse(this.depouillementService.create(this.depouillement));
    }
  }

  /* protected getTableCandidatLot(candidatLotListe: ICandidatLot[]) {
     if (candidatLotListe.length > 0) {
       candidatLotListe.forEach(value => {
         this.depouillement.candidatLots.push(value);
       });
     }
   } */

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepouillement>>) {
    result.subscribe((res: HttpResponse<IDepouillement>) => {
      window.console.log(res.body);
      this.onSaveSuccess();
    }, () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.display = false;
    if (this.depouillement.id !== undefined) {
      this.message('success', 'Modification', 'Modification effectuée avec succès.');
    } else {
      this.message('success', 'Enregistrement', 'Enregistrement effectué avec succès.');
    }
    this.filterDepouillement();
    this.candidatLots = [];
  }

  protected onSaveError() {
    this.message('warn', 'Erreur', 'Echec de l\'enregistrement');
    this.isSaving = false;
  }

  updateDepouillement(depouillement) {
    this.lots = [];
    this.lotsSelected = [];
    this.depouillement = depouillement;
    this.display = true;
    window.console.log('====== update ========');
    window.console.log(this.depouillement);
    window.console.log('==============');
  }

  supprimerDepouillement(depouillement: IDepouillement) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        depouillement.deleted = true;
        this.depouillementService.update(depouillement).subscribe((res: HttpResponse<any>) => {
          window.console.log(res);
          this.loadAll(this.getActiviteId());
        });
      }
    });
  }

  findCautionByLot() {
    this.cautionService.findAllByLot(this.lotTMP.id).subscribe((res: HttpResponse<ICaution[]>) => {
      this.cautions = res.body;
      window.console.log(this.cautions);
    });
  }

  updateEngagement(candidat, candidatLot) {
    this.candidatTMP = candidat;
    this.lotTMP = candidatLot.lot;
    this.findCautionByLot();
    this.displayCaution = true;
  }

  annulerCaution() {
    this.displayCaution = false;
  }

  validerEngagement() {
    this.depouillement.candidats.forEach(value => {
      if (value.id === this.candidatTMP.id) {
        value.soumisionniares.forEach(candidaLot => {
          if (candidaLot.lotId === this.lotTMP.id) {
            candidaLot.candidatCautionLots = [];
            candidaLot.candidatCautionLots.push(this.candidatCautionLot);
            this.candidatCautionLot = new CandidatCautionLot();
          }
        });
      }
    });
    this.displayCaution = false;
  }

  removeEngagement(candidat, lot) {
    this.depouillement.candidats.forEach(value => {
      if (value.id === candidat.id) {
        value.soumisionniares.forEach(candidaLot => {
          if (candidaLot.id === lot.id) {
            candidaLot.candidatCautionLots = [];
          }
        });
      }
    });
  }

  message(severite: string, resume: string, detaille: string) {
    this.messageService.add({key: 'key', severity: severite, summary: resume, detail: detaille});
  }

  showInfructueuxModal() {
    if (this.lots.length !== 0) {
      this.lots = this.lots.filter(value => value.infructueux);
    }
    this.display = true;
  }

  setFileData(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.depouillement.files = this.files;
    }
  }

  getFiles(depouillement: IDepouillement) {
    this.isLoading = true;
    this.depouillementTMP = depouillement;
    this.depouillementService.find(depouillement.id).subscribe((res: HttpResponse<IDepouillement>) => {
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModal = true;
  }

  dowloadFichier(file) {
    this.dataUtils.downloadFile(file.fileContentType, file.file, file.fileName);
  }

  retirerFihier(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.depouillementTMP.id, TypeDossier.DEPOUILLEMENT, file.fileName).subscribe(() => {
          this.message('success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFiles(this.depouillementTMP);
        }, () => {
          this.message('warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }

  setFileAddData(event) {
    this.fileListe = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.depouillementTMP.files = this.files;
    }
    window.console.log('========= files =========');
    window.console.log(this.fileListe);
  }

  addFile(vale) {
    if (vale != null && this.fileListe.length !== 0) {
      this.isSaving = true;
      this.depouillementService.update(this.depouillementTMP).subscribe(() => {
        this.fileListe = undefined;
        this.getFiles(this.depouillementTMP);
        this.isSaving = false;
        this.message('success', 'Chargement de fichiers', 'Le chargement des fichiers effectué avec succès');
      }, () => {
        this.isSaving = false;
        this.message('warn', 'Erreur', 'Le chargement des fichiers à echouer');
      });
    }
  }

  addLot(candidat) {
    this.candidatTMP = candidat;
    this.lotModal = true;
  }

  annulerLot(int: number) {
    if (int === 1) {
      this.depouillement.candidats.forEach(value => {
        if (value.id === this.candidatTMP.id) {
          value.soumisionniares = this.lotsSelected;
        }
      });
    }
    this.lotModal = false;
  }

  validerLot() {
    this.depouillement.candidats.forEach(value => {
      if (value.id === this.candidatTMP.id) {
        value.soumisionniares = this.lotsSelected;
      }
    });
    this.lotsSelected = [];
    this.lotModal = false;
  }

  updateLot(candidat) {
    this.candidatTMP = candidat;
    this.depouillement.candidats.forEach(value => {
      if (value.id === this.candidatTMP.id) {
        this.lotsSelected = value.soumisionniares;
        value.soumisionniares = [];
      }
    });
    this.lotModal = true;
  }

  tabViewChange(event) {
    this.index = event.index;
  }
}
