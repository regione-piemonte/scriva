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
import { cloneDeep, compact, flattenDeep, remove } from 'lodash';
import { ScrivaCodesPlaceholders } from '../../../../core/consts/scriva-codes-placeholders.consts';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { Quadro } from '../../../../shared/models';
import { MessageService } from '../../../../shared/services';
import { FormioUtilitiesService } from '../../../../shared/services/formio/formio-utilities.service';
import { TipiComponentiFormIo } from '../../../../shared/services/formio/utilities/formio.enums';
import {
  IFormioFormBuilder,
  IFormioFormBuilderHidden,
  IFormioFormBuilderPanel,
} from '../../../../shared/services/formio/utilities/formio.interfaces';
import { FormioFormBuilderTypes } from '../../../../shared/services/formio/utilities/formio.types';
import { LoggerService } from '../../../../shared/services/logger.service';
import { IMsgPlacholder } from '../../../../shared/services/message/utilities/message.interfaces';
import { OggettoIstanza, Opera } from '../../models';
import {
  AggregatoreDatiTecniciMancanti,
  SezioneObbligatoriaDatiTecniciMancanti,
} from '../../pages/presentazione-istanza/opere/utilities/opere.classes';
import { OpereConsts } from '../../pages/presentazione-istanza/opere/utilities/opere.consts';
import {
  IAggregatoreDatiTecniciMancanti,
  IDatiTecniciOggettoIstanza,
  IListeDatiTecniciOggettiIstanze,
  IOggettoIstanzaSenzaDatiTecnici,
  ISezioneObbligatoriaDatiTecniciMancanti,
} from '../../pages/presentazione-istanza/opere/utilities/opere.interfaces';
import { OpereService } from './opere.service';
import {
  IDatiObbligatoriDTParams,
  IDatiObbligatoriDTSezioneFormIoDTSpecificiParams,
  IDatiObbligatoriDTSezioneFormIoParams,
} from './utilities/opere-verifiche-step.interfaces';

@Injectable({ providedIn: 'root' })
export class OpereVerificheStepService {
  /** OpereConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new OpereConsts();

  /**
   * Costruttore.
   */
  constructor(
    protected _formioUtilities: FormioUtilitiesService,
    protected _logger: LoggerService,
    protected _message: MessageService,
    protected _opere: OpereService
  ) {}

  /**
   * #######################################################
   * FUNZIONI DI VERIFICA PASSAGGIO AL PROSSIMO STEP ISTANZA
   * #######################################################
   */

  // #region "VERIFICA OGGETTI ISTANZA ASSOCIATI AD ISTANZA"

