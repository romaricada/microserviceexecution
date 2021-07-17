import { ReceptionComponent } from './../reception/reception.component';
//import { EtatComponent } from './../etat/etat.component';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {createRequestOption} from "app/shared/util/request-util";


// import { IPvDeliberation } from 'app/shared/model/microdaccam/pv-deliberation';

@Injectable({
    providedIn: 'root'
})
export class ReportService {
    public resourceUrl = SERVER_API_URL + 'services/microservicedaccam/api/reporting';

    constructor(protected http: HttpClient) {}


    imprimeAllMarche(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/all-activite`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }
    imprimeFinishedMarches(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/finished-activities`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }

    imprimeMarchesEnCours(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/activities-en-cours`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }
    imprimeMarchesAyantLitige(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/activities-ayant-litige`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }

    imprimeMarchesAyantContratResilie(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/activities-ayant-contrat-resilie`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }

    ImprimerOffre(): Observable<ArrayBuffer> {
        return this.http.get(`${this.resourceUrl}/recu-paiement`, {
            params: createRequestOption({}),
            responseType: 'arraybuffer'
        });
    }

    

}
