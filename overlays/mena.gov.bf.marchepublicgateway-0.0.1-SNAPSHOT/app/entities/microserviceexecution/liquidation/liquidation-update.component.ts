import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ILiquidation, Liquidation } from 'app/shared/model/microserviceexecution/liquidation.model';
import { LiquidationService } from './liquidation.service';
import { IContrat } from 'app/shared/model/microserviceexecution/contrat.model';
import { ContratService } from 'app/entities/microserviceexecution/contrat/contrat.service';

@Component({
  selector: 'jhi-liquidation-update',
  templateUrl: './liquidation-update.component.html'
})
export class LiquidationUpdateComponent implements OnInit {
  isSaving: boolean;

  contrats: IContrat[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required]],
    date: [null, [Validators.required]],
    deleted: [null, [Validators.required]],
    contrat: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected liquidationService: LiquidationService,
    protected contratService: ContratService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ liquidation }) => {
      this.updateForm(liquidation);
    });
    this.contratService
      .query()
      .subscribe((res: HttpResponse<IContrat[]>) => (this.contrats = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(liquidation: ILiquidation) {
    this.editForm.patchValue({
      id: liquidation.id,
      montant: liquidation.montant,
      date: liquidation.date,
      deleted: liquidation.deleted,
      contrat: liquidation.contrat
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const liquidation = this.createFromForm();
    if (liquidation.id !== undefined) {
      this.subscribeToSaveResponse(this.liquidationService.update(liquidation));
    } else {
      this.subscribeToSaveResponse(this.liquidationService.create(liquidation));
    }
  }

  private createFromForm(): ILiquidation {
    return {
      ...new Liquidation(),
      id: this.editForm.get(['id']).value,
      montant: this.editForm.get(['montant']).value,
      date: this.editForm.get(['date']).value,
      deleted: this.editForm.get(['deleted']).value,
      contrat: this.editForm.get(['contrat']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILiquidation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackContratById(index: number, item: IContrat) {
    return item.id;
  }
}
