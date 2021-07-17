import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkflow } from 'app/shared/model/microservicedaccam/workflow.model';

@Component({
  selector: 'jhi-workflow-detail',
  templateUrl: './workflow-detail.component.html'
})
export class WorkflowDetailComponent implements OnInit {
  workflow: IWorkflow;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ workflow }) => {
      this.workflow = workflow;
    });
  }

  previousState() {
    window.history.back();
  }
}
