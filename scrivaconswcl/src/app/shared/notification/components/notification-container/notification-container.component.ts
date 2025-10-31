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
import { NotificationService } from '@shared/notification/notification.service';

@Component({
  selector: 'app-notification-container',
  template: `
    <div class="row">
      <div class="col-12">
        <ngb-toast
          *ngFor="let notification of notificationService.notifications"
          [autohide]="true"
          [delay]="notification.time || 7000"
          (hidden)="notificationService.remove(notification)"
        >
          <ng-template [ngTemplateOutlet]="notification.template"></ng-template>
        </ngb-toast>
      </div>
    </div>
  `,
  host: {
    class: 'container-fluid toast-container',
    style: ''
  }
})
export class NotificationContainerComponent implements OnInit {
  constructor(public notificationService: NotificationService) {}

  ngOnInit(): void {}
}
