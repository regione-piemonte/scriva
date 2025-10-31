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
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { ILoadNotificheCount } from '../../models/load-notifiche-count';
import { NotificheHttpService } from '../../services/notifiche-http/notifiche-http.service';

@Component({
  selector: 'scriva-notifiche-button-count',
  templateUrl: './notifiche-button-count.component.html',
  styleUrls: ['./notifiche-button-count.component.scss'],
})
export class NotificheButtonCountComponent implements OnInit, OnDestroy {
  loadNotificheCount: ILoadNotificheCount;
  private _subscription: Subscription;

  @Output() onClick = new EventEmitter();

  constructor(private _notificheHttp: NotificheHttpService) {}

  ngOnInit(): void {
    this._subscription = this._notificheHttp
      .getLoadNotificheCount()
      .subscribe((loadNotificheCount: ILoadNotificheCount) => {
        this.loadNotificheCount = loadNotificheCount;
      });
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo che verifica se ci sono notifiche non lette.
   * @returns boolean che definisce se il cerchietto con notifiche è da visualizzare.
   */
  get checkNotificheNonLette(): boolean {
    // Verifico che le notifiche non lette siano maggiori di 0
    return this.notificheNonLette > 0;
  }

  /**
   * Getter di comodo che recupera il contatore delle notifiche non lette.
   * @returns number con il numero di notifiche non lette.
   */
  get notificheNonLette(): number {
    // Verifico che esista il contatore altrimenti torno 0
    return this.loadNotificheCount?.non_lette ?? 0;
  }
}
