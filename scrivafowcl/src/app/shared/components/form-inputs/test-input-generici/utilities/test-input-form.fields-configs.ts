import { CommonConsts } from 'src/app/shared/consts/common-consts.consts';
import { ScrivaFormBuilderService } from 'src/app/shared/services/form-inputs/scriva-form-builder.service';
import {
  ScrivaFormInputCheckbox,
  ScrivaFormInputDatepicker,
  ScrivaFormInputEmail,
  ScrivaFormInputNumber,
  ScrivaFormInputRadio,
  ScrivaFormInputSearch,
  ScrivaFormInputSelect,
  ScrivaFormInputText,
  ScrivaFormInputTypeahead,
} from '../../form-input/utilities/form-input.classes';
import { TestInputGenericiConsts } from './test-input-form.consts';
import { ScrivaComponentConfig } from './test-input-form.interfaces';
import { ScrivaInputLabelComponent } from '../../scriva-input-label/scriva-input-label.component';
import { ScrivaFormBuilderSize } from '../../form-input/utilities/form-input.enums';
import { of } from 'rxjs';
import { IScrivaCheckboxData } from '../../form-input/utilities/form-input.interfaces';

/**
 * Classe che definisce le configurazioni per i campi del componente associato.
 */
export class TestInputFormFieldsConfig {
  /** Oggetto contenente le costanti comuni all'applicazione. */
  C_C = new CommonConsts();
  /** CanoniFormConsts come classe che definisce le costanti del componente. */
  TEST_CONSTS = new TestInputGenericiConsts();

  /** Oggetto di configurazione per il campo: annoSelect */
  testInputCheckboxConfig: ScrivaComponentConfig<ScrivaFormInputCheckbox>;
  testInputDateConfig: ScrivaComponentConfig<ScrivaFormInputDatepicker>;
  testInputEmailConfig: ScrivaComponentConfig<ScrivaFormInputEmail>;
  testInputLabelConfig: ScrivaComponentConfig<ScrivaFormInputText>;
  testInputNumberConfig: ScrivaComponentConfig<ScrivaFormInputNumber>;
  testInputNumberFormattedConfig: ScrivaComponentConfig<ScrivaFormInputNumber>;
  testInputNumberItFormatConfig: ScrivaComponentConfig<ScrivaFormInputNumber>;
  testInputRadioConfig: ScrivaComponentConfig<ScrivaFormInputRadio>;
  testInputSelectConfig: ScrivaComponentConfig<ScrivaFormInputSelect>;
  testInputTextConfig: ScrivaComponentConfig<ScrivaFormInputText>;
  testInputTextFakeConfig: ScrivaComponentConfig<ScrivaFormInputText>;
  testInputTextareaConfig: ScrivaComponentConfig<ScrivaFormInputText>;
  testInputTypeaheadConfig: ScrivaComponentConfig<ScrivaFormInputTypeahead>;
  testInputSearchConfig: ScrivaComponentConfig<ScrivaFormInputSearch>;

  /**
   * Costruttore.
   */
  constructor(scrivaFormBuilder: ScrivaFormBuilderService) {
    // Lancio il setup delle configurazioni
    this.setupFormInputs(scrivaFormBuilder);
  }

