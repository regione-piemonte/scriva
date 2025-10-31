/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { RicercaPaginataResponse } from '../../../../../shared/services/helpers/utilities/http-helper.classes';
import { ILoadNotificheCount } from '../../../models/load-notifiche-count';
import { INotifichePagingCount } from './notifiche-http.interfaces';

/**
 * Classe che definisce la struttura di ritorno delle informazioni per il recupero delle notifiche paginate, con il count specifico delle notifiche.
 */
export class NotifichePagingCount<T>
  extends RicercaPaginataResponse<T>
  implements INotifichePagingCount<T>
{
  contatori: ILoadNotificheCount;

  constructor(c?: INotifichePagingCount<T>) {
    super(c);

    this.contatori = c?.contatori;
  }
}
