/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { EventEmitter, Injectable } from '@angular/core';
import { ModalOptions } from './models/modal.options';
import { ModalComponent } from './models/modal.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ContainerModalComponent } from './components/container-modal/container-modal.component';

@Injectable()
export class ModalService {
  constructor(private modalService: NgbModal) {}

  get activeInstances(): EventEmitter<NgbModalRef[]> {
    return this.modalService.activeInstances;
  }

  get hasOpenModals(): boolean {
    return this.modalService.hasOpenModals();
  }

  dismissAll(reason: any): void {
    this.modalService.dismissAll(reason);
  }

  /**
   * Metodo da utilizzare per l'apertura di una dialog.
   * Prende in input un componente che estende ModalComponent e delle options
   *
   */
  openDialog(
    component: ModalComponent | any,
    options: ModalOptions
  ): NgbModalRef {
    console.info(options);
    const modalRef = this.modalService.open(
      ContainerModalComponent,
      this._getNgbOptions(options)
    );
    const componentInstance =
      modalRef.componentInstance as ContainerModalComponent;
    componentInstance.bodyComponent = component;

    // pass options
    componentInstance.options = options;

    return modalRef;
  }

  private _getNgbOptions(options: ModalOptions): any {
    return {
      size: options.sizeModal ? options.sizeModal : 'xl',
      centered: options.centered ? options.centered : true,
      scrollable: options.scrollable ? options.scrollable : true
    };
  }
}
