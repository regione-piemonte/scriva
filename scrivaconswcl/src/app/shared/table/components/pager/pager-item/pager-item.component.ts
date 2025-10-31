/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface ChangeEvent {
  page?: number;
  size?: number;
}

@Component({
  selector: 'app-pager-item',
  templateUrl: './pager-item.component.html',
  styleUrls: ['./pager-item.component.scss']
})
export class AgidPagerItemComponent {

  /*
  * Flag che indica se l'elemento è disabilitato
  * @private
  * @see https://italia.github.io/bootstrap-italia/docs/componenti/paginazione/#stato-disabilitato-e-attivo
  */
  @Input() disabled = false;

  /*
  * Flag che indica se l'elemento è attivo
  * @private
  * @see https://italia.github.io/bootstrap-italia/docs/componenti/paginazione/#stato-disabilitato-e-attivo
  */
  @Input() active = false;

  /*
  * Numero di pagina
  * @private
  */
  @Input() page: number;

  /*
  * Evento invocato al click se l'elemento non è disabilitato
  * @private
  */
  @Output() pageChange: EventEmitter<number> = new EventEmitter();

  handleItemClick() {
    this.pageChange.emit(this.page);
  }

  getClassName(): string {
    let className = 'page-link my-auto mx-auto ';

    if (this.active) {
      className = className + 'active ';
    } else {
      className = className + 'disabled ';
    }

    return className;
  }

}
