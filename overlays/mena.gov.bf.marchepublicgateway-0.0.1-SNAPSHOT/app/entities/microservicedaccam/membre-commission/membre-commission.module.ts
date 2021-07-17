import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MarchepublicgatewaySharedModule } from 'app/shared/shared.module';
import { MembreCommissionComponent } from './membre-commission.component';
import { MembreCommissionDetailComponent } from './membre-commission-detail.component';
import { MembreCommissionUpdateComponent } from './membre-commission-update.component';
import { MembreCommissionDeletePopupComponent, MembreCommissionDeleteDialogComponent } from './membre-commission-delete-dialog.component';
import { membreCommissionRoute, membreCommissionPopupRoute } from './membre-commission.route';

const ENTITY_STATES = [...membreCommissionRoute, ...membreCommissionPopupRoute];

@NgModule({
  imports: [MarchepublicgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MembreCommissionComponent,
    MembreCommissionDetailComponent,
    MembreCommissionUpdateComponent,
    MembreCommissionDeleteDialogComponent,
    MembreCommissionDeletePopupComponent
  ],
  entryComponents: [MembreCommissionDeleteDialogComponent]
})
export class MicroservicedaccamMembreCommissionModule {}
