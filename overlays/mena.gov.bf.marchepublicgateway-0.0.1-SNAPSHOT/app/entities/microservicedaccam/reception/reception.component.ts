import { ReportService } from 'app/entities/microservicedaccam/reports/reportService';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {IReception, Reception} from 'app/shared/model/microservicedaccam/reception.model';

import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {ReceptionService} from './reception.service';

import {ConfirmationService, MessageService} from "primeng/api";

import {IActivite} from "app/shared/model/microserviceppm/activite.model";

import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {IExerciceBudgetaire} from "app/shared/model/microserviceppm/exercice-budgetaire.model";

import {ExerciceBudgetaireService} from "app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service";
import {ILot} from "app/shared/model/microservicedaccam/lot.model";
import {LotService} from "app/entities/microservicedaccam/lot/lot.service";
import {TypeReception} from 'app/shared/model/enumerations/TypeReception';



@Component({
  selector: 'jhi-reception',
  templateUrl: './reception.component.html'
})
export class ReceptionComponent implements OnInit, OnDestroy {
  receptions: IReception[];
  receptionSelected: IReception[];
  lots: ILot[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  reception: IReception;
  page: any;
  isSaving: boolean;
  displayAdd: boolean;
  displaych: boolean;
  predicate: any;
  display: Boolean;
  previousPage: any;
  reverse: any;
  displayDelete: boolean;
  dateDp: any;
  lot: ILot;
  activites: IActivite[];
  activite: IActivite;
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  date: Date;
  modiSatatus: boolean;
  imprime: boolean;

  constructor(
    protected receptionService: ReceptionService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected messageService: MessageService,
    protected lotService: LotService,
    protected confirmationService: ConfirmationService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected eventManager: JhiEventManager,
    protected activiteService: ActiviteService,
    protected reportService: ReportService
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
    this.receptionService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReception[]>) => this.paginateReceptions(res.body, res.headers));
  }


  getlotId(): number {
    if (this.lot !== null) {
      return this.lot.id;
    } else {
      return 0;
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
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

  transition() {
    this.router.navigate(['/reception'], {
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
      '/reception',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.displayAdd = false;
    this.modiSatatus = false;
    this.imprime = false;
    this.date = new Date();
    this.exercice = null;
    this.activite = null;
    this.lot = null;
    this.reception = new Reception();
    this.isSaving = false;
    this.displaych = false;
    this.display = false;
    this.activites = [];
    this.loadAll();
    //this.loadReceptionByActivite();
    this.exerciceBudgetaireService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });

    this.registerChangeInReceptions();
  }

  actualisation(){
    this.exercice = null;
    this.activite = null;
    this.lot = null;
    this.activites = [];
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReception>>) {
    result.subscribe(
      () => {
        this.showMessage('success', 'ENREGISTREMENT', 'offre ajouté avec succès!');
        this.onSaveSuccess();
      },
      () => {
        this.showMessage('error', 'ENREGISTREMENT', "Echec d'enregistrement!");
        this.onSaveError();
      }
    );
  }


  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAll();
    this.displayAdd = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  save() {
    if (!this.ifExist()) {
      this.isSaving = true;
      /* this.reception.date = moment(this.date);*/
      if (this.reception.id !== undefined) {
        this.subscribeToSaveResponse(this.receptionService.update(this.reception));
        this.displayAdd = false;
      } else {
        this.reception.activiteId = this.activite.id;
        this.subscribeToSaveResponse(this.receptionService.create(this.reception));
        this.displayAdd = false;
      }
    } else {
      this.showMessage('error', 'ENREGISTREMENT', 'cette offre existe déjà !');
    }
    this.reception.nom = '';
    this.reception.prenom = '';
    this.reception.lieu = '';
    this.reception.heure = '';
    this.reception.email = '';
    this.reception.telephone = '';
    this.loadAll();
  }

  enre() {
    this.displaych = true;
  }

  supprimer() {
    this.displayDelete = true;
  }

  annuler() {
    this.reception = new Reception();
    this.display = false;
    this.loadAll();

  }

  annulerDelete() {
    this.reception = new Reception();
    this.displayDelete = false;
  }

  trackId(index: number, item: IReception) {
    return item.id;
  }

  registerChangeInReceptions() {
    this.eventSubscriber = this.eventManager.subscribe('receptionListModification', () => this.loadAll());
  }

  add(reception: IReception) {
    reception === null ? (this.reception = new Reception()) : (this.reception = reception);
    this.display = true;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }
//******************************************************************************************************** */
ImprimerOffre() {
    this.reportService
      .ImprimerOffre()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  }
//******************************************************************************************************** */

  deleteAll() {
    this.receptionService.deleteAll(this.receptionSelected).subscribe(
      () => {
        this.loadAll();
        this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
      },
      () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !')
    );

    this.displayDelete = false;
  }

  deleteElement(reception: IReception) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (reception === null) {
          return;
        } else {
          reception.deleted = true;
          this.receptionService.update(reception).subscribe(
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

  showMessage(sever: string, sum: string, det: string) {
    this.messageService.add({
      severity: sever,
      summary: sum,
      detail: det
    });
  }

  ifExist(): boolean {
    return this.receptions.some(
      value => value.id !== this.reception.id &&
        value.nom === this.reception.nom &&
        value.activiteId === this.reception.activiteId &&
        value.typeReception === this.reception.typeReception &&
        value.prenom === this.reception.prenom &&
        value.telephone === this.reception.telephone
    );
  }

  protected paginateReceptions(data: IReception[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.receptions = data;
  }

  loadReceptionByActivite() {      
    this.imprime = true;
    this.receptionService.findReceptionByActivite(this.getActiviteId()).subscribe((res: HttpResponse<IReception[]>) => {
      if (res.body.length > 0) {
        res.body.forEach(value => {
          value.nom = value.nom + ' ' + value.prenom;
        });
      }
      this.receptions = res.body;
   
    });
  }


  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
    } else {
      return 0;
    }
  }


  changeTypeReception(reception: IReception) {
    window.console.log(reception);
    if (reception.typeReception === TypeReception.RETRAIT) {
      this.reception = reception;
      this.modiSatatus = true;
    } else {
      this.confirmationService.confirm({
        header: 'Confirmation',
        message: 'Etes-vous sûr de changer le type ?',
        accept: () => {
          reception.typeReception = TypeReception.RETRAIT;
          this.receptionService.update(reception).subscribe(() => {
            this.reception = new Reception();
            this.loadReceptionByActivite();
            this.showMessage('success', 'Mise à jour', 'Operation effectuée avec sucès!');
          });
        }
      });
    }
  }

  modifier() {
    this.receptionService.update(this.reception).subscribe(() => {
      this.modiSatatus = false;
      this.reception = new Reception();
      this.loadReceptionByActivite();
      this.showMessage('success', 'Mise à jour', 'Operation effectuée avec sucès!');
    });
  }

  fermer() {
    this.modiSatatus = false;
  }


}
