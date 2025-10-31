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
 * Enum che definisce le tipologie d'informazioni adempimento per lo scarico informazioni da server.
 */
export enum TipoInfoAdempimento {
  oggetto = 'OGGETTO',
  tipoIntegrazione = 'TIPO_INTEGR',
  searchTipoOgg = 'SEARCH_TIPO_OGG',
  azTipoAllegato = 'AZ_TIPO_ALLEGATO',
  osservazioni = 'OSSERVAZIONI',
  azTipoQuadro = 'AZ_TIPO_QUADRO',
  azEsitoRifiuto = 'AZ_ESITO_RIFIUTO',
  elencoDoc = 'ELENCO_DOC',
  acWeb = 'WEB_AC_GDPR',
  acWebPub = 'WEB_AC_PUB',
}
