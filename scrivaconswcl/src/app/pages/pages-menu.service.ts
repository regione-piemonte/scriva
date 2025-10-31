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
import { I18nService } from '@eng-ds/translate';
import { NbAccessChecker } from '@nebular/security';
import { MenuItem, ValueMapperService } from '../core';

@Injectable()
export class PagesMenuService {
  // tslint:disable-next-line:variable-name
  private _items: MenuItem[] = [
    {
      title: this.i18n.translate('MENU.HOME'),
      icon: 'eng-home',
      link: '/home',
      activeCheck: '/home',
      home: true,
      data: {
        permission: 'view',
        resource: 'home'
      }
    }
  ];

  constructor(
    private i18n: I18nService,
    private accessChecker: NbAccessChecker,
    private valueMapperService: ValueMapperService
  ) {
    this.valueMapperService.getAllAmbiti().forEach((menu) => {
      this._items.push({
        title: menu.des_ambito,
        icon: 'eng-ambito-' + menu.des_ambito.toLowerCase().replace(' ', '-'),
        link: '/ambito/' + menu.cod_ambito,
        activeCheck: menu.cod_ambito,
        home: false,
        data: {
          permission: 'view',
          resource: 'menu-item'
        }
      });
    });
    this._items.push(
      {
        title: this.i18n.translate('MENU.SEARCH_PRACTCE'),
        icon: 'eng-scriva-search',
        link: '/procedimenti/ricerca',
        activeCheck: '/procedimenti/ricerca',
        home: false,
        data: {
          permission: 'view',
          resource: 'procedimenti/ricerca'
        }
      },
      {
        title: this.i18n.translate('MENU.CONTATTI'),
        icon: 'icon-sidebar-contatti.svg',
        link: '/contatti',
        activeCheck: '/contatti',
        home: false,
        data: {
          permission: 'view',
          resource: 'contatti'
        }
      },
      //  [SCRIVA-1431] Intervento momentaneo per messa in esercizio, riTolto il 28/01 per passaggio in prod
      // {
      //   title: this.i18n.translate('MENU.OBSERVATIONS'),
      //   icon: 'eng-scriva-osservazioni',
      //   link: '/osservazioni',
      //   activeCheck: '/osservazioni',
      //   home: false,
      //   private: true,
      //   data: {
      //     permission: 'view',
      //     resource: 'osservazioni'
      //   }
      // }
    );
  }

  get items(): MenuItem[] {
    return this._items;

    // No need to hide not granted menu item
    // return this._checkAuthMenuItems(this._items);
  }

  private _checkAuthMenuItems(menuItems: MenuItem[]): MenuItem[] {
    return menuItems.map((item) => this._checkAuthMenuItem(item));
  }

  private _checkAuthMenuItem(menuItem: MenuItem): MenuItem {
    if (menuItem.children) {
      menuItem.children = this._checkAuthMenuItems(menuItem.children);
    }

    if (menuItem.data && menuItem.data.permission && menuItem.data.resource) {
      this.accessChecker
        .isGranted(menuItem.data.permission, menuItem.data.resource)
        .subscribe((granted) => {
          // console.log(menuItem.data.permission)
          // console.log(menuItem.data.resource)
          // console.log(granted)
          menuItem.hidden = !granted;
        });
    } else {
      menuItem.hidden = true;
    }
    return menuItem;
  }
}
