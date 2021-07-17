import {Moment} from 'moment';
import {IStatutExecution} from 'app/shared/model/microserviceexecution/statut-execution.model';
import {IPenalite} from 'app/shared/model/microserviceexecution/penalite.model';
import {IContentieux} from 'app/shared/model/microserviceexecution/contentieux.model';
import {IAvenant} from 'app/shared/model/microserviceexecution/avenant.model';
import {IEtapeExecution} from 'app/shared/model/microserviceexecution/etape-execution.model';
import {ILiquidation} from 'app/shared/model/microserviceexecution/liquidation.model';
import {IFichier} from 'app/entities/file-manager/file-menager.model';
import {ICandidatLot} from "app/shared/model/microservicedaccam/candidat-lot.model";

export interface IContrat {
  id?: number;
  reference?: string;
  dateSignature?: Moment;
  dateDebutPrevu?: Moment;
  dateFinPrevu?: Moment;
  candidatLotId?: number;
  cautionCandidatLotId?: number;
  resilierContrat?: boolean;
  deleted?: boolean;
  statutExecutions?: IStatutExecution[];
  penalites?: IPenalite[];
  candidatLot?: ICandidatLot;
  candidatLots?: ICandidatLot[];
  contentieuxs?: IContentieux[];
  avenants?: IAvenant[];
  etapeExecutions?: IEtapeExecution[];
  liquidations?: ILiquidation[];
  files?: IFichier[];
}

export class Contrat implements IContrat {
  constructor(
    public id?: number,
    public reference?: string,
    public dateSignature?: Moment,
    public dateDebutPrevu?: Moment,
    public dateFinPrevu?: Moment,
    public candidatLotId?: number,
    public cautionCandidatLotId?: number,
    public resilierContrat?: boolean,
    public deleted?: boolean,
    public statutExecutions?: IStatutExecution[],
    public penalites?: IPenalite[],
    public candidatLot?: ICandidatLot,
    public candidatLots?: ICandidatLot[],
    public contentieuxs?: IContentieux[],
    public avenants?: IAvenant[],
    public etapeExecutions?: IEtapeExecution[],
    public liquidations?: ILiquidation[],
    public files?: IFichier[]
  ) {
    this.resilierContrat = this.resilierContrat || false;
    this.deleted = this.deleted || false;
  }
}
