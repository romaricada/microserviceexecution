import {IReclamation} from 'app/shared/model/microservicedaccam/reclamation.model';
import {Moment} from "moment";
import {IFichier} from "app/entities/file-manager/file-menager.model";

export interface IDecision {
  id?: number;
  structure?: string;
  decision?: string;
  deleted?: boolean;
  reclamation?: IReclamation;
  reference?: string;
  date?: Moment;
  files?: IFichier[];
}

export class Decision implements IDecision {
  constructor(
    public id?: number,
    public structure?: string,
    public decision?: string,
    public deleted?: boolean,
    public reclamation?: IReclamation,
    public reference?: string,
    public date?: Moment,
    public files?: IFichier[]
  ) {
    this.deleted = this.deleted || false;
  }
}
