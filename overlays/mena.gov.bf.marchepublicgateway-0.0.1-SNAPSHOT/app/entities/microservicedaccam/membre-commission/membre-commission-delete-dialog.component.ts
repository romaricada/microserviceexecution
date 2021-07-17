import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembreCommission } from 'app/shared/model/microservicedaccam/membre-commission.model';
import { MembreCommissionService } from './membre-commission.service';

@Component({
  selector: 'jhi-membre-commission-delete-dialog',
  templateUrl: './membre-commission-delete-dialog.component.html'
})
export class MembreCommissionDeleteDialogComponent {
  membreCommission: IMembreCommission;

  constructor(
    protected membreCommissionService: MembreCommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.membreCommissionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'membreCommissionListModification',
        content: 'Deleted an membreCommission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-membre-commission-delete-popup',
  template: ''
})
export class MembreCommissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ membreCommission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MembreCommissionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.membreCommission = membreCommission;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/membre-commission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/membre-commission', { outlets: { popup: null } }]);
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
