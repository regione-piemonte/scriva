/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  Component,
  ComponentFactoryResolver,
  ComponentRef,
  ElementRef,
  Input,
  OnInit,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import { ModalOptions } from '../../models/modal.options';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-container-modal',
  templateUrl: './container-modal.component.html',
  styleUrls: ['./container-modal.component.scss']
})
export class ContainerModalComponent implements OnInit {
  @Input() options?: ModalOptions;
  @Input() bodyComponent?: any;
  bodyComponentRef: ComponentRef<any>;

  @ViewChild('modal') modal: ElementRef<any>;
  @ViewChild('body', {
    read: ViewContainerRef,
    static: true
  })
  body: ViewContainerRef;

  constructor(protected activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this._renderContent();
  }

  onDismiss(): void {
    this.activeModal.dismiss();
  }

  private _renderContent(): void {
    this.body.clear();
    const injector = this.body.injector;
    const cfr = injector.get(ComponentFactoryResolver);

    const componentFactory = cfr.resolveComponentFactory(this.bodyComponent);

    // @ts-ignore
    const componentRef = this.body.createComponent<any>(componentFactory);

    componentRef.instance.modalContainer = this.activeModal;

    if (this.options && this.options.context) {
      const context: string[] = Object.keys(this.options.context);
      context.forEach((key: string) => {
        componentRef.instance[key] = this.options.context[key];
      });
    }

    this.bodyComponentRef = componentRef;
  }
}
