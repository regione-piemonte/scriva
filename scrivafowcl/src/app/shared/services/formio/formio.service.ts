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
import { FormioAlert, FormioComponent } from 'angular-formio';
import { FormioForm, FormioOptions } from 'angular-formio/formio.common';
import { cloneDeep } from 'lodash';
import { Observable, of } from 'rxjs';
import { OggettoIstanza, Opera } from '../../../features/ambito/models';
import { FormIoAlertTypes } from '../../components/formio/utilities/formio.enums';
import { IFormIoRenderOptions } from '../../interfaces/scriva-utilities.interfaces';
import { Help } from '../../models';
import { IChiaveHelpParams } from '../help/utilities/help.interfaces';
import { MessageService } from '../message/message.service';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import { FormioModalsService } from './formio-modals.service';
import { FormioI18NService } from './i18n/formio-i18n.service';
import { FormIoI18NVocabularies } from './i18n/utilities/formio-i18n.enums';
import { IFormIoI18N } from './i18n/utilities/formio-i18n.interfaces';
import { FormioInfoACService } from './orientamento/formio-info-ac.service';
import { FormioQuadriService } from './quadri/formio-quadri.service';
import {
  FormioIgnore,
  formioIgnoreList,
  FormioIgnoreType,
} from './utilities/formio-ignore';
import { KEY_FORMIO_SECTION_REQUIRED } from './utilities/formio.consts';
import {
  IFormioRenderOptionsParams,
  IFormioValidationCheck,
} from './utilities/formio.interfaces';
import { FormioVincaAppService } from './vinca/formio-vinca-app.service';
import { FormioVincaScrService } from './vinca/formio-vinca-scr.service';

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi
 */
@Injectable({ providedIn: 'root' })
export class FormioService {
  /** String costante che definisce la KEY WORD applicativa specifica per gestire in maniera automatica la rimozione dei dati helper sul template formio. */
  private KEY_FORMIO_HELPER = 'JS_HELP';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire i campi disabilitati FormIo, anche se deprecato. */
  private KEY_FORMIO_FLG_DISABLE = 'disableFlag';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire in maniera automatica la rimozione dei dati helper sul template formio. */
  private KEY_FORMIO_OPZ_DOMANDA = 'opzione_domanda';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire le logiche per le modali. */
  private KEY_FORMIO_MODAL = 'JS_MODAL';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire le logiche per i pulsanti di salvataggio dati delle sezioni. */
  private KEY_FORMIO_SAVE_SECTION = 'JS_SAVE_SECTION';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire le logiche dei dati tecnici. */
  private KEY_FORMIO_DATI_TECNICI = 'JS_DATI_';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire le logiche di rimozione dati di sola lettura. */
  private KEY_FORMIO_READONLY = 'JS_READ_ONLY';
  /** String costante che definisce la KEY WORD applicativa specifica per gestire le logiche di rimozione dati di sola lettura. */
  private KEY_FORMIO_SECTION_REQUIRED = KEY_FORMIO_SECTION_REQUIRED;

