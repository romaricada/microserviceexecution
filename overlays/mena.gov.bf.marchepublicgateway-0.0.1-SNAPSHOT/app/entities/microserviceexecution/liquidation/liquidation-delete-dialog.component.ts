import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILiquidation } from 'app/shared/model/microserviceexecution/liquidation.model';
import { LiquidationService } from './liquidation.service';

@Component({
  selector: 'jhi-liquidation-delete-dialog',
  templateUrl: './liquidation-delete-dialog.component.html'
})
export class LiquidationDeleteDialogComponent {
  liquidation: ILiquidation;

  constructor(
    protected liquidationService: LiquidationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.liquidationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'liquidationListModification',
        content: 'Deleted an liquidation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-liquidation-delete-popup',
  template: ''
})
export class LiquidationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ liquidation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LiquidationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.liquidation = liquidation;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/liquidation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/liquidation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
