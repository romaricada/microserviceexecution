import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {Caution, ICaution} from "app/shared/model/microservicedaccam/caution.model";
import {ILot} from "app/shared/model/microservicedaccam/lot.model";
import {CautionService} from "app/entities/microservicedaccam/caution/caution.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {ExerciceBudgetaireService} from "app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service";
import {LotService} from "app/entities/microservicedaccam/lot/lot.service";
import {IActivite} from "app/shared/model/microserviceppm/activite.model";
import {IExerciceBudgetaire} from "app/shared/model/microserviceppm/exercice-budgetaire.model";
import {ITypeCaution, TypeCaution} from "app/shared/model/microservicedaccam/typeCaution.model";
import {TypeCautionService} from "app/entities/microservicedaccam/type-caution/type-caution.service";

@Component({
  selector: 'jhi-caution',
  templateUrl: './caution.component.html'
})
export class CautionComponent implements OnInit, OnDestroy {

  cautions: ICaution[];
  cautionTMP: ICaution[];
  caution: ICaution;
  cautionSelected: ICaution[];
  lots: ILot[];
  lot: ILot;
  typeCautions: ITypeCaution[];
  typeCaution: ITypeCaution;
  activites: IActivite[];
  activite: IActivite;
  exercice: IExerciceBudgetaire;
  exercices: IExerciceBudgetaire[];
  error: any;
  isSaving: boolean;
  displaych: boolean;
  displayAdd: boolean;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  ajout = false;
  ajoute = false;
  display: Boolean;
  displayDelete: Boolean;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected cautionService: CautionService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected activiteService: ActiviteService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected lotService: LotService,
    protected typeCautionService: TypeCautionService,
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
    this.cautionService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICaution[]>) => this.paginateCautions(res.body, res.headers));
    window.console.log()
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  annuler() {
    this.caution = new Caution();
    this.caution.typeCaution = new TypeCaution();
    this.display = false;
    this.loadAll();
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

  ajouterInstitution(): void {
    if (!this.ajoute) {
      this.loadInstitutionBancaire();
      this.ajoute = true;
    } else {
      this.ajoute = false;
    }
  }

  annulerDelete() {
    this.caution = new Caution();
    this.caution.typeCaution = new TypeCaution();
    this.displayDelete = false;
  }

  deleteAll() {
    this.cautionService.deleteAll(this.cautionSelected).subscribe(
      () => {
        this.loadAll();
        this.showMessage('success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
      },
      () => this.showMessage('error', 'SUPPRESSION', 'Echec de la suppression !')
    );

    this.displayDelete = false;
  }


  supprimer() {
    this.displayDelete = true;
  }

  deleteCaution(caution: ICaution) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (caution === null) {
          return;
        } else {
          caution.deleted = true;
          this.cautionService.update(caution).subscribe(
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
      key: 'myKey1',
      severity: sever,
      summary: sum,
      detail: det
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

  loadActivite() {
    this.lotService.findLotByActivite(this.getActiviteId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.lots = res.body;
    });
  }

  loadInstitutionBancaire() {
    this.cautionService.findAllInstitutionByCautions().subscribe((res: HttpResponse<Caution[]>) => {
      this.cautionTMP = res.body; });
  }

  loadAllTypeCaution() {
    this.typeCautionService.query().subscribe((res: HttpResponse<ITypeCaution[]>) => {
      this.typeCautions = res.body;
    });
  }

  getActiviteId(): number {
    if (this.activite !== null) {
      return this.activite.id;
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

  loadCautionByLot() {
    this.cautionService.findCautionByLot(this.getLotId()).subscribe((res: HttpResponse<ILot[]>) => {
      this.cautions = res.body;
    });
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
  }

  transition() {
    this.router.navigate(['/caution'], {
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
      '/decision',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.display = false;
    this.displayAdd = false;
    this.exercice = null;
    this.loadAllTypeCaution();
    this.loadInstitutionBancaire();
    this.activite = null;
    this.lot = null;
    this.activites = [];
    this.caution = new Caution();
    this.caution.typeCaution = new TypeCaution();
    this.isSaving = false;
    this.displaych = false;
    this.loadAll();
    this.exerciceBudgetaireService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });

    this.registerChangeInCautions();
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

  trackId(index: number, item: ICaution) {
    return item.id;
  }

  registerChangeInCautions() {
    this.eventSubscriber = this.eventManager.subscribe('cautionListModification', () => this.loadAll());
  }

  add(caution: ICaution) {
    if (caution !== null) {
      this.caution = caution;
    } else {
      this.caution = new Caution();
      this.caution.typeCaution = new TypeCaution();
    }
    this.display = true;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCautions(data: ICaution[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cautions = data;
  }

  save() {
    this.caution.libelle =this.caution.typeCaution.libelle;
    if (!this.ifExist()) {
      this.isSaving = true;
      if (this.caution.id !== undefined) {
        this.subscribeToSaveResponse(this.cautionService.update(this.caution));
        this.loadInstitutionBancaire();
        this.displayAdd = false;
        this.display = false;
      } else {
        this.caution.lotId = this.lot.id;
        this.subscribeToSaveResponse(this.cautionService.create(this.caution));
        this.loadInstitutionBancaire();
        this.displayAdd = false;
        this.display = false;
      }
    } else {
      this.showMessage('error', 'ENREGISTREMENT', 'cette offre existe déjà !');
    }
    this.caution = new Caution();
    this.caution.typeCaution = new TypeCaution();
    this.loadInstitutionBancaire();
  }

  ifExist(): boolean {
    return this.cautions.some(
      value => value.id !== this.caution.id &&
        // value.libelle === this.caution.libelle &&
        value.validite === this.caution.validite &&
        value.montant === this.caution.montant &&
        value.lotId === this.caution.lotId &&
        value.typeCautionId === this.caution.typeCautionId
    );
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaution>>) {
    result.subscribe(
      () => {
        this.showMessage('success', 'ENREGISTREMENT', 'caution ajoutée avec succès!');
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
    this.loadAllTypeCaution();
    this.ajout = false;
    this.displayAdd = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
