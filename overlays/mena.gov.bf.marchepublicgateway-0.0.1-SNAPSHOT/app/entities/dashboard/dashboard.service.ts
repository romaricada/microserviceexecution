import {Injectable} from "@angular/core";
import {SERVER_API_URL} from "app/app.constants";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class DashboardService {
  public resourceUrl = SERVER_API_URL + 'services/microservicedaccam/api/dashboard';

  constructor(protected http: HttpClient) {
  }

  getAccueilInfo(): Observable<any> {
    return this.http.get<any>(this.resourceUrl, {observe: 'response'});
  }
}