  /**
   * Funzione che gestisce i controlli per verificare se l'utente ha collegato degli OggettiIstanza all'istanza in lavorazione.
   * @param oggettiIstanza OggettoIstanza[] con la lista di dati come riferimento per la verifica.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  verificaSegnalaOggIstAssociati(
    oggettiIstanza: OggettoIstanza[],
    segnalaRisultato: boolean
  ) {
    // Verifico se all'interno del componente sono presenti delle opere associate all'istanza
    const checkOggIstAssegnati = oggettiIstanza?.length > 0;
    // Verifico se non esistono elementi e se devo informare l'utente dell'errore
    if (!checkOggIstAssegnati && segnalaRisultato) {
      // C'è errore, definisco le informazioni per la messaggistica
      const code = ScrivaCodesMesseges.E011;
      // Visualizzo la segnalazione utente
      this.alertOpera(code);
      // #
    }

    // Ritorno il check per il valore dell'opera
    return checkOggIstAssegnati;
    // #
  }

  // #endregion "VERIFICA OGGETTI ISTANZA ASSOCIATI AD ISTANZA"

  // #region "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni OggettoIstanza collegato all'istanza abbia collegato a se le informazioni dei dati tecnici.
   * @param datiTecniciParams IListeDatiTecniciOggettiIstanze con le informazioni dei dati tecnici per la verifica.
   * @param oggettiIstanzaParams OggettoIstanza[] con la lista di dati come riferimento per la verifica.
   * @param opereParams Opera[] con la lista di opere gestita dal componente e usate per le verifiche dati.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  verificaTuttiOggettiIstanzaHannoDatiTecnici(
    datiTecniciParams: IListeDatiTecniciOggettiIstanze,
    oggettiIstanzaParams: OggettoIstanza[],
    opereParams: Opera[],
    segnalaRisultato: boolean
  ): boolean {
    // Verifico l'input
    oggettiIstanzaParams = oggettiIstanzaParams ?? [];
    // Recupero le informazioni per i controlli minimi
    let datiTecnici: IListeDatiTecniciOggettiIstanze;
    datiTecnici = cloneDeep(datiTecniciParams);

    // Definisco un flag che verifichi la validità dei dati tecnici degli oggetti istanza
    let checkDatiTecniciPerOggettiIstanza: boolean = false;
    // Verifico se sono stati definiti a livello componente dei dati tecnici e ci sono oggettiIstanza associati
    if (!datiTecnici && oggettiIstanzaParams.length > 0) {
      // Gestisco la segnalazione
      this.segnalaOggettiIstanzaMancanti(segnalaRisultato);
      // Mancanod le informazioni minimi, interompo il flusso
      return checkDatiTecniciPerOggettiIstanza;
      // #
    }

    // Definisco tutte le informazioni che servono per il controllo dati
    const oggettiIstanza: OggettoIstanza[] = cloneDeep(oggettiIstanzaParams);
    const opere: Opera[] = cloneDeep(opereParams);
    // Definisco una array che tenga traccia delle informazioni mancanti per i dati tecnici
    const datiTecniciMancanti: IOggettoIstanzaSenzaDatiTecnici[] = [];

    // Ciclo ogni oggetto istanza associato all'istanza e verifico se ha dei dati tecnici collegati ad esso
    oggettiIstanza.forEach((oggettoIstanza: OggettoIstanza) => {
      // Richiamo al funzione per verificare se l'oggetto istanza ha dati tecnici collegati
      let infoDTMancanti: IOggettoIstanzaSenzaDatiTecnici;
      infoDTMancanti = this.verificaOggettoIstanzaHaDatiTecnici(
        oggettoIstanza,
        datiTecnici,
        opere
      );

      // Verifico se effettivamente non ci sono dati tecnici per l'OggettoIstanza
      if (infoDTMancanti) {
        // Mancano i dati tencici, aggiungo le info alla lista per la comunicazione
        datiTecniciMancanti.push(infoDTMancanti);
        // #
      }
      // #
    });

    // La validità dei dati tecnici dipende dal fatto che tutti i dati tecnici siano abbinati agli oggetti istanza, quindi non ci sono segnalazioni da fare
    checkDatiTecniciPerOggettiIstanza = datiTecniciMancanti.length === 0;
    // Lancio la funzione di segnalazione informazioni all'utente
    this.segnalaOggettiIstanzaSenzaDatiTecnici(
      datiTecniciMancanti,
      segnalaRisultato
    );

    // Ritorno il risultato del check
    return checkDatiTecniciPerOggettiIstanza;
  }

  /**
   * Funzione che verifica se l'OggettoIstanza in input ha associati i propri dati tecnici.
   * @param oggettoIstanza OggettoIstanza con le informazioni per la verifica.
   * @param datiTecnici IListeDatiTecniciOggettiIstanze con l'oggetto contenente i dati tecnici della pagina per la verifica.
   * @param opere Opera[] con la lista delle opere per il recupero delle informazioni per gestire le segnalazioni.
   * @returns IDatiTecniciMancanti con le informazioni di dettaglio nel caso in cui l'OggettoIstanza non abbia dati tecnici collegati. Altrimenti undefined.
   */
  verificaOggettoIstanzaHaDatiTecnici(
    oggettoIstanza: OggettoIstanza,
    datiTecnici: IListeDatiTecniciOggettiIstanze,
    opere: Opera[]
  ): IOggettoIstanzaSenzaDatiTecnici {
    // Definisco una array che tenga traccia delle informazioni mancanti per i dati tecnici
    let datiTecniciMancanti: IOggettoIstanzaSenzaDatiTecnici;

    // Verifico se per l'oggetto istanza ciclato è presente il relativo dato tecnico all'interno dell'oggetto che raccoglie tutti i dati tecnici
    const checkDTOggIst: boolean =
      this.verificaPresenzaDatiTecniciPerOggettoIstanza(
        datiTecnici,
        oggettoIstanza
      );

    // Verifico se effettivamente ho trovato il dato tecnico associato all'OggettoIstanza collegato all'istanza attiva
    if (!checkDTOggIst) {
      // Manca il dato tecnico per l'OggettoIstanza iterato, raccolgo delle informazioni per il log
      let desTipoOggetto: string;
      desTipoOggetto =
        oggettoIstanza?.tipologia_oggetto?.des_tipologia_oggetto ?? '';
      let operaOggIst: Opera;
      operaOggIst = opere.find((opera: Opera) => {
        // Cerco nella lista delle opere dell'istanza quella collegata all'OggettoIstanza ciclato
        return opera.id_oggetto === oggettoIstanza.id_oggetto;
        // #
      });

      // Definisco le informazioni nell'oggetto per la tracciatura dei dati
      const infoDTMancanti: IOggettoIstanzaSenzaDatiTecnici = {
        tipo: desTipoOggetto,
        codScriva: operaOggIst?.cod_scriva,
      };

      // Definisco le informazioni per gestire i dati mancanti
      datiTecniciMancanti = infoDTMancanti;
      // #
    }

    // Ritorno eventuali segnalazioni
    return datiTecniciMancanti;
  }

