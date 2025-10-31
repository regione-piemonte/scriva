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
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import {
  ConfigurazioniScrivaService,
  MessageService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { ScrivaUtilitiesService } from '../../../shared/services/scriva-utilities/scriva-utilities.service';
import { INotificheSearchData } from '../components/notifiche-search/utilities/notifiche-search.interfaces';
import { NotificaApplicativa } from '../models/notifica-applicativa';
import { ISearchNotificheRequest } from '../models/search-notifiche-request';
import { NotificheConfigurazioniService } from './notifiche-configurazioni.service';
import { NotificheHttpService } from './notifiche-http/notifiche-http.service';
import { NotifichePagingCount } from './notifiche-http/utilities/notifiche-http.classes';
import { NotificaCardTypeView } from '../components/notifica-card/notifiche-card/notifica-card.enums';

/**
 * Interfaccia che rappresenta la chiamata in forkJoin per lo scarico delle configurazioni delle notifiche.
 */
interface IConfigNotificheReq {
  configsTime: Observable<any>;
  configsNumNotifiche: Observable<any>;
}

/**
 * Interfaccia che rappresenta la risposta nel forkJoin per lo scarico delle configurazioni delle notifiche.
 */
interface IConfigNotificheRes {
  configsTime: any;
  configsNumNotifiche: any;
}

@Injectable({ providedIn: 'root' })
export class NotificheService {
  private LIST_URL = 'notifiche';
  private PROFILE_NOTIFICHE_URL = ['profilo'];
  private NOTICHE_SPINNER_NAME = 'notificheSpinner';

  private _from:NotificaCardTypeView;

  constructor(
    private _message: MessageService,
    private _notificheConfig: NotificheConfigurazioniService,
    private _notificheHttp: NotificheHttpService,
    private _router: Router,
    private _scrivaConfig: ConfigurazioniScrivaService,
    private _scrivaUtilities: ScrivaUtilitiesService,
    private _spinner: NgxSpinnerService
  ) {}

  /**
   * Funzione che scarica le configurazioni per le notifiche.
   * A seguito dello scarico delle configurazioni vengono scaricati la prima volta i dati e viene agganciato il polling per le nuove notifiche.
   */
  initNotifiche() {
    // Recupero dal file di configurazione per le notifiche le chiavi per lo scarico configurazioni
    const keyTime = this._notificheConfig.CONF_SCRIVA_NOTIFY_TIME;
    const keyNum = this._notificheConfig.CONF_SCRIVA_NOTIFY_NUM_NOTIFICHE_HOME;
    // Creo un oggetto per le request di scarico configurazioni
    const req: IConfigNotificheReq = {
      configsTime: this._scrivaConfig.getConfigurazione(keyTime),
      configsNumNotifiche: this._scrivaConfig.getConfigurazione(keyNum),
    };

    // Avvio lo spinner
    this._spinner.show(this.NOTICHE_SPINNER_NAME);

    // Lancio la richista per lo scarico configurazioni
    forkJoin(req)
      .pipe(
        tap((res: IConfigNotificheRes) => {
          // Estraggo dalla response le configurazioni scaricate
          const { configsTime, configsNumNotifiche } = res || {};
          // Gestisco la configurazione: configsTime
          this.onInitNotificheTime(configsTime);
          // Gestisco la configurazione: configsNumNotifiche
          this.onInitNotificheNumerics(configsNumNotifiche);
          // #
        }),
        switchMap((res: IConfigNotificheRes) => {
          // A seguito delle configurazioni lancio il primo scarico dati per le notifiche
          return this._notificheHttp.getNotificheWithCounters()
        })
      )
      .subscribe({
        next: (res: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Lancio il primo aggiornamento tramite listener del servizio
          this._notificheHttp.updateNotificheListener(res);
          // Chiudo lo spinner
          this._spinner.hide(this.NOTICHE_SPINNER_NAME);
          // Terminato lo scarico configurazioni e recuperate i prima dati sulle notifiche, agganciamo il polling per l'aggiornamento automatico
          this._notificheHttp.initTimerGetNotifiche();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Definisco le informazioni per visualizzare l'alert box nell'app componente
          const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
          const target = 'errorBox';
          const autoFade = true;
          // Visualizzo il messaggio
          this._message.showMessage(code, target, autoFade);
        },
      });
  }

  /**
   * Funzione che gestisce il risultato dello scarico dati per le configurazioni per le notifiche, sezione: "time".
   * @param configsTime any con la configurazione scaricata dal server.
   */
  private onInitNotificheTime(configsTime: any) {
    // Definisco una variabile di comodo per i controlli su: configsTime
    const timeElems = Array.isArray(configsTime) && configsTime?.length > 0;
    // Verifico che esistano le configurazioni per la parte di "time"
    if (timeElems) {
      // Recupero i minuti ritornati dal BE
      const timeBE: number = configsTime[0].valore;
      // Converto il tempo in minuti in millisecondi
      const timeFE = timeBE * 60 * 1000;
      // Aggiorno le configurazioni locali
      this._notificheConfig.setScrivaNotifyTime(timeFE);
    }
  }

  /**
   * Funzione che gestisce il risultato dello scarico dati per le configurazioni per le notifiche, sezione: "numero notifiche".
   * @param configsNumNotifiche any con la configurazione scaricata dal server.
   */
  private onInitNotificheNumerics(configsNumNotifiche: any) {
    // Definisco una variabile di comodo per i controlli su: configsTime
    const timeElems =
      Array.isArray(configsNumNotifiche) && configsNumNotifiche?.length > 0;
    // Verifico che esistano le configurazioni per la parte di "number"
    if (timeElems) {
      // Recupero il numero di notifiche ritornati dal BE
      const numNotifiche: number = configsNumNotifiche[0].valore;
      // Aggiorno le configurazioni locali
      this._notificheConfig.setScrivaNotifyNumNotificheHome(numNotifiche);
    }
  }

  /**
   * Navigazione verso la pagina delle notifiche
   */
  navigateToNotifiche() {
    this._router.navigate([this.LIST_URL]);
  }

  /**
   * Navigazione verso la pagina delle notifica singola
   */
  navigateToNotifica(idNotificaApplicativa: number, from:NotificaCardTypeView = NotificaCardTypeView.HOME) {
    // tengo da parte da dove arrivo al dettaglio notifica
    this.from = from;
    this._router.navigate([this.LIST_URL, idNotificaApplicativa]);
  }

  /**
   * Navigazione verso il procedimento correllata alla notifica
   */
  navigateToPratica(notificaApplicativa: NotificaApplicativa) {
    /* Recupero idIstanza dalla istanza della notifica corrente */
    const idIStanza = notificaApplicativa.istanza?.id_istanza;
    /* Recupero codAdempimento da Adempimento dalla istanza della notifica corrente */
    const codAdempimento =
      notificaApplicativa.istanza?.adempimento?.cod_adempimento;
    /* Recupero codAmbito da Ambito collegato alla notifica corrente */
    const codAmbito =
      notificaApplicativa.istanza?.adempimento?.tipo_adempimento?.ambito
        ?.cod_ambito;
    this._router.navigate([`/ambito/${codAmbito}/istanza/${codAdempimento}`], {
      state: {
        idIstanza: idIStanza,
        attoreGestioneIstanza: AttoreGestioneIstanzaEnum.READ_ONLY,
      },
    });
  }

  /**
   * Navigazione verso la pagina di setings delle notifiche
   */
  navigateToNoticheSettings() {
    this._router.navigate(this.PROFILE_NOTIFICHE_URL);
  }

  /**
   * #######################################
   * FUNZIONI DI CONVERSIONE PER GLI OGGETTI
   * #######################################
   */

  /**
   * Convertitore da da INotificheSearchData a SearchNotificheRequest.
   * @param filtri INotificheSearchData da convertire.
   * @returns SearchNotificheRequest convertito.
   */
  convertINotificheSearchDataToSearchNotificheRequest(
    filtri: INotificheSearchData
  ): ISearchNotificheRequest {
    // Verifico l'input
    if (!filtri) {
      // Nessuna configurazione
      return undefined;
    }

    // Estraggo dall'oggetto di filtro le informazioni
    let { statoNotifica, procedimento, dataDa, dataA, numeroIstanza } =
      filtri || {};
    // Mappo le proprietà dell'oggetto filtro su quelle di search
    const codStatoNotifica = statoNotifica?.cod_stato_notifica;
    const data_inizio = this._scrivaUtilities.convertMomentToServerDate(dataDa);
    const data_fine = this._scrivaUtilities.convertMomentToServerDate(dataA);
    const id_adempimento = procedimento?.id_adempimento; // => qui è singolo, ma nel search è un array di id
    const num_istanza = numeroIstanza;

    // Converto l'oggetto
    const searchNReq: ISearchNotificheRequest = {
      cod_stato_notifica: codStatoNotifica,
      data_inizio: data_inizio,
      data_fine: data_fine,
      id_adempimento: id_adempimento ? [id_adempimento] : undefined,
      num_istanza: num_istanza,
    };

    // Ritorno l'oggetto generato
    return searchNReq;
  }


  /**
   * Getter di comodo per il recupero della vista da cui si arriva al dettaglio notifica
   * @returns string con la descrizione.
   */
  get from(): NotificaCardTypeView {
    return this._from;
  }

  
  /**
   * Setter di comodo per il recupero della vista da cui si arriva al dettaglio notifica
   * @returns NotificaCardTypeView con la vista
   */
  set from(v:NotificaCardTypeView) {
    // Recupero dalla notifica la descrizione adempimento
    this._from = v;
  }
}
