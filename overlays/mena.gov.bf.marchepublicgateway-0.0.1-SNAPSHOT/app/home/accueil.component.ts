import {AfterViewInit, Component, OnInit} from "@angular/core";
import {Account} from "app/core/user/account.model";
import {Subscription} from "rxjs";
import {Activite, IActivite} from "app/shared/model/microserviceppm/activite.model";
import {AccountService} from "app/core/auth/account.service";
import {ActiviteService} from "app/entities/microserviceppm/activite/activite.service";
import {DashboardService} from "app/entities/dashboard/dashboard.service";
import {HttpResponse} from "@angular/common/http";
import {JhiEventManager} from "ng-jhipster";
import {ReportService} from "app/entities/microservicedaccam/reports/reportService";

@Component({
  selector: 'jhi-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['home.scss']
})

export class AccueilComponent implements OnInit, AfterViewInit {

  account: Account;
  password: string;
  rememberMe: boolean;
  username: string;
  subscription: Subscription;
  data: any;
  data1: any;
  activites: IActivite[];
  selectedActivite: IActivite;
  isChartLine: boolean;
  accueil: any;
  type: string;
  display: boolean;
  authSubscription: Subscription;

  constructor(
    private accountService: AccountService,
    protected activiteService: ActiviteService,
    protected dashboardService: DashboardService,
    private eventManager: JhiEventManager,
    protected reportService: ReportService
    ) {
  }

  ngOnInit(): void {
    this.accueil = {};
    this.accueil.activitesEnCours = [];
    this.display = false;
    this.isChartLine = true;
    // this.accueil = {};
    this.type = 'D';
    this.selectedActivite = new Activite();
    this.accountService.identity().subscribe(account => {
      this.account = account;
      localStorage.setItem('Login',account.login);
     // this.loadAccueiInfo();
    });
    this.registerAuthenticationSuccess();
  }

  ngAfterViewInit(): void {
   // location.reload();
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', () => {
      this.accountService.identity().subscribe(account => {
        this.account = account;
      });
    });
  }

  loadAccueiInfo() {
    this.dashboardService.getAccueilInfo().subscribe((res: HttpResponse<any>) => {
      this.accueil = res.body;

      this.data1 = {
        labels: this.accueil.labels,
        datasets: [
          {
            data: this.accueil.data,
            backgroundColor: this.accueil.colors,
            hoverBackgroundColor: this.accueil.colors
          }]
      };

      this.data = {
        labels: this.accueil.labels,
        datasets: [
          {
            label: 'Etat',
            backgroundColor: this.accueil.colors,
            borderColor: '#1E88E5',
            data: this.accueil.data
          }
        ]
      };
    });
  }

  onIncateurClick(element: string) {
    this.selectedActivite = new Activite();
    if (element === 'EX') {
      {
        this.activites = this.accueil.activitesExecute;
        this.type = element;

      }
    } else if (element === 'EC') {
      {
        this.activites = this.accueil.activitesEnCours;
        this.type = element;
      }
    } else if (element === 'EL') {
      {
        this.activites = this.accueil.activitesEnLitige;
        this.type = element;
      }
    } else if (element === 'ER') {
      {
        this.activites = this.accueil.activitesResilie;
        this.type = element;
      }
    } else {
      {
        this.activites = this.accueil.allActivites;
        this.type = element;
      }
    }
  }

  showDetailActivite(activite: IActivite) {
    if(this.display) {
      this.display = false;
    } else {
      if (activite) {
        this.selectedActivite = activite;
      }
      this.display = true;
    }
  }

  changerGraphe(chartType: string) {
    switch (chartType) {
      case 'LINE':
        this.isChartLine = true;
        break;
      case 'BAR':
        this.isChartLine = false;
        break;
    }
  }

  onTabChange(event) {
    if(event.index === 1) {
      this.loadAccueiInfo();
    }
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  /* imprimeAllMarche() {
    this.reportService
      .imprimeAllMarche()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  }

  imprimeFinishedMarches() {
    this.reportService
      .imprimeFinishedMarches()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  }

  imprimeMarchesEnCours() {
    this.reportService
      .imprimeMarchesEnCours()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  }

  imprimeMarchesAyantLitige() {
    this.reportService
      .imprimeMarchesAyantLitige()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  }

  imprimeMarchesAyantContratResilie() {
    this.reportService
      .imprimeMarchesAyantContratResilie()
      .subscribe(response => window.open(URL.createObjectURL(new Blob([response], { type: 'application/pdf' })), '_blank'));
  } */

}
