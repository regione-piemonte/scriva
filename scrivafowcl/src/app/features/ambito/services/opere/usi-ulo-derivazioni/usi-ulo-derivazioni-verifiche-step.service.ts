/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { compact } from 'lodash';
import { ScrivaCodesPlaceholders } from '../../../../../core/consts/scriva-codes-placeholders.consts';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { MessageService } from '../../../../../shared/services';
import { FormioUtilitiesService } from '../../../../../shared/services/formio/formio-utilities.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { IMsgPlacholder } from '../../../../../shared/services/message/utilities/message.interfaces';
import { TypeSiNo } from '../../../../../shared/types/formio/scriva-formio.types';
import { OggettoIstanza, Opera } from '../../../models';
import {
  AggregatoreDatiTecniciMancanti,
  SezioneObbligatoriaDatiTecniciMancanti,
} from '../../../pages/presentazione-istanza/opere/utilities/opere.classes';
import {
  IOggettoIstanzaSenzaDatiTecnici,
  ISezioneObbligatoriaDatiTecniciMancanti,
} from '../../../pages/presentazione-istanza/opere/utilities/opere.interfaces';
import { OpereVerificheStepService } from '../opere-verifiche-step.service';
import { OpereService } from '../opere.service';
import { IDatiObbligatoriDTParams } from '../utilities/opere-verifiche-step.interfaces';
import {
  IDTFormioUsiUloDER,
  IDTFormioUsiUloDER_USO_ENERGETICO,
  IDTFormioUsiUloDER_USO_LAVAGGIO_INERTI,
  IDTFormioUsiUloDER_USO_PISCICOLO,
  IDTFormioUsiUloDER_USO_PRODUZIONE,
  IDTFormioUsiUloDER_USO_RIQUALIFICAZIONE,
  IDTFormioUsiUloDER_USO_ZOOTECNICO,
} from './utilities/usi-ulo-derivazioni.interfaces';

@Injectable({ providedIn: 'root' })
export class UsiUloDerivazioniVerificheStepService extends OpereVerificheStepService {
  /**
   * Costruttore.
   */
  constructor(
    formioUtilities: FormioUtilitiesService,
    logger: LoggerService,
    message: MessageService,
    opere: OpereService
  ) {
    super(formioUtilities, logger, message, opere);
  }

  // #region "VERIFICA CHE I DATI TECNICI ABBIANO ALMENO UN USO DEFINITO"

  /**
   * Funzione che verifica le informazioni per le tipologie opere e le informazioni dei dati tecnici.
   * La funzione verifica le informazioni con uno strato di controlli a livello: Tipi Opere => Tipo Opera.
   * @param verificaParams IDatiObbligatoriDTParams con l'oggetto contenente i parametri di verifica per i dati.
   * @returns SezioniObbligatorieDatiTecniciMancanti[] con la lista di eventuali segnalazioni per la mancanza dei dati obbligatori delle sezioni dei dati tecnici. Se non ci sono errori, verrà ritornato lista vuota.
   * @override A differenza delle altre opere DER, per la gestione degli usi bisogna verificare che i dati tecnici contengano effettivamente almeno un uso, impostato su "si".
   *           La logica definita che controlla dentro il formio la condizione di "isRequired", non serve in questo caso. Viene quindi sovrascritta la logica.
   */
  verificaDatiObbligatoriDatiTecniciPerTipoOpera(
    verificaParams: IDatiObbligatoriDTParams
  ): SezioneObbligatoriaDatiTecniciMancanti[] {
    // Verifico l'input
    verificaParams = verificaParams ?? ({} as any);
    // Creo una copia delle informazioni
    const params: IDatiObbligatoriDTParams = verificaParams;
    // Estraggo dall'oggetto i parametri per gestire le verifiche
    const { datiTecnici, tipoOpera } = params;

    // ### VERIFICA DATI 1
    // Verifico se ci sono le informazioni per la gestione dati
    if (!datiTecnici || !tipoOpera) {
      // Mancano le informazioni, ritorno la lista dei dati mancanti per com'è stata passata
      return [];
      // #
    }

    // Recupero il prefisso per la generazione della chiave per i dati tecnici
    const prefix: string = this.OPERE_CONSTS.PREFIX_JS_DT;
    // Genero la chiave tramite prefisso e codice tipo opera
    const key: string = this._opere.keyDatiTecniciTipoOpera(prefix, tipoOpera);
    // Estraggo dai dati tecnici la lista specifica di dati
    const datiTecniciUsiSpec: IDTFormioUsiUloDER[] = datiTecnici[key];

    // Definisco una matrice che contiene le segnalazioni per i dt usi
    let segnalazioniDTUsi: SezioneObbligatoriaDatiTecniciMancanti[];
    segnalazioniDTUsi = [];

    // Itero la lista di dati tecnici per ogni oggetto dato tecnico usi
    datiTecniciUsiSpec?.forEach((datoTecnicoUso: IDTFormioUsiUloDER) => {
      // Richiamo la funzione di verifica e generazione errori
      let errore: SezioneObbligatoriaDatiTecniciMancanti;
      errore = this.verificaAlmenoUnUsoDatiTecnici(
        datoTecnicoUso,
        verificaParams
      );

      // Inserisco all'interno della matrice di errori le segnalazioni per specifico uso
      segnalazioniDTUsi.push(errore);
      // #
    });

    // Vado a rimuovere tutti i possibili valori undefined
    segnalazioniDTUsi = compact(segnalazioniDTUsi);

    // Ritorno la lista di segnalazioni per i dati obbligatori mancanti per i dati tecnici
    return segnalazioniDTUsi;
  }

