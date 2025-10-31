
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { TestInputGenericiConsts } from './test-input-form.consts';
import { ScrivaFormConfigurator } from 'src/app/shared/services/form-inputs/utilities/scriva-form-builer.classes';
import { ScrivaErrorKeys } from 'src/app/shared/utils/classes/errors-keys';

/**
 * Classe che definisce le configurazioni per i campi del componente associato.
 */
export class TestInputFormFormConfigs extends ScrivaFormConfigurator {
  /** TestInputGenericiConsts come classe contenente le costanti associate al componente. */
  private TEST_CONSTS = new TestInputGenericiConsts();

  /**
   * Costruttore.
   */
  constructor(formBuilder: FormBuilder) {
    // Richiamo il super
    super(formBuilder);
    // Lancio la funzione di setup dell'oggetto di configurazione
    this.setFormConfigs(this.TEST_CONSTS);
    // Lancio la funzione di setup per i validatori
    this.setFormValidators(this.TEST_CONSTS);
  }

  /**
   * Funzione che definisce le logiche di popolazione dell'oggetto per la configurazione dei campi del form.
   * La funzione è pensata per l'override.
   * @param keys TestInputGenericiConsts che definisce la struttura dalla quale estrarre le chiavi.
   * @override
   */
  protected setFormConfigs(keys: TestInputGenericiConsts) {
    // Definisco i formControl per i campi del form con le chiavi definite nella classe di costanti.
    this.formConfigs[keys.ANNO_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.CHECKBOX_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.DATE_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.EMAIL_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.LABEL_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.NUMBER_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.NUMBER_FORMATTED_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.NUMBER_IT_FORMATTED_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.RADIO_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.TEXT_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.TEXTAREA_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.TEXT_FAKE_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.TYPEAHEAD_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
    this.formConfigs[keys.SEARCH_FORM] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required] }
    );
   
  }

  /**
   * Funzione che definisce le logiche di generazione delle funzioni di controlli per i campi incrociati del form.
   * La funzione è pensata per l'override.
   * @param keys TestInputGenericiConsts che definisce la struttura dalla quale estrarre le chiavi.
   * @override
   */
  protected setFormValidators(keys: TestInputGenericiConsts) {
    // Definisco la costante contenente le chiavi d'errore per i forms validators
    const ERR_KEY = ScrivaErrorKeys;

    // Assegno i validatori
    this.formValidators = [];
  }
}
