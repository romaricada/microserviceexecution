import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Liquidation } from 'app/shared/model/microserviceexecution/liquidation.model';
import { LiquidationService } from './liquidation.service';
import { LiquidationComponent } from './liquidation.component';
import { LiquidationDetailComponent } from './liquidation-detail.component';
import { LiquidationUpdateComponent } from './liquidation-update.component';
import { LiquidationDeletePopupComponent } from './liquidation-delete-dialog.component';
import { ILiquidation } from 'app/shared/model/microserviceexecution/liquidation.model';

@Injectable({ providedIn: 'root' })
export class LiquidationResolve implements Resolve<ILiquidation> {
  constructor(private service: LiquidationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILiquidation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((liquidation: HttpResponse<Liquidation>) => liquidation.body));
    }
    return of(new Liquidation());
  }
}

export const liquidationRoute: Routes = [
  {
    path: '',
    component: LiquidationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'marchepublicgatewayApp.microserviceexecutionLiquidation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LiquidationDetailComponent,
    resolve: {
      liquidation: LiquidationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'marchepublicgatewayApp.microserviceexecutionLiquidation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LiquidationUpdateComponent,
    resolve: {
      liquidation: LiquidationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'marchepublicgatewayApp.microserviceexecutionLiquidation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LiquidationUpdateComponent,
    resolve: {
      liquidation: LiquidationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'marchepublicgatewayApp.microserviceexecutionLiquidation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const liquidationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LiquidationDeletePopupComponent,
    resolve: {
      liquidation: LiquidationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'marchepublicgatewayApp.microserviceexecutionLiquidation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
