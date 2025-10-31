/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component } from '@angular/core';
import { AutoUnsubscribe, MenuService } from '@app/core';
import { LoadingService } from '@theme/layouts/loading.services';
import { NotificationService } from '@shared/notification/notification.service';

@Component({
  selector: 'app-default-layout',
  styleUrls: ['./app-default.layout.scss'],
  templateUrl: './app-default.layout.html'
})
export class AppDefaultLayoutComponent extends AutoUnsubscribe {
  menuStatus: boolean = true;
  showMenu: boolean = true;

  constructor(
    public loadingService: LoadingService,
    public notificationService: NotificationService,
    public menu: MenuService
  ) {
    super();
    this.menu.getMenuStatus().subscribe((data) => {
      this.menuStatus = data.menuStatus;
    });
    this.menu.showMenu$.subscribe((show) => {
      this.showMenu = show;
    });
  }
}
