import { Moment } from 'moment';
import { IContrat } from 'app/shared/model/microserviceexecution/contrat.model';
import {IFichier} from 'app/entities/file-manager/file-menager.model';

export interface ILiquidation {
  id?: number;
  contratId?: number;
  montant?: number;
  activiteId?: number;
  date?: Moment;
  deleted?: boolean;
  contrat?: IContrat;
  files?: IFichier[];
}

export class Liquidation implements ILiquidation {
  constructor(
    public id?: number,
    public contratId?: number,
    public activiteId?: number,
    public montant?: number,
    public date?: Moment,
    public deleted?: boolean,
    public contrat?: IContrat,
    public files?: IFichier[]
    ) {
    this.deleted = this.deleted || false;
  }
}