  /**
   * Funzione che verifica se all'interno dei dati tecnici esiste almeno un'associazione all'OggettoIstanza in input.
   * @param datiTecnici IListeDatiTecniciOggettiIstanze come oggetto dei dati tecnici del quadro. L'oggetto raccoglie le liste con tutti i dati tecnici per tutti gli oggetti istanza in pagina.
   * @param oggettoIstanza OggettoIstanza con l'oggetto di riferimento per verificare la presenza delle informazioni dei dati tecnici.
   * @returns boolean con il risultato della verifica.
   */
  verificaPresenzaDatiTecniciPerOggettoIstanza(
    datiTecnici: IListeDatiTecniciOggettiIstanze,
    oggettoIstanza: OggettoIstanza
  ): boolean {
    // Definisco un check di validità a false
    let checkDTOggIst = false;

    // CODICE PRECEDENTE
    // Object.keys(this.datiTecnici).forEach((block) => {
    //   const isPresent = this.datiTecnici[block].some(
    //     (elem) => elem.id_oggetto_istanza === oggIst.id_oggetto_istanza
    //   );
    //   if (isPresent) {
    //     checkDTOggIst = true;
    //   }
    // });

    // Ciclo le informazioni dei dati tecnici e verifico se ci sono dati che combaciano con il riferimento ad un oggettoIstanza
    for (const [keyDT, valueDT] of Object.entries(datiTecnici)) {
      // Vado a fare un parse del tipo del dato tecnico forzando la tipizzazione
      const valuesDatoTecnico: IDatiTecniciOggettoIstanza[] = valueDT as any;
      // Il valore del dato tecnico è un array d'informazioni verifico se l'id_oggetto_istanza combacia con l'oggetto istanza iterato
      const isDTLinkToOggIst = valuesDatoTecnico?.some(
        (dt: IDatiTecniciOggettoIstanza) => {
          // Effettuo un confronto fra id_oggetto_istanza dell'oggetto ciclato e dell'oggetto istanza
          return dt.id_oggetto_istanza === oggettoIstanza.id_oggetto_istanza;
          // #
        }
      );

      // Verifico se effettivamente il dato tecnico è collegato all'oggetto istanza
      // NOTA: questa logica si attiva se un qualunque dei dati dati tecnici siano collegati ad uno degli oggetti istanza
      if (isDTLinkToOggIst) {
        // Forzo a true il flag
        checkDTOggIst = true;
        // #
      }
    }

    // Ritorno il valore del flag
    return checkDTOggIst;
  }

  /**
   * Funzione di comodo che gestisce la segnalazione all'utente della mancanza dei dati per gli OggettiIstanza associati all'istanza.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori. Per default è: true.
   */
  segnalaOggettiIstanzaMancanti(segnalaRisultato: boolean = true) {
    // Verifico effettivamente se devo segnalare
    if (segnalaRisultato) {
      // Definisco il codice per la segnalazione
      const code = ScrivaCodesMesseges.E062;
      // Lancio la funzione di segnalazione utente
      this.alertOpera(code);
      // #
    }
  }

  /**
   * Funzione di comodo che gestisce la segnalazione all'utente della mancanza dei dati tecnici per gli OggettiIstanza associati all'istanza.
   * @param datiTecniciMancanti IDatiTecniciMancanti[] con la lista delle informazioni per la segnalazione utente.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori. Per default è: true.
   */
  segnalaOggettiIstanzaSenzaDatiTecnici(
    datiTecniciMancanti: IOggettoIstanzaSenzaDatiTecnici[],
    segnalaRisultato: boolean = true
  ) {
    // Verifico effettivamente se devo segnalare qualcosa e ci sono informazioni
    if (datiTecniciMancanti?.length > 0 && segnalaRisultato) {
      // Vado a generare una stringa unica per la segnalazione di tutti gli oggetti istanza che son senza dati tecnici
      let segnalazioni: string = datiTecniciMancanti
        // Rimappo i dati da oggetto a stinga descrittiva
        .map((dtMancanti: IOggettoIstanzaSenzaDatiTecnici) => {
          // Definisco una stringa che contenga le informazioni per i dati mancanti
          return `<br>${dtMancanti.tipo} ${dtMancanti.codScriva}`;
          // #
        })
        // Concateno tutte le descrizioni delle segnalazioni
        .join('');

      // Recupero il codice per la segnalazione
      const code = ScrivaCodesMesseges.E063;
      // Recupero il codice di placholder per la stringa
      const phE063 = ScrivaCodesPlaceholders.E063[0];
      // Genero l'oggetto specifico per la sostituzione del placholder
      const placeholder: IMsgPlacholder = { ph: phE063, swap: segnalazioni };

      // Richiamo la funzionalità per la segnalazione utente
      this.alertOpera(code, undefined, placeholder);
    }
  }

  // #endregion "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  // #region "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni sezione definita come obbligatoria dei dati tecnici sia effettivamente compilata.
   * @param datiTecniciParams IListeDatiTecniciOggettiIstanze con i dati tecnici per la verifica dei dati obbligatori.
   * @param tipologieOpereParams string[] con la lista delle tipologie di opere utilizzate dal quadro dati.
   * @param oggettiIstanzaParams OggettoIstanza[] con la lista degli oggetti istanza associati all'istanza nel quadro.
   * @param opereParams Opera[] con la lista delle opere dalla quale gli oggetti istanza hanno un collegamento con l'istanza.
   * @param quadroParams Quadro con le configurazioni del quadro per la gestione delle verifiche.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  verificaPresenzaDatiTecniciObbligatori(
    datiTecniciParams: IListeDatiTecniciOggettiIstanze,
    tipologieOpereParams: string[],
    oggettiIstanzaParams: OggettoIstanza[],
    opereParams: Opera[],
    quadroParams: Quadro,
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni per le verifiche
    let datiTecnici: IListeDatiTecniciOggettiIstanze;
    datiTecnici = datiTecniciParams;
    const tipoOpere: string[] = tipologieOpereParams;
    const quadro: Quadro = quadroParams;
    const oggettiIstanza: OggettoIstanza[] = oggettiIstanzaParams;
    const opere: Opera[] = opereParams;

    // Definisco un array contenitore per la raccolta informazioni per le sezioni dei dati tecnici non compilati
    let sezioniDTNonCompilate: SezioneObbligatoriaDatiTecniciMancanti[];
    sezioniDTNonCompilate = this.verificaDatiObbligatoriDatiTecniciPerTipiOpere(
      datiTecnici,
      tipoOpere,
      oggettiIstanza,
      opere,
      quadro
    );
    // Gestisco il dato di ritorno
    sezioniDTNonCompilate = sezioniDTNonCompilate ?? [];

    // Verifico se esistono segnalazioni e gestisco la visualizzazione all'utente
    this.segnalaSezioniObbligatorieDatiTecniciNonCompilate(
      sezioniDTNonCompilate,
      segnalaRisultato
    );

    // Ritorno il risultato del check
    return sezioniDTNonCompilate.length === 0;
  }

