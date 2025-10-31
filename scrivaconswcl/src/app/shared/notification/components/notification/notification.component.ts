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
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import { I18nService } from '@eng-ds/translate';
import { NotificationPosition, Status } from '@core/enums';
import { UtilsService } from '@shared/components/utils.service';
import { NotificationModel } from '@shared/notification/model/notification.model';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit, AfterViewInit, OnChanges {
  @ViewChild('notificationTemplate') notificationTemplate;
  @Input() notification: NotificationModel;
  closeText: string;
  h5Id: string;
  classes;
  alertVisible: boolean = false;
  dismissable: boolean = false;

  constructor(private i18n: I18nService, private utils: UtilsService) {}

  ngOnInit(): void {
    if (this.scrollTop) {
      window.scroll({
        top: 0,
        left: 0,
        behavior: 'smooth'
      });
    }
    this.initNotification();
  }

  ngAfterViewInit() {
    this.notification.template = this.notificationTemplate;
  }

  ngOnChanges(changes: SimpleChanges) {
    // Verifico se la configurazione è stata cambiata
    if (changes.notification && !changes.notification.firstChange) {
      this.initNotification();
    }
  }

  private initNotification() {
    if (!this.notification) {
      return;
    }

    if(this.notification.time){
      setTimeout(()=>{
        this.closeAlert();
      },this.notification.time)
    }
    
    this.alertVisible = true;
    this.h5Id = 'h5_' + this.notification.id;
    this.classes = {
      notification: true,
      'alert-box': true,
      dismissable: this.notification.dismissable,
      success:
        this.utils.isDefined(this.notification.status) &&
        this.notification.status === Status.success,
      error:
        this.utils.isDefined(this.notification.status) &&
        this.notification.status === Status.error,
      info:
        this.utils.isDefined(this.notification.status) &&
        this.notification.status === Status.info,
      warning:
        this.utils.isDefined(this.notification.status) &&
        this.notification.status === Status.warning,
      'top-fix':
        this.utils.isDefined(this.notification.position) &&
        this.notification.position === NotificationPosition.top,
      'bottom-fix':
        this.utils.isDefined(this.notification.position) &&
        this.notification.position === NotificationPosition.bottom,
      'left-fix':
        this.utils.isDefined(this.notification.position) &&
        this.notification.position === NotificationPosition.left,
      'right-fix':
        this.utils.isDefined(this.notification.position) &&
        this.notification.position === NotificationPosition.right,
      'with-icon':
        this.utils.isDefined(this.notification.icon) &&
        this.utils.isNotNull(this.notification.icon)
    };

    this.closeText = this.i18n.translate('UTILS.NOTIFICATIONS.CLOSE', {
      titleNotification: this.notification.title
    });
  }

  closeAlert(){
   this.alertVisible= false;
  }

  get scrollTop(): boolean {
    return this.notification.scrollTop ?? false;
  }
}
