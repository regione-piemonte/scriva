/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem, MenuService, SecurityService, UserInfo } from '@app/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

@UntilDestroy()
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  @Input() menuItems: MenuItem[];
  public menuStatus: boolean = true;
  public currentItem: string;

  isLoggedIn = false;

  constructor(
    private router: Router,
    private securityService: SecurityService,
    private menuService: MenuService
  ) {
    this.menuService.getMenuStatus().subscribe((data) => {
      this.menuStatus = data.menuStatus;
      this.currentItem = data.currentMenu;
    });
  }

  ngOnInit(): void {
    this.menuService.initRoute(this.menuItems);
    this.securityService
      .getUser()
      .pipe(untilDestroyed(this))
      .subscribe((user: UserInfo) => {
        if (!!user) {
          this.isLoggedIn = true;
        }
      });
  }

  navigateTo(link: string): void {
    this.menuService.changeCurrent(link);
    this.router.navigate([link]);
  }

  closeMenu(): void {
    this.menuService.close();
  }

  openMenu(): void {
    this.menuService.open();
  }
}
