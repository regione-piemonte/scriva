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
  EventEmitter,
  forwardRef,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormInputComponent } from '../../../../shared/components/form-inputs/form-input/form-input.component';
import { FormInputsService } from '../../../../shared/services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { IPDUAction } from './utilities/profilo-dato-utente.interfaces';

@Component({
  selector: 'scriva-profilo-dato-utente',
  templateUrl: './profilo-dato-utente.component.html',
  styleUrls: ['./profilo-dato-utente.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ProfiloDatoUtenteComponent),
      multi: true,
    },
  ],
})
export class ProfiloDatoUtenteComponent
  extends FormInputComponent<any>
  implements OnInit, ControlValueAccessor
{
  /** Input string con la label del campo e per l'aria-label. */
  @Input() label: string = '';
  /** Input string che identifica l'icona da visualizzare a sinistra della label. */
  @Input() icon: string = '';
  /** Input boolean che definisce se il componente NON avrà la parte dati, ma solo la label ed eventuali actions. */
  @Input() labelOnly: boolean = false;
  /** Input IPDUAction[] che definisce la lista di action da visualizzare per il dato utente. */
  @Input() actions: IPDUAction[];

  /** Output IPDUAction collegato al click del pulsante di una specifica azione effettuata sulla pagina. */
  @Output('onActionClick') onActionClick$ = new EventEmitter<IPDUAction>();

  /**
   * Costruttore.
   */
  constructor(
    formInputs: FormInputsService,
    scrivaUtilities: ScrivaUtilitiesService
  ) {
    // Richiamo il super
    super(formInputs, scrivaUtilities);
    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup del componente, lanciato dal costruttore.
   */
  private setupComponente() {}

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {}

  /**
   * ##############################
   * FUNZIONI COLLEGATE AL TEMPLATE
   * ##############################
   */

  /**
   * Funzione collegata al click di una action.
   * @param action IPDUAction con la configurazione dell'action premuta.
   */
  onActionClick(action: IPDUAction) {
    // Emetto l'evento di click con i dati
    this.onActionClick$.emit(action);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo per verificare l'esistenza di configurazioni per le azioni.
   * @returns boolean con il risultato del check.
   */
  get checkActions(): boolean {
    // Verifico e ritorno se esistono configurazioni per le action
    return this.actions?.length > 0;
  }
}
