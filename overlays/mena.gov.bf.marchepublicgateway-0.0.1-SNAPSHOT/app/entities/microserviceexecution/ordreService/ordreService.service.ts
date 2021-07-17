import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared/util/request-util';
import {IOrdreService} from "app/shared/model/microserviceexecution/ordre-service.model";
import {IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {ICandidatLot} from "app/shared/model/microservicedaccam/candidat-lot.model";


type EntityResponseType = HttpResponse<IOrdreService>;
type EntityArrayResponseType = HttpResponse<IOrdreService[]>;

type EntityResponseType1 = HttpResponse<IContrat>;
type EntityArrayResponseType1 = HttpResponse<IContrat[]>;
type EntityResponseType2 = HttpResponse<ICandidatLot>;

@Injectable({providedIn: 'root'})
export class OrdreServiceService {

  public resourceUrl = SERVER_API_URL + 'services/microserviceexecution/api/ordre-services';
  public resourceUrl1 = SERVER_API_URL + 'services/microserviceexecution/api/contrats';

  constructor(protected http: HttpClient) {
  }

  create(ordreService: IOrdreService): Observable<EntityResponseType> {
    return this.http.post<IOrdreService>(this.resourceUrl, ordreService, {observe: 'response'});
  }

  update(ordreService: IOrdreService): Observable<EntityResponseType> {
    return this.http.put<IOrdreService>(this.resourceUrl, ordreService, {observe: 'response'});
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrdreService>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrdreService[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  findContratByCandidat(candidatId: number): Observable<EntityArrayResponseType1> {
    const options = createRequestOption({candidatId});
    return this.http.get<IContrat[]>(this.resourceUrl1 + '/contratByCandidat', {params: options, observe: 'response'});
  }

  findContratByOrdreService(id: number): Observable<EntityArrayResponseType1> {

    const option = createRequestOption({id});
    return this.http.get<IContrat[]>(this.resourceUrl + '/findContratbyordre', {params: option, observe: 'response'});
  }

  findCandidatLotbyContrat(contratId?: number): Observable<EntityResponseType2> {
    const options = createRequestOption({contratId});
    return this.http.get<ICandidatLot>(this.resourceUrl1 + '/candidatbyContrat', {params: options, observe: 'response'});
  }

}