  /**
   * Costruttore.
   */
  constructor(
    private _formioVincaApp: FormioVincaAppService,
    private _formioVincaScr: FormioVincaScrService,
    private _formioI18N: FormioI18NService,
    private _formioInfoAC: FormioInfoACService,
    private _formioModals: FormioModalsService,
    private _formioQuadri: FormioQuadriService,
    private _message: MessageService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * ##########################################################
   * FUNZIONE PER LA DEFINIZIONE DELLE RENDER OPTIONS DI FORMIO
   * ##########################################################
   */

  /**
   * Funzione di setup che raccoglie tutte le configurazioni del FormIo.
   * La funzione ritorna le configurazioni globali. Definendo in input un override, le globali verranno sostituite.
   * @param overrideOptions FormioOptions con le configurazioni di override rispetto quelle globali applicative.
   * @returns FormioOptions con le configurazioni generate.
   */
  getFormIoOptions(overrideOptions?: FormioOptions): FormioOptions {
    // Definisco le configurazioni globali
    const globalOptions: FormioOptions = {
      disableAlerts: true,
    };

    // Verifico l'input
    overrideOptions = overrideOptions ?? {};

    // Vado a creare le opzioni del formio, sovrascrivendo le proprietà globali con quelle specifiche
    const formioOptions: FormioOptions = {
      ...globalOptions,
      ...overrideOptions,
    };

    // Ritorno la configurazione
    return formioOptions;
  }

  /**
   * Funzione di setup che raccoglie tutte le logiche, servizi e configurazioni da passare come riferimento al contesto FormIo.
   * @param formioParams IFormioRenderOptionsParams con le configurazioni da passare alla funzione per la generazione delle render options.
   * @param otherParams any che definisce un oggetto con tutte i possibili parametri extra da iniettare in formio.
   * @notes Internazionalizzazione: per un bug della libreria, la definizione di un vocabolario per l'internazionalizzazione va a "rompere" tutti i calendari usati come componenti formio.
   *        Per il momento l'utilizzo di questa funzionalità è integrato, ma disattivato.
   * @returns IFormIoRenderOptions con le opzioni di configurazione per i quadri formio.
   */
  getFormIoRenderOptions(
    formioParams: IFormioRenderOptionsParams,
    otherParams?: any
  ): IFormIoRenderOptions {
    // Verifico l'input
    otherParams = otherParams ?? {};

    // Definisco dei valori di default
    let formioUpdate: (data: any) => void;
    formioUpdate = this.defineFormioUpdate(formioParams);
    // # FORMIO UPDATE COMPONENT
    let formioUpdateComponent: (data: any, key: string, value: any) => any;
    formioUpdateComponent = this.defineFormioUpdateComponent(formioParams);

    // Definisco l'oggetto "SCRIVA" che verrà reso disponibile nella variabile interna "options" di FormIo
    const SCRIVA = {
      // Parametri extra, li definisco per primi così che possibili duplicati vengano sovrascritti dalle proprietà sotto
      ...otherParams,
      // Servizi di supporto
      messages: this._message,
      utilities: this._scrivaUtilities,
      formioModals: this._formioModals,
      formioQuadri: this._formioQuadri,
      // Variabili di configurazione con valori fissi
      isBackOffice: !formioParams.isFrontOffice,
      isFrontOffice: formioParams.isFrontOffice,
      // Funzioni di gestione/aggiornamento dati
      formioUpdateComponent,
      formioUpdate,
    };

    // Vado a richiamare le configurazioni per la gestione del formio
    const i18nIT = FormIoI18NVocabularies.IT;
    const internazionalizzazione: IFormIoI18N =
      this._formioI18N.getActiveVocabulary(i18nIT);

    // Aggiungo l'oggetto con le informazioni applicative a FormIo
    const renderOptions: IFormIoRenderOptions = {
      SCRIVA,
      // ...internazionalizzazione,
    };

    // Ritorno la configurazione generata
    return renderOptions;
  }

  /**
   * Funzione che permette d'aggiungere dei parametri all'oggetto delle render options di FormIo.
   * I parametri veranno aggiunti all'interno dell'oggetto dedicato: SCRIVA.
   * La modifica delle configurazioni avverrà per riferimento.
   * @param renderOptions IFormIoRenderOptions con l'oggetto dei parametri FormIo d'aggiornare.
   * @param params any che definisce un oggetto con tutte i possibili parametri extra da iniettare in formio.
   */
  addFormIoRenderOptions(renderOptions: IFormIoRenderOptions, params: any) {
    // Verifico l'input
    if (!renderOptions || !params) {
      // Mancano le configurazioni
      return;
    }

    // Vado a recuperare l'oggetto delle options di SCRIVA
    let newSCRIVA: any = renderOptions.SCRIVA ?? {};
    // Aggiorno l'oggetto delle render options aggiungendo i nuovi parametri
    newSCRIVA = { ...params, ...newSCRIVA };
    // Riassegno le informazioni all'oggetto originale
    renderOptions.SCRIVA = newSCRIVA;
  }

  /**
   * Funzione che gestisce la logica di definire per la funzionalità: formioUpdate; dall'oggetto di configurazione: IFormioRenderOptionsParams.
   * @param formioParams IFormioRenderOptionsParams con le configurazioni da passare alla funzione per la generazione delle render options.
   * @returns (data: any) => void; con la funzione definita per il ritorno.
   */
  private defineFormioUpdate(
    formioParams: IFormioRenderOptionsParams
  ): (data: any) => void {
    // # FORMIO UPDATE
    const defaultFormioUpdate = (data: any) => {};
    let formioUpdate: (data: any) => void;
    formioUpdate = formioParams?.formioUpdate ?? defaultFormioUpdate;

    // Ritorno la funzione
    return formioUpdate;
  }

  /**
   * Funzione che gestisce la logica di definire per la funzionalità: formioUpdateComponent; dall'oggetto di configurazione: IFormioRenderOptionsParams.
   * @param formioParams IFormioRenderOptionsParams con le configurazioni da passare alla funzione per la generazione delle render options.
   * @returns (data: any, key: string, value: any) => any; con la funzione definita per il ritorno.
   */
  private defineFormioUpdateComponent(
    formioParams: IFormioRenderOptionsParams
  ): (data: any, key: string, value: any) => any {
    // # FORMIO UPDATE COMPONENT
    const defaultFormioUpdateComponent: (
      data: any,
      key: string,
      value: any
    ) => any = (data: any, key: string, value: any) => {
      // Lancio la funzione locale di aggiornamento
      return this.updateFormIoComponent(data, key, value);
    };

    let formioUpdateComponent: (data: any, key: string, value: any) => any;
    formioUpdateComponent =
      formioParams?.formioUpdateComponent ?? defaultFormioUpdateComponent;

    // Ritorno la funzione
    return formioUpdateComponent;
  }

  /**
   * ###################################################
   * FUNZIONE PER IL SETUP DELLE INFORMAZIONI DEL FORMIO
   * ###################################################
   */

  /**
   * Funzionalità di supporto che gestisce il setup delle informazioni dall'oggetto di pre-configurazione del formio.
   * Il dato verrà preparato per la lettura dalla libreria.
   * @param customForm FormioForm contenente le configurazioni del formio.
   * @param formioData any contenente l'oggetto "data" che verrà passato come pre-configurazione del formio.
   * @returns any con l'oggetto formattato per la lettura da parte della libreria.
   */
  handleFormioSetupFromDB(customForm: FormioForm, formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia del dato in ingresso
    let data = cloneDeep(formioData);

    // Effettuo il setup del formio a seconda delle possibile specifiche configurazioni
    data = this.setupFormioData(customForm, data);

    // Ritorno l'oggetto generato e sanitizzato
    return data;
  }

  /**
   * ###########################################################
   * GESTIONE DELLE FUNZIONI CUSTOM PER IL SETUP DATI DEL FORMIO
   * ###########################################################
   */

  /**
   * Funzionalità di supporto che gestisce le possibili gestioni custom per il formio, a seconda del tipo di formio stesso.
   * Il formio può necessitare di specifiche configurazioni di setup, partendo dalla struttura dei componenti del formio stesso.
   * Le logiche specifiche sono definite dai servizi di supporto.
   * @param customForm FormioForm contenente le configurazioni del formio.
   * @param formioData any contenente l'oggetto "data" che verrà passato come pre-configurazione del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  setupFormioData(customForm: FormioForm, formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Richiamo il servizio per la gestione formio: VINCA APP
    formioData = this._formioVincaApp.setupFormioVincaAppData(
      customForm,
      formioData
    );
    // Richiamo il servizio per  la gestione formio: VINCA SCR
    formioData = this._formioVincaScr.setupFormioVincaScrData(
      customForm,
      formioData
    );

    // Ritorno l'oggetto
    return formioData;
  }

  /**
   * ################################################################
   * FUNZIONE PER LA SANITIZZAZIONE DELL'OGGETTO DI SUBMIT DEL FORMIO
   * ################################################################
   */

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dall'oggetto di submit del formio.
   * Il dato verrà preparato per il salvataggio su DB.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any con l'oggetto sanitizzato per il salvataggio su DB.
   */
  handleFormioSubmitForDB(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia del dato in ingresso
    let data = cloneDeep(formioData);

    // Pulisco il formio dagli helper
    data = this.formioHelperSanitizer(data);
    // Pulisco il formio dalle chiavi per la gestione delle modali
    data = this.formioModalSanitizer(data);
    // Pulisco il formio dalle chiavi di sola lettura
    data = this.formioReadonlySanitizer(data);
    // Pulisco il formio dal flag di disabilitazione
    data = this.formioDisableFlagSanitizer(data);
    // Pulisco il formio dalla proprietà opzione domanda
    data = this.formioOpzioneDomandaSanitizer(data);
    // Pulisco il formio dalla proprietà opzione domanda
    data = this.formioSalvaDatiSezioneSanitizer(data);
    // Pulisco il formio dalla proprietà opzione domanda
    data = this.formioSezioneRichiestaSanitizer(data);
    // Pulisco il formio a seconda delle possibile specifiche configurazioni
    data = this.formioTipiIstanzaSanitizer(data);
    // Pulisco il formio a seconda di un array di configurazione di dati che non devono mai essere salvati a db
    data = this.formioDataCleanerIgnore(data);

    // Ritorno l'oggetto generato e sanitizzato
    return data;
  }

  /**
   * ##########################################################
   * GESTIONE DELLE PROPRIETA' HELPER DEL FORM SUBMIT DI FORMIO
   * ##########################################################
   */

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni che cominciano per una determinata stringa per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @param keyStartWith string che definisce come deve iniziare una determinata proprietà per la sua rimozione dal formio.
   * @returns any contenente l'oggetto senza le proprietà che inizia per una determinata del formio.
   */
  formioStartWithSanitizer(formioData: any, keyStartWith: string): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia locale
    const data = formioData;
    // Definisco la keyword per la gestione del dato
    const jsKey = keyStartWith;

    // Rimuovo dall'oggetto di submit tutti gli helper definiti nella configurazione
    Object.keys(data).forEach((componentKey: string) => {
      // Verifico se il componente è un helper
      if (componentKey.startsWith(jsKey)) {
        // E' un helper, lo rimuovo dall'oggetto di submit
        delete data[componentKey];
        // #
      }
    });

    // Ritorno l'oggetto aggiornato
    return data;
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni che coincidono con una determinata stringa per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @param key string che definisce esattamente una determinata proprietà per la sua rimozione dal formio.
   * @returns any contenente l'oggetto senza le proprietà che inizia per una determinata del formio.
   */
  formioExactSanitizer(formioData: any, key: string): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia locale
    const data = formioData;

    // Rimuovo dall'oggetto di submit tutti gli helper definiti nella configurazione
    Object.keys(data).forEach((componentKey: string) => {
      // Verifico se il componente ha la chiave in input
      if (componentKey === key) {
        // E' un helper, lo rimuovo dall'oggetto di submit
        delete data[componentKey];
      }
    });

    // Ritorno l'oggetto aggiornato
    return data;
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dell'helper per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dell'helper del formio.
   */
  formioHelperSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_HELPER;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni per i flag disabled del formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dei flag disabled del formio.
   */
  formioDisableFlagSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia del dato in ingresso
    let data = cloneDeep(formioData);
    // Definisco la keyword per la gestione del dato
    const flgDisableKey = this.KEY_FORMIO_FLG_DISABLE;

    // Rimuovo dall'oggetto di submit tutti gli helper definiti nella configurazione
    Object.keys(data).forEach((componentKey: string) => {
      // Verifico se il componente è un helper
      if (componentKey === flgDisableKey) {
        // E' un helper, lo rimuovo dall'oggetto di submit
        delete data[componentKey];
      }
    });

    // Ritorno l'oggetto aggiornato
    return data;
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni per i flag opzione_domanda del formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dei flag opzione_domanda del formio.
   */
  formioOpzioneDomandaSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_OPZ_DOMANDA;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni per i flag opzione_domanda del formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dei flag opzione_domanda del formio.
   */
  formioSalvaDatiSezioneSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_SAVE_SECTION;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni per i flag opzione_domanda del formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dei flag opzione_domanda del formio.
   */
  formioSezioneRichiestaSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_SECTION_REQUIRED;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dell'helper per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dell'helper del formio.
   */
  formioModalSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_MODAL;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dell'helper per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto senza le proprietà dell'helper del formio.
   */
  formioReadonlySanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la keyword per la gestione del dato
    const jsKey = this.KEY_FORMIO_READONLY;
    // Richiamo e ritorno la funzione di pulizia dei dati
    return this.formioStartWithSanitizer(formioData, jsKey);
  }

