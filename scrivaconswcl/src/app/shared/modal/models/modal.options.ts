/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export class ModalOptions {
  /*
   * Titolo header
   */
  header?: string;

  /*
   * Abilita o disabilita il close button sull'header
   */
  showCloseButton?: boolean;

  /*
   * Size della modale
   */
  sizeModal?: string;

  /*
   * Scrollable
   */
  scrollable ? = true;

  /*
   * Posizione centrale
   */
  centered ? = true;

  /*
   * Oggetto per passaggio input sul body della modale
   */
  context?: any;
}
