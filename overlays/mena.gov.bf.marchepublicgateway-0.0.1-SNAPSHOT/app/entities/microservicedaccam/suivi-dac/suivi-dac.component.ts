import {Component, OnInit} from '@angular/core';
import {IExerciceBudgetaire} from "app/shared/model/microserviceppm/exercice-budgetaire.model";
import {HttpResponse} from "@angular/common/http";
import {TacheService} from "app/entities/microservicedaccam/tache/tache.service";
import {JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks} from "ng-jhipster";
import {ActivatedRoute, Router} from "@angular/router";
import {ExerciceBudgetaireService} from "app/entities/microserviceppm/exercice-budgetaire/exercice-budgetaire.service";
import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {MembreCommissionService} from "app/entities/microservicedaccam/membre-commission/membre-commission.service";
import {TypeCommissionService} from "app/entities/microservicedaccam/type-commission/type-commission.service";
import {EtapeActivitePpmService} from "app/entities/microserviceppm/etape-activite-ppm/etape-activite-ppm.service";
import {ConfirmationService, MessageService, SelectItem} from "primeng/api";
import {UniteAdministrativeService} from "app/entities/microserviceppm/unite-administrative/unite-administrative.service";
import {MembreService} from "app/entities/microservicedaccam/membre/membre.service";
import {IActivite} from "app/shared/model/microserviceppm/activite.model";
import {ITypeCommission, TypeCommission} from "app/shared/model/microservicedaccam/type-commission.model";
import {IMembre, Membre} from "app/shared/model/microservicedaccam/membre.model";
import {ITacheEtape, TacheEtape} from "app/shared/model/microservicedaccam/tache-etape.model";
import {IMembreCommission, MembreCommission} from "app/shared/model/microservicedaccam/membre-commission.model";
import {IJourFerier} from "app/shared/model/microserviceppm/jour-ferier.model";
import {ITache, Tache} from "app/shared/model/microservicedaccam/tache.model";
import {IEtapeActivitePpm} from "app/shared/model/microserviceppm/etape-activite-ppm.model";
import {IUniteAdministrative} from "app/shared/model/microserviceppm/unite-administrative.model";
import {Poste} from "app/shared/model/enumerations/poste.model";
import {CreateTacheComponent} from "app/entities/microservicedaccam/create-tache/create-tache.component";
import {TacheComponent} from "app/entities/microservicedaccam/tache/tache.component";
import {LotService} from "app/entities/microservicedaccam/lot/lot.service";
import {ILot} from "app/shared/model/microservicedaccam/lot.model";
import {Etat} from "app/shared/model/enumerations/etat";
import {FileMenagerService} from "app/entities/file-manager/file-menager.service";
import {TypeDossier} from "app/shared/model/enumerations/typeDossier";
import {Fichier, IFichier} from "app/entities/file-manager/file-menager.model";
import {DataUtils} from "app/entities/file-manager/dataUtils";
import {TacheEtapeService} from "app/entities/microservicedaccam/tache-etape/tache-etape.service";
import {TypeTache} from "app/shared/model/enumerations/TypeTache";
import {Observable} from "rxjs";
import {IReception} from "app/shared/model/microservicedaccam/reception.model";
import {IReferentielDelai} from "app/shared/model/microserviceppm/referentiel-delai.model";
import {ReferentielDelaiService} from "app/entities/microserviceppm/referentiel-delai/referentiel-delai.service";
import {IEtape} from "app/shared/model/microserviceppm/etape.model";

