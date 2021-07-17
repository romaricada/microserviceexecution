import {IContrat} from 'app/shared/model/microserviceexecution/contrat.model';
import {IDecisionContentieux} from 'app/shared/model/microserviceexecution/decision-contentieux.model';
import {IFichier} from 'app/entities/file-manager/file-menager.model';

export interface IContentieux {
  id?: number;
  contratId?: number;
  objet?: string;
  deleted?: boolean;
  contrat?: IContrat;
  decisionContentieux?: IDecisionContentieux;
  files?: IFichier[];
}

export class Contentieux implements IContentieux {
  constructor(
    public id?: number,
    public contratId?: number,
    public objet?: string,
    public deleted?: boolean,
    public contrat?: IContrat,
    public decisionContentieux?: IDecisionContentieux,
    public files?: IFichier[]
  ) {
    this.deleted = this.deleted || false;
  }
}
