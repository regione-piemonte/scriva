/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { MenuItem } from '@app/core';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  public subject = new Subject<any>();
  private status = true;
  private current;

  private _showMenu = new BehaviorSubject<boolean>(true);
  showMenu$ = this._showMenu.asObservable();

  constructor(private route: ActivatedRoute, private router: Router) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe((navigationEnd: NavigationEnd) => {
        this.changeCurrent(navigationEnd.url);
      });
  }

  hideMenu(): void {
    this._toggle(false);
  }

  showMenu(): void {
    this._toggle(true);
  }

  private _toggle(value: boolean): void {
    this._showMenu.next(value);
  }

  open(): void {
    this.status = true;
    this.subject.next({ menuStatus: this.status, currentMenu: this.current });
  }

  close(): void {
    this.status = false;
    this.subject.next({ menuStatus: this.status, currentMenu: this.current });
  }

  changeCurrent(menu) {
    this.current = menu;
    this.subject.next({ menuStatus: this.status, currentMenu: this.current });
  }

  initRoute(menuItem: Array<MenuItem>) {
    menuItem.forEach((item) => {
      if (
        item.link === this.route.snapshot['_routerState'].url ||
        item.link.indexOf(this.route.snapshot['_routerState'].url) >= 0
      ) {
        this.changeCurrent(this.route.snapshot['_routerState'].url);
      }
    });
  }

  getMenuStatus(): Observable<any> {
    return this.subject.asObservable();
  }
}