@Component({
  selector: 'jhi-suivi-dac',
  templateUrl: './suivi-dac.component.html',
  styleUrls: ['./suivi-dac.component.scss']
})
export class SuiviDacComponent implements OnInit {
taches: ITache[];
tache: ITache;
tacheSuivi: ITache;
tacheTMP: ITache[];
tacheTMP2: ITache[];
display: boolean;
ifDescrib = true;
display2: boolean;
display3: boolean;
toSaisi: boolean;
tacheEtape: ITacheEtape;
exercices: IExerciceBudgetaire[];
exercice: IExerciceBudgetaire;
display4: boolean;

activites: IActivite[];
activite: IActivite;

typeComissions: ITypeCommission[];
typeComission: ITypeCommission;

membres: IMembre[];

membreCommissions: IMembreCommission[] = [];
membreCommissionsToRemove: IMembreCommission[] = [];
membreCommissionNoteAffected: IMembreCommission[] = [];
membreCommissionsToAffecte: IMembreCommission[] = [];

lots: ILot[];
lot: ILot;

refToRemove: ITacheEtape[] = [];
refNoteAffected: ITacheEtape[] = [];
refToAffecte: ITacheEtape[] = [];
etapeActivitePpms: IEtapeActivitePpm[] = [];
refDelai: IReferentielDelai[] = [];

newMembreCommission: IMembreCommission;
displayAddMembre:boolean;
uniteadministratives: IUniteAdministrative[];
postes = [Poste.MEMBRE, Poste.OBSERVATEUR, Poste.PRESIDENT, Poste.RAPPORTEUR];

etaValide: number;
cont: number;
objectId: string;
addNewType: boolean;
isSaving: boolean;
isSaving2: boolean;
types: SelectItem[];
fichiers: IFichier[];
files: Fichier[];
displayF: Boolean;
type: string;
type1: TypeTache;
numIndex: number;


  constructor(
    protected tacheService: TacheService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected exerciceService: ExerciceBudgetaireService,
    protected activiteService: ActiviteService,
    protected membreCommissionService: MembreCommissionService,
    protected typCommissionService: TypeCommissionService,
    protected jhiAlertService: JhiAlertService,
    protected etapeActiviteService: EtapeActivitePpmService,
    protected refDelaiService: ReferentielDelaiService,
    protected messageService: MessageService,
    protected uniteadministrativeService: UniteAdministrativeService,
    protected lotService: LotService,
    protected membreService: MembreService,
    protected fileManagerService: FileMenagerService,
    protected etapeTacheService: TacheEtapeService,
    protected fileUtils: DataUtils,
    protected dataUtils: JhiDataUtils,
    protected confirmationService: ConfirmationService
    ) {}


  loadExercice() {
    this.exerciceService.findAllWithoutPage()
      .subscribe((res: HttpResponse<IExerciceBudgetaire[]>) => {
        this.exercices = res.body;
      });
  }

  loadActivieByExercice(exercice: IExerciceBudgetaire) {
    this.activiteService.findAllByAnneeExercice(exercice.id)
      .subscribe((res: HttpResponse<IActivite[]>) => {
        if (res.body.length > 0) {
          res.body.forEach(value => {
            value.nomActivite = value.codeLignePlan + ' ' + value.naturePrestationLibelle;
          });
        }
        this.activites = res.body;
      })
  }

  loadTypeCommission() {
    this.typCommissionService.findAllTypeCommisWithoutPage()
      .subscribe((res: HttpResponse<ITypeCommission[]>) => {
        this.typeComissions = res.body;
      });
  }

  loadAllMembres() {
    this.membreService.findAll()
      .subscribe(
        (res: HttpResponse<IMembre[]>) => {
          this.membres = res.body;
        });
  }

  onActiviteChange() {
    if (this.activite) {
      this.loadTaskByCriteria(this.activite.id, 'ALL', 'byActivite');
    }
  }

  getActiviteId(): number {
    if (this.activite.id !== undefined) {
      return this.activite.id;
    } else {
      return 0;
    }
  }

  loadTaskByCriteria(id: number, eta: string, crieteria: string) {
    this.tacheService.finAllByCriteria(id, eta, crieteria).subscribe((res: HttpResponse<ITache[]>) => {
      this.taches = res.body;
      this.tacheTMP = res.body;
      this.tacheTMP2 = res.body;
      if(this.tacheSuivi.typeTache) {
        this.onEtapeChange();
      }
    });
  }


