import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICandidat } from 'app/shared/model/microservicedaccam/candidat.model';

type EntityResponseType = HttpResponse<ICandidat>;
type EntityArrayResponseType = HttpResponse<ICandidat[]>;

@Injectable({ providedIn: 'root' })
export class CandidatService {
  public resourceUrl = SERVER_API_URL + 'services/microservicedaccam/api/candidats';

  constructor(protected http: HttpClient) {}

  create(candidat: ICandidat): Observable<EntityResponseType> {
    return this.http.post<ICandidat>(this.resourceUrl, candidat, { observe: 'response' });
  }

  update(candidat: ICandidat): Observable<EntityResponseType> {
    return this.http.put<ICandidat>(this.resourceUrl, candidat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICandidat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  /* query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidat[]>(this.resourceUrl, { params: options, observe: 'response' });
  } */

  findAllByActivite(activiteId: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption({activiteId});
    return this.http.get<ICandidat[]>(this.resourceUrl+'/all-by-activite', { params: options, observe: 'response' });
  }

 /* findAllCandidat(activiteId: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption({activiteId});
    return this.http.get<ICandidat[]>(this.resourceUrl+'/all-with-lot', { params: options, observe: 'response' });
  } */

  query(activiteId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidat[]>(this.resourceUrl + '?activiteId=' + activiteId, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

}
