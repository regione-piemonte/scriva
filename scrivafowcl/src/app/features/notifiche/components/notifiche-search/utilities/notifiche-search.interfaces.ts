/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Moment } from 'moment';
import { Adempimento } from '../../../../../shared/models';

/**
 * Interfaccia che rappresenta l'oggetto generato dal form per i filtri di ricerca delle notifiche.
 */
export interface INotificheSearchData {
  statoNotifica: IStatoNotifica;
  procedimento: Adempimento;
  dataDa: Moment;
  dataA: Moment;
  numeroIstanza: string;
}

/**
 * Interfaccia di comodo che rappresenta l'oggetto per gestire lo stato delle notifiche per i filtri di ricerca.
 * Al momento, questo tipo di dato non viene scaricato dal server, per cui la mappatura è solo di FE.
 */
export interface IStatoNotifica {
  id: number;
  cod_stato_notifica: string;
  des_stato_notifica: string;
}
