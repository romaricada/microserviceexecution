import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import {OrdreServiceService} from "app/entities/microserviceexecution/ordreService/ordreService.service";
import {IOrdreService, OrdreService} from "app/shared/model/microserviceexecution/ordre-service.model";
import {OdreServiceComponent} from "app/entities/microserviceexecution/ordreService/odreService.component";

@Injectable({ providedIn: 'root' })
export class OrdreServiceResolve implements Resolve<IOrdreService> {
  constructor(private service: OrdreServiceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrdreService> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((ordreservice: HttpResponse<OrdreService>) => ordreservice.body));
    }
    return of(new OrdreService());
  }
}

export const OrdreServiceRoute: Routes = [
  {
    path: '',
    component: OdreServiceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'marchepublicgatewayApp.microserviceexecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

