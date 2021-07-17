import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContrat, Contrat } from 'app/shared/model/microserviceexecution/contrat.model';
import { ContratService } from './contrat.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html'
})
export class ContratUpdateComponent implements OnInit {
  isSaving: boolean;
  dateSignatureDp: any;
  dateDebutPrevuDp: any;
  dateFinPrevuDp: any;

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.required]],
    dateSignature: [null, [Validators.required]],
    dateDebutPrevu: [null, [Validators.required]],
    dateFinPrevu: [null, [Validators.required]],
    candidatLotId: [null, [Validators.required]],
    cautionCandidatLotId: [],
    deleted: [null, [Validators.required]]
  });

  constructor(protected contratService: ContratService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contrat }) => {
      this.updateForm(contrat);
    });
  }

  updateForm(contrat: IContrat) {
    this.editForm.patchValue({
      id: contrat.id,
      reference: contrat.reference,
      dateSignature: contrat.dateSignature,
      dateDebutPrevu: contrat.dateDebutPrevu,
      dateFinPrevu: contrat.dateFinPrevu,
      candidatLotId: contrat.candidatLotId,
      cautionCandidatLotId: contrat.cautionCandidatLotId,
      deleted: contrat.deleted
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  private createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id']).value,
      reference: this.editForm.get(['reference']).value,
      dateSignature: this.editForm.get(['dateSignature']).value,
      dateDebutPrevu: this.editForm.get(['dateDebutPrevu']).value,
      dateFinPrevu: this.editForm.get(['dateFinPrevu']).value,
      candidatLotId: this.editForm.get(['candidatLotId']).value,
      cautionCandidatLotId: this.editForm.get(['cautionCandidatLotId']).value,
      deleted: this.editForm.get(['deleted']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