  /**
   * Funzione che verifica le informazioni per le tipologie opere e le informazioni dei dati tecnici.
   * Le sezioni dei dati tecnici obbligatori devono essere presenti, altrimenti verranno gestite le informazioni per eventuali sengalazioni utente.
   * La funzione verifica le informazioni con uno strato di controlli a livello: Tipi Opere.
   * @param datiTecnici IListeDatiTecniciOggettiIstanze con tutte le informazioni dei dati tecnici degli OggettiIstanza dell'Istanza.
   * @param tipiOpere string[] con la lista delle tipologie delle opere gestite dalla pagina.
   * @param oggettiIstanza OggettoIstanza[] con la lista degli oggetti istanza associati all'istanza.
   * @param opere Opera[] con la lista delle opere gestite nella sessione della pagina.
   * @param quadro Quadro con le configurazioni del quadro per poter gestire la verifica sui dati obbligatori.
   * @returns SezioniObbligatorieDatiTecniciMancanti[] con la lista di eventuali segnalazioni per la mancanza dei dati obbligatori delle sezioni dei dati tecnici. Se non ci sono errori, verrà ritornato lista vuota.
   */
  verificaDatiObbligatoriDatiTecniciPerTipiOpere(
    datiTecnici: IListeDatiTecniciOggettiIstanze,
    tipiOpere: string[],
    oggettiIstanza: OggettoIstanza[],
    opere: Opera[],
    quadro: Quadro
  ) {
    // Definisco un array contenitore per la raccolta informazioni per le sezioni dei dati tecnici non compilati
    let sezioniDTNonCompilate: SezioneObbligatoriaDatiTecniciMancanti[][] = [];

    // Verifico che dati tecnici e i tipi opere esistano come informazioni
    if (datiTecnici && tipiOpere) {
      // Itero tutti i tipi opere per il quadro
      tipiOpere.forEach((tipoOpera: string) => {
        // Definisco l'oggetto con i parametri da passare alla funzione per la generazione delle segnazioni
        const verificaParams: IDatiObbligatoriDTParams = {
          datiTecnici,
          oggettiIstanza,
          opere,
          quadro,
          tipoOpera,
        };

        // Richiamo la funzione che gestirà le segnalazioni e riassegno il valore ottenuto alla variabile locale
        const segnalazioniDTVuoti: SezioneObbligatoriaDatiTecniciMancanti[] =
          this.verificaDatiObbligatoriDatiTecniciPerTipoOpera(verificaParams);

        // Aggiungo alla matrice dati l'array per le segnalazioni del tipo opera
        sezioniDTNonCompilate.push(segnalazioniDTVuoti);
        // #
      });
    }

    // Vado ad "appiattire" la matrice, creando un array monodimensionale
    let segnalazioniDT: SezioneObbligatoriaDatiTecniciMancanti[];
    segnalazioniDT = flattenDeep(sezioniDTNonCompilate);

    // Ritorno la lista di segnalazioni per i dati obbligatori mancanti per i dati tecnici
    return segnalazioniDT;
  }

