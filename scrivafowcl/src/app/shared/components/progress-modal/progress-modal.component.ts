/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { takeUntil } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AllegatiUploaderProgressService } from 'src/app/features/ambito/services/allegati-uploader-progress';

@Component({
  selector: 'progress-modal',
  templateUrl: './progress-modal.component.html',
  styleUrls: ['./progress-modal.component.scss'],
})
export class ProgressModalComponent
  extends AutoUnsubscribe
  implements OnInit
{
  
  progressList:ProgressFile[];
  hideProgressActive: boolean = false;

  constructor(
    private allegatiUploaderProgressService: AllegatiUploaderProgressService,
    public activeModal: NgbActiveModal
  ) {
    super();
  }

  ngOnInit() {
    this.hideProgressActive = false;
    this.allegatiUploaderProgressService.getProgress().pipe(takeUntil(this.destroy$))
    .subscribe((ret:ProgressFile[]) => {
      this.progressList = ret;
    }); 
  
    this.allegatiUploaderProgressService.getStatus().pipe(takeUntil(this.destroy$))
    .subscribe((ret:any) => {
      if(ret.action==='hideProgressActive') {
        this.hideProgressActive = true;
      }
    });  
  }

  onDismiss() {
    this.activeModal.dismiss();
  }
}
