/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { AsyncValidatorFn, FormGroup } from '@angular/forms';
import { BaseInput, ValidatorOrOptions } from './inputs';

export interface FormHeader {
  show: boolean;
  title?: string;
  closeButton?: boolean;
  actionClose?: () => void;
}

export interface FormFooter {
  submit?: {
    text?: string;
    disabled?: boolean;
    containerClass?: string[];
  };
  clear?: {
    show?: boolean;
    text?: string;
  };
}

export interface FormContructor {
  header: FormHeader;
  footer?: FormFooter;
  controls: {
    [key: string]: BaseInput | any;
  };
  /**
   * Indica se è un form di recerca
   */
  filter?: boolean;
  skipDestroy?: boolean;
  validatorOrOpts?: ValidatorOrOptions;
  asyncValidator?: AsyncValidatorFn | AsyncValidatorFn[] | null;
  onDestroy?: () => void;
}

export class Form extends FormGroup {
  header: FormHeader;
  footer?: FormFooter;
  filter = false;
  skipDestroy = false;

  controls: {
    [key: string]: BaseInput;
  };

  private _onDestroy: () => void;

  constructor(options: FormContructor) {
    super(options.controls, options.validatorOrOpts, options.asyncValidator);
    this.header = options.header;
    this.footer = options.footer;
    this._onDestroy = options.onDestroy;

    if (options.filter !== undefined) {
      this.filter = options.filter;
    }
    if (options.skipDestroy !== undefined) {
      this.skipDestroy = options.skipDestroy;
    }

    this._setControlsIndex(this.controls);
  }

  submit(): void {
    throw new Error('Form not used with <app-form></app-form> element');
  }

  get(path: (string | number)[] | string): BaseInput | null {
    return super.get(path) as BaseInput;
  }

  addControl(name: string, control: BaseInput): void {
    super.addControl(name, control);
  }

  registerControl(name: string, control: BaseInput): BaseInput {
    const retControl = super.registerControl(name, control) as BaseInput;

    if (retControl.index === undefined) {
      retControl.setIndex(this._findMaxIndex(this.controls) + 1);
    } else {
      this._incControlsIndexStartByIndexExceptName(retControl.index, name);
    }

    return retControl;
  }

  /**
   * Inserisce un nuovo campo prima del campo specificato da beforeName
   * @param name nome del campo da aggiungere
   * @param control campo da inserire
   * @param beforeName nome del campo il cui predecessore sarà field
   */
  addControlBefore(name: string, control: BaseInput, beforeName: string): void {
    const beforeControl = this.get(beforeName);

    if (beforeControl) {
      control.setIndex(beforeControl.index);
      beforeControl.setIndex(beforeControl.index);
      this.addControl(name, control);
    }
  }

  /**
   * Inserisce un nuovo campo dopo del campo specificato da afterName
   * @param name nome del campo da aggiungere
   * @param control campo da inserire
   * @param afterName nome del campo il cui successore sarà field
   */
  addControlAfter(
    name: string,
    control: BaseInput<any>,
    afterName: string,
  ): void {
    const afterControl = this.get(afterName);

    if (afterControl) {
      control.setIndex(afterControl.index + 1);
      this.addControl(name, control);
    }
  }

  onDestroy(): void {
    if (this._onDestroy && typeof this._onDestroy === 'function') {
      this._onDestroy();
    }
    for (const name in this.controls) {
      if (this.controls[name] instanceof BaseInput) {
        this.controls[name].unsubscribeSubscriptions();
      }
    }
  }

  private _setControlsIndex(
    controls:
      | {
      [key: string]: BaseInput;
    },
  ): void {
    let i = this._findMaxIndex(controls) + 1;
    for (const name in controls) {
      if (controls[name]) {
        if (controls[name].index === undefined) {
          controls[name].setIndex(i);
          i++;
        }
      }
    }
  }

  private _findMaxIndex(
    controls:
      | {
      [key: string]: BaseInput;
    },
  ): number {
    let i = 0;
    for (const name in controls) {
      if (
        controls[name] &&
        Number.isInteger(controls[name].index) &&
        controls[name].index > i
      ) {
        i = controls[name].index;
      }
    }
    return i;
  }

  /** @internal */
  private _incControlsIndexStartByIndexExceptName(
    index: number,
    name: string,
  ): void {
    for (const _name in this.controls) {
      if (
        this.controls[_name] &&
        this.controls[_name].index !== undefined &&
        this.controls[_name].index >= index &&
        name !== _name
      ) {
        this.controls[_name].setIndex(this.controls[_name].index + 1);
      }
    }
  }
}
