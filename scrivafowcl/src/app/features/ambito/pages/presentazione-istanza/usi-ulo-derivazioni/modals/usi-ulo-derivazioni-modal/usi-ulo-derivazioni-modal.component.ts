/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScrivaComponenteApp } from '../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import {
  AuthStoreService,
  HelpService,
  MessageService,
} from '../../../../../../../shared/services';
import { FormioService } from '../../../../../../../shared/services/formio/formio.service';
import { LoggerService } from '../../../../../../../shared/services/logger.service';
import {
  OggettoIstanzaGeometrieFE,
  OggettoIstanzaLike,
  SoggettoIstanza,
} from '../../../../../models';
import { DatiTecniciOperaModalComponent } from '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component';
import {
  IDTUsiUloDERModalParams,
  IFormIoDTUsiUloDERParams,
} from './utilities/usi-ulo-derivazioni-modal.interfaces';

@Component({
  selector: 'app-usi-ulo-derivazioni-modal',
  templateUrl:
    '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component.html',
  styleUrls: [
    '../../../opere/modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component.scss',
  ],
})
export class UsiUloDerivazioniModalComponent
  extends DatiTecniciOperaModalComponent
  implements OnInit
{
  /** Input IDTUsiUloDERModalParams con i parametri passati alla modale come dati di configurazione. */
  @Input() dataModal: IDTUsiUloDERModalParams;

  /**
   * Costruttore.
   */
  constructor(
    activeModal: NgbActiveModal,
    authStore: AuthStoreService,
    formio: FormioService,
    help: HelpService,
    logger: LoggerService,
    message: MessageService,
    spinner: NgxSpinnerService
  ) {
    // Richiamo il super
    super(activeModal, authStore, formio, help, logger, message, spinner);
    // Lancio la funzione di setup componente
    this.setupComponente();
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

  /**
   * Funzione di init che genera le informazioni da iniettare all'interno del contesto FormIo.
   * @returns IFormIoDTUsiUloDERParams con le informazioni da iniettare.
   * @override Modifico il valore in riferimento ai parametri da passare al formio.
   */
  protected initFormIoInjectParams(): IFormIoDTUsiUloDERParams {
    // Cerco di recuperare il soggetto istanza di riferimento
    const soggettiIstanza: SoggettoIstanza[] =
      this.oggettoIstanza?.soggettiGeometrie ?? [];
    const soggetto: SoggettoIstanza = soggettiIstanza[0];

    // Creo un oggetto con le informazioni extra da definire come render options
    const formioDTOperaParams: IFormIoDTUsiUloDERParams = {
      componenteQuadro: this.componente as ScrivaComponenteApp,
      oggetto: this.opera,
      oggettoIstanza: this.oggettoIstanza,
      soggetto,
    };

    // Ritorno la configurazione
    return formioDTOperaParams;
  }

  // #endregion "INIT COMPONENTE"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns OggettoIstanzaGeometrieFE con il valore del paramatro.
   * @override
   */
  get oggettoIstanza(): OggettoIstanzaGeometrieFE {
    // Estraggo dall'oggetto di configurazione il parametro
    const oggettoIstanza: OggettoIstanzaLike = this.dataModal?.oggettoIstanza;
    // Ritorno una copia forzando la tipizzazione
    return cloneDeep(<OggettoIstanzaGeometrieFE>oggettoIstanza);
    // #
  }

  // #endregion "GETTER E SETTER"
}
