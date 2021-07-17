import { Moment } from 'moment';
import {TypeReception} from 'app/shared/model/enumerations/TypeReception';

export interface IReception {
  id?: number;
  nom?: string;
  prenom?: string;
  telephone?: string;
  email?: string;
  date?: Moment;
  heure?: string;
  lieu?: string;
  activiteId?: number;
 lotId?: number;
  retirer?: boolean;
  deleted?: boolean;
  typeReception?: TypeReception;
}

export class Reception implements IReception {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public telephone?: string,
    public email?: string,
    public date?: Moment,
    public heure?: string,
    public lieu?: string,
    public activiteId?: number,
    public lotId?: number,
    public retirer?: boolean,
    public deleted?: boolean,
    public typeReception?: TypeReception,
  ) {
    this.retirer = this.retirer || false;
    this.deleted = this.deleted || false;
  }
}
