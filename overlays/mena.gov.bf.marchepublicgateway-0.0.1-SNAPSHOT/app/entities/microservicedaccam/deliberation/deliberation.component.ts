import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {JhiDataUtils, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Deliberation, IDeliberation} from 'app/shared/model/microservicedaccam/deliberation.model';
import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {DeliberationService} from './deliberation.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {IActivite} from 'app/shared/model/microserviceppm/activite.model';
import {ILot} from 'app/shared/model/microservicedaccam/lot.model';
import {ActiviteService} from 'app/entities/microserviceppm/activite/activite.service';
import {LotService} from 'app/entities/microservicedaccam/lot/lot.service';
import {IExerciceBudgetaire} from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import {ExerciceBudgetaireService} from 'app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service';
import {CandidatLotService} from 'app/entities/microservicedaccam/candidat-lot/candidat-lot.service';
import {FileMenagerService} from 'app/entities/file-manager/file-menager.service';
import {TypeDossier} from 'app/shared/model/enumerations/typeDossier';
import {Fichier, IFichier} from 'app/entities/file-manager/file-menager.model';
import {DataUtils} from 'app/entities/file-manager/dataUtils';

@Component({
  selector: 'jhi-deliberation',
  templateUrl: './deliberation.component.html',
  styleUrls: ['deliberation.component.scss']
})
export class DeliberationComponent implements OnInit, OnDestroy {
  deliberations: IDeliberation[];
  deliberationSelected: IDeliberation[];
  candidatLots: ICandidatLot[];
  activites: IActivite[];
  activite: IActivite;
  exercice: IExerciceBudgetaire;
  exercices: IExerciceBudgetaire[];
  candidatLot: ICandidatLot;
  deliberation: IDeliberation;
  lots: ILot[];
  lot: ILot;
  error: any;
  success: any;
  dateDp: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  display: Boolean;
  displayDelete: Boolean;
  isSaving: Boolean;
  navigation = false;
  index: number;
  fichiers: FileList;
  soumissionnairesLot: ICandidatLot[];
  showFicModal: boolean;
  fileListe: FileList;
  deliberationTPM: IDeliberation;
  files: IFichier[];
  file: Fichier;
  isLoading: boolean;
  dataFiles: Fichier[];


