/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, from, Observable, of, Subscription } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { EditAllegatoModalComponent } from 'src/app/shared/components/edit-allegato-modal/edit-allegato-modal.component';
import { ProgressModalComponent } from 'src/app/shared/components/progress-modal/progress-modal.component';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { CodTipologiaAllegato } from 'src/app/shared/enums/cod-tipologia-allegato.enums';
import {
  Adempimento,
  ConfigAdempimento,
  Istanza,
  StepConfig,
  TipoAdempimentoOggApp,
} from 'src/app/shared/models';
import { IntegrazioneIstanzaStatus } from 'src/app/shared/models/istanza/integrazione-istanza-status.model';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';
import {
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import {
  AttoreGestioneIstanzaEnum,
  StatoIstanzaEnum,
  TipoEventoEnum,
} from 'src/app/shared/utils';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { InfoProtocolloAttoModalComponent } from '../../../../../shared/components';
import { CardRiepilogoAllegatiActsion } from '../../../../../shared/components/card-riepilogo-allegati/utilities/card-riepilogo-allegati.enums';
import { TipoInfoAdempimento } from '../../../../../shared/services/configurazioni/utilities/configurazioni.enums';
import { Allegato, CategoriaAllegato, TipoAllegato } from '../../../models';
import { AllegatiUploadOptions } from '../../../models/allegati/allegati-upload-options';
import { AllegatoAdditionalInfo } from '../../../models/allegati/allegato-additional-info.model';
import { AllegatiService } from '../../../services';
import { AllegatiIntegrazioniService } from '../../../services/allegati-integrazioni.service';
import { AllegatiUploaderProgressService } from '../../../services/allegati-uploader-progress';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  RequestDataQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-allegati',
  templateUrl: 'allegati.component.html',
  styleUrls: ['allegati.component.scss'],
})
export class AllegatiComponent
  extends StepperIstanzaComponent
  implements OnInit, OnDestroy
{
  codMaschera = '.MSCR013F';
  codContesto = '';

  gestioneEnum = AttoreGestioneIstanzaEnum;

  tipoEventoEnum = TipoEventoEnum;

  oggAppBtnPubblica: TipoAdempimentoOggApp;
  oggAppBtnProtocolla: TipoAdempimentoOggApp;
  oggAppBtnPerfezionaAllegati: TipoAdempimentoOggApp;
  oggAppBtnIntegraAllegati: TipoAdempimentoOggApp;
  oggAppBtnIntegraAllegatiSucc: TipoAdempimentoOggApp;

  formAllegati: FormGroup;
  tipologiaSelezionata: TipoAllegato = null;

  adempimento: Adempimento;

  /** TipoAllegato[] con la lista completa dei tipi allegato. */
  fullListTipiAllegato: TipoAllegato[];
  /** CategoriaAllegato[] con la lista completa delle categorie allegato scaricate. */
  categorie: CategoriaAllegato[];
  /** TipoAllegato[] con la lista dei tipi allegato, filtrata sulla base del valore della categoria scelta in pagina. */
  tipiAllegato: TipoAllegato[];

  allegatiObbligatoriPerVincoli: TipoAllegato[];

  istanza: Istanza;

  saveWithPut = false;

  acceptedFileTypes: string;
  fileExtensionsArray = [];
  maxFileSize: number;
  maxNote = 2000;
  maxLengths: {
    key: string;
    max: number;
  }[] = [
    { key: 'numProtocollo', max: 20 }, // num_protocollo_allegato
    { key: 'numAtto', max: 20 }, // num_atto
    { key: 'titolo', max: 1000 }, // titolo allegato
    { key: 'autore', max: 300 }, // autore allegato
  ];
  // numero di protocollo lunghezza massima campo
  numProtocolloMaxLength: number;

  tipoIntegrazione;

  rows: Allegato[] = [];
  additionalInfo: AllegatoAdditionalInfo = null;

  alertText: string;
  showAlertBanner = false;
  disableBtnRiferimentiAtto = false;

  showSuccessMessageIntegrazione = false;
  showBtnConferma = false;
  flgIntegrazioneAllegati = false;
  flgContinue = false;
  flgContinueIntegrazione = false;

  setAsterisk = false;
  totalMandatory = 0;

  today = new Date();

  _readonlyAllegati: boolean = false;

  helpListLoaded: boolean = false;

  helpBannerTextShow: boolean = true;
  helpTestoFormatoFile: string;
  private modalProgressRef: NgbModalRef;

  /** Subscription registrata sull'evento di: annullamento integrazione documenti. */
  onAnnullaIntegrazione$: Subscription;

  constructor(
    public router: Router,
    private allegatiService: AllegatiService,
    private allegatiIntegrazioniService: AllegatiIntegrazioniService,
    private allegatiUploaderProgressService: AllegatiUploaderProgressService,
    private configurazioniService: ConfigurazioniScrivaService,
    private fb: FormBuilder,
    private modalService: NgbModal,

    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService
  ) {
    super(
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
    this.checkOggettiApplicativi();

    // Lancio il setup dei listener del componente
    this.setupListeners();
  }

  /**
   * #################
   * FUNZIONI DI SETUP
   * #################
   */



  checkOggettiApplicativi() {
    const oggAppPulsanti = this.istanzaService.getOggAppPulsanti()
      ? this.istanzaService.getOggAppPulsanti()
      : this.istanzaService.getOggApp();
    oggAppPulsanti?.forEach((element) => {
      switch (element.cod_oggetto_app) {
        case 'btn_pubblica_doc':
          this.oggAppBtnPubblica = element;
          break;
        case 'btn_protocolla':
          this.oggAppBtnProtocolla = element;
          break;
        case 'btn_perfeziona_allegati':
          this.oggAppBtnPerfezionaAllegati = element;
          break;
        case 'btn_integra_allegati':
          this.oggAppBtnIntegraAllegati = element;
          break;
        case 'btn_integra_allegati_succ':
          this.oggAppBtnIntegraAllegatiSucc = element;
          break;
        default:
          break;
      }
    });
  }

  /**
   * Funzione di setup dei listeners del componente.
   */
  private setupListeners() {
    // Mi registro all'evento di annuall integrazione documenti
    this.onAnnullaIntegrazione$ =
      this.allegatiIntegrazioniService.onAnnullaIntegrazione$.subscribe({
        next: (istanza: Istanza) => {
          // Recupero l'oggetto dell'adempimento
          const adempimento: Adempimento = this.adempimento;
          // Aggiorno le informazioni collegate al cambio istanza
          this.updateTipoIntegrazione(istanza, adempimento);
          // #
        },
      });
  }

  /**
   * ################
   * FUNZIONI DI INIT
   * ################
   */

  ngOnInit() {
    this.setVisibilityButtonNext(true);
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.changed = false;
    this.loadData();

    this._getStatusAllegatiIntegrazioni();
    this._getStatusAllegatiUploader();
  }

  private _getStatusAllegatiIntegrazioni() {
    this.allegatiIntegrazioniService
      .getStatus()
      .subscribe((ret: IntegrazioneIstanzaStatus) => {
        this.readonlyAllegati = ret.readonlyAllegati;
        if (ret.istanza) {
          const precDaconfermare =
            this.istanza.stato_istanza.codice_stato_istanza ===
            StatoIstanzaEnum.DA_CONFERMARE_INTEG;
          const statoIstanzaOld =
            this.istanza.stato_istanza.codice_stato_istanza;
          this.istanza = { ...ret.istanza };
          const statoIstanza = this.istanza.stato_istanza.codice_stato_istanza;
          const isfirstState =
            statoIstanza == StatoIstanzaEnum.PERF_ALLEGATI ||
            statoIstanza === StatoIstanzaEnum.RIC_INTEGR ||
            statoIstanza === StatoIstanzaEnum.RIC_INTEGR_SUCC
              ? true
              : false;
          if (
            (!precDaconfermare &&
              this.istanza.stato_istanza.codice_stato_istanza ===
                StatoIstanzaEnum.DA_CONFERMARE_INTEG) ||
            (precDaconfermare &&
              (this.istanza.stato_istanza.codice_stato_istanza ===
                StatoIstanzaEnum.DA_FIRMARE_LETTERA ||
                isfirstState)) ||
            statoIstanzaOld !== statoIstanza
          ) {
            this.reloadAllegati();
          }
        }
        if (ret.idIstanza === this.idIstanza) {
          this.flgContinueIntegrazione = true;
        }
      });
  }
  private _getStatusAllegatiUploader() {
    this.allegatiUploaderProgressService.getStatus().subscribe((ret: any) => {
      // SCRIVA-1441
      if (ret.action === 'endUpload' || ret.action === 'endUploadProgress') {
        this.formAllegati.reset();
        // SCRIVA-1555
        this.disableBtnRiferimentiAtto = false;
        this.additionalInfo = null;
        this.getAllegatiQuadro(
          ret.action === 'endUploadProgress' ? false : true
        );
        // negli upload resetto la variabile di ambiente dello stepper changed
        this.changed = false;
      }
      if (ret.action === 'reloadAllegati') {
        this.reloadAllegati();
      }
      if (ret.action === 'showProgress') {
        this.showProgress();
      }
    });
  }

  /**
   * ###################
   * FUNZIONE DI DESTROY
   * ###################
   */

  ngOnDestroy() {
    try {
      // Tento di effettuare l'unsubscribe dei listener
      this.onAnnullaIntegrazione$?.unsubscribe();
      // #
    } catch (e) {}

    // Richiamo il destroy della classe estesa
    super.ngOnDestroy();
  }

  /**
   * mostro il progress
   */
  private showProgress() {
    this.modalProgressRef = this.modalService.open(ProgressModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'xl',
    });
    this.modalProgressRef.result.then((metaData: any) => {}).catch((err) => {});
  }

  /**
   * nascondo il progress
   */
  private hideProgress() {
    this.modalProgressRef.dismiss();
  }

  onHelpClicked(chiave: string) {
    const contesto = this.codContesto + '.' + this.codTipoQuadro;
    const modalContent =
      this.helpList.find(
        (help) => help.chiave_help === this.componente + contesto + '.' + chiave
      )?.des_testo_help || 'Help non trovato...';

    this._messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  updateHelpBannerText(chiave: string) {
    // testo_formato_file
    const helpBanner = this.helpList?.find(
      (help) =>
        help.tipo_help.cod_tipo_help === 'BNR' &&
        help.chiave_help.includes(chiave)
    );

    if (helpBanner?.des_testo_help !== 'NA' && helpBanner !== undefined) {
      this.helpBannerTextShow = true;
      this.helpTestoFormatoFile = helpBanner?.des_testo_help;
    } else {
      this.helpBannerTextShow = false;
    }
  }

  getHelpBannerText(chiave: string): string {
    const helpBanner = this.helpList?.find(
      (help) =>
        help.tipo_help.cod_tipo_help === 'BNR' &&
        help.chiave_help.includes(chiave)
    );

    return (
      helpBanner?.des_testo_help ||
      "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza."
    );
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
      (res) => {
        this.istanza = res;
        this.adempimento = this.istanza.adempimento;
        this.codContesto =
          '.' +
          this.adempimento.tipo_adempimento.cod_tipo_adempimento +
          '.' +
          this.adempimento.cod_adempimento;
        this.helpService.setCodContesto(this.codContesto);
        //chiamo la funzione per ottenere help, è override perché gestisce anche la visualizzazione del help
        this.getHelpList();
        const jsonData = JSON.parse(this.istanza.json_data);
        this.qdr_riepilogo = jsonData.QDR_RIEPILOGO;
        if (jsonData[this.codTipoQuadro]) {
          this.saveWithPut = true;
          this.isStepValid().subscribe((isValid: boolean) => {
            if (isValid) {
              // dopo aver ottenuto i dati chiama il salva...
              this.getAllegatiQuadro();
            }
          });
        }
        if (
          JSON.parse(res.json_data).QDR_CONFIG[this.codTipoQuadro]?.solo_lettura
        ) {
          this.attoreGestioneIstanza = this.gestioneEnum.READ_ONLY as string;
          // this.showButtonNext = true;
        }

        if (!this.oggAppBtnPubblica) {
          this.checkOggettiApplicativi();
        }

        // In caso di READ_ONLY non devo startare integrazione
        if (this.attoreGestioneIstanza !== this.gestioneEnum.READ_ONLY) {
          this.allegatiIntegrazioniService
            .checkIntegrazioneAndInit(this.istanza)
            .subscribe((ret: IntegrazioneIstanza) => {
              // non è necessaria alcuna azione
              this.getConfigAllegati();
            });
        } else {
          this.getConfigAllegati();
        }
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
      }
    );
  }

  getConfigAllegati() {
    forkJoin([
      this.allegatiService.getAllTipiAllegatoByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.allegatiService.getCategorieAllegatoByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.allegatiService.getAcceptedFileTypesByCodAdempimento(
        this.adempimento.cod_adempimento
      ),
      this.configurazioniService.getConfigurazione('SCRIVA_INDEX_MAX_MB_FILE'),
      this.configurazioniService
        .getConfigurazioniByInfoAndChiave(
          this.adempimento.cod_adempimento,
          TipoInfoAdempimento.tipoIntegrazione,
          this.istanza.stato_istanza.codice_stato_istanza
        )
        .pipe(catchError((err) => of(null))),
      this.allegatiService
        .getAllegatiObbligatoriPerVincoli(this.idIstanza)
        .pipe(
          catchError((err) => {
            console.log(
              "# Errore nel servizio /vincoli-autorizzazioni/configurazioni-allegati, o vincoli non configurati per l'adempimento selezionato."
            );
            return of(null);
          })
        ),
    ]).subscribe(
      (response) => {
        this.fullListTipiAllegato = response[0];
        this.categorie = response[1];

        this.acceptedFileTypes = '';
        this.fileExtensionsArray = [];
        response[2].map((ext) => {
          this.fileExtensionsArray.push(ext.estensione_allegato.toLowerCase());
          if (this.acceptedFileTypes.length > 0) {
            this.acceptedFileTypes += ', ';
          }
          this.acceptedFileTypes += '.' + ext.estensione_allegato.toLowerCase();
        });
        this.maxFileSize = parseFloat(response[3][0]['valore']);
        this.tipoIntegrazione = response[4]?.find(
          (elem) =>
            elem.chiave === this.istanza.stato_istanza.codice_stato_istanza
        )?.valore;
        this.allegatiObbligatoriPerVincoli = response[5];

        // Recupero il valore per la gestione istanza "originale"
        const attoreGestione: string =
          this.istanzaService.getAttoreGestioneIstanza() ?? '';

        // SCRIVA-1645/1653: PROBLEMA NELL'ABILITARE PAGINA ALLEGATI,
        // NOTE DA EXCEL SCHEMA ATTORE GESTORE ISTANZA:
        // SEI SEI FRONT-OFFICE E ATTORE GESTIONE ISTANZA È WRITE_LOCK,
        // E' STATO CLICCATO INTEGRA ALLEGATI DALLE AZIONI AVANZATE DELLA RICERCA,
        // L'ATTORE GESTORE ISTANZA DIVENTA QDR_ALLEGATO ABILITANDO IL CARICA ALLEGATI
        if (
          this.isFrontOffice &&
          attoreGestione === this.gestioneEnum.WRITE_LOCK &&
          (this.oggAppBtnIntegraAllegati || this.oggAppBtnPerfezionaAllegati) &&
          (this.istanza.stato_istanza.codice_stato_istanza ===
            StatoIstanzaEnum.PERF_ALLEGATI ||
            this.istanza.stato_istanza.codice_stato_istanza ===
              StatoIstanzaEnum.RIC_INTEGR ||
            this.istanza.stato_istanza.codice_stato_istanza ===
              StatoIstanzaEnum.RIC_INTEGR_SUCC)
        ) {
          this.attoreGestioneIstanza = this.codTipoQuadro;
        }

        // Gestisco il flag integrazione allegati
        this.flgIntegrazioneAllegati = attoreGestione === this.codTipoQuadro;
        //i pulsanti dello stepper sono visibili solo se flgIntegrazioneAllegati è false
        this.setVisibilityButtonBack(this.flgIntegrazioneAllegati == false);
        this.setVisibilityButtonNext(this.flgIntegrazioneAllegati == false);

        this.setPage();
        this.setCategorieObbligatorie();
        

        // TODO Capire come incastrare, credo su evento
        // this.readonlyAllegati = this.allegatiIntegrazioniService.getReadonlyAllegati(this.istanza.stato_istanza.codice_stato_istanza as StatoIstanzaEnum);
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
      }
    );
  }

  setCategorieObbligatorie() {
    if (this.allegatiObbligatoriPerVincoli) {
      this.allegatiObbligatoriPerVincoli.forEach((tipo) => {
        const index = this.fullListTipiAllegato.findIndex(
          (element) =>
            element.tipologia_allegato.id_tipologia_allegato ===
            tipo.tipologia_allegato.id_tipologia_allegato
        );
        if (index > -1) {
          this.fullListTipiAllegato[index].flg_obbligo = true;
        }
      });
    }

    this.categorie.forEach((categoria) => {
      categoria.flg_obbligo = this.fullListTipiAllegato
        .filter(
          (tipo) =>
            tipo.tipologia_allegato.categoria_allegato.id_categoria_allegato ===
            categoria.id_categoria_allegato
        )
        .some((element) => element.flg_obbligo === true);

      if (categoria.flg_obbligo) {
        this.totalMandatory++;
      }
    });

    this.setAsterisk = this.totalMandatory > 1;
    this.totalMandatory = 0;

    this.buildFormAllegati();
  }

  onChangeCategoria() {
    const categSelected = this.formAllegati.get('categoria').value;
    this.tipiAllegato = this.fullListTipiAllegato.filter(
      (tipo) =>
        tipo.tipologia_allegato.categoria_allegato.id_categoria_allegato ===
        categSelected.id_categoria_allegato
    );
    // Quando cambio categoria il pulsante riferimento atto va disabilitato
    this.disableBtnRiferimentiAtto = false;
    // e cancellare le additionalInfo riferimenti atto
    this.onDeleteRiferimentiAtto();

    this.formAllegati.get('tipologia').reset();
  }

  onChangeTipologia() {
    this.formAllegati.get('riservato').enable();
    this.tipologiaSelezionata = this.formAllegati.get('tipologia')?.value;
    this.formAllegati
      .get('riservato')
      .setValue(this.tipologiaSelezionata?.flg_riservato);
    if (this.tipologiaSelezionata?.flg_riservato && this.isFrontOffice) {
      this.formAllegati.get('riservato').disable();
    }

    this.formAllegati.get('nota').clearValidators();
    if (this.tipologiaSelezionata?.flg_nota) {
      this.formAllegati
        .get('nota')
        .setValidators([
          Validators.required,
          Validators.maxLength(this.maxNote),
        ]);
    } else {
      this.formAllegati
        .get('nota')
        .setValidators([Validators.maxLength(this.maxNote)]);
    }
    this.formAllegati.get('nota').updateValueAndValidity();

    this.disableBtnRiferimentiAtto =
      !this.isFrontOffice &&
      this.tipologiaSelezionata?.tipologia_allegato.flg_atto;
    if (!this.disableBtnRiferimentiAtto) {
      this.onDeleteRiferimentiAtto();
    }
    // MOCK
    // this.disableBtnRiferimentiAtto = true;
  }

  compareCategoria(c1: CategoriaAllegato, c2: CategoriaAllegato) {
    return c1 && c2 && c1.cod_categoria_allegato === c2.cod_categoria_allegato;
  }

  compareTipologia(t1: TipoAllegato, t2: TipoAllegato) {
    return (
      t1 &&
      t2 &&
      t1.tipologia_allegato?.cod_tipologia_allegato ===
        t2.tipologia_allegato?.cod_tipologia_allegato
    );
  }

  private buildFormAllegati() {
    this.numProtocolloMaxLength = this.maxLengths.find(
      (i) => i.key === 'numProtocollo'
    ).max;

    this.formAllegati = this.fb.group({
      categoria: [null, { validators: [Validators.required] }],
      tipologia: [null, { validators: [Validators.required] }],
      riservato: [false, { validators: [Validators.required] }],
      nota: ['', { validators: [] }],
      dataProtocolloAllegato: [null, [this.maxDateValidator]],
      numProtocolloAllegato: [
        '',
        { validators: [Validators.maxLength(this.numProtocolloMaxLength)] },
      ],
    });

    // TODO: Refactor con jira che aggiorna logica attoreGestioneIstanza
    if (
      (this.attoreGestioneIstanza === this.gestioneEnum.WRITE ||
        this.attoreGestioneIstanza === this.codQuadro) &&
      !this.readonlyAllegati
    ) {
      this.enabledFormAllegati();
    } else {
      if (
        this.attoreGestioneIstanza === this.gestioneEnum.READ_ONLY ||
        this.attoreGestioneIstanza === this.gestioneEnum.WRITE_LOCK
      ) {
        this.readonlyAllegati = true;
      } else {
        this.enabledFormAllegati(false);
      }
    }

    this.formAllegati.valueChanges.subscribe((x) => {
      // formAllegati onChange setto la variabile di ambiente dello stepper changed
      this.changed = this.formAllegati.dirty;
    });

    // changed
  }

  enabledFormAllegati(enable: boolean = true) {
    if (!this.formAllegati) return;

    if (enable) {
      this.formAllegati.get('categoria').enable();
      this.formAllegati.get('tipologia').enable();
      this.formAllegati.get('riservato').enable();
      this.formAllegati.get('dataProtocolloAllegato').enable();
      this.formAllegati.get('numProtocolloAllegato').enable();
      this.formAllegati.get('nota').enable();
    } else {
      this.formAllegati.get('categoria').disable();
      this.formAllegati.get('tipologia').disable();
      this.formAllegati.get('riservato').disable();
      this.formAllegati.get('dataProtocolloAllegato').disable();
      this.formAllegati.get('numProtocolloAllegato').disable();
      this.formAllegati.get('nota').disable();
    }
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    // Verifico che la data sia definita
    if (!control.value) {
      // Non c'è da verificare la data
      return null;
    }

    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
  }

  onInserisciRiferimentiAtto() {
    const modalRef = this.modalService.open(InfoProtocolloAttoModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'md',
    });

    modalRef.componentInstance.type = 'A';
    modalRef.componentInstance.tempValues = this.additionalInfo?.atto;
    modalRef.componentInstance.maxLengths = this.maxLengths;

    modalRef.result
      .then(({ data, numero, titolo, autore }) => {
        if (!this.additionalInfo) {
          this.additionalInfo = {};
        }

        this.additionalInfo.atto = {
          data,
          numero,
          titolo,
          autore,
        };
      })
      .catch((err) => {});
  }

  onDeleteRiferimentiAtto() {
    if (this.additionalInfo?.atto) {
      this.additionalInfo.atto = null;
    }
  }

  onInserisciAllegato(fileInput) {
    if (!this.formAllegati.valid) {
      if (
        !this.formAllegati.get('categoria').value ||
        !this.formAllegati.get('tipologia').value
      ) {
        this._messageService.showMessage(
          'E018',
          'containerUploadDocumenti',
          false
        );
      } else {
        this._messageService.showMessage(
          'E017',
          'containerUploadDocumenti',
          false
        );
      }
      return;
    }

    fileInput.click();
  }

  inserisciAllegato(files) {
    // SCRIVA-1041
    this._messageService.clearAnchorMessages('containerUploadDocumenti');
    const auo: AllegatiUploadOptions = {
      idIstanza: this.idIstanza,
      maxFileSize: this.maxFileSize,
      tipologiaSelezionata: this.tipologiaSelezionata,
      flgRiservato: this.formAllegati.get('riservato')?.value,
      numProtocolloAllegato: this.formAllegati.get('numProtocolloAllegato')
        ?.value,
      dataProtocolloAllegato: this.formAllegati.get('dataProtocolloAllegato')
        ?.value,
      nota: this.formAllegati.get('nota')?.value,
      allegatoAdditionalInfo: this.additionalInfo,
      tipoIntegrazione: this.tipoIntegrazione,
      fileExtensionsArray: this.fileExtensionsArray,
      targetMessage: 'containerUploadDocumenti',
    };
    this.allegatiUploaderProgressService.allegatiUploadOptions = auo;
    this.allegatiUploaderProgressService.inserisciAllegato(files);
  }

  /**
   * metodo per resettare il form e ricaricare la lista se serve
   * @param getAllegatiQuadro boolean
   * @param skipGeneraDocumento boolean che permette di saltare la chiamata alla generazione/download dei documenti. Per defaul è: false.
   */
  annullaInserisciAllegato(
    getAllegatiQuadro: boolean = false,
    skipGeneraDocumento: boolean = false
  ) {
    this.formAllegati.reset();
    this.additionalInfo = null;
    if (getAllegatiQuadro) {
      this.getAllegatiQuadro(true, skipGeneraDocumento);
    }
  }

  setPage() {
    this.spinner.show();
    // SCRIVA-1379
    this.allegatiService
      .getAllegatiIstanzaSearch(this.idIstanza, { flg_canc_logica: true })
      .subscribe(
        (response) => {
          this.rows = [...response];

          let element;
          if (this.rows?.length > 0) {
            element = document.getElementById('containerDocumenti');
          } else {
            element = document.getElementById('containerUploadDocumenti');
          }
          if (element) {
            element.scrollIntoView();
          }

          if (this.showSuccessMessageIntegrazione) {
            this._messageService.showMessage('P008', 'divBtnConferma', false);
            this.showSuccessMessageIntegrazione = false;
            setTimeout(() => {
              this.router.navigate(['/ricerca']);
            }, 1500);
          } else if (this.flgIntegrazioneAllegati) {
            this.checkBtnConferma();
          }

          this.spinner.hide();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
        }
      );
  }

  /**
   * Funzione che apre la modale per la modifica dati relativi all'allegato in input.
   * @param allegato Allegato con le informazioni dell'allegato da modificare.
   * @param readonly boolean come flag che forza la modale in sola lettura. Per default è: false.
   */
  modificaAllegato(allegato: Allegato, readonly: boolean = false) {
    const modalRef = this.modalService.open(EditAllegatoModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'xl',
    });

    modalRef.componentInstance.allegatoInEdit = { ...allegato };
    modalRef.componentInstance.categorie = this.categorie;
    modalRef.componentInstance.fullListTipiAllegato = this.fullListTipiAllegato;
    modalRef.componentInstance.maxNote = this.maxNote;
    modalRef.componentInstance.maxLengths = this.maxLengths;
    modalRef.componentInstance.isFrontOffice = this.isFrontOffice;
    modalRef.componentInstance.oggAppBtnProtocolla = this.oggAppBtnProtocolla;
    modalRef.componentInstance.visualizzazione = readonly;

    modalRef.result
      .then((metaData: Allegato) => {
        this.spinner.show();
        this.allegatiService.updateAllegati(metaData).subscribe(
          (res) => {
            this.formAllegati.reset();
            this.getAllegatiQuadro();
          },
          (err) => {
            this.showErrorsQuadroConCodeENoCode(
              err,
              'containerUploadDocumenti'
            );
          }
        );
      })
      .catch((err) => {});
  }

  checkBtnConferma() {
    this.allegatiService.getAllAllegatiIstanza(this.idIstanza).subscribe(
      (res) => {
        const filteredList = res.filter(
          (elem) =>
            !!elem.tipo_integra_allegato?.id_tipo_integra_allegato &&
            !elem.data_integrazione
        );
        this.showBtnConferma = filteredList.length > 0;
      },
      (err) => {
        if (err.status !== 404) {
          this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
        }
      }
    );
  }

  /**
   * @override
   */
  protected getHelpList() {
    const contesto = this.codContesto + '.' + this.codTipoQuadro;

    this.helpService.getHelpByChiave(this.componente + contesto).subscribe(
      (res) => {
        this.helpList = res;
        this.helpListLoaded = true;
        this.updateHelpBannerText('testo_formato_file');
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
      }
    );
  }

  /**
   * @override
   */
  protected onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.flgContinue = true;
      this.isStepValid().subscribe((isValid)=>{
        if (isValid) {
          // dopo aver ottenuto i dati chiama il salva...
          this.getAllegatiQuadro();
        }
      })
    } else {
      this.setStepCompletedEmit('AllegatiComponent', true);
      this.goToStep(this.stepIndex);
    }
  }

  
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    const checkCategorieObbligatorie =
      this.allegatiService.checkCategorieObbligatorie(this.idIstanza);
    const checkCategorieAdempimento =
      this.allegatiService.checkCategorieAdempimento(this.idIstanza);

    this.spinner.show();
    return forkJoin([
      checkCategorieObbligatorie,
      checkCategorieAdempimento,
    ]).pipe(
      switchMap((res) => {
        if (res[0].length > 0 || res[1].length > 0) {
          this.showAlertBanner = true;
          //vengono mostrati i messaggi di errore
          this.checkAllegatiLists(res[0], res[1]);
          this.spinner.hide();
        }
        this.showAlertBanner = false;
        this.setStepCompletedEmit('AllegatiComponent', true);
        if ((this.flgContinue && (res[0].length > 0) || res[1].length > 0)) {
          this.spinner.hide();
          // checkAlert apre un messaggio se viene cliccato conferma ritorna true quindi può salvare
          return from(this.checkAlert(res[0].length > 0));
        }
        this.spinner.hide();
        return of(true);
      }),
      catchError((err) => {
        this.spinner.hide();
        this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
        return of(false);
      })
    );
  }

  checkAlert(flgMissingMandatory: boolean): Promise<boolean>  {
    this.spinner.hide();
    const code = flgMissingMandatory ? 'A019' : 'A020';
    return new Promise((resolve) => {
    this._messageService.showConfirmation({
      title: 'Attenzione',
      codMess: code,
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {
            setTimeout(() => {
              const cardElement = document.getElementById(
                'containerUploadDocumenti'
              );
              cardElement.scrollIntoView();
            }, 0);
            resolve(false); // Restituisce false se l'utente annulla
          },
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            resolve(true); // Restituisce true se l'utente conferma
          },
        },
      ],
    });
  })
  }

  /**
   * Funzione che gestisce l'aggiornamento e la gestione del quadro allegati.
   * @param showSpinner boolean che definisce se deve essere lanciato lo spinner di caricamento. Per default è: true.
   * @param skipGeneraDocumento boolean che permette di saltare la chiamata alla generazione/download dei documenti. Per defaul è: false.
   */
  getAllegatiQuadro(
    showSpinner: boolean = true,
    skipGeneraDocumento: boolean = false
  ) {
    // Definisco una chiamata per la gestione della generazione/download documenti
    let firstCall$: Observable<any> = of(null);

    // Verifico la condizione di skip della chiamata
    if (!skipGeneraDocumento) {
      // La chiamata non è da skippare a priori, gestisco la logica sui flag di continuazione
      if (!this.flgContinue && !this.flgContinueIntegrazione) {
        // Entrambi i flag continuazione sono a false, genero i documenti di sistema
        firstCall$ = this.allegatiService.generaDocumentoDiSistema(
          this.idIstanza
        );
        // #
      }
      // #
    }

    // // In caso di upload in fase di integrazione non devo rigenerare elenco allegati
    // firstCall$ =
    //   this.flgContinue || this.flgContinueIntegrazione
    //     ? of(null)
    //     : this.allegatiService.generaDocumentoDiSistema(this.idIstanza);

    if (showSpinner) {
      this.spinner.show();
    }

    this.allegatiService
      .getAllegatiIstanzaSearch(this.idIstanza)
      .pipe(
        switchMap((allegati) => {
          const allegatiUtente = allegati.filter(
            (doc) =>
              doc.tipologia_allegato.categoria_allegato
                .cod_categoria_allegato !== 'SYS' &&
              doc.tipologia_allegato.categoria_allegato
                .cod_categoria_allegato !== 'INTEG' &&
              doc.flg_cancellato === false
          );

          if (allegatiUtente.length > 0) return firstCall$;
          const elencoAllegati = allegati.filter(
            (elem) =>
              elem.tipologia_allegato.cod_tipologia_allegato ===
                CodTipologiaAllegato.elencoAllegati &&
              elem.flg_cancellato === false
          );
          if (elencoAllegati?.length > 0) {
            return this.allegatiService.deleteAllegatoByUuid(
              elencoAllegati[0].uuid_index
            );
          }
          return of(null);
        }),
        switchMap((ret: any) =>
          // SCRIVA-1379
          this.allegatiService.getAllegatiIstanzaSearch(this.idIstanza)
        )
      )
      .subscribe(
        (res) => {
          this.saveData(res);
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
        }
      );
  }

  checkAllegatiLists(list1, list2) {
    if (list1.length > 0) {
      this.spinner.hide();
      let list = '';
      list1.forEach(
        (category) => (list = list + category.des_categoria_allegato + '<br>')
      );
      const mess = this._messageService.messaggi.find(
        (m) => m.cod_messaggio === 'E023'
      );
      this.alertText = mess.des_testo_messaggio.replace(
        ' [{PH_CATEGORIA}].',
        '<br>' + list
      );
      return;
    }

    if (list2.length > 0) {
      this.spinner.hide();
      let list = '';
      list2.forEach(
        (category) => (list = list + category.des_categoria_allegato + '<br>')
      );
      const mess = this._messageService.messaggi.find(
        (m) => m.cod_messaggio === 'E024'
      );
      this.alertText = mess.des_testo_messaggio.replace(
        ' [{PH_CATEGORIA}].',
        '<br>' + list
      );
      return;
    }
  }

  /**
   * Funzione che richiama la creazione del body della richiesta e richiama il salvataggio del quadro
   * @param allegatiFullList
   */
  private saveData(allegatiFullList: Allegato[]) {
    const docSYS = allegatiFullList.filter(
      (doc) =>
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato ===
          'SYS' &&
        doc.tipologia_allegato.cod_tipologia_allegato.includes('ELENCO_')
    );
    const allegatiUtente = allegatiFullList.filter(
      (doc) =>
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato !==
          'SYS' &&
        doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato !==
          'INTEG' &&
        doc.flg_cancellato === false
    );

    // if (docSYS.length === 0 && this.isFrontOffice) {
    //   this.messageService.showMessage('E100', 'containerUploadDocumenti', false);
    //   console.log('# Documenti di sistema non trovati.');
    //   return;
    // }

    let categoryList = [];
    allegatiUtente.forEach((doc) =>
      categoryList.push(
        doc.tipologia_allegato.categoria_allegato.des_categoria_allegato
      )
    );
    categoryList = categoryList.filter(
      (cat, index) => categoryList.indexOf(cat) === index
    );

    const riepilogoAllegati = [];
    categoryList.forEach((cat) => {
      riepilogoAllegati.push({
        categoria: cat,
        count: 0,
      });
    });
    riepilogoAllegati.forEach((el) => {
      allegatiUtente.forEach((allegato) => {
        if (
          allegato.tipologia_allegato.categoria_allegato
            .des_categoria_allegato === el.categoria
        ) {
          el.count++;
        }
      });
    });

    const documentiDiSistema = [];
    docSYS.forEach((doc) => {
      const dataFullSplit = doc.data_upload.split(' ');
      const dataSplit = dataFullSplit[0].split('-');
      const dataUpload = dataSplit[2] + '/' + dataSplit[1] + '/' + dataSplit[0];
      const timeUpload = dataFullSplit[1];

      documentiDiSistema.push({
        nomeDocumento: doc.nome_allegato,
        dataDocumento: dataUpload + ' ' + timeUpload,
        codAllegato: doc.cod_allegato,
        codTipologia: doc.tipologia_allegato.cod_tipologia_allegato,
      });
    });

    const datiDaSalvare = {
      allegatiUtente,
      riepilogoAllegati,
      documentiDiSistema,
    };
    this.salvaDatiQuadro(datiDaSalvare);
  }

  salvaDatiQuadro(datiDaSalvare: any) {
    const configs: IConfigsSaveDataQuadro = {
      onAvantiFlag: this.flgContinue,
      datiQuadro: {
        numeroAllegatiUtente: datiDaSalvare.allegatiUtente.length,
        riepilogoAllegati: datiDaSalvare.riepilogoAllegati,
        documentiDiSistema: datiDaSalvare.documentiDiSistema,
      },
      showSpinner: true,
      isPutDatiQuadro: this.saveWithPut,
      isPutDatiRiepilogo: true,
      datiRiepilogo: this.qdr_riepilogo,
    };

    this.saveDataQuadro(configs).subscribe(
      (res) => {
        this.saveWithPut = true;
        if (configs.onAvantiFlag) {
          this.flgContinue = false;
          this.goToStep(this.stepIndex);
        } else {
          this.setPage();
        }
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
      }
    );
  }

  /**
   *
   * @param configs
   * @returns Observable<any>
   * @override
   */
  protected saveDataQuadro(configs: IConfigsSaveDataQuadro): Observable<any> {
    // estraggo le configurazioni
    const { showSpinner, isPutDatiQuadro, isPutDatiRiepilogo, datiQuadro } =
      configs;
    if (showSpinner) {
      this.spinner.show();
    }
    const qdrRiepilogoConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro,
    };
    this.qdr_riepilogo = this.prepareDatiRiepilogo(qdrRiepilogoConfigs);
    //differisce dalla generica perché vengono azzerati i dati quadro
    //(da controllare, è un possibile bug)
    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: {},
      datiRiepilogo: this.qdr_riepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      isPutDatiQuadro,
      isPutDatiRiepilogo,
      showSpinner
    );
  }

  reloadAllegati() {
    this.spinner.show();
    // SCRIVA-1379
    this.allegatiService
      .getAllegatiIstanzaSearch(this.idIstanza, { flg_canc_logica: true })
      .subscribe(
        (response) => {
          this.rows = [...response];

          let element;
          if (this.rows?.length > 0) {
            element = document.getElementById('containerDocumenti');
          } else {
            element = document.getElementById('containerUploadDocumenti');
          }
          if (element) {
            element.scrollIntoView();
          }
          this.spinner.hide();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'containerUploadDocumenti');
        }
      );
  }

  /**
   * Funzione collegata all'event emitter del componente che gestisce le informazioni degli allegati.
   * @param event { action: any; args?: any } con le informazioni emesse dall'evento.
   */
  emitEventChild(event: { action: any; args?: any }) {
    // Estraggo dall'input le informazioni
    const { action, args } = event ?? {};
    // Verifico se esiste effettivamente l'informazione per l'azione da gestire
    if (!action) {
      // Non è definito il tipo di operazione da gestire, blocco il flusso
      return;
    }

    // Definisco le configurazioni per la gestione delle chiamate
    const showSpin: boolean = true;
    const skipDocGen: boolean = true;

    // Verifico quale evento è stato richiesto di gestire
    switch (action) {
      case 'annullaInserisciAllegatoReload':
        // Richiamo la funzione di gestione
        this.annullaInserisciAllegato(showSpin, skipDocGen);
        break;
      // #
      case 'reloadAllegati':
        // Richiamo la funzione di gestione
        this.getAllegatiQuadro(showSpin);
        break;
      // #
      case CardRiepilogoAllegatiActsion.modificaAllegato:
        // Richiamo la funzione di gestione
        this.modificaAllegato(args);
        break;
      // #
      case CardRiepilogoAllegatiActsion.visualizzaAllegato:
        // Richiamo la funzione di gestione
        this.modificaAllegato(event.args, true);
        break;
      // #
    }
  }

  /**
   * #################################
   * FUNZIONI DI AGGIORNAMENTO ISTANZA
   * #################################
   */

  /**
   * Funzione che aggiorna le informazioni correlate al tipo integrazione.
   * Verranno scaricate le informazioni per definire il nuovo tipo d'integrazione attivo.
   * @param istanza Istanza con le infomazioni per lo scarico dati.
   */
  private updateTipoIntegrazione(istanza: Istanza, adempimento: Adempimento) {
    // Recupero le informazioni "statiche" per lo scarico dati
    const codAdempimento: string = adempimento?.cod_adempimento;
    const tipoIntegrazione = TipoInfoAdempimento.tipoIntegrazione;
    const codStatoIstanza: string =
      istanza?.stato_istanza?.codice_stato_istanza;

    // Richiamo il servizio per lo scarico dati delle configurazioni
    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        codAdempimento,
        tipoIntegrazione,
        codStatoIstanza
      )
      .subscribe({
        next: (configAdempimento: ConfigAdempimento[]) => {
          // Cerco di recuperare dall'array dati il tipo adempimento
          let configAdIntegrazione: ConfigAdempimento;
          configAdIntegrazione = configAdempimento?.find(
            (configA: ConfigAdempimento) => {
              // Verifico la chiave della configurazione con il codice istanza
              return configA.chiave === codStatoIstanza;
              // #
            }
          );

          // Verifico se ho trovato corrispondenza dati
          if (configAdIntegrazione) {
            // L'oggetto è stato trovato, recupero il parametro "valore"
            this.tipoIntegrazione = configAdIntegrazione.valore;
          }
          // #
        },
        error: (e: any) => {
          // Definisco le informazioni per visualizzare l'alert box nell'app componente
          const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
          const target = 'containerUploadDocumenti';
          const autoFade = e?.error?.code ? false : true;
          // Visualizzo il messaggio
          this._messageService.showMessage(code, target, autoFade);
        },
      });
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  set readonlyAllegati(v: boolean) {
    this._readonlyAllegati = v;
    // il readonly si propaga al form allegati
    this.enabledFormAllegati(!this.readonlyAllegati);
  }

  get readonlyAllegati(): boolean {
    return this._readonlyAllegati;
  }
}
