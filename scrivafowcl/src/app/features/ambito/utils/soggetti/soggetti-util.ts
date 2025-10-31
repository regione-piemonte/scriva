/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Istanza, RuoloCompilante } from 'src/app/shared/models';
import { GruppoSoggetto, RecapitoAlternativo, RuoloSoggetto, Soggetto, SoggettoIstanza } from '../../models';

export class SoggettiUtil {
  // returns SoggettoIstanza to supply to API given required inputs
  static returnSoggettoIstanza(
    istanza: Istanza,
    soggetto: Soggetto,
    ruoloCompilante: RuoloCompilante,

    idSoggettoIstanza: number = null,
    gestUID: string = null,
    gruppo_soggetto: GruppoSoggetto = null,

    ruoloSoggetto: RuoloSoggetto = null,
    padre: SoggettoIstanza = null
  ) {
    const soggIst: SoggettoIstanza = {
      id_soggetto_istanza: idSoggettoIstanza,
      gestUID: gestUID,
      soggetto: soggetto,
      id_soggetto_padre: padre?.id_soggetto_istanza,
      // istanza: istanza,
      id_istanza: istanza.id_istanza,
      cognome: soggetto.cognome,
      nome: soggetto.nome,
      nazione_nascita: soggetto.nazione_nascita,
      nazione_residenza: soggetto.nazione_residenza,
      nazione_sede_legale: soggetto.nazione_sede_legale,
      comune_nascita: soggetto.comune_nascita,
      comune_residenza: soggetto.comune_residenza,
      comune_sede_legale: soggetto.comune_sede_legale,
      citta_estera_nascita: soggetto.citta_estera_nascita,
      citta_estera_residenza: soggetto.citta_estera_residenza,
      citta_estera_sede_legale: soggetto.citta_estera_sede_legale,
      data_nascita_soggetto: soggetto.data_nascita_soggetto?.substring(0, 10),
      tipo_soggetto: soggetto.tipo_soggetto,
      cf_soggetto: soggetto.cf_soggetto,
      indirizzo_soggetto: soggetto.indirizzo_soggetto,
      num_civico_indirizzo: soggetto.num_civico_indirizzo,
      cap_residenza: soggetto.cap_residenza,
      cap_sede_legale: soggetto.cap_sede_legale,
      des_localita: soggetto.des_localita,
      num_telefono: soggetto.num_telefono,
      num_cellulare: soggetto.num_cellulare,
      des_email: soggetto.des_email,
      des_pec: soggetto.des_pec,
      id_masterdata: 1,
      ruolo_soggetto: ruoloSoggetto,
      ruolo_compilante: ruoloCompilante,
      tipo_natura_giuridica: soggetto.tipo_natura_giuridica,
      den_soggetto: soggetto.den_soggetto,
      data_cessazione_soggetto: soggetto.data_cessazione_soggetto,
      partita_iva_soggetto: soggetto.partita_iva_soggetto,
      den_provincia_cciaa: soggetto.den_provincia_cciaa,
      den_anno_cciaa: soggetto.den_anno_cciaa,
      den_numero_cciaa: soggetto.den_numero_cciaa,
      id_masterdata_origine: soggetto.id_masterdata_origine || 1,
      gruppo_soggetto: gruppo_soggetto
    };

    return soggIst;
  }

  static returnRecapitoAlternativo(idSoggettoIstanza: number, formRecapitoAlternativo) {
    const recapito: RecapitoAlternativo = {
      id_soggetto_istanza: idSoggettoIstanza,
      id_recapito_alternativo: formRecapitoAlternativo.idRecapitoAlternativo,
      gestUID: formRecapitoAlternativo.gestUID,
      cod_recapito_alternativo: formRecapitoAlternativo.codRecapitoAlternativo,

      nazione_residenza: formRecapitoAlternativo.statoResidenzaAlt,
      nazione_sede_legale: formRecapitoAlternativo.statoSedeLegaleAlt,
      comune_residenza: formRecapitoAlternativo.comuneResidenzaAlt,
      comune_sede_legale: formRecapitoAlternativo.comuneSedeLegaleAlt,
      citta_estera_residenza: formRecapitoAlternativo.cittaEsteraResidenzaAlt,
      citta_estera_sede_legale: formRecapitoAlternativo.cittaEsteraSedeLegaleAlt,
      indirizzo_soggetto: formRecapitoAlternativo.indirizzoResidenzaAlt || formRecapitoAlternativo.indirizzoSedeLegaleAlt,
      num_civico_indirizzo: formRecapitoAlternativo.civicoResidenzaAlt || formRecapitoAlternativo.civicoSedeLegaleAlt,
      cap_residenza: formRecapitoAlternativo.capResidenzaAlt,
      cap_sede_legale: formRecapitoAlternativo.capSedeLegaleAlt,
      des_localita: formRecapitoAlternativo.localitaResidenzaAlt || formRecapitoAlternativo.localitaSedeLegaleAlt,
      des_email: formRecapitoAlternativo.emailAlt || formRecapitoAlternativo.emailSedeLegaleAlt,
      des_pec: formRecapitoAlternativo.pecAlt || formRecapitoAlternativo.pecSedeLegaleAlt,
      num_telefono: formRecapitoAlternativo.telefonoAlt || formRecapitoAlternativo.telefonoSedeLegaleAlt,
      num_cellulare: formRecapitoAlternativo.cellulareAlt || formRecapitoAlternativo.cellulareSedeLegaleAlt,
    };

    return recapito;
  }

  static pushOrReplace<T>(collection: T[], item: T, property: string) {
    let found = false;

    collection.map((i) => {
      if (i[property] === item[property]) {
        found = true;
        i = item;
      }
    });

    if (!found) {
      collection.push(item);
    }

    return collection;
  }
}
