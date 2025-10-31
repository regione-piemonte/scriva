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
import { forkJoin, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ScrivaCodTipiAdempimenti } from '../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { ConfigAdempimento } from '../../../../../shared/models';
import { ConfigurazioniScrivaService } from '../../../../../shared/services';
import { TipoInfoAdempimento } from '../../../../../shared/services/configurazioni/utilities/configurazioni.enums';
import {
  IFormioFormBuilderContainer,
  IFormioFormBuilderHidden,
  IFormioFormBuilderHTMLElement,
} from '../../../../../shared/services/formio/utilities/formio.interfaces';
import { FormioFormBuilderTypes } from '../../../../../shared/services/formio/utilities/formio.types';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { CodiciAutoritaCompetenti } from '../../../pages/orientamento-istanza/utilities/orientamento-istanza.enums';
import {
  IFormIoOrientamentoDichiarazioni,
  IFormIoOrientamentoDichiarazioniImports,
} from '../../../pages/orientamento-istanza/utilities/orientamento-istanza.interfaces';
import { DichiarazioniOrientamentoVIAConsts } from './utilities/dichiarazioni/dichiarazioni-orientamento-via.consts';
import {
  IACFormioReq,
  IACFormioRes,
  IGestisciACDichiarazioni,
} from './utilities/orientamento-istanza-dichiarazioni.interfaces';

/**
 * Servizio di supporto al componente presentazione-istanza.component.ts.
 */
@Injectable({ providedIn: 'root' })
export class OrientamentoIstanzaDichiarazioniService {
  /** OrientamentoIstanzaDichiarazioniConsts con le costanti del componente. */
  private DVIA_C = new DichiarazioniOrientamentoVIAConsts();

  /**
   * Costruttore.
   */
  constructor(
    private _configurazioni: ConfigurazioniScrivaService,
    private _logger: LoggerService
  ) {}

  // #region "GESTIONE DICHIARAZIONI PER COMPETENZA TERRITORIO"

  /**
   * Funzione di supporto che gestisce il flusso applicativo per il quadro orientamento quando viene selezionata un'autorità competente.
   * Alla selezione di un'autorità competente, il flusso deve ritornare tutta una serie d'informazioni per la gestione dei quadri FormIo.
   * Al momento la tipologia d'informazione gestita è "WEB_AC_GDPR", ma lascio generico il parametro per gestire altri flussi simili.
   * @param params IGestisciACVIA con tutte le informazioni per la gestione dell'autorità competente.
   * @returns Observale<IFormIoOrientamentoVIAImports> con le informazioni per il quadro FormIo per la gestione della pagina.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   * @notes Sto creando un flusso a parte e modulare perché le logiche per il quadro orientamento dovranno essere rifattorizzate.
   *        In questo modo cerco di preparare il lavoro per quando verrà fatta la manutenzione/modifica del codice.
   */
  gestisciDatiACDichiarazioniFormIo(
    params: IGestisciACDichiarazioni
  ): Observable<IFormIoOrientamentoDichiarazioni> {
    // Verifico l'input
    if (!params) {
      // Ritorno undefined
      return of(undefined);
      // #
    }

    // Estraggo dai parametri le informazioni per la gestione dei dati
    const { codiceProcedimento, tipoInformazione, autoritaCompetente } = params;
    // La casistica prevede due strade: l'autorità competente è regione piemonte, oppure un'altra autorità competente, quindi definisco un oggetto per le chiamate
    // La chiamata per lo scarico dei dati di regione piemonte ci sarà sempre, @Simona_Rotolo ha confermato questo flusso, quindi la definisco immediatamente
    const autoritaCompetentiReq: IACFormioReq = {
      regionePiemonte: this.getConfigurazioniAutoritaCompetente(
        codiceProcedimento,
        tipoInformazione,
        CodiciAutoritaCompetenti.regionePiemonte
      ),
    };
    // Recupero il codice dall'oggetto dell'autorità competente selezionata dall'utente
    const codiceAutoritaCompetente: string =
      autoritaCompetente?.cod_competenza_territorio;
    // Verifico se il codice dell'autorità competente è diverso da quello di regione piemonte
    if (codiceAutoritaCompetente !== CodiciAutoritaCompetenti.regionePiemonte) {
      // L'utente ha selezionato un'altra autorità competente rispetto a regione piemonte, aggiungo la chiamata
      autoritaCompetentiReq.altraAutoritaCompetente =
        this.getConfigurazioniAutoritaCompetente(
          codiceProcedimento,
          tipoInformazione,
          codiceAutoritaCompetente
        );
    }

    // Effettuo le chiamate per la gestione dei dati
    return forkJoin(autoritaCompetentiReq).pipe(
      map((autoritaCompetentiRes: IACFormioRes) => {
        // Ottenute le informazioni lancio la funzione per generare le informazioni per il formio
        let formioData: IFormIoOrientamentoDichiarazioni;
        formioData = this.gestisciDichiarazioniCompetenzeTerritorio(
          autoritaCompetentiRes,
          params
        );
        // Ritorno la struttura del formio con i dati per le dichiarazioni
        return formioData;
      })
    );
  }

