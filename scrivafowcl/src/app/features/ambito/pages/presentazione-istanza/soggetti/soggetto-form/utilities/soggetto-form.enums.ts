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
 * Enum che gestisce le informazioni relative allo stato di "trovato", "non trovato", "non gestito" per la ricerca richiedente.
 */
export enum StatiRicercaRichiedente {
  nonGestito = 'NON_GESTITO',
  trovato = 'TROVATO',
  nonTrovato = 'NON_TROVATO',
  nonTrovatoUsatoCompilante = 'NON_TROVATO_USATO_DA_COMPILANTE',
  daCompilante = 'DA_COMPILANTE',
}
