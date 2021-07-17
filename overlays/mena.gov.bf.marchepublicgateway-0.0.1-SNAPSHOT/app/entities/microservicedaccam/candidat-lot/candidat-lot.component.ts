import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {CandidatLot, ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {CandidatLotService} from './candidat-lot.service';
import {ILot} from 'app/shared/model/microservicedaccam/lot.model';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {Candidat, ICandidat} from 'app/shared/model/microservicedaccam/candidat.model';
import {CandidatService} from 'app/entities/microservicedaccam/candidat/candidat.service';
import {IDeliberation} from 'app/shared/model/microservicedaccam/deliberation.model';
import {IDepouillement} from 'app/shared/model/microservicedaccam/depouillement.model';
import {IReclamation} from 'app/shared/model/microservicedaccam/reclamation.model';
import {IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';

@Component({
  selector: 'jhi-candidat-lot',
  templateUrl: './candidat-lot.component.html'
})
export class CandidatLotComponent implements OnInit, OnDestroy {
  candidatLots: ICandidatLot[];
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
  candidatLot: ICandidatLot;
  displayAdd: boolean;
  lots: ILot[];
  lot: ILot;
  candidats: ICandidat[];
  candidat: ICandidat;
  deliberation: IDeliberation;
  depouillement: IDepouillement;
  reclamation: IReclamation;
  activites: IActivite[];
  activite: IActivite;
  isSaving: boolean;
  CandidatLotSelected: ICandidatLot[];
  displayDelete: Boolean;
  display: Boolean;
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  selectedLots: any[];
  item: string;

  constructor(
    protected candidatLotService: CandidatLotService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected lotService: LotService,
    protected jhiAlertService: JhiAlertService,
    protected candidatService: CandidatService,
    protected activiteService: ActiviteService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected exerciceService: ExerciceBudgetaireService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  /* loadAll() {
    this.candidatLotService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICandidatLot[]>) => {
        this.paginateCandidatLots(res.body, res.headers)
        window.console.log('========= liste ============');
        window.console.log(this.candidatLots);
        window.console.log('=====================');
      });
  } */


  filterCandidat() {
    this.loadAll(this.getActiviteId());
  }


  loadAll(activiteId: number) {
    this.candidatService
      .query(activiteId, {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICandidat[]>) => {
        this.paginateCandidats(res.body, res.headers)
      });
  }

  /* loadAllCandidatByLot() {
    this.candidatService.findAllCandidat(this.getActiviteId()).subscribe((res:HttpResponse<ICandidat[]>) =>{
      this.candidats = res.body;
      window.console.log('========= liste ============');
      window.console.log(this.candidats);
      window.console.log(this.getActiviteId());
      window.console.log('=====================');
    });
  } */

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/candidat-lot'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll(this.getActiviteId());
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/candidat-lot',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll(this.getActiviteId());
  }

  ngOnInit() {
    this.activite = null;
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new CandidatLot();
    this.candidat = new Candidat();
    this.loadAllExercice();
    this.registerChangeInCandidatLots();
    this.candidatLot.lots = [];
    this.lots = [];
    this.candidatLots = [];
    this.lotService
      .query().subscribe((res: HttpResponse<ILot[]>) => (this.lots = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  registerChangeInCandidatLots() {
    this.eventSubscriber = this.eventManager.subscribe('candidatLotListModification', () => this.loadAll(this.getActiviteId()));
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCandidats(data: ICandidat[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.candidats = data;
  }



  /* loadAllCandidat() {
    this.candidatService
      .query().subscribe((res: HttpResponse<ICandidat[]>) => (this.candidats = res.body),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  } */

  adds() {
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = new Candidat();
    this.candidat = new Candidat();
    // this.loadAllCandidat();
    this.displayAdd = true;
  }

  update(candidat: ICandidat) {
    this.candidatLot = new CandidatLot();
    this.candidatLot.candidat = candidat;
    this.candidatLot.lots = this.candidatLot.candidat.lots;
    // candidatLot === null ? (this.candidatLot = new CandidatLot()) : (this.candidatLot = candidatLot);
    this.displayAdd = true;
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

  getLotId(): number {
    if (this.lot !== null) {
      return this.lot.id;
    } else {
      return 0;
    }
  }

  activiteChange() {
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
    });
  }

  filterLot() {
    this.candidatLotService.findAllByCandidatByLot(this.getLotId()).subscribe((res: HttpResponse<ICandidatLot[]>) => {
      this.candidatLots = res.body;
    });
  }

  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
    } else {
      return 0;
    }
  }

  ifExist(): boolean {
    if (this.candidatLot.candidat.id !== undefined) {
      return this.candidatLot.candidats.some(value => value.id !== this.candidatLot.candidat.id && value.nomStructure === this.candidatLot.candidat.nomStructure);
    } else {
      return this.candidatLot.candidats.some(value => value.nomStructure === this.candidatLot.candidat.nomStructure);
    }
  }

  save() {
      this.isSaving = true;
      this.candidatLot.dossierPaye = true;
      // this.candidatLots.forEach(value => value.dossierPaye === true);
      if (this.candidatLot.id !== undefined) {
        this.subscribeToSaveResponse(this.candidatLotService.update(this.candidatLot));

      } else {
        // this.candidatLot.lot.activiteId = this.activite.id;
        this.subscribeToSaveResponse(this.candidatLotService.create(this.candidatLot));
      }
  }


  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidatLot>>) {
    result.subscribe(() => {
      this.showMessage('success', 'ENREGISTREMENT', 'Candidat ajouté avec succès!!!');
      this.onSaveSuccess()
    }, () => {
      this.showMessage('error', 'ENREGISTREMENT', "Echec de l'enregistrement!!!");
      this.onSaveError()
    });
  }

  protected onSaveError() {
    this.isSaving = false;
    this.displayAdd = false;
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAll(this.getActiviteId());
    this.displayAdd = false;
  }

  previousState() {
    this.displayAdd = false;
  }

  findCandidat() {
    if (this.candidat !== null) {
      this.candidatLot.candidat = new CandidatLot();
      this.candidatLot.candidat = this.candidat;
      this.candidatLot.candidatId = this.candidat.id;
      this.candidatLot.lots = this.candidatLot.candidat.lots;
    } else {
      this.candidatLot.candidat = new Candidat();
    }
  }

  annuler() {
    this.displayAdd = false;
  }


  annulerDelete() {
    this.displayDelete = false;
  }

  deleteAll() {
    this.candidatLotService.deleteAll(this.CandidatLotSelected).subscribe(
      () => {
        this.loadAll(this.getActiviteId());
        this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
      },
      () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !')
    );

    this.displayDelete = false;
  }

  supprimer() {
    this.displayDelete = true;
  }

  showMessage(sever: string, sum: string, det: string) {
    this.messageService.add({
      severity: sever,
      summary: sum,
      detail: det
    });
  }

  deleteElement(candidat: ICandidat) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (candidat === null) {
          return;
        } else {
          candidat.deleted = true;
          this.candidatService.update(candidat).subscribe(
            () => {
              this.loadAll(this.getActiviteId());
              this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }
}
