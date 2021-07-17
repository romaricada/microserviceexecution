import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse} from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { MessageService, MenuItem } from 'primeng/api';
import { IPPM, PPM } from 'app/shared/model/microserviceppm/ppm.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PPMService } from './ppm.service';
import {IBesoin} from 'app/shared/model/microserviceppm/besoin.model';
import {BesoinService} from 'app/entities/microserviceppm/besoin/besoin.service';
import {NaturePrestationService} from 'app/entities/microserviceppm/nature-prestation/nature-prestation.service';
import {INaturePrestation, NaturePrestation} from 'app/shared/model/microserviceppm/nature-prestation.model';
import { IExerciceBudgetaire, ExerciceBudgetaire } from 'app/shared/model/microserviceppm/exercice-budgetaire.model';
import { ExerciceBudgetaireService } from '../exercice-budgetaire/exercice-budgetaire.service';
import { IModePassation, ModePassation } from 'app/shared/model/microserviceppm/mode-passation.model';
import { NaturePrestationModePassationService } from '../nature-prestation-mode-passation/nature-prestation-mode-passation.service';
import { ReferentielDelaiService } from '../referentiel-delai/referentiel-delai.service';
import { IReferentielDelai, ReferentielDelai } from 'app/shared/model/microserviceppm/referentiel-delai.model';
import { IActivite, Activite } from 'app/shared/model/microserviceppm/activite.model';
import { ActiviteService } from 'app/entities/microserviceppm/activite/activite.service';
import { PpmActivite } from 'app/shared/model/microserviceppm/ppm-activite.model';
import {IEtape} from 'app/shared/model/microserviceppm/etape.model';
import {DateCalcule} from 'app/shared/model/microserviceppm/date-calcule.model';
import {LigneBudgetaireService} from "app/entities/microserviceppm/ligne-budgetaire/ligne-budgetaire.service";
import {ILigneBudgetaire} from "app/shared/model/microserviceppm/ligne-budgetaire.model";

@Component({
  selector: 'jhi-ppm',
  templateUrl: './ppm.component.html',
  styleUrls: ['./ppm.component.scss']
})
export class PPMComponent implements OnInit, OnDestroy {
  ppm: IPPM;
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
  pPMSelected: IPPM[];
  display: boolean;
  besoin: IBesoin;
  besoins: IBesoin[];
  besoinTmp: IBesoin[];
  besoinTmp1: IBesoin[];
  besoinSelecteds: IBesoin[];
  naturePrestation: INaturePrestation;
  naturePrestations: INaturePrestation[];
  naturePrestationsTMP: INaturePrestation[];
  exercices: IExerciceBudgetaire[];
  exercice: IExerciceBudgetaire;
  exerciceBudgetaire: IExerciceBudgetaire;
  modePassation: IModePassation;
  totalMontantBesoin: number;
  checkedAll: boolean;
  referentielDelais: IReferentielDelai[];
  displayDate = false;
  ligneBudgetaires: ILigneBudgetaire[];
  ligneBudgetaire: ILigneBudgetaire;
  etapes: IEtape[] = [];
  debut: Date;
  fin: Date;
  periodeLancementAppel: Date;
  dateProblableDemaragePrestation: Date;
  priodeRemiseOffre: Date;
  dateProbableDemarage: Date;
  dateButoire: Date;
  etapesSelecteds: IEtape[] = [];
  montantDepenseEngageNonLiquide: number;
  activiteSelecteds: IActivite[];
  isSaving: boolean;

  calendriers: Object[];
  options: any;
  public eventMarkers: any;
  detailCalendar = false;
  detailListe = false;
  activite: IActivite;
  activites: IActivite[];
  clonedBesoin: { [s: string]: IBesoin; } = {};
  natureTmp: INaturePrestation;
  taskfield: Object;
  items: MenuItem[];

  libelle = '';

