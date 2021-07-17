import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {IReclamation, Reclamation} from 'app/shared/model/microservicedaccam/reclamation.model';

import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {ReclamationService} from './reclamation.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CandidatLot, ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {ILot} from 'app/shared/model/microservicedaccam/lot.model';
import {IReclamationCandidatLot} from 'app/shared/model/microservicedaccam/reclamation-candidat-lot.model';
import {IPieceCandidat} from 'app/shared/model/microservicedaccam/piece-candidat.model';
import {ReclamationCandidatLotService} from 'app/entities/microservicedaccam/reclamation-candidat-lot/reclamation-candidat-lot.service';
import {IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';
import {Decision, IDecision} from 'app/shared/model/microservicedaccam/decision.model';
import {Fichier} from "app/entities/file-manager/file-menager.model";
import {DataUtils} from "app/entities/file-manager/dataUtils";

@Component({
  selector: 'jhi-reclamation',
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.scss']
})
export class ReclamationComponent implements OnInit, OnDestroy {
  reclamations: IReclamation[];
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
  reclamation: IReclamation;
  date: Date;
  displayAdd: boolean;
  displayAddDecision: boolean;
  dateDp: any;
  isSaving: boolean;
  displaySoumissionnaireModal: boolean;
  candidatLots: ICandidatLot[];
  candidatLot: ICandidatLot;
  lot: ILot;
  lots: ILot[];
  display: boolean;
  reclamationCandidatLot: IReclamationCandidatLot;
  reclamationCandidatLotTemp: IReclamationCandidatLot;
  reclamationCandidatLots: IReclamationCandidatLot[];
  candidatLotTemp: ICandidatLot;
  pieceCandiats: IPieceCandidat[];
  pieceCandiatsSelected: IPieceCandidat[];
  filesListe: FileList;
  etat: boolean;
  displayPieceModal: boolean;
  soumissionnairesSelected: ICandidatLot[];
  exercice: IExerciceBudgetaire;
  activites: IActivite[];
  activite: IActivite;
  exercices: IExerciceBudgetaire[];
  decision: IDecision;
  dateReclamation: Date;
  modif: boolean;
  fichiers: FileList;
  file: Fichier;
  files: Fichier[];



  constructor(
    protected reclamationService: ReclamationService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected reclamationCandidatLotService: ReclamationCandidatLotService,
    protected activiteService: ActiviteService,
    protected lotService: LotService,
    protected exerciceService: ExerciceBudgetaireService,
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
    this.reclamationService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReclamation[]>) => {
        this.paginateReclamations(res.body, res.headers);
        window.console.log('=======reclamations=======');
        window.console.log(res.body);
        window.console.log('==============');
      });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/reclamation'], {
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
      '/reclamation',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  annuler() {
    this.display = false;
  }

  annulerSomissionaire() {
    this.displaySoumissionnaireModal = false;
    this.soumissionnairesSelected = [];
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInReclamations();
    this.init();
    this.date = new Date();
    this.reclamation = new Reclamation();
    this.reclamations = [];
  }

  init() {
    this.lot = null;
    this.display = false;
    this.displayAddDecision = false;
    this.displayPieceModal = false;
    this.isSaving = false;
    this.etat = false;
    this.displaySoumissionnaireModal = false;
    this.candidatLots = [];
    this.pieceCandiats = [];
    this.pieceCandiatsSelected = [];
    this.candidatLotTemp = new CandidatLot();
    this.soumissionnairesSelected = [];
    this.loadAllExercice();
    this.decision = new Decision();
    this.reclamationCandidatLotTemp = null;
    this.dateReclamation = new Date();
    this.modif= false;
    this.reclamationCandidatLot = null ;
    this.files = [];
  }

  actualisation(){
    this.lot = null;
    this.candidatLots = [];
    this.pieceCandiats = [];
    this.pieceCandiatsSelected = [];
    this.files = [];
    this.reclamationCandidatLot = null ;
    this.reclamationCandidatLotTemp = null;
  }

  updateReclamation(reclamation) {
    this.reclamation = reclamation;
    this.display = true;
    window.console.log('=======reclamation=======');
    window.console.log(this.reclamation);
    window.console.log('==============');
  }

  supprimerReclamation(reclamation) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        reclamation.delete = false;
        this.reclamationService.update(reclamation).subscribe((res: HttpResponse<any>) => {
          window.console.log(res);
          this.loadAll();
        });
      }
    });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReclamation) {
    return item.id;
  }

  registerChangeInReclamations() {
    this.eventSubscriber = this.eventManager.subscribe('reclamationListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateReclamations(data: IReclamation[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.reclamations = data;
  }

  ifExist(): boolean {
    if (this.reclamation.id) {
      return this.reclamations.some(value => value.id !== this.reclamation.id && value.description=== this.reclamation.description);
    } else {
      return this.reclamations.some(value => value.date === this.reclamation.date);
    }
  }

  save() {
    if (!this.ifExist()) {
    this.isSaving = true;
    if (this.reclamation.id !== undefined) {
      this.subscribeToSaveResponse(this.reclamationService.update(this.reclamation));
    } else {
      this.reclamation.lotId = this.lot.id;
      window.console.log("==================================");
      window.console.log(this.reclamation);
      window.console.log("==================================");
     this.subscribeToSaveResponse(this.reclamationService.create(this.reclamation));
    }
    this.display= false;
  }else {
      this.showMessage('error', 'ENREGISTREMENT', 'Une reclamation deja pour ce lot !');
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReclamation>>) {
    result.subscribe(() => {
      this.showMessage('success', 'ENREGISTREMENT', 'reclamation ajouté avec succès!!!');
      this.onSaveSuccess()
    }, () => {
      this.showMessage('error', 'ENREGISTREMENT', "Echec de l'enregistrement!!!");
      this.onSaveError()
    });
  }

  showMessage(sever: string, sum: string, det: string) {
    this.messageService.add({
      severity: sever,
      summary: sum,
      detail: det
    });
  }

  protected onSaveError() {
    this.isSaving = false;
    this.displayAdd = false;
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAll();
    this.displayAdd = false;
  }

  previousState() {
    this.displayAdd = false;
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

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
  }

  activiteChange() {
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
    });
  }

  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
    } else {
      return 0;
    }
  }


  filterDepouillement() {
    this.reclamationService.findReclamationByLot(this.getLotId()).subscribe((res: HttpResponse<IReclamation[]>) => (this.reclamations = res.body)
      .filter(value => value.lotId === this.reclamation.lotId));
    if(this.reclamation.reclamationCandidatLots.length>0) {
      this.reclamation.reclamationCandidatLots.forEach(value => {
        this.reclamationCandidatLots = this.reclamationCandidatLots.filter(value1 => value1.candidatLotId === value.candidatLotId);
      })
    }
  }

  getLotId() {
    if(this.lot !== null) {
      return this.lot.id;
    } else {
      return 0;
    }
  }

  showSoumissionnaireModal() {
    this.loadAllSoummissionnaire();
    this.displaySoumissionnaireModal = true;
  }

  showModal() {
    this.reclamation = new Reclamation();
    this.reclamation.reclamationCandidatLots = [];
    this.display = true;
    this.isSaving = false;
  }

  loadAllSoummissionnaire() {
    this.reclamationCandidatLotService.initReclamationCandidatLot(this.lot.id).subscribe((res: HttpResponse<IReclamationCandidatLot[]>) => {
      {
        this.reclamationCandidatLots = res.body;
        if(this.reclamation.reclamationCandidatLots.length>0) {
          this.reclamation.reclamationCandidatLots.forEach(value => {
            this.reclamationCandidatLots = this.reclamationCandidatLots.filter(value1 => value1.candidatLotId !== value.candidatLotId);
          })
        }
        window.console.log("==============================");
        window.console.log(this.reclamationCandidatLots);
        window.console.log("==============================");
      }
    });
  }

  valider() {
    if (this.reclamation.reclamationCandidatLots.length > 0) {
      this.soumissionnairesSelected.forEach(value => {
        value.lotId = this.lot.id;
        this.reclamation.reclamationCandidatLots.push(value);
      });
    } else {
      this.soumissionnairesSelected.forEach(value => value.lotId = this.lot.id);
      this.reclamation.reclamationCandidatLots = this.soumissionnairesSelected;
    }
    this.displaySoumissionnaireModal = false;
  }

  addDecision(reclamationCandidatLot) {
    this.reclamationCandidatLotTemp = reclamationCandidatLot;
      this.reclamation.reclamationCandidatLots.forEach(value => {
        if (value.candidatLotId === this.reclamationCandidatLotTemp.candidatLotId) {
          window.console.log("===============reclamationCandidatLotTemp===============");
          window.console.log(this.reclamationCandidatLotTemp);
          window.console.log("==============================");
          if (value.decision !== null) {
          this.decision = value.decision;
        } else {
          this.decision = new Decision();
        }
        }
      });

    this.displayAddDecision = true;
  }

  retireSoumissionnaire(reclamationCandidatLot) {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir retirer ce soummissionaire de la liste?',
      accept: () => {
        this.reclamation.reclamationCandidatLots = this.reclamation.reclamationCandidatLots.filter(value => value.id !== reclamationCandidatLot.id);
      }
    });
    window.console.log('==============');
    window.console.log(this.reclamation.reclamationCandidatLots);
    window.console.log('==============');
  }


  annulerDecision() {
    this.displayAddDecision = false;
  }


  validerDecision() {
    this.confirmationService.confirm({
      message: 'Êtes-vous sûr de vouloir ajouter ce soumissionnaire à la liste?',
      accept: () => {
        this.reclamation.reclamationCandidatLots.forEach(value => {
          if (value.candidatLotId === this.reclamationCandidatLotTemp.candidatLotId) {
            window.console.log("==========================");
            window.console.log(this.reclamationCandidatLotTemp.candidatLot);
            window.console.log("==========================");
            value.decision = this.decision;
          }
        });
        this.displayAddDecision = false;
        this.loadAll();
        window.console.log(this.reclamation);
      }
    });
  }

  deleteElement(reclamation: IReclamation) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (reclamation === null) {
          return;
        } else {
          reclamation.deleted = true;
          this.reclamationService.update(reclamation).subscribe(
            () => {
              this.loadAll();
              this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
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
    }
    this.reclamation.files = this.files;
  }


}
