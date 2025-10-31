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
  Component,
  Inject,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { Adempimento, Istanza, StepConfig } from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
  VincoliAutService,
} from 'src/app/shared/services';
import {
  AttoreGestioneIstanzaEnum,
  TipoEventoEnum,
} from 'src/app/shared/utils';
import { IstanzaVincoloAut, VincoloAutorizza } from '../../../models';
import { ObjectHelper } from '../../../utils';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo, RequestSaveBodyQuadro
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-dettaglio-via-statale',
  templateUrl: './dettaglio-via-statale.component.html',
  styleUrls: ['./dettaglio-via-statale.component.scss'],
})
export class DettaglioViaStataleComponent
  extends StepperIstanzaComponent
  implements OnInit
{
  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  // @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  // @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  codMaschera = '.MSCR021F';

  // funzionario: FunzionarioAutorizzato;

  codAdempimento: string;

  istanza: Istanza;
  qdr_dettaglio;
  qdr_riepilogo;

  autorizzazioneL443: VincoloAutorizza;

  tipoEventoEnum = TipoEventoEnum;
  gestioneEnum = AttoreGestioneIstanzaEnum;

  dettaglioForm: FormGroup;
  isFormReady = false;
  ColumnMode = ColumnMode;
  today = new Date();
  saveWithPut = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private adempimentoService: AdempimentoService,
    private vincoliAutService: VincoliAutService,
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
    this.setVisibilityButtonNext(true);
    this.codAdempimento = this.route.snapshot.paramMap.get('codAdempimento');
    // this.funzionario = this.authStoreService.getFunzionario();
  }

  ngOnInit() {
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.buildForm();
    this._getAdempimento();
  }

  private _getAdempimento() {
    this.adempimentoService
      .getAdempimentoByCode(this.codAdempimento)
      .pipe(
        catchError((error) => {
          this.showErrorsQuadroConCodeENoCode(error, 'dettaglioContainer');
          return throwError(error);
        }),
        switchMap((resp: Adempimento) => {
          const getAutorizz = this.vincoliAutService.getVincoliAutByAdempimento(
            resp.id_adempimento
          );

          // Recupero il codice del tipo adimpimento
          const codTipAdempi: string =
            resp.tipo_adempimento.cod_tipo_adempimento;
          const codAdempi: string = resp.cod_adempimento;
          // Imposto il codice contesto per l'helper
          this.helpService.setCodContesto(`.${codTipAdempi}.${codAdempi}`);

          // Recupero la lista di helper per chiave
          const getHelpList = this.helpService.getHelpByChiave(
            `${this.componente}.${codTipAdempi}.${this.codAdempimento}.${this.codQuadro}`
          );
          return forkJoin([getAutorizz, getHelpList]).pipe(
            catchError((err) => {
              this.showErrorsQuadroConCodeENoCode(err, 'dettaglioContainer');
              return throwError(err);
            })
          );
        })
      )
      .subscribe((res) => {
        this.autorizzazioneL443 = res[0].find(
          (aut) => aut.cod_vincolo_autorizza === 'L443'
        );
        this.helpList = res[1];
        this.loadData();
      });
  }

  buildForm() {
    this.dettaglioForm = this.fb.group({
      numeroProtocollazione: null,
      dataProtocollazione: null,
      scadenzaConsultazione: null,
      scadenzaProcedimento: null,
      urlElaborati: null,
      direzioneCompetente: null,
      rappresentanteNucleo: null,
      responsabileProc: null,
      flgLegge443: [false],
    });

    if (this.codAdempimento === 'VRN') {
      this.dettaglioForm.addControl('dataConsegna', new FormControl(null));
    }
    if (this.codAdempimento === 'PAR') {
      this.dettaglioForm.addControl('dataPubblicazione', new FormControl(null));
    }

    this.isFormReady = true;
    this.spinner.hide(); //todo
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.dettaglioForm.enable();
    } else {
      this.dettaglioForm.disable();
    }

    this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
      (res) => {
        this.istanza = res;
        this.qdr_riepilogo = JSON.parse(res.json_data).QDR_RIEPILOGO;

        const nestedBlock = this.codQuadro !== this.codTipoQuadro;
        this.qdr_dettaglio = nestedBlock
          ? JSON.parse(res.json_data)[this.codTipoQuadro][this.codQuadro]
          : JSON.parse(res.json_data)[this.codTipoQuadro];
        if (this.qdr_dettaglio) {
          this.saveWithPut = true;
          this.dettaglioForm
            .get('flgLegge443')
            .setValue(!!this.qdr_dettaglio.autorizzazioni);
          this.setFormValues();
        }

        this.setStepCompletion();
        this.spinner.hide();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'dettaglioContainer');
      }
    );
  }

  setFormValues() {
    if (this.qdr_dettaglio.js_procedimentoStatale) {
      this.dettaglioForm
        .get('urlElaborati')
        .setValue(
          this.qdr_dettaglio.js_procedimentoStatale.url_portale_esterno
        );
      if (this.codAdempimento === 'VRN') {
        this.dettaglioForm
          .get('dataConsegna')
          .setValue(
            this.qdr_dettaglio.js_procedimentoStatale.data_consegna_doc_esterna
          );
      }
      if (this.codAdempimento === 'PAR') {
        this.dettaglioForm
          .get('dataPubblicazione')
          .setValue(
            this.qdr_dettaglio.js_procedimentoStatale.data_pubblicazione_esterna
          );
      }
    }

    if (this.qdr_dettaglio.js_organoTecnico) {
      this.dettaglioForm
        .get('direzioneCompetente')
        .setValue(this.qdr_dettaglio.js_organoTecnico.des_direzione_competente);
      this.dettaglioForm
        .get('rappresentanteNucleo')
        .setValue(
          this.qdr_dettaglio.js_organoTecnico.des_rappresentante_nucleo
        );
      this.dettaglioForm
        .get('responsabileProc')
        .setValue(
          this.qdr_dettaglio.js_organoTecnico.des_responsabile_procedimento
        );
    }
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpList.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'MDL' &&
          help.chiave_help.includes(chiave)
      )?.des_testo_help || 'Help non trovato...';

    this._messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  onAnnulla() {
    this.dettaglioForm.reset();
    this.setStepCompletion();
  }

  setStepCompletion() {
    this.isStepValid().subscribe((isValid: boolean) => {
      this.setStepCompletedEmit('DettaglioViaStataleComponent', isValid);
    });
   
  }

  /**
   * @override
   * @param showErrors
   * @returns
   */
  protected isStepValid(showErrors = false): Observable<boolean> {
    let check = true;

    // give error if checkbox is not checked or if you have 'Scadenza consultazione al pubblico' but no Istanza Nota
    if (this.dettaglioForm.invalid) {
      check = false;
      if (showErrors) {
        this._messageService.showMessage('E018', 'dettaglioContainer', false);
      }
    }

    const dataConsegna = this.dettaglioForm.get('dataConsegna')?.value || null;
    if (dataConsegna) {
      const date = new Date(dataConsegna).getTime();
      if (date > this.today.getTime()) {
        check = false;
        if (showErrors) {
          this._messageService.showMessage('E067', 'divAnnulla', true);
        }
      }
    }
    return of(check);
  }

  buildRequestBody(onAvantiFlag = false) {
    const data: any = {};

    if (!onAvantiFlag) {
      const dataRiepilogo: any = ObjectHelper.cloneObject(data);
      this.salvaDatiQuadro(data, dataRiepilogo);
    } else {
      data.js_procedimentoStatale = {
        url_portale_esterno: this.dettaglioForm.get('urlElaborati').value,
      };
      if (this.codAdempimento === 'VRN') {
        data.js_procedimentoStatale.data_consegna_doc_esterna =
          this.dettaglioForm.get('dataConsegna').value || null;
      }
      if (this.codAdempimento === 'PAR') {
        data.js_procedimentoStatale.data_pubblicazione_esterna =
          this.dettaglioForm.get('dataPubblicazione').value || null;
      }
      // todo: check what else is needed for Riepilogo
      const dataRiepilogo: any = ObjectHelper.cloneObject(data);
      dataRiepilogo.data_fine_osservazioni =
        this.istanza.data_fine_osservazioni;

      let autorizzazioneCall$ = of(null);
      const isPresent = this.qdr_dettaglio?.autorizzazioni?.length > 0;
      if (this.dettaglioForm.get('flgLegge443').value) {
        dataRiepilogo.autorizzazioniL443 = true;
        if (!isPresent) {
          const autorizzazione: IstanzaVincoloAut = {
            id_istanza: this.idIstanza,
            des_vincolo_calcolato: null,
            des_ente_utente: null,
            des_email_pec_ente_utente: null,
            vincolo_autorizza: this.autorizzazioneL443,
          };
          autorizzazioneCall$ =
            this.vincoliAutService.postVincoliAut(autorizzazione);
        } else {
          data.autorizzazioni = this.qdr_dettaglio.autorizzazioni;
        }
      } else {
        dataRiepilogo.autorizzazioniL443 = false;
        if (isPresent) {
          autorizzazioneCall$ = this.vincoliAutService.deleteVincoloAut(
            this.qdr_dettaglio.autorizzazioni[0].gestUID
          );
        }
      }

      let pubblicaIstanza$ = of(null);
      const dataPubblicazioneMillis = this.istanza.data_pubblicazione
        ? new Date(this.istanza.data_pubblicazione.split(' ')[0]).getTime()
        : null;

      if (dataPubblicazioneMillis) {
        pubblicaIstanza$ = this.istanzaService.pubblicaIstanza(this.idIstanza);
      }

      // gestione "forzata" aggiornamento autorizzazioni sul json_data dell'istanza
      const jsonDataIstanza = JSON.parse(this.istanza.json_data);
      if (jsonDataIstanza.QDR_DETT_PROCED) {
        if (!dataRiepilogo.autorizzazioniL443) {
          delete jsonDataIstanza.QDR_DETT_PROCED.autorizzazioni;
          this.istanza.json_data = JSON.stringify(jsonDataIstanza);
        }
      }

      const firstTimeRiepilogo = !this.istanza.cod_pratica;
      this._salvaIstanza(
        pubblicaIstanza$,
        autorizzazioneCall$,
        firstTimeRiepilogo,
        data,
        dataRiepilogo
      );
    }
  }

  private _salvaIstanza(
    pubblicaIstanza$: Observable<any>,
    autorizzazioneCall$: Observable<any>,
    firstTimeRiepilogo: boolean,
    data: any,
    dataRiepilogo: any
  ) {
    this.istanzaService
      .salvaIstanza(this.istanza)
      .pipe(
        // maybe this tap() is not needed... we're going to the next step anyway
        tap((istanza) => (this.istanza = istanza)),
        switchMap(() => pubblicaIstanza$),
        switchMap(() => autorizzazioneCall$)
      )
      .subscribe(
        (res) => {
          if (res) {
            data.autorizzazioni = [res];
          }
          this.istanzaService.messageSuccessBO = firstTimeRiepilogo;
          this.salvaDatiQuadro(data, dataRiepilogo);
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'dettaglioContainer');
        }
      );
  }

  /**
   * Funzione per creare body per il salvataggio del quadro
   * @returns RequestSaveBodyQuadro contenente le info del quadro e del riepilogo
   *
   */
  private prepareDataQuadroERiepilogo(
    configs: IPrepareDatiRiepilogo
  ): RequestSaveBodyQuadro {
    const data = configs?.datiQuadro;
    const dataRiepilogo = configs?.datiRiepilogo;

    const nestedBlock = this.codQuadro !== this.codTipoQuadro;
    let dataQuadro: any = {};

    if (nestedBlock) {
      if (!this.qdr_riepilogo[this.codTipoQuadro]) {
        this.qdr_riepilogo[this.codTipoQuadro] = {};
      }
      dataQuadro[this.codQuadro] = data;
      this.qdr_riepilogo[this.codTipoQuadro][this.codQuadro] = dataRiepilogo;
    } else {
      dataQuadro = data;
      this.qdr_riepilogo[this.codTipoQuadro] = dataRiepilogo;
    }
    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: dataQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };
    return this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);
  }

  salvaDatiQuadro(data: any, dataRiepilogo: any) {
    // Preparo le informazioni per il salvataggio dei dati
    const configs: IConfigsSaveQuadro = {
      datiQuadro: data,
      datiRiepilogo: dataRiepilogo,
    };

    this.saveDataQuadro(configs).subscribe(
      (res: any) => {
        this.saveWithPut = true;
        // Attivo le logiche di gestione per proseguire con le logiche dello stepper
        this.goToStep(this.stepIndex);
        // #
      },
      (err) => {
        // Gestisco l'errore in entrata per il componente
        this.showErrorsQuadroConCodeENoCode(err, 'dettaglioContainer');
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
    const requestConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro: configs.datiQuadro,
      datiRiepilogo: configs.datiRiepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.prepareDataQuadroERiepilogo(requestConfigs);
      this.spinner.show()
    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      this.saveWithPut,
      true,
      true
    );
  }

  /**
   * @override
   */
  protected onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.isStepValid().subscribe((isValid: boolean) => {
        if (isValid) {
          this.spinner.show();
          this.buildRequestBody(true);
        }
      });
    
    } else {
      this.goToStep(this.stepIndex);
    }
  }
}
