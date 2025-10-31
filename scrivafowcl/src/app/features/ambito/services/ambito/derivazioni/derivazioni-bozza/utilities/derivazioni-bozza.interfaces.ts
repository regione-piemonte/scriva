/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from '../../../../../../../shared/models';

/**
 * Interfaccia con la mappatura delle informazioni per il quadro orientamento con i dati per le derivazioni.
 */
export interface IOrientamentoDerivazioni {
  ACPratica?: CompetenzaTerritorio[];
  Adempimento?: number;
  IndAbilitaGruppo?: string;
  IndGeoMode?: string;
  IndNumComuniOggetto?: string;
  IndNumOggetto?: string;
  IndNumReferente?: string;
  IndNumSoggetto?: string;
  IndTipoSelezioneAC?: string;
  IndTipoSoggetto?: string;
  QDR_ALLEGATO?: any; // {procedura_semplificata: true, flg_opere_fisse: false, uso_geotermico_max20l_s: true, uso_esclusivo_domestico: true};
  QDR_DER_CAPTAZIONE?: any; // {criteri_ricerca: {…}};
  QDR_DER_DATIGEN?: any; // {tipo_concessione: 'ordinaria'};
  QDR_DER_INFRACC?: any; // {criteri_ricerca: {…}};
  QDR_DER_OPERATRASP?: any; // {criteri_ricerca: {…}};
  QDR_DER_RESTITUZIONE?: any; // {criteri_ricerca: {…}};
  dichiarazioni?: any[]; // [{…}];
  valutazione_impatto_ambientale?: string;
  verifica_impatto_ambientale?: string;
}
