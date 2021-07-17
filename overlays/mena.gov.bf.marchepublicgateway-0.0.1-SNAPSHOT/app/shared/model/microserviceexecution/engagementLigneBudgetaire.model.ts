import {ILigneBudgetaire} from "app/shared/model/microserviceppm/ligne-budgetaire.model";

export interface IEngagementLigneBudgetaire {
  id?: number;
  engagementId?: number;
  ligneBudgetaireId?: number;
  montantEngageLigne?: number;
  deleted?: boolean;
  ligneBudgetaires?: ILigneBudgetaire[];
}

export class EngagementLigneBudgetaire implements IEngagementLigneBudgetaire {
  constructor(
    public id?: number,
    public engagementId?: number,
    public ligneBudgetaireId?: number,
    public montantEngageLigne?: number,
    public deleted?: boolean,
    public ligneBudgetaires?: ILigneBudgetaire[]
  ) {
    this.deleted = this.deleted || false;
  }
}