  ngOnInit() {
    this.type = '';
    this.tacheTMP = [];
    this.loadExercice();
    this.loadTypeCommission();
    this.exercices = [];
    this.cont = 0;
    this.tacheTMP2 = [];
    this.toSaisi = false;
    this.tacheEtape = new TacheEtape();
    this.isSaving = false;
    this.displayF = false;
    this.display4 = false;
    this.numIndex = null;
    this.tacheSuivi = new Tache();

    this.newMembreCommission = new MembreCommission();
    this.newMembreCommission.membre = new Membre();

    this.loadAllMembres();
    this.uniteadministrativeService
      .findAll()
      .subscribe((res: HttpResponse<IJourFerier[]>) => this.uniteadministratives = res.body);
    this.initElement();

  }

  initElement() {
    this.tache = new Tache();
    this.tache.tacheEtapes = [];
    this.tache.membreCommissions = [];
    this.calculerEtatValidite();
  }

  save() {
    this.tache.typeCommission = this.typeComission;
    this.tache.activiteId = this.activite.id;
    this.tache.etatAvancement = this.etaValide;
    this.tache.typeTache = this.tacheSuivi.typeTache;
    if (this.tache.id !== undefined) {
      this.subscribeToSaveResponse(this.tacheService.update(this.tache));
    } else {
      this.subscribeToSaveResponse(this.tacheService.create(this.tache));
    }
    this.display = false;

  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReception>>) {
    result.subscribe(
      () => {
        this.showMessage('success', 'success', 'ENREGISTREMENT', 'tâche ajoutée avec succès!');
        this.onSaveSuccess();
      },
      () => {
        this.showMessage('error', 'danger','ENREGISTREMENT', "Echec d'enregistrement!");
        this.onSaveError();
      }
    );
  }

  protected onSaveSuccess() {
    this.isSaving = true;
    this.onActiviteChange();
    this.display = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }


  /* *
  * la fonction executer lors de la selection d'un type de commission
  */
  onTypeChange() {
    if (this.activite && this.typeComission) {
      this.membreCommissionService.findAllByActiviteAndTypeCommiss(this.activite.id, this.typeComission.id)
        .subscribe((res: HttpResponse<IMembreCommission[]>) => {
          this.membreCommissions = res.body;
          window.console.log(this.membreCommissions);
        });
    }
  }

  /* *
   * les fonction d'affichage du dialogue de creation et de modification de la tache
   */


  creeTache() {
    if(this.activite) {
      this.tache = new Tache();
      this.tache.typeTache;
      this.typeComission = new TypeCommission();
      this.addNewType = false;
      this.tache.membreCommissions = [];
      this.tache.tacheEtapes = [];
      this.calculerEtatValidite();
      this.fichiers = [];
      this.display = true;
    }
  }

  modifTache(tach: ITache) {
    if (tach) {
      this.tache = tach;
      this.getFiles(this.tache);
      if (this.tache.membreCommissions.length > 0) {
        this.typeComission = this.typeComissions.find(value =>
          value.id === this.tache.membreCommissions[0].typeCommission.id)
      }
    }
    this.loadMembreNoteAffected();
     this.display = true;
  }

  annuler() {
   // this.activite = null;
    this.membreCommissions = [];
    this.typeComission = null;
    this.tache = new Tache();
    this.display = false;
  }

  /* ********************** fin ***************************/

  /* ************************************************************
   * les fonction pour le traitement des membres a une tache
   * ***********************************************************
   */
  loadMembreNoteAffected() {
    if (this.typeComission) {
      this.membreCommissionService.findAllByActiviteAndTypeCommiss(this.activite.id, this.typeComission.id)
        .subscribe((res: HttpResponse<IMembreCommission[]>) => {
          this.membreCommissionNoteAffected = res.body;

          if (this.tache.membreCommissions.length > 0) {
            this.tache.membreCommissions.forEach(value => {
              this.membreCommissionNoteAffected = this.membreCommissionNoteAffected
                .filter(value1 => value1.id !== value.id);
            });
          }
        });
    }
  }

