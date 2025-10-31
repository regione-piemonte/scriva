/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Status } from '../../../core/enums';

export interface AlertModel {
  /*
  * Titolo della notifica
  * */
  title: string;
  /*
  * Testo della notifica
  * */
  text: string;
  /*
  * Dismissable
  * */
  dismissable?: boolean;
  /*
  * Status (Info, Warning, Error, Success
  * */
  status?: Status;
  /*
  * Nome Icon da visualizzare
  * */
  icon?: string;
  /**
   * Booleano che gestisce la visibità fissa
   */
  persistentMessage?: boolean;
}
