import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class SuiviExecutionService {
  public resourceUrl = SERVER_API_URL + 'services/microserviceexecution/api/SuiviExecutions';

  constructor(protected http: HttpClient) {}


}
