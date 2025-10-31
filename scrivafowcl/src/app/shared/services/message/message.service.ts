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
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { ConfirmModalComponent } from '../../components/confirm-modal/confirm-modal.component';
import { IServiziErrorConfig } from '../../interfaces/scriva-utilities.interfaces';
import { CodTipoMessEnum, MessaggioUtente, ModalConfig } from '../../models';
import { AppConfigService } from '../app-config.service';
import { ScrivaAlertMessagesService } from '../scriva-alert/scriva-alert-messages.service';
import { IMsgPlacholder } from './utilities/message.interfaces';

@Injectable({ providedIn: 'root' })
export class MessageService {
  /** string costante che definisce il placeholder per i messaggi non trovati. */
  private MSG_PLACEHOLDER = '[SCRIVA_ERROR] Message code not found.';

  private beUrl = '';

  // SCRIVA-1041
  // utiili per il clear dei timeout del fade
  private fadetimeouts: {
    id: string;
    timeoutID1: any; // number timeout
    timeoutID2: any; // number timeout
  }[] = [];

  messaggi: MessaggioUtente[];

  constructor(
    private http: HttpClient,
    private config: AppConfigService,
    private spinner: NgxSpinnerService,
    private modalService: NgbModal,
    private _scrivaAlertMsg: ScrivaAlertMessagesService
  ) {
    this.beUrl = this.config.getBEUrl();
    this.spinner.show();
    this.getMessaggi().subscribe((messaggi: MessaggioUtente[]) => {
      this.messaggi = messaggi;
      this.spinner.hide();

      // 2023/02/23 => E' stato migrato un servizio dall'applicazione RISCA a SCRIVA, ma non ho il tempo di fare refactor ad hoc, quindi inizializzo così il servizio.
      // Definisco le informazioni anche per il servizio di supporoto
      this._scrivaAlertMsg.initMessageService(messaggi);
    });
  }

  getMessaggi(): Observable<MessaggioUtente[]> {
    return this.http.get<MessaggioUtente[]>(`${this.beUrl}/messaggi`);
  }

