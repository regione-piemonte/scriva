/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  AfterViewInit,
  Component,
  Inject,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { AmbitoService } from 'src/app/features/ambito/services';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { StepConfig } from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { FormioService } from '../../../../../shared/services/formio/formio.service';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { OggettoIstanza } from '../../../models';
import { OpereVerificheStepService } from '../../../services/opere/opere-verifiche-step.service';
import { OpereService } from '../../../services/opere/opere.service';
import { OpereComponent } from '../opere/opere.component';
import { IndLivelliOpere } from '../opere/utilities/opere.enums';
import { InfrastruttureAccessorieOperaModalComponent } from './modals/infrastrutture-accessorie-opera-modal/infrastrutture-accessorie-opera-modal.component';
import { InfrastruttureAccessorieConsts } from './utilities/infrastrutture-accessorie.consts';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';

@Component({
  selector: 'app-infrastrutture-accessorie',
  templateUrl: './infrastrutture-accessorie.component.html',
  styleUrls: ['../opere/opere.component.scss'],
  providers: [OpereVerificheStepService],
})
export class InfrastruttureAccessorieComponent
  extends OpereComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  /** InfrastruttureAccessorieConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new InfrastruttureAccessorieConsts();

  /** string costante che definisce il contenitore per agganciare i messaggi di segnalazione. */
  readonly ALERT_TARGET_MODALE: string = 'infrastrutture-accessorie-content';

  /**
   * string che definisce il nome del componente di riferimento.
   * @override
   */
  protected componentName: string = 'InfrastruttureAccessorieComponent';

  /**
   * any per gestire il componente da usare per la modale di dettaglio delle opere.
   * @override
   */
  protected componenteModale: any = InfrastruttureAccessorieOperaModalComponent;

  /**
   * Costruttore.
   */
  constructor(
    @Inject(CONFIG) protected injConfig: StepConfig,
    ambito: AmbitoService,
    adempimento: AdempimentoService,
    auth: AuthStoreService,
    configurazioni: ConfigurazioniScrivaService,
    formio: FormioService,
    geeco: GeecoService,
    help: HelpService,
    istanza: IstanzaService,
    location: LocationService,
    logger: LoggerService,
    message: MessageService,
    modal: NgbModal,
    opere: OpereService,
    opereVerificheStep: OpereVerificheStepService,
    route: ActivatedRoute,
    stepManager: StepManagerService,
    spinner: NgxSpinnerService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
  ) {
    // Richiamo il super passando tutti i servizi
    super(
      injConfig,
      ambito,
      adempimento,
      configurazioni,
      formio,
      geeco,
      location,
      logger,
      modal,
      opere,
      opereVerificheStep,
      route,
      presentazioneIstanzaService,
      message,
      help,
      istanza,
      auth,
      stepManager,
      spinner
    );
  }

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Richiamo la funzione del super
    super.ngOnInit();
  }

  /**
   * ngAfterViewInit.
   */
  ngAfterViewInit() {
    // Richiamo la funzione del super
    super.ngAfterViewInit();
  }

  /**
   * ngOnDestroy.
   */
  ngOnDestroy() {
    // Richiamo il destroy della classe padre
    super.ngOnDestroy();
  }

  /**
   * #################
   * FUNZIONI OVERRIDE
   * #################
   */

  /**
   * Funzione di supporto invocata nel momento in cui i dati sono stati scaricati per il dato contestuale.
   * La funzione gestisce le logiche di assegnamento delle informazioni per i dati del componente.
   * @param oggettiIstanza OggettoIstanza[] con la lista di elementi scaricati.
   * @override
   */
  protected onInitOggettiIstanza(oggettiIstanza: OggettoIstanza[]) {
    // Effettuo il filtro sulla lista degli oggetti istanza forzando la tipizzazione del risultato della funzione
    let oggIstQdr: OggettoIstanza[] = <OggettoIstanza[]>(
      this.filterOpereOggettiIstanzaByQuadro(oggettiIstanza)
    );

    // Definisco il livello per il recupero dati
    const indLivello = IndLivelliOpere.secondo;
    // Nelle restituzioni sono con ind_livello 2 il livello 1 viene salvato nel quadro dati generali
    this.oggettiIstanza = this.filterOggettiIstanzaByLivello(
      oggIstQdr,
      indLivello
    );
  }

  // #region "GESTIONE TABELLE"

  /**
   * Funzione di set che va a definire la struttura della tabella per la ricerca delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: Opera.
   * @override Override del set della struttura della tabella per la ricerca opere.
   */
  protected setTableRicercaOpere() {
    // Definisco la configurazione per la tabella
    this.searchResultsColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizeable: false,
        width: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxColTemplate,
        cellClass: 'checkbox-cell',
      },
      {
        name: 'Tipologia di captazione',
        prop: 'tipologia_oggetto.des_tipologia_oggetto',
      },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Descrizione', prop: 'des_oggetto' },
      { name: 'Codice rilievo', cellTemplate: this.codiceRilievoOperaTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOperaTemplate,
        sortable: false,
      },
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOperaTemplate },
    ];
  }

  /**
   * Funzione di set che va a definire la struttura della tabella di associazione delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: OggettoIstanza.
   * @override Override del set della struttura della tabella per l'associazione opere.
   */
  protected setTableAssociazioneOpere() {
    // Definisco la configurazione per la tabella
    this.associazioniColumns = [
      { name: 'Denominazione opera', prop: 'den_oggetto' },
      { name: 'Descrizione opera', prop: 'des_oggetto' },
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOggIstTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOggIstTemplate,
        sortable: false,
      },
      {
        name: 'Località',
        cellTemplate: this.localitaTemplate,
        sortable: false,
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 120,
        resizeable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];
  }

  // #endregion "GESTIONE TABELLE"
}
