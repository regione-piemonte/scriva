/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Costanti di comodo che deifniscono i nomi delle icone all'interno della cartella assets.
 */
const ISTANZE_BOZZA = `icon-istanze-bozza.svg`;
const ISTANZE_DAINTEGRARE = `icon-istanze-daintegrare.svg`;
const ISTANZE_DAPAGARE = `icon-istanze-dapagare.svg`;
const ISTANZE_INCARICO = `icon-istanze-incarico.svg`;
const ISTANZE_INCORSO = `icon-istanze-incorso.svg`;
const ISTANZE_PRESENTATE = `icon-istanze-presentate.svg`;
const NAVBAR_CAMPANELLA = `icon-navbar-campanella.svg`;
const NAVBAR_INFO = `icon-navbar-info.svg`;
const PAGE_ADD = `icon-page-add.svg`;
const SIDEBAR_ADEMPIMENTI = `icon-sidebar-adempimenti.svg`;
const SIDEBAR_AES = `icon-sidebar-aes.svg`;
const SIDEBAR_AMB = `icon-sidebar-amb.svg`;
const SIDEBAR_ATTIVITA = `icon-sidebar-attivita.svg`;
const SIDEBAR_CERCA = `icon-sidebar-cerca.svg`;
const SIDEBAR_CONTATTI = `icon-sidebar-contatti.svg`;
const SIDEBAR_DOCUMENTI = `icon-sidebar-documenti.svg`;
const SIDEBAR_ESPANDI = `icon-sidebar-espandi.svg`;
const SIDEBAR_FOR = `icon-sidebar-for.svg`;
const SIDEBAR_HOME = `icon-sidebar-home.svg`;
const SIDEBAR_IMPOSTAZIONI = `icon-sidebar-impostazioni.svg`;
const SIDEBAR_RIDUCI = `icon-sidebar-riduci.svg`;
const STEPPER_ARROW_CIRCLE_LEFT_SOLID = `icon-stepper-arrow-circle-left-solid.svg`;
const STEPPER_ARROW_CIRCLE_RIGHT_SOLID = `icon-stepper-arrow-circle-right-solid.svg`;
const TABLE_DETTAGLIO = `icon-table-dettaglio.svg`;
const TABLE_DOWNLOAD = `icon-table-download.svg`;
const TABLE_EDIT_DISABLED = `icon-table-edit-disabled.svg`;
const TABLE_EDIT = `icon-table-edit.svg`;
const TABLE_ELIMINA_DISABLED = `icon-table-elimina-disabled.svg`;
const TABLE_ELIMINA_LOGICO = `icon-table-elimina-logico.svg`;
const TABLE_ELIMINA = `icon-table-elimina.svg`;
const CALENDAR = `icon-calendar.svg`;
const CALENDAR_DISABLED = `icon-calendar-disabled.svg`;
const PAGINATOR_FORWARD = `icon-table-nav-forward.svg`;
const PAGINATOR_BACKWARD = `icon-table-nav-backward.svg`;
const PAGINATOR_START = `icon-table-nav-start.svg`;
const PAGINATOR_END = `icon-table-nav-end.svg`;

/**
 * Classe contenente la lista delle icone presenti nella cartella assets, con la possibilità di etichette parlanti.
 */
export class ScrivaIconsMap {
  /** String con il puntamento alle risorse locali. */
  readonly assets: string = 'assets/';

  // Icone presenti al momento della stesura della classe
  readonly istanzeBozza = ISTANZE_BOZZA;
  readonly istanzeDaIntegrare = ISTANZE_DAINTEGRARE;
  readonly istanzeDaPagare = ISTANZE_DAPAGARE;
  readonly istanzeInCarico = ISTANZE_INCARICO;
  readonly istanzeInCorso = ISTANZE_INCORSO;
  readonly istanzePresentate = ISTANZE_PRESENTATE;
  readonly navbarCampanella = NAVBAR_CAMPANELLA;
  readonly navbarInfo = NAVBAR_INFO;
  readonly aggiungiPagina = PAGE_ADD;
  readonly sidebarAdempimenti = SIDEBAR_ADEMPIMENTI;
  readonly sidebarAes = SIDEBAR_AES;
  readonly sidebarAmb = SIDEBAR_AMB;
  readonly sidebarAttivita = SIDEBAR_ATTIVITA;
  readonly sidebarCerca = SIDEBAR_CERCA;
  readonly sidebarContatti = SIDEBAR_CONTATTI;
  readonly sidebarDocumenti = SIDEBAR_DOCUMENTI;
  readonly sidebarEspandi = SIDEBAR_ESPANDI;
  readonly sidebarFor = SIDEBAR_FOR;
  readonly sidebarHome = SIDEBAR_HOME;
  readonly sidebarImpostazioni = SIDEBAR_IMPOSTAZIONI;
  readonly sidebarRiduci = SIDEBAR_RIDUCI;
  readonly stepperArrowCircleLeftSolid = STEPPER_ARROW_CIRCLE_LEFT_SOLID;
  readonly stepperArrowCircleRightSolid = STEPPER_ARROW_CIRCLE_RIGHT_SOLID;
  readonly tableDettaglio = TABLE_DETTAGLIO;
  readonly tableDownload = TABLE_DOWNLOAD;
  readonly tableEditDisabled = TABLE_EDIT_DISABLED;
  readonly tableEdit = TABLE_EDIT;
  readonly tableEliminaDisabled = TABLE_ELIMINA_DISABLED;
  readonly tableEliminaLogico = TABLE_ELIMINA_LOGICO;
  readonly tableElimina = TABLE_ELIMINA;

  // Etichette generiche di comodo
  readonly modifica = TABLE_EDIT;
  readonly modificaDisabled = TABLE_EDIT_DISABLED;
  readonly elimina = TABLE_ELIMINA;
  readonly eliminaDisabled = TABLE_ELIMINA_DISABLED;

  readonly calendar = CALENDAR;
  readonly calendarDisabled = CALENDAR_DISABLED;

  readonly paginatorForward = PAGINATOR_FORWARD;
  readonly paginatorBackward = PAGINATOR_BACKWARD;
  readonly paginatorStart = PAGINATOR_START;
  readonly paginatorEnd = PAGINATOR_END;

  constructor() {}
}

// Definizione della class in maniera da condividerla in modalità "singleton"
export const ScrivaIcons = new ScrivaIconsMap();