  constructor(
    protected pPMService: PPMService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected besoinService: BesoinService,
    protected naturePrestationService: NaturePrestationService,
    protected messageService: MessageService,
    protected exerciceBudgetaireService: ExerciceBudgetaireService,
    protected naturePrestationModePassationService: NaturePrestationModePassationService,
    protected ligneBudgetaireService: LigneBudgetaireService,
    protected referentielDelaiService: ReferentielDelaiService,
    protected activiteService: ActiviteService
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
    this.activiteService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IActivite[]>) => {
        this.paginateActivites(this.activites = res.body, res.headers)
        window.console.log("=================activités=========================");
        window.console.log(this.activites);
        window.console.log("=================activités=========================");
      });
  }
  filterData() {
    this.activiteService.findAllByAnneeExercice(isNaN(this.exercice.id) ? 0 : this.exercice.id).subscribe(
      (res: HttpResponse<IActivite[]>) => {
        this.activites = res.body;
      }, () => this.message('myKey1', 'error', 'Erreur de chargement', "Une erreur s'est produit lors du chargement des activités du PPM")
    )

  }
  loadBesoinByNature() {
    if (this.naturePrestation !== null && this.exercice !== undefined) {
      this.besoinService.findAllByNaturePrestation(isNaN(this.naturePrestation.id) ? 0 : this.naturePrestation.id, isNaN(this.exercice.id) ? 0 : this.exercice.id)
        .subscribe(
          (res: HttpResponse<IBesoin[]>) => { this.besoins = res.body;},
          () => this.message('myKey1', 'error', 'Erreur de chargement', "Une erreur s'est produit lors du chargement des besoins")
        );
    } else{
      this.besoins = [];
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/ppm'], {
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
      '/ppm',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  loadExercice() {
    this.exerciceBudgetaireService.query().subscribe(
      (res: HttpResponse<IExerciceBudgetaire[]>) => {
        this.exercices = res.body;
        /* const exer = this.exercices.find(exo => exo !== null && exo.active);
        this.exerciceBudgetaire = this.exercices.find(exo => exo !== null && exo.annee === (exer.annee + 1));
        if (this.exerciceBudgetaire) {
          this.pPMService.findPpmByExercice(this.exerciceBudgetaire.id).subscribe(
            (resPPM: HttpResponse<IPPM>) => {
              if(resPPM.body) {
                this.ppm = resPPM.body;
              } else {
                this.ppm = new PPM();
              }
            },
          () => this.message('myKey1', 'error', 'Erreur de chargement', "Une erreur s'est produit lors du chargement du PPM"));
        } */
      },
      () => this.message('myKey1', 'error', 'Erreur de chargement', "Une erreur s'est produit lors du chargement des exercices budgétaires")
    );
  }

  loaNaturePrestationList() {
    this.naturePrestationService.findAlList().subscribe((res: HttpResponse<INaturePrestation[]>) => {
      this.naturePrestationsTMP = res.body;
    });
  }
  loaNatureLigneBudgetaireList() {
    this.ligneBudgetaireService.findAllListeLigne().subscribe((res: HttpResponse<ILigneBudgetaire[]>) => {
      this.ligneBudgetaires = res.body;
    });
  }

  init(): void {
    this.exerciceBudgetaire = new ExerciceBudgetaire();
    this.exercice = new ExerciceBudgetaire();
    this.ppm = new PPM();
    this.exercices = [];
    this.activites = [];
    this.activiteSelecteds = [];
    this.activite = new Activite();
    this.priodeRemiseOffre = new Date();
    this.dateProbableDemarage = new Date();
    this.dateButoire = new Date();
    this.referentielDelais = [];
    this.checkedAll = true;
    this.totalMontantBesoin = 0;
    this.besoins = [];
    this.naturePrestations = [];
    this.naturePrestation = new NaturePrestation();
    this.besoinSelecteds = [];
    this.pPMSelected = [];
    this.isSaving = false;
    this.loadAll();
    this.registerChangeInPPMS();
    this.loadExercice();
    this.loadBesoin();
    this.loadNaturePrestation();
    this.loadBesoinByNature();
    this.loaNaturePrestationList();
    this.loaNatureLigneBudgetaireList();
    this.loadAllBesoin();
    // this.loadAll1();
    // this.loadCurentExercice();
    this.display = false;
    this.modePassation = new ModePassation();
    this.debut = new Date();
    this.fin = new Date();
    this.periodeLancementAppel= new Date();
    this.dateProblableDemaragePrestation= new Date();
    this.calendriers = [];
    this.activite.ppmActivite = new PpmActivite();
    this.activite.gestionnaireCredit = "DAF MENA";
    this.eventMarkers = [{
      day: Date.now(),
      label: 'Research phase'
    }];
  }

  ngOnInit() {
    this.init();
    this.loadCurentExercice();
    this.items = [
      {label: 'Liste', icon: 'pi pi-list', command: () => {
          this.detailListe = true;
          this.detailCalendar = false;
      }},
      {label: 'Diagramme', icon: 'pi pi-calendar', command: () => {
        this.detailListe = false;
        this.detailCalendar = true;
      }}
    ];

    this.taskfield = {
      id: 'TaskID',
      name: 'TaskName',
      startDate: 'StartDate',
      endDate: 'EndDate',
      child: 'subtasks'
    };

  }

  loadCurentExercice() {
    this.exerciceBudgetaireService
      .findCurrentExerciceByElaborerIsTrue()
      .subscribe(
        (res: HttpResponse<IExerciceBudgetaire>) => {
          // this.exercicebudgetaires = res.body;
          this.exercice = res.body;
          if (this.exercice === null) {
            this.exercice = new ExerciceBudgetaire();
          }
        },
        () => this.exercice = new ExerciceBudgetaire()
      );
  }
  loadAllBesoin() {
    this.besoinService
      .findAllByExercice(this.exercice.id)
      .subscribe(
        (res: HttpResponse<IBesoin[]>) => {
          this.besoinTmp1 = res.body;
        });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPPM) {
    return item.id;
  }

  registerChangeInPPMS() {
    this.eventSubscriber = this.eventManager.subscribe('pPMListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateActivites(data: IPPM[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.activites = data;
  }

  protected paginateBesoins(data: IBesoin[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.besoins = data;
    this.besoinTmp = data;
  }

   loadBesoin() {
    this.besoinService.findAllByExerciceAndUsedFalse(isNaN(this.exerciceBudgetaire.id) ? 0 : this.exerciceBudgetaire.id)
      .subscribe((res: HttpResponse<IBesoin[]>) => {
        this.besoins = res.body;
      }, () => this.message('myKey3', 'error', 'erreur de chargement','erreur de chargement des sous PPM '));
  }

  loadNaturePrestation() {
    this.naturePrestationService.query().subscribe(
      (res: HttpResponse<INaturePrestation[]>) => (this.naturePrestations = res.body)
    )
  }
  /* onNatureChange() {
    if(this.naturePrestation && this.naturePrestation != null) {
      this.besoins = this.besoinTmp.filter(value => value.naturePrestationId === this.naturePrestation.id);
    }
    else {
      this.besoins = this.besoinTmp;
    }
  } */
  add(activite: IActivite) {
    if (activite === null) {

      this.ppm = new PPM();
      this.activite = new Activite();
      this.activite.ppm = new PPM();
      this.activite.ppmActivite = new PpmActivite();

      this.pPMService.generateCodePPM().subscribe(
        (res: HttpResponse<IPPM>) => {
          // this.codePPM = res.body;
          // window.console.log(res.body);
          this.ppm = res.body;

        }, () => this.message('myKey3', 'error', 'erreur de génération','erreur de génération du code PPM ')
      );

      this.activiteService.generateCodeActivite().subscribe(
        (resAct: HttpResponse<IActivite>) => {
          // this.codeActivite = res.body;

          this.activite.codeLignePlan = resAct.body.codeLignePlan;
          this.activite.gestionnaireCredit = "DAF MENA";
        }, () => this.message('myKey3', 'error', 'erreur de génération','erreur de génération du code de l\'Activité')
      );

    } else {
      window.console.log(activite);
      this.activite = activite;
      this.ppm = activite.ppm;
      this.activite.ppmActivite = activite.ppmActivite;
      this.modePassation = activite.modePassation;
      this.naturePrestation = activite.naturePrestation;
      this.loadCurentExercice();

      /* this.dateButoire = activite.ppmActivite.dateButtoire;
      this.dateProbableDemarage = activite.ppmActivite.date.toDate();
      this.dateProblableDemaragePrestation = activite.ppmActivite.dateProblableDemaragePrestation;
      this.periodeLancementAppel = activite.ppmActivite.periodeLancementAppel;
      this.priodeRemiseOffre = activite.ppmActivite.periodeRemiseOffre; */


      /* this.referentielDelaiService.findReferentielDelaiByModePassationId(this.modePassation.id).subscribe(
        (res: HttpResponse<IReferentielDelai[]>) => {
          this.referentielDelais = res.body;
        }, () => this.message('myKey3','error', 'erreur de chargement', "Une erreur s'est produite lors du chargement de délais de réfférence")
      ); */

      this.pPMService.getEtapePpmActivite(this.activite.ppmActivite.id, this.modePassation.id).subscribe(
        (res: HttpResponse<IReferentielDelai[]>) => {
          this.referentielDelais = res.body;
          this.setCalendar();
        }, () => this.message('myKey3','error', 'erreur de chargement', "Une erreur s'est produite lors du chargement de délais de réfférence")
      );

      this.loadBesoinByNature();

      this.besoinSelecteds = activite.besoins;
      window.console.log('m   m   m   m   m     ' + this.besoinSelecteds.length + '    m   m   m   m');
      // this.besoinSelecteds = activite.besoins;

    }
    // this.loadBesoin();
    this.display = true;
  }

  annuler() {
    this.display = false;
    this.modePassation = new ModePassation();
    this.referentielDelais = [];
    this.isSaving = false;
  }
  message(cle: string, severite: string, resume: string, detaille: string) {
    this.messageService.add({ key: cle, severity: severite, summary: resume, detail: detaille });
  }

  selectBesoins() {
    if(this.besoinSelecteds.length > 0) {
      if (this.besoinSelecteds.length === 1) {
        this.activite.nomActivite = this.besoinSelecteds[0].libelle;
      }
      this.besoinSelecteds.forEach(besoin => {
        this.totalMontantBesoin = this.totalMontantBesoin + besoin.montantEstime;

        window.console.log('=========================   ' + this.totalMontantBesoin);

      });
      if (this.totalMontantBesoin > 0) {
        this.loadMontant(this.naturePrestation.id, this.totalMontantBesoin);
      } else {
        this.message('myKey2','error', 'erreur sur le montant', 'votre activité doit avoir un montant valide');
      }
    } else {
      this.totalMontantBesoin = 0;
    }
  }

  loadMontant(naturePrestationId: number, totalMontantBesoin: number) {

    this.naturePrestationModePassationService.getAllNaturePrestationModePassations(naturePrestationId, totalMontantBesoin)
        .subscribe(
          (res: HttpResponse<IModePassation>) => {
            if (res.body !== null) {
             this.modePassation = res.body;
             this.loadReferentielDelais();
            } else {
              this.message('myKey2','error', 'erreur sur le mode de passation', 'nous avons pas pu charger le mode de passation conforme à votre montant et à la nature de votre besoin');
              this.modePassation = new ModePassation();
              window.console.log('-------------------------->' + this.modePassation.libellePassation);
            }
          }, () => {
            this.message('myKey2','error', 'erreur sur le mode de passation', 'nous avons pas pu charger le mode de passation conforme à votre montant et à la nature de votre besoin');
            this.modePassation = new ModePassation();
          }
        );
  }

  loadReferentielDelais() {
    this.referentielDelaiService.findReferentielDelaiByModePassationId(this.modePassation.id).subscribe(
      (res: HttpResponse<IReferentielDelai[]>) => {
        this.referentielDelais = res.body;
        window.console.log(':::::::::::::::::::::::::      ' + this.referentielDelais.length);
      }, () => this.message('', '', '', '')
    )
  }

  /* onDateChange() {
    const ref: IReferentielDelai = new ReferentielDelai();
    ref.debut = this.debut;
    ref.modePassation.id = this.modePassation.id;
    window.console.log(ref);
    this.referentielDelaiService.getReferentielDelaiByModePassationId(ref)
    .subscribe(
      (res: HttpResponse<IReferentielDelai[]>) => {
        this.referentielDelais = res.body;
        if (this.referentielDelais !== undefined && this.referentielDelais.length > 0) {
          this.debut = this.referentielDelais[0].debut;
          this.fin = this.referentielDelais[this.referentielDelais.length - 1].fin;
          this.setCalendar();
        }

      }, () => this.message('mykey2', 'error', 'erreur de chargement', 'Erreur de chargement des seuils')
    )
  } */

  setCalendar() {

    if (this.referentielDelais.length > 0) {
      this.referentielDelais.forEach(ref => {

        // const calend = {'title': ref.etapeLibelle, 'start': ref.debut.toString(), 'end': ref.fin.toString()}; Duration: 5, Progress: 30
        this.calendriers = this.calendriers.concat(
          {
            TaskID: ref.id, TaskName: '' + ref.etape.libelle, StartDate: ref.debut, EndDate: ref.fin, Duration: ref.duration, Progress: ref.progress
          }
        );
      });

      // this.fc.dateAlignment.
      // this.options = {...this.options,  weekends: false};

      window.console.log(this.calendriers);
    }
  }

  save() {
    this.isSaving = true;
    this.activite.deleted = false;
    this.activite.passationId = this.modePassation.id;
    this.activite.naturePrestationId = this.naturePrestation.id;
    this.activite.total = this.totalMontantBesoin;
    this.activite.besoins = this.besoinSelecteds;
    this.activite.reported = false;
    this.activite.referentielDelais = this.referentielDelais;

    this.ppm.idExercice = this.exercice.id;

    this.activite.ppm = this.ppm;


    this.periodeLancementAppel= new Date();
    this.dateProblableDemaragePrestation= new Date();

    /* this.activite.ppmActivite.dateProblableDemaragePrestation = moment(this.dateProblableDemaragePrestation);
    this.activite.ppmActivite.periodeLancementAppel = moment(this.periodeLancementAppel);
    this.activite.ppmActivite.dateButtoire = moment(this.dateButoire);
    this.activite.ppmActivite.periodeRemiseOffre = moment(this.priodeRemiseOffre); */
    window.console.log(this.activite);
    if (this.activite.id !== undefined) {
      this.subscribeToSaveResponse(this.activiteService.update(this.activite));
    } else {
      // window.console.log(this.referentielDelais);
      this.subscribeToSaveResponse(this.activiteService.create(this.activite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivite>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.display = false;
    this.ppm = new PPM();
    this.activite = new Activite();
    this.activite.ppmActivite = new PpmActivite();
    this.activite.ppm = new PPM();
    this.referentielDelais = [];
    this.isSaving = false;
    this.exercice = new ExerciceBudgetaire();
    this.loadAll();
    // this.besoins = [];
  }

  protected onSaveError() {
    this.message('mykey2', 'error', "Erreur d'enregistrement", "Une erreur s'est produite lors de l'enregistrement de votre activité");
  }

  onRowEditInit(besoin: IBesoin) {
    this.natureTmp = new NaturePrestation();
    this.clonedBesoin[besoin.id] = {...besoin};
  }

  onRowEditSave(besoin: IBesoin) {
    besoin.naturePrestationId = this.natureTmp.id;
    this.besoinService.update(besoin).subscribe(
      (res: HttpResponse<IBesoin>) => {
        besoin = res.body;
        delete this.clonedBesoin[besoin.id];
      }, () => this.message('myKey2','success', 'Success', 'Besoin mis à jour avec succès')
    );

  }

  onRowEditCancel(besoin: IBesoin, index: number) {
      this.besoins[index] = this.clonedBesoin[besoin.id];
      delete this.clonedBesoin[besoin.id];
  }

  calculerDalai(): void {

    if (this.libelle !== null && this.naturePrestation !== null && this.modePassation !== null) {
      const ref: IReferentielDelai = new ReferentielDelai();
      ref.modePassation = this.modePassation;
      ref.fin = this.activite.ppmActivite.dateButtoire;
      ref.etapes = this.etapesSelecteds;
      ref.observation = this.libelle;
      this.referentielDelaiService.getReferentielDelaiByModePassationId(ref).subscribe(
        (res: HttpResponse<IReferentielDelai[]>) => {
          this.referentielDelais = res.body;
          this.displayDate = false;
          this.etapes = [];
          this.etapesSelecteds = [];
          this.setDateCalcule();
          this.setCalendar();
        }, () => this.message('myKey2','error', 'ERREUR', 'Nous n\'navons pas pu caluculer vos delais')
      );
    }
  }

  setDateCalcule() {
    if (this.referentielDelais) {
      this.referentielDelaiService.getDateCalcule(this.referentielDelais).subscribe(
        (res: HttpResponse<DateCalcule[]>) => {
          if (res.body !== undefined) {

            if (res.body.find(r => r.libelle === 'PERIODELANCEMENTAPPELCONCURENCE') !== undefined) {
              this.activite.ppmActivite.periodeLancementAppel = res.body.find(r => r.libelle === 'PERIODELANCEMENTAPPELCONCURENCE').date;
              // this.activite.ppmActivite.periodeLancementAppel = res.body.find(r => r.libelle === 'PERIODELANCEMENTAPPELCONCURENCE').date;
              // window.console.log('PERIODELANCEMENTAPPELCONCURENCE================  ' + this.periodeLancementAppel + '    ================');
            } // else { this.periodeLancementAppel = new Date(); }

            if (res.body.find(r => r.libelle === 'PERIODEREMISEOFFRES') !== undefined) {
              this.activite.ppmActivite.periodeRemiseOffre = res.body.find(r => r.libelle === 'PERIODEREMISEOFFRES').date;
              // this.activite.ppmActivite.periodeRemiseOffre = moment(res.body.find(r => r.libelle === 'PERIODEREMISEOFFRES').date);
              // window.console.log('PERIODEREMISEOFFRES================  ' + this.priodeRemiseOffres + '    ================');
            } // else { this.priodeRemiseOffre = new Date(); }

            if (res.body.find(r => r.libelle === 'DATEPROBABLEDEMARAGEPRESTATIONS') !== undefined) {
              this.activite.ppmActivite.dateProblableDemaragePrestation = res.body.find(r => r.libelle === 'DATEPROBABLEDEMARAGEPRESTATIONS').date;
              // this.activite.ppmActivite.dateProblableDemaragePrestation = moment(res.body.find(r => r.libelle === 'DATEPROBABLEDEMARAGEPRESTATIONS').date);
              // window.console.log('DATEPROBABLEDEMARAGEPRESTATIONS================  ' + this.dateProblableDemaragePrestation + '    ================');
            } // else { this.dateProblableDemaragePrestation = new Date(); }
            // this.priodeRemiseOffres = res.body.find(r => r.libelle === 'PERIODEREMISEOFFRES').date;
            // this.dateProblableDemaragePrestation = res.body.find(r => r.libelle === 'DATEPROBABLEDEMARAGEPRESTATIONS').date;
            // this.periodeLancementAppel = res.body.find(r => r.libelle === 'PERIODELANCEMENTAPPELCONCURENCE').date;
          }
        }
      );
    }
  }

  viewMore(): void {
    this.detailCalendar = false;
    this.detailListe = false;
  }

  supprimer() {
    this.pPMService.deleteAll(this.activiteSelecteds).subscribe(
      (res: HttpResponse<string>) => {
        window.console.log('res  :  ' + res.body);
        if (res.body.toUpperCase() !== 'OK') {
          this.message('myKey2','error', 'Erreur', 'Vous ne pouvez pas supprimer la liste sélectionnée !');
        }
      }, () => window.console.log('Erreur de suppression')
    );

  }
}

