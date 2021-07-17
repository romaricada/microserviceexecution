import {Moment} from 'moment';
import {ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {IFichier} from 'app/entities/file-manager/file-menager.model';

export interface IDeliberation {
  id?: number;
  description?: string;
  date?: Moment;
  valide?: boolean;
  lotId?: number;
  deleted?: boolean;
  candidatLots?: ICandidatLot[];
  files?: IFichier[];
}

export class Deliberation implements IDeliberation {
  constructor(
    public id?: number,
    public description?: string,
    public date?: Moment,
    public valide?: boolean,
    public lotId?: number,
    public deleted?: boolean,
    public candidatLots?: ICandidatLot[],
    public files?: IFichier[]
  ) {
    this.valide = this.valide || false;
    this.deleted = this.deleted || false;
  }
}