  /**
   * Funzione che verifica le informazioni per le tipologie opere e le informazioni dei dati tecnici.
   * La funzione verifica le informazioni con uno strato di controlli a livello: Tipi Opere => Tipo Opera.
   * @param verificaParams IDatiObbligatoriDTParams con l'oggetto contenente i parametri di verifica per i dati.
   * @returns SezioniObbligatorieDatiTecniciMancanti[] con la lista di eventuali segnalazioni per la mancanza dei dati obbligatori delle sezioni dei dati tecnici. Se non ci sono errori, verrà ritornato lista vuota.
   */
  verificaDatiObbligatoriDatiTecniciPerTipoOpera(
    verificaParams: IDatiObbligatoriDTParams
  ): SezioneObbligatoriaDatiTecniciMancanti[] {
    // Verifico l'input
    verificaParams = verificaParams ?? ({} as any);
    // Creo una copia delle informazioni
    const params: IDatiObbligatoriDTParams = verificaParams;
    // Estraggo dall'oggetto i parametri per gestire le verifiche
    const { datiTecnici, oggettiIstanza, quadro, tipoOpera } = params;

    // ### VERIFICA DATI 1
    // Verifico se ci sono le informazioni per la gestione dati
    if (!datiTecnici || !tipoOpera) {
      // Mancano le informazioni, ritorno la lista dei dati mancanti per com'è stata passata
      return [];
      // #
    }

    // Recupero la configurazione del quadro specifico, dato il tipo dell'opera
    const quadroConfigByTipoOpera: IFormioFormBuilder =
      quadro?.json_configura_quadro[tipoOpera];
    // Verifico se all'interno degli oggetti istanza collegati all'istanza esiste il tipo dell'opera gestito
    const existTipoOperaInOggIst: boolean = this.tipoOperaInOggettiIstaza(
      tipoOpera,
      oggettiIstanza
    );

    // ### VERIFICA DATI 2
    // Verifico se le configurazioni del quadro e degli oggetti istanza sono state trovate
    if (!quadroConfigByTipoOpera || !existTipoOperaInOggIst) {
      // Anche per questo livello non si può proseguire con la logica
      return [];
      // #
    }

    // Estraggo i componenti FormIo dalla configurazione del quadro
    let formioComponents: FormioFormBuilderTypes[];
    formioComponents = quadroConfigByTipoOpera.components ?? [];
    // Dai componenti estratti, filtro per ottenere solo i componenti "panel" di FormIo
    const sezioniDatiFormio: IFormioFormBuilderPanel[] =
      formioComponents.filter((component: FormioFormBuilderTypes) => {
        // Ritorno solo i componenti di tipo "panel"
        return component?.type === TipiComponentiFormIo.pannello;
        // #
      });

    // Definisco un array contenitore per la raccolta informazioni per le sezioni dei dati tecnici non compilati
    let segnalazioniSezioniDTVouti: SezioneObbligatoriaDatiTecniciMancanti[][];
    segnalazioniSezioniDTVouti = [];
    // Ciclo tutte le sezioni del FormIo con i campi dati
    sezioniDatiFormio.forEach((sezioneFormIo: IFormioFormBuilderPanel) => {
      // Definisco le informazioni per la gestione delle segnalazioni al "prossimo livello"
      let param: IDatiObbligatoriDTSezioneFormIoParams = {
        datiObbligatoriParams: params,
        sezioneFormIo,
      };

      // Richiamo la funzione e assegno il risultato alla lista locale
      const segnalazioniTipoOpera: SezioneObbligatoriaDatiTecniciMancanti[] =
        this.verificaDatiObbligatoriDatiTecniciPerTipoOperaSezioneFormIo(param);
      // Vado a definire l'array per la matrice dati
      segnalazioniSezioniDTVouti.push(segnalazioniTipoOpera);
      // #
    });

    // Vado ad "appiattire" la matrice, creando un array monodimensionale
    let segnalazioniDT: SezioneObbligatoriaDatiTecniciMancanti[];
    segnalazioniDT = flattenDeep(segnalazioniSezioniDTVouti);

    // Ritorno la lista di segnalazioni per i dati obbligatori mancanti per i dati tecnici
    return segnalazioniDT;
  }

  /**
   * Funzione che verifica le informazioni per le tipologie opere e le informazioni dei dati tecnici.
   * La funzione verifica le informazioni con uno strato di controlli a livello: Tipi Opere => Tipo Opera => Sezione FormIo.
   * @param verificaParams IDatiObbligatoriDTSezioneFormIoParams con l'oggetto contenente i parametri di verifica per i dati.
   * @returns SezioneObbligatoriaDatiTecniciMancanti[] con la lista di eventuali segnalazioni per la mancanza dei dati obbligatori delle sezioni dei dati tecnici. Se non ci sono errori, verrà ritornato lista vuota.
   */
  verificaDatiObbligatoriDatiTecniciPerTipoOperaSezioneFormIo(
    verificaParams: IDatiObbligatoriDTSezioneFormIoParams
  ): SezioneObbligatoriaDatiTecniciMancanti[] {
    // Verifico l'input
    verificaParams = verificaParams ?? ({} as any);
    // Creo una copia delle informazioni
    let params: IDatiObbligatoriDTSezioneFormIoParams;
    params = verificaParams;
    // Estraggo dall'oggetto i parametri per gestire le verifiche
    const { datiObbligatoriParams, sezioneFormIo } = params;
    const { datiTecnici, tipoOpera } = datiObbligatoriParams ?? {};

    // Cerco all'interno della sezione quel componente FormIo che abbia la chiave identificativa per la sezione obbligatoria
    const componenteSezioneObbligatoria: IFormioFormBuilderHidden =
      this.cercaSezioneObbligatoriaFormIo(sezioneFormIo);

    // Cerco di estrarre il valore di default per la sezione
    let isSezioneObbligatoria: boolean = this.gestisciValoreSezioneObbligatoria(
      componenteSezioneObbligatoria,
      params
    );

    // Cerco di recuperare all'interno dei dati tecnici il blocco dati per il tipo opera
    const chiaveDTTipoOpera: string = this._opere.keyDatiTecniciTipoOpera(
      this.prefixJsDT,
      tipoOpera
    );
    let datiTecniciTipoOpera: IDatiTecniciOggettoIstanza[];
    datiTecniciTipoOpera = datiTecnici[chiaveDTTipoOpera];

    // Verifico se è stato trovato un valore per il campo obbligatorio e dentro i dati tecnici esistano effettivamente i dati tecnici per la sezione
    if (!isSezioneObbligatoria || !datiTecniciTipoOpera) {
      // Mancano le informazioni per proseguire
      return [];
      // #
    }

    // Definisco un array contenitore per la raccolta informazioni per le sezioni dei dati tecnici non compilati
    let segnalazioniDTVuoti: SezioneObbligatoriaDatiTecniciMancanti[];
    segnalazioniDTVuoti = [];
    // Itero le informazioni tecniche estratte per il tipo opera
    datiTecniciTipoOpera.forEach((dTTipoOpera: IDatiTecniciOggettoIstanza) => {
      // Definisco le informazioni per la gestione delle segnalazioni al "prossimo livello"
      let params: IDatiObbligatoriDTSezioneFormIoDTSpecificiParams;
      params = {
        datiObbligatoriParams,
        datiTecniciPerTipoOpera: dTTipoOpera,
        sezioneFormIo,
      };
      // Richiamo la funzione e assegno il risultato alla lista locale
      const segnalazione: SezioneObbligatoriaDatiTecniciMancanti =
        this.verificaDatiObbligatoriDatiTecniciPerTipoOperaDTSpecifici(params);

      // Aggiungo l'oggetto alla lista
      segnalazioniDTVuoti.push(segnalazione);
      // #
    });

    // Dalla lista applico un filtro per togliere tutti gli oggetti undefined
    segnalazioniDTVuoti = compact(segnalazioniDTVuoti);

    // Ritorno la lista di segnalazioni per i dati obbligatori mancanti per i dati tecnici
    return segnalazioniDTVuoti;
  }