  /**
   * Funzione che permette lo scarico delle configurazioni adempimento per le autorità competenti.
   * @param codiceAdempimento string con il codice dell'adempimento per la ricerca delle configurazioni.
   * @param tipoInformazione TipoInfoAdempimento con la tipologia d'informazione da scaricare.
   * @param autoritaCompetente string con il codice dell'aturotià competente o codice competenza territorio.
   * @returns Observable<ConfigAdempimento> con la configurazione scaricata.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   * @notes Secondo quanto mi è stato spiegato, scaricando le configurazioni per un'autorità competente si avrà sempre un solo oggetto.
   *        Vado quindi a rimappare il ritorno, in maniera tale che si abbia solo un oggetto restituito e non una lista.
   */
  private getConfigurazioniAutoritaCompetente(
    codiceAdempimento: string,
    tipoInformazione: TipoInfoAdempimento,
    autoritaCompetente: string
  ): Observable<ConfigAdempimento> {
    // Richiamo il servizio passando i parametri per la ricerca
    return this._configurazioni
      .getConfigurazioniByInfoAndChiave(
        codiceAdempimento,
        tipoInformazione,
        autoritaCompetente
      )
      .pipe(
        map((configurazioni: ConfigAdempimento[]) => {
          // Ritorno solo il primo elemento estratto
          return configurazioni[0];
          // #
        })
      );
  }

