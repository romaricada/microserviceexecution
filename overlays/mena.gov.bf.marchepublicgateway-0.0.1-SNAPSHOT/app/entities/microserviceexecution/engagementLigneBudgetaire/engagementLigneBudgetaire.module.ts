import {engagementLigneBudgetaireRoute} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.route";
import {MarchepublicgatewaySharedModule} from "app/shared/shared.module";
import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {EngagementLigneBudgetaireComponent} from "app/entities/microserviceexecution/engagementLigneBudgetaire/engagementLigneBudgetaire.component";

const ENTITY_STATES = [...engagementLigneBudgetaireRoute];

@NgModule({
  imports: [MarchepublicgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EngagementLigneBudgetaireComponent],
  entryComponents: []
})
export class MicroserviceexecutionEngagementLigneBudgetaireModule {}