  /**
   * Funzione che gestisce le verifiche e forza eventuali parse per l'indicazione di sezione obbligatoria per una parte delle sezioni di formio.
   * @param componenteSezioneObbligatoria IFormioFormBuilderHidden con le informazioni del componente FormIo che definisce se la sezione è obbligatoria.
   * @param verificaParams IDatiObbligatoriDTSezioneFormIoParams con le informazioni di contesto dati che si stanno andando a gestire.
   * @returns boolean come risultato per il valore della sezione obbligatoria.
   */
  protected gestisciValoreSezioneObbligatoria(
    componenteSezioneObbligatoria: IFormioFormBuilderHidden,
    verificaParams: IDatiObbligatoriDTSezioneFormIoParams
  ): boolean {
    // Cerco di estrarre il valore di default per la sezione
    let isSezioneObbligatoria: any =
      componenteSezioneObbligatoria?.defaultValue;

    // ATTENZIONE: questo valore deve essere boolean, ma il formio potrebbe essere impostato come "string", forzo quindi un parse
    if (typeof isSezioneObbligatoria === 'string') {
      // Per sicurezza stampo un warning con il valore effettivo presente su FormIo
      const t = `[opere-verifiche-step.service.ts] - verificaDatiObbligatoriDatiTecniciPerTipoOperaSezioneFormIo`;
      const b = {
        formioDefaultValue: componenteSezioneObbligatoria?.defaultValue,
        expected: `Valid values: true, false, 'true', 'false'`,
        contextData: verificaParams,
      };
      this._logger.warning(t, b);
      // E' una stringa, devo forzare il parse a booleano
      const boolText: string = isSezioneObbligatoria.trim().toLocaleLowerCase();
      const isTrue: boolean = boolText === 'true';
      const isFalse: boolean = boolText === 'false';
      // Verifico che tipo di valore è definito (se true, torno true; se false, torno false, altrimenti torno nulll)
      return isTrue ? true : isFalse ? false : null;
      // #
    }

    // E' un altro valore, dovrebbe essere boolean, altrimenti è un errore di configurazione formio
    return isSezioneObbligatoria;
  }

  /**
   * Funzione che verifica le informazioni per le tipologie opere e le informazioni dei dati tecnici.
   * La funzione verifica le informazioni con uno strato di controlli a livello: Tipi Opere => Tipo Opera => Sezione FormIo.
   * @param verificaParams IDatiObbligatoriDTSezioneFormIoDTSpecificiParams con l'oggetto contenente i parametri di verifica per i dati.
   * @returns SezioniObbligatorieDatiTecniciMancanti con le informazioni per l'eventuale segnalazione. Se non ci sono segnalazioni, ritornerà: undefined.
   */
  verificaDatiObbligatoriDatiTecniciPerTipoOperaDTSpecifici(
    verificaParams: IDatiObbligatoriDTSezioneFormIoDTSpecificiParams
  ): SezioneObbligatoriaDatiTecniciMancanti {
    // Verifico l'input
    verificaParams = verificaParams ?? ({} as any);
    // Creo una copia delle informazioni
    let params: IDatiObbligatoriDTSezioneFormIoDTSpecificiParams;
    params = verificaParams;
    // Estraggo dall'oggetto i parametri per gestire le verifiche
    const { datiObbligatoriParams, sezioneFormIo, datiTecniciPerTipoOpera } =
      params;
    const { oggettiIstanza, opere } = datiObbligatoriParams ?? {};

    // Recupero la proprietà "key" della sezione
    const codiceSezioneObbligatoria: string =
      this.identificativoComponenteFormIo(sezioneFormIo);
    // Cerco di recuperare la sezione specifica dai dati tecnici legata all'opera
    const sezioneDTOpera: any =
      datiTecniciPerTipoOpera[codiceSezioneObbligatoria];

    // Verifico se nei dati tecnici x opera esiste la chiave della sezione
    if (sezioneDTOpera) {
      // I dati tecnici per la sezione sono presenti, quindi non ci sono segnalazioni, blocco il flusso e ritorno la lista delle segnalazioni
      return undefined;
      // #
    }

    // Recupero dai dati tecnici dell'opera ciclati l'id_oggetto_istanza come riferimento
    const idOggIstRefDT = datiTecniciPerTipoOpera.id_oggetto_istanza;
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

    // Definisco le informazioni per la segnalazione della mancanza dati
    let infoSezioniDTMancante: ISezioneObbligatoriaDatiTecniciMancanti;
    infoSezioniDTMancante = {
      oggettoIstanza: oggettoIstanzaPerDT,
      opera: operaPerDT,
      sezioneFormIo: sezioneFormIo,
    };
    // Creo un oggetto per la gestione delle segnalazioni
    const datiSezioneDTMancante = new SezioneObbligatoriaDatiTecniciMancanti(
      infoSezioniDTMancante
    );

    // Ritorno la lista di segnalazioni per i dati obbligatori mancanti per i dati tecnici
    return datiSezioneDTMancante;
  }

