/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TableColumn } from '@swimlane/ngx-datatable';

/**
 * Classe conteneti le configurazioni costanti del componente.
 */
export class RiepilogoConsts {
  /** string con la dichiarazione di una costante. */
  TBL_COL_RIFERIMENTI_PROCEDIMENTO: string = 'Riferimenti procedimento';
  /** string con la dichiarazione di una costante. */
  TBL_COL_NOMINATIVO: string = 'Nominativo';
  /** string con la dichiarazione di una costante. */
  TBL_COL_RECAPITO: string = 'Recapito';
  /** string con la dichiarazione di una costante. */
  TBL_EMPTY_DATA: string = 'Nessun resposanbile';

  /** TableColumn[] con le configurazioni per la tabella. */
  riferimentiProcedimento: TableColumn[] = [];

  constructor() {
    // Lancio la funzione di definizione delle variabili
    this.setupRiferimentiProcedimento();
  }

  /**
   * Setup delle colonne per la tabella.
   * Al momento non sono necessari parametri, essendo che le colonne possono essere associate a template HTML, s'imposta la funzione così da poter gestire in un futuro un construttore con i parametri.
   */
  private setupRiferimentiProcedimento() {
    // L'oggetto di riferimento è: IstanzaResponsabile
    this.riferimentiProcedimento = [
      {
        name: this.TBL_COL_RIFERIMENTI_PROCEDIMENTO,
        prop: 'label_responsabile',
        sortable: false,
      },
      {
        name: this.TBL_COL_NOMINATIVO,
        prop: 'nominativo_responsabile',
        sortable: false,
      },
      {
        name: this.TBL_COL_RECAPITO,
        prop: 'recapito_responsabile',
        sortable: false,
      },
    ];
  }
}
