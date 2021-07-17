import { Component, OnInit, OnDestroy } from '@angular/core';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {Contentieux, IContentieux} from 'app/shared/model/microserviceexecution/contentieux.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContentieuxService } from './contentieux.service';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {ILot} from 'app/shared/model/microservicedaccam/lot.model';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {ContratService} from 'app/entities/microserviceexecution/contrat/contrat.service';
import {IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {ICandidatLot} from "app/shared/model/microservicedaccam/candidat-lot.model";
import {MessageService} from "primeng/api";

@Component({
  selector: 'jhi-contentieux',
  templateUrl: './contentieux.component.html'
})
export class ContentieuxComponent implements OnInit, OnDestroy {
  contentieuxes: IContentieux[];
  contentieux: IContentieux;
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
  activite: IActivite;
  activites: IActivite[];
  exercice: IExerciceBudgetaire;
  exercices: IExerciceBudgetaire[];
  lot: ILot;
  lots:ILot[];
  contentieuxSelecteds: IContentieux[];
  display: boolean;
  contrats: IContrat[];
  isSaving: boolean;

  constructor(
    protected contentieuxService: ContentieuxService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected activiteService: ActiviteService,
    protected exerciceService: ExerciceBudgetaireService,
    protected jhiAlertService: JhiAlertService,
    protected lotService: LotService,
    protected contratService: ContratService,
    protected messageService: MessageService

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
    this.contentieuxService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContentieux[]>) => {
        this.paginateContentieuxes(res.body, res.headers);
      });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/contentieux'], {
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
      '/contentieux',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContentieuxes();
    this.init();
    this.contratService.query().subscribe((res: HttpResponse<IContrat[]>) => (this.contrats = res.body),
      (res: HttpErrorResponse) => this.onError(res.message))
  }

  init () {
    this.loadAllExercice();
    this.contentieux = new Contentieux();
    this.display= false;
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContentieux) {
    return item.id;
  }

  registerChangeInContentieuxes() {
    this.eventSubscriber = this.eventManager.subscribe('contentieuxListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContentieuxes(data: IContentieux[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contentieuxes = data;
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
    this.loadAll();
  }

  ajoutContentieux() {
    this.contentieux = new Contentieux();
    this.display = true;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
  previousState() {
    this.display = false;
  }
  annuler() {
    this.display = false;
  }

  save() {
    this.isSaving = true;
    if (this.contentieux.id !== undefined) {
      this.subscribeToSaveResponse(this.contentieuxService.update(this.contentieux));
    } else {
      this.subscribeToSaveResponse(this.contentieuxService.create(this.contentieux));
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidatLot>>) {
    result.subscribe(() => {
      this.showMessage( 'success', 'ENREGISTREMENT', 'Candidat ajouté avec succès!!!');
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
    this.display = false;
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAll();
    this.display = false;
  }
  /* filterContrat (){
    this.contratService
  } */
}
