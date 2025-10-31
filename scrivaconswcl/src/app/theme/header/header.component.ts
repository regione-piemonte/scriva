/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuService } from '@app/core';
import { HelpModalComponent } from '@app/core/components/help-modal/help-modal.component';
import { ModalService } from '@app/shared/modal/modal.service';
import { HelpService } from '@app/shared/services/help/help.service';
import { UserInfo } from '@core/backend/model';
import { AutoUnsubscribe } from '@core/components';
import { SecurityService, UtilityService } from '@core/services';
import { I18nService } from '@eng-ds/translate';
import { environment } from '@env/environment';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { DropDownItem } from '@shared/models';

interface PathMask {
  path: string;
  mask: PathMask[] | string;
}

@UntilDestroy()
@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html'
})
export class HeaderComponent
  extends AutoUnsubscribe
  implements OnInit, OnDestroy
{
  dropdownMenu: DropDownItem[] = [];

  avatarContent = ' ';
  user: UserInfo;

  constructor(
    private i18n: I18nService,
    private securityService: SecurityService,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router,
    private utilityService: UtilityService,
    private modalService: ModalService,
    private helpService: HelpService
  ) {
    super();
  }

  ngOnInit(): void {
    this.securityService
      .getUser()
      .pipe(untilDestroyed(this))
      .subscribe((user: UserInfo) => {
        if (!!user) {
          this.dropdownMenu = [
            {
              label: ' ',
              detail: ' '
            },
            {
              icon: {
                name: 'eng-scriva-logout',
                type: 'eng',
                cssClass: 'custom-icon cursor-pointer exit-icon',
                fill: '',
                size: 'extra-small'
              },
              label: this.i18n.translate('HEADER.DROPDOWN.LOGOUT'),
              onClick: this.onLogout.bind(this)
            }
          ];
          this.user = user;
          this.dropdownMenu[0].label = this.user.nome + ' ' + this.user.cognome;
          this.avatarContent = this.user.nome[0] + this.user.cognome[0];
        }
      });

    if (window.innerWidth <= 768) {
      this.menuService.close();
    }
  }

  openMenu(): void {
    this.menuService.open();
  }

  onHelp(): void {
    const codMaschera = this.helpService.getCodMaschera();

    if (codMaschera) {
      this.helpService.getHelpByChiave(codMaschera).subscribe((help) => {
        // Filtro l'array di help per codMaschera
        this.modalService.openDialog(HelpModalComponent, {
          header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
          showCloseButton: true,
          context: { helpOBJ: help.find((h) => h.chiave_help == codMaschera) }
        });
      });
    }
  }

  onResize(e) {
    if (window.innerWidth <= 768) {
      this.menuService.close();
    }
  }

  onLogout() {
    this.securityService.onLogout();
    window.location = environment.auth.ssoLogout as (string | Location) &
      Location;
  }

  onLogin() {
    window.location = `${environment.backend.baseUrl}/auth` as (
      | string
      | Location
    ) &
      Location;
  }
}