  constructor(
    protected deliberationService: DeliberationService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected activiteService: ActiviteService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected lotService: LotService,
    protected candidatLotService: CandidatLotService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected fileManagerService: FileMenagerService,
    protected fileUtils: DataUtils,
    protected dataUtils: JhiDataUtils
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
    this.deliberationService
      .query(this.getLotId(), {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDeliberation[]>) => this.paginateDeliberations(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  supprimer() {
    this.displayDelete = true;
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.loadAll();
    this.display = false;
    if (this.deliberation.id !== undefined) {
      this.showMessage('myKey', 'success', 'Mise à jour', 'Operation effectuée avec succès!');
    } else {
      this.showMessage('myKey', 'success', 'ENREGISTREMENT', 'Operation effectuée avec succès!');
    }
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  add(deliberation: IDeliberation) {
    deliberation === null ? (this.deliberation = new Deliberation()) : (this.deliberation = deliberation);
    this.display = true;
    this.loadSoumissionnaire();
  }

  transition() {
    this.router.navigate(['/deliberation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  showMessage(cle: string, severite: string, resume: string, detaille: string): void {
    this.messageService.add({
      key: cle,
      severity: severite,
      summary: resume,
      detail: detaille
    });
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/deliberation',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.deliberation = new Deliberation();
    this.lots = null;
    this.candidatLot = null;
    this.lot = null;
    this.index = 0;
    this.showFicModal = false;
    this.deliberation.candidatLots = [];
    this.exerciceBudgetaireService.findAllWithoutPage().subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
      this.exercices = res.body;
    });
    this.registerChangeInDeliberations();
    this.isLoading = true;
  }

  actualisation(){
    this.exercice = null;
    this.lot = null;
    this.lots = [];
    this.candidatLot = null;
  }

  /* protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliberation>>) {
    result.subscribe(
      (res: HttpResponse<IDeliberation>) => {

        this.showMessage('myKey', 'success', 'ENREGISTREMENT', 'Un lot ajouté avec succès!');
        this.onSaveSuccess();
      },
      () => {
        this.showMessage('myKey', 'error', 'ENREGISTREMENT', "Echec d'enregistrement!");
        this.onSaveError();
      }
    );
  } */

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDeliberation) {
    return item.id;
  }


  save() {
    if (!this.ifExist()) {
      this.isSaving = true;
      this.deliberation.lotId = this.lot.id;
      this.saveFunction(this.deliberation);
    } else {
      this.showMessage('myKey', 'error', 'Erreur', 'Cet enregistrement existe déjà !');
    }
  }

  saveFunction(deliberation: IDeliberation) {
    if (deliberation.id !== undefined) {
      this.deliberationService.update(deliberation).subscribe((res: HttpResponse<IDeliberation>) => {
        this.onSaveSuccess();
        window.console.log(res);
      });
    } else {
      this.deliberationService.create(deliberation).subscribe((res: HttpResponse<IDeliberation>) => {
        this.onSaveSuccess();
        window.console.log(res);
      });
    }
  }

  deleteAll() {
    this.deliberationService.deleteAll(this.deliberationSelected).subscribe(
      () => {
        this.loadAll();
        this.showMessage('myKey', 'success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
      },
      () => this.showMessage('myKey', 'error', 'SUPPRESSION', 'Echec de la suppression !')
    );

    this.displayDelete = false;
  }

  deleteElement(deliberation: IDeliberation) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (deliberation === null) {
          return;
        } else {
          deliberation.deleted = true;
          this.deliberationService.delete(deliberation.id).subscribe(
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

  annulerDelete() {
    this.deliberation = new Deliberation();
    this.displayDelete = false;
  }

  annuler() {
    this.deliberation = new Deliberation();
    this.display = false;
    this.loadAll();
  }


  ifExist(): boolean {
    if (this.deliberation.id) {
      return this.deliberations.some(value => value.id !== this.deliberation.id && value.description === this.deliberation.description);
    } else {
      return this.deliberations.some(value => value.date === this.deliberation.date);
    }
  }

  registerChangeInDeliberations() {
    this.eventSubscriber = this.eventManager.subscribe('deliberationListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDeliberations(data: IDeliberation[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.deliberations = data;
    window.console.log(this.deliberations);
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

  lotChange() {
    this.loadAll();
    this.loadSoumissionnaire();
  }

  loadSoumissionnaire() {
    this.candidatLotService.findSoumissionnaireByLot(this.getLotId()).subscribe((res: HttpResponse<ICandidatLot[]>) => {
      this.deliberation.candidatLots = res.body;
      this.soumissionnairesLot = res.body;
      window.console.log('===============');
      window.console.log(this.deliberation.candidatLots);
    });
  }

   showMessageConfimation(etat: boolean): string {
    if (etat) {
      return 'Êtes vous sûr de vouloir attribuer le marché à ce soumissionnaire?';
    } else {
      return 'Êtes vous sûr de vouloir retirer le marché à ce soumissionnaire?';
    }
  }

  selectionDeLattributaire(candidatLot: ICandidatLot, etat: boolean) {
    this.confirmationService.confirm({
      message: this.showMessageConfimation(etat),
      accept: () => {
        candidatLot.attributaire = etat;
        this.candidatLotService.changeStatus(candidatLot).subscribe(() => {
          this.loadSoumissionnaire();
        });
      }
    });
  }

  updateDeliberationIsvalide(deliberation: IDeliberation, etat: boolean) {
    deliberation.valide = etat;
    this.deliberationService.changeDeliberationStatus(deliberation)
      .subscribe(() => {this.loadAll()});
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

  getLotId(): number {
    if (this.lot !== null) {
      return this.lot.id;
    } else {
      return 0;
    }
  }

  onTabChange(event) {
    this.index = event.index;
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
    this.deliberation.files = this.files;
  }

  getFiles(deliberation: IDeliberation) {
    this.isLoading = true;
    this.deliberation = deliberation;
    this.deliberationService.find(deliberation.id).subscribe((res: HttpResponse<IDeliberation>) =>{
      this.dataFiles = res.body.files;
      this.isLoading = false;
      window.console.log(this.dataFiles);
    });
    this.showFicModal = true;
  }

  setFileAddData(event) {
    if (event.target.files.length > 0) {
      this.fileListe = event.target.files;
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.file = new Fichier();
        this.fileUtils.setFileData(event, this.file, 'file', false, i);
        this.file.fileName = event.target.files[i].name;
        this.files.push(this.file);
      }
      this.deliberation.files = this.files;
      window.console.log(this.deliberation);
    }
  }

  addFile() {
      this.isSaving = true;
      this.deliberationService.update(this.deliberation).subscribe((res: HttpResponse<IDeliberation>) => {
        window.console.log(res.body);
        this.fileListe = undefined;
        this.isSaving = false;
        this.showMessage('myKey', 'success', 'Chargement fichier', 'Operation effectueé avec succès!');
        this.getFiles(this.deliberationTPM);
      }, () => {
        this.showMessage('myKey', 'warn', 'Chargement fichier', 'Echec de chargement du fichier!');
      });
  }

  dowloadFichier(file) {
    this.dataUtils.downloadFile(file.fileContentType, file.file, file.fileName);
  }

  retirerFihier(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.deliberationTPM.id, TypeDossier.DELIBERATION, file.fileName).subscribe(() => {
          this.showMessage('myKey', 'success', 'Suppression de fichier', 'Fichier supprimé avec succès');
          this.getFiles(this.deliberationTPM);
        }, () => {
          this.showMessage('myKey', 'warn', 'Suppression de fichier', 'Echec de suppression');
        });
      }
    });
  }
}
