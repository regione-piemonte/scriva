/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable, Optional, EventEmitter } from '@angular/core';

import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

import { Language } from '../models/language.model';
import { I18nServiceConfig } from '../models/i18n-service.model';

@Injectable()
export class I18nService {
  public languages: Language[];
  public onLangChange$: EventEmitter<LangChangeEvent> = this.translateService
    .onLangChange;

  constructor(
    @Optional() public config: I18nServiceConfig,
    private translateService: TranslateService
  ) {
    if (config) {
      this.config = config;
      this.init(config);
    }
  }

  /**
   * Download the i18n config file and init default language
   */
  private init(config: I18nServiceConfig): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (config.langs) {
        this.initLocalLangs().then(resolve, reject);
      } else {
        reject(new Error('NO_I18N_CONFIG_DEFINED'));
      }
    });
  }

  /**
   * Set default (fallback) language
   * Use the local language files and store them in LocalStorage
   * @returns promise that resolve init completed
   */
  private initLocalLangs(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.config.langs.forEach((lang: Language) => {
        if (lang.code) {
          this.translateService.setTranslation(lang.code, lang.translations);
        }
      });
      // Set the default language
      this.setDefaultLanguage();
      resolve();
    });
  }

  /**
   * Set the default language as fallback when a translation isn't found in the current language
   * @param lang Language to set as default
   */
  private setDefaultLanguage(): void {
    const lang = this.getDefault();
    this.translateService.setDefaultLang(lang.code);
    this.setLanguage(lang);
  }

  /**
   * Get the default language in langs property
   * @returns default language
   */
  private getDefault(): Language {
    const def = this.config.langs.find((l: Language) => l.isDefault);
    return def || this.config.langs[0];
  }

  /**
   * Get the language configuration if available, null otherwise
   * @param langCode Requested language
   * @returns language
   */
  private getConfig(langCode: string): Language | undefined {
    return this.config.langs.find((l: Language) => l.code === langCode);
  }

  /**
   * Get the current used language
   * @returns current language
   */
  getCurrentLanguage(): Language | undefined {
    return this.getConfig(this.translateService.currentLang);
  }

  /**
   * Get the default language
   * @returns language
   */
  getDefaultLanguage(): Language | undefined {
    return this.getConfig(this.translateService.getDefaultLang());
  }

  /**
   * Set the last used language for the next app bootstrap
   * @param lang Language to set as last
   * @param automatic If false or undefined the selection request is made by user, if true is automatic
   */
  setLanguage(lang: Language | string): void {
    if (typeof lang === 'string') {
      lang = <Language>this.getConfig(lang);
    }
    if (lang) {
      // this.storage.set(storageKeys.lastLang, lang);
      this.translateService.use(lang.code);
    }
  }

  /**
   * Get the list of all available languages
   */
  getAllLanguages(): Language[] {
    return this.config.langs;
  }

  /**
   * @param key translate key
   * @param params trenslate params
   * @returns string
   */
  translate(key: string, params?: Object): string {
    return this.translateService.instant(key, params);
  }
}
