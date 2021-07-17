import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {IReception} from 'app/shared/model/microservicedaccam/reception.model';
import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared/util/request-util';

type EntityResponseType = HttpResponse<IReception>;
type EntityArrayResponseType = HttpResponse<IReception[]>;

@Injectable({ providedIn: 'root' })
export class ReceptionService {
  public resourceUrl = SERVER_API_URL + 'services/microservicedaccam/api/receptions';
  public resourceUrl1 = SERVER_API_URL + 'services/microservicedaccam/api/receptions/updateListe';

  constructor(protected http: HttpClient) {}

  create(reception: IReception): Observable<EntityResponseType> {
   // const copy = this.convertDateFromClient(reception);
    return this.http
      .post<IReception>(this.resourceUrl, reception, { observe: 'response' })
     // .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reception: IReception): Observable<EntityResponseType> {
   // const copy = this.convertDateFromClient(reception);
    return this.http
      .put<IReception>(this.resourceUrl, reception, { observe: 'response' })
    //  .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReception>(`${this.resourceUrl}/${id}`, { observe: 'response' })
     // .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReception[]>(this.resourceUrl, { params: options, observe: 'response' })
    //  .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

 /* protected convertDateFromClient(reception: IReception): IReception {
    const copy: IReception = Object.assign({}, reception, {
      date: reception.date != null && reception.date.isValid() ? reception.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reception: IReception) => {
        reception.date = reception.date != null ? moment(reception.date) : null;
      });
    }
    return res;
  }*/

  deleteAll(reception: IReception[]): Observable<EntityArrayResponseType> {
    return this.http.put<IReception[]>(this.resourceUrl1, reception, { observe: 'response' });
  }

  findReceptionByActivite(activiteId: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption({activiteId});
    return this.http.get<IReception[]>(this.resourceUrl + '/reception-by-activite', {params: options, observe: 'response'});
  }


}
