/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Help } from '@app/core/interfaces/help';
import { trimOnChange } from '@app/core/services/utilities/utilities.functions';
import { Adempimenti } from '@app/pages/ambiti/model';
import { AbstractFormHelperService } from '@core/helper/abstract-form-helper.service';
import { UtilityService } from '@core/index';
import { I18nService } from '@eng-ds/translate';
import {
  AutocompleteInput,
  CheckboxInput,
  Divider,
  Form,
  RadiosInput,
  SelectOption,
  TextInput,
  ValidationStatus
} from '@shared/form';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class SearchFormHelperService extends AbstractFormHelperService {
  _help: Help;

  constructor(
    private i18n: I18nService,
    private utilityService: UtilityService
  ) {
    super();
    this._initForms();
  }

  setHelp(help: Help) {
    this._help = help;
  }

  protected _initForms() {
    this._items.set('pratiche-search', this._getPracticeSearch.bind(this));
  }

  getAdempimentiPubblicati(): void {
    this.utilityService
      .getAdempimentiPubblicati()
      .pipe(
        catchError((e: HttpErrorResponse) => {
          return of(null);
        })
      )
      .subscribe((result: any) => {
        this.setHelpPlaceholder(result);
      });
  }

  setHelpPlaceholder(data: Adempimenti[]) {
    //console.log('LISTA ADEMPIMENTI -->> ', data);
    let textPlaceholder = '';
    let codAdempimenti = [];

    data.forEach((el) => {
      let isFound = codAdempimenti.includes(
        el.tipo_adempimento.cod_tipo_adempimento
      );

      if (!isFound) {
        codAdempimenti.push(el.tipo_adempimento.cod_tipo_adempimento);
        textPlaceholder +=
          '<br><strong>' +
          el.tipo_adempimento.cod_tipo_adempimento +
          ' ' +
          el.tipo_adempimento.des_tipo_adempimento +
          ':' +
          '</strong><br>';
        textPlaceholder +=
          '<ul><li>' +
          el.cod_adempimento +
          ' ' +
          el.des_adempimento +
          '</li></ul>';
      }

      if (isFound) {
        textPlaceholder = textPlaceholder.slice(0, -5);
        textPlaceholder +=
          '<li>' + el.cod_adempimento + ' ' + el.des_adempimento + '</li></ul>';
      }
    });

    this._help.placeholders = {
      key: '{PH_ELENCO_PROCEDIMENTI}',
      value: textPlaceholder
    };
  }

  private _getPracticeSearch() {
    const form = new Form({
      header: { show: false },
      filter: true,
      controls: {
        id_ambito: new AutocompleteInput({
          options: this.utilityService.getAmbiti(),
          label: 'PRACTICE.SEARCH.FIELDS.AMBITO',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.AMBITO',
          size: '12|12|6|4|4'
        }),
        id_tipo_adempimento: new AutocompleteInput<
          { id: number; cod: string },
          string
        >({
          options: this.utilityService.getAdempimenti(),
          label: 'PRACTICE.SEARCH.FIELDS.TIPO_PROCEDIMENTO',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.TIPO_PROCEDIMENTO',
          size: '12|12|6|4|4',
          compareWith: (item, selected) => item.id.id === selected,
          valueChange: (value) => {
            if (value && value.id !== -1) {
              const id_adempimento: AutocompleteInput<number, string> =
                form.get('id_adempimento') as unknown as AutocompleteInput<
                  number,
                  string
                >;
              this.utilityService
                .getAdempimentiByCod(value.cod)
                .subscribe((result: SelectOption<number, string>[]) => {
                  id_adempimento.setOptions(of(result));
                });

              if (value.cod === 'VIA') {
                form.addControlAfter(
                  'flg_infrastrutture_strategiche',
                  new CheckboxInput({
                    label: 'PRACTICE.SEARCH.FIELDS.INFRASTRUTTURA',
                    size: '12|12|6|4|4'
                  }),
                  'label_stato'
                );
                form.addControlAfter(
                  'flg_vinca',
                  new CheckboxInput({
                    label: 'PRACTICE.SEARCH.FIELDS.VAL_INCIDENZA',
                    size: '12|12|6|4|4'
                  }),
                  'flg_infrastrutture_strategiche'
                );
                // form.addControlAfter(
                //   'flg_pnrr',
                //   new CheckboxInput({
                //     label: 'PRACTICE.SEARCH.FIELDS.PROGETTO_PNRR',
                //     size: '12|12|6|4|4'
                //   }),
                //   'flg_vinca'
                // );
                form.addControlAfter(
                  'cod_categoria_oggetto',
                  new AutocompleteInput<number, string>({
                    options:
                      this.utilityService.getCategorieProgettualiByAdempimento(
                        value.id
                      ),
                    label: 'PRACTICE.SEARCH.FIELDS.CAT_OPERA',
                    placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.CAT_OPERA',
                    size: '12|12|6|4|4'
                  }),
                  'flg_vinca'
                );

                form.addControlAfter(
                  'cod_stato_procedimento_statale',
                  new AutocompleteInput({
                    options: this.utilityService.getStatiProcedimentoStatale(),
                    label: 'PRACTICE.SEARCH.FIELDS.STATO_PROCEDIMENTO_STATALE',
                    placeholder:
                      'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.STATO_PROCEDIMENTO_STATALE',
                    size: '12|12|6|4|4'
                  }),
                  'cod_categoria_oggetto'
                );
              } else {
                if (form.get('flg_infrastrutture_strategiche')) {
                  form.removeControl('flg_infrastrutture_strategiche');
                }
                if (form.get('flg_vinca')) {
                  form.removeControl('flg_vinca');
                }
                if (form.get('flg_pnrr')) {
                  form.removeControl('flg_pnrr');
                }
                if (form.get('cod_categoria_oggetto')) {
                  form.removeControl('cod_categoria_oggetto');
                }
                if (form.get('cod_stato_procedimento_statale')) {
                  form.removeControl('cod_stato_procedimento_statale');
                }
              }
            }
          }
        }),
        id_adempimento: new AutocompleteInput<number, string>({
          options: of([]),
          label: 'PRACTICE.SEARCH.FIELDS.TIPO_PROCEDURA',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.TIPO_PROCEDURA',
          size: '12|12|6|4|4'
        }),
        id_competenza_territorio: new AutocompleteInput({
          options: this.utilityService.getCompetenzeTerritorio(),
          label: 'PRACTICE.SEARCH.FIELDS.AUTORITA_COMP',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.AUTORITA_COMP',
          size: '12|12|6|4|4'
        }),
        anno_presentazione_progetto: new TextInput({
          type: 'number',
          label: 'PRACTICE.SEARCH.FIELDS.ANNO_PRES',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.ANNO',
          maxLenght: 4,
          validationStatus: [
            ValidationStatus.ERROR.CUSTOM(
              (control) => control.touched && control.value.length > 4,
              {
                text: 'Inserisci un anno valido'
              }
            )
          ],
          size: '12|12|6|4|4',
          validatorOrOpts: { updateOn: 'blur' }
        }),
        anno_presentazione_progetto_superiore: new RadiosInput({
          options: of([
            {
              label: this.i18n.translate('PRACTICE.SEARCH.FIELDS.MINORE'),
              value: 'minore_di'
            },
            {
              label: this.i18n.translate('PRACTICE.SEARCH.FIELDS.MAGGIORE'),
              value: 'maggiore_di'
            }
          ]),
          inline: true,
          size: '12|12|6|4|4'
        }),
        /*
        minoredi: new CheckboxInput({
          label: 'PRACTICE.SEARCH.FIELDS.MINORE',
          size: '6|6|3|2|2',
          valueChange: (value) => {
            if (value) {
              const maggiore_di: CheckboxInput = form.get(
                'maggiore_di'
              ) as unknown as CheckboxInput;
              maggiore_di.setValue(false);
            }
          }
        }),
        maggiore_di: new CheckboxInput({
          label: 'PRACTICE.SEARCH.FIELDS.MAGGIORE',
          size: '6|6|3|2|2',
          valueChange: (value) => {
            if (value) {
              const minore_di: CheckboxInput = form.get(
                'minore_di'
              ) as unknown as CheckboxInput;
              minore_di.setValue(false);
            }
          }
        }),*/
        codice_pratica: new TextInput({
          type: 'text',
          help: this._help,
          label: 'PRACTICE.SEARCH.FIELDS.COD_PRATICA',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.COD_PRATICA',
          size: '12|12|6|4|4',
          validatorOrOpts: { updateOn: 'blur' }
        }),
        denominazione_oggetto: new TextInput({
          type: 'text',
          label: 'PRACTICE.SEARCH.FIELDS.TITOLO_PROG',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.DENOMINAZIONE',
          size: '12|12|6|4|4',
          validatorOrOpts: { updateOn: 'blur' }
        }),
        divider_2: new Divider({ size: '0|0|0|4|4' }),
        sigla_provincia_oggetto: new AutocompleteInput({
          options: this.utilityService.getProvince(),
          label: 'PRACTICE.SEARCH.FIELDS.PROVINCIA',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.PROVINCIA',
          size: '12|12|6|4|4',
          valueChange: (value) => {
            const cod_istat_comune_oggetto: AutocompleteInput<number, string> =
              form.get(
                'cod_istat_comune_oggetto'
              ) as unknown as AutocompleteInput<number, string>;
            if (value && value !== '-1') {
              const provincia = this.utilityService.getProvincia(value);
              cod_istat_comune_oggetto.reset();
              cod_istat_comune_oggetto.enable();
              cod_istat_comune_oggetto.setOptions(
                this.utilityService.getComuni(
                  provincia.sigla_provincia,
                  provincia.cod_provincia
                )
              );
              return;
            }
            cod_istat_comune_oggetto.disable();
          }
        }),
        cod_istat_comune_oggetto: new AutocompleteInput<number, string>({
          options: of([]),
          label: 'PRACTICE.SEARCH.FIELDS.COMUNE',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.COMUNE',
          size: '12|12|6|4|4'
        }),
        label_stato: new AutocompleteInput({
          options: this.utilityService.getStatoIstanza(),
          label: 'PRACTICE.SEARCH.FIELDS.STATO_PROC',
          placeholder: 'PRACTICE.SEARCH.FIELDS.PLACEHOLDER.STATO_PROC',
          size: '12|12|6|4|4'
        })
      }
    });

    trimOnChange(form, 'anno_presentazione_progetto');
    trimOnChange(form, 'codice_pratica');
    trimOnChange(form, 'denominazione_oggetto');

    return form;
  }

  private makeList(options: SelectOption<number, string>[], nomeCampo: string) {
    const list = [];
    options.forEach((opt) => {
      list.push(opt[nomeCampo]);
    });
    return list;
  }
}
