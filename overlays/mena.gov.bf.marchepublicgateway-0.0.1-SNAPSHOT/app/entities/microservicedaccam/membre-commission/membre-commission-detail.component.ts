import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembreCommission } from 'app/shared/model/microservicedaccam/membre-commission.model';

@Component({
  selector: 'jhi-membre-commission-detail',
  templateUrl: './membre-commission-detail.component.html'
})
export class MembreCommissionDetailComponent implements OnInit {
  membreCommission: IMembreCommission;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ membreCommission }) => {
      this.membreCommission = membreCommission;
    });
  }

  previousState() {
    window.history.back();
  }
}
