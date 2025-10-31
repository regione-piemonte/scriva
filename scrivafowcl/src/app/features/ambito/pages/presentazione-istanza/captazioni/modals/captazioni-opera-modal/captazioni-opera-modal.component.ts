/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import {
  AuthStoreService,
  HelpService,
  MessageService,
} from 'src/app/shared/services';
import { FormioCustomEvents } from '../../../../../../../shared/components/formio/utilities/formio.enums';
import { ScrivaComponenteApp } from '../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { FormioService } from '../../../../../../../shared/services/formio/formio.service';
import { LoggerService } from '../../../../../../../shared/services/logger.service';
import { DatiTecniciOperaModalComponent } from '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component';
import { IDTOperaSalvataggio } from '../../../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { FontanileUtilities } from './fontanile/fontanile.utilities';
import { PozzoUtilities } from './pozzo/pozzo.utilities';
import { PresaUtilities } from './presa/presa.utilities';
import { SorgenteUtilities } from './sorgente/sorgente.utilities';
import { TrinceaDrenanteUtilities } from './trincea-drenante/trincea-drenante.utilities';
import {
  IDTOperaSalvataggioSezione,
  IFormIoCaptazioniOperaParams,
} from './utilities/captazioni-opera-modal.interfaces';

/**
 * Componente modale per le opere di captazione.
 * Il layout e la grafica è ripresa dal componente generico: dati-tecnici-opera-modal.
 */
