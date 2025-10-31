/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18nService } from '@eng-ds/translate';

@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss']
})
export class BreadcrumbsComponent implements OnInit {
  @Input() optTemplate: TemplateRef<any>;

  @Input() overrideBreadcrumbs: {
    label: string;
    href: string;
  }[];

  routesLabels: {
    label: string;
    href: string;
    query?: Record<string, string>;
  }[] = [];
  headerLabel: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private i18n: I18nService
  ) {}

  ngOnInit() {
    this._createBreadcrumbs();
  }

  private _createBreadcrumbs(): void {
    if (this.overrideBreadcrumbs) {
      this.overrideBreadcrumbs[1].label = this.formatBreadcrumbs(this.overrideBreadcrumbs[1].label);
      this.routesLabels = this.overrideBreadcrumbs;
      return;
    }

    const routeSnapshot = this.route.snapshot;
    const routeData = routeSnapshot.data;

    this.routesLabels = routeData.breadcrumbs;
    this.headerLabel = routeData.headerLabel;
  }

  formatBreadcrumbs(input: string): string {
    // Rimuovoo gli spazi prima e dopo la stringa
    const trimmedInput = input.trim();

    // Trovo la posizione del primo trattino "-" che separa la parte numerica dalla descrizione
    const index = trimmedInput.indexOf('-');

    if (index === -1) {
      // Se non c'è un trattino, ritorno la stringa originale
      return input;
    }

    // La parte numerica include la parte che va fino al primo trattino
    const part1 = trimmedInput.substring(0, index + 3); // Include la parte numerica, con il secondo trattino

    // La parte descrittiva inizia subito dopo il trattino e deve essere separata correttamente
    let part2 = trimmedInput.substring(index + 3).trim(); // Prendiamo tutto dopo il trattino e rimuoviamo gli spazi

    // Mi assicuro che la parte descrittiva non inizi con trattini o spazi
    part2 = part2.replace(/^-+\s*/, ''); // Rimuove trattini e spazi all'inizio della parte descrittiva

    // Restituisco la stringa formattata correttamente
    return `${part1}/${part2}`;
  }
}