  /**
   * Funzione di supporto che verifica se un tipo opera è presente all'interno della lista oggetti istanza.
   * @param tipoOpera string con il tipo opera da verificare.
   * @param oggettiIstanza OggettoIstanza[] con la lista di dati per la verifica.
   * @returns boolean con la presenza del dato nella lista.
   */
  protected tipoOperaInOggettiIstaza(
    tipoOpera: string,
    oggettiIstanza: OggettoIstanza[]
  ): boolean {
    // Verifico l'input per gestire i dati
    oggettiIstanza = oggettiIstanza ?? [];
    // Verifico all'interno della lista degli oggetti istanza la presenza del tipo opera
    return oggettiIstanza.some((oggIst: OggettoIstanza) => {
      // Recupero la tipologia dell'oggetto istanza ciclato
      const codTO: string = oggIst.tipologia_oggetto?.cod_tipologia_oggetto;
      // Verifico se il codice estratto è lo stesso del tipo opera
      return codTO === tipoOpera;
      // #
    });
  }

  /**
   * Funzione di comodo che cerca all'interno di una sezione di componente FormIo, il sotto componente specifico che definisce le informazioni obbligatorie.
   * @param sezioneFormIo FormioFormBuilderTypes con la struttura FormIo del componente che dovrebbe contenere le informazioni per l'obbligatorietà della sezione.
   * @returns FormioFormBuilderTypes con le informazioni del componente che determina le informazioni obbligatorie della sezione.
   */
  protected cercaSezioneObbligatoriaFormIo(
    sezioneFormIo: FormioFormBuilderTypes
  ): FormioFormBuilderTypes {
    // Recupero la proprietà "key" della sezione
    const identificativoSezione: string =
      this.identificativoComponenteFormIo(sezioneFormIo);
    // Recupero la costante utilizzata su FormIo usata per indicare come obbligatoria la sezione
    const chiaveSezioneRequired: string = this.sectionRequired;
    // Combino le chiavi in una stringa che permetta d'identificare una sezione obbligatoria
    const sezioneObbligatoria: string = `${chiaveSezioneRequired}.${identificativoSezione}`;

    // Cerco all'interno della sezione quel componente FormIo che abbia la chiave identificativa per la sezione obbligatoria
    const componenteSezioneObbligatoria = sezioneFormIo?.components?.find(
      (componente: FormioFormBuilderTypes) => {
        // Recupero la chiave del componente ciclato
        const keyComponente = componente.key;
        // Verifico se l'identificativo del componente è quello per la sezione obbligatoria
        return keyComponente === sezioneObbligatoria;
        // #
      }
    );

    // Ritorno il componente, se trovato, della sezione obbligatori
    return componenteSezioneObbligatoria;
  }

  /**
   * Funzione di comodo che recupera l'identificativo di un componente formIo.
   * @param componenteFormIo FormioFormBuilderTypes con la struttura FormIo del componente che dovrebbe contenere le informazioni per il recupero dell'identificativo.
   * @returns string con l'identificativo trovato, altrimenti stringa vuota.
   */
  protected identificativoComponenteFormIo(
    componenteFormIo: FormioFormBuilderTypes
  ): string {
    // Richiamo la funzione del servizio
    return this._formioUtilities.identificativoComponenteFormIo(
      componenteFormIo
    );
  }

