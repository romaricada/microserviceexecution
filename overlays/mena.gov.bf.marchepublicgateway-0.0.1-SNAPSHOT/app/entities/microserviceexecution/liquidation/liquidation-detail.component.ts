import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILiquidation } from 'app/shared/model/microserviceexecution/liquidation.model';

@Component({
  selector: 'jhi-liquidation-detail',
  templateUrl: './liquidation-detail.component.html'
})
export class LiquidationDetailComponent implements OnInit {
  liquidation: ILiquidation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ liquidation }) => {
      this.liquidation = liquidation;
    });
  }

  previousState() {
    window.history.back();
  }
}
