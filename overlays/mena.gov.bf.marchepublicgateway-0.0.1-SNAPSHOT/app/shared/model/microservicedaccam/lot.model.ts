import {ICandidatLot} from 'app/shared/model/microservicedaccam/candidat-lot.model';
import {ICandidatCautionLot} from 'app/shared/model/microservicedaccam/candidatCautionLot.model';
import {ICaution} from "app/shared/model/microservicedaccam/caution.model";

export interface ILot {
  id?: number;
  cautionId?: number;
  libelle?: string;
  description?: string;
  activiteId?: number;
  deleted?: boolean;
  infructueux?: boolean;
  dossierPaye?: boolean;
  candidatLots?: ICandidatLot[];
  candidatLot?: ICandidatLot;
  cautionslots?: ICaution[];
  candidatCautionLots?: ICandidatCautionLot[];
}

export class Lot implements ILot {
  constructor(
    public id?: number,
    public libelle?: string,
    public description?: string,
    public activiteId?: number,
    public cautionId?: number,
    public deleted?: boolean,
    public infructueux?: boolean,
    public dossierPaye?: boolean,
    public candidatLots?: ICandidatLot[],
    public candidatLot?: ICandidatLot,
    public cautionslots?: ICaution[],
    public candidatCautionLots?: ICandidatCautionLot[]
  ) {
    this.deleted = this.deleted || false;
    this.infructueux = this.infructueux || false;
  }
}
