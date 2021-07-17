import { IBesoinLigneBudgetaire } from 'app/shared/model/microserviceppm/besoin-ligne-budgetaire.model';

export interface ILigneBudgetaire {
  id?: number;
  budget?: string;
  ligneCredit?: string;
  section?: string;
  programme?: string;
  action?: string;
  chapitre?: string;
  uniteAdministrativeId?: number;
  activite?: string;
  article?: string;
  paragraphe?: string;
  dotInitAE?: number;
  dotInitCP?: number;
  dotCorAE?: number;
  dotCorCP?: number;
  engage?: number;
  ordonne?: number;
  engageCF?: number;
  liquide?: number;
  vbp?: number;
  ecp?: number;
  montantEstime?: number;
  deleted?: boolean;
  besoins?: IBesoinLigneBudgetaire[];
  exerciceId?: number;
  annee?: number;
  besoinLigneBuget?: IBesoinLigneBudgetaire;
  selected?: boolean;
}

export class LigneBudgetaire implements ILigneBudgetaire {
  constructor(
    public id?: number,
    public budget?: string,
    public ligneCredit?: string,
    public section?: string,
    public programme?: string,
    public action?: string,
    public chapitre?: string,
    public uniteAdministrativeId?: number,
    public activite?: string,
    public article?: string,
    public paragraphe?: string,
    public dotInitAE?: number,
    public dotInitCP?: number,
    public dotCorAE?: number,
    public dotCorCP?: number,
    public engage?: number,
    public ordonne?: number,
    public engageCF?: number,
    public liquide?: number,
    public vbp?: number,
    public ecp?: number,
    public montantEstime?: number,
    public deleted?: boolean,
    public besoins?: IBesoinLigneBudgetaire[],
    public exerciceId?: number,
    public annee?: number,
    public besoinLigneBuget?: IBesoinLigneBudgetaire,
    public selected?: boolean
  ) {
    this.deleted = this.deleted || false;
    this.selected = this.selected || false;
  }
}
