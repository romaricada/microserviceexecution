import { IMembreCommission } from 'app/shared/model/microservicedaccam/membre-commission.model';
import {IUniteAdministrative} from "app/shared/model/microserviceppm/unite-administrative.model";

export interface IMembre {
  id?: number;
  nom?: string;
  prenom?: string;
  telephone?: string;
  email?: string;
  directionId?: number;
  direction?: IUniteAdministrative;
  post?: string;
  deleted?: boolean;
  cases?:boolean;
  commissions?: IMembreCommission[];
}

export class Membre implements IMembre {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public telephone?: string,
    public email?: string,
    public directionId?: number,
    public  post?: string,
    public cases?: boolean,
    public deleted?: boolean,
    public direction?: IUniteAdministrative,
    public commissions?: IMembreCommission[]
  ) {
    this.deleted = this.deleted || false;
    this.cases = false;
  }
}