@Component({
  selector: 'app-captazioni-opera-modal',
  templateUrl:
    '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component.html',
  styleUrls: [
    '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component.scss',
  ],
})
export class CaptazioniOperaModalComponent
  extends DatiTecniciOperaModalComponent
  implements OnInit
{
  /**
   * Costruttore.
   */
  constructor(
    // Parametri super
    activeModal: NgbActiveModal,
    authStore: AuthStoreService,
    formio: FormioService,
    help: HelpService,
    logger: LoggerService,
    message: MessageService,
    spinner: NgxSpinnerService
    // Parametri locali
  ) {
    // Lancio il super
    super(activeModal, authStore, formio, help, logger, message, spinner);
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  // #region "INIT COMPONENTE"

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente padre
    super.ngOnInit();
  }

  // #endregion "INIT COMPONENTE"

  /**
   * ####################
   * FUNZIONI DI OVERRIDE
   * ####################
   */

  /**
   * Funzione di init che genera le informazioni da iniettare all'interno del contesto FormIo.
   * @returns IFormIoCaptazioniOperaParams con le informazioni da iniettare.
   * @override
   */
  protected initFormIoInjectParams(): IFormIoCaptazioniOperaParams {
    // Definisco delle variabili di comodo
    const isBO: boolean = this.isBackOffice;
    const isFO: boolean = this.isFrontOffice;

    // Creo un oggetto con le informazioni extra da definire come render options
    const formioCaptazioniOperaParams: IFormIoCaptazioniOperaParams = {
      componenteQuadro: this.componente as ScrivaComponenteApp,
      oggetto: this.opera,
      oggettoIstanza: this.oggettoIstanza,
      pozzoUtils: new PozzoUtilities(isBO, isFO),
      presaUtils: new PresaUtilities(isBO, isFO),
      sorgenteUtils: new SorgenteUtilities(isBO, isFO),
      fontanileUtils: new FontanileUtilities(isBO, isFO),
      trinceaDrenanteUtils: new TrinceaDrenanteUtilities(isBO, isFO),
    };

    // Ritorno la configurazione
    return formioCaptazioniOperaParams;
  }

  /**
   * Funzione invocata all'emissione di un evento custom da parte del componente formio.
   * @param formioEvent any contenente le informazioni di dettaglio per l'evento generato dal componente formio.
   * @override Override della funzione che intercetta gli eventi custom del FormIo. Nella gestione degli eventi vengono aggiunti gli eventi specifici per questo componente.
   */
  onCustomEvent(formioEvent: any) {
    // Cerco d'identificare l'evento generato dal formio
    switch (formioEvent.type) {
      // ### Visualizzazione degli helper
      case FormioCustomEvents.visualizzaHelper:
        // Tento di visualizzare l'helper data la chiave identificativa censita sul formio
        this.formioHelp(formioEvent);
        break;
      // ### Salvataggio dati
      case FormioCustomEvents.salvaDatiSezione:
        // Lancio la funzione di gestione per il salvataggio dei dati
        this.formioSaveDatiTecniciSezione(formioEvent);
        break;
      // ### default
      default:
        break;
    }
  }

  /**
   * Funzione che gestisce il flusso logico per salvare le informazioni dei dati tecnici.
   * @param formioEvent any con l'oggetto contenente i dati generati per l'evento formio.
   */
  protected formioSaveDatiTecniciSezione(formioEvent: any) {
    // Recupero il nome della sezione dall'evento formio
    const sezione: string = this._formio.getFormioSectionData(formioEvent);
    // Lancio il salvataggio dei dati tecnici
    this.aggiornaDatiTecniciSezione(sezione);
  }

  /**
   * #####################################
   * FUNZIONALITA' SALVATAGGIO DATI FORMIO
   * #####################################
   */

  // #region "SALVATAGGIO FORMIO"

  /**
   * Funzione che effettua l'aggiornamento dati per una sezione specifica dei dati tecnici.
   * @param sectionName string con il riferimento alla sezione dei dati tecnici da salvare.
   * @param forceEmit boolean che definisce la possibilità di emettere l'evento di dati tecnici da salvare anche se è definita la callback già adibita a questo tipo di lofica. Per default è: false.
   */
  private aggiornaDatiTecniciSezione(
    sectionName: string,
    forceEmit: boolean = false
  ) {
    // Recupero la porzione dati specifica per la sezione e ne creo una copia
    const datiSezione: any = cloneDeep(this.formioUserData[sectionName]);

    // Definisco un oggetto con le informazioni iniziali
    let datiTecnici: any = this.sourceData;
    // Verifico se l'oggetto iniziale è definito
    if (!datiTecnici) {
      // Inizializzo il dato
      datiTecnici = {};
    }
    // Verifico se la sezione specifica richiesta ha effettivamente già dati compilati
    if (!datiTecnici[sectionName]) {
      // Inizializzo i dati della sezione
      datiTecnici[sectionName] = {};
    }

    // Assegno le informazioni della sezione nel dato effettivo da salvare
    datiTecnici[sectionName] = datiSezione;
    // Se definita, vado ad eliminare la proprietà di sezione dato obbligatorio
    delete datiTecnici[sectionName][this.SECTION_REQUIRED];

    // Definisco l'oggetto contenente le informazioni per salvare i dati tecnici
    const datiTecniciDaSalvare: IDTOperaSalvataggioSezione = {
      datiSezione: datiTecnici[sectionName],
      sezione: sectionName,
      datiTecnici: this.formioUserData,
    };

    // Invoco le logiche della callback per il salvataggio dei dati
    this.saveDatiTecniciOpera(datiTecniciDaSalvare, forceEmit);
  }

  /**
   * Funzione che gestisce il flusso logico per il salvataggio dei dati tecnici opera.
   * La funzione verifica se è stata definita una callback specifica per il salvataggio dati.
   * Se non è stata definita, verrà effettuato l'emit di un evento con i dati da salvare.
   * @param sectionName string con il riferimento alla sezione dei dati tecnici da salvare.
   * @param forceEmit boolean che definisce la possibilità di emettere l'evento di dati tecnici da salvare anche se è definita la callback già adibita a questo tipo di lofica. Per default è: false.
   */
  protected saveDatiTecniciOpera(
    datiTecniciDaSalvare: IDTOperaSalvataggio,
    forceEmit: boolean = false
  ) {
    // Recupero la callback dai parametri di input
    let saveDatiTecniciOperaPartial: (dtDaSalvare: IDTOperaSalvataggio) => any;
    saveDatiTecniciOperaPartial = this.saveDatiTecniciOperaPartialCallback;

    // Verifico se esiste effettivamente una funzione di callback
    if (saveDatiTecniciOperaPartial) {
      // La funzione esiste, la eseguo
      saveDatiTecniciOperaPartial(datiTecniciDaSalvare);
      // #
    }

    // Gestisco la casista per l'uso dell'emitter dati
    if (!saveDatiTecniciOperaPartial || forceEmit) {
      // Condizione per l'uso dell'evento da propagare, lo lancio
      this.onSalvaModifiche$.emit(datiTecniciDaSalvare);
      // #
    }
  }

  // #endregion "SALVATAGGIO FORMIO"

  // #region "GETTER E SETTER"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera la callback con le logiche di salvataggio dei dati tecnici opera.
   * @returns (datiTecniciDaSalvare: IDTOperaSalvataggio) => any; con la funzione se definita.
   */
  get saveDatiTecniciOperaPartialCallback(): (
    datiTecniciDaSalvare: IDTOperaSalvataggio
  ) => any {
    // Cerco di recuperare dalla configurazione la funzione
    return this.callbacks?.saveDatiTecniciOperaPartial;
  }

  // #endregion "GETTER E SETTER"
}