  ajouteMembre() {
    this.membreCommissionNoteAffected = [];
    if (this.typeComission) {
      this.loadMembreNoteAffected();
    }
    this.display2 = true;
  }

  closeMembreDiaglog() {
    this.membreCommissionsToAffecte = [];
    this.membreCommissionNoteAffected = [];
    this.display2 = false;
  }

  removeMembre(membreCommiss: IMembreCommission) {
    if (membreCommiss.id !== undefined) {
      CreateTacheComponent.moveElmementFromTab1ToTab2Copi(membreCommiss, this.tache.membreCommissions,
        this.membreCommissionNoteAffected);
    } else {
      const index = this.tache.membreCommissions.indexOf(membreCommiss);
      this.tache.membreCommissions.splice(index, 1);
    }
  }

  addMembre(membreCommiss: IMembreCommission) {
    CreateTacheComponent.moveElmementFromTab1ToTab2Copi(membreCommiss,
      this.membreCommissionNoteAffected, this.tache.membreCommissions);
  }

  addSelectedMembre() {
    if (this.membreCommissionsToAffecte.length > 0) {
      this.membreCommissionsToAffecte.forEach(value => {
        this.addMembre(value);
      });
    }
  }

  removeSelectedMembre() {
    if (this.membreCommissionsToRemove.length > 0) {
      this.membreCommissionsToRemove.forEach(value => {
        this.removeMembre(value);
      });
    }
  }
  /* ******************************** fin ************************/

  /* *************************************************************
   * les fonction pour le traitement  des eTapes  a d'une tache
   * ************************************************************
   */
  /* * fonction pour loader les etapes non affecter a une taches **/
  loadRefNoteAffected() {
    if (this.activite) {
      window.console.log(this.activite);
      this.refNoteAffected = [];
      this.refDelaiService.findEtapeByModePassationId(this.activite.passationId)
        .subscribe((res: HttpResponse<IEtape[]>) => {
          this.refDelai = res.body;
          res.body.forEach(value => {
            const myTacheEtape: ITacheEtape = new TacheEtape();
            myTacheEtape.etapeActivitePpmId = value.id;
            myTacheEtape.etapeLibelle = value.libelle;
            this.refNoteAffected.push(myTacheEtape);
          });

          if (this.tache.tacheEtapes.length > 0) {
            this.tache.tacheEtapes.forEach(value => {
              this.refNoteAffected = this.refNoteAffected
                .filter(value1 => value1.etapeActivitePpmId !== value.etapeActivitePpmId);
            });
          }
          this.calculerEtatValidite();
        });
    }
  }

  ajouteRef() {
    this.refNoteAffected = [];
    if (this.activite) {
      this.loadRefNoteAffected();
    }
    this.display3 = true;
  }

  closeRefDiaglog() {
    this.refToAffecte = [];
    this.refNoteAffected = [];
    this.display3 = false;
  }

  removeRef(tacheTape: ITacheEtape) {
    if (tacheTape.etapeActivitePpmId != null) {
      TacheComponent.moveElmementFromTab1ToTab2(tacheTape, this.tache.tacheEtapes,
        this.refNoteAffected);
    } else {
      const index = this.tache.tacheEtapes.indexOf(tacheTape);
      this.tacheSuivi.tacheEtapes.splice(index, 1);
    }
  }

  addRef(tacheEtape: IMembreCommission) {
    TacheComponent.moveElmementFromTab1ToTab2(tacheEtape,
      this.refNoteAffected, this.tache.tacheEtapes);
  }

  addSelectedRef() {
    if (this.refToAffecte.length > 0) {
      this.refToAffecte.forEach(value => {
        this.addRef(value);
        this.calculerEtatValidite();
      });
    }
  }