  /**
   * Funzione di comodo che recupera dai messaggi scaricati dal servere uno specifico messaggio dato il codice messaggio.
   * @param codMess string che definisce il codice del messaggio da recuperare.
   * @returns Message che definisce la configurazione del messaggio recuperato. Altrimenti undefined.
   */
  getMessaggio(codMess: string): MessaggioUtente {
    // Cerco nella lista del servizio il messaggio
    return this.messaggi.find((m: MessaggioUtente) => {
      // Effettuo una comparazione per codice
      return m.cod_messaggio === codMess;
    });
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
   * @param swapPh IMsgPlacholder che definisce la configurazione per la sostituzione dei placholder all'interno del messaggio.
   * @returns Message che definisce la configurazione del messaggio recuperato. Altrimenti undefined.
   */
  getMessaggioPlaceholders(
    codMess: string,
    swapPh: IMsgPlacholder | IMsgPlacholder[]
  ): MessaggioUtente {
    // Cerco nella lista del servizio il messaggio
    let messaggio = { ...this.getMessaggio(codMess) };
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
    swapPh: IMsgPlacholder | IMsgPlacholder[]
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

  /**
   * Funzione di supporto che permette la generazione di un oggetto per la gestione della visualizzazione informativa.
   * @param target string con le informazioni per la generazione delle configurazioni.
   * @param error ScrivaServerError con le informazioni per la generazione delle configurazioni.
   * @param defaultCode ScrivaCodesMesseges con le informazioni per la generazione delle configurazioni.
   * @param autoFade boolean con le informazioni per la generazione delle configurazioni.
   * @returns IServiziErrorConfig con l'oggetto generato.
   */
  createMessageConfig(
    target: string,
    error?: ScrivaServerError,
    defaultCode?: ScrivaCodesMesseges,
    autoFade?: boolean
  ): IServiziErrorConfig {
    // Verifico se esiste l'errore dal servizio
    const existErr: boolean = error?.error?.code !== undefined;

    // Caso specifico con gestione richiesta mediante informazione specifica
    const message: IServiziErrorConfig = {
      target,
      e: error,
      defaultCode: defaultCode ?? ScrivaCodesMesseges.E100,
      autoFade: autoFade ?? !existErr,
    };

    // Ritorno l'oggetto generato
    return message;
  }

  /**
   * Funzione di comodo che gestisce i possibili errori generati dal server.
   * @param configs IServiciErrorConfig che definisce la configurazione per la visualizzazione degli errori.
   */
  showMessageConfigs(configs: IServiziErrorConfig) {
    // Verifico l'input
    if (!configs) {
      // Nessuna configurazione, blocco
      return;
    }

    // Estraggo le configurazioni dall'input
    const { e, defaultCode, target, autoFade } = configs;

    // Estraggo il possibile codice d'errore dall'oggetto ritornato dal server
    let errCode = e?.error?.code;
    // Verifico se esiste il codice, altrimenti definisco un default
    errCode = errCode ?? defaultCode;
    // Visualizzo il messaggio d'errore
    this.showMessage(errCode, target, autoFade ?? false);
  }

  /**
   * Metodo che mostra il messaggio a schermo
   * @param codMess string codice messaggio presente in DB
   * @param elementId string id elemento html al quale si appende il messaggio
   * @param autoFade boolean per autofade
   * @param swapPh Replace da effettuare nel corpo del messaggio
   * @param fullMessage String da utilizzare al posto della descrizione del messaggio
   * @param clearElementID boolean di default a false che chiede di svuotare il contenuto del messaggeID
   * @returns HTMLDivElement
   */
  showMessage(
    codMess: string,
    elementId: string,
    autoFade: boolean,
    swapPh?: IMsgPlacholder | IMsgPlacholder[],
    fullMessage?: string,
    clearElementID: boolean = false,
    codTipoMessaggio: string = null
  ): HTMLDivElement {
    // Tento di recuperare il messaggio per codice messaggio
    let message = { ...this.getMessaggio(codMess) };
    // Verifico se ho estratto le informazioni
    if (Object.keys(message).length == 0) {
      // caso che arriva da showMessageStatic
      if (codTipoMessaggio) {
        message = {
          id_messaggio: 0,
          tipo_messaggio: {
            cod_tipo_messaggio: codTipoMessaggio as CodTipoMessEnum,
            id_tipo_messaggio: 0,
            des_tipo_messaggio: '',
          },
          cod_messaggio: codMess,
          des_testo_messaggio: '',
          des_titolo_messaggio: '',
        };
      } else {
        // Il messaggio non esiste per il codice in input, ritorno un default
        message = { ...this.getMessaggio(ScrivaCodesMesseges.E100) };
      }
      // #
    }

    if (clearElementID) {
      this.clearAnchorMessages(elementId);
      return;
    }

    if (fullMessage) {
      message.des_testo_messaggio = fullMessage;
    }

    if (!message?.des_testo_messaggio) {
      return null;
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
      elementId = 'main';
    }
    const elementIdMessage = elementId + '_' + codMess;

    let type;
    let icon;
    let close: boolean = true;
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
        close = false;
        type = 'error';
        break;
      case 'A':
        type = 'warning';
        icon = 'fa-exclamation-circle';
        break;
      default:
        break;
    }

    // SCRIVA-1041
    if (document.getElementById(elementIdMessage)) {
      // se ho gia' il messaggio per il codmessage uso lo sfarfallio
      this._blink(
        elementIdMessage,
        document.getElementById(elementIdMessage),
        autoFade
      );
      return;
    }

    const tag: HTMLDivElement = document.createElement('div');
    tag.setAttribute('role', 'alert');
    tag.style.marginBottom = '10px';
    tag.innerHTML = `
    ${icon ? `<i class="icon icon-big fa ${icon}" aria-hidden="true"></i>` : ``}
    ${close ? `<i class="close fa fa-times" aria-label="chiudi"></i>` : ``}
    <span>
      ${
        message.des_titolo_messaggio
          ? `<p><strong>${message.des_titolo_messaggio}</strong></p>
              <p>${message.des_testo_messaggio}</p>`
          : `<p class="text-only">${message.des_testo_messaggio}</p>`
      }
    </span>`;
    tag.id = elementIdMessage;
    tag.className = 'alert-box ' + type;
    tag.onclick = function () {
      tag.parentNode.removeChild(tag);
    };

    comp.insertAdjacentElement('afterend', tag);
    tag.scrollIntoView({
      behavior: 'smooth',
      block: 'nearest',
      inline: 'nearest',
    });

    this.spinner.hide();
    if (autoFade) {
      this._autoFade(elementIdMessage, tag);
    }
    return tag;
  }

  /**
   * Pulisco gli alert fratelli di elementId
   * @param elementId string id Html
   */
  public clearAnchorMessages(elementId: string) {
    document
      .querySelectorAll('#' + elementId + '  ~.alert-box')
      .forEach((tag: HTMLElement) => {
        this._autoFade(tag.getAttribute('id'), tag, true);
      });
  }

  /**
   * Sfaefallio per il messaggio
   * @param elementIdMessage string id
   * @param tag HTMLElement
   * @param autoFade boolean
   */
  private _blink(
    elementIdMessage: string,
    tag: HTMLElement,
    autoFade: boolean = false
  ) {
    // SCRIVA-1041
    const fTimeouts = this.fadetimeouts.find((i) => i.id == elementIdMessage);
    if (fTimeouts) {
      clearTimeout(fTimeouts.timeoutID1);
      clearTimeout(fTimeouts.timeoutID2);
      this.fadetimeouts.splice(
        this.fadetimeouts.findIndex((i) => i.id === elementIdMessage),
        1
      );
    }
    tag.classList.add('refresh_message');
    setTimeout(() => {
      tag.classList.remove('refresh_message');
      if (autoFade) {
        this._autoFade(elementIdMessage, tag);
      }
    }, 2000);
  }

  /**
   * Fade per il messaggio
   * @param elementIdMessage  string id
   * @param tag HTMLElement
   * @param shortFade boolean
   */
  private _autoFade(
    elementIdMessage: string,
    tag: HTMLElement,
    shortFade: boolean = false
  ) {
    const t1Milliseconds = shortFade ? 2500 : 10000;
    const t2Milliseconds = shortFade ? 3750 : 15000;
    const timeoutID1 = setTimeout(() => {
      tag.style.opacity = '0';
    }, t1Milliseconds); // immediate change of opacity will skip the transition effect
    const timeoutID2 = setTimeout(() => {
      if (tag.parentNode) {
        tag.parentNode.removeChild(tag);
      }
      this.fadetimeouts.splice(
        this.fadetimeouts.findIndex((i) => i.id == elementIdMessage),
        1
      );
    }, t2Milliseconds);
    this.fadetimeouts.push({
      id: elementIdMessage,
      timeoutID1,
      timeoutID2,
    });
  }

  /**
   * Mostra un messaggio per un Blob
   * @param elementId id dell'elemento
   * @param autoFade  autoFade
   * @param blobResponse Blob risposta di errore
   * @param cod_tipo_messaggio codice tipo messaggio
   */
  showMessageBlob(
    elementId: string,
    autoFade: boolean,
    blobResponse: Blob,
    cod_tipo_messaggio: string = 'E'
  ): void {
    const blob = new Blob([blobResponse]);
    const reader = new FileReader();
    reader.addEventListener('loadend', (e) => {
      const r: string = e.target.result as string;
      if (JSON.parse(r).title) {
        this.showMessageStatic(
          elementId,
          autoFade,
          JSON.parse(r).title,
          cod_tipo_messaggio
        );
      }
    });
    reader.readAsText(blob);
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
    cod_tipo_messaggio: string = 'E',
    codMess: string = null
  ): HTMLDivElement {
    // SCRIVA-1041
    if (codMess) {
      this.showMessage(
        codMess,
        elementId,
        autoFade,
        null,
        fullMessage,
        false,
        cod_tipo_messaggio
      );
      return;
    }
    let message = {
      des_testo_messaggio: fullMessage,
      tipo_messaggio: { cod_tipo_messaggio: cod_tipo_messaggio },
    };
    // SCRIVA-1041
    // TODO remove dopo che per tutto applicativo saranno tutti con il codMess
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
      inline: 'nearest',
    });

    this.spinner.hide();

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

  showConfirmation(config: ModalConfig, swapPh = null) {
    let content;

    if (config.content) {
      content = config.content;
    } else {
      const message = this.messaggi.find(
        (msg) => msg.cod_messaggio === config.codMess
      );

      if (!message) {
        console.log(
          '# ERRORE! Nessun messaggio è stato trovato con il seguente codice: ' +
            config.codMess
        );
        return;
      }

      content = message.des_testo_messaggio;
      if (swapPh) {
        content = content.replace(swapPh.ph, swapPh.swap);
      }

      if (config?.testo) {
        content = config.testo;
      }
    }

    const modalRef = this.modalService.open(ConfirmModalComponent, {
      centered: true,
      scrollable: true,
      size: 'lg',
    });
    modalRef.componentInstance.data = {
      title: config.title,
      content,
      buttons: config.buttons,
    };
    modalRef.result
      .then((index) => {
        if (index > -1) {
          config.buttons[index].callback();
        }
      })
      .catch((err) => {});
  }
}