  /**
   * Funzione pensata per gestire la logica per le competenze territorio di VIA.
   * @param configurazioniAdempimento ConfigAdempimento[] con la lista degli elementi scaricati per VIA.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   */
  private gestisciDichiarazioniCompetenzeTerritorio(
    autoritaCompetentiRes: IACFormioRes,
    params: IGestisciACDichiarazioni
  ): IFormIoOrientamentoDichiarazioni {
    // Estraggo dai parametri le informazioni di cui necessito per la gestione dei dati
    const { tipoAdempimento, formioStructure, autoritaCompetente } = params;
    // Recupero il codice dall'oggetto dell'autorità competente selezionata dall'utente
    const codiceAutoritaCompetente: string =
      autoritaCompetente?.cod_competenza_territorio;
    // Il codice per il tipo adempimento è uguale indipendentemente che l'ac selezionata sia regione piemonte o un'altra
    const codTipoAdempimento: string = tipoAdempimento?.cod_tipo_adempimento;
    // Per il codice dell'autorità competente come dato "base", è sempre quella di regione piemonte
    let codAutCompetente: string = CodiciAutoritaCompetenti.regionePiemonte;
    // Anche per il link di accesso usato come dato "base", vale per default quello di regione piemonte
    let acWeb: string = autoritaCompetentiRes?.regionePiemonte?.valore ?? '';

    // Ricostruisco il formio, aggiungendo le informazioni specifiche
    let formioDichiarazioni: IFormIoOrientamentoDichiarazioni = {
      ...formioStructure,
    };
    // Aggiungo le informazioni con gli imports specifici per la gestione delle dichiarazioni
    formioDichiarazioni.imports = {
      acWeb: acWeb,
      codAutCompetente: codAutCompetente,
      codTipoAdempimento: codTipoAdempimento,
    };

    // Verifico se sono stati scaricate informazioni anche per un ac non regione piemonte
    if (autoritaCompetentiRes.altraAutoritaCompetente) {
      // Ci sono i dati di un'altra AC, l'aggiungo all'oggetto degli imports per le dichiarazioni
      formioDichiarazioni.imports.autoritaCompetenteSpecifica = {
        // Recupero il link dall'oggetto specifico per altra autorità competente
        acWeb: autoritaCompetentiRes?.altraAutoritaCompetente?.valore,
        // Il codice autorità competente fa parte dei params
        codAutCompetente: codiceAutoritaCompetente,
        // Questa proprietà dovrebbe essere la stessa, se VIA rimane VIA
        codTipoAdempimento: codTipoAdempimento,
        // Imposto l'oggetto di configurazione adempimento come autorità competente
        autoritaCompetente: autoritaCompetente,
      };
    }

    // Verifico se il codice adempimento è quello specifico di VIA
    if (codTipoAdempimento === ScrivaCodTipiAdempimenti.VIA) {
      // Vado a gestire le informazioni per le dichiarazioni di VIA
      this.gestisciDichiarazioniVIA(
        formioDichiarazioni,
        codiceAutoritaCompetente
      );
      // #
    }

    // Riassegno la struttura per la gestione
    return formioDichiarazioni;
    // #
  }

  // #endregion "GESTIONE DICHIARAZIONI PER COMPETENZA TERRITORIO"

  /**
   * ##############################
   * GESTIONE DICHIARAZIONI AC: VIA
   * ##############################
   */

  // #region "FUNZIONI GESTIONE DICHIARAZIONI AC: VIA"

  /**
   * Funzione che forza la sovrascrittura delle dichiarazioni per il FormIo di VIA.
   * Questa è una misura estrema e funziona solo con la struttura attualmente presente su DB, con data riferimento: 10/12/2024, per il quadro orientamento di VIA.
   * Le informazioni sono modificate per riferimento.
   * @param formioVia IFormIoOrientamentoVIA con la struttura del FormIo per la gestione delle informazioni per VIA.
   * @param codiceAutoritaCompetente string con il codice dell'autorità competente gestita.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   * @notes Questa è una forzatura estrema che è stata necessaria poiché la struttura del FormIo non permette la gestione facile per avere due descrizioni/label diverse.
   *        Il problema parte dal FormIo per il quadro che ha un'impostazione forzata di suo. Non si possono mettere condizioni come su Angular e quindi non si riesce
   *        a separare le logiche per il tipo di autorità competente. Per questo motivo faccio questo script il più flessibile possibile per sistemare sta situazione.
   *        E' già stata segnalata la problematica, per il momento questo è l'approccio più veloce possibile, poiché l'alternativa sarebbe di rivedere per intero FormIo
   *        e tutto il resto del sistema con cui è stato messo in piedi questa parte.
   */
  private gestisciDichiarazioniVIA(
    formioVia: IFormIoOrientamentoDichiarazioni,
    codiceAutoritaCompetente: string
  ) {
    // Verifico l'input
    if (!formioVia) {
      // Manca la configurazione
      return;
    }

    // Richiamo la gestione del testo all'interno del formio come label
    this.gestisciTestoDichiarazioniVIA(formioVia, codiceAutoritaCompetente);
    // Richiamo la gestione della descrizione all'interno del formio come dato effettivo
    this.gestisciDescrizioneDichiarazioniVIA(
      formioVia,
      codiceAutoritaCompetente
    );
  }

