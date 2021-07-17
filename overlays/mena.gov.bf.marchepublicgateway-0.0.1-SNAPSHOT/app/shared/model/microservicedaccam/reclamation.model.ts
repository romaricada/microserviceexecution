import {Moment} from 'moment';
import {IReclamationCandidatLot} from "app/shared/model/microservicedaccam/reclamation-candidat-lot.model";
import {IFichier} from "app/entities/file-manager/file-menager.model";

export interface IReclamation {
  id?: number;
  date?: Moment;
  lotId?: number;
  description?: string;
  deleted?: boolean;
  reclamationCandidatLots?: IReclamationCandidatLot[];
  files?: IFichier[];
}

export class Reclamation implements IReclamation {
  constructor(
    public id?: number,
    public date?: Moment,
    public lotId?: number,
    public description?: string,
    public deleted?: boolean,
    public reclamationCandidatLots?: IReclamationCandidatLot[],
    public  files?: IFichier[]
  ) {
    this.deleted = this.deleted || false;
  }
}