  removeSelectedRef() {
    if (this.refToRemove.length > 0) {
      this.refToRemove.forEach(value => {
        if (!value.estRealise) {
          this.removeRef(value);
          this.calculerEtatValidite();
        }
      });
    }
  }

  getExerciceId(): number {
    if (this.exercice !== null) {
      return this.exercice.id;
    } else {
      return 0;
    }
  }

  saisirEtape() {
    if (this.toSaisi) {
      this.toSaisi = false;
    } else {
      this.tacheEtape = new TacheEtape();
      this.toSaisi = true;
    }
  }

  ajouterEtapeSaisie() {
    if (this.tacheEtape) {
      if (!this.tache.tacheEtapes.some(value => value.etapeLibelle.includes(this.tacheEtape.etapeLibelle))) {
        this.tache.tacheEtapes.push(this.tacheEtape);
        this.calculerEtatValidite();
        this.tacheEtape = new TacheEtape();
      } else {
        this.showMessage('key1','info', 'AJOUT D\'UNE ETAPE', 'Une même existe dans votre Tâche')
      }
    }
  }

  suiviEtape(tache: ITache){
    if(!this.display4) {
      this.tache = tache;
      if (this.tache.membreCommissions.length > 0) {
        this.typeComission = this.typeComissions.find(value =>
          value.id === this.tache.membreCommissions[0].typeCommission.id)
      }
      this.loadRefNoteAffected();
      this.calculerEtatValidite();
      this.display4 = true;

    } else {
      this.tache.typeCommission = this.typeComission;
      this.tache.activiteId = this.activite.id;
      this.tache.etatAvancement = this.etaValide;

      if (this.tache.id !== undefined) {
        window.console.log("====================== tache a suivre =========");
        window.console.log(this.tache);
        window.console.log("===============================================");
        this.tacheService.update(this.tache).subscribe(() => {
          this.tache = new Tache();
          this.onActiviteChange();
          this.display4 = false;
        });
      }
      // this.display4 = false;
    }


  }

  /* **** ajout d'un nouveau membre***** */
  showDialoToaddMembre() {
    this.newMembreCommission = new MembreCommission();
    this.newMembreCommission.membre = new Membre();
    this.displayAddMembre = true;
  }

  annulerAjouMembre() {
    this.newMembreCommission = new MembreCommission();
    this.newMembreCommission.membre = new Membre();
    this.displayAddMembre = false;
  }

  saveMembre() {
    if (this.newMembreCommission) {
      if (!this.membreCommissionNoteAffected.some(value =>
        value.membre.email.includes(this.newMembreCommission.membre.email) &&
        value.membre.nom.includes(this.newMembreCommission.membre.nom) &&
        value.membre.telephone.includes(this.newMembreCommission.membre.telephone)
      )) {
        // this.newMembreCommission.typeCommissionId = this.typeComission.id;
        this.newMembreCommission.activiteId = this.activite.id;
        this.tache.membreCommissions.push(this.newMembreCommission);
        this.newMembreCommission = new MembreCommission();
        this.newMembreCommission.membre = new Membre();
        this.displayAddMembre = false;
      } else {
        this.showMessage('key1','info', 'AJOUT D\'UN MEMBRE', 'Un même meme existe dans votre Tâche');
      }
    }
  }

  calculerEtatValidite() {
    if (this.tache.tacheEtapes.length > 0) {
      const nbrEtape: number = this.tache.tacheEtapes.length;
      const nbEtapeValide: number = this.tache.tacheEtapes.filter(value => value.estRealise).length;
      this.etaValide = Math.trunc((nbEtapeValide / nbrEtape) * 100);
    } else {
      this.etaValide = 0;
    }
    this.tache.etatAvancement = this.etaValide;
    if (this.etaValide === 0) {
      this.tache.etat = Etat.INITIAL;
    }
    if (this.etaValide > 0 && this.etaValide < 100) {
      this.tache.etat = Etat.ENCOURS;
    }

    if (this.etaValide === 100) {
      this.tache.etat = Etat.TERMINE;
    }
  }

