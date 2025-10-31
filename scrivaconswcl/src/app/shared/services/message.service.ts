/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
//import { NgxSpinnerService } from 'ngx-spinner'; //TODO-->funzionerà solo dopo un insta!!!!
import { Observable } from 'rxjs';
import { ApiClient } from '@app/core/api-client/public-api';
import { MessaggioUtente } from '../notification/model/notification.model';
import { ConswebCodesMesseges } from '@app/core/enums/codes-messagges.enums';
import { ConswebAlertMessagesService } from './alert.messagess.service';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  /** string costante che definisce il placeholder per i messaggi non trovati. */
  private MSG_PLACEHOLDER = '[CONSWEB_ERROR] Message code not found.';

  messaggi: MessaggioUtente[];

  constructor(
    private http: HttpClient,
    //private spinner: NgxSpinnerService,
    private _conswebAlertMsg: ConswebAlertMessagesService,
    private apiClient: ApiClient
  ) {
    //this.spinner.show();
    this.getMessaggi().subscribe((messaggi: MessaggioUtente[]) => {
      this.messaggi = messaggi;
      //this.spinner.hide();

      // 2024/02/05 => E' stato migrato un servizio dall'applicazione SCRIVA, ma non ho il tempo di fare refactor ad hoc, quindi inizializzo così il servizio.
      // Definisco le informazioni anche per il servizio di supporto
      this._conswebAlertMsg.initMessageService(messaggi);
    });
  }

  getMessaggi(): Observable<MessaggioUtente[]> {
    return this.apiClient.request<MessaggioUtente[]>('messaggi');
  }

  /**
   * Funzione di comodo che recupera dai messaggi scaricati dal servere uno specifico messaggio dato il codice messaggio.
   * @param codMess string che definisce il codice del messaggio da recuperare.
   * @returns Message che definisce la configurazione del messaggio recuperato. Altrimenti undefined.
   */
  getMessaggio(codMess: string): MessaggioUtente {
    // Cerco nella lista del servizio il messaggio
    return this.messaggi?.find((m: MessaggioUtente) => {
      // Effettuo una comparazione per codice
      return m.cod_messaggio === codMess;
    });
  }

  /**
   * Funzione che restituisce un messaggio dato il suo codice.
   * @param code string codice del messaggio.
   * @param fallbackPlaceholder string opzionale che definisce il messaggio da ritornare in caso in cui non venga trovato per il codice.
   * @returns string con il messaggio.
   */
  getDesMessaggio(code: string, fallbackPlaceholder?: string): string {
    // Tento di recuperare il messaggio
    const messaggio = this.getMessaggio(code);

    // Verifico che sia stato trovato un messaggio
    if (!messaggio) {
      // Ritorno un placeholder
      return fallbackPlaceholder ?? 'Descrizione non trovata';
    }

    // Ritorno il messaggio
    return messaggio.des_testo_messaggio;
  }

  /**
   * Funzione di comodo che recupera dai messaggi scaricati dal servere uno specifico messaggio dato il codice messaggio.
   * @param codMess string che definisce il codice del messaggio da recuperare.
   * @returns string con il messaggio descrittivo dell'errore. Altrimenti stringa sostitutiva.
   */
  getDescrizioneMessaggio(codMess: string): string {
    // Cerco nella lista del servizio il messaggio
    let messaggio = this.getMessaggio(codMess);
    // Ritorno la descrizione del messaggio o una stringa di replace
    return messaggio?.des_testo_messaggio ?? this.MSG_PLACEHOLDER;
  }

  /**
   * Funzione di comodo che recupera dai messaggi scaricati dal servere uno specifico messaggio dato il codice messaggio.
   * @param codMess string che definisce il codice del messaggio da recuperare.
   * @param swapPh { ph: string; swap: string } che definisce la configurazione per la sostituzione dei placholder all'interno del messaggio.
   * @returns Message che definisce la configurazione del messaggio recuperato. Altrimenti undefined.
   */
  getMessaggioPlaceholders(
    codMess: string,
    swapPh: { ph: string; swap: string } | { ph: string; swap: string }[]
  ): MessaggioUtente {
    // Cerco nella lista del servizio il messaggio
    let messaggio = this.getMessaggio(codMess);
    // Sostituisco i placeholder
    if (swapPh) {
      messaggio.des_testo_messaggio = this._getSwapReplacedTestoMessaggio(
        messaggio.des_testo_messaggio,
        swapPh
      );
    }

    // Restituisco l'oggetto del messaggio
    return messaggio;
  }

  public getConditionalTestoMessaggio(
    des_testo_messaggio: string,
    key: string,
    show: boolean = false
  ): string {
    if (key) {
      /**
       * Es di des_testo_messaggio:
       * Sulla base dell’Autorità competente selezionata, della localizzazione del progetto e delle categorie progettuali indicate, l'istanza sarà inviata a: 
<br>
<b>[{PH_ACP_DES_COMPETENZA_TERRITORIO}]</b>
<PH_ACS_DES_COMPETENZA_TERRITORIO>e per conoscenza a <br></PH_ACS_DES_COMPETENZA_TERRITORIO>
[{PH_ACS_DES_COMPETENZA_TERRITORIO}]
       * 
       */
      let re = new RegExp(`<${key}>(.*?)<\\/${key}>`);
      des_testo_messaggio = des_testo_messaggio.replace(re, show ? '$1' : '');
    }
    return des_testo_messaggio;
  }

  _getSwapReplacedTestoMessaggio(
    des_testo_messaggio: string,
    swapPh: { ph: string; swap: string } | { ph: string; swap: string }[]
  ): string {
    if (swapPh) {
      if (Array.isArray(swapPh)) {
        swapPh.forEach((s) => {
          des_testo_messaggio = des_testo_messaggio.replace(s.ph, s.swap);
        });
      } else {
        des_testo_messaggio = des_testo_messaggio.replace(
          swapPh.ph,
          swapPh.swap
        );
      }
    }
    return des_testo_messaggio;
  }

  showMessage(
    codMess: string,
    elementId: string,
    autoFade: boolean,
    swapPh?: { ph: string; swap: string } | { ph: string; swap: string }[],
    fullMessage?: string
  ): HTMLDivElement {
    // Tento di recuperare il messaggio per codice messaggio
    let message = { ...this.getMessaggio(codMess) };
    // Verifico se ho estratto le informazioni
    if (!message) {
      // Il messaggio non esiste per il codice in input, ritorno un default
      message = this.getMessaggio(ConswebCodesMesseges.E100);
      // #
    }

    if (!message?.des_testo_messaggio) {
      return null;
    }

    if (fullMessage) {
      message.des_testo_messaggio = fullMessage;
    }

    if (swapPh) {
      message.des_testo_messaggio = this._getSwapReplacedTestoMessaggio(
        message.des_testo_messaggio,
        swapPh
      );
    }

    let comp = document.getElementById(elementId);
    if (!comp) {
      console.log(`#MS# Message anchor "${elementId}" not found in DOM. #MS#`);
      comp = document.getElementById('main');
    }

    let type;
    let icon;
    switch (message.tipo_messaggio.cod_tipo_messaggio) {
      case 'P':
        type = 'success';
        icon = 'fa-check-circle-o';
        break;
      case 'I':
        type = 'info';
        icon = 'fa-info-circle';
        break;
      case 'E':
        type = 'error';
        icon = 'fa-times-circle-o';
        break;
      case 'A':
        type = 'warning';
        icon = 'fa-exclamation-circle';
        break;
      default:
        break;
    }

    const tag: HTMLDivElement = document.createElement('div');
    tag.setAttribute('role', 'alert');
    tag.style.marginBottom = '10px';
    tag.innerHTML = `<i class="icon icon-big fa ${icon}" aria-hidden="true"></i>
    <span>
      ${
        message.des_titolo_messaggio
          ? `<p><strong>${message.des_titolo_messaggio}</strong></p>
              <p>${message.des_testo_messaggio}</p>`
          : `<p class="text-only">${message.des_testo_messaggio}</p>`
      }
    </span>`;
    tag.className = 'alert-box ' + type;
    tag.onclick = function () {
      tag.parentNode.removeChild(tag);
    };

    comp.insertAdjacentElement('afterend', tag);
    tag.scrollIntoView({
      behavior: 'smooth',
      block: 'nearest',
      inline: 'nearest'
    });

    //this.spinner.hide();

    if (autoFade) {
      setTimeout(() => {
        tag.style.opacity = '0';
      }, 10000); // immediate change of opacity will skip the transition effect
      setTimeout(() => {
        if (tag.parentNode) {
          tag.parentNode.removeChild(tag);
        }
      }, 15000);
    }

    return tag;
  }

  /**
   * Mostra messaggi statici
   * @param elementId id dell'elemento
   * @param autoFade  autoFade
   * @param fullMessage  fullMessage
   * @param cod_tipo_messaggio codice tipo messaggio
   * @returns
   */
  showMessageStatic(
    elementId: string,
    autoFade: boolean,
    fullMessage: string,
    cod_tipo_messaggio: string = 'E'
  ): HTMLDivElement {
    // Tento di recuperare il messaggio per codice messaggio
    let message = {
      des_testo_messaggio: fullMessage,
      tipo_messaggio: { cod_tipo_messaggio: cod_tipo_messaggio }
    };
    let comp = document.getElementById(elementId);
    if (!comp) {
      console.log(`#MS# Message anchor "${elementId}" not found in DOM. #MS#`);
      comp = document.getElementById('main');
    }

    let type;
    let icon;
    switch (message.tipo_messaggio.cod_tipo_messaggio) {
      case 'P':
        type = 'success';
        icon = 'fa-check-circle-o';
        break;
      case 'I':
        type = 'info';
        icon = 'fa-info-circle';
        break;
      case 'E':
        type = 'error';
        icon = 'fa-times-circle-o';
        break;
      case 'A':
        type = 'warning';
        icon = 'fa-exclamation-circle';
        break;
      default:
        break;
    }

    const tag: HTMLDivElement = document.createElement('div');
    tag.setAttribute('role', 'alert');
    tag.style.marginBottom = '10px';
    tag.innerHTML = `<i class="icon icon-big fa ${icon}" aria-hidden="true"></i>
    <span>
      ${`<p class="text-only">${message.des_testo_messaggio}</p>`}
    </span>`;
    tag.className = 'alert-box ' + type;
    tag.onclick = function () {
      tag.parentNode.removeChild(tag);
    };

    comp.insertAdjacentElement('afterend', tag);
    tag.scrollIntoView({
      behavior: 'smooth',
      block: 'nearest',
      inline: 'nearest'
    });

    //this.spinner.hide();

    if (autoFade) {
      setTimeout(() => {
        tag.style.opacity = '0';
      }, 10000); // immediate change of opacity will skip the transition effect
      setTimeout(() => {
        if (tag.parentNode) {
          tag.parentNode.removeChild(tag);
        }
      }, 15000);
    }
    return tag;
  }

  hideMessage(tag: HTMLDivElement) {
    if (tag) {
      tag.style.opacity = '0';
      if (tag.parentNode) {
        tag.parentNode.removeChild(tag);
      }
    }
  }

}
