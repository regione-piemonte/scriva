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
import { AutoUnsubscribe, MenuService } from '../core';
import { filter } from 'rxjs/operators';
import {
  ActivatedRouteSnapshot,
  ActivationStart,
  ChildActivationStart,
  Router
} from '@angular/router';
import { LoadingService } from '@theme/layouts/loading.services';
import { PagesMenuService } from '@pages/pages-menu.service';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';

@Component({
  styleUrls: ['pages.component.scss'],
  template: `
    <app-default-layout>
      <app-menu [menuItems]="pagesMenu.items"></app-menu>
      <router-outlet></router-outlet>
    </app-default-layout>
  `
})
export class PagesComponent extends AutoUnsubscribe {
  sidebarExpanded = false;
  menuStatus: boolean = true;

  constructor(
    public loadingService: LoadingService,
    private router: Router,
    public pagesMenu: PagesMenuService,
    private menuService: MenuService
  ) {
    super();
    this._routerOnChangeListen();
    this.menuService.getMenuStatus().subscribe((data) => {
      this.menuStatus = data.menuStatus;
    });
  }

  private _routerOnChangeListen(): void {
    this.router.events
      .pipe(
        filter(
          (event) =>
            (event instanceof ActivationStart ||
              event instanceof ChildActivationStart) &&
            (this._routeHasResolve(event.snapshot) ||
              this._routeHasLoadingStart(event.snapshot))
        )
      )
      .subscribe((e) => {
        this.loadingService.show();
      });
  }

  private _routeHasLoadingStart(snapshot: ActivatedRouteSnapshot): boolean {
    return !!snapshot.routeConfig?.data?.loading?.start;
  }

  private _routeHasResolve(snapshot: ActivatedRouteSnapshot): boolean {
    return !!(
      snapshot.routeConfig?.resolve &&
      Object.keys(snapshot.routeConfig?.resolve).length
    );
  }
}