  /**
   * Funzione che forza la sovrascrittura delle dichiarazioni per il FormIo di VIA.
   * Questa è una misura estrema e funziona solo con la struttura attualmente presente su DB, con data riferimento: 10/12/2024, per il quadro orientamento di VIA.
   * Le informazioni sono modificate per riferimento.
   * @param formioVia IFormIoOrientamentoVIA con la struttura del FormIo per la gestione delle informazioni per VIA.
   * @param codiceAutoritaCompetente string con il codice dell'autorità competente gestita.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   */
  private gestisciTestoDichiarazioniVIA(
    formioVia: IFormIoOrientamentoDichiarazioni,
    codiceAutoritaCompetente: string
  ) {
    // Verifico l'input
    if (!formioVia) {
      // Manca la configurazione
      return;
    }

    // Metto dentro tutto un try catch, non ci provo nemmeno a gestire tutte le casistiche. Se va, va, altrimenti bisogna debuggare e capire
    try {
      // Recupero le informazioni per la gestione dei dati
      let codAutCompetente = codiceAutoritaCompetente;
      // Recupero dalle costanti la stringa con la descrizione sulla base dell'autorità competente
      let dichiarazioniPerAC: string;
      if (codAutCompetente === CodiciAutoritaCompetenti.regionePiemonte) {
        // Recupero le dichiarazioni per regione piemonte
        dichiarazioniPerAC =
          this.DVIA_C.linkDichiarazioniVIARegionePiemonteFormIo();
        // #
      } else {
        // Recupero le dichiarazioni per altra autorità, non piemonte
        dichiarazioniPerAC =
          this.DVIA_C.linkDichiarazioniVIAACSpecificaFormIo();
        // #
      }

      // Recupero i componenti del formio per il primo livello di struttura
      let components1stLevel: FormioFormBuilderTypes[];
      components1stLevel = formioVia.components;
      // Da questo array cerco il componente che gestisce i dati per la parte delle dichiarazioni (API che c'è sulla struttura formio)
      const keyDichiarazioni: string = 'dich-orientamento';
      const iCompDich: number = components1stLevel.findIndex(
        (c: FormioFormBuilderTypes) => {
          // Effettuo un match con la proprietà key
          return c.key === keyDichiarazioni;
          // #
        }
      );

      // Estraggo il componente del container per le dichiarazioni
      let dichContainer: IFormioFormBuilderContainer;
      dichContainer = components1stLevel[iCompDich];
      // Dal container estraggo i componenti di cui è composto
      let componentsDichContainer: FormioFormBuilderTypes[];
      componentsDichContainer = dichContainer.components;
      // Da questo array cerco il componente che definisce il testo delle dichiarazioni (API che c'è sulla struttura formio)
      const keyTestoDich: string = 'dichiarazioni[0].label';
      const iCompTestoDich: number = componentsDichContainer.findIndex(
        (c: FormioFormBuilderTypes) => {
          // Effettuo un match con la proprietà key
          return c.key === keyTestoDich;
          // #
        }
      );

      // Estraggo il componente che definisce l'elemento html con il testo delle dichiarazioni
      let testoDichComponent: IFormioFormBuilderHTMLElement;
      testoDichComponent = componentsDichContainer[iCompTestoDich];
      // Sostituisco il testo presente dentro la proprietà, passando la descrizione specifica per autorità competente
      testoDichComponent.content = dichiarazioniPerAC;
      // #
    } catch (e) {
      // Segnalo con un warning per almeno capire la situazione
      const t = `[orientamento-istanza.components.ts] - Exception on: gestisciTestoDichiarazioniVIA`;
      const b = { exception: e };
      this._logger.warning(t, b);
    }
  }

