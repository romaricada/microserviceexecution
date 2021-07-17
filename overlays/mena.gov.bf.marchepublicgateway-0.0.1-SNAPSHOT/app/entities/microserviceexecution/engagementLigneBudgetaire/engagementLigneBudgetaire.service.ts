import {Injectable} from "@angular/core";
import {SERVER_API_URL} from "app/app.constants";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IEngagementLigneBudgetaire} from "app/shared/model/microserviceexecution/engagementLigneBudgetaire.model";
import {createRequestOption} from "app/shared/util/request-util";
import {IBesoinLigneBudgetaire} from "app/shared/model/microserviceppm/besoin-ligne-budgetaire.model";

type EntityResponseType = HttpResponse<IEngagementLigneBudgetaire>;
type EntityArrayResponseType = HttpResponse<IEngagementLigneBudgetaire[]>;

@Injectable({ providedIn: 'root' })
export class EngagementLigneBudgetaireService {
  public resourceUrl = SERVER_API_URL + 'services/microserviceexecution/api/engagementLigneBudgetaires';
  public resourceUrl1 = SERVER_API_URL + 'services/microserviceexecution/api/engagements/engagement-besoin-ligne-budget';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEngagementLigneBudgetaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
  }

  findAllLigneByEngagement(engagementId: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption({engagementId});
    return this.http.get<IEngagementLigneBudgetaire[]>(this.resourceUrl + '/ligne_budgetaire_by_engagement', { params: options, observe: "response"})
  }

  findAllBeoin(engagementId: number, activiteId: number): Observable<HttpResponse<IBesoinLigneBudgetaire[]>> {
    const options = createRequestOption({engagementId, activiteId});
    return this.http.get<IBesoinLigneBudgetaire[]>(this.resourceUrl + '/find_besoin', { params: options, observe: "response"})
  }


  montantEngage(ligneId: number): Observable<HttpResponse<number>> {
    const options = createRequestOption({ligneId});
    return this.http.get<number>(this.resourceUrl + '/montant-ligne-enga', {params: options, observe: 'response'});
  }

}
