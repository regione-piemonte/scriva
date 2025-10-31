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
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { catchError, concatMap, delay, switchMap, tap } from 'rxjs/operators';
import { forkJoin, from, Observable, of, throwError } from 'rxjs';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';

import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Istanza,
  StepConfig,
  Adempimento,
  DataQuadro,
} from 'src/app/shared/models';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import {
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
  VincoliAutService,
} from 'src/app/shared/services';
import { IstanzaVincoloAut, VincoloAutorizza } from '../../../models';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';

interface TitoloTableElement {
  index: number;
  selected: boolean;
  addedByUser?: boolean;
  istanzaVincoloAut: IstanzaVincoloAut;
}

@Component({
  selector: 'app-titoli-abilitativi',
  templateUrl: './titoli-abilitativi.component.html',
  styleUrls: ['./titoli-abilitativi.component.scss'],
})
export class TitoliAbilitativiComponent
  extends StepperIstanzaComponent
  implements OnInit, AfterViewInit
{
  @ViewChild('selectionTemplate') selectionTemplate: TemplateRef<any>;
  @ViewChild('descrizioneTemplate') descrizioneTemplate: TemplateRef<any>;
  @ViewChild('enteTemplate') enteTemplate: TemplateRef<any>;
  @ViewChild('emailTemplate') emailTemplate: TemplateRef<any>;

  codMaschera = '.MSCR018F';

  istanza: Istanza;


  gestioneEnum = AttoreGestioneIstanzaEnum;

  savedTitoli: IstanzaVincoloAut[] = [];
  titoliLightList: VincoloAutorizza[];
  nuovoTitoloTemplate: VincoloAutorizza;

  titoliTableSource: TitoloTableElement[];
  columns = [];
  ColumnMode = ColumnMode;

  emailErrorIndexes: number[] = [];

  constructor(
    private vincoliService: VincoliAutService,
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
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.loadData();
  }

  ngAfterViewInit() {
    this._setColumns();
  }

  /**
   * Funzione per settare le colonne
   */
  private _setColumns() {
    this.columns = [
      {
        name: 'Seleziona',
        minWidth: 120,
        maxWidth: 120,
        sortable: false,
        cellTemplate: this.selectionTemplate,
      },
      {
        name: 'Titolo abilitativo',
        minWidth: 210,
        sortable: false,
        cellTemplate: this.descrizioneTemplate,
      },
      {
        name: 'Soggetto che rilascia il titolo',
        minWidth: 200,
        sortable: false,
        cellTemplate: this.enteTemplate,
      },
      {
        name: 'E-mail PEC',
        minWidth: 200,
        sortable: false,
        cellTemplate: this.emailTemplate,
      },
    ];
  }

  /**
   * Funzione per caricare i dati
   */
  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();
    this._getIstanzaById();
  }

  private _getIstanzaById() {
    this.istanzaService
      .getIstanzaById(this.idIstanza)
      .pipe(
        tap((istanza) => {
          this.istanza = istanza;
          this.qdr_riepilogo = JSON.parse(this.istanza.json_data).QDR_RIEPILOGO;

          // Recupero dall'istanza l'oggetto dell'adempimento
          const adempimento: Adempimento = istanza.adempimento;
          // Lancio il setup per il contesto dell'helper
          this.helpService.setCodContestoFromAdempimento(adempimento);
          // #
        }),
        switchMap(() => {
          const getAutList = this.vincoliService.getVincoliAutByAdempimento(
            this.istanza.adempimento.id_adempimento
          );
          const getSavedTitoliAbilitativi = this.vincoliService
            .getVincoliAutByIstanza(this.idIstanza)
            .pipe(
              catchError((err) => {
                if (err.status === 404) {
                  return of([]);
                } else {
                  return throwError(err);
                }
              })
            );
          return forkJoin([getAutList, getSavedTitoliAbilitativi]);
        })
      )
      .subscribe(
        (res) => {
          this.titoliLightList = res[0].filter(
            (vincolo) => vincolo.tipo_vincolo_aut.cod_tipo_vincolo_aut === 'A'
          );
          this.savedTitoli = res[1].filter(
            (istanzaVincolo) =>
              istanzaVincolo.vincolo_autorizza.tipo_vincolo_aut
                .cod_tipo_vincolo_aut === 'A'
          );
          this.buildTable();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'titoliContainer');
        }
      );
  }

  /**
   * Funzione per costruire tabella
   */
  buildTable() {
    this.titoliTableSource = [];
    let tableIndex = -1;
    this.savedTitoli.forEach((titolo, i) => {
      this.titoliTableSource.push({
        index: i,
        selected: true,
        istanzaVincoloAut: titolo,
      });
      tableIndex = i;
    });

    this.titoliLightList.forEach((tl) => {
      const check = this.titoliTableSource.some(
        (element) =>
          element.istanzaVincoloAut.vincolo_autorizza.id_vincolo_autorizza ===
          tl.id_vincolo_autorizza
      );
      if (!check) {
        const titolo: IstanzaVincoloAut = {
          id_istanza: this.idIstanza,
          des_vincolo_calcolato: null,
          des_ente_utente: null,
          des_email_pec_ente_utente: null,
          vincolo_autorizza: tl,
        };
        this.titoliTableSource.push({
          index: tableIndex + 1,
          selected: false,
          istanzaVincoloAut: titolo,
        });
        tableIndex++;
      }
    });

    this.nuovoTitoloTemplate = this.titoliLightList.find(
      (elem) => elem.cod_vincolo_autorizza === 'AGEN'
    );
    this.spinner.hide();
  }

  onSeleziona(index: number, checked: boolean) {
    const titolo = this.titoliTableSource[index];
    titolo.selected = checked;
  }

  viewReference(index: number) {
    const normativa =
      this.titoliTableSource[index].istanzaVincoloAut.vincolo_autorizza
        .des_rif_normativo;
    if (normativa.includes('http')) {
      window.open(normativa);
    } else {
      this.downloadFile(normativa);
    }
  }

  downloadFile(fileName) {
    this.spinner.show();
    this.vincoliService.downloadNormativa(fileName).subscribe(
      (res) => {
        const blob = new Blob([res], { type: 'application/octet-stream' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
        this.spinner.hide();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'titoliContainer');
      }
    );
  }

  onDescriptionChange(index, value) {
    const titolo = this.titoliTableSource[index];
    titolo.istanzaVincoloAut.des_vincolo_calcolato = value;
    if (value) {
      titolo.selected = true;
      this.changed = true;
    }
  }

  onSoggettoChange(index, value) {
    const titolo = this.titoliTableSource[index];
    titolo.istanzaVincoloAut.des_ente_utente = value;
    if (value) {
      titolo.selected = true;
      this.changed = true;
    }
  }

  onEmailChange(index, value) {
    const isValidEmail = this.checkEmail(index, value);
    if (isValidEmail) {
      const titolo = this.titoliTableSource[index];
      titolo.istanzaVincoloAut.des_email_pec_ente_utente = value;
      if (value) {
        titolo.selected = true;
        this.changed = true;
      }
    }
  }

  checkEmail(index, value): boolean {
    const errorIndex = this.emailErrorIndexes.indexOf(index);
    if (!value) {
      if (errorIndex > -1) {
        this.emailErrorIndexes.splice(errorIndex, 1);
      }
      return true;
    }

    const emailRegex = new RegExp(
      /^[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\.[A-Za-z]{2,4}$/
    );
    if (!emailRegex.test(value)) {
      if (errorIndex === -1) {
        this.emailErrorIndexes.push(index);
      }
      return false;
    } else {
      if (errorIndex > -1) {
        this.emailErrorIndexes.splice(errorIndex, 1);
      }
      return true;
    }
  }

  onInserisci() {
    this.titoliTableSource.push({
      index: this.titoliTableSource.length,
      addedByUser: true,
      selected: false,
      istanzaVincoloAut: {
        id_istanza: this.idIstanza,
        des_vincolo_calcolato: null,
        des_ente_utente: null,
        des_email_pec_ente_utente: null,
        vincolo_autorizza: this.nuovoTitoloTemplate,
      },
    });
    this.titoliTableSource = [...this.titoliTableSource];
    this.changed = true;
  }

  /**
   * @override
   */
  protected onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.isStepValid().subscribe((isValid: boolean) => {
        if (isValid) {
          this.saveData();
        }
      });
    } else {
      this.setStepCompletedEmit('TitoliAbilitativiComponent', true);
      this.goToStep(this.stepIndex);
    }
  }
  
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid():Observable<boolean> {
    if (this.emailErrorIndexes.length > 0) {
      this._messageService.showMessage('E004', 'titoliContainer', true);
      return of(false);
    }
    const checkDescription = this.titoliTableSource.some(
      (titolo) =>
        titolo.selected &&
        titolo.istanzaVincoloAut.vincolo_autorizza.cod_vincolo_autorizza ===
          'AGEN' &&
        !titolo.istanzaVincoloAut.des_vincolo_calcolato
    );
    if (checkDescription) {
      this._messageService.showMessage('E001', 'titoliContainer', true);
      return of(false);
    }

    const checkSelection = this.titoliTableSource.some(
      (titolo) =>
        !titolo.selected &&
        (titolo.istanzaVincoloAut.des_ente_utente ||
          titolo.istanzaVincoloAut.des_email_pec_ente_utente ||
          titolo.istanzaVincoloAut.des_vincolo_calcolato)
    );

    if (checkSelection) {
      this._messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A022',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {},
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              return of(true);
            },
          },
        ],
      });
    } else {
      return of(true);
    }
  }

  /**
   * Funzione che richiama il salvataggio del quadro dopo aver gestito i titoli
   */
  saveData() {
    const selectedTitoli = this.titoliTableSource.filter(
      (elem) => elem.selected
    );

    const titoliToAdd: IstanzaVincoloAut[] = [];
    const titoliToEdit: IstanzaVincoloAut[] = [];
    const titoliToDel: IstanzaVincoloAut[] = [];

    const addCallList = [];
    const editCallList = [];
    const delCallList = [];

    selectedTitoli.forEach((selected) => {
      if (selected.istanzaVincoloAut.gestUID) {
        titoliToEdit.push(selected.istanzaVincoloAut);
        editCallList.push(
          this.vincoliService.putVincoliAut(selected.istanzaVincoloAut)
        );
      } else {
        addCallList.push(
          this.vincoliService.postVincoliAut(selected.istanzaVincoloAut)
        );
      }
    });

    this.savedTitoli.forEach((saved) => {
      const checkDel = selectedTitoli.some(
        (selected) => selected.istanzaVincoloAut.gestUID === saved.gestUID
      );
      if (!checkDel) {
        titoliToDel.push(saved);
        delCallList.push(this.vincoliService.deleteVincoloAut(saved.gestUID));
      }
    });

    this.spinner.show();
    if (
      addCallList.length === 0 &&
      editCallList.length === 0 &&
      delCallList.length === 0
    ) {
      this.salvaDatiQuadro();
    } else {
      const requests = [...addCallList, ...editCallList, ...delCallList];
      from(requests)
        .pipe(concatMap((request) => request.pipe(delay(100))))
        .subscribe(
          (res: IstanzaVincoloAut) => {
            if (res !== null) {
              titoliToAdd.push(res);
            }
          },
          (err) => {
            this.showErrorsQuadroConCodeENoCode(err, 'titoliContainer');
          },
          () => {
            this.updateSelectedList(titoliToAdd, titoliToDel);
            this.salvaDatiQuadro();
          }
        );
    }
  }

  updateSelectedList(
    titoliToAdd: IstanzaVincoloAut[],
    titoliToDel: IstanzaVincoloAut[]
  ) {
    titoliToDel.forEach((titolo) => {
      const index = this.savedTitoli.findIndex(
        (st) => st.gestUID === titolo.gestUID
      );
      this.savedTitoli.splice(index, 1);
    });

    titoliToAdd.forEach((titolo) => {
      const checkAdd = this.savedTitoli.findIndex(
        (save) => save.gestUID === titolo.gestUID
      );
      if (checkAdd === -1) {
        this.savedTitoli.push(titolo);
      }
    });
  }


  salvaDatiQuadro() {
    this.spinner.show();
    const configsBuild: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: { titoliAbilitativi: this.savedTitoli },
      datiRiepilogo: this.qdr_riepilogo,
    };
    this.saveDataQuadro(configsBuild).subscribe(
      (res) => {
        this.spinner.hide();
        this.changed = false;
        this.setStepCompletedEmit('TitoliAbilitativiComponent', true);
        this.goToStep(this.stepIndex);
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'titoliContainer');
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
    this.spinner.show();
    return this.stepManagerService
      .salvaJsonDataQuadro(requestData.requestDataRiepilogo)
      .pipe(
        tap((res: DataQuadro) => {
          this.spinner.hide();
        }),
        catchError((e: ScrivaServerError) => {
          this.spinner.hide();

          return throwError(e);
        })
      );
  }
}
