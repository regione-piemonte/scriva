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
import { NotificationComponent } from './components/notification/notification.component';
import { NotificationModel } from './model/notification.model';
import { Status } from '@core/enums';
import { NotificationIcons } from './components/notification/utilities/notification.enum';

declare const notificationShow: any;

@Injectable({ providedIn: 'root' })
export class NotificationService {
  notificationComponentRefs: Map<string, NotificationModel> = new Map<
    string,
    NotificationModel
  >();

  notifications: NotificationModel[] = [];

  constructor(
    private resolver: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector
  ) {}

  /*
   * Mostra la notifica passata in input come ERROR
   * per la durata stabilita.
   * Di default 7000ms
   *
   * @param notification NotificationModel
   * @param duration number
   *
   * @return void
   * */
  error(notification: NotificationModel, duration?: number): void {
    notification.icon = NotificationIcons.ERROR;
    notification.status = Status.error;

    this.showNotification(notification, duration);
  }

  /*
   * Mostra la notifica passata in input come SUCCESS
   * per la durata stabilita.
   * Di default 7000ms
   *
   * @param notification NotificationModel
   * @param duration number
   *
   * @return void
   * */
  success(notification: NotificationModel, duration?: number): void {
    notification.icon = NotificationIcons.SUCCESS;
    notification.status = Status.success;

    this.showNotification(notification, duration);
  }

  /*
   * Mostra la notifica passata in input come INFO
   * per la durata stabilita.
   * Di default 7000ms
   *
   * @param notification NotificationModel
   * @param duration number
   *
   * @return void
   * */
  info(notification: NotificationModel, duration?: number): void {
    notification.icon = NotificationIcons.INFO;
    notification.status = Status.info;

    this.showNotification(notification, duration);
  }

  /*
   * Mostra la notifica passata in input come WARNING
   * per la durata stabilita.
   * Di default 8000ms
   *
   * @param notification NotificationModel
   * @param duration number
   *
   * @return void
   * */
  warning(notification: NotificationModel, duration?: number): void {
    notification.icon = NotificationIcons.WARNING;
    notification.status = Status.warning;

    this.showNotification(notification, duration);
  }

  /*
   * Mostra la notifica passata in input per la durata stabilita.
   * Di default 7000ms
   *
   * @param notification NotificationModel
   * @param duration number
   *
   * @return void
   * */
  showNotification(notification: NotificationModel, duration?: number): void {
    if (!duration) {
      duration = 8000; //ricambia
    }
    notification.scrollTop = true;
    notification.time = duration;
    
    const notificationId: string =
      'notification_' + Math.random().toFixed(5).substring(2);
    notification.id = notificationId;
    notificationShow(notificationId.toString(), duration);
    const factory: ComponentFactory<NotificationComponent> =
      this.resolver.resolveComponentFactory(NotificationComponent);
    const componentRef: ComponentRef<NotificationComponent> = factory.create(
      this.injector
    );
    componentRef.instance.notification = notification;
    componentRef.changeDetectorRef.detectChanges();

    this.appRef.attachView(componentRef.hostView);
    this.notifications.push(notification);
    this.notificationComponentRefs.set(notificationId, notification);
  }

  remove(notification: NotificationModel) {
    this.notifications = this.notifications.filter((n) => n !== notification);
  }

}
