/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { BaseInput, BaseInputConstructor } from './base-input';

export interface DateInputConstructor<T = Date>
  extends BaseInputConstructor<T> {
  value: T;
  /**
   * Datepicker date format. Can be used only with date adapters (moment, date-fns) since native date
   * object doesn't support formatting.
   */
  format: string;
  /**
   * Defines if we should render previous and next months
   * in the current month view.
   */
  boundingMonth: boolean;
  /**
   * Hide picker when a date or a range is selected, `true` by default
   */
  hideOnSelect: boolean;
  /**
   * Determines should we show calendars navigation or not.
   */
  showNavigation: boolean;
  /**
   * Determines should we show week numbers column.
   * False by default.
   */
  showWeekNumber: boolean;

  /**
   * Sets symbol used as a header for week numbers column
   */
  weekNumberSymbol: string;

  /**
   * Minimum available date for selection.
   */
  min: Date;
  /**
   * Maximum available date for selection.
   */
  max: Date;
  /**
   * Predicate that decides which cells will be disabled.
   */
  filter: (date: Date) => boolean;
}

export class DateInput<T = Date> extends BaseInput<T> {
  inputType = 'date';
  value: T;

  format: string;
  boundingMonth = true;
  hideOnSelect = true;
  showNavigation = true;
  showWeekNumber = false;
  weekNumberSymbol = '#';
  min: Date;
  max: Date;
  filter: (date: Date) => boolean;

  constructor(options: Partial<DateInputConstructor<T>>) {
    super(options);

    if (options.format) {
      this.format = options.format;
    }

    if (options.boundingMonth) {
      this.boundingMonth = options.boundingMonth;
    }

    if (options.hideOnSelect) {
      this.hideOnSelect = options.hideOnSelect;
    }

    if (options.showNavigation) {
      this.showNavigation = options.showNavigation;
    }

    if (options.showWeekNumber) {
      this.showWeekNumber = options.showWeekNumber;
    }

    if (options.weekNumberSymbol) {
      this.weekNumberSymbol = options.weekNumberSymbol;
    }

    if (options.min) {
      this.min = options.min;
    }

    if (options.max) {
      this.max = options.max;
    }

    if (options.filter) {
      this.filter = options.filter;
    }
  }
}