  /* ********************** */
  addType() {
    this.typeComission = new TypeCommission();
    this.addNewType = !this.addNewType;
  }

  calculDateFin(ref: ITacheEtape) {
    if (ref) {
      this.tacheService.calculateDateByDelai(ref)
        .subscribe((res: HttpResponse<ITacheEtape>) => {
          ref.dateFin = res.body.dateFin;
        });
    }
  }

  showMessage(theKey: string, sever: string, sum: string, det: string) {
    this.messageService.add({
      severity: sever,
      summary: sum,
      detail: det,
      key: theKey,
    });
  }

  retirerFihier(file) {
    this.confirmationService.confirm({
      message: 'Êtes vous sûr de vouloir supprimer?',
      accept: () => {
        this.fileManagerService.deleteFile(this.tache.id, TypeDossier.TACHE, file.fileName).subscribe((res: HttpResponse<any>) => {
          window.console.log(res.body);
          this.showMessage('key2','success', 'Suppression de fichier', 'Fichier supprimé avec succès');
        });
      }
    });
  }

  setFileData(event) {
    this.fichiers = event.target.files;
    if (event.target.files.length > 0) {
      this.files = [];
      for (let i = 0; i < event.target.files.length; i++) {
        const file = new Fichier();
        this.fileUtils.setFileData(event, file, 'file', false, i);
        file.fileName = event.target.files[i].name;
        this.files.push(file);
      }
      this.tache.files = this.files;
      window.console.log(this.tache.files);
    }
  }

  dowloadFichier(file) {
    this.dataUtils.downloadFile(file.fileContentType, file.file, file.fileName);
  }

  getFiles(tache: ITache) {
    this.tache = tache;
    this.isSaving2 = true;
    this.tacheService.find(this.tache.id).subscribe((res: HttpResponse<ITache>) => {
      this.fichiers = res.body.files;
      this.isSaving2 = false;
    });
  }

  tabViewChange(event) {
    this.taches = this.tacheTMP;
    if (event.index === 1) {
      this.taches = this.tacheTMP.filter(value => value.etat === Etat.INITIAL);
      this.numIndex = event.index;
    } else if (event.index === 2) {
      this.taches = this.tacheTMP.filter(value => value.etat === Etat.ENCOURS);
      this.numIndex = event.index;
    } else if (event.index === 3) {
      this.taches = this.tacheTMP.filter(value => value.etat === Etat.TERMINE);
      this.numIndex = event.index;
    } else if (event.index === 4) {
      this.taches = this.tacheTMP;
    }
    window.console.log(event);
    window.console.log("===============Numéro d'index====================");
    window.console.log(this.numIndex);
  }

  onTacheChange() {
    if(this.tache) {
      this.etapeTacheService.findTacheEtapeByTache(this.tache.id)
        .subscribe((res: HttpResponse<ITacheEtape[]>) => (this.taches = res.body));
    }
  }


  onEtapeChange() {
    if (this.tacheSuivi.typeTache) {
        if(this.tacheTMP2.length > 0) {
            this.taches = this.tacheTMP2.filter(value => value.typeTache.toString().includes(this.tacheSuivi.typeTache));
        }
    } else {
       this.onActiviteChange();
    }
  }
  deleteElement(tache: ITache) {
    this.confirmationService.confirm({
      header: 'Confirmation',
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        if (tache === null) {
          return;
        } else {
          tache.deleted = true;
          this.tacheService.update(tache).subscribe(
            () => {
              this.onActiviteChange();
              this.showMessage('key1','success', 'SUPPRESSION', 'Suppression effectuée avec succès !');
            },
            () => this.showMessage('key2','error', 'SUPPRESSION', 'Echec de la suppression !')
          );
        }
      }
    });
  }



}