  /**
   * Funzione che va a gestire la sanitizzazione delle chiavi per la gestione dati FormIo.
   * @param formioData any con l'oggetto di submit generato da formio.
   * @returns any con l'oggetto di submit sanitizzato.
   */
  formioDataCleanerIgnore(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Itero la lista di chiavi per la gestione specifiche
    formioIgnoreList.forEach((item: FormioIgnore) => {
      // Verifico la tipologia di elemento per la sanitizzazione
      if (item.type === FormioIgnoreType.STARTWITH) {
        // Effetto la pulizia per uno "start with"
        formioData = this.formioStartWithSanitizer(formioData, item.key);
        // #
      } else if (item.type === FormioIgnoreType.EXACTKEY) {
        // Effetto la pulizia quando la configurazione presente una specifica chiave
        formioData = this.formioExactSanitizer(formioData, item.key);
        // #
      }
    });

    // Ritorno l'oggetto
    return formioData;
  }

  /**
   * ##########################################################################
   * GESTIONE DELLE FUNZIONI CUSTOM PER MANIPOLAZIONE DEL FORM SUBMIT DI FORMIO
   * ##########################################################################
   */

  /**
   * Funzionalità di supporto che gestisce le possibili gestioni custom per il formio, a seconda del tipo di formio stesso.
   * Il formio può essere, come per il flag disable, arricchito con una chiave che permette di gestire, se presente, delle logiche specifiche.
   * Le logiche specifiche sono definite dai servizi di supporto.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  formioTipiIstanzaSanitizer(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Richiamo il servizio per la gestione formio: VINCA APP
    formioData = this._formioVincaApp.formioVincaAppHandler(formioData);
    // Richiamo il servizio per la gestione formio: VINCA SCR
    formioData = this._formioVincaScr.formioVincaScrHandler(formioData);
    // Richiamo il servizio per la gestione formio: INFO AC
    formioData = this._formioInfoAC.formioInfoACHandler(formioData);

    // Ritorno l'oggetto
    return formioData;
  }

  /**
   * ##########################################################################
   * GESTIONE DELLE FUNZIONI CUSTOM PER MANIPOLAZIONE DEL FORM SUBMIT DI FORMIO
   * ##########################################################################
   */

