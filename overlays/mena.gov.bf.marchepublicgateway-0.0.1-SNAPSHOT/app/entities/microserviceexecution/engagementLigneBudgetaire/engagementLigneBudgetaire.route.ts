import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Routes} from "@angular/router";
import {EngagementLigneBudgetaireService} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.service";
import {
  EngagementLigneBudgetaire,
  IEngagementLigneBudgetaire
} from "app/shared/model/microserviceexecution/engagementLigneBudgetaire.model";
import {Observable, of} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {JhiResolvePagingParams} from "ng-jhipster";
import {UserRouteAccessService} from "app/core/auth/user-route-access-service";
import {map} from "rxjs/operators";
import {EngagementLigneBudgetaireComponent} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.component";

@Injectable({ providedIn: 'root' })
export class EngagementLigneBudgetaireResolve implements Resolve<IEngagementLigneBudgetaire> {
  constructor(private service: EngagementLigneBudgetaireService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEngagementLigneBudgetaire> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((engagementLigneBudgetaire: HttpResponse<EngagementLigneBudgetaire>) => engagementLigneBudgetaire.body));
    }
    return of(new EngagementLigneBudgetaire());
  }
}

export const engagementLigneBudgetaireRoute: Routes = [
  {
    path: '',
    component: EngagementLigneBudgetaireComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService]
  }
];

