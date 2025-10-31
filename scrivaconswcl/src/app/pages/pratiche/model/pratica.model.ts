/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Adempimenti, Ambito } from '@pages/ambiti/model';

export interface Pratica {
  id_istanza: number;
  cod_istanza: string;
  cod_pratica: string;
  stato_istanza: StatoIstanza;
  adempimento: Adempimenti;
  ambito?: Ambito;
  cod_ambito: string;
  cod_tipo_adempimento: string;
  numero_protocollo: number;
  data_inserimento_istanza: string;
  data_modifica_istanza: string;
  data_pubblicazione: string;
  des_stato_sintesi_pagamento: string;
  data_protocollo: string;
  data_inizio_osservazione: string;
  data_fine_osservazione: string;
  data_conclusione_procedimento: string;
  competenza_territorio: CompetenzaTerritorio[];
  responsabili_istanza: Responsabile[];
  oggetto_istanza: OggettoIstanza[];
  soggetto_istanza: SoggettoIstanza[];
  flg_osservazione: boolean;
  data_scadenza_procedimento: string;
  des_esito_procedimento: string;
  des_stato_procedimento_statale: string;
  des_esito_procedimento_statale: string;
  url_portale_esterno: string;
  procedimento_statale: any;
  esito_procedimento_statale: {
    id_esito_procedimento: number;
    cod_esito_procedimento: string;
    des_esito_procedimento: string;
  };
  stato_procedimento_statale: {
    id_stato_proced_statale: number;
    cod_stato_proced_statale: string;
    des_stato_proced_statale: string;
    des_estesa_stato_proced_statale: string;
    label_stato_proced_statale: string;
  };
  esito_procedimento: {
    id_esito_procedimento: number;
    cod_esito_procedimento: string;
    des_esito_procedimento: string;
  };
}

export interface CompetenzaTerritorio {
  id_competenza_territorio: number;
  comune_competenza: ComuneCompetenza;
  tipo_competenza: TipoCompetenza;
  cod_competenza_territorio: string;
  des_competenza_territorio: string;
  indirizzo_competenza: string;
  num_civico_indirizzo: string;
  cap_competenza: string;
  flg_autorita_principale?: boolean;
  flg_autorita_assegnata_bo?: boolean;
  sito_web?: string;
  orario?: string;
  des_mail_pec?: string;
  ind_assegnata_da_sistema: string;
}

export interface ComuneCompetenza {
  id_comune: number;
  cod_istat_comune: number;
  cod_belfiore_comune: string;
  denom_comune: string;
  data_inizio_validita: string;
  dt_id_comune: number;
  dt_id_comune_prev: number;
  dt_id_comune_next: number;
  cap_comune: string;
  provincia: Provincia;
}

interface Provincia {
  id_provincia: number;
  cod_provincia: string;
  denom_provincia: string;
  sigla_provincia: string;
  data_inizio_validita: string;
  regione: Regione;
}

interface Regione {
  id_regione: number;
  cod_regione: string;
  denom_regione: string;
  nazione: Nazione;
}

interface TipoCompetenza {
  id_tipo_competenza: 2;
  cod_tipo_competenza: string;
  des_tipo_competenza: string;
  des_tipo_competenza_estesa: string;
  ordinamento_tipo_competenza: number;
}

interface Nazione {
  id_nazione: number;
  cod_istat_nazione: string;
  denom_nazione: string;
  data_inizio_validita: string;
  dt_id_stato: number;
  dt_id_stato_prev: number;
  dt_id_stato_next: number;
  id_origine: number;
  cod_iso2: string;
}

export interface StatoIstanza {
  cod_stato_istanza: string;
  des_stato_istanza: string;
  label_stato: string;
}

interface OggettoIstanza {
  id_oggetto_istanza: number;
  cod_scriva: string;
  den_oggetto: string;
  des_oggetto: string;
  gestUID?: string;
  id_istanza?: string;
  id_masterdata?: number;
  id_masterdata_origine?: number;
  id_oggetto?: number;
  id_tipologia_oggetto?: number;
  flg_georeferito: boolean;
  tipologia_oggetto: TipologiaOggetto;
  ubicazione_oggetto: UbicazioneOggetto[];
  siti_natura_2000: SitiReteNatura[];
  aree_protette: AreeProtette[];
}

interface TipologiaOggetto {
  id_tipologia_oggetto: number;
  cod_tipologia_oggetto: string;
  des_natura_oggetto: string;
  natura_oggetto: NaturaOggetto;
}

interface NaturaOggetto {
  id_natura_oggetto: number;
  cod_natura_oggetto: string;
  des_natura_oggetto: string;
}

export interface UbicazioneOggetto {
  id_oggetto_istanza: number;
  comune: Comune;
}

export interface SitiReteNatura {
  cod_amministrativo: string;
  cod_gestore_fonte: string;
  competenza_territorio: CompetenzaTerritorioSiti;
  des_sito_natura_2000: string;
  flg_ricade: boolean;
  gestUID: string;
  id_oggetto_natura_2000: number;
  num_distanza: number;
  oggetto_istanza: OggettoIstanza[];
  tipo_natura_2000: TipoNatura200;

}

export interface CompetenzaTerritorioSiti {
  cod_competenza_territorio: string;
  data_inizio_validita: string;
  des_competenza_territorio: string;
  des_email: string;
  gestUID: string;
  id_competenza_territorio: number;
  id_comune_competenza: number;
  ind_visibile: string;
}

export interface TipoNatura200 {
  cod_tipo_natura_2000: string;
  des_tipo_natura_2000: string;
  id_tipo_natura_2000: number;
}

export interface AreeProtette {
  cod_amministrativo: string;
  cod_gestore_fonte: string;
  competenza_territorio: CompetenzaTerritorioSiti;
  des_area_protetta: string;
  flg_ricade: boolean;
  gestUID: string;
  id_oggetto_area_protetta: number;
  oggetto_istanza: OggettoIstanza[];
  tipo_area_protetta: TipoAreaProtetta;
}

export interface TipoAreaProtetta {
  cod_tipo_area_protetta: string;
  des_tipo_area_protetta: string;
  id_tipo_area_protetta: number;
}

export interface Comune {
  id_comune: number;
  cod_istat_comune: string;
  cod_belfiore_comune: string;
  denom_comune: string;
  data_inizio_validita: string;
  dt_id_comune: number;
  dt_id_comune_prev: number;
  dt_id_comune_next: number;
  cap_comune: string;
  provincia: Provincia;
}

interface SoggettoIstanza {
  id_soggetto_istanza: number;
  codice_fiscale: string;
  natura_giuridica: NaturaGiuridica;
  partita_iva: string;
  denominazione: string;
  comune_sede_legale: Comune;
  tipo_soggetto: {
    cod_tipo_soggetto: string;
  };
  nome: string;
  cognome: string;
}

interface NaturaGiuridica {
  cod_natura_giuridica: string;
  des_natura_giuridica: string;
}

interface Responsabile {
  flg_riservato?: boolean;
  id_istanza?: number;
  id_istanza_responsabile?: number;
  label_responsabile?: string;
  nominativo_responsabile?: string;
  recapito_responsabile?: number;
  tipo_responsabile: TipoResponsabile;
}

interface TipoResponsabile {
  cod_tipo_responsabile?: string;
  des_tipo_responsabile?: string;
  id_tipo_responsabile?: string;
}