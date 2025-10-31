/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { takeUntil } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AllegatiIntegrazioniService } from 'src/app/features/ambito/services/allegati-integrazioni.service';
import { Help, Istanza, OggettoApp } from 'src/app/shared/models';
import { IntegrazioneIstanzaStatus } from '../../models/istanza/integrazione-istanza-status.model';
import { AuthStoreService, HelpService, MessageService } from '../../services';
import { StatoIstanzaEnum } from '../../utils/stato-istanza.enum';

enum buttonsIntegrazioneAllegati {
  BTN_PROSEGUI_INTEGR = 'btn_prosegui_integr',
  BTN_DOWNLOAD_LETT_ACCOMP = 'btn_download_lett_accomp',
  BTN_INTEGRA_ALLEGATI = 'btn_integra_allegati',
  BTN_PERFEZIONA_ALLEGATI = 'btn_perfeziona_allegati',
  BTN_INTEGRA_ALLEGATI_SUCC = 'btn_integra_allegati_succ',
  BTN_UPLOAD_LETT_ACCOMP = 'btn_upload_lett_accomp',
  BTN_DOWNLOAD_ELEN_INTEG = 'btn_download_elen_integ',
}

@Component({
  selector: 'app-integrazione-allegati',
  templateUrl: './integrazione-allegati.component.html',
  styleUrls: ['./integrazione-allegati.component.scss'],
})
export class IntegrazioneAllegatiComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() istanza: Istanza;

  integrazioneIstanzaStatus: IntegrazioneIstanzaStatus = null;

  btn_prosegui_integr: OggettoApp;
  btn_download_lett_accomp: OggettoApp;
  btn_upload_lett_accomp: OggettoApp;
  btn_download_elen_integ: OggettoApp;
  pan_lettera_accompagnamento: OggettoApp;
  // Conferma
  btn_integra_allegati: OggettoApp;
  btn_integra_allegati_succ: OggettoApp;
  btn_perfeziona_allegati: OggettoApp;

  disabled: any;

  form: FormGroup;
  helpList: Help[];
  chiaveHelpStart: string;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<{ action; args? }>();

  constructor(
    private authStoreService: AuthStoreService,
    private allegatiIntegrazioniService: AllegatiIntegrazioniService,
    private fb: FormBuilder,
    private helpService: HelpService,
    private messageService: MessageService
  ) {
    super();
  }

  ngOnInit() {
    const componente = this.authStoreService.getComponente();
    this.chiaveHelpStart = `${componente}.${this.istanza.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.istanza.adempimento.cod_adempimento}.QDR_ALLEGATO`;
    const getHelpList = this.helpService.getHelpByChiave(this.chiaveHelpStart);

    getHelpList.subscribe((helpList: Help[]) => {
      this.helpList = helpList;
    });

    this.allegatiIntegrazioniService
      .getStatus()
      .pipe(takeUntil(this.destroy$))
      .subscribe((ret: IntegrazioneIstanzaStatus) => {
        if (ret.idIstanza === this.istanza.id_istanza) {
          this._updateStatusIntegrazione(ret);
        }
      });
    this.buildForm();
  }

  onHelpClicked(key: string) {
    const chiaveHelp = `${this.chiaveHelpStart}.${key}`;
    const modalContent =
      this.helpList.find((help) => help.chiave_help === chiaveHelp)
        ?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  private _updateStatusIntegrazione(
    integrazioneIstanzaStatus: IntegrazioneIstanzaStatus
  ) {
    this.integrazioneIstanzaStatus = integrazioneIstanzaStatus;
    this._makeVisibilityAndDisabled();
    this.updateForm();
  }

  private _makeVisibilityAndDisabled() {
    const keys = Object.keys(buttonsIntegrazioneAllegati);
    // todo array di OggettiApp ma in realtà nel servizio il tipo che si recupera
    // dovrebbe essere tipoAdempimentoOggApp
    // andrebbe ripulito su tutto il codice
    if (!this.integrazioneIstanzaStatus.tipoAdempimentoOggApp) return;

    const oggettiApp = [
      ...this.integrazioneIstanzaStatus.tipoAdempimentoOggApp,
    ];
    keys.forEach((key) => {
      if (
        oggettiApp.find(
          (i) => i.cod_oggetto_app === buttonsIntegrazioneAllegati[key]
        )
      ) {
        this[buttonsIntegrazioneAllegati[key]] =
          this.integrazioneIstanzaStatus.tipoAdempimentoOggApp.find(
            (i) => i.cod_oggetto_app === buttonsIntegrazioneAllegati[key]
          );
      }
    });
    this.disabled = {
      ...this.integrazioneIstanzaStatus.disabled,
      lettera_accompagnamento: this.integrazioneIstanzaStatus.readonlyAllegati,
    };
  }

  private buildForm() {
    this.form = this.fb.group({
      nota: [''],
    });
  }

  private updateForm() {
    this.form
      .get('nota')
      .setValue(
        this.integrazioneIstanzaStatus.jsIntegrazioni
          ?.info_lettera_accompagnamento?.testo_lettera_integrazioni
      );

    if (
      this.integrazioneIstanzaStatus.disabled?.btn_download_lett_accomp_disabled
    ) {
      this.form.get('nota').disable();
    } else {
      this.form.get('nota').enable();
    }
  }

  public clickBtn(oggettoApp: OggettoApp, fileInput = null, file = null) {
    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_PROSEGUI_INTEGR
    ) {
      this.allegatiIntegrazioniService.onProsegui(this.istanza, oggettoApp);
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_DOWNLOAD_ELEN_INTEG
    ) {
      this.allegatiIntegrazioniService.onDownloadElenIntegrazioni(
        this.istanza,
        oggettoApp
      );
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_DOWNLOAD_LETT_ACCOMP
    ) {
      this.allegatiIntegrazioniService.onDownloadLettAccomp(
        this.istanza,
        oggettoApp,
        this.form.get('nota').value
      );
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_UPLOAD_LETT_ACCOMP
    ) {
      if (!file) {
        fileInput.click();
      } else {
        this.allegatiIntegrazioniService.onUploadLettAccomp(
          this.istanza,
          oggettoApp,
          file
        );
      }
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_INTEGRA_ALLEGATI
    ) {
      this.allegatiIntegrazioniService.onIntegraAllegati(
        this.istanza,
        oggettoApp
      );
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_INTEGRA_ALLEGATI_SUCC
    ) {
      this.allegatiIntegrazioniService.onIntegraAllegatiSucc(
        this.istanza,
        oggettoApp
      );
    }

    if (
      oggettoApp.cod_oggetto_app ===
      buttonsIntegrazioneAllegati.BTN_PERFEZIONA_ALLEGATI
    ) {
      this.allegatiIntegrazioniService.onPerfezionaAllegati(
        this.istanza,
        oggettoApp
      );
    }
  }

  public onDeleteLettera() {
    this.allegatiIntegrazioniService.onDeleteLettera(this.istanza);
  }

  public onAnnulla() {
    this.allegatiIntegrazioniService.onAnnulla(this.istanza);
  }

  get isPerfAllegati(): boolean {
    if (this.integrazioneIstanzaStatus.jsIntegrazioni) {
      return (
        this.integrazioneIstanzaStatus.jsIntegrazioni?.stato_integrazione ===
        StatoIstanzaEnum.PERF_ALLEGATI
      );
    }
    return (
      this.istanza.stato_istanza.codice_stato_istanza ===
      StatoIstanzaEnum.PERF_ALLEGATI
    );
  }

  get isRicIntegr(): boolean {
    if (this.integrazioneIstanzaStatus.jsIntegrazioni) {
      return (
        this.integrazioneIstanzaStatus.jsIntegrazioni?.stato_integrazione ===
        StatoIstanzaEnum.RIC_INTEGR
      );
    }
    return (
      this.istanza.stato_istanza.codice_stato_istanza ===
      StatoIstanzaEnum.RIC_INTEGR
    );
  }

  get isRicIntegrSucc(): boolean {
    if (this.integrazioneIstanzaStatus.jsIntegrazioni) {
      return (
        this.integrazioneIstanzaStatus.jsIntegrazioni?.stato_integrazione ===
        StatoIstanzaEnum.RIC_INTEGR_SUCC
      );
    }
    return (
      this.istanza.stato_istanza.codice_stato_istanza ===
      StatoIstanzaEnum.RIC_INTEGR_SUCC
    );
  }

  get hasLettera(): boolean {
    return this.integrazioneIstanzaStatus.jsIntegrazioni
      ?.info_lettera_accompagnamento?.lettera_accompagnamento;
  }
}
