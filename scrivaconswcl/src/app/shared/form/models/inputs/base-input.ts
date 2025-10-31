/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { EventEmitter } from '@angular/core';
import {
  AbstractControlOptions,
  AsyncValidatorFn,
  FormControl,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { merge, Observable, Subscription } from 'rxjs';
import { debounceTime, tap } from 'rxjs/operators';
import { BootstrapSize } from '../bootstrap-size';
import { InternalStatusMessage, ValidationStatus } from '../validations';
import { InputStatus } from '../validations/input-status';
import { Help } from '@app/core/interfaces/help';
declare type NbComponentStatus =
  | 'basic'
  | 'primary'
  | 'success'
  | 'warning'
  | 'danger'
  | 'info'
  | 'control';

export type ValidatorOrOptions =
  | ValidatorFn
  | ValidatorFn[]
  | AbstractControlOptions
  | null;

export interface BaseInputConstructor<T = string> {
  value?: T;
  label?: string;
  size?: string | BootstrapSize;
  placeholder?: string;
  index?: number;
  clearable?: boolean;
  required?: boolean;
  readonly?: boolean;
  pattern?: string;
  valueChange?: (value: T) => void;
  validationStatus?: ValidationStatus[];
  validatorOrOpts?: ValidatorOrOptions;
  asyncValidator?: AsyncValidatorFn | AsyncValidatorFn[] | null;
  formGroupClass?: string[];
  maxLenght?: number;
  minLenght?: number;
  help?: Help;
}

export const TOUCHED = 'TOUCHED';
export const UNTOUCHED = 'UNTOUCHED';
export const DIRTY = 'DIRTY';
export const PRISTINE = 'PRISTINE';

export abstract class BaseInput<T = string> extends FormControl {
  readonly inputType: string;
  readonly value: T;

  readonly size?: string | BootstrapSize;
  readonly label?: string;
  readonly placeholder?: string;
  readonly index?: number;
  readonly clearable?: boolean;
  private _required?: boolean;
  readonly readonly?: boolean;
  readonly pattern?: string;
  readonly maxLenght?: number;
  readonly minLenght?: number;
  readonly help?: Help;
  /**
   * The input status of the control. There are four possible
   * status values:
   *
   * * **DIRTY**: This control has been modified.
   * * **PRISTINE**: This control has not been modified yet.
   * * **TOUCHED**: This control has been touched.
   * * **UNTOUCHED**: This control has not been touched yet..
   *
   * These status values are mutually exclusive, so a control cannot be
   * both dirty AND pristine or touched AND untuched.
   */
  readonly inputStatus: string;
  readonly inputStatusChanges: Observable<any>;

  readonly valueChange?: (value: T) => void;

  readonly validationStatus?: ValidationStatus[];

  readonly formGroupClass?: string[];
  readonly colorStatus: NbComponentStatus;
  readonly statusMessage: InternalStatusMessage;

  private _subscriptions: Subscription[] = [];

  constructor(options: BaseInputConstructor<T>) {
    super(options.value, options.validatorOrOpts, options.asyncValidator);

    this.label = options.label;
    this.formGroupClass = options.formGroupClass || [];

    this._initSize(options.size);
    this._initInputStatusObservable();
    this._initStatusChanges();

    if (options.placeholder) {
      this.placeholder = options.placeholder;
    }

    if (options.index) {
      this.index = options.index;
    }

    if (options.clearable) {
      this.clearable = options.clearable;
    }

    if (options.required) {
      this.required = options.required;
      this.setValidators(Validators.required);
    }

    if (options.maxLenght) {
      this.maxLenght = options.maxLenght;
      const validator = [];
      if (this.validator) {
        validator.push(this.validator);
      }
      validator.push(Validators.maxLength(this.maxLenght));
      this.setValidators(validator);
    }

    if (options.minLenght) {
      this.minLenght = options.minLenght;
      this.setValidators(Validators.minLength(this.minLenght));
    }

    if (options.readonly) {
      this.readonly = options.readonly;
      this.disable();
    }

    if (options.pattern) {
      this.pattern = options.pattern;
      const validator = [];
      if (this.validator) {
        validator.push(this.validator);
      }
      validator.push(Validators.pattern(options.pattern));
      this.setValidators(validator);
    }

    if (options.valueChange) {
      this.valueChange = options.valueChange;
      this._initValueChanges();
    }

    if (options.validationStatus) {
      this.validationStatus = options.validationStatus;
    }

    if (options.help) {
      this.help = options.help;
    }

    this.colorStatus = this._getColorStatus();
    this.statusMessage = this._getStatusMessage();
  }

  /** @internal */
  private _initValueChanges(): void {
    // inizializza evento value changes
    this._subscriptions.push(this.valueChanges.subscribe(this.valueChange));
  }

  /** @internal */
  private _initStatusChanges(): void {
    this._subscriptions.push(
      merge(this.inputStatusChanges, this.statusChanges, this.valueChanges)
        .pipe(
          debounceTime(10),
          tap(() => {
            (
              this as {
                colorStatus: any;
              }
            ).colorStatus = this._getColorStatus();
            (
              this as {
                statusMessage: any;
              }
            ).statusMessage = this._getStatusMessage();
          })
        )
        .subscribe()
    );
  }

  /** @internal */
  private _initSize(size: string | BootstrapSize): void {
    if (typeof size === 'string') {
      (this as { size: any }).size = BootstrapSize.formPipe(size);
    } else if (size instanceof BootstrapSize) {
      (this as { size: any }).size = size;
    } else {
      (this as { size: any }).size = new BootstrapSize();
    }
  }

  /** @internal */
  private _initInputStatusObservable(): void {
    (
      this as {
        inputStatusChanges: Observable<any>;
      }
    ).inputStatusChanges = new EventEmitter();
  }

  /** @internal */
  private _getColorStatus(): NbComponentStatus {
    if (this.validationStatus && Array.isArray(this.validationStatus)) {
      for (const vs of this.validationStatus) {
        if ((vs as InputStatus).checkFn(this)) {
          return (vs as InputStatus).status;
        }
      }
    }

    if (
      this.invalid &&
      (!this.validationStatus || !Array.isArray(this.validationStatus))
    ) {
      return 'danger';
    }

    return 'basic';
  }

  /** @internal */
  private _getStatusMessage(): InternalStatusMessage {
    if (this.validationStatus && Array.isArray(this.validationStatus)) {
      for (const vs of this.validationStatus) {
        if ((vs as InputStatus).checkFn(this)) {
          return (vs as InputStatus).message;
        }
      }
    }

    return null;
  }

  isRequired(): boolean {
    let required = false;

    if (this.required === true) {
      required = true;
    }

    if (this.validator) {
      const validationResult = this.validator(new FormControl());
      required =
        validationResult !== null && validationResult.required === true;
    }

    return required;
  }

  unsubscribeSubscriptions(): void {
    this._subscriptions.forEach((subscription) => {
      subscription.unsubscribe();
    });
  }

  setSize(size: string | BootstrapSize): void {
    this._initSize(size);
  }

  setIndex(index: number): void {
    (this as { index: number }).index = index;
  }

  setValue(
    value: any,
    options?: {
      onlySelf?: boolean;
      emitEvent?: boolean;
      emitModelToViewChange?: boolean;
      emitViewToModelChange?: boolean;
    }
  ): void {
    super.setValue(value, options);
    (this as { value: any }).value = value;
  }
  
  patchValue(
    value: any,
    options?: {
      onlySelf?: boolean;
      emitEvent?: boolean;
      emitModelToViewChange?: boolean;
      emitViewToModelChange?: boolean;
    }
  ): void {
    super.patchValue(value, options);
  }

  markAsTouched(opts: { onlySelf?: boolean; emitEvent?: boolean } = {}): void {
    super.markAsTouched(opts);

    (this as { inputStatus: string }).inputStatus = TOUCHED;

    if (opts.emitEvent !== false) {
      (this.inputStatusChanges as EventEmitter<any>).emit(this.inputStatus);
    }
  }

  markAsUntouched(
    opts: { onlySelf?: boolean; emitEvent?: boolean } = {}
  ): void {
    super.markAsUntouched(opts);

    (this as { inputStatus: string }).inputStatus = UNTOUCHED;

    if (opts.emitEvent !== false) {
      (this.inputStatusChanges as EventEmitter<any>).emit(this.inputStatus);
    }
  }

  markAsDirty(opts: { onlySelf?: boolean; emitEvent?: boolean } = {}): void {
    super.markAsDirty(opts);

    (this as { inputStatus: string }).inputStatus = DIRTY;

    if (opts.emitEvent !== false) {
      (this.inputStatusChanges as EventEmitter<any>).emit(this.inputStatus);
    }
  }

  markAsPristine(opts: { onlySelf?: boolean; emitEvent?: boolean } = {}): void {
    super.markAsPristine(opts);

    (this as { inputStatus: string }).inputStatus = PRISTINE;

    if (opts.emitEvent !== false) {
      (this.inputStatusChanges as EventEmitter<any>).emit(this.inputStatus);
    }
  }

  set required(required: boolean) {
    this._required = required;
  }

  get required() {
    return this._required;
  }
}
