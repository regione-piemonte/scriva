/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaTipiPersona } from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { SoggettoIstanza, TipoSoggetto } from '../../../../models';

/**
 * Funzione che gestisce la generazione della denominazione per l'azienda in riferimento al soggetto collegato all'oggetto-istanza.
 * @param soggetto SoggettoIstanza con le informazioni per l'estrazione dei dati e la generazione della denominazione azienda.
 * @returns string con la denominazione generata.
 */
export const generaDenominazioneAzienda = (
  soggetto: SoggettoIstanza
): string => {
  // Definisco il contenitore per la denominazione da visualizzare
  let denAziendale: string = '';

  // Estraggo le informazioni di cui ho bisogno per la generazione della denominazione
  const cognome: string = soggetto?.cognome ?? '';
  const nome: string = soggetto?.nome ?? '';
  const denSoggetto: string = soggetto?.den_soggetto ?? '';

  // Recupero la tipologia del soggetto
  const tipoSoggetto: TipoSoggetto = soggetto?.tipo_soggetto;
  // Verifico qual è il tipo del soggetto
  switch (tipoSoggetto?.cod_tipo_soggetto) {
    case ScrivaTipiPersona.personaFisica:
      // Genero la denominazione per la persona fisica
      denAziendale = `${cognome} ${nome}`;
      break;
    // #
    default:
      // Genero la denominazione per gli altri tipi di soggetti
      denAziendale = `${denSoggetto}`;
      break;
    // #
  }

  // Ritorno la denominazione composta
  return denAziendale;
  // #
};
