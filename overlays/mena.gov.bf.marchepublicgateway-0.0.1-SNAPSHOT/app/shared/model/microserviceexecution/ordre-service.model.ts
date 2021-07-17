import {TypeOs} from "app/shared/model/enumerations/TypeOs";
import {EtatOs} from "app/shared/model/enumerations/EtatOs";
import {IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {Moment} from "moment";
import {IFichier} from "app/entities/file-manager/file-menager.model";

export interface IOrdreService {
  id?: number;
  libelle?: string;
  deleted?: boolean;
  type?: TypeOs;
  etat?: EtatOs;
  delai?: number;
  dateDebut?: Moment;
  contratId?: number;
  contrats?: IContrat[];
  files?: IFichier[]
}

export class OrdreService implements IOrdreService {
  constructor(
    public id?: number,
    public libelle?: string,
    public deleted?: boolean,
    public type?: TypeOs,
    public etat?: EtatOs,
    public delai?: number,
    public dateDebut?: Moment,
    public contratId?: number,
    public files?: IFichier[],
    public contrats?: IContrat[]
  ) {
    this.deleted = this.deleted || false;
  }
}