  /**
   * Funzione di setup per le input del form.
   * @param scrivaFormBuilder ScrivaFormBuilderService servizio per la costruzione delle form input.
   * @returns Oggetto contenente le configurazioni dei campi del form.
   */
  setupFormInputs(scrivaFormBuilder: ScrivaFormBuilderService) {
    this.testInputSelectConfig = scrivaFormBuilder.genInputSelect({
      label: this.TEST_CONSTS.LABEL_ANNO,
      emptyLabelSelected: false,
      showErrorFC: true,
    });

    this.testInputCheckboxConfig = scrivaFormBuilder.genInputCheckbox({});
    const checkboxSource: IScrivaCheckboxData[] = [
      {
        id: this.TEST_CONSTS.LABEL_CHECKBOX,
        label: this.TEST_CONSTS.LABEL_CHECKBOX,
        value: false,
        check: false,
      },
    ];
    this.testInputCheckboxConfig.source = checkboxSource;
    this.testInputDateConfig = scrivaFormBuilder.genInputDatepicker({
      label: this.TEST_CONSTS.LABEL_DATE,
      showErrorFC: true,
    });
    this.testInputEmailConfig = scrivaFormBuilder.genInputEmail({
      label: this.TEST_CONSTS.LABEL_EMAIL,
      showErrorFC: true,
    });
    this.testInputLabelConfig = scrivaFormBuilder.genInputText({
      label: this.TEST_CONSTS.LABEL_LABEL,
      showErrorFC: true,
    });
    this.testInputNumberConfig = scrivaFormBuilder.genInputNumber({
      label: this.TEST_CONSTS.LABEL_NUMBER,
      showErrorFC: true,
    });
    this.testInputNumberFormattedConfig = scrivaFormBuilder.genInputNumber({
      label: this.TEST_CONSTS.LABEL_NUMBER_FORMATTED,
      useDecimal: true,
      decimals: 4,
      size: ScrivaFormBuilderSize.standard,
    });
    this.testInputNumberItFormatConfig = scrivaFormBuilder.genInputNumber({
      label: this.TEST_CONSTS.LABEL_NUMBER_IT_FORMATTED,
      useDecimal: true,
      decimals: 4,
      size: ScrivaFormBuilderSize.standard,
    });
    this.testInputRadioConfig = scrivaFormBuilder.genInputRadio({
      label: this.TEST_CONSTS.LABEL_RADIO,
      showErrorFC: true,
    });
    this.testInputTextConfig = scrivaFormBuilder.genInputText({
      label: this.TEST_CONSTS.LABEL_TEXT,
      showErrorFC: true,
    });
    this.testInputTextFakeConfig = scrivaFormBuilder.genInputText({
      label: this.TEST_CONSTS.LABEL_TEXT_FAKE,
      showErrorFC: true,
      size: ScrivaFormBuilderSize.small,
    });
    this.testInputTextareaConfig = scrivaFormBuilder.genInputText({
      label: this.TEST_CONSTS.LABEL_TEXTAREA,
      showErrorFC: true,
    });
    this.testInputSearchConfig = scrivaFormBuilder.genInputSearch({
      label: this.TEST_CONSTS.LABEL_SEARCH,
      showErrorFC: true,
    });

    // Definisco la funzione di recupero dati alla digitazione
    const typeaheadSearch = (v: string) => {
      // Richiamo la funzione di scarico dei comuni
      return of([
        { denom_comune: 'Roma', provincia: { sigla_provincia: 'RM' } },
      ]);
    };
    // Definisco la funzione di mapping dati a seguito della scelta dai consigli
    const typeaheadMap = (c: any) => {
      // Richiamo la funzione di comodo per la formattazione
      return typeaheadComuneFormatter(c);
    };
    this.testInputTypeaheadConfig = scrivaFormBuilder.genInputTypeahead({
      label: this.TEST_CONSTS.LABEL_TYPEAHEAD,
      showErrorFG: true,
      showErrorFC: true,
      typeaheadSearch,
      typeaheadMap,
      maxLength: 100,
    });
  }
}

/**
 * Funzione di comodo che definisce le logiche di formattazione dell'output per i typeahead del comune.
 * @param c Comune con le informazioni del comune.
 * @returns string con il nome del comune formattato <NOME_COMUNE> (<SIGLA_PROVINCIA>).
 */
export const typeaheadComuneFormatter = (c: any): string => {
  // Recupero il nome del comune
  const cDes = c?.denom_comune ?? '';
  // Recupero la sigla provincia
  const sProv = c?.provincia?.sigla_provincia;
  const spDes = sProv ? `(${sProv})` : '';

  // Definisco la descrizione
  const desc = `${cDes} ${spDes}`.trim();

  // Mappo le informazioni dell'oggetto per valorizzare la input
  return desc;
};