  /**
   * Funzione di comodo che gestisce la segnalazione all'utente della mancanza dei dati obbligatori per le sezioni dei dati tecnici.
   * @param sezioniDTNonCompilate SezioneObbligatoriaDatiTecniciMancanti[] con la lista delle informazioni per la segnalazione utente.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori. Per default è: true.
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
        const sezioniFormIo = segnalazioneOpere.desSezioniFormIo;
        // Definisco una stringa che contenga le informazioni per i dati mancanti
        return `<br>${desTipoOggettoIstanza} ${codiceScrivaOpere} (<strong>${sezioniFormIo}</strong>)`;
        // #
      })
      // Concateno tutte le descrizioni delle segnalazioni
      .join('');

    // Recupero il codice per la segnalazione
    const code = ScrivaCodesMesseges.E064;
    // Recupero il codice di placholder per la stringa
    const phE064 = ScrivaCodesPlaceholders.E064[0];
    // Genero l'oggetto specifico per la sostituzione del placholder
    const placeholder: IMsgPlacholder = { ph: phE064, swap: testoSegnalazioni };

    // Richiamo la funzionalità per la segnalazione utente
    this.alertOpera(code, undefined, placeholder);
  }

  /**
   * Funzione di comodo che gestisce l'aggragazione delle informazioni per le segnalazioni dei dati tecnici mancanti.
   * @param sezioniDTNonCompilate SezioneObbligatoriaDatiTecniciMancanti[] con la lista delle informazioni per la segnalazione utente.
   * @returns AggregatoreDatiTecniciMancanti[] con le informazioni aggregate.
   */
  protected aggregaSegnalazioniDatiTecniciMacanti(
    sezioniDTNonCompilate: SezioneObbligatoriaDatiTecniciMancanti[]
  ): AggregatoreDatiTecniciMancanti[] {
    // Gestisco l'input
    sezioniDTNonCompilate = sezioniDTNonCompilate ?? [];

    // Verifico effettivamente se devo segnalare qualcosa e ci sono informazioni
    if (sezioniDTNonCompilate.length <= 0) {
      // Non ci sono dati
      return [];
      // #
    }

    // Creo una copia delle segnalazioni per sicurezza
    let segnalazioni: SezioneObbligatoriaDatiTecniciMancanti[];
    segnalazioni = [...sezioniDTNonCompilate];
    // La prima logica prevede di avere le segnalazioni aggregate per opera
    const segnalazioniAggregate: AggregatoreDatiTecniciMancanti[] = [];

    // Vado a gestire le logiche fino a che ho elementi nelle sezioni non compilate
    // Nel ciclo verranno rimossi gli elementi nell'array fino a che le segnalazioni non saranno state tutte aggregate.
    while (segnalazioni.length > 0) {
      // Recupero l'opera del primo oggetto della lista
      let segnalazione1st: SezioneObbligatoriaDatiTecniciMancanti;
      segnalazione1st = segnalazioni[0];
      const opera1st: Opera = segnalazione1st.opera;

      // Recuperata l'opera filtro e RIMUOVO gli oggetti per stesso oggetto opera
      const segnalazioniStesseOpere: SezioneObbligatoriaDatiTecniciMancanti[] =
        remove(segnalazioni, (s: SezioneObbligatoriaDatiTecniciMancanti) => {
          // Verifico se l'oggetto opera è lo stesso
          return s.sameOpera(opera1st);
          // #
        });

      // Ci sarà sempre almeno un'opera, genero quindi un oggetto aggregatore
      let params: IAggregatoreDatiTecniciMancanti;
      params = { datiSegnalazioni: segnalazioniStesseOpere };
      // Creo la classe che gestirà l'insieme delle segnalazioni
      const segnalazioniOpere = new AggregatoreDatiTecniciMancanti(params);

      // Pusho le aggregazioni nell'array
      segnalazioniAggregate.push(segnalazioniOpere);
      // #
    }

    // Ritorno le informazioni aggregate
    return segnalazioniAggregate;
  }

  // #endregion "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * ###############################
   * FUNZIONI DI SEGNALAZIONE UTENTE
   * ###############################
   */

  /**
   * Funzione di supporto che gestisce le segnalazioni da effettuare verso l'utente con target: opera.
   * @param code ScrivaCodesMesseges con il codice da visualizzare all'utente.
   * @param autoFade boolean con le indicazioni per la gestione della scomparsa automatica del messaggio.
   * @param placeholders IMsgPlacholder | IMsgPlacholder[] con la configurazione per i placeholder dentro il codice messaggio.
   */
  alertOpera(
    code: ScrivaCodesMesseges,
    autoFade?: boolean,
    placeholders?: IMsgPlacholder | IMsgPlacholder[]
  ) {
    // Definisco la configurazione per la gestione dell'errore
    code = code ?? ScrivaCodesMesseges.E100;
    autoFade = autoFade ?? false;
    const target = this.OPERE_CONSTS.ALERT_TARGET_OPERA;

    // Visualizzo il messaggio d'errore
    this._message.showMessage(code, target, autoFade, placeholders);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera la chiave FormIo per identificare una sezione required.
   * @returns string che definisce la chiave del FormIo che definisce se una sezione dati risulta obbligatoria.
   */
  get sectionRequired(): string {
    // Recupero dalla configurazione delle costanti attiva la chiave identificativa
    return this.OPERE_CONSTS.SECTION_REQUIRED;
  }

  /**
   * Getter che recupera la chiave FormIo per il prefisso delle sezioni dati formio.
   * @returns string che definisce la chiave del FormIo che definisce il prefisso per le strutture dati dei dati tecnici.
   */
  get prefixJsDT(): string {
    // Recupero dalla configurazione delle costanti attiva la chiave identificativa
    return this.OPERE_CONSTS.PREFIX_JS_DT;
  }
}