  /**
   * Funzione che forza la sovrascrittura delle dichiarazioni per il FormIo di VIA.
   * Questa è una misura estrema e funziona solo con la struttura attualmente presente su DB, con data riferimento: 10/12/2024, per il quadro orientamento di VIA.
   * Le informazioni sono modificate per riferimento.
   * @param formioVia IFormIoOrientamentoVIA con la struttura del FormIo per la gestione delle informazioni per VIA.
   * @param codiceAutoritaCompetente string con il codice dell'autorità competente gestita.
   * @author Ismaele Bottelli
   * @date 16/12/2024
   * @jira SCRIVA-1568
   */
  private gestisciDescrizioneDichiarazioniVIA(
    formioVia: IFormIoOrientamentoDichiarazioni,
    codiceAutoritaCompetente: string
  ) {
    // Verifico l'input
    if (!formioVia) {
      // Manca la configurazione
      return;
    }

    // Metto dentro tutto un try catch, non ci provo nemmeno a gestire tutte le casistiche. Se va, va, altrimenti bisogna debuggare e capire
    try {
      // Recupero le informazioni per la gestione dei dati
      let codAutCompetente = codiceAutoritaCompetente;
      // Recupero dalle costanti la stringa con la descrizione sulla base dell'autorità competente
      let descrizioneDichPerAC: string;
      if (codAutCompetente === CodiciAutoritaCompetenti.regionePiemonte) {
        // Estraggo il link per l'ac piemonte
        const linkRP: string = formioVia.imports.acWeb;
        // Recupero le dichiarazioni per regione piemonte
        descrizioneDichPerAC =
          this.DVIA_C.dichiarazioniVIARegionePiemonte(linkRP);
        // #
      } else {
        // Recupero le informazioni per l'autorità competente specifica
        const acSpecifica: IFormIoOrientamentoDichiarazioniImports =
          formioVia.imports?.autoritaCompetenteSpecifica;
        // Estraggo il link per l'ac specifica non regione piemonte
        const linkAC: string = acSpecifica.acWeb;
        // Estraggo il link per l'ac piemonte
        const linkRP: string = formioVia.imports.acWeb;
        // Per la descrizione della dichiarazione devo andare a sostituire all'interno il nome dell'autorità competente
        let descAC: string =
          acSpecifica?.autoritaCompetente?.des_competenza_territorio;
        // Recupero le dichiarazioni per altra autorità, non piemonte
        descrizioneDichPerAC = this.DVIA_C.dichiarazioniVIAACSpecifica(
          linkRP,
          linkAC,
          descAC
        );
        // #
      }

      // Recupero i componenti del formio per il primo livello di struttura
      let components1stLevel: FormioFormBuilderTypes[];
      components1stLevel = formioVia.components;
      // Da questo array cerco il componente che gestisce la descrizione per la dichiarazione
      const keyDescDich: string = 'dichiarazioni[0].des_dichiarazione';
      const iCompDescDich: number = components1stLevel.findIndex(
        (c: FormioFormBuilderTypes) => {
          // Effettuo un match con la proprietà key
          return c.key === keyDescDich;
          // #
        }
      );

      // Recupero il componente che gestisce la descrizione della dichiarazione
      let desDichComponent: IFormioFormBuilderHidden;
      desDichComponent = components1stLevel[iCompDescDich];
      // Sostituisco il valore di default per il componente con la descrizione corretta della dichiarazione
      desDichComponent.defaultValue = descrizioneDichPerAC;
      // #
    } catch (e) {
      // Segnalo con un warning per almeno capire la situazione
      const t = `[orientamento-istanza.components.ts] - Exception on: gestisciDescrizioneDichiarazioniVIA`;
      const b = { exception: e };
      this._logger.warning(t, b);
    }
  }

  // #endregion "FUNZIONI GESTIONE DICHIARAZIONI AC: VIA"
}
