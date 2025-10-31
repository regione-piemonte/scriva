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
  ApplicationRef,
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  EmbeddedViewRef,
  Injectable,
  Injector
} from '@angular/core';
import { AlertComponent } from './components/alert/alert.component';
import { AlertModel } from './model/alert.model';
import { Status } from '../../core/enums/status';

declare const notificationShow: any;

@Injectable()
export class AlertService {

  alertComponentRefs: Map<string, ComponentRef<AlertComponent>> = new Map<string, ComponentRef<AlertComponent>>();

  constructor(private resolver: ComponentFactoryResolver,
              private appRef: ApplicationRef,
              private injector: Injector) {
  }

  /*
  * Mostra la notifica passata in input come ERROR
  * per la durata stabilita.
  * Di default 300000ms
  *
  * @param id string
  * @param alert AlertModel
  * @param duration number
  *
  * @return void
  * */
  error(id: string, alert: AlertModel, duration?: number): void {
    alert.icon = 'it-close-circle';
    alert.status = Status.error;
    alert.dismissable = true;

    this.showAlert(id, alert, duration);
  }

  /*
  * Mostra la notifica passata in input come SUCCESS
  * per la durata stabilita.
  * Di default 300000ms
  *
  * @param id string
  * @param alert AlertModel
  * @param duration number
  *
  * @return void
  * */
  success(id: string, alert: AlertModel, duration?: number): void {
    alert.icon = 'it-check-circle';
    alert.status = Status.success;

    this.showAlert(id, alert, duration);
  }

  /*
  * Mostra la notifica passata in input come INFO
  * per la durata stabilita.
  * Di default 300000ms
  *
  * @param id string
  * @param alert AlertModel
  * @param duration number
  *
  * @return void
  * */
  info(id: string, alert: AlertModel, duration?: number): void {
    alert.icon = 'it-info-circle';
    alert.status = Status.info;

    this.showAlert(id, alert, duration);
  }

  /*
  * Mostra la notifica passata in input come WARNING
  * per la durata stabilita.
  * Di default 300000ms
  *
  * @param id string
  * @param alert AlertModel
  * @param duration number
  *
  * @return void
  * */
  warning(id: string, alert: AlertModel, duration?: number): void {
    alert.icon = 'it-error';
    alert.status = Status.warning;

    this.showAlert(id, alert, duration);
  }

  /*
  * Mostra la notifica passata in input per la durata stabilita.
  * Di default 300000ms
  *
  * @param id string
  * @param alert AlertModel
  * @param duration number
  *
  * @return void
  * */
  showAlert(id: string, alert: AlertModel, duration?: number): void {
    if (!duration) {
      duration = 300000;
    }

    const factory: ComponentFactory<AlertComponent> = this.resolver.resolveComponentFactory(AlertComponent);
    const componentRef: ComponentRef<AlertComponent> = factory.create(this.injector);
    componentRef.instance.alert = alert;
    componentRef.changeDetectorRef.detectChanges();

    const alertId: string = componentRef.instance.alertId;
    this.appRef.attachView(componentRef.hostView);

    const domElem = (componentRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;
    document.getElementById(id).after(domElem);

    notificationShow(alertId.toString(), duration);
    this.alertComponentRefs.set(alertId, componentRef);

    setTimeout(() => {
      this._removeAlertComponentFromBody(componentRef);
    }, duration);
  }

  private _removeAlertComponentFromBody(ref: ComponentRef<AlertComponent>) {
    this.appRef.detachView(ref.hostView);
    ref.destroy();
    this.alertComponentRefs.delete(ref.instance.alertId);
  }

  clearAll(): void {
    document.querySelectorAll('*[id^="alert_"]').forEach(element => {
      document.getElementById(element.id).style.display = 'none';
    });
  }

  clearById(id: string): void {
    document.getElementById(id).parentNode.querySelectorAll('*[id^="alert_"]').forEach(element => {
      document.getElementById(element.id).style.display = 'none';
    });
  }

}

