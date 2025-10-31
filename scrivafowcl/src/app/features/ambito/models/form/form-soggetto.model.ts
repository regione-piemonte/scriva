/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune, Nazione, Provincia, Regione, RuoloCompilante } from 'src/app/shared/models';
import { RuoloSoggetto } from '../soggetto/ruolo-soggetto.model';
import { TipoNaturaGiuridica } from '../soggetto/tipo-natura-giuridica.model';
import { TipoSoggetto } from '../soggetto/tipo-soggetto.model';

export interface FormSoggettoPF {
  anagraficaSoggetto: AnagraficaSoggettoPF;
  ruoloCompilante: RuoloCompilante;
  anagraficaRichiedente?: AnagraficaSoggettoPF;
}

export interface FormSoggettoPG {
  anagraficaSoggetto: AnagraficaSoggettoPG;
  ruoloCompilante: RuoloCompilante;
  anagraficaRichiedente: AnagraficaSoggettoPF;
}

export interface AnagraficaSoggettoPF {
  id_soggetto?: number;
  gestUID?: string;
  id_masterdata?: number;
  id_masterdata_origine?: number;
  cf: string;
  tipoSoggetto: TipoSoggetto;
  cognome: string;
  nome: string;
  statoNascita: Nazione;
  provinciaNascita: string;
  comuneNascita: Comune;
  dataNascita: string;
  cittaEsteraNascita: string;
  statoResidenza: Nazione;
  provinciaResidenza: string;
  comuneResidenza: Comune;
  cittaEsteraResidenza: string;
  indirizzoResidenza: string;
  civicoResidenza: string;
  capResidenza: string;
  localitaResidenza: string;
  email: string;
  pec: string;
  telefono: string;
  cellulare: string;
  ruoloSoggetto: RuoloSoggetto;
  recapitoAlternativoResidenza: FormRecapitoAlternativoResidenza;
  flg_capogruppo: boolean;
}

export interface AnagraficaSoggettoPG {
  id_soggetto?: number;
  gestUID?: string;
  id_masterdata?: number;
  id_masterdata_origine?: number;
  ragioneSociale: string;
  naturaGiuridica: TipoNaturaGiuridica;
  cf: string;
  pIva: string;
  tipoSoggetto: TipoSoggetto;
  statoSedeLegale: Nazione;
  provinciaSedeLegale: string;
  comuneSedeLegale: Comune;
  cittaEsteraSedeLegale: string;
  indirizzoSedeLegale: string;
  civicoSedeLegale: string;
  capSedeLegale: string;
  localitaSedeLegale: string;
  emailSedeLegale: string;
  pecSedeLegale: string;
  telefonoSedeLegale: string;
  cellulareSedeLegale: string;
  provinciaCciaa: string;
  annoCciaa: number;
  numeroCciaa: string;
  recapitoAlternativoSedeLegale: FormRecapitoAlternativoSedeLegale;
  flg_capogruppo: boolean;
}

interface FormRecapitoAlternativoResidenza {
  idRecapitoAlternativo: number;
  gestUID: string;
  codRecapitoAlternativo: string;
  statoResidenzaAlt: Nazione;
  regioneResidenzaAlt: Regione;
  provinciaResidenzaAlt: Provincia;
  comuneResidenzaAlt: Comune;
  cittaEsteraResidenzaAlt: string;
  indirizzoResidenzaAlt: string;
  civicoResidenzaAlt: string;
  capResidenzaAlt: string;
  localitaResidenzaAlt: string;
  emailAlt: string;
  pecAlt: string;
  telefonoAlt: string;
  cellulareAlt: string;
}

interface FormRecapitoAlternativoSedeLegale {
  idRecapitoAlternativo: number;
  gestUID: string;
  codRecapitoAlternativo: string;
  statoSedeLegaleAlt: Nazione;
  regioneSedeLegaleAlt: Regione;
  provinciaSedeLegaleAlt: Provincia;
  comuneSedeLegaleAlt: Comune;
  cittaEsteraSedeLegaleAlt: string;
  indirizzoSedeLegaleAlt: string;
  civicoSedeLegaleAlt: string;
  capSedeLegaleAlt: string;
  localitaSedeLegaleAlt: string;
  emailSedeLegaleAlt: string;
  pecSedeLegaleAlt: string;
  telefonoSedeLegaleAlt: string;
  cellulareSedeLegaleAlt: string;
}