  /**
   * Funzione di verifica che controlla in maniera specifica la struttura del dato tecnico in input.
   * Il dato tecnico deve contenere almeno una delle sezioni con la gestione del flag dato tecnico a "si".
   * @param datoTecnicoUso IDTFormioUsiUloDER con i dati tecnici da verificare.
   * @param verificaParams IDatiObbligatoriDTParams con i parametri di configurazione per la verifica.
   * @returns SezioneObbligatoriaDatiTecniciMancanti con l'eventuale errore generato per il dato tecnico.
   */
  private verificaAlmenoUnUsoDatiTecnici(
    datoTecnicoUso: IDTFormioUsiUloDER,
    verificaParams: IDatiObbligatoriDTParams
  ): SezioneObbligatoriaDatiTecniciMancanti {
    // Verifico l'input
    if (!datoTecnicoUso || !verificaParams) {
      // Mancano le informazioni
      return undefined;
      // #
    }

    // Estraggo dall'oggetto del dato tecnico le informazioni per la verifica
    let usoPiscicolo: IDTFormioUsiUloDER_USO_PISCICOLO;
    usoPiscicolo = datoTecnicoUso?.USO_PISCICOLO;
    let usoEnergetico: IDTFormioUsiUloDER_USO_ENERGETICO;
    usoEnergetico = datoTecnicoUso?.USO_ENERGETICO;
    let usoProduzione: IDTFormioUsiUloDER_USO_PRODUZIONE;
    usoProduzione = datoTecnicoUso?.USO_PRODUZIONE;
    let usoZootecnico: IDTFormioUsiUloDER_USO_ZOOTECNICO;
    usoZootecnico = datoTecnicoUso?.USO_ZOOTECNICO;
    let usoLavaggioInerti: IDTFormioUsiUloDER_USO_LAVAGGIO_INERTI;
    usoLavaggioInerti = datoTecnicoUso?.USO_LAVAGGIO_INERTI;
    let usoRiqualificazione: IDTFormioUsiUloDER_USO_RIQUALIFICAZIONE;
    usoRiqualificazione = datoTecnicoUso?.USO_RIQUALIFICAZIONE;

    // Per ogni oggetto di sezione estraggo il relativo flag che ne indica la definizione, se manca l'indicazione metto di default 'no'
    let flgUsoPiscicolo: TypeSiNo;
    flgUsoPiscicolo = usoPiscicolo?.flgUsoPiscicolo ?? 'no';
    let flgUsoEnergetico: TypeSiNo;
    flgUsoEnergetico = usoEnergetico?.flgUsoEnergetico ?? 'no';
    let flgUsoProduzione: TypeSiNo;
    flgUsoProduzione = usoProduzione?.flgUsoProduzione ?? 'no';
    let flgUsoZootecnico: TypeSiNo;
    flgUsoZootecnico = usoZootecnico?.flgUsoZootecnico ?? 'no';
    let flgUsoLavaggioInerti: TypeSiNo;
    flgUsoLavaggioInerti = usoLavaggioInerti?.flgUsoLavaggioInerti ?? 'no';
    let flgUsoRiqualificazione: TypeSiNo;
    flgUsoRiqualificazione =
      usoRiqualificazione?.flgUsoRiqualificazione ?? 'no';

    // Inserisco tutti i flag in una lista per un controllo più rapido
    const listaFlgUsi: TypeSiNo[] = [
      flgUsoPiscicolo,
      flgUsoEnergetico,
      flgUsoProduzione,
      flgUsoZootecnico,
      flgUsoLavaggioInerti,
      flgUsoRiqualificazione,
    ];

    // Verifico se almeno un flag è impostato a 'si'
    const almenoUnUsoSI: boolean = listaFlgUsi.some((flg: TypeSiNo) => {
      // Verifico se il flag è 'si' e faccio un tolowercase di sicurezza
      return <string>flg.toLocaleLowerCase() === 'si';
      // #
    });

    // Verifico se non c'è nemmeno un sì
    if (!almenoUnUsoSI) {
      // Genero l'errore con le logiche specifiche
      let errore: SezioneObbligatoriaDatiTecniciMancanti;
      errore = this.erroreAlmenoUnUsoDatiTecnici(
        datoTecnicoUso,
        verificaParams
      );
      // Ritorno l'errore
      return errore;
    }
  }

