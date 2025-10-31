/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IRicercaPaginataResponse } from '../../../../../shared/services/helpers/utilities/http-helper.interfaces';
import { ILoadNotificheCount } from '../../../models/load-notifiche-count';

/**
 * Interfaccia che estende le informazioni per la paginazione dell'app. In aggiunta ci saranno informazioni sui dati di conteggio notifiche.
 */
export interface INotifichePagingCount<T> extends IRicercaPaginataResponse<T> {
  contatori: ILoadNotificheCount;
}
