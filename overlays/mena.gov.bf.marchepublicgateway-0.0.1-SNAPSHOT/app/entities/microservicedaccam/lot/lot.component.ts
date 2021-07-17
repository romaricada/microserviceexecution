import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {ILot, Lot} from 'app/shared/model/microservicedaccam/lot.model';
import { IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';
import {MessageService, ConfirmationService} from 'primeng/api';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {Caution, ICaution} from 'app/shared/model/microservicedaccam/caution.model';
import {CautionService} from 'app/entities/microservicedaccam/caution/caution.service';
import {ITypeCaution, TypeCaution} from 'app/shared/model/microservicedaccam/typeCaution.model';
import {TypeCautionService} from 'app/entities/microservicedaccam/type-caution/type-caution.service';
import {ITypeCommission} from 'app/shared/model/microservicedaccam/type-commission.model';

@Component({
  selector: 'jhi-lot',
  templateUrl: './lot.component.html'
})
export class LotComponent implements OnInit, OnDestroy {
  lots: ILot[];
  typecaution: ITypeCaution[];
  lot: ILot;
  caution: ICaution;
  cautions: ICaution[];
  cautionSelected: ICaution[];
  lotSelected: ILot[];
  activites: IActivite[];
  activite: IActivite;
  typeCautions: ITypeCaution[];
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  displayLigne: boolean;
  ajout = false;
  totalItems: any;
  isSavinge: boolean;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  isSaving: Boolean;
  display: Boolean;
  displayDelete: Boolean;
  status: String;
  nbresLots: any;
  counter:any;

  constructor(
    protected lotService: LotService,
    protected cautionService: CautionService,

    protected typeCautionService: TypeCautionService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected activiteService: ActiviteService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
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
    this.lotService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILot[]>) => this.paginateLots(res.body, res.headers));

    this.typeCautionService.query().subscribe((res:HttpResponse<ITypeCaution[]>)=>this.typecaution = res.body);
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }
  ajouter(): void {
    if (!this.ajout) {
      this.caution.typeCaution = new TypeCaution();
      this.caution.typeCautionId = undefined;
      this.ajout = true;
    } else {
      this.caution.typeCaution = new TypeCaution();
      this.ajout = false;
    }
  }


  add(lot: ILot) {

    if(lot ===null){
      this.lot = new Lot();
    }
    else{
      this.lot =lot;
      this.cautionService.findAllByLot(lot.id).subscribe((res:HttpResponse<ICaution[]>)=>this.cautions = res.body);

    }
    this.display = true;
  }
  addCaution(caution: ICaution) {
    caution === null ? (this.caution = new Caution()) : (this.caution = caution);
    this.displayLigne = true;
  }

  transition() {
    this.router.navigate(['/lot'], {
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
      '/lot',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.exercice = null;
    this.activite = null;
    this.lots =[];
    this.cautions = [];
    this.typecaution = [];
    this.caution = new Caution();
    this.displayLigne = false;
    this.lot = new Lot();
    this.isSavinge= false;
    this.isSaving = false;
    // this.loadAll();
    this.exerciceBudgetaireService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });
    this.registerChangeInLots();
  }
    // fonction permettant de compter le nombre d'item d'un tableau
    Counter(inputs) {
    let counter = 0;
      for (let i = 0; i < inputs.length; i++) {
       counter++;
      }
    return counter;
  }

  actualisation(){
    this.exercice = null;
    this.activite = null;
    this.lots =[];
    this.lot = new Lot();
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
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

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILot) {
    return item.id;
  }

  showMessage( cle: string,sever: string, sum: string, det: string) {
    this.messageService.add({
      key: cle,
      severity: sever,
      summary: sum,
      detail: det
    });
  }
  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
    } else {
      return 0;
    }
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAllLotByActivite();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  registerChangeInLots() {
    this.eventSubscriber = this.eventManager.subscribe('lotListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLots(data: ILot[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.lots = data;
  }

  loadAllLotByActivite() {
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
      this.nbresLots = this.lots.length;
    });
  }

 /* protected subscribeToSaveResponse(result: Observable<HttpResponse<ILot>>) {
    result.subscribe(
      () => {
        this.showMessage('myKey1', 'success', 'ENREGISTREMENT', 'une caution ajoutée avec succès!');
        this.onSaveSuccess();
      },
      () => {
        this.showMessage('myKey1', 'error', 'ENREGISTREMENT', "Echec d'enregistrement!");
        this.onSaveError();
      }
    );
  }*/
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCommission>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  ifExistCaution(): boolean {
    return this.cautions.some(
      value => value.id !== this.caution.id &&
        value.libelle === this.caution.libelle &&
        value.validite === this.caution.validite &&
        value.montant === this.caution.montant &&
        value.lotId === this.caution.lotId &&
        value.typeCautionId === this.caution.typeCautionId
    );
  }
  saveCaution() {
    if (!this.ifExistCaution()) {

      this.cautionService.create(this.caution).subscribe((cause: HttpResponse<ICaution>) => {
        const membre1 = cause.body;
        this.cautions.push(membre1);
      });
    } else {
      this.showMessage('myKey1','error', 'ENREGISTREMENT', 'Le membre existe déjà !');
    }
    this.displayLigne = false;
  }


 /* save() {
    if (!this.ifExist()) {
      this.isSaving = true;
      if (this.lot.id !== undefined) {
        this.subscribeToSaveResponse(this.lotService.update(this.lot));
        this.display = false;
      } else {
        this.lot.activiteId = this.activite.id;
        this.subscribeToSaveResponse(this.lotService.create(this.lot));
        this.display = true;
      }
    } else {
      this.showMessage('myKey1', 'error', 'ENREGISTREMENT', 'Un lot avec le  même libéllé existe déjà !');
    }
    this.lot.libelle = '';
    this.lot.description = '';
  }*/

  /* saveLot() {
    this.isSavinge = true;
    if (!this.ifExist()) {
      this.typeCommission.activiteId = this.activite.id;
      if (this.typeCommission.id !== undefined) {
        this.subscribeToSaveResponse(this.typeCommissionService.update(this.typeCommission));
      } else {
        this.subscribeToSaveResponse(this.typeCommissionService.create(this.typeCommission));
      }
    } else {
      this.showMessage('error', 'ENREGISTREMENT', 'Un membre commision avec le  même libéllé existe déjà !');
    }

  }
*/
  save() {
    if (!this.ifExist()) {
      this.isSaving = true;
      /* this.reception.date = moment(this.date);*/
      this.lot.cautionId = this.caution.id;
      this.lot.cautionslots = this.cautions;

      if (this.lot.id !== undefined) {
        this.subscribeToSaveResponse(this.lotService.update(this.lot));
        this.display = false;
      } else {
        this.lot.activiteId = this.activite.id;
        this.subscribeToSaveResponse(this.lotService.create(this.lot));
        this.display = false;
      }
    } else {
      this.showMessage('myKey1','error', 'ENREGISTREMENT', 'cette offre existe déjà !');
    }

  }





  deleteAll() {
    this.lotService.deleteAll(this.lotSelected).subscribe(
      () => {
        this.loadAllLotByActivite();
        this.showMessage('myKey1', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
      },
      () => this.showMessage('myKey1', 'error', 'SUPPRESSION', 'Echec de la suppression !')
    );

    this.displayDelete = false;
  }

  deleteElement(lot: ILot) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (lot === null) {
          return;
        } else {
          lot.deleted = true;
          this.lotService.delete(lot.id).subscribe(
            () => {
              this.loadAll();
              this.showMessage('myKey1', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('myKey1', 'error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }

  annulerDelete() {
    this.lot = new Lot();
    this.displayDelete = false;
  }

  annuler() {
    this.lot = new Lot();
    this.display = false;
    this.loadAllLotByActivite();
  }
  annulerCaution() {
    this.caution = new Caution();
    this.caution.typeCaution = new TypeCaution();
    this.displayLigne = false;

  }

  supprimer() {
    this.displayDelete = true;
  }

  ifExist(): boolean {
  /*  if (this.lot.id) {
      return this.lots.some(value => value.id !== this.lot.id && value.libelle === this.lot.libelle);
    } else {
      return this.lots.some(value => value.libelle === this.lot.libelle);
    }*/
    return this.lots.some(
      value => value.id !== this.lot.id &&
        value.libelle === this.lot.libelle &&
        value.activiteId === this.lot.activiteId &&
        value.description === this.lot.description

    );
  }


  showMessageConfimation(status: boolean): string {
    if (status) {
      return 'Êtes-vous sûr de vouloir rendre fructueux ce lot du marché?';
    } else {
      return 'Êtes-vous sûr de vouloir rendre infructueux cet lot du marché?';
    }
  }

  changeStatus(lot, status: boolean) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: this.showMessageConfimation(status),
      accept: () => {
        lot.infructueux = status;
        this.lotService.changeStatus(lot).subscribe((res: HttpResponse<any>) => {
          window.console.log(res);
          this.showMessage('myKey1', 'success', 'Mise à jour', 'Operation effectuée avec succès !');
          this.loadAllLotByActivite();
        }, () => {
          this.showMessage('myKey1', 'error', 'Mise à jour', 'Echec de l\' operation !');
        });
      }
    });
  }
}
