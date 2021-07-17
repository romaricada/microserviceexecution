import { Moment } from 'moment';
import {IBesoinLigneBudgetaire} from "app/shared/model/microserviceppm/besoin-ligne-budgetaire.model";
import {IContrat} from "app/shared/model/microserviceexecution/contrat.model";
import {ILot} from "app/shared/model/microservicedaccam/lot.model";
import {ILigneBudgetaire} from "app/shared/model/microserviceppm/ligne-budgetaire.model";
import {IEngagementLigneBudgetaire} from "app/shared/model/microserviceexecution/engagementLigneBudgetaire.model";

export interface IEngagement {
  id?: number;
  montantEngage?: number;
  date?: Moment;
  contratId?: number;
  besoinLigneBudgetaireId?: number;
  lotId?: number;
  deleted?: boolean;
  besoinLigneBudgetaire?: IBesoinLigneBudgetaire[];
  activiteId?: number;
  contratEn?: IContrat;
  lotEn?: ILot;
  ligneBudgetaireId?: ILigneBudgetaire;
  ligneBudget?: ILigneBudgetaire[];
  engagementLigneBudgetaires?: IEngagementLigneBudgetaire[]
}

export class Engagement implements IEngagement {
  constructor(
    public id?: number,
    public montantEngage?: number,
    public date?: Moment,
    public contratId?: number,
    public besoinLigneBudgetaireId?: number,
    public activiteId?: number,
    public lotId?: number,
    public deleted?: boolean,
    public besoinLigneBudgetaire?: IBesoinLigneBudgetaire[],
    public contratEn?: IContrat,
    public lotEn?: ILot,
    public ligneBudgetaireId?: ILigneBudgetaire,
    public ligneBudget?: ILigneBudgetaire[]
  ) {
    this.deleted = this.deleted || false;
  }
}