  /**
   * Funzione di comodo che aggiorna il valore di un componente del formio, data la chiave di "accesso" e il valore d'aggiornare.
   * @param data any con l'oggetto che definisce tutte le informazioni del formio.
   * @param key string con la chiave del campo d'aggiornare. Il campo può avere domini separati da ".".
   * @param value any con il valore da definire all'interno di data.
   * @returns any con l'oggetto data aggiornato.
   */
  updateFormIoComponent(data: any, key: string, value: any) {
    // Effettuo un try catch per gestire la situazione
    try {
      // Definisco un oggetto di appoggio con la referenza degli oggetti di data fino a scendere alla proprietà da usare
      let dataDeep: any = data;

      // Vado a separare i domini della chiave per ottenere il path alla proprietà di data
      const keysDomain: string[] = key.split('.');
      // Itero i domini e cerco di accedere
      keysDomain.forEach((k: string, i: number) => {
        // Verifico se sto iterando l'ultima chiave della lista
        if (!keysDomain[i + 1]) {
          // Ho raggiunto l'ultimo livello di dominio, aggiorno la variabile
          dataDeep[k] = value;
          // #
        } else {
          // Non sono all'ultima chiave, aggiorno l'oggetto con il sotto-oggetto
          dataDeep = dataDeep[k];
        }
      });

      // Ho aggiornato i dati, ritorno il risultato
      return data;
      // #
    } catch (e) {
      // Ritorno l'input
      return data;
    }
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione che gestisce le logiche per l'apertura di un help derivato dall'evento formio.
   * @param formioEvent any con l'oggetto contenente i dati generati per l'evento formio.
   * @param chiaveHelpParams IChiaveHelpParams con le informazioni per comporre la stringa per la chiave dell'helper
   * @param helpQuadro Help[]
   */
  formioHelp(
    formioEvent: any,
    chiaveHelpParams: IChiaveHelpParams,
    helpQuadro: Help[]
  ) {
    // Estraggo dall'evento del formio la chiave dell'API
    const keyFormio: string = formioEvent.component.key;
    // Rimuovo il prefisso della chiave con tramite la costante decisa per gestire gli helper
    const key: string = keyFormio.replace(`${this.KEY_FORMIO_HELPER}.`, '');

    // Estraggo le informazioni per la generazione dei "domini" per la chiave
    const { adempimento, componente, quadro } = chiaveHelpParams ?? {};
    const codAdempi: string = adempimento?.cod_adempimento;
    const codTipoAdempi: string =
      adempimento?.tipo_adempimento?.cod_tipo_adempimento;
    // Definisco i domini effettivi della chiave
    const keyComp = componente ? `${componente}.` : '';
    const keyCodTipoAdempi = codTipoAdempi ? `${codTipoAdempi}.` : '';
    const keyCodAdempi = codAdempi ? `${codAdempi}.` : '';
    const keyQuadro = quadro ? `${quadro}.` : '';
    const keysDominio = `${keyComp}${keyCodTipoAdempi}${keyCodAdempi}${keyQuadro}`;

    // Compongo la chiave dell'help
    const chiaveHelp = `${keysDominio}${key}`;
    const helpByChiave: Help = helpQuadro.find((help: Help) => {
      // Verifico il match tra le chiavi
      return help.chiave_help === chiaveHelp;
      // #
    });
    // Estraggo la descrizione dell'help oppure metto un placholder
    const bodyModale = helpByChiave?.des_testo_help ?? 'Help non trovato';

    // Apro il pannello con i dati dell'helper
    this._message.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: bodyModale,
      buttons: [],
    });
  }

  /**
   * Funzione che ritorna il nome della sezione di un FormIo dato un evento generato da FormIo.
   * @param formioEvent any con le informazioni dell'evento formio.
   */
  getFormioSectionData(formioEvent: any) {
    // Recupero la chiave dell'evento del componente del formio
    const key: string = formioEvent?.component?.key;
    // Recuperata la chiave del componente vado a rimuovere l'identificativo del salvataggio
    const section: string = key?.replace(
      `${this.KEY_FORMIO_SAVE_SECTION}.`,
      ''
    );

    // Recuperata la sezione senza prefisso, la ritorno
    return section ?? '';
  }

  /**
   * Funzione che va ad estrarre le informazioni dei dati tecnici di un'opera dato l'oggetto dati tecnici estratto dal json data.
   * @param datiTecnici any con l'oggetto contenente i dati tecnici di un'opera.
   * @param opera Opera con l'oggetto che definisce le informazioni dell'opera.
   * @param oggettoIstanza OggettoIstanza con le informazioni dell'oggetto istanza di riferimento per il recupero dati.
   * @returns any con la struttura dati di una specifica sezione dei dati tecnici. Se non definiti, ritorno undefined.
   * @jira SCRIVA-1003
   */
  estraiDatiTecniciOpera(
    datiTecnici: any,
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ) {
    // Verifico l'input
    if (!datiTecnici || !opera || !oggettoIstanza) {
      // Mancano i dati di configurazione
      return undefined;
      // #
    }

    // Recupero le informazioni per la composizione della proprietà per il recupero dati
    const codTipoOgg: string = opera.tipologia_oggetto?.cod_tipologia_oggetto;
    const prefixDT: string = this.KEY_FORMIO_DATI_TECNICI;
    // Definisco la chiave per l'accesso ai dati tecnici e la ricerca configurazioni
    const dtTarget: string = `${prefixDT}${codTipoOgg}`;

    // Recupero l'array d'informazioni dei dati tecnici
    const arrayInfoDT: any[] = datiTecnici[dtTarget];
    // Cerco all'interno dell'array dei dati tecnici lo stesso elemento per oggetto istanza
    const dtOpera: any = arrayInfoDT?.find((elemInfoDt: any) => {
      // Effettuo un match tra l'id dell'elemento e l'id dell'oggetto in input
      const elemIdOggIst: number = elemInfoDt.id_oggetto_istanza;
      const inputIdOggIst: number = oggettoIstanza.id_oggetto_istanza;
      // Ritorno la verifica tra gli id
      return elemIdOggIst === inputIdOggIst;
      // #
    });

    // Ritorno l'oggetto del dato tecnico dell'opera oppure null (perché null? Non so, nel codice c'era così)
    return dtOpera ?? null;
  }

  /**
   * Funzione che gestisce le logiche di controllo per la validazione di un FormIo.
   * @notes La logica di verifica è stata ereditata, non ho avuto tempo di trovare un'alternativa più semplice.
   * @param params IFormioValidationCheck con le informazioni per la gestione della verifica.
   * @returns Observable<boolean> con l'indicazione se il form FormIo è valido.
   */
  handleFormIoValidation(params: IFormioValidationCheck): Observable<boolean> {
    // Verifico l'input
    if (!params || !params.formFormIo) {
      // Mancano le informazioni minime
      return of(undefined);
      // #
    }

    // Estraggo dall'oggetto dei parametri le informazioni
    // - Attenzione che è il FormioComponent della libreria
    const formFormIo: FormioComponent = params.formFormIo;
    const callback: (valid: boolean) => any = params.callback;

    // Recupero le informazioni per la gestione del formio
    const formio = formFormIo.formio;
    // Recupero la Promise per la submit del FormIo
    const onSubmission = formio.onSubmission;

    // Registro una funzione al submit del FormIo
    onSubmission.then(() => {
      // NOTA DELLO SVILUPPATORE - Non ho idea del perché del TimeOut
      setTimeout(() => {
        // Verifico se il formio è valido
        const validForm = this.isFormIoValid(formFormIo);
        // Se esiste eseguo la callback
        callback ? callback(validForm) : undefined;
        // #
      }, 250);
      // #
    });
    // Lancio manualmente il submit del formio, la funzione evidenziera' i campi non validati in rosso con .formio-error-wrapper
    formio.submit();
  }

  /**
   * Funzione di comodo che verifica se all'interno del componente FormioComponent della libreria sono presenti degli errori.
   * @param formFormIo FormioComponent con il riferimento al componente FormIo.
   * @returns boolean con il risultato del check sugli errori del form.
   */
  isFormIoValid(formFormIo: FormioComponent): boolean {
    // Verifico l'input
    if (!formFormIo) {
      // Manca l'oggetto
      return undefined;
      // #
    }

    // Recupero gli alert di errore interni a FormIo
    const alerts: FormioAlert[] = formFormIo.alerts.alerts;
    // Verifico se all'interno degli alert del FormIo sono presenti degli errori
    const formioWithErrors: boolean = alerts.some((a: FormioAlert) => {
      // Verifico se l'alert è di errore
      return a.type === FormIoAlertTypes.error;
      // #
    });

    // Ritorno valido se il formio non ha errori
    return !formioWithErrors;
  }
}
