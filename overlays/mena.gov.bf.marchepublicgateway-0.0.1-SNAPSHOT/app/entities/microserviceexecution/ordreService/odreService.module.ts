import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarchepublicgatewaySharedModule } from 'app/shared/shared.module';
import {OrdreServiceRoute} from "app/entities/microserviceexecution/ordreService/ordreService.route";
import {OdreServiceComponent} from "app/entities/microserviceexecution/ordreService/odreService.component";


const ENTITY_STATES = [...OrdreServiceRoute];

@NgModule({
  imports: [MarchepublicgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OdreServiceComponent],
  entryComponents: []
})
export class MicroserviceexecutionOrdreServiceModule {}
