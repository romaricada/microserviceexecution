import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDecisionContentieux } from 'app/shared/model/microserviceexecution/decision-contentieux.model';

type EntityResponseType = HttpResponse<IDecisionContentieux>;
type EntityArrayResponseType = HttpResponse<IDecisionContentieux[]>;

@Injectable({ providedIn: 'root' })
export class DecisionContentieuxService {
  public resourceUrl = SERVER_API_URL + 'services/microserviceexecution/api/decision-contentieuxes';

  constructor(protected http: HttpClient) {}

  create(decisionContentieux: IDecisionContentieux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(decisionContentieux);
    return this.http
      .post<IDecisionContentieux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(decisionContentieux: IDecisionContentieux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(decisionContentieux);
    return this.http
      .put<IDecisionContentieux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDecisionContentieux>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDecisionContentieux[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(decisionContentieux: IDecisionContentieux): IDecisionContentieux {
    const copy: IDecisionContentieux = Object.assign({}, decisionContentieux, {
      date: decisionContentieux.date != null && decisionContentieux.date.isValid() ? decisionContentieux.date.format(DATE_FORMAT) : null
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
      res.body.forEach((decisionContentieux: IDecisionContentieux) => {
        decisionContentieux.date = decisionContentieux.date != null ? moment(decisionContentieux.date) : null;
      });
    }
    return res;
  }
}