  /**
   * Funzione che genera un errore dovuto alla mancanza di almeno un uso definito come dato tecnico.
   * @param datoTecnicoUso IDTFormioUsiUloDER con i dati tecnici da verificare.
   * @param verificaParams IDatiObbligatoriDTParams con i parametri di configurazione per la verifica.
   * @returns SezioneObbligatoriaDatiTecniciMancanti con l'errore generato per il dato tecnico.
   */
  private erroreAlmenoUnUsoDatiTecnici(
    datoTecnicoUso: IDTFormioUsiUloDER,
    verificaParams: IDatiObbligatoriDTParams
  ): SezioneObbligatoriaDatiTecniciMancanti {
    // Verifico l'input
    if (!datoTecnicoUso || !verificaParams) {
      // Mancano le informazioni
      return undefined;
      // #
    }

    // Estraggo dall'oggetto i parametri per gestire le verifiche
    const { oggettiIstanza, opere } = verificaParams;

    // Recupero dai dati tecnici dell'opera ciclati l'id_oggetto_istanza come riferimento
    const idOggIstRefDT = datoTecnicoUso.id_oggetto_istanza;
    // Verifico se nella lista di oggetti istanza esiste un collegamento tramite id
    const oggettoIstanzaPerDT = oggettiIstanza?.find(
      (oggIst: OggettoIstanza) => {
        // Verifico gli id oggetto istanza
        return oggIst.id_oggetto_istanza === idOggIstRefDT;
        // #
      }
    );
    // Recupero l'oggetto dell'opera specifico
    const operaPerDT = opere?.find((opera: Opera) => {
      // Verifico gli id oggetto istanza
      const idOggettoOpera = opera.id_oggetto;
      const idOggettoOggIst = oggettoIstanzaPerDT?.id_oggetto;
      // Verifico gli id oggetto delle informazioni
      return idOggettoOpera === idOggettoOggIst;
      // #
    });

    // Definisco le informazioni per la generazione dell'errore
    const configs: ISezioneObbligatoriaDatiTecniciMancanti = {
      oggettoIstanza: oggettoIstanzaPerDT,
      opera: operaPerDT,
      sezioneFormIo: undefined,
    };

    // Creo la classe d'errore
    const errore = new SezioneObbligatoriaDatiTecniciMancanti(configs);

    // Ritorno l'errore generato
    return errore;
  }

  // #region "VERIFICA CHE I DATI TECNICI ABBIANO ALMENO UN USO DEFINITO"

  /**
   * Funzione di comodo che gestisce la segnalazione all'utente della mancanza dei dati obbligatori per le sezioni dei dati tecnici.
   * @param sezioniDTNonCompilate SezioneObbligatoriaDatiTecniciMancanti[] con la lista delle informazioni per la segnalazione utente.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori. Per default è: true.
   * @override La considerazione per i dati tecnici mancanti per gli USI/ULO, si riferisce al fatto che il dato tecnico non ha nemmeno un uso definito.
   *           La logica di default prende in condiserdazione l'obbligatorietà delle sezioni, che questo specifico quadro NON utilizza.
   */
  segnalaSezioniObbligatorieDatiTecniciNonCompilate(
    sezioniDTNonCompilate: SezioneObbligatoriaDatiTecniciMancanti[],
    segnalaRisultato: boolean = true
  ) {
    // Gestisco l'input
    sezioniDTNonCompilate = sezioniDTNonCompilate ?? [];

    // Verifico effettivamente se devo segnalare qualcosa e ci sono informazioni
    if (sezioniDTNonCompilate.length <= 0 || !segnalaRisultato) {
      // Non ci sono dati, oppure non bisogna segnlare niente
      return;
    }

    // La prima logica prevede di avere le segnalazioni aggregate per opera
    let segnalazioniAggregate: AggregatoreDatiTecniciMancanti[];
    segnalazioniAggregate = this.aggregaSegnalazioniDatiTecniciMacanti(
      sezioniDTNonCompilate
    );

    // Vado a generare una stringa unica per la segnalazione di tutti gli oggetti istanza che son senza dati tecnici
    let testoSegnalazioni: string = segnalazioniAggregate
      // Rimappo i dati da oggetto a stinga descrittiva
      .map((segnalazioneOpere: AggregatoreDatiTecniciMancanti) => {
        // Richiamo la funzione di aggregazione delle segnalazioni per la descrizione
        const desTipoOggettoIstanza = segnalazioneOpere.desTipoOggettoIstanza;
        const codiceScrivaOpere = segnalazioneOpere.codiceScrivaOpere;
        // Definisco una stringa che contenga le informazioni per i dati mancanti
        return `<br>${desTipoOggettoIstanza} ${codiceScrivaOpere}`;
        // #
      })
      // Concateno tutte le descrizioni delle segnalazioni
      .join('');

    // Recupero il codice per la segnalazione
    const code = ScrivaCodesMesseges.E086;
    // Recupero il codice di placholder per la stringa
    const phE086 = ScrivaCodesPlaceholders.E086[0];
    // Genero l'oggetto specifico per la sostituzione del placholder
    const placeholder: IMsgPlacholder = { ph: phE086, swap: testoSegnalazioni };

    // Richiamo la funzionalità per la segnalazione utente
    this.alertOpera(code, undefined, placeholder);
  }
}
