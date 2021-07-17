import {ITypeCaution} from 'app/shared/model/microservicedaccam/typeCaution.model';
import {ILot} from "app/shared/model/microservicedaccam/lot.model";

export interface ICaution {
  id?: number;
  libelle?: string;
  institutionBancaire?: string;
  montant?: number;
  validite?: number;
  deleted?: boolean;
  typeCautionId?: number;
  lotId?: number;
  lot?: ILot;
  typeCaution?: ITypeCaution;
}

export class Caution implements ICaution {
  constructor(
    public id?: number,
    public libelle?: string,
    public institutionBancaire?: string,
    public montant?: number,
    public validite?: number,
    public deleted?: boolean,
    public typeCautionId?: number,
    public lotId?: number,
    public typeCaution?: ITypeCaution,
  ) {
    this.deleted = this.deleted || false;
  }
}

