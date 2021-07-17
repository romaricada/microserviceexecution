import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared/util/request-util';
import {IPieceCandidat} from 'app/shared/model/microservicedaccam/piece-candidat.model';

type EntityResponseType = HttpResponse<IPieceCandidat>;
type EntityArrayResponseType = HttpResponse<IPieceCandidat[]>;

@Injectable({providedIn: 'root'})
export class PieceCandidatService {
  public resourceUrl = SERVER_API_URL + 'services/microservicedaccam/api/piece-candidats';

  constructor(protected http: HttpClient) {
  }

  create(pieceCandidat: IPieceCandidat): Observable<EntityResponseType> {
    return this.http.post<IPieceCandidat>(this.resourceUrl, pieceCandidat, {observe: 'response'});
  }

  update(pieceCandidat: IPieceCandidat): Observable<EntityResponseType> {
    return this.http.put<IPieceCandidat>(this.resourceUrl, pieceCandidat, {observe: 'response'});
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPieceCandidat>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPieceCandidat[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  initPieceCandidat(): Observable<EntityArrayResponseType> {
    return this.http.get<IPieceCandidat[]>(this.resourceUrl + '/init-piece-candidat', {observe: 'response'});
  }
}
