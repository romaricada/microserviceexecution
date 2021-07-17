import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarchepublicgatewaySharedModule } from 'app/shared/shared.module';
import { LiquidationComponent } from './liquidation.component';
import { LiquidationDetailComponent } from './liquidation-detail.component';
import { LiquidationUpdateComponent } from './liquidation-update.component';
import { LiquidationDeletePopupComponent, LiquidationDeleteDialogComponent } from './liquidation-delete-dialog.component';
import { liquidationRoute, liquidationPopupRoute } from './liquidation.route';

const ENTITY_STATES = [...liquidationRoute, ...liquidationPopupRoute];

@NgModule({
  imports: [MarchepublicgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LiquidationComponent,
    LiquidationDetailComponent,
    LiquidationUpdateComponent,
    LiquidationDeleteDialogComponent,
    LiquidationDeletePopupComponent
  ],
  entryComponents: [LiquidationDeleteDialogComponent]
})
export class MicroserviceexecutionLiquidationModule {}
