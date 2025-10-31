/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  Istanza,
  IstanzaCompetenza,
  StepConfig,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { environment } from 'src/environments/environment';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-dichiarazioni-vinca',
  templateUrl: 'dichiarazioni-vinca.component.html',
  styleUrls: ['dichiarazioni-vinca.component.scss'],
})
export class DichiarazioniVincaComponent
  extends StepperIstanzaComponent
  implements OnInit
{
  GDPR_docUrl = environment.GDPR_docUrl;
  codMaschera = '.MSCR029F';

  formDichiarazioni: FormGroup;
  adempimento: Adempimento;

  istanza: Istanza;

  qdr_dichiarazioni;
  jsonData;
  saveWithPut = false;

  gestioneEnum = AttoreGestioneIstanzaEnum;

  flagVisualizzaTexarea: boolean = false;
  flagVisualizzaSelect: boolean = false;
  info: TipoInfoAdempimento = TipoInfoAdempimento.acWeb;
  acWebPiemonte: string;
  acWebNotPiemonte: string;
  cod_tipo_adempimento: string;
  cod_competenza_territorio: string;
  comune_competenza: string;
  serviziRegionePiemonteUrl: string;

  today = new Date();

  constructor(
    public router: Router,
    private fb: FormBuilder,
    private configurazioniService: ConfigurazioniScrivaService,
    private adempimentoService: AdempimentoService,
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
  }

  ngOnInit() {
    this.serviziRegionePiemonteUrl = environment.serviziRegionePiemonteUrl;
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.changed = false;
    this.buildFormDichiarazioni();

    this.loadData();
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.formDichiarazioni.enable();
    } else {
      this.formDichiarazioni.disable();
    }

    this.spinner.show();
    this._getIstanzaById();
  }

  /**
   * Funzione che richiama servizio per ottenere l'istanza
   */
  private _getIstanzaById() {
    this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
      (res) => {
        this.istanza = res;
        this.adempimento = this.istanza.adempimento;

        // Recupero dall'istanza l'oggetto dell'adempimento
        const adempimento: Adempimento = this.adempimento;
        // Lancio il setup per il contesto dell'helper
        this.helpService.setCodContestoFromAdempimento(adempimento);

        this.jsonData = JSON.parse(this.istanza.json_data);
        this.cod_tipo_adempimento =
          this.adempimento.tipo_adempimento.cod_tipo_adempimento;

        this.qdr_riepilogo = this.jsonData.QDR_RIEPILOGO;
        const qdrDichiarazioni = this.jsonData[this.codTipoQuadro];
        if (qdrDichiarazioni) {
          this.saveWithPut = true;
        }
        if (
          JSON.parse(res.json_data).QDR_CONFIG[this.codTipoQuadro]?.solo_lettura
        ) {
          this.attoreGestioneIstanza = this.gestioneEnum.READ_ONLY as string;
        }

        this._ottieniListaCompetenzaPrincipale(qdrDichiarazioni);
        this.spinner.hide();
      },
      (err) => {
        this.spinner.hide();
        this.showErrorsQuadroConCodeENoCode(err, 'containerDichiarazioni');
      }
    );
  }

  /**
   * Funzione che permette di ottenere la lista delle competenze
   * e setta validatore form per la dichiarazione gdpr 2
   * @param qdrDichiarazioni
   */
  private _ottieniListaCompetenzaPrincipale(qdrDichiarazioni: any) {
    this._getIstanzaCompetenzaAcPrincipaleList(this.istanza).subscribe(
      (data) => {
        this.cod_competenza_territorio =
          data[0].competenza_territorio.cod_competenza_territorio;
        this.comune_competenza =
          data[0].competenza_territorio.des_competenza_territorio;

        this.linkPrivacy('gp_29');
        if (this.cod_competenza_territorio != 'gp_29') {
          // Se presente un comune di competenza territoriale diverso da Regione Piemonete
          // setto i validatori di dchr_gdpr_2
          this.formDichiarazioni
            .get('dchr_gdpr_2')
            .setValidators(Validators.required);
          this.formDichiarazioni.get('dchr_gdpr_2').updateValueAndValidity();
          this.linkPrivacy(this.cod_competenza_territorio);
        }

        if (this.saveWithPut === true) {
          this.getCaricaQdrDichiarazioni(qdrDichiarazioni);
        }
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  private getCaricaQdrDichiarazioni(q: any) {
    this.formDichiarazioni
      .get('dchr_dati_pers')
      .setValue(q.dchr_dati_pers.check);
    this.formDichiarazioni
      .get('dchr_pubbl_sito')
      .setValue(q.dchr_pubbl_sito.check);
    this.formDichiarazioni
      .get('dchr_dati_pers')
      .setValue(q.dchr_dati_pers.check);
    this.formDichiarazioni
      .get('dchr_pagam_bollo')
      .setValue(q.dchr_pagam_bollo.check);
    this.formDichiarazioni.get('dchr_sanzione').setValue(q.dchr_sanzione.check);

    this.formDichiarazioni.get('dchr_gdpr_1').setValue(q.dchr_gdpr_1.check);
    if (this.cod_competenza_territorio != 'gp_29') {
      this.formDichiarazioni.get('dchr_gdpr_2').setValue(q.dchr_gdpr_2.check);
    }
    if (this.attoreGestioneIstanza != this.gestioneEnum.WRITE) {
      this.formDichiarazioni.disable();
    }
  }

  private _getIstanzaCompetenzaAcPrincipaleList(
    istanza: Istanza
  ): Observable<IstanzaCompetenza[]> {
    return this.adempimentoService
      .getAutoritaCompetenteByIstanza(istanza.id_istanza)
      .pipe(
        map((list) => list.filter((istComp) => istComp.flg_autorita_principale))
      );
  }

  private linkPrivacy(codComTerritorio) {
    // console.log('cod_adempimento: ',this.adempimento.cod_adempimento)         // SRC - APP
    // console.log('info: ',this.info)                                           // WEB_AC_GDPR
    // console.log('cod_competenza_territorio: ',this.cod_competenza_territorio) // gp_29:regione Piemonte
    // console.log('cod_tipo_adempimento: ',this.cod_tipo_adempimento)           // VINCA

    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        this.adempimento.cod_adempimento, // SRC - APP
        this.info, // WEB_AC_GDPR
        codComTerritorio // gp_29 (o altro valore)
      )
      .subscribe(
        (res) => {
          if (codComTerritorio === 'gp_29') {
            this.acWebPiemonte = res[0].valore;
          } else {
            this.acWebNotPiemonte = res[0].valore;
          }
          if (res[0].valore === undefined) {
            console.log('Sito web non trovato');
          }
        },
        (err) => {
          console.log('err.status: ', err.status);
          if (err.status === 404) {
            this.spinner.hide();
            return;
          }
        }
      );
  }

  private buildFormDichiarazioni() {
    this.formDichiarazioni = this.fb.group({
      dchr_gdpr_1: [null, { validators: [Validators.required] }],
      dchr_gdpr_2: [null],
      dchr_dati_pers: [null, { validators: [Validators.required] }],
      dchr_pubbl_sito: [null, { validators: [Validators.required] }],
      dchr_pagam_bollo: [null, { validators: [Validators.required] }],
      dchr_sanzione: [null, { validators: [Validators.required] }],
    });
  }

  setValid(formTarget) {
    const indice = formTarget.currentTarget.id;
    const valore = this.formDichiarazioni.get(indice).value;
    if (valore === false) {
      this.formDichiarazioni.get(indice).setValue(null);
    }
  }

  private caricaQuadro() {
    const v = this.formDichiarazioni;
    const webLink1 = this.acWebPiemonte ? ': ' + this.acWebPiemonte : '';
    const webLink2 = this.acWebNotPiemonte ? ': ' + this.acWebNotPiemonte : '';
    const comuneNotPiemonte = this.comune_competenza;

    const arDichiarazioni = [
      {
        check: !!v.get('dchr_gdpr_1').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_gdpr_1',
        des_dichiarazione:
          "dichiara di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale del soggetto gestore Regione Piemonte" +
          webLink1,
      },
      {
        check: !!v.get('dchr_dati_pers').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_dati_pers',
        des_dichiarazione:
          'dichiara di aver verificato che gli elaborati progettuali sono firmati esclusivamente in formato digitale da tutti i professionisti che li hanno redatti e che non contengono dati personali eccedenti e non pertinenti',
      },
      {
        check: !!v.get('dchr_pubbl_sito').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_pubbl_sito',
        des_dichiarazione:
          "dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza sul sito Portale di pubblicazione di procedimenti e Osservazioni (" +
          this.serviziRegionePiemonteUrl +
          ') ai sensi della L 241/90 e del D. Lgs. 33/2013, al fine di ricevere eventuali osservazioni',
      },
      {
        check: !!v.get('dchr_pagam_bollo').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_pagam_bollo',
        des_dichiarazione:
          'dichiara di essere consapevole che il pagamento del bollo, se dovuto e non assolto, potrà essere richiesto successivamente alla presentazione della presente istanza',
      },
      {
        check: !!v.get('dchr_sanzione').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_sanzione',
        des_dichiarazione:
          'dichiara di essere consapevole delle sanzioni penali previste in caso di dichiarazioni non veritiere e di falsità negli atti e della conseguente decadenza dai benefici di cui agli artt. 75 e 76 del D.P.R. 445/2000',
      },
    ];

    const QDR_DICHIARAZIONE = {
      dchr_dati_pers: {
        check: !!v.get('dchr_dati_pers').value,
      },
      dchr_pubbl_sito: {
        check: !!v.get('dchr_pubbl_sito').value,
      },
      dchr_pagam_bollo: {
        check: !!v.get('dchr_pagam_bollo').value,
      },
      dchr_sanzione: {
        check: !!v.get('dchr_sanzione').value,
      },
      dchr_gdpr_1: {
        check: !!v.get('dchr_gdpr_1').value,
      },
    };

    if (this.cod_competenza_territorio != 'gp_29') {
      // Se la competenza territorio è diversa da Regione Piemonte l'aggiungo all'array
      const datiPersonali2 = {
        check: !!v.get('dchr_gdpr_2').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_gdpr_2',
        des_dichiarazione:
          "dichiara di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale del soggetto gestore " +
          comuneNotPiemonte +
          webLink2,
      };
      // Calcola la seconda posizione
      const position = 1;
      // Uso splice per aggiungere il nuovo oggetto in seconda posizione
      arDichiarazioni.splice(position, 0, datiPersonali2);

      // Stessa logica l'oggetto QDR_DICHIARAZIONE
      QDR_DICHIARAZIONE['dchr_gdpr_2'] = {
        check: !!v.get('dchr_gdpr_2').value,
      };
    }

    QDR_DICHIARAZIONE['dichiarazioni'] = arDichiarazioni;

    this.qdr_riepilogo[this.codTipoQuadro] = QDR_DICHIARAZIONE;
    this.qdr_dichiarazioni = QDR_DICHIARAZIONE;
  }

  salvaDatiQuadro() {
    this.spinner.show();
    const configsBuild: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: this.qdr_dichiarazioni,
      datiRiepilogo: this.qdr_riepilogo,
    };
    this.saveDataQuadro(configsBuild).subscribe(
      (res) => {
        this.spinner.hide();
        this.saveWithPut = true;
        this.setStepCompletedEmit('DichiarazioniVincaComponent', true);
        this.goToStep(this.stepIndex);
      },
      (err) => {
        this.spinner.hide();
        this.showErrorsQuadroConCodeENoCode(
          err,
          'formContainer',
          'containerDichiarazioni'
        );
      }
    );
  }

  /**
   * Funzione per il salvataggio del quadro e del riepilogo
   * @override
   */
  protected saveDataQuadro(configs: IConfigsSaveQuadro) {
    // Preparo le informazioni per il salvataggio dei dati
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(configs);

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      this.saveWithPut,
      true,
      true
    );
  }

   
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    if (!this.formDichiarazioni.valid) {
      Object.keys(this.formDichiarazioni.controls).forEach((controlName) => {
        this.formDichiarazioni.get(controlName).markAsTouched();
      });
      // Form NON valido
      return of(false);
    } else {
      return of(true);
    }
  }

  /**
   * @override
   */
  onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.isStepValid().subscribe((isValid: boolean) => {
        if (isValid) {
          this.caricaQuadro();
          this.salvaDatiQuadro();
        } else {
          // Form NON valido
          this._messageService.showMessage(
            'E001',
            'containerDichiarazioni',
            true
          );
        }
      });
    } else {
      this.goToStep(this.stepIndex);
    }
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }
}
