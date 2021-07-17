import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import {Avenant, IAvenant} from 'app/shared/model/microserviceexecution/avenant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AvenantService } from './avenant.service';
import {IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {ContratService} from "app/entities/microserviceexecution/contrat/contrat.service";
import {ITypeAvenant, TypeAvenant} from "app/shared/model/microserviceexecution/type-avenant.model";

@Component({
  selector: 'jhi-avenant',
  templateUrl: './avenant.component.html'
})
export class AvenantComponent implements OnInit, OnDestroy {
  avenants: IAvenant[];
  avenant: IAvenant;
  avenantSelected: IAvenant[];
  displayDelete: boolean;
  isSaving: boolean;
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  ajout = false;
  typeAvenant: ITypeAvenant;
  typeAvenants: ITypeAvenant[];
  contrats: IContrat[];
  contrat: IContrat;
  predicate: any;
  display: Boolean;
  previousPage: any;
  displaych: boolean;

  reverse: any;

  constructor(
    protected avenantService: AvenantService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected contratService: ContratService,

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
    this.avenantService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAvenant[]>) => this.paginateAvenants(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/avenant'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }
  supprimer() {
    this.displayDelete = true;
  }
  annuler() {
    this.avenant = new Avenant();
    this.display = false;

  }
  fermerFormulaire() {
    this.typeAvenant = new TypeAvenant();


    this.display = false;
  }
  ajouter(): void {
    if(!this.ajout) {
      this.typeAvenant = new TypeAvenant();
      this.ajout = true;
    }
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/avenant',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }
  ngOnInit() {
    this.loadAll();
    this.display = false;
    this.contrat = null;
    this.isSaving = false;
    this.displaych = false;
    this.avenant = new Avenant();
    this.typeAvenant = new TypeAvenant();
    this.registerChangeInAvenants();
  }
  add(avenant: IAvenant) {
    avenant === null ? (this.avenant = new Avenant()) : (this.avenant = avenant);
    this.display = true;
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAvenant) {
    return item.id;
  }

  registerChangeInAvenants() {
    this.eventSubscriber = this.eventManager.subscribe('avenantListModification', () => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAvenants(data: IAvenant[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.avenants = data;
  }

 /* loadContrat() {

  }

  deleteElement(avenant: IAvenant) {

  }

  save() {

  }*/
}
