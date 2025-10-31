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
import { NgxSpinnerService } from 'ngx-spinner';
import {
  AuthStoreService,
  HelpService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaComponenteApp } from '../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { FormioService } from '../../../../../../../shared/services/formio/formio.service';
import { LoggerService } from '../../../../../../../shared/services/logger.service';
import { DatiTecniciOperaModalComponent } from '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component';
import { IFormIoInfrastruttureAccessorieOperaParams } from './utilities/infrastrutture-accessorie-opera-modal.interfaces';

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
export class InfrastruttureAccessorieOperaModalComponent
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
   * @returns IFormIoInfrastruttureAccessorieOperaParams con le informazioni da iniettare.
   * @override
   */
  protected initFormIoInjectParams(): IFormIoInfrastruttureAccessorieOperaParams {
    // Creo un oggetto con le informazioni extra da definire come render options
    const formioCaptazioniOperaParams: IFormIoInfrastruttureAccessorieOperaParams =
      {
        componenteQuadro: this.componente as ScrivaComponenteApp,
        oggetto: this.opera,
        oggettoIstanza: this.oggettoIstanza,
      };

    // Ritorno la configurazione
    return formioCaptazioniOperaParams;
  }
}
