import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IMembreCommission, MembreCommission } from 'app/shared/model/microservicedaccam/membre-commission.model';
import { MembreCommissionService } from './membre-commission.service';
import { IMembre } from 'app/shared/model/microservicedaccam/membre.model';
import { MembreService } from 'app/entities/microservicedaccam/membre/membre.service';
import { ITypeCommission } from 'app/shared/model/microservicedaccam/type-commission.model';
import { TypeCommissionService } from 'app/entities/microservicedaccam/type-commission/type-commission.service';
import { ITache } from 'app/shared/model/microservicedaccam/tache.model';
import { TacheService } from 'app/entities/microservicedaccam/tache/tache.service';

@Component({
  selector: 'jhi-membre-commission-update',
  templateUrl: './membre-commission-update.component.html'
})
export class MembreCommissionUpdateComponent implements OnInit {
  isSaving: boolean;

  membres: IMembre[];

  typecommissions: ITypeCommission[];

  taches: ITache[];

  editForm = this.fb.group({
    id: [],
    activiteId: [],
    referenceArrete: [],
    poste: [null, [Validators.required]],
    deleted: [null, [Validators.required]],
    membre: [],
    typeCommission: [],
    tache: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected membreCommissionService: MembreCommissionService,
    protected membreService: MembreService,
    protected typeCommissionService: TypeCommissionService,
    protected tacheService: TacheService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ membreCommission }) => {
      this.updateForm(membreCommission);
    });
    this.membreService
      .query()
      .subscribe((res: HttpResponse<IMembre[]>) => (this.membres = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.typeCommissionService
      .query()
      .subscribe(
        (res: HttpResponse<ITypeCommission[]>) => (this.typecommissions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.tacheService
      .query()
      .subscribe((res: HttpResponse<ITache[]>) => (this.taches = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(membreCommission: IMembreCommission) {
    this.editForm.patchValue({
      id: membreCommission.id,
      activiteId: membreCommission.activiteId,
      referenceArrete: membreCommission.referenceArrete,
      poste: membreCommission.poste,
      deleted: membreCommission.deleted,
      membre: membreCommission.membre,
      typeCommission: membreCommission.typeCommission,
      tache: membreCommission.tache
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const membreCommission = this.createFromForm();
    if (membreCommission.id !== undefined) {
      this.subscribeToSaveResponse(this.membreCommissionService.update(membreCommission));
    } else {
      this.subscribeToSaveResponse(this.membreCommissionService.create(membreCommission));
    }
  }

  private createFromForm(): IMembreCommission {
    return {
      ...new MembreCommission(),
      id: this.editForm.get(['id']).value,
      activiteId: this.editForm.get(['activiteId']).value,
      referenceArrete: this.editForm.get(['referenceArrete']).value,
      poste: this.editForm.get(['poste']).value,
      deleted: this.editForm.get(['deleted']).value,
      membre: this.editForm.get(['membre']).value,
      typeCommission: this.editForm.get(['typeCommission']).value,
      tache: this.editForm.get(['tache']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreCommission>>) {
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

  trackMembreById(index: number, item: IMembre) {
    return item.id;
  }

  trackTypeCommissionById(index: number, item: ITypeCommission) {
    return item.id;
  }

  trackTacheById(index: number, item: ITache) {
    return item.id;
  }
}